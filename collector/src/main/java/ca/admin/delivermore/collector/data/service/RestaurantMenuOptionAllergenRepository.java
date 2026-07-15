package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuOptionAllergen;

public interface RestaurantMenuOptionAllergenRepository extends JpaRepository<RestaurantMenuOptionAllergen, Long> {

    List<RestaurantMenuOptionAllergen> findByOptionIdOrderByDisplayOrderAscIdAsc(Long optionId);

    void deleteByMenuVersionId(Long menuVersionId);

    void deleteByOptionId(Long optionId);
}
