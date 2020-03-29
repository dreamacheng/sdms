package com.pro.it.sdms.dao;

import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.ClubMember;
import com.pro.it.sdms.entity.dto.SchoolClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ClubMemberDAO extends JpaSpecificationExecutor<ClubMember>, JpaRepository<ClubMember, BigDecimal> {

    List<ClubMember> getAllByMemberStatusAndMember(short memberStatus, Account member);

    List<ClubMember> getAllByMember(Account member);

    List<ClubMember> getAllByMemberStatusAndClub(short memberStatus, SchoolClub club);

    List<ClubMember> getAllByPositionIsNotAndMember(String position, Account member);

}
