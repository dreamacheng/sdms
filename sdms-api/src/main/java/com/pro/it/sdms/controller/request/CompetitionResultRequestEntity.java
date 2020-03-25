package com.pro.it.sdms.controller.request;

import com.pro.it.sdms.entity.dto.CompetitionResult;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionResultRequestEntity {

    /** 比赛ID */
    private BigDecimal competitionId;

    /** 获奖人学号 */
    private String accountNo;

    /** 获奖人姓名 */
    private String accountName;

    /** 比赛名称 */
    private String competitionName;

    /** 比赛描述 */
    private String type;

    /** 比赛成绩 */
    private String grade;

    public CompetitionResult toDTO() {
        return CompetitionResult.builder()
                .accountName(getAccountName())
                .competitionName(getCompetitionName())
                .grade(getGrade())
                .type(getType())
                .accountNo(getAccountNo()).build();
    }
}
