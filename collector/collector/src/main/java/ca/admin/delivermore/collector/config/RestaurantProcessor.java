package ca.admin.delivermore.collector.config;

import ca.admin.delivermore.collector.data.entity.Restaurant;
import org.springframework.batch.item.ItemProcessor;

public class RestaurantProcessor implements ItemProcessor<Restaurant,Restaurant> {

    @Override
    public Restaurant process(Restaurant restaurant) throws Exception {
        return restaurant;
    }
}
