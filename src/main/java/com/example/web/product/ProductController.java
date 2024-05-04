package com.example.web.product;

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
        if(validCheck(memberDetails)){
            Long id = productService.add(productAddDto);
            return ResponseEntity.ok(id);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/product")
    public ResponseEntity update(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @Valid @RequestBody ProductUpdateDto productUpdateDto){
        if(validCheck(memberDetails)){
            Long id = productService.update(productUpdateDto);
            return ResponseEntity.ok(id);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity delete(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @PathVariable(value = "id") Long id){
        if(validCheck(memberDetails)){
            productService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    private boolean validCheck(MemberDetails memberDetails){
        Collection<? extends GrantedAuthority> authorities = memberDetails.getAuthorities();
        String authority = authorities.stream().toList().get(0).getAuthority();
        System.out.println("authority = " + authority);
        return authority.equals("ADMIN") ? true : false;
    }

}
