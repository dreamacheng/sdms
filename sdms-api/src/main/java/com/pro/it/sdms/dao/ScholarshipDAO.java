package com.pro.it.sdms.dao;


import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.Scholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;

public interface ScholarshipDAO extends JpaSpecificationExecutor<Scholarship>, JpaRepository<Scholarship, BigDecimal> {

    Scholarship getByTermAndWinner(short term, Account winner);

    List<Scholarship> getAllByWinner(Account account);
}
