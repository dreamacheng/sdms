package com.pro.it.sdms.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ApprovalClubRequestEntity {

    private BigDecimal id;

    private String operation;
}
