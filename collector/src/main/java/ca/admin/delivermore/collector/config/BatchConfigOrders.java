package ca.admin.delivermore.collector.config;

import ca.admin.delivermore.collector.data.Config;
import ca.admin.delivermore.collector.data.entity.OrderDetail;
import ca.admin.delivermore.collector.data.entity.TaskEntity;
import ca.admin.delivermore.collector.data.service.OrderDetailLoader;
import ca.admin.delivermore.collector.data.service.OrderDetailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
@Log4j2
public class BatchConfigOrders {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
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
            RepositoryItemWriter<OrderDetail> writer = new RepositoryItemWriter<>();
            writer.setRepository(orderDetailRepository);
            return writer;
        }else{
            return null;
        }
    }

    @Bean
    public Step orderDetailStep(){
        if(Config.getInstance().getRunOrderJob()){
            return stepBuilderFactory.get("orderDetailStep").<OrderDetail, OrderDetail>chunk(10)
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
            return jobBuilderFactory.get("orderDetailJob")
                    .incrementer(new RunIdIncrementer())
                    .flow(orderDetailStep())
                    .end().build();
        }else{
            return null;
        }
    }

}
