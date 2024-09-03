package ca.admin.delivermore.collector.config;


import ca.admin.delivermore.collector.data.Config;
import ca.admin.delivermore.collector.data.entity.TaskEntity;
import ca.admin.delivermore.collector.data.service.*;
import ca.admin.delivermore.collector.data.tookan.TaskDetail;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
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

@Configuration
//@EnableBatchProcessing
@AllArgsConstructor
@Log4j2
public class BatchConfigTasks {

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;
    private RestClientService restClientService;

    private TaskDetailRepository taskDetailRepository;
    private RestaurantRepository restaurantRepository;
    private OrderDetailRepository orderDetailRepository;
    private DriversRepository driversRepository;

    @Bean
    public ListItemReader<TaskDetail> taskItemReader(){
        if(Config.getInstance().getRunTaskJob()){
            LocalDateTime maxDateTime = taskDetailRepository.getMaxDate();
            //Long maxJobId = taskDetailRepository.getMaxSuccessfulJobId();
            //log.info("taskItemReader: maxJobId:" + maxJobId);
            restClientService = new RestClientService();
            log.info("taskItemReader: processing all tasks that are not already successful.");
            //return new ListItemReader<TaskDetail>(restClientService.getAllTasks(LocalDate.parse("2022-08-14"),LocalDate.parse("2022-08-15")));
            //return new ListItemReader<TaskDetail>(restClientService.getAllTasks(LocalDate.parse("2022-08-14"),LocalDate.now()));
            //return new ListItemReader<TaskDetail>(restClientService.getAllTasks(LocalDate.parse("2022-09-24"),LocalDate.now()));
            //return new ListItemReader<TaskDetail>(restClientService.getAllTasks(maxDateTime.toLocalDate(),LocalDate.now(),maxJobId));
            //return new ListItemReader<TaskDetail>(restClientService.getAllTasks(LocalDate.parse("2024-02-20"),LocalDate.now()));
            return new ListItemReader<TaskDetail>(restClientService.getAllTasks(maxDateTime.toLocalDate(),LocalDate.now()));
        }else{
            return null;
        }
    }

    TaskProcessor taskProcessor() {
        return new TaskProcessor(restaurantRepository,orderDetailRepository,driversRepository,taskDetailRepository);
    }

    @Bean
    public RepositoryItemWriter<TaskEntity> taskItemWriter(){
        if(Config.getInstance().getRunTaskJob()){
            RepositoryItemWriter<TaskEntity> writer = new RepositoryItemWriter<>();
            writer.setRepository(taskDetailRepository);
            return writer;
        }else{
            return null;
        }
    }

    @Bean
    public Step taskStep(){
        if(Config.getInstance().getRunTaskJob()){
            return new StepBuilder("taskStep", jobRepository).<TaskDetail, TaskEntity>chunk(100, transactionManager)
                    .reader(taskItemReader())
                    .processor(taskProcessor())
                    .writer(taskItemWriter())
                    .build();
        }else{
            return null;
        }
    }

    @Bean
    public Job taskJob(){
        if(Config.getInstance().getRunTaskJob()){
            return new JobBuilder("taskJob", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .flow(taskStep())
                    .end().build();
        }else{
            return null;
        }
    }

}
