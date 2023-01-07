package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.entity.SettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SettingRepository extends JpaRepository<SettingEntity, UUID> {

    @Override
    List<SettingEntity> findAll();

    @Query("select s from SettingEntity s where s.section = :section and s.name = :name")
    SettingEntity findBySectionAndName(@Param("section") String section, @Param("name") String name);


}
