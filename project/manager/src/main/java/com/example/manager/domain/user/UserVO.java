package com.example.manager.domain.user;

import lombok.Data;

@Data
public class UserVO {
    String userId;
    String userPassword;

    String emailAddr;
    String name;

    public static User of(UserVO userInfo) {
        return User.builder()
                .userId(userInfo.getUserId())
                .userPassword(userInfo.getUserPassword())
                .emailAddr(userInfo.getEmailAddr())
                .name(userInfo.getName()).build();
    }
}
