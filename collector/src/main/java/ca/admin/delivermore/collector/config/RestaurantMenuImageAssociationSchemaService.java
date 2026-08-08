package ca.admin.delivermore.collector.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class RestaurantMenuImageAssociationSchemaService {

    private static final Logger log = LoggerFactory.getLogger(RestaurantMenuImageAssociationSchemaService.class);

    private final JdbcTemplate jdbcTemplate;

    public RestaurantMenuImageAssociationSchemaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ensureRestaurantMenuImageAssociationTable() {
        try {
            jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS restaurant_menu_image_association (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    restaurant_id BIGINT NOT NULL,
                    scope_type VARCHAR(16) NOT NULL,
                    mapping_key VARCHAR(255) NOT NULL,
                    source_menu_id BIGINT NULL,
                    source_category_id BIGINT NULL,
                    source_item_id BIGINT NULL,
                    image_asset_id BIGINT NOT NULL,
                    updated_at DATETIME(6) NOT NULL,
                    PRIMARY KEY (id),
                    UNIQUE KEY uk_restaurant_menu_image_association_restaurant_key (restaurant_id, mapping_key),
                    KEY idx_restaurant_menu_image_association_asset (image_asset_id),
                    KEY idx_restaurant_menu_image_association_restaurant (restaurant_id)
                )
                """);
            log.info("ensureRestaurantMenuImageAssociationTable: ensured restaurant_menu_image_association exists");
        } catch (RuntimeException ex) {
            log.warn("ensureRestaurantMenuImageAssociationTable: unable to create restaurant_menu_image_association: {}", ex.getMessage(), ex);
        }
    }
}