package com.pro.it.sdms.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.AccountInfo;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.SemesterEnum;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class AccountVO implements Serializable {

    private BigDecimal id;

    private BigDecimal infoId;

    /** 姓名 */
    private String username;

    /** 用户编号（学号、职工号） */
    private String accountNo;

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
    private String major;

    /** 学院 */
    private String college;

    /** 政治面貌 */
    private String politicsStatus;

    /** 入学时间 */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT")
    private Date enrollment;

    /** 用户角色 */
    private String role;

    /** 用户头像 **/
    private String avatar;

    /** 用户是否锁定 **/
    private Short isLock;

    /** 用户参加的社团 */
    private List<SchoolClubVO> schoolClubList;

    /** 用户当前学期 */
    private String currentTerm;

}
