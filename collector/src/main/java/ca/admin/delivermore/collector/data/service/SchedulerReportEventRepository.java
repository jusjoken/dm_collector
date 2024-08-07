package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.entity.SchedulerReportEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SchedulerReportEventRepository  extends JpaRepository<SchedulerReportEvent, UUID> {
    @Query("select s from SchedulerReportEvent s where s.published = ?1 and s.start between ?2 and ?3 order by s.teamId, s.type, s.start, s.end")
    List<SchedulerReportEvent> findByPublishedAndStartBetween(Boolean published, LocalDateTime startStart, LocalDateTime startEnd);

}
