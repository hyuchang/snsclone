package com.huttchang.global.model;

public enum ACL {
    PUBLIC(0), PRIVATE(1);
    int acl;
    ACL(int value){
        this.acl = value;
    }
}
