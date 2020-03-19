package com.pro.it.sdms.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.enums.GenderEnum;
import com.pro.it.sdms.enums.IdentityEnum;
import com.pro.it.sdms.enums.PoliticsStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.TimeZone;


@Setter
@Getter
@NoArgsConstructor
@ToString
public class PersistAccountRequestEntity implements Serializable {

    private BigDecimal id;

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

    /** 学院 */
    private String college;

    /** 专业科系 */
    private String major;

    /** 政治面貌 */
    private String politicsStatus;

    /** 用户角色 */
    private String role;

    /** 管理员注册码 */
    private String registerCode;


    public Account toDTO (){
        Account account = new Account();
        account.setUsername(this.getUsername());
        String encodePwd = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(this.getPassword());
        account.setPassword(encodePwd);
        account.setAccountNo(this.getAccountNo());
        account.setAge(this.getAge());
        account.setBirthday(this.getBirthday());
        account.setIdentityCard(this.getIdentityCard());
        account.setCollege(getCollege());
        account.setMajor(getMajor());
        account.setLodgingHouse(this.getLodgingHouse());
        account.setNation(this.getNation());
        try {
            account.setPoliticsStatus(PoliticsStatusEnum.valueOf(this.getPoliticsStatus()).getCode());
            account.setGender(GenderEnum.valueOf(this.getGender()).getCode());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(Constants.Code.PARAM_ILLEGAL_VALUE, "gender or politics status not exist in this system");
        }
        account.setTel(this.getTel());
        try {
            IdentityEnum.valueOf(this.getRole());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(Constants.Register.ROLE_NOT_EXIST, "role not exist in this system");
        }
        account.setRole(this.getRole());
        return account;
    }

}
