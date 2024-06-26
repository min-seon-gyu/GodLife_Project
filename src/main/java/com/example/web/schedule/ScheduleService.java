package com.example.web.schedule;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.web.es.ScheduleDocument;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.member.Member;
import com.example.web.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final ElasticsearchClient elasticsearchClient;

    @Transactional
    public Long post(Long id, ScheduleAddDto scheduleAddDto) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 존재하지 않습니다."));
        Schedule schedule = Schedule.builder()
                .member(member)
                .content(scheduleAddDto.getContent())
                .localDate(scheduleAddDto.getDate())
                .build();
        return scheduleRepository.save(schedule).getId();
    }

    @Transactional
    public Long update(ScheduleUpdateDto scheduleUpdateDto){
        Schedule schedule = scheduleRepository.findById(scheduleUpdateDto.getId()).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 일정이 존재하지 않습니다."));
        schedule.changeContent(scheduleUpdateDto.getContent());
        schedule.initStatus();
        return schedule.getId();
    }

    @Transactional
    public void delete(Long id) throws IOException {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 일정이 존재하지 않습니다."));
        scheduleRepository.delete(schedule);
        SearchResponse<ScheduleDocument> search = elasticsearchClient.search(s -> s
                .index("schedule")
                .query(QueryBuilders.match().field("schedule_id").query(id).build()._toQuery()), ScheduleDocument.class);
        if(search.hits().total().value() > 0){
            elasticsearchClient.delete(d -> d.index("schedule").id(String.valueOf(id)));
        }
    }

    @Transactional
    public void deleteByMemberId(Long id) throws IOException {
        scheduleRepository.deleteByMemberId(id);
        SearchResponse<ScheduleDocument> search = elasticsearchClient.search(s -> s
                .index("schedule")
                .query(QueryBuilders.match().field("member_id").query(id).build()._toQuery()), ScheduleDocument.class);
        if(search.hits().total().value() > 0){
            elasticsearchClient.deleteByQuery(d -> d.index("schedule").query(QueryBuilders.match().field("member_id").query(id).build()._toQuery()));
        }
    }

    @Transactional
    public void success(Long id) {
        Schedule schedule = scheduleRepository.findByScheduleId(id).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 일정이 존재하지 않습니다."));
        schedule.success();
    }
}
