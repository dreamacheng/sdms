package com.pro.it.sdms.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@ToString
public class AccountVO implements Serializable {

    /** 姓名 */
    @JsonProperty("username")
    private String username;

    /** 用户编号（学号、职工号） */
    @JsonProperty("accountNo")
    private String accountNo;

    /** 密码 */
    @JsonProperty("password")
    private String password;

    /** 性别 */
    @JsonProperty("gender")
    private Short gender;

    /** 民族 */
    @JsonProperty("nation")
    private String nation;

    /** 出生年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("birthday")
    private Date birthday;

    /** 年龄 */
    @JsonProperty("age")
    private Integer age;

    /** 在校公寓 */
    @JsonProperty("lodgingHouse")
    private String lodgingHouse;

    /** 联系电话 */
    @JsonProperty("tel")
    private String tel;

    /** 身份证号码 */
    @JsonProperty("identityCard")
    private String identityCard;

    /** 专业科系 */
    @JsonProperty("department")
    private String department;

    /** 政治面貌 */
    @JsonProperty("politicsStatus")
    private Short politicsStatus;

    /** 用户角色 */
    @JsonProperty("role")
    private String role;

    /** 管理员注册码 */
    @JsonProperty("registerCode")
    private String registerCode;

}
