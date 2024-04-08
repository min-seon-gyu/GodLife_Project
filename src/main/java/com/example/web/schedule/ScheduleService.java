package com.example.web.schedule;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.member.Member;
import com.example.web.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long post(Long id, ScheduleAddDto scheduleAddDto) {
        Optional<Member> findMember = memberRepository.findById(id);
        Member member = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다."));
        Schedule schedule = Schedule.builder()
                .member(member)
                .content(scheduleAddDto.getContent())
                .localDate(scheduleAddDto.getDate())
                .build();
        return scheduleRepository.save(schedule).getId();
    }

    @Transactional
    public Long update(ScheduleUpdateDto scheduleUpdateDto){
        Optional<Schedule> findSchedule = scheduleRepository.findById(scheduleUpdateDto.getId());
        Schedule schedule = findSchedule.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 일정이 없습니다."));
        schedule.changeContent(scheduleUpdateDto.getContent());
        schedule.initStatus();
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
