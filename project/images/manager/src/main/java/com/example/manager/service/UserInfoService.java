package com.example.manager.service;

import com.azure.data.tables.models.TableEntity;
import com.example.manager.domain.user.UserVO;
import com.example.manager.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public void joinUser(UserVO user) {
        try {
            userInfoRepository.upsert(UserVO.of(user));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Mono<TableEntity> getUser(String userId) {
        return Mono.fromCallable(()->userInfoRepository.get(userId));
    }

    public void deleteUser(String userId) {
        userInfoRepository.delete(userId);
    }
}
