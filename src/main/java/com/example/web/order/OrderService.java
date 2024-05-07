package com.example.web.order;

import com.example.web.member.Member;
import com.example.web.memberCoupon.MemberCoupon;
import com.example.web.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public Long add(OrderAddDto orderAddDto, Member member, Product product) {
        product.removeQuantity(orderAddDto.getTotalQuantity());
        member.buy(orderAddDto.getTotalPrice());

        Order order = Order.builder()
                .member(member)
                .product(product)
                .totalPrice(orderAddDto.getTotalPrice())
                .totalQuantity(orderAddDto.getTotalQuantity())
                .userCouponId(orderAddDto.getMemberCouponId() == 0 ? null : orderAddDto.getMemberCouponId())
                .build();
        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public Long add(OrderAddDto orderAddDto, Member member, Product product, MemberCoupon memberCoupon) {
        product.removeQuantity(orderAddDto.getTotalQuantity());
        member.buy(orderAddDto.getTotalPrice());
        memberCoupon.use(LocalDateTime.now());

        Order order = Order.builder()
                .member(member)
                .product(product)
                .totalPrice(orderAddDto.getTotalPrice())
                .totalQuantity(orderAddDto.getTotalQuantity())
                .userCouponId(orderAddDto.getMemberCouponId() == 0 ? null : orderAddDto.getMemberCouponId())
                .build();
        orderRepository.save(order);
        return order.getId();
    }
}
