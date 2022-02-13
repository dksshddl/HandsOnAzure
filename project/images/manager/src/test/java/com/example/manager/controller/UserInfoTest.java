package com.example.manager.controller;

import com.azure.data.tables.models.TableEntity;
import com.example.manager.domain.user.UserVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import reactor.core.publisher.Mono;

import java.util.Objects;

@SpringBootTest
public class UserInfoTest {
    @Autowired
    UserInfoController userInfoController;

    UserVO user;

    @Test
    void main_test() {
        user = new UserVO();
        user.setUserId("testId");
        user.setUserPassword("testPassword");
        user.setName("testName");
        user.setEmailAddr("test@Addr");

        join_test();
        get_test();
        delete_test();
    }

    void join_test() {
        ResponseEntity<Object> join = userInfoController.join(user);
        Assertions.assertThat(join.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    void get_test() {
        ResponseEntity<Mono<TableEntity>> getResp = userInfoController.get("testId");
        Mono<TableEntity> mono = Objects.requireNonNull(getResp.getBody());
        mono.subscribe((entity) -> {
            String partitionKey = (String) entity.getProperty("PartitionKey");
            String userId = (String) entity.getProperty("userId");
            String userPassword = (String) entity.getProperty("userPassword");
            String emailAddr = (String) entity.getProperty("emailAddr");
            String name = (String) entity.getProperty("name");

            Assertions.assertThat(partitionKey).isEqualTo("user");
            Assertions.assertThat(userId).isEqualTo("testId");
            Assertions.assertThat(userPassword).isEqualTo("testPassword");
            Assertions.assertThat(emailAddr).isEqualTo("test@Addr");
            Assertions.assertThat(name).isEqualTo("testName");
        });
    }

    void delete_test() {
        ResponseEntity<Object> resp = userInfoController.delete("testId");
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
