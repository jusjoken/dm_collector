package ca.admin.delivermore.collector.config;

import ca.admin.delivermore.collector.data.Config;
import ca.admin.delivermore.collector.data.entity.GlobalOrderJson;
import ca.admin.delivermore.collector.data.entity.OrderDetail;
import ca.admin.delivermore.collector.data.entity.Restaurant;
import ca.admin.delivermore.collector.data.entity.TaskEntity;
import ca.admin.delivermore.collector.data.global.GlobalOrder;
import ca.admin.delivermore.collector.data.global.GlobalOrderList;
import ca.admin.delivermore.collector.data.service.*;
import ca.admin.delivermore.collector.data.tookan.TaskByOrderDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
//@EnableBatchProcessing
@AllArgsConstructor
@Log4j2
public class BatchConfigGlobalOrders {
    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;
    private RestClientService restClientService;
    private RestaurantRepository restaurantRepository;
    private OrderDetailRepository orderDetailRepository;
    private GlobalOrderJsonRepository globalOrderJsonRepository;

    private TaskDetailRepository taskDetailRepository;

    private EmailService emailService;

    private List<GlobalOrderJson> masterGlobalOrderJsonSource = new ArrayList<>();
    //private Logger log = LoggerFactory.getLogger(BatchConfigGlobalOrders.class);

    @Bean
    public ListItemReader<GlobalOrderJson> globalOrderJsonItemReader(){
        if(Config.getInstance().getRunGlobalOrderJob()){
            List<GlobalOrderJson> allGlobalOrders = new ArrayList<>();
            restClientService = new RestClientService();
            //process all restaurants that have an Auth Code
            log.info("globalOrderJsonItemReader: processing all restaurants. Details in log only for one that have data.");
            for (Restaurant restaurant: restaurantRepository.findAll()) {
                if(restaurant.getGlobalAuthCode()!=null && !restaurant.getGlobalAuthCode().isEmpty()){
                    String tGlobalOrderJson = restClientService.getGlobalOrderJson(restaurant);
                    if(tGlobalOrderJson!=null){
                        GlobalOrderJson globalOrderJson = new GlobalOrderJson();
                        globalOrderJson.setJson(tGlobalOrderJson);
                        globalOrderJson.setStatus(GlobalOrderJson.Status.NEW);
                        globalOrderJson.setLastUpdated(LocalDateTime.now());
                        allGlobalOrders.add(globalOrderJson);
                    }
                }
            }
            for (GlobalOrderJson globalOrderJson: allGlobalOrders) {
                log.info("globalOrderJsonItemReader: globalOrderJson:" + globalOrderJson);
            }
            return new ListItemReader<GlobalOrderJson>(allGlobalOrders);
        }else{
            return null;
        }
    }

    @Bean
    public RepositoryItemWriter<GlobalOrderJson> globalOrderJsonItemWriter(){
        if(Config.getInstance().getRunGlobalOrderJob()){
            RepositoryItemWriter<GlobalOrderJson> writer = new RepositoryItemWriter<>();
            writer.setRepository(globalOrderJsonRepository);
            return writer;
        }else{
            return null;
        }
    }

    @Bean
    public Step globalOrderJsonStep(){
        if(Config.getInstance().getRunGlobalOrderJob()){
            return new StepBuilder("globalOrderJsonStep", jobRepository).<GlobalOrderJson, GlobalOrderJson>chunk(10, transactionManager)
                    .reader(globalOrderJsonItemReader())
                    .writer(globalOrderJsonItemWriter())
                    .build();
        }else{
            return null;
        }
    }

