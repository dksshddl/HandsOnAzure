package com.example.manager.controller;

import com.azure.data.tables.models.TableEntity;
import com.example.manager.service.UserInfoService;
import com.example.manager.domain.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserInfoController extends AbstractV1UserController {

    private final UserInfoService userInfoService;

    @PostMapping("/user")
    public ResponseEntity<Object> join(@RequestBody UserVO user) {
        userInfoService.joinUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Mono<TableEntity>> get(@PathVariable String userId) {
        return new ResponseEntity<>(userInfoService.getUser(userId), HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Object> delete(@PathVariable String userId) {
        userInfoService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
