package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuItem;

public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {

    long countByImageAssetId(Long imageAssetId);

    List<RestaurantMenuItem> findByMenuVersionId(Long menuVersionId);

    void deleteByMenuVersionId(Long menuVersionId);
}