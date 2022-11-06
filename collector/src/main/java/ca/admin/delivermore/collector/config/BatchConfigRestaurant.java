package ca.admin.delivermore.collector.config;

import ca.admin.delivermore.collector.data.Config;
import ca.admin.delivermore.collector.data.entity.Restaurant;
import ca.admin.delivermore.collector.data.service.RestaurantRepository;
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

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfigRestaurant {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private RestaurantRepository restaurantRepository;

    @Bean
    public ListItemReader<Restaurant> restaurantItemReader(){
        if(Config.getInstance().getRunRestaurantJob()){
            System.out.println("BatchConfigRestaurant: restaurantItemReader");
            return new ListItemReader<>(getRestaurants());
        }else{
            return null;
        }
    }

    @Bean
    public RestaurantProcessor restaurantProcessor(){
        if(Config.getInstance().getRunRestaurantJob()){
            return new RestaurantProcessor();
        }else{
            return null;
        }
    }

    @Bean
    public RepositoryItemWriter<Restaurant> restaurantItemWriter(){
        if(Config.getInstance().getRunRestaurantJob()){
            RepositoryItemWriter<Restaurant> writer = new RepositoryItemWriter<>();
            writer.setRepository(restaurantRepository);
            return writer;
        }else{
            return null;
        }
    }

    @Bean
    public Step restaurantsStep(){
        if(Config.getInstance().getRunRestaurantJob()){
            return stepBuilderFactory.get("restaurantsStep").<Restaurant,Restaurant>chunk(10)
                    .reader(restaurantItemReader())
                    .processor(restaurantProcessor())
                    .writer(restaurantItemWriter())
                    .build();
        }else{
            return null;
        }
    }

    @Bean
    public Job restaurantJob(){
        if(Config.getInstance().getRunRestaurantJob()){
            System.out.println("BatchConfigRestaurant: restaurantJob");
            return jobBuilderFactory.get("restaurantJob")
                    .incrementer(new RunIdIncrementer())
                    .flow(restaurantsStep())
                    .end().build();
        }else{
            return null;
        }
    }

    private List<Restaurant> getRestaurants(){
        List<Restaurant> restaurantList = new ArrayList<>();
        /*
        restaurantList.add(new Restaurant(402970L, "A&W", 0.1, 551118L, "QMvJ9Fg5esjqNXymZq"));
        restaurantList.add(new Restaurant(402971L, "Booster Juice", 0.1, "XkdvOh75RF01OeEqEp"));
        restaurantList.add(new Restaurant(402972L, "Boston Pizza", 0.1, "arZQmt7E7irYOXE37J"));
        restaurantList.add(new Restaurant(0L, "Custom", 0.0, ""));
        restaurantList.add(new Restaurant(405508L, "Dairy Queen", 0.0, ""));
        restaurantList.add(new Restaurant(402977L, "Dobre", 0.1, "3E5MZfQElSGD5Jgv30"));
        restaurantList.add(new Restaurant(402973L, "Edo Japan", 0.1, 551316L, "pmxGPCeqbidrRBVbYX"));
        restaurantList.add(new Restaurant(402974L, "Fritou Chicken", 0.1, 551318L, "0zxMpsWE0FQWGdv6Z0"));
        restaurantList.add(new Restaurant(402976L, "Husky House Restaurant Strathmore", 0.1, "nZq4ncRYdCmj3yNP8x"));
        restaurantList.add(new Restaurant(402979L, "Imperial Dragon", 0.1, 551315L, "BGgq3tkbqhPpPjlDX"));
        restaurantList.add(new Restaurant(402981L, "Little Caesars", 0.1, "rjwlYUdmqhj0YmEE0"));
        restaurantList.add(new Restaurant(402982L, "McDonald's",0.1, 3.5, "6q1lWHWgaUaJnxwO8"));
        restaurantList.add(new Restaurant(402983L, "Mike's Bar & Grill",0.15,2.5, 0.0, 551314L, "yxzVnhRVVc49RQoN6Y"));
        restaurantList.add(new Restaurant(402985L, "OPA!",0.1,3.0, "VV1vPUQbOFv5Z9adG"));
        restaurantList.add(new Restaurant(402986L, "Original Joe's Strathmore",0.1, "PJx56CwgyCDoEkXj8w"));
        restaurantList.add(new Restaurant(402987L, "Papa John's",0.15, 551465L, "gQ7dotQ0nfmdjNlv3B"));
        restaurantList.add(new Restaurant(402990L, "Pho Minh",0.1, 551317L, "70nDxin79UaqeBDNJB"));
        restaurantList.add(new Restaurant(402992L, "Pizza 249",0.1, 552128L, "yxzVEcRVBhz8YjJvND"));
        restaurantList.add(new Restaurant(402993L, "Quesada",0.1,4.0, "va1l5IG4bcwblwWQPB"));
        restaurantList.add(new Restaurant(402994L, "Saffron Bistro",0.0, "ogBwziObrUQQ96Rad4"));
        restaurantList.add(new Restaurant(402995L, "Smiley's",0.0, "8MdP5tW8bCB5YDq9x5"));
        restaurantList.add(new Restaurant(402996L, "Taco Time",0.1,4.0, "M4MOpT4zgI4RqM97w"));
        restaurantList.add(new Restaurant(402057L, "Demo",0.1,550957L, "0zOwwuW6mCQJeWqORW"));
         */
        return restaurantList;
    }


}
