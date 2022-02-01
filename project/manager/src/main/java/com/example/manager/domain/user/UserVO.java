package com.example.manager.domain.user;

import lombok.Data;

@Data
public class UserVO {
    String userId;
    String userPassword;

    String name;

    public static User of(UserVO userInfo) {
        return User.builder()
                .userId(userInfo.getUserId())
                .userPassword(userInfo.getUserPassword())
                .name(userInfo.getName()).build();
    }
}
