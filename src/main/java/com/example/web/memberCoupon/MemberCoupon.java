package com.example.web.memberCoupon;

import com.example.web.common.JpaBaseEntity;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicUpdate
public class MemberCoupon extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_COUPON_ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    private Long couponId;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
    private LocalDateTime usedAt;
    @Enumerated(EnumType.STRING)
    private MemberCouponStatus status;

    @Builder
    public MemberCoupon(Member member, Long couponId, LocalDateTime issuedAt, LocalDateTime expiredAt) {
        this.member = member;
        this.couponId = couponId;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.status = MemberCouponStatus.UNUSED;
    }

    public void use(LocalDateTime currentTime){
        validate(currentTime);
        this.status = MemberCouponStatus.USED;
        usedAt = currentTime;
    }

    public void setMember(Member member){
        this.member = member;
    }

    private void validate(LocalDateTime currentTime) {
        if (status.equals(MemberCouponStatus.USED)) {
            throw new RestApiException(ErrorCode.BAD_REQUEST, "이미 사용한 쿠폰입니다.");
        }
        if (expiredAt.isBefore(currentTime)) {
            throw new RestApiException(ErrorCode.BAD_REQUEST, "유효 기간이 종료된 쿠폰입니다.");
        }
    }
}
