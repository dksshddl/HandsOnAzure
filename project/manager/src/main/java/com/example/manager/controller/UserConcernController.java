package com.example.manager.controller;

import com.example.manager.service.UserConcernService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserConcernController extends AbstractV1UserController {
    private final UserConcernService userConcernService;

    @GetMapping("/concern/{userId}")
    public void addConcern(@PathVariable String userId, @RequestParam("category") List<String> category) {
        userConcernService.addConcern(userId, category);
    }

    @DeleteMapping("/concern/{userId}")
    public void deleteConcern(@PathVariable String userId, @RequestParam("category") List<String> category) {
        userConcernService.deleteConcern(userId, category);
    }
}
