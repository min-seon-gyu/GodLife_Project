package com.example.web.memberCoupon;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.product.ProductUpdateDto;
import com.example.web.security.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberCouponController {

    private final MemberCouponRepository memberCouponRepository;

    @GetMapping("/memberCoupon")
    public ResponseEntity<List<MemberCouponResponse>> find(@AuthenticationPrincipal MemberDetails memberDetails){
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberDetails.getId()).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "보유하는 쿠폰이 존재하지 않습니다."));
        return ResponseEntity.ok(memberCoupons.stream().map(c -> new MemberCouponResponse(c)).toList());
    }
}
