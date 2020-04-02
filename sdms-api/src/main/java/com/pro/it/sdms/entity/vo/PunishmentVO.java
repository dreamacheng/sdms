package com.pro.it.sdms.entity.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.it.sdms.entity.dto.Punishment;
import com.pro.it.sdms.enums.PunishmentTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Date;

@ToString
@Getter
@Setter
@Builder(toBuilder=true)
public class PunishmentVO {

    /** 处分类型 */
    private String type;

    /** 处分时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT")
    private Date punishmentTime;

    /** 取消时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT")
    private Date cancelTime;

    /** 是否取消 */
    private Short isCancel;

    /** 受处分人学号 */
    private String punishmentNo;

    /** 受处分人姓名 */
    private String punishmentName;

    /** 处分原因及描述 */
    private String desc;


    public Punishment toDTO() {
        return Punishment.builder()
                .cancelTime(getCancelTime())
                .punishmentTime(getPunishmentTime())
                .desc(getDesc())
                .isCancel((short) 0)
                .type(PunishmentTypeEnum.valueOf(getType()).getCode()).build();
    }

}
