package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "sdms_register_code")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class RegisterCode extends BaseDTO {

    @Column(name = "code", nullable = false, columnDefinition = "varchar(10)")
    /** 注册邀请码 */
    private String code;

    @Column(name = "available", nullable = false, columnDefinition = "int")
    /**
     * 是否有效
     * 1 有效  2 失效
     * */
    private Short available;

}
