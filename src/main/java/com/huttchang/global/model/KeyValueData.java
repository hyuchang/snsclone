package com.huttchang.global.model;

import lombok.Data;

@Data
public class KeyValueData<K,V> {
    private K id;
    private  V value;
}
