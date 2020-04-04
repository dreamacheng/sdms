package com.pro.it.sdms.dao;

import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.ActivityPractice;
import com.pro.it.sdms.entity.dto.ActivityResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ActivityResultDAO extends JpaSpecificationExecutor<ActivityResult>, JpaRepository<ActivityResult, BigDecimal> {

    List<ActivityResult> findAllByMember(Account member);

}
