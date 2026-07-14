package ca.admin.delivermore.collector.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuOption;

public interface RestaurantMenuOptionRepository extends JpaRepository<RestaurantMenuOption, Long> {

    void deleteByMenuVersionId(Long menuVersionId);
}