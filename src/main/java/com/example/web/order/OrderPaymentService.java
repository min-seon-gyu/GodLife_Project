package com.example.web.order;

import com.example.web.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderPaymentService {
    private final OrderService orderService;
    private final ItemService itemService;

    @Transactional
    public Long payment(OrderAddDto orderAddDto, Long memberId){
        itemService.add(orderAddDto, memberId);
        return  orderService.add(orderAddDto, memberId);
    }
}
