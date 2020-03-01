package com.pro.it.sdms.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@ToString
public class AccountVO{
    /** 姓名 */
    private String username;

    /** 用户编号（学号、职工号） */
    private String accountNo;

    /** 密码 */
    private String password;

    /** 性别 */
    private Short gender;

    /** 民族 */
    private String nation;

    /** 出生年月 */
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
    private Short politicsStatus;

    /** 用户角色 */
    private String role;

    /** 管理员注册码 */
    private String registerCode;

}
