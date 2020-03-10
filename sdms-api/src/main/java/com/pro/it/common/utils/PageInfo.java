package com.pro.it.common.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PageInfo {
    private int total;
    private int pageNum;
    private int pageSize;
}
