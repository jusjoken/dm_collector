package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobStatusRepository extends JpaRepository<JobEntity, String> {

    @Query("select j from JobEntity j WHERE j.Id = :jobId")
    JobEntity findByJobId(String jobId);


}
