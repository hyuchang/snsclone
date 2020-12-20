package com.huttchang.sns.post.dto;

import lombok.Data;

@Data
public class KeyValueData<K,V> {
    private K id;
    private  V value;
}
