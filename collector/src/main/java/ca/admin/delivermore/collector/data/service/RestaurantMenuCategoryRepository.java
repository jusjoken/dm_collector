package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuCategory;

public interface RestaurantMenuCategoryRepository extends JpaRepository<RestaurantMenuCategory, Long> {

    long countByImageAssetId(Long imageAssetId);

    List<RestaurantMenuCategory> findByMenuVersionId(Long menuVersionId);

    void deleteByMenuVersionId(Long menuVersionId);
}