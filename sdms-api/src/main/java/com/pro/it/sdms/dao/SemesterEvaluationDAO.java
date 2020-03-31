package com.pro.it.sdms.dao;

import com.pro.it.sdms.entity.dto.SemesterEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SemesterEvaluationDAO extends JpaSpecificationExecutor<SemesterEvaluation>, JpaRepository<SemesterEvaluation, BigDecimal> {

    List<SemesterEvaluation> findAllByStudentNo(String studentNo);

    SemesterEvaluation findByStudentNoAndSemester(String studentNo, short semester);

    List<SemesterEvaluation> findAllByGradeIsNull();

    List<SemesterEvaluation> findAllByGradeIsNotNull();

}
