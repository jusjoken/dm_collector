package ca.admin.delivermore.collector.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuOptionGroup;

public interface RestaurantMenuOptionGroupRepository extends JpaRepository<RestaurantMenuOptionGroup, Long> {

    void deleteByMenuVersionId(Long menuVersionId);
}