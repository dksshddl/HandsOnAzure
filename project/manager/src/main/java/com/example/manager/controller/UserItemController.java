package com.example.manager.controller;

import com.example.manager.domain.ItemInfo;
import com.example.manager.service.UserItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserItemController extends AbstractV1UserController {

    private final UserItemService userItemService;

    @PostMapping("/item/{userId}/like")
    public void addLikeItem(@PathVariable("userId") String userId,
                            @RequestBody ItemInfo iteminfo) {
        userItemService.addLikeItem(userId, iteminfo);
    }

    @DeleteMapping("/item/{userId}/like")
    public void deleteLikeItem(@PathVariable("userId") String userId,
                         @RequestParam String category,
                         @RequestParam String id) {
        userItemService.deleteLikeItem(userId, category, id);
    }

    @PostMapping("/item/{userId}/hate")
    public void addHateItem(@PathVariable("userId") String userId,
                            @RequestBody ItemInfo iteminfo) {
        userItemService.addHateItem(userId, iteminfo);
    }

    @DeleteMapping("/item/{userId}/hate")
    public void deleteHateItem(@PathVariable("userId") String userId,
                               @RequestParam String category,
                               @RequestParam String id) {
        userItemService.deleteHateItem(userId, category, id);
    }
}
