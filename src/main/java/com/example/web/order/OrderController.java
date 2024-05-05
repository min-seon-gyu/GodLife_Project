package com.example.web.order;

import com.example.web.common.NumberFormatConvert;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/cost")
    public ResponseEntity cost(@RequestBody OrderCalculatorDto orderCalculatorDto){
        if(orderCalculatorDto.getQuantity() < 1) throw new RestApiException(ErrorCode.BAD_REQUEST, "수량 데이터가 유효하지 않습니다.");
        return ResponseEntity.ok(NumberFormatConvert.convert(orderService.cost(orderCalculatorDto)));
    }

    @PostMapping("/order")
    public ResponseEntity order(@AuthenticationPrincipal MemberDetails memberDetails,
                                @RequestBody OrderAddDto orderAddDto){
        Long id = orderService.order(orderAddDto, memberDetails.getId());
        return ResponseEntity.ok(id);
    }
}
