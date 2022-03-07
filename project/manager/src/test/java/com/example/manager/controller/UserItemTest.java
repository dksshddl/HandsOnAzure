package com.example.manager.controller;

import com.example.manager.domain.item.ItemListVO;
import com.example.manager.domain.item.ItemType;
import com.example.manager.domain.item.ItemVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
public class UserItemTest {
    @Autowired
    UserItemController userItemController;

    @Test
    void main_test() {
        add_like_test();
        get_like_test();
        delete_like_test();

        add_hate_test();
        get_hate_test();
        delete_hate_test();
    }

    void add_like_test() {
        ResponseEntity<Mono<Object>> resp = userItemController.addLikeItem("dksshddl", "addLikeTest");
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    void get_like_test() {
        ResponseEntity<Mono<ItemListVO>> response = userItemController.getLikeItem("dksshddl");
        Mono<ItemListVO> mono = Objects.requireNonNull(response.getBody());
        mono.doOnEach((vo) -> {
            ItemListVO itemListVO = Objects.requireNonNull(vo.get());
            ItemVO result = itemListVO.getItemVOList().stream().filter((itemVO) -> itemVO.getItemId().equals("addLikeTest")).findAny().orElseThrow();
            Assertions.assertThat(result.getItemId()).isEqualTo("addLikeTest");
            Assertions.assertThat(result.getItemType()).isEqualTo(ItemType.LIKE);
            Assertions.assertThat(result.getUserId()).isEqualTo("dksshddl");
        });
    }

    void delete_like_test() {
        ResponseEntity<Object> resp = userItemController.deleteLikeItem("dksshddl", "addLikeTest");
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    void add_hate_test() {
        ResponseEntity<Mono<Object>> resp = userItemController.addHateItem("dksshddl", "addHateTest");
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    void get_hate_test() {
        ResponseEntity<Mono<ItemListVO>> response = userItemController.getHateItem("dksshddl");
        Mono<ItemListVO> mono = Objects.requireNonNull(response.getBody());
        mono.doOnEach((vo) -> {
            ItemListVO itemListVO = Objects.requireNonNull(vo.get());
            ItemVO result = itemListVO.getItemVOList().stream().filter((itemVO) -> itemVO.getItemId().equals("addHateTest")).findAny().orElseThrow();
            Assertions.assertThat(result.getItemId()).isEqualTo("addHateTest");
            Assertions.assertThat(result.getItemType()).isEqualTo(ItemType.HATE);
            Assertions.assertThat(result.getUserId()).isEqualTo("dksshddl");
        });
    }

    void delete_hate_test() {
        ResponseEntity<Object> resp = userItemController.deleteHateItem("dksshddl", "addHateTest");
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
