package com.pro.it.sdms.entity.vo;

import com.pro.it.sdms.entity.BaseDTO;
import com.pro.it.sdms.entity.dto.SemesterEvaluation;
import com.pro.it.sdms.enums.BaseCodeEnum;
import com.pro.it.sdms.enums.SemesterEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@Builder(toBuilder=true)
public class SemesterEvaluationVO {

    /** id */
    private BigDecimal id;

    /** 所属学生 */
    private String studentNo;

    /** 所属学生姓名 */
    private String studentName;

    /** 自我评价 */
    private String selfEval;

    /** 教师评价 */
    private String teacherEval;

    /** 所属学期 */
    private String semester;

    /** 评价老师 */
    private String teacherNo;

    /** 评价老师姓名 */
    private String teacherName;

    /** 评价等级 */
    private String grade;

    public SemesterEvaluation toDTO() {
        return SemesterEvaluation.builder()
                .studentNo(getStudentNo())
                .teacherNo(getStudentNo())
                .studentName(getStudentName())
                .teacherName(getTeacherName())
                .grade(getGrade())
                .semester(SemesterEnum.valueOf(getSemester()).getCode())
                .grade(getGrade())
                .selfEval(getSelfEval()).build();
    }
}
