package com.example.web.schedule;

import com.example.web.es.ScheduleDocument;
import com.example.web.es.ScheduleDocumentPaging;
import com.example.web.es.ScheduleDocumentRepository;
import com.example.web.es.ScheduleDocumentResponse;
import com.example.web.redis.PostService;
import com.example.web.security.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final int pageSize = 5;
    private final String sortProps = "localDate";
    private final ScheduleService scheduleService;
    private final PostService postService;
    private final ScheduleDocumentRepository scheduleDocumentRepository;

    @PostMapping("/schedule")
    public ResponseEntity<Long> add(@AuthenticationPrincipal MemberDetails memberDetails,
                               @Valid @RequestBody ScheduleAddDto scheduleAddDto){
        postService.decreaseWriteCount(scheduleAddDto.getDate()+ "_" + memberDetails.getId());
        Long id = scheduleService.post(memberDetails.getId(), scheduleAddDto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/schedule/changeStatus")
    public ResponseEntity change(@RequestBody String id){
        JSONObject jsonObject = new JSONObject(id);
        scheduleService.change(Long.parseLong(jsonObject.getString("id")));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/schedule")
    public ResponseEntity<Long> update(@Valid @RequestBody ScheduleUpdateDto scheduleUpdateDto){
        Long id = scheduleService.update(scheduleUpdateDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/schedule")
    public ResponseEntity delete(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @RequestBody ScheduleDeleteDto scheduleDeleteDto){
        postService.incrementWriteCount(scheduleDeleteDto.getDate()+ "_" + memberDetails.getId());
        scheduleService.delete(scheduleDeleteDto.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/search_schedule/{content}/{pageIndex}")
    public ScheduleDocumentPaging find(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @PathVariable(value = "content", required = false) String content,
                                       @PathVariable(value = "pageIndex", required = false) int pageIndex){

        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by(sortProps));
        Slice<ScheduleDocument> findScheduleDocument = scheduleDocumentRepository.findByContentAndMemberId(pageRequest, content, memberDetails.getId());
        List<ScheduleDocumentResponse> result = findScheduleDocument.getContent().stream().map((s) -> new ScheduleDocumentResponse(s)).toList();

        return ScheduleDocumentPaging.builder().
                lst(result).
                nextIndex(findScheduleDocument.getNumber() + 1).
                hasNext(findScheduleDocument.hasNext()).
                build();
    }
}
