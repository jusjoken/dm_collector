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
        if (!columnExists("restaurant_menu_item", "spotlight_preferred")) {
            try {
                jdbcTemplate.execute("ALTER TABLE restaurant_menu_item ADD COLUMN spotlight_preferred BIT NOT NULL DEFAULT b'0'");
                log.info("ensureRestaurantMenuItemSpotlightPreferenceStorage: added restaurant_menu_item.spotlight_preferred");
            } catch (RuntimeException ex) {
                log.warn("ensureRestaurantMenuItemSpotlightPreferenceStorage: unable to add restaurant_menu_item.spotlight_preferred: {}", ex.getMessage(), ex);
            }
        } else {
            log.debug("ensureRestaurantMenuItemSpotlightPreferenceStorage: restaurant_menu_item.spotlight_preferred already present");
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

    private boolean columnExists(String tableName, String columnName) {
        try {
            Integer count = jdbcTemplate.queryForObject(
                    """
                    SELECT COUNT(*)
                    FROM information_schema.columns
                    WHERE table_schema = DATABASE()
                      AND table_name = ?
                      AND column_name = ?
                    """,
                    Integer.class,
                    tableName,
                    columnName);
            return count != null && count > 0;
        } catch (RuntimeException ex) {
            log.debug("ensureRestaurantMenuItemSpotlightPreferenceStorage: unable to inspect column existence for {}.{}: {}", tableName, columnName, ex.getMessage());
            return false;
        }
    }
}