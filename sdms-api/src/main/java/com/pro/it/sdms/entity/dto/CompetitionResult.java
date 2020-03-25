package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "sdms_Competition_result")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CompetitionResult extends BaseDTO {

    /** 获奖人学号 */
    @Column(name = "account_no", nullable = false, columnDefinition = "varchar(30)")
    private String accountNo;

    /** 获奖人姓名 */
    @Column(name = "account_name", nullable = false, columnDefinition = "varchar(30)")
    private String accountName;

    /** 比赛名称 */
    @Column(name = "competition_name", nullable = false, columnDefinition = "varchar(100)")
    private String competitionName;

    /** 比赛描述 */
    @Column(name = "type", columnDefinition = "varchar(30)")
    private String type;

    /** 比赛成绩 */
    @Column(name = "grade", nullable = false, columnDefinition = "varchar(30)")
    private String grade;

}
