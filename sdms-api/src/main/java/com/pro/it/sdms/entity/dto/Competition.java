package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.CompetitionVO;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.CompetitionLevelEnum;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "sdms_competition")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
/**
 * 比赛表
 */
public class Competition extends BaseDTO {

    /** 比赛名称 */
    @Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
    private String name;

    /** 比赛级别 */
    @Column(name = "level", nullable = false, columnDefinition = "int")
    private Short level;

    /** 比赛时间 */
    @Column(name = "competition_time", nullable = false, columnDefinition = "datetime")
    private Date competitionTime;

    /** 报名开始时间 */
    @Column(name = "registration_start_time", nullable = false, columnDefinition = "datetime")
    private Date registrationStartTime;

    /** 报名截止时间 */
    @Column(name = "registration_end_time", nullable = false, columnDefinition = "datetime")
    private Date registrationEndTime;

    /** 比赛描述 */
    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String desc;

    /** 参赛人员 */
    @ManyToMany
    @JoinTable(name = "competition_account",joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id"))
    private List<Account> attendeeList;

    /** 获奖选手 */
    @ManyToMany
    @JoinTable(name = "competition_prizewinner",joinColumns = @JoinColumn(name = "result_id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id"))
    private List<CompetitionResult> prizewinnerList;

    public CompetitionVO toVO() {
        String type;
        Date now = new Date();
        if (now.before(registrationStartTime)) {
            type = "准备报名";
        } else if (now.before(registrationEndTime)) {
            type = "报名中";
        } else if (now.before(competitionTime)) {
            type = "尚未开始";
        } else {
            type = "已结束";
        }
        if (attendeeList == null) {
            attendeeList = Collections.EMPTY_LIST;
        }
        return CompetitionVO.builder()
                .id(getId())
                .competitionTime(getCompetitionTime())
                .level(BaseCodeEnum.codeOf(CompetitionLevelEnum.class, getLevel()).toString())
                .name(getName())
                .type(type)
                .prizewinnerList(getPrizewinnerList())
                .attendeeList(getAttendeeList().stream().map(Account::toVO).collect(Collectors.toList()))
                .registrationEndTime(getRegistrationEndTime())
                .registrationStartTime(getRegistrationStartTime())
                .desc(getDesc()).build();
    }
}
