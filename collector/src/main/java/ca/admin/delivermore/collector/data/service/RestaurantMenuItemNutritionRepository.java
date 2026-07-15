package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuItemNutrition;

public interface RestaurantMenuItemNutritionRepository extends JpaRepository<RestaurantMenuItemNutrition, Long> {

    List<RestaurantMenuItemNutrition> findByItemIdOrderByDisplayOrderAscIdAsc(Long itemId);

    void deleteByMenuVersionId(Long menuVersionId);

    void deleteByItemId(Long itemId);
}
