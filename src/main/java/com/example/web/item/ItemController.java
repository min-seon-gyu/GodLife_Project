package com.example.web.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item/use")
    public ResponseEntity use(@RequestBody ItemUseDto itemUseDto){
        return ResponseEntity.ok(itemService.use(itemUseDto));
    }
}
