package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuItemTag;

public interface RestaurantMenuItemTagRepository extends JpaRepository<RestaurantMenuItemTag, Long> {

    List<RestaurantMenuItemTag> findByItemIdOrderByDisplayOrderAscIdAsc(Long itemId);

    void deleteByMenuVersionId(Long menuVersionId);

    void deleteByItemId(Long itemId);
}
