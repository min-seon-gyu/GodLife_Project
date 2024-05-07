package com.example.web.order;

import com.example.web.common.NumberFormatConvert;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.member.Member;
import com.example.web.member.MemberRepository;
import com.example.web.memberCoupon.MemberCoupon;
import com.example.web.memberCoupon.MemberCouponRepository;
import com.example.web.product.Product;
import com.example.web.product.ProductRepository;
import com.example.web.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderPaymentService orderPaymentService;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final MemberCouponRepository memberCouponRepository;

    @GetMapping("/order/cost")
    public ResponseEntity cost(@RequestParam(value = "price") Long price,
                               @RequestParam(value = "quantity") Long quantity,
                               @RequestParam(value = "memberCouponId") Long userCouponId){
        if(quantity < 1) throw new RestApiException(ErrorCode.BAD_REQUEST, "수량 데이터가 유효하지 않습니다.");
        Long cost = price * quantity;
        if(userCouponId != 0){
            MemberCoupon memberCoupon = memberCouponRepository.findById(userCouponId).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 쿠폰이 존재하지 않습니다."));;
            cost = memberCoupon.apply(cost);
        }
        OrderCostResponse orderCostResponse = new OrderCostResponse(NumberFormatConvert.convert(cost));
        return ResponseEntity.ok(orderCostResponse);
    }

    @PostMapping("/order")
    public ResponseEntity add(@AuthenticationPrincipal MemberDetails memberDetails,
                                @RequestBody OrderAddDto orderAddDto){
        Member member = memberRepository.findById(memberDetails.getId()).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 유저가 존재하지 않습니다."));
        Product product = productRepository.findById(orderAddDto.getProductId()).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 상품이 존재하지 않습니다."));
        if(orderAddDto.getMemberCouponId() == 0){
            return ResponseEntity.ok(orderPaymentService.payment(orderAddDto, member, product));
        }else{
            MemberCoupon memberCoupon = memberCouponRepository.findById(orderAddDto.getMemberCouponId()).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 쿠폰이 존재하지 않습니다."));
            return ResponseEntity.ok(orderPaymentService.payment(orderAddDto, member, product, memberCoupon));
        }
    }
}
