package com.huttchang.global.model;

import lombok.Data;

@Data
public class Pagination {
    private int page = 1;
    private int offset = 0;
    private int limit = 30;

    public int getOffset(){
        return page == 1 ? 0 : (limit * (page - 1));
    }
}
