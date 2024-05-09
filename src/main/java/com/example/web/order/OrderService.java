package com.example.web.order;

import com.example.web.member.Member;
import com.example.web.member.MemberService;
import com.example.web.memberCoupon.MemberCouponService;
import com.example.web.product.Product;
import com.example.web.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final MemberService memberService;
    private final MemberCouponService memberCouponService;
    @Transactional
    public Long add(OrderAddDto orderAddDto, Long memberId) {
        Product product = productService.decreaseQuantity(orderAddDto.getProductId(), orderAddDto.getTotalQuantity());
        Member member = memberService.buyItem(memberId, orderAddDto.getTotalPrice());
        memberCouponService.use(orderAddDto.getMemberCouponId());

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
    public void deleteByMemberId(Long id) {
        orderRepository.deleteByMemberId(id);
    }
}
