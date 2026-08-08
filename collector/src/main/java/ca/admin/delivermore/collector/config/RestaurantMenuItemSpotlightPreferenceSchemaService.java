package ca.admin.delivermore.collector.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class RestaurantMenuItemSpotlightPreferenceSchemaService {

    private static final Logger log = LoggerFactory.getLogger(RestaurantMenuItemSpotlightPreferenceSchemaService.class);

    private final JdbcTemplate jdbcTemplate;

    public RestaurantMenuItemSpotlightPreferenceSchemaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ensureRestaurantMenuItemSpotlightPreferenceStorage() {
        try {
            jdbcTemplate.execute("ALTER TABLE restaurant_menu_item ADD COLUMN spotlight_preferred BIT NOT NULL DEFAULT b'0'");
            log.info("ensureRestaurantMenuItemSpotlightPreferenceStorage: added restaurant_menu_item.spotlight_preferred");
        } catch (RuntimeException ex) {
            log.debug("ensureRestaurantMenuItemSpotlightPreferenceStorage: restaurant_menu_item.spotlight_preferred already present or could not be added: {}", ex.getMessage());
        }

        try {
            jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS restaurant_menu_item_spotlight_preference (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    restaurant_id BIGINT NOT NULL,
                    mapping_key VARCHAR(255) NOT NULL,
                    source_category_id BIGINT NULL,
                    source_item_id BIGINT NULL,
                    preferred BIT NOT NULL DEFAULT b'1',
                    updated_at DATETIME(6) NOT NULL,
                    PRIMARY KEY (id),
                    UNIQUE KEY uk_restaurant_menu_item_spotlight_preference_restaurant_key (restaurant_id, mapping_key),
                    KEY idx_restaurant_menu_item_spotlight_preference_restaurant (restaurant_id)
                )
                """);
            log.info("ensureRestaurantMenuItemSpotlightPreferenceStorage: ensured restaurant_menu_item_spotlight_preference exists");
        } catch (RuntimeException ex) {
            log.warn("ensureRestaurantMenuItemSpotlightPreferenceStorage: unable to create restaurant_menu_item_spotlight_preference: {}", ex.getMessage(), ex);
        }
    }
}