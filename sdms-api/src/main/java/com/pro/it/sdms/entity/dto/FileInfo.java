package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "sdms_file_info")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
public class FileInfo extends BaseDTO {

    /** 文件名 */
    @Column(name = "file_name", nullable = false, columnDefinition = "varchar(100)")
    private String fileName;

    /** 文件关联对象类型 */
    @Column(name = "object_type", nullable = false, columnDefinition = "varchar(100)")
    private String objectType;

    /** 文件关系对象Id */
    @Column(name = "object_id", nullable = false, columnDefinition = "int")
    private BigDecimal objectId;

    /** 文件路径 */
    @Column(name = "file_path", nullable = false, columnDefinition = "varchar(100)")
    private String filePath;

}
