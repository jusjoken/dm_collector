package ca.admin.delivermore.collector.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import ca.admin.delivermore.collector.data.Config;
import ca.admin.delivermore.collector.data.entity.Restaurant;
import ca.admin.delivermore.collector.data.menu.RestaurantMenuImportPayload;
import ca.admin.delivermore.collector.data.service.RestClientService;
import ca.admin.delivermore.collector.data.service.RestaurantMenuImportService;
import ca.admin.delivermore.collector.data.service.RestaurantRepository;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class BatchConfigRestaurantMenu {

    private static final Logger log = LoggerFactory.getLogger(BatchConfigRestaurantMenu.class);

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;
    private RestaurantRepository restaurantRepository;
    private RestClientService restClientService;
    private RestaurantMenuImportService restaurantMenuImportService;

    @Bean
    public ListItemReader<RestaurantMenuImportPayload> restaurantMenuItemReader(){
        if(Config.getInstance().getRunRestaurantMenuJob()){
            List<RestaurantMenuImportPayload> menusToImport = new ArrayList<>();
            List<Restaurant> restaurants = restaurantRepository.getEffectiveRestaurantsWithMenuKey(LocalDate.now());
            log.info("restaurantMenuItemReader: checking {} effective restaurants for menu data", restaurants.size());
            for (Restaurant restaurant : restaurants) {
                if(restaurantMenuImportService.isPullLocked(restaurant.getRestaurantId())){
                    log.info("restaurantMenuItemReader: skipping restaurant {} ({}) because a draft version exists", restaurant.getName(), restaurant.getRestaurantId());
                    continue;
                }
                String menuJson = restClientService.getGlobalMenuJson(restaurant);
                if(menuJson != null){
                    menusToImport.add(new RestaurantMenuImportPayload(restaurant.getRestaurantId(), restaurant.getName(), menuJson));
                }
            }
            log.info("restaurantMenuItemReader: queued {} menu payloads for import", menusToImport.size());
            return new ListItemReader<>(menusToImport);
        }else{
            return null;
        }
    }

    @Bean
    public ItemWriter<RestaurantMenuImportPayload> restaurantMenuItemWriter(){
        if(Config.getInstance().getRunRestaurantMenuJob()){
            return chunk -> {
                for (RestaurantMenuImportPayload payload : chunk.getItems()) {
                    restaurantMenuImportService.importMenu(payload);
                }
            };
        }else{
            return null;
        }
    }

    @Bean
    public Step restaurantMenuStep(){
        if(Config.getInstance().getRunRestaurantMenuJob()){
            return new StepBuilder("restaurantMenuStep", jobRepository)
                    .<RestaurantMenuImportPayload, RestaurantMenuImportPayload>chunk(5)
                    .transactionManager(transactionManager)
                    .reader(restaurantMenuItemReader())
                    .writer(restaurantMenuItemWriter())
                    .build();
        }else{
            return null;
        }
    }

    @Bean
    public Job restaurantMenuJob(){
        if(Config.getInstance().getRunRestaurantMenuJob()){
            return new JobBuilder("restaurantMenuJob", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .flow(restaurantMenuStep())
                    .end()
                    .build();
        }else{
            return null;
        }
    }
}