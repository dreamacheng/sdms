package com.pro.it.sdms.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class ApplyResultRequestEntity {

    private String applyStatus;

    private String applyComment;

    private BigDecimal id;

}
