package com.example.web.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    void deleteByMemberId(Long memberId);
    @Query("select s from Schedule s join fetch s.member m where s.id = :id")
    Optional<Schedule> findByScheduleId(@Param("id") Long id);
    @Query("select s from Schedule s join s.member m where s.localDate = :localDate and m.id = :id")
    List<Schedule> findByMemberIdAndLocalDate(@Param("id") Long id, @Param("localDate") LocalDate localDate);
    @Query("select COUNT(*) from Schedule s join s.member m where s.localDate = :localDate and m.id = :id")
    Long countByMemberIdAndLocalDate(@Param("id") Long id, @Param("localDate") LocalDate localDate);
}
