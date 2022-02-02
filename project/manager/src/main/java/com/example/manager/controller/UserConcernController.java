package com.example.manager.controller;

import com.example.manager.domain.concern.ConcernVO;
import com.example.manager.service.UserConcernService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserConcernController extends AbstractV1UserController {
    private final UserConcernService userConcernService;

    @GetMapping("/concern/{userId}")
    public ResponseEntity<Object> addConcern(@PathVariable String userId, @RequestBody ConcernVO category) {
        userConcernService.addConcern(userId, category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/concern/{userId}")
    public ResponseEntity<Object> deleteConcern(@PathVariable String userId, @RequestBody ConcernVO category) {
        userConcernService.deleteConcern(userId, category);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
