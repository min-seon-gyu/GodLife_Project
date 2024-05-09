package com.example.web.order;

import com.example.web.common.NumberFormatConvert;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.memberCoupon.MemberCouponService;
import com.example.web.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderPaymentService orderPaymentService;
    private final MemberCouponService memberCouponService;

    @GetMapping("/order/cost")
    public ResponseEntity cost(@RequestParam(value = "price") Long price,
                               @RequestParam(value = "quantity") Long quantity,
                               @RequestParam(value = "memberCouponId") Long userCouponId){
        if(quantity < 1) throw new RestApiException(ErrorCode.BAD_REQUEST, "수량 데이터가 유효하지 않습니다.");
        Long point = price * quantity;
        if(userCouponId != 0){
            point = memberCouponService.expectPoint(userCouponId, point);
        }
        OrderCostResponse orderCostResponse = new OrderCostResponse(NumberFormatConvert.convert(point));
        return ResponseEntity.ok(orderCostResponse);
    }

    @PostMapping("/order")
    public ResponseEntity add(@AuthenticationPrincipal MemberDetails memberDetails,
                                @RequestBody OrderAddDto orderAddDto){
        return ResponseEntity.ok(orderPaymentService.payment(orderAddDto, memberDetails.getId()));
    }
}
