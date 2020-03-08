package com.pro.it.sdms.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;


@Setter
@Getter
@NoArgsConstructor
@ToString
public class CreateAccountRequestEntity implements Serializable {

    /** 姓名 */
    private String username;

    /** 用户编号（学号、职工号） */
    private String accountNo;

    /** 密码 */
    private String password;

    /** 性别 */
    private String gender;

    /** 民族 */
    private String nation;

    /** 出生年月 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT")
    private Date birthday;

    /** 年龄 */
    private Integer age;

    /** 在校公寓 */
    private String lodgingHouse;

    /** 联系电话 */
    private String tel;

    /** 身份证号码 */
    private String identityCard;

    /** 专业科系 */
    private String department;

    /** 政治面貌 */
    private String politicsStatus;

    /** 用户角色 */
    private String role;

    /** 管理员注册码 */
    private String registerCode;

}
