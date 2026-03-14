package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.tookan.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamsRepository extends JpaRepository<Team, Long> {
    @Query("select t from Team t order by t.teamName")
    List<Team> findByOrderByTeamNameAsc();

    @Query("select t from Team t where t.active = true or t.active is null order by t.teamName")
    List<Team> findByActiveTrueOrderByTeamNameAsc();

    @Query("select t from Team t where t.teamId = :teamId")
    Team findByTeamId(@Param("teamId") Long teamId);

    @Query("select t from Team t where t.teamName = :teamName")
    Team findByTeamName(@Param("teamName") String teamName);



}
