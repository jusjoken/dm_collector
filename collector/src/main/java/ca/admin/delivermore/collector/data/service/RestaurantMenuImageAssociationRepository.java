package ca.admin.delivermore.collector.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.RestaurantMenuImageAssociation;

public interface RestaurantMenuImageAssociationRepository extends JpaRepository<RestaurantMenuImageAssociation, Long> {

    long countByImageAssetId(Long imageAssetId);

    void deleteByRestaurantIdAndMappingKey(Long restaurantId, String mappingKey);

    Optional<RestaurantMenuImageAssociation> findByRestaurantIdAndMappingKey(Long restaurantId, String mappingKey);

    List<RestaurantMenuImageAssociation> findByRestaurantId(Long restaurantId);
}