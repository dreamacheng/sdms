package com.pro.it.sdms.entity.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder(toBuilder=true)
public class CertificateVO {

    private String name;

    private String grade;
}
