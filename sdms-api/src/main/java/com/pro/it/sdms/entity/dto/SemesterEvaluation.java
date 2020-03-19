package com.pro.it.sdms.entity.dto;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.vo.SemesterEvaluationVO;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.SemesterEnum;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "sdms_semester_evaluation")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SemesterEvaluation extends BaseDTO {

    /** 所属学生 */
    private String studentNo;

    /** 所属学生姓名 */
    private String studentName;

    /** 自我评价 */
    private String selfEval;

    /** 教师评价 */
    private String teacherEval;

    /** 所属学期 */
    private Short semester;

    /** 评价老师 */
    private String teacherNo;

    /** 评价老师姓名 */
    private String teacherName;

    /** 评价等级 */
    private String grade;

    public SemesterEvaluationVO toVO() {
        return SemesterEvaluationVO.builder()
                .grade(getGrade())
                .semester(BaseCodeEnum.codeOf(SemesterEnum.class, getSemester()).toString())
                .teacherNo(getTeacherNo())
                .studentNo(getStudentNo())
                .studentName(getStudentName())
                .teacherName(getTeacherName())
                .selfEval(getSelfEval())
                .teacherEval(getTeacherEval()).build();
    }
}
