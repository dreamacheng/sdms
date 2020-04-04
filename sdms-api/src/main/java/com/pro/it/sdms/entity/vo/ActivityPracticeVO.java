package com.pro.it.sdms.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.it.sdms.entity.dto.ActivityPractice;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
@Builder(toBuilder=true)
public class ActivityPracticeVO {

    private BigDecimal id;

    /** 发起组织 */
    private String organization;

    /** 活动主题 */
    private String title;

    /** 活动内容 */
    private String content;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date applyStartTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date applyEndTime;

    /** 参加人员 */
    private List<AccountVO> attendeeList;

    private String type;

    public ActivityPractice toDTO() {
        return ActivityPractice.builder()
                .title(getTitle())
                .content(getContent())
                .applyStartTime(getApplyStartTime())
                .applyEndTime(getApplyEndTime())
                .startTime(getStartTime())
                .endTime(getEndTime()).build();
    }
}
