package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuOptionNutrition;

public interface RestaurantMenuOptionNutritionRepository extends JpaRepository<RestaurantMenuOptionNutrition, Long> {

    List<RestaurantMenuOptionNutrition> findByOptionIdOrderByDisplayOrderAscIdAsc(Long optionId);

    void deleteByMenuVersionId(Long menuVersionId);

    void deleteByOptionId(Long optionId);
}
