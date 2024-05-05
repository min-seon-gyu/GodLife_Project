package com.example.web.order;

import com.example.web.coupon.Coupon;
import com.example.web.coupon.CouponRepository;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.item.Item;
import com.example.web.item.ItemRepository;
import com.example.web.member.Member;
import com.example.web.member.MemberRepository;
import com.example.web.memberCoupon.MemberCouponRepository;
import com.example.web.product.Product;
import com.example.web.product.ProductRepository;
import com.example.web.memberCoupon.MemberCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;
    private final ItemRepository itemRepository;

    public Long cost(OrderCalculatorDto orderCalculatorDto) {
        Long cost = orderCalculatorDto.getPrice() * orderCalculatorDto.getQuantity();
        Optional<MemberCoupon> findUserCoupon = memberCouponRepository.findById(orderCalculatorDto.getUserCouponId());
        if(findUserCoupon.isPresent()){
            MemberCoupon memberCoupon = findUserCoupon.get();
            memberCoupon.use(LocalDateTime.now());
            Coupon coupon = couponRepository.findById(memberCoupon.getCouponId()).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 쿠폰이 존재하지 않습니다."));
            cost = coupon.costAfterDiscount(cost);
        }
        return cost;
    }

    @Transactional
    public Long order(OrderAddDto orderAddDto, Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 유저가 존재하지 않습니다."));
        Product product = productRepository.findById(orderAddDto.getProductId()).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 상품이 존재하지 않습니다."));

        product.removeQuantity(orderAddDto.getTotalQuantity());
        member.buy(orderAddDto.getTotalPrice());

        Order order = Order.builder()
                .member(member)
                .product(product)
                .totalPrice(orderAddDto.getTotalPrice())
                .totalQuantity(orderAddDto.getTotalQuantity())
                .userCouponId(orderAddDto.getUserCouponId() == 0 ? null : orderAddDto.getUserCouponId())
                .build();
        orderRepository.save(order);

        Item item = itemRepository.findTop1ByProductIdAndMemberId(orderAddDto.getProductId(), id).orElseGet(() -> saveItem(member, product));
        item.addQuantity(orderAddDto.getTotalQuantity());

        return order.getId();
    }

    private Item saveItem(Member member, Product product){
        Item item = Item.builder()
                .member(member)
                .product(product)
                .quantity(0l)
                .build();
        itemRepository.save(item);
        return item;
    }
}
