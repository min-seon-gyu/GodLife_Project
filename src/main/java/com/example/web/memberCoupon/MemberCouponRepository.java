package com.example.web.memberCoupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    @Query("select mc from MemberCoupon mc join fetch mc.coupon c join fetch mc.member m where m.id = :id")
    Optional<List<MemberCoupon>> findByMemberId(@Param("id") Long memberId);
}
