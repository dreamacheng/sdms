package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.AccountVO;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.GenderEnum;
import com.pro.it.sdms.enums.PoliticsStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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

    /** 联系电话 */
    @Column(name = "tel", nullable = false, columnDefinition = "varchar(30)")
    private String tel;

    /** 身份证号码 */
    @Column(name = "identity_card", nullable = false, columnDefinition = "varchar(30)")
    private String identityCard;

    /** 学院 **/
    @Column(name = "college", columnDefinition = "varchar(30)")
    private String college;

    /** 专业科系 */
    @Column(name = "major", columnDefinition = "varchar(30)")
    private String major;

    /** 政治面貌 */
    @Column(name = "politics_status", nullable = false, columnDefinition = "int")
    private Short politicsStatus;

    /** 在校公寓 */
    @Column(name = "lodging_house", columnDefinition = "varchar(30)")
    private String lodgingHouse;

    /** 入学时间 */
    @Column(name = "enrollment", columnDefinition = "datetime")
    private Date enrollment;

    /** 用户是否锁定 **/
    @Column(name = "isLock", nullable = false, columnDefinition = "int")
    private Short isLock;

    /** 登录salt */
    @Column(name = "salt",columnDefinition = "varchar(30)")
    private String salt;

    @Column(name = "role",columnDefinition = "varchar(10)")
    private String role;


    public AccountVO toVO () {
        AccountVO vo = new AccountVO();
        vo.setUsername(getUsername());
        vo.setAccountNo(getAccountNo());
        vo.setAge(getAge());
        vo.setBirthday(getBirthday());
        vo.setIdentityCard(getIdentityCard());
        vo.setLodgingHouse(getLodgingHouse());
        vo.setNation(getNation());
        vo.setMajor(getMajor());
        vo.setCollege(getCollege());
        vo.setPoliticsStatus(BaseCodeEnum.codeOf(PoliticsStatusEnum.class, getPoliticsStatus()).toString());
        vo.setGender(BaseCodeEnum.codeOf(GenderEnum.class, getGender()).toString());
        vo.setTel(getTel());
        vo.setRole(getRole());
        vo.setAvatar("/avatar2.jpg");
        vo.setIsLock(getIsLock());
        vo.setId(getId());
        return vo;
    }

}
