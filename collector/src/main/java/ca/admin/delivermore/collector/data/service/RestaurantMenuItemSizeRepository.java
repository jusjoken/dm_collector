package ca.admin.delivermore.collector.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuItemSize;

public interface RestaurantMenuItemSizeRepository extends JpaRepository<RestaurantMenuItemSize, Long> {

    void deleteByMenuVersionId(Long menuVersionId);
}