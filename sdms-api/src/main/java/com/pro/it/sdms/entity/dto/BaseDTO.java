package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BeanBase;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class BaseDTO extends BeanBase {
    private Date createDatetime;
    private String createUser;
    private Date updateDatetime;
    private String updateUser;
}
