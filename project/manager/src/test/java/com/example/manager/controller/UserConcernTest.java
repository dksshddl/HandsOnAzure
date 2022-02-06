package com.example.manager.controller;

import com.azure.data.tables.TableClient;
import com.example.manager.domain.concern.ConcernVO;
import com.example.manager.domain.user.UserVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserConcernTest {
    @Autowired
    UserConcernController userConcernController;

    @Autowired
    @Qualifier("concernTableClient")
    private TableClient tableClient;


    void concern_test() {
        tableClient.getEntity("dksshddl", "dksshddl_식품");
    }
    @Test
    void main_test() {
        add_test();
        delete_one_test();
        delete_multi_test();
    }

    void add_test() {
        List<String> categories = new ArrayList<>();
        categories.add("여행");
        categories.add("남성패션");
        categories.add("식품");
        categories.add("생활용품");
        ConcernVO concernVO = ConcernVO.builder().categories(categories).build();
        ResponseEntity<Object> resp = userConcernController.addConcern("dksshddl", concernVO);
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    void delete_one_test() {
        List<String> categories = new ArrayList<>();
        categories.add("여행");
        ConcernVO concernVO = ConcernVO.builder().categories(categories).build();
        ResponseEntity<Object> resp = userConcernController.deleteConcern("dksshddl", concernVO);
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    void delete_multi_test() {
        List<String> categories = new ArrayList<>();
        categories.add("남성패션");
        categories.add("식품");
        categories.add("생활용품");
        ConcernVO concernVO = ConcernVO.builder().categories(categories).build();
        ResponseEntity<Object> resp = userConcernController.deleteConcern("dksshddl", concernVO);
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
