package com.pro.it.common.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageInfo {
    private int total;
    private int pageNum;
    private int pageSize;
}
