package com.example.web.schedule;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s join fetch s.member m where s.localDate = :localDate and m.id = :id")
    List<Schedule> findByMemberIdAndLocalDate(@Param("id") Long id, @Param("localDate") LocalDate localDate);

    @Query("select COUNT(*) from Schedule s join s.member m where s.localDate = :localDate and m.id = :id")
    Long countByMemberIdAndLocalDate(@Param("id") Long id, @Param("localDate") LocalDate localDate);

    @Query("select s from Schedule s join fetch s.member m where m.id = :id and s.content like %:content% order by s.localDate")
    Slice<Schedule> findByMemberIdAndContent(Pageable pageable, @Param("id") Long id, @Param("content") String content);
}
