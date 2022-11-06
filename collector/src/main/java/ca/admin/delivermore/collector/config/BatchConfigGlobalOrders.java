package ca.admin.delivermore.collector.config;

import ca.admin.delivermore.collector.data.Config;
import ca.admin.delivermore.collector.data.entity.GlobalOrderJson;
import ca.admin.delivermore.collector.data.entity.OrderDetail;
import ca.admin.delivermore.collector.data.entity.Restaurant;
import ca.admin.delivermore.collector.data.entity.TaskEntity;
import ca.admin.delivermore.collector.data.global.GlobalOrder;
import ca.admin.delivermore.collector.data.global.GlobalOrderList;
import ca.admin.delivermore.collector.data.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfigGlobalOrders {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private RestClientService restClientService;
    private RestaurantRepository restaurantRepository;
    private OrderDetailRepository orderDetailRepository;
    private GlobalOrderJsonRepository globalOrderJsonRepository;

    private TaskDetailRepository taskDetailRepository;

    private List<GlobalOrderJson> masterGlobalOrderJsonSource = new ArrayList<>();

    @Bean
    public ListItemReader<GlobalOrderJson> globalOrderJsonItemReader(){
        if(Config.getInstance().getRunGlobalOrderJob()){
            List<GlobalOrderJson> allGlobalOrders = new ArrayList<>();
            restClientService = new RestClientService();
            //process all restaurants that have an Auth Code
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
                System.out.println("globalOrderJsonItemReader: globalOrderJson:" + globalOrderJson);
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
            return stepBuilderFactory.get("globalOrderJsonStep").<GlobalOrderJson, GlobalOrderJson>chunk(10)
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
            System.out.println("globalOrderItemReader: globalOrderJsonList: size:" + globalOrderJsonList.size());
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
                    System.out.println("globalOrderItemReader: processing: could not map to GlobalOrderJson:" + globalOrderJson.toString());
                }else{
                    for (GlobalOrder globalOrder: globalOrderList.getOrders()) {
                        OrderDetail orderDetail = globalOrder.getOrderDetail();
                        orderDetail.setJsonSourceId(globalOrderJson.getId());
                        orderDetailList.add(orderDetail);
                        //update an existing TaskEntiy if one already exists
                        List<TaskEntity> taskEntityList = taskDetailRepository.findByOrderId(String.valueOf(orderDetail.getOrderId()));
                        if(taskEntityList!=null){
                            for (TaskEntity taskEntity: taskEntityList) {
                                taskEntity.updateGlobalData(orderDetail);
                                taskEntity.updateCalculatedFields();
                                taskEntity.updateDriverPayoutEntity();
                                taskDetailRepository.save(taskEntity);
                            }
                        }
                        System.out.println("globalOrderItemReader: globalOrderList:" + globalOrder);
                    }
                }
            }
            System.out.println(String.format("...processed %d globalOrders.", orderDetailList.size()));
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
                System.out.println("globalOrderDetailItemWriter: globalOrderJson:" + globalOrderJson);
            }
            return writer;
        }else{
            return null;
        }
    }

    @Bean
    public Step globalOrderJsonToDetailStep(){
        if(Config.getInstance().getRunGlobalOrderJob()){
            return stepBuilderFactory.get("globalOrderJsonToDetailStep").<OrderDetail,OrderDetail>chunk(10)
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
            return jobBuilderFactory.get("globalOrderJob")
                    .incrementer(new RunIdIncrementer())
                    .flow(globalOrderJsonStep())
                    .next(globalOrderJsonToDetailStep())
                    .end().build();
        }else{
            return null;
        }
    }

}
