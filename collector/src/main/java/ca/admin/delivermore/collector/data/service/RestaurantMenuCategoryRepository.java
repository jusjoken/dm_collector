package ca.admin.delivermore.collector.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuCategory;

public interface RestaurantMenuCategoryRepository extends JpaRepository<RestaurantMenuCategory, Long> {

    long countByImageAssetId(Long imageAssetId);

    void deleteByMenuVersionId(Long menuVersionId);
}