package com.pro.it.sdms.dao;

import com.pro.it.sdms.entity.dto.OrganizationApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrganizationApplyDAO extends JpaSpecificationExecutor<OrganizationApply>, JpaRepository<OrganizationApply, BigDecimal> {

    OrganizationApply findByTypeAndProposer(short type, String proposer);

    List<OrganizationApply> findByApproverAndApplyStatus(String approver, short applyStatus);

}
