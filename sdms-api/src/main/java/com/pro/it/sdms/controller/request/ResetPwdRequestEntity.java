package com.pro.it.sdms.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResetPwdRequestEntity {

    private String username;

    private String accountNo;

    private String identityCard;
}
