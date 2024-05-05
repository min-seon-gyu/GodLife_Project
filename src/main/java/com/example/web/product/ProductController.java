package com.example.web.product;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.security.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    public ResponseEntity add(@AuthenticationPrincipal MemberDetails memberDetails,
                              @Valid @RequestBody ProductAddDto productAddDto){
        validCheck(memberDetails);
        Long id = productService.add(productAddDto);
        return ResponseEntity.ok(id);
    }

    @PatchMapping("/product")
    public ResponseEntity update(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @Valid @RequestBody ProductUpdateDto productUpdateDto){
        validCheck(memberDetails);
        Long id = productService.update(productUpdateDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity delete(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @PathVariable(value = "id") Long id){
        validCheck(memberDetails);
        productService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    private void validCheck(MemberDetails memberDetails){
        Collection<? extends GrantedAuthority> authorities = memberDetails.getAuthorities();
        String authority = authorities.stream().toList().get(0).getAuthority();
        if(!authority.equals("ADMIN")) {
            throw new RestApiException(ErrorCode.FORBIDDEN_ACCESS, "유효하지 않은 접근입니다.");
        }
    }
}
