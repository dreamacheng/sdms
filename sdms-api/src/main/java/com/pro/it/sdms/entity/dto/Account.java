package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.AccountVO;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.GenderEnum;
import com.pro.it.sdms.enums.PoliticsStatusEnum;
import com.pro.it.sdms.enums.SemesterEnum;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "sdms_account", uniqueConstraints = {
        @UniqueConstraint(columnNames = "account_no"),
        @UniqueConstraint(columnNames = "identity_card")
})
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "accountNo")
/**
 * 用户表 （学生、教师）
 */
public class Account extends BaseDTO {

    /** 姓名 */
    @Column(name = "username", nullable = false, columnDefinition = "varchar(30)")
    private String username;

    /** 用户编号（学号、职工号） */
    @Column(name = "account_no", nullable = false, columnDefinition = "varchar(20)")
    private String accountNo;

    /** 密码 */
    @Column(name = "password", nullable = false, columnDefinition = "varchar(300)")
    private String password;

    /** 头像URL */
    @Column(name = "avatar", columnDefinition = "varchar(200)")
    private String avatar;

    /** 性别 */
    @Column(name = "gender", nullable = false, columnDefinition = "int")
    private Short gender;

    /** 民族 */
    @Column(name = "nation", nullable = false, columnDefinition = "varchar(30)")
    private String nation;

    /** 出生年月 */
    @Column(name = "birthday", nullable = false, columnDefinition = "date")
    private Date birthday;

    /** 联系电话 */
    @Column(name = "tel", nullable = false, columnDefinition = "varchar(30)")
    private String tel;

    /** 身份证号码 */
    @Column(name = "identity_card", nullable = false, columnDefinition = "varchar(30)")
    private String identityCard;

    /** 政治面貌 */
    @Column(name = "politics_status", nullable = false, columnDefinition = "int")
    private Short politicsStatus;

    /** 用户是否锁定 **/
    @Column(name = "isLock", nullable = false, columnDefinition = "int")
    private Short isLock;

    /** 登录salt */
    @Column(name = "salt",columnDefinition = "varchar(30)")
    private String salt;

    @Column(name = "role",columnDefinition = "varchar(10)")
    private String role;

    @OneToOne(cascade=CascadeType.ALL)//Account是关系的维护端，当删除 Account，会级联删除 AccountInfo
    @JoinColumn(name = "accountInfo_id", referencedColumnName = "id")//Account 中的 accountInfo_id 字段参考 AccountInfo 表中的id字段
    private AccountInfo accountInfo;


    public AccountVO toVO () {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate previous = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(accountInfo.getEnrollment()), dateTimeFormatter);
        Period period = Period.between(previous, localDate);
        short currentTerm = (short) ((period.getYears()* 12 + period.getMonths())/6 + 1);
        LocalDate previousAge = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(getBirthday()), dateTimeFormatter);
        Period periodAge = Period.between(previousAge, localDate);
        if (StringUtils.isEmpty(getAvatar())) {
            setAvatar("/avatar2.jpg");
        }
        return AccountVO.builder()
                .username(getUsername())
                .accountNo(getAccountNo())
                .birthday(getBirthday())
                .identityCard(getIdentityCard())
                .nation(getNation())
                .currentTerm(BaseCodeEnum.codeOf(SemesterEnum.class, currentTerm).toString())
                .age(periodAge.getYears())
                .politicsStatus(BaseCodeEnum.codeOf(PoliticsStatusEnum.class, getPoliticsStatus()).toString())
                .gender(BaseCodeEnum.codeOf(GenderEnum.class, getGender()).toString())
                .tel(getTel())
                .role(getRole())
                .avatar(getAvatar())
                .isLock(getIsLock())
                .lodgingHouse(accountInfo.getLodgingHouse())
                .enrollment(accountInfo.getEnrollment())
                .major(accountInfo.getMajor())
                .college(accountInfo.getCollege())
                .infoId(accountInfo.getId())
                .id(getId()).build();
    }

}
