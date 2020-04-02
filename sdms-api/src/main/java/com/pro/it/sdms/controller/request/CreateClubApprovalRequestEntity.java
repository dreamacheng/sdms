package com.pro.it.sdms.controller.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateClubApprovalRequestEntity {

    private BigDecimal id;

    private String approvalComment;

    private String approvalResult;
}
