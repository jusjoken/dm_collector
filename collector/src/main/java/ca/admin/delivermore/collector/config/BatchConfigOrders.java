package ca.admin.delivermore.collector.config;

import ca.admin.delivermore.collector.data.Config;
import ca.admin.delivermore.collector.data.entity.OrderDetail;
import ca.admin.delivermore.collector.data.service.OrderDetailLoader;
import ca.admin.delivermore.collector.data.service.OrderDetailRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.data.RepositoryItemWriter;
import org.springframework.batch.infrastructure.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
//@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfigOrders {
    private static final Logger log = LoggerFactory.getLogger(BatchConfigOrders.class);

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;
    private OrderDetailRepository orderDetailRepository;

    @Bean
    public ListItemReader<OrderDetail> orderDetailItemReader(){
        if(Config.getInstance().getRunOrderJob()){
            log.info("BatchConfigOrders: orderDetailItemReader");
            OrderDetailLoader orderDetailLoader = new OrderDetailLoader();
            orderDetailLoader.loadFromCSV("global_restaurants_orders.csv");
            return new ListItemReader<OrderDetail>(orderDetailLoader.getOrderDetailList());
        }else{
            return null;
        }
    }

    @Bean
    public RepositoryItemWriter<OrderDetail> orderDetailItemWriter(){
        if(Config.getInstance().getRunOrderJob()){
            RepositoryItemWriter<OrderDetail> writer = new RepositoryItemWriter<>(orderDetailRepository);
            return writer;
        }else{
            return null;
        }
    }

    @Bean
    public Step orderDetailStep(){
        if(Config.getInstance().getRunOrderJob()){
            return new StepBuilder("orderDetailStep", jobRepository).<OrderDetail, OrderDetail>chunk(10).transactionManager(transactionManager)
                    .reader(orderDetailItemReader())
                    .writer(orderDetailItemWriter())
                    .build();
        }else{
            return null;
        }
    }

    @Bean
    public Job orderDetailsJob(){
        if(Config.getInstance().getRunOrderJob()){
            log.info("BatchConfigRestaurant: orderDetailsJob");
            return new JobBuilder("orderDetailJob", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .flow(orderDetailStep())
                    .end().build();
        }else{
            return null;
        }
    }

}
