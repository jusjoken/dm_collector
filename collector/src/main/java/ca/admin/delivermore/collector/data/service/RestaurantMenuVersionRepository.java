package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuVersion;

public interface RestaurantMenuVersionRepository extends JpaRepository<RestaurantMenuVersion, Long> {

    RestaurantMenuVersion findTopByRestaurantIdOrderByVersionNumberDesc(Long restaurantId);

    List<RestaurantMenuVersion> findByRestaurantIdOrderByVersionNumberDesc(Long restaurantId);

    RestaurantMenuVersion findByRestaurantIdAndActiveTrue(Long restaurantId);
}