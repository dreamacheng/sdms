package com.pro.it.sdms.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.Competition;
import com.pro.it.sdms.entity.dto.CompetitionResult;
import com.pro.it.sdms.enums.CompetitionLevelEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
@Builder(toBuilder=true)
public class CompetitionVO {

    private BigDecimal id;

    /** 比赛名称 */
    private String name;

    /** 比赛级别 */
    private String level;

    /** 比赛时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date competitionTime;

    /** 报名开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date registrationStartTime;

    /** 报名截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date registrationEndTime;

    /** 比赛描述 */
    private String desc;

    /** 比赛状态 （未开始、结束）*/
    private String type;

    /** 参赛人员 */
    private List<AccountVO> attendeeList;

    /** 获奖选手 */
    private List<CompetitionResult> prizewinnerList;

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
