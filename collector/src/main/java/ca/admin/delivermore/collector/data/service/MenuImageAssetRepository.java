package ca.admin.delivermore.collector.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.admin.delivermore.collector.data.entity.MenuImageAsset;

public interface MenuImageAssetRepository extends JpaRepository<MenuImageAsset, Long> {

    List<MenuImageAsset> findAllByOrderByUpdatedAtDescCreatedAtDescIdDesc();

    List<MenuImageAsset> findByShapeTypeOrderByUpdatedAtDescCreatedAtDescIdDesc(String shapeType);
}