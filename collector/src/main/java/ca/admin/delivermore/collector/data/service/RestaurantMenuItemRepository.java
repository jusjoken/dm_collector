package ca.admin.delivermore.collector.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuItem;

public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {

    long countByImageAssetId(Long imageAssetId);

    void deleteByMenuVersionId(Long menuVersionId);
}