package com.example.web.item;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.member.Member;
import com.example.web.member.MemberRepository;
import com.example.web.order.OrderAddDto;
import com.example.web.product.Product;
import com.example.web.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long add(OrderAddDto orderAddDto, Long memberId){
        Item item = itemRepository.findTop1ByProductIdAndMemberId(orderAddDto.getProductId(), memberId).orElseGet(() -> getNewItem(orderAddDto.getProductId(), memberId));
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
    public void deleteByMemberId(Long id) {
        itemRepository.deleteByMemberId(id);
    }

    @Transactional
    private Item getNewItem(Long productId, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 존재하지 않습니다."));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 상품이 존재하지 않습니다."));
        Item item = Item.builder()
                .member(member)
                .product(product)
                .quantity(0l)
                .build();
        itemRepository.save(item);
        return item;
    }
}
