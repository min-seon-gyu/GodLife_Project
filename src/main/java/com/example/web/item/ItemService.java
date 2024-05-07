package com.example.web.item;

import com.example.web.member.Member;
import com.example.web.order.OrderAddDto;
import com.example.web.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long update(OrderAddDto orderAddDto,  Member member, Product product){
        Item item = itemRepository.findTop1ByProductIdAndMemberId(product.getId(), member.getId()).orElseGet(() -> getNewItem(member, product));
        item.addQuantity(orderAddDto.getTotalQuantity());
        return item.getId();
    }

    private Item getNewItem(Member member, Product product){
        Item item = Item.builder()
                .member(member)
                .product(product)
                .quantity(0l)
                .build();
        itemRepository.save(item);
        return item;
    }
}
