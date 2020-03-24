package com.pro.it.sdms.entity.vo;


import com.pro.it.sdms.entity.dto.Punishment;
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
    private Date punishmentTime;

    /** 取消时间 */
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
                .studentNo(getPunishmentNo())
                .studentName(getPunishmentName())
                .cancelTime(getCancelTime())
                .punishmentTime(getPunishmentTime())
                .desc(getDesc())
                .isCancel((short) 0)
                .type(getType()).build();
    }

}