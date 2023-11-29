package com.example.web.memberReservation;

import com.example.web.member.MemberEntity;
import com.example.web.reservation.ReservationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_RESERVATION_ID")
    private long id;

    @ManyToOne()
    @JoinColumn(name = "MEMBER_ID")
    private MemberEntity memberEntity;

    @ManyToOne()
    @JoinColumn(name = "RESERVATION_ID")
    private ReservationEntity reservationEntity;
}
