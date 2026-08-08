package ca.admin.delivermore.collector.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuItemSpotlightPreference;

public interface RestaurantMenuItemSpotlightPreferenceRepository extends JpaRepository<RestaurantMenuItemSpotlightPreference, Long> {

    void deleteByRestaurantIdAndMappingKey(Long restaurantId, String mappingKey);

    Optional<RestaurantMenuItemSpotlightPreference> findByRestaurantIdAndMappingKey(Long restaurantId, String mappingKey);

    List<RestaurantMenuItemSpotlightPreference> findByRestaurantIdAndPreferredTrue(Long restaurantId);
}