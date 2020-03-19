package com.pro.it.sdms.entity.vo;

import com.pro.it.sdms.entity.dto.Competition;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.CompetitionLevelEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Setter
@Getter
@ToString
@Builder(toBuilder=true)
public class CompetitionVO {

    /** 比赛名称 */
    private String name;

    /** 比赛级别 */
    private String level;

    /** 比赛时间 */
    private Date competitionTime;

    /** 报名开始时间 */
    private Date registrationStartTime;

    /** 报名截止时间 */
    private Date registrationEndTime;

    /** 比赛描述 */
    private String desc;

    public Competition toDTO() {
        return Competition.builder()
                .desc(getDesc())
                .level(CompetitionLevelEnum.valueOf(level).getCode())
                .competitionTime(getCompetitionTime())
                .registrationEndTime(getRegistrationEndTime())
                .registrationStartTime(getRegistrationStartTime())
                .name(getName()).build();
    }
}
