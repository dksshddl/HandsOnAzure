package com.example.manager.controller;

import com.example.manager.service.UserInfoService;
import com.example.manager.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserInfoController extends AbstractV1UserController {

    private final UserInfoService userInfoService;

    @PostMapping("/user")
    public void join(@RequestBody UserInfo user) {
        userInfoService.joinUser(user);
    }

    @GetMapping("/user/{userId}")
    public void get(@PathVariable String userId) {
        userInfoService.getUser(userId);
    }

    @DeleteMapping("/user/{userId}")
    public void delete(@PathVariable String userId) {
        userInfoService.deleteUser(userId);
    }

    @PutMapping("/user/{userId}/password")
    public void changePassword(@PathVariable("userId") String userId, @RequestBody Map<String, String> password) {
        userInfoService.changePassword(userId, password);
    }
}
