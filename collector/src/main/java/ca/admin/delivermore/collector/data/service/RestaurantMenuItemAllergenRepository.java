package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuItemAllergen;

public interface RestaurantMenuItemAllergenRepository extends JpaRepository<RestaurantMenuItemAllergen, Long> {

    List<RestaurantMenuItemAllergen> findByItemIdOrderByDisplayOrderAscIdAsc(Long itemId);

    void deleteByMenuVersionId(Long menuVersionId);

    void deleteByItemId(Long itemId);
}
