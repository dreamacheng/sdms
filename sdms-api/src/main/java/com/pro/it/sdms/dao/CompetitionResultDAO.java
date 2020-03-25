package com.pro.it.sdms.dao;

import com.pro.it.sdms.entity.dto.CompetitionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CompetitionResultDAO extends JpaSpecificationExecutor<CompetitionResult>, JpaRepository<CompetitionResult, BigDecimal> {
}
