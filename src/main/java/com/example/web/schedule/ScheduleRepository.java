package com.example.web.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByUsernameAndLocalDate(String username, LocalDate localDate);

    Long countByUsernameAndLocalDate(String username, LocalDate localDate);

    Long deleteByUsernameAndLocalDate(String username, LocalDate localDate);
}
