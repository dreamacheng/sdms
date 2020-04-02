package com.pro.it.sdms.dao;

import com.pro.it.sdms.entity.dto.ClubApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ClubApplyDAO extends JpaSpecificationExecutor<ClubApply>, JpaRepository<ClubApply, BigDecimal> {

    List<ClubApply> findAllByApplyStatusEquals(short applyStatus);

    List<ClubApply> findAllByApplyStatusIsNot(short applyStatus);

    List<ClubApply> findAllByCreateUser(String accountNo);
}
