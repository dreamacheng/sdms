package com.pro.it.sdms.dao;

import com.pro.it.sdms.entity.dto.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ClubMemberDAO extends JpaSpecificationExecutor<ClubMember>, JpaRepository<ClubMember, BigDecimal> {
}
