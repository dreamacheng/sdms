package com.pro.it.sdms.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sdms_account")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Account extends BaseDTO{
    @Id
    private BigDecimal id;
    /** 姓名 */
    @Column(name = "username", nullable = false, columnDefinition = "varchar(30)")
    private String username;
    /** 用户编号（学号、职工号） */
    @Column(name = "account_no", nullable = false, columnDefinition = "varchar(20)")
    private String accountNo;
    /** 密码 */
    @Column(name = "password", nullable = false, columnDefinition = "varchar(20)")
    private String password;
    /** 性别 */
    @Column(name = "gender", nullable = false, columnDefinition = "int")
    private Short gender;
    /** 民族 */
    @Column(name = "nation", nullable = false, columnDefinition = "varchar(30)")
    private String nation;
    /** 出生年月 */
    @Column(name = "birthday", nullable = false, columnDefinition = "datetime")
    private Date birthday;
    /** 年龄 */
    @Column(name = "age", nullable = false, columnDefinition = "int")
    private Integer age;
    /** 在校公寓 */
    @Column(name = "lodging_house", nullable = false, columnDefinition = "varchar(30)")
    private String lodgingHouse;
    /** 联系电话 */
    @Column(name = "tel", nullable = false, columnDefinition = "varchar(30)")
    private String tel;
    /** 身份证号码 */
    @Column(name = "identity_card", nullable = false, columnDefinition = "varchar(30)")
    private String identityCard;
    /** 专业科系 */
    @Column(name = "department", nullable = false, columnDefinition = "varchar(30)")
    private String department;
    /** 政治面貌 */
    @Column(name = "politics_status", nullable = false, columnDefinition = "int")
    private Short politicsStatus;
    /** 登录salt */
    @Column(name = "salt",columnDefinition = "varchar(30)")
    private String salt;
    @Column(name = "role",columnDefinition = "varchar(10)")
    private String role;

}
