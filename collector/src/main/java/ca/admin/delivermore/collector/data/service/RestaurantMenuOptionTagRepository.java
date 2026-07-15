package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuOptionTag;

public interface RestaurantMenuOptionTagRepository extends JpaRepository<RestaurantMenuOptionTag, Long> {

    List<RestaurantMenuOptionTag> findByOptionIdOrderByDisplayOrderAscIdAsc(Long optionId);

    void deleteByMenuVersionId(Long menuVersionId);

    void deleteByOptionId(Long optionId);
}
