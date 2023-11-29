package com.example.web.reservation;

import com.example.web.memberReservation.MemberReservationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "PRICE")
    private int price;
    @Column(name = "DATE")
    private Date data;
    @OneToMany(mappedBy = "reservationEntity")
    List<MemberReservationEntity> memberReservations = new ArrayList<>();

}
