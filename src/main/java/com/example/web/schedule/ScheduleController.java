package com.example.web.schedule;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.web.es.ScheduleDocument;
import com.example.web.es.ScheduleDocumentPaging;
import com.example.web.es.ScheduleDocumentResponse;
import com.example.web.redis.PostService;
import com.example.web.security.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final int pageSize = 5;
    private final ScheduleService scheduleService;
    private final PostService postService;
    private final ElasticsearchClient elasticsearchClient;

    @PostMapping("/schedule")
    public ResponseEntity<Long> add(@AuthenticationPrincipal MemberDetails memberDetails,
                               @Valid @RequestBody ScheduleAddDto scheduleAddDto){
        postService.decreaseWriteCount(scheduleAddDto.getDate()+ "_" + memberDetails.getId());
        Long id = scheduleService.post(memberDetails.getId(), scheduleAddDto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/schedule/success")
    public ResponseEntity success(@RequestBody String id){
        JSONObject jsonObject = new JSONObject(id);
        scheduleService.success(Long.parseLong(jsonObject.getString("id")));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/schedule")
    public ResponseEntity<Long> update(@Valid @RequestBody ScheduleUpdateDto scheduleUpdateDto){
        Long id = scheduleService.update(scheduleUpdateDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/schedule")
    public ResponseEntity delete(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @RequestBody ScheduleDeleteDto scheduleDeleteDto) throws IOException {
        postService.incrementWriteCount(scheduleDeleteDto.getDate()+ "_" + memberDetails.getId());
        scheduleService.delete(scheduleDeleteDto.getId());
        Query matchQuery = QueryBuilders.match().field("schedule_id").query(scheduleDeleteDto.getId()).build()._toQuery();
        SearchResponse<ScheduleDocument> search = elasticsearchClient.search(s -> s
                        .index("schedule")
                        .query(matchQuery), ScheduleDocument.class);
        if(search.hits().total().value() > 0){
            elasticsearchClient.delete(d -> d.index("schedule").id(String.valueOf(scheduleDeleteDto.getId())));
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/search_schedule/{content}/{pageIndex}")
    public ScheduleDocumentPaging find(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @PathVariable(value = "content", required = false) String content,
                                       @PathVariable(value = "pageIndex", required = false) int pageIndex) throws IOException {
        Query termQuery = QueryBuilders.term().field("content").value(FieldValue.of(content)).build()._toQuery();
        Query matchQuery = QueryBuilders.match().field("member_id").query(2).build()._toQuery();
        SearchResponse<ScheduleDocument> search = elasticsearchClient.search(s -> s
                        .index("schedule")
                        .query(q -> q
                                .bool(t -> t
                                        .must(termQuery, matchQuery)))
                        .from(pageIndex).size(pageSize)
                        .sort(so -> so.field(f -> f.field("local_date").order(SortOrder.Asc)))
                , ScheduleDocument.class);
        List<ScheduleDocumentResponse> result = new ArrayList<>();
        for (Hit<ScheduleDocument> hit: search.hits().hits()) {
            result.add(new ScheduleDocumentResponse(hit.source()));
        }

        return ScheduleDocumentPaging.builder().
                lst(result).
                nextIndex(pageIndex + pageSize).
                hasNext(search.hits().total().value() > pageIndex + pageSize - 1).
                build();
    }
}
