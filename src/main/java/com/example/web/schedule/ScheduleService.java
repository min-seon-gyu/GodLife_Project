package com.example.web.schedule;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Long post(String username, ScheduleAddDto scheduleAddDto) {
        Schedule schedule = Schedule.builder()
                .username(username)
                .content(scheduleAddDto.getContent())
                .localDate(LocalDate.parse(scheduleAddDto.getDate()))
                .build();
        return scheduleRepository.save(schedule).getId();
    }

    @Transactional
    public Long update(ScheduleUpdateDto scheduleUpdateDto){
        Optional<Schedule> findSchedule = scheduleRepository.findById(scheduleUpdateDto.getId());
        Schedule schedule = findSchedule.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 일정이 없습니다."));
        schedule.changeContent(scheduleUpdateDto.getContent());
        schedule.setStatus(false);
        return schedule.getId();
    }

    @Transactional
    public void delete(Long id){
        Optional<Schedule> findSchedule = scheduleRepository.findById(id);
        scheduleRepository.delete(findSchedule.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 일정이 없습니다.")));
    }

    @Transactional
    public void change(Long id) {
        Optional<Schedule> findSchedule = scheduleRepository.findById(id);
        Schedule schedule = findSchedule.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 일정이 없습니다."));
        schedule.changeStatus();
    }
}
