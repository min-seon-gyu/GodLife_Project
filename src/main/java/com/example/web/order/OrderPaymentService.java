package com.example.web.order;

import com.example.web.item.ItemService;
import com.example.web.member.Member;
import com.example.web.memberCoupon.MemberCoupon;
import com.example.web.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderPaymentService {

    private final OrderService orderService;
    private final ItemService itemService;

    @Transactional
    public Long payment(OrderAddDto orderAddDto, Member member, Product product){
        itemService.add(orderAddDto, member, product);
        return  orderService.add(orderAddDto, member, product);
    }

    @Transactional
    public Long payment(OrderAddDto orderAddDto, Member member, Product product, MemberCoupon memberCoupon){
        itemService.add(orderAddDto, member, product);
        return  orderService.add(orderAddDto, member, product, memberCoupon);
    }
}
