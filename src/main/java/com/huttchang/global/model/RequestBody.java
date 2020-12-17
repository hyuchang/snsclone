package com.huttchang.global.model;

import lombok.Data;

@Data
public class RequestBody<T> {

    private Long id;
    private T body;
}
