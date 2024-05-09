package com.example.web.memberCoupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {
    private final MemberCouponRepository memberCouponRepository;

    public Long expectPoint(Long id, Long point){
        Optional<MemberCoupon> findMemberCoupon = memberCouponRepository.findByMemberCouponId(id);
        if(findMemberCoupon.isPresent()){
            return findMemberCoupon.get().expectPoint(point);
        }
        return point;
    }
    @Transactional
    public void use(Long id){
        Optional<MemberCoupon> findMemberCoupon = memberCouponRepository.findById(id);
        if(findMemberCoupon.isPresent()){
            findMemberCoupon.get().use(LocalDateTime.now());
        }
    }
    @Transactional
    public void deleteByMemberId(Long id){
        memberCouponRepository.deleteByMemberId(id);
    }
}
