package com.pro.it.sdms.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UpdatePwdRequestEntity {

    private String oldPwd;

    private String newPwd;
}
