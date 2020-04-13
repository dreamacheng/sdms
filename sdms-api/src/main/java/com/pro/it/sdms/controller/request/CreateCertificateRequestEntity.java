package com.pro.it.sdms.controller.request;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCertificateRequestEntity {

    /** 证书名称 */
    private String name;

    /** 成绩 */
    private String grade;

}
