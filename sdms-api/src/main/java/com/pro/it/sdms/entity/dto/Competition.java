package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.CompetitionVO;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.CompetitionLevelEnum;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.Date;

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

    public CompetitionVO toVO() {
        String type;
        if(competitionTime.before(new Date())) {
            type = "已结束";
        } else {
            type = "尚未开始";
        }
        return CompetitionVO.builder()
                .id(getId())
                .competitionTime(getCompetitionTime())
                .level(BaseCodeEnum.codeOf(CompetitionLevelEnum.class, getLevel()).toString())
                .name(getName())
                .type(type)
                .registrationEndTime(getRegistrationEndTime())
                .registrationStartTime(getRegistrationStartTime())
                .desc(getDesc()).build();
    }
}
