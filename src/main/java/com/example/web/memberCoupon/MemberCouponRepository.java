package com.example.web.memberCoupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    void deleteByMemberId(Long memberId);
    @Query("select mc from MemberCoupon mc join fetch mc.coupon c where mc.id = :id")
    Optional<MemberCoupon> findByMemberCouponId(@Param("id") Long memberCouponId);
    @Query("select mc from MemberCoupon mc join fetch mc.coupon c join fetch mc.member m where m.id = :id and mc.status = 'unused'")
    Optional<List<MemberCoupon>> findByMemberId(@Param("id") Long memberId);
}
