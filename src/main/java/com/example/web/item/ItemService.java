package com.example.web.item;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
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
    public Long add(OrderAddDto orderAddDto,  Member member, Product product){
        Item item = itemRepository.findTop1ByProductIdAndMemberId(product.getId(), member.getId()).orElseGet(() -> getNewItem(member, product));
        item.addQuantity(orderAddDto.getTotalQuantity());
        return item.getId();
    }

    @Transactional
    public Long use(ItemUseDto itemUseDto){
        Item item = itemRepository.findById(itemUseDto.getId()).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 아이템이 존재하지 않습니다."));
        item.use();
        return item.getId();
    }

    @Transactional
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
