package ca.admin.delivermore.collector;

import ca.admin.delivermore.collector.data.Config;
import ca.admin.delivermore.collector.data.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"ca.admin.delivermore"})
@EnableJpaRepositories("ca.admin.delivermore")
@EntityScan("ca.admin.delivermore")
public class CollectorApplication {

	public static void main(String[] args) {
		//System.exit(SpringApplication.exit(SpringApplication.run(CollectorApplication.class, args)));
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		System.out.println("CollectorApplication: startup args:");
		for ( String arg : args ) {
			System.out.println("CollectorApplication: startup arg:" + arg);
			if(arg.contains("driverJob")) Config.getInstance().setRunDriverJob(true);
			if(arg.contains("orderDetailJob")) Config.getInstance().setRunOrderJob(true);
			if(arg.contains("globalOrderJob")) Config.getInstance().setRunGlobalOrderJob(true);
			if(arg.contains("taskJob")) Config.getInstance().setRunTaskJob(true);
			if(arg.contains("restaurantJob")) Config.getInstance().setRunRestaurantJob(true);
		}
		SpringApplication.run(CollectorApplication.class, args);
	}

}
