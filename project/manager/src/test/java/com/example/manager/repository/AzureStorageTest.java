package com.example.manager.repository;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.TableServiceClient;
import com.azure.data.tables.models.TableServiceException;
import com.example.manager.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@Slf4j
@SpringBootTest
public class AzureStorageTest {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private TableClient userTableClient;

    @Autowired
    private TableServiceClient tableServiceClient;

    private User user;

    @BeforeEach
    void beforeEach() {
        user = User.builder()
                .userId("id")
                .userPassword("password")
                .name("name")
                .build();
        try {
            userInfoRepository.upsert(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void afterEach() {
        userTableClient.deleteEntity("user", user.getUserId());
    }

    @Test
    void bean_load_test() {
        Assertions.assertThat(userInfoRepository).isNotNull();
        Assertions.assertThat(userTableClient).isNotNull();
        Assertions.assertThat(tableServiceClient).isNotNull();
    }

    @Test
    void insert_test() {
        Assertions.assertThatCode(()->userInfoRepository.upsert(user)).doesNotThrowAnyException();
        userTableClient.deleteEntity("user", user.getUserId());
    }

    @Test
    void update_test() {
        User updateUser = User.builder()
                .userId("id")
                .userPassword("password")
                .name("update name")
                .build();
        Assertions.assertThatCode(()->userInfoRepository.upsert(updateUser)).doesNotThrowAnyException();
    }

    @Test
    void delete_test() {
        Assertions.assertThatCode(()->userInfoRepository.delete("id")).doesNotThrowAnyException();
    }

    @Test
    void delete_throw_test() {
        Assertions.assertThatCode(()->userInfoRepository.delete("nullId")).isInstanceOf(TableServiceException.class);
    }

    @Test
    void get_test() {
        Assertions.assertThatCode(() -> userInfoRepository.get("id")).doesNotThrowAnyException();
    }

    @Test
    void get_throw_test() {
//          "com.azure.data.tables.models.TableServiceException: Status code 404, "{"odata.error":{"code":"ResourceNotFound","message":{"lang":"en-US","value":"The specified resource does not exist.\nRequestId:2160742f-b002-0005-1825-170074000000\nTime:2022-02-01T04:37:52.9609448Z"}}}"
        Assertions.assertThatCode(() -> userInfoRepository.get("nullId")).isInstanceOf(TableServiceException.class);
    }
}
