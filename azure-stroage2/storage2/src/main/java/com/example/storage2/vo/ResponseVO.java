package com.example.storage2.vo;

import lombok.Data;

@Data
public class ResponseVO {
    String id;
    String msg;

    public ResponseVO(String id, String msg) {
        this.id = id;
        this.msg = msg;
    }
}