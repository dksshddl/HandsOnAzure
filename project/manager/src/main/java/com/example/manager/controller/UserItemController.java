package com.example.manager.controller;

import com.example.manager.domain.item.ItemListVO;
import com.example.manager.domain.item.ItemVO;
import com.example.manager.service.UserItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserItemController extends AbstractV1UserController {

    private final UserItemService userItemService;

    @GetMapping("/item/{userId}/like")
    public ResponseEntity<Mono<ItemListVO>> getLikeItem(@PathVariable("userId") String userId) {
        Mono<ItemListVO> likeItem = userItemService.getLikeItem(userId);
        return new ResponseEntity<>(likeItem, HttpStatus.OK);
    }

    @PostMapping("/item/{userId}/like/{itemId}")
    public ResponseEntity<Object> addLikeItem(@PathVariable("userId") String userId,
                                        @PathVariable("itemId") String itemId) {
        userItemService.addLikeItem(userId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/item/{userId}/like/{itemId}")
    public ResponseEntity<Object> deleteLikeItem(@PathVariable("userId") String userId,
                                                 @PathVariable("itemId") String itemId) {

        userItemService.deleteLikeItem(userId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/item/{userId}/hate")
    public ResponseEntity<Mono<ItemListVO>> getHateItem(@PathVariable("userId") String userId) {
        Mono<ItemListVO> hateItem = userItemService.getHateItem(userId);
        return new ResponseEntity<>(hateItem, HttpStatus.OK);
    }

    @PostMapping("/item/{userId}/hate/{itemId}")
    public ResponseEntity<Object> addHateItem(@PathVariable("userId") String userId,
                                              @PathVariable("itemId") String itemId) {
        userItemService.addHateItem(userId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/item/{userId}/hate/{itemId}")
    public ResponseEntity<Object> deleteHateItem(@PathVariable("userId") String userId,
                                                 @PathVariable("itemId") String itemId) {
        userItemService.deleteHateItem(userId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
