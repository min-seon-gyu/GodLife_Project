package com.example.web.schedule;

import com.example.web.redis.PostService;
import com.example.web.security.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final PostService postService;

    @PostMapping("/schedule")
    public ResponseEntity add(@AuthenticationPrincipal MemberDetails memberDetails,
                               @Valid @RequestBody ScheduleAddDto scheduleAddDto){
        postService.decreaseWriteCount(scheduleAddDto.getDate()+ "_" + memberDetails.getId());
        scheduleService.post(memberDetails.getId(), scheduleAddDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/schedule/changeStatus")
    public ResponseEntity change(@RequestBody String id){
        JSONObject jsonObject = new JSONObject(id);
        scheduleService.change(Long.parseLong(jsonObject.getString("id")));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/schedule")
    public ResponseEntity update(@Valid @RequestBody ScheduleUpdateDto scheduleUpdateDto){
        scheduleService.update(scheduleUpdateDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/schedule")
    public ResponseEntity delete(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @RequestBody ScheduleDeleteDto scheduleDeleteDto){
        postService.incrementWriteCount(scheduleDeleteDto.getDate()+ "_" + memberDetails.getId());
        scheduleService.delete(scheduleDeleteDto.getId());
        return new ResponseEntity(HttpStatus.OK);
    }
}
