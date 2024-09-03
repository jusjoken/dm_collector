package ca.admin.delivermore.collector.config;

import ca.admin.delivermore.collector.data.Config;
import ca.admin.delivermore.collector.data.Utility;
import ca.admin.delivermore.collector.data.entity.SchedulerReportEvent;
import ca.admin.delivermore.collector.data.service.DriversRepository;
import ca.admin.delivermore.collector.data.service.EmailService;
import ca.admin.delivermore.collector.data.service.SchedulerReportEventRepository;
import ca.admin.delivermore.collector.data.service.TeamsRepository;
import ca.admin.delivermore.collector.data.tookan.Driver;
import ca.admin.delivermore.collector.data.tookan.Team;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Configuration
//@EnableBatchProcessing
@AllArgsConstructor
@Log4j2
public class BatchConfigScheduleReport {

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;
    private SchedulerReportEventRepository schedulerReportEventRepository;
    private DriversRepository driversRepository;
    private TeamsRepository teamsRepository;
    private EmailService emailService;

    private List<SchedulerReportEvent> schedulerReportEvents;

    @Bean
    public ListItemReader<SchedulerReportEvent> schedulerItemReader(){
        if(Config.getInstance().getRunScheduleReportJob()){
            LocalDate currentDate = LocalDate.now();
            log.info("schedulerItemReader: processing all schedule entries for " + currentDate);
            schedulerReportEvents = new ArrayList<>();
            schedulerReportEvents = schedulerReportEventRepository.findByPublishedAndStartBetween(Boolean.TRUE,currentDate.atStartOfDay(),currentDate.atTime(23,59));
            return new ListItemReader<SchedulerReportEvent>(schedulerReportEvents);
        }else{
            return null;
        }
    }

    @Bean
    public RepositoryItemWriter<SchedulerReportEvent> schedulerItemWriter(){
        if(Config.getInstance().getRunScheduleReportJob()){
            RepositoryItemWriter<SchedulerReportEvent> writer = new RepositoryItemWriter<>();
            writer.setRepository(schedulerReportEventRepository);
            LocalDate currentDate = LocalDate.now();
            String subject = "DeliverMore schedule:" + currentDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
            String emailTo = "support@delivermore.ca";
            String emailBody = "<p>Schedule for " + currentDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + "<br><br>";
            log.info("schedulerItemWriter: email subject:" + subject );
            Utility.EventType prevType = null;
            //event list is ordered by locationId, eventType then start/end
            Long currentLocationId = null;
            String currentLocationName = null;
            for (SchedulerReportEvent event: schedulerReportEvents) {
                Long locationId = event.getTeamId();
                if(!locationId.equals(currentLocationId)){
                    currentLocationId = locationId;
                    //TODO: lookup the team name from this id
                    Team currentTeam = teamsRepository.findByTeamId(currentLocationId);
                    if(currentTeam!=null){
                        currentLocationName = currentTeam.getTeamName();
                        emailBody += "<br><br>" + currentLocationName + " schedule<br>";
                    }
                }
                String driverId = event.getResourceId();
                String driverName = "Unassigned";
                if(driverId.equals("0")){
                    driverName = "Available shift";
                }else{
                    Driver driver = driversRepository.findByFleetId(Long.valueOf(driverId));
                    if(driver!=null){
                        driverName = driver.getName();
                    }
                }
                if(prevType!=null && event.getType()!=prevType) emailBody += "<br>";
                prevType = event.getType();
                emailBody += event.formatForReport() + " : " + driverName + "<br>";
                log.info("schedulerItemWriter: email record:" + event.formatForReport() + " : " + driverName);
            }
            emailBody += "</p>";
            emailService.sendMailWithHtmlBody(emailTo,subject,emailBody);
            return writer;
        }else{
            return null;
        }
    }

    @Bean
    public Step schedulerStep(){
        if(Config.getInstance().getRunScheduleReportJob()){
            return new StepBuilder("schedulerStep", jobRepository).<SchedulerReportEvent, SchedulerReportEvent>chunk(10, transactionManager)
                    .reader(schedulerItemReader())
                    .writer(schedulerItemWriter())
                    .build();
        }else{
            return null;
        }
    }

    @Bean
    public Job schedulerJob(){
        if(Config.getInstance().getRunScheduleReportJob()){
            return new JobBuilder("schedulerJob", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .flow(schedulerStep())
                    .end().build();
        }else{
            return null;
        }
    }

}
