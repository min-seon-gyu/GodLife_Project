package com.example.web.product;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long add(ProductAddDto productAddDto) {
        Product product = Product.builder()
                .price(productAddDto.getPrice())
                .quantity(productAddDto.getQuantity())
                .name(productAddDto.getName())
                .build();
        return productRepository.save(product).getId();
    }

    @Transactional
    public Long update(ProductUpdateDto productUpdateDto) {
        Optional<Product> findProduct = productRepository.findById(productUpdateDto.getId());
        Product product = findProduct.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 상품이 없습니다."));
        product.update(productUpdateDto);
        return product.getId();
    }

    @Transactional
    public void delete(Long id) {
        Optional<Product> findProduct = productRepository.findById(id);
        productRepository.delete(findProduct.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 상품이 없습니다.")));
    }
}
