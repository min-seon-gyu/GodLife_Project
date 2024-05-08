package com.example.web.memberCoupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    @Transactional
    public void deleteByMemberId(Long id){
        memberCouponRepository.deleteByMemberId(id);
    }
}
