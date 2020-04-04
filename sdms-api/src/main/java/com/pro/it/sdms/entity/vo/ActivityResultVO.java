package com.pro.it.sdms.entity.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@Builder(toBuilder=true)
public class ActivityResultVO {

    private BigDecimal id;

    private ActivityPracticeVO activityPracticeVO;

    private AccountVO accountVO;

    private String summary;

}
