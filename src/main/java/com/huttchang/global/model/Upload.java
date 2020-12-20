package com.huttchang.global.model;

import lombok.Data;

@Data
public class Upload {

    private String realPath;
    private String downloadPath;

    public Upload(String realPath, String downloadPath) {
        this.realPath = realPath;
        this.downloadPath = downloadPath;
    }
}