    @Bean
    public ListItemReader<OrderDetail> globalOrderItemReader(){
        if(Config.getInstance().getRunGlobalOrderJob()){
            List<GlobalOrderJson> globalOrderJsonList = new ArrayList<>();
            globalOrderJsonList.addAll(globalOrderJsonRepository.getAllNewJson());
            //log.info("globalOrderItemReader: globalOrderJsonList: size:" + globalOrderJsonList.size());
            masterGlobalOrderJsonSource.addAll(globalOrderJsonList);
            List<OrderDetail> orderDetailList = new ArrayList<>();

            for (GlobalOrderJson globalOrderJson : globalOrderJsonList){
                ObjectMapper objectMapper = new ObjectMapper();
                GlobalOrderList globalOrderList = null;
                try {
                    globalOrderList = objectMapper.readValue(globalOrderJson.getJson(),GlobalOrderList.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                if(globalOrderList==null){
                    log.info("globalOrderItemReader: processing: could not map to GlobalOrderJson:" + globalOrderJson.toString());
                }else{
                    for (GlobalOrder globalOrder: globalOrderList.getOrders()) {
                        OrderDetail orderDetail = globalOrder.getOrderDetail();
                        orderDetail.setJsonSourceId(globalOrderJson.getId());
                        orderDetailList.add(orderDetail);
                        //update an existing TaskEntiy if one already exists
                        List<TaskEntity> taskEntityList = taskDetailRepository.findByOrderId(String.valueOf(orderDetail.getOrderId()));
                        //log.info("globalOrderItemReader: checking if order is in tookan: " + orderDetail.getOrderId());
                        if(taskEntityList!=null && taskEntityList.size()>0){
                            //log.info("globalOrderItemReader: found in tookan: " + orderDetail.getOrderId());
                            for (TaskEntity taskEntity: taskEntityList) {
                                taskEntity.updateGlobalData(orderDetail);
                                taskEntity.updateCalculatedFields();
                                taskEntity.updateDriverPayoutEntity();
                                taskDetailRepository.save(taskEntity);
                            }
                        }else{
                            //log.info("globalOrderItemReader: NOT found in database.  Checking Tookan API for order: " + orderDetail.getOrderId());
                            //perform extra check incase the database is just behind in processing
                            if(restClientService.hasOrderId(orderDetail.getOrderId().toString())){
                                //nothing to do here as tookan has the order
                                //log.info("globalOrderItemReader: found order using Tookan API for order: " + orderDetail.getOrderId());

                            }else{
                                //this likely does nothing as was put here to catch a Global to Tookan issue that was fixed by Global
                                log.info("globalOrderItemReader: NOT found using Tookan API for order: " + orderDetail.getOrderId());
                                //no tookan task found so send an email
                                String emailBody = null;
                                List<Restaurant> restaurants = restaurantRepository.findEffectiveByRestaurantId(orderDetail.getRestaurantId(), LocalDate.now());
                                if(restaurants!=null && restaurants.size()>0){
                                    emailBody = "Restaurant:" + restaurants.get(0).getName() + " order:" + orderDetail.getOrderId() + " missing from Tookan";
                                }else{
                                    emailBody = "Order:" + orderDetail.getOrderId() + " missing from Tookan";
                                }
                                //add additional global order info to body
                                String tip;
                                if(orderDetail.getTip()==null) tip = "";
                                else tip = orderDetail.getTip().toString();
                                String customer;
                                if(orderDetail.getClientName()==null) customer = "";
                                else customer = orderDetail.getClientName();
                                emailBody = emailBody + "\nGlobal info: \n method: "
                                        + orderDetail.getPaymentMethod()
                                        + " \n subtotal: " + orderDetail.getSubtotal()
                                        + "\n taxes: " + orderDetail.getTotalTaxes()
                                        + "\n del fee: " + orderDetail.getDeliveryFee()
                                        + "\n service fee: " + orderDetail.getServiceFee()
                                        + "\n tip: " + tip
                                        + "\n customer: " + customer;
                                //calc what del fee should be as it needs to include taxes as custom orders do not have a taxes field
                                Double customDelFee = 4.5;
                                if(orderDetail.getDeliveryFee()!=null && orderDetail.getTotalTaxes()!=null){
                                    customDelFee = orderDetail.getDeliveryFee() + orderDetail.getTotalTaxes();
                                }
                                emailBody = emailBody + "\nCustom info:\n receipt total: " + orderDetail.getSubtotal() + "\n del fee: " + customDelFee + " (includes del fee and taxes if any)";
                                emailBody = emailBody + "\nExtra info:\n";
                                emailBody = emailBody + "\nCustomer Address: " + globalOrder.getClientAddress();
                                emailBody = emailBody + "\nFulfillAt: " + globalOrder.getFulfillAt();
                                emailBody = emailBody + "\nTotal Price: " + globalOrder.getTotalPrice();

                                emailService.sendMail("support@delivermore.ca", "Order missing task:" + orderDetail.getOrderId(), emailBody);
                                log.info("globalOrderItemReader: missing order from tookan: " + emailBody);
                            }
                        }

                        //need to see if this is for a non-partnered vendor where the order text needs sent to tookan descriptiion
                        List<Restaurant> restaurants = restaurantRepository.findEffectiveByRestaurantId(orderDetail.getRestaurantId(), LocalDate.now());
                        if(restaurants!=null && restaurants.size()>0){
                            //log.info("globalOrderItemReader: checking if need to process OrderText for order:" + orderDetail.getOrderId());
                            if(restaurants.get(0).getProcessOrderText()!=null && restaurants.get(0).getProcessOrderText()){
                                //log.info("globalOrderItemReader: processing OrderText to update tookan task for order:" + orderDetail.getOrderId());
                                TaskByOrderDetail taskByOrderDetail = restClientService.getTaskByOrderId(orderDetail.getOrderId().toString());
                                if(taskByOrderDetail!=null){
                                    Long jobId = taskByOrderDetail.getJobId();
                                    String newDesc = orderDetail.getOrderText();
                                    log.info("globalOrderItemReader: orderText created for order:" + orderDetail.getOrderId() + " jobId:" + jobId + " orderText:" + newDesc);
                                    restClientService.updateTaskDescriptiion(jobId,newDesc);
                                }
                            }
                        }


                        log.info("globalOrderItemReader: globalOrderList:" + globalOrder);
                    }
                }
            }
            log.info(String.format("...processed %d globalOrders.", orderDetailList.size()));
            return new ListItemReader<>(orderDetailList);
        }else{
            return null;
        }
    }

    @Bean
    public RepositoryItemWriter<OrderDetail> globalOrderDetailItemWriter(){
        if(Config.getInstance().getRunGlobalOrderJob()){
            RepositoryItemWriter<OrderDetail> writer = new RepositoryItemWriter<>();
            writer.setRepository(orderDetailRepository);
            //find the original JSON from the reader and set it to Status COMPLETE
            for (GlobalOrderJson globalOrderJson: masterGlobalOrderJsonSource) {
                globalOrderJson.setStatus(GlobalOrderJson.Status.COMPLETE);
                globalOrderJson.setLastUpdated(LocalDateTime.now());
                globalOrderJsonRepository.save(globalOrderJson);
                log.info("globalOrderDetailItemWriter: globalOrderJson:" + globalOrderJson);
            }
            return writer;
        }else{
            return null;
        }
    }

    @Bean
    public Step globalOrderJsonToDetailStep(){
        if(Config.getInstance().getRunGlobalOrderJob()){
            return new StepBuilder("globalOrderJsonToDetailStep", jobRepository).<OrderDetail,OrderDetail>chunk(10, transactionManager)
                    .reader(globalOrderItemReader())
                    .writer(globalOrderDetailItemWriter())
                    .build();
        }else{
            return null;
        }
    }

    @Bean
    public Job globalOrderJob(){
        if(Config.getInstance().getRunGlobalOrderJob()){
            return new JobBuilder("globalOrderJob", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .flow(globalOrderJsonStep())
                    .next(globalOrderJsonToDetailStep())
                    .end().build();
        }else{
            return null;
        }
    }

}
