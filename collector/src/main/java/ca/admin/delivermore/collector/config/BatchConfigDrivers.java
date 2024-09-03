package ca.admin.delivermore.collector.config;

import ca.admin.delivermore.collector.data.Config;
import ca.admin.delivermore.collector.data.service.DriversRepository;
import ca.admin.delivermore.collector.data.service.RestClientService;
import ca.admin.delivermore.collector.data.tookan.Driver;
import lombok.AllArgsConstructor;
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
import org.springframework.transaction.TransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfigDrivers {
    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;
    private RestClientService restClientService;
    private DriversRepository driversRepository;

    private List<Driver> activeDrivers = new ArrayList<>();

    @Bean
    public ListItemReader<Driver> driverItemReader(){
        if(Config.getInstance().getRunDriverJob()){
            restClientService = new RestClientService();
            List<Driver> restDrivers = restClientService.getAllDrivers();
            activeDrivers.addAll(restDrivers);
            return new ListItemReader<Driver>(restDrivers);
        }else{
            return null;
        }
    }

    @Bean
    public RepositoryItemWriter<Driver> driverItemWriter(){
        if(Config.getInstance().getRunDriverJob()){
            RepositoryItemWriter<Driver> writer = new RepositoryItemWriter<>();
            writer.setRepository(driversRepository);
            return writer;
        }else{
            return null;
        }
    }

    @Bean
    public Step driverStep(){
        if(Config.getInstance().getRunDriverJob()){
            return new StepBuilder("driverStep", jobRepository).<Driver, Driver>chunk(10, transactionManager)
                    .reader(driverItemReader())
                    .writer(driverItemWriter())
                    .build();
        }else{
            return null;
        }
    }

    @Bean
    public ListItemReader<Driver> cleanupDriverItemReader(){
        if(Config.getInstance().getRunDriverJob()){
            return new ListItemReader<Driver>(driversRepository.findAll());
        }else{
            return null;
        }
    }

    DriverProcessor driverProcessor(){
        return new DriverProcessor(activeDrivers);
    }

    @Bean
    public RepositoryItemWriter<Driver> cleanupDriverItemWriter(){
        if(Config.getInstance().getRunDriverJob()){
            RepositoryItemWriter<Driver> writer = new RepositoryItemWriter<>();
            writer.setRepository(driversRepository);
            return writer;
        }else{
            return null;
        }
    }

    @Bean
    public Step cleanupDriverStep(){
        if(Config.getInstance().getRunDriverJob()){
            return new StepBuilder("cleanupDriverStep",jobRepository).<Driver, Driver>chunk(10, transactionManager)
                    .reader(cleanupDriverItemReader())
                    .processor(driverProcessor())
                    .writer(cleanupDriverItemWriter())
                    .build();
        }else{
            return null;
        }
    }


    @Bean
    public Job driverJob(){
        if(Config.getInstance().getRunDriverJob()){
            return new JobBuilder("driverJob", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .flow(driverStep())
                    .next(cleanupDriverStep())
                    .end().build();
        }else{
            return null;
        }
    }

}
