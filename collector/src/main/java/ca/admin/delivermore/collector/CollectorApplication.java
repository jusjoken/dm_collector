package ca.admin.delivermore.collector;

import ca.admin.delivermore.collector.data.Config;
import ca.admin.delivermore.collector.data.service.RestClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.rmi.registry.Registry;

@SpringBootApplication
@ComponentScan({"ca.admin.delivermore"})
@EnableJpaRepositories("ca.admin.delivermore")
@EntityScan("ca.admin.delivermore")
public class CollectorApplication {
	private static Logger log = LoggerFactory.getLogger(CollectorApplication.class);

	public static void main(String[] args) {
		//System.exit(SpringApplication.exit(SpringApplication.run(CollectorApplication.class, args)));
		log.info("Working Directory = " + System.getProperty("user.dir"));
		log.info("CollectorApplication: startup args:");
		for ( String arg : args ) {
			log.info("CollectorApplication: startup arg:" + arg);
			//if(arg.contains("driverJob")) Config.getInstance().setRunDriverJob(true);
			if(checkJob("orderDetailJob", arg)){
				Config.getInstance().setRunOrderJob(true);
			}
			if(checkJob("globalOrderJob", arg)){
				Config.getInstance().setRunGlobalOrderJob(true);
			}
			if(checkJob("taskJob", arg)){
				Config.getInstance().setRunTaskJob(true);
			}
			if(checkJob("scheduleReportJob", arg)){
				Config.getInstance().setRunScheduleReportJob(true);
			}

			//if(arg.contains("restaurantJob")) Config.getInstance().setRunRestaurantJob(true);
		}
		SpringApplication.run(CollectorApplication.class, args);
	}

	private static Boolean checkJob(String jobId, String arg){
		if(arg.contains(jobId)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
