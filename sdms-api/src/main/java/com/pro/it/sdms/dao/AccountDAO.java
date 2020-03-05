package com.pro.it.sdms.dao;

import com.pro.it.sdms.entity.dto.Account;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountDAO extends JpaSpecificationExecutor<Account>, JpaRepository<Account, BigDecimal> {

    Account getAccountByAccountNo(String accountNo);

    Account getAccountByAccountNoAndPassword(String accountNo, String password);

    List<Account> getAccountsByIdentityCard(String identityCard);

    List<Account> getAccountsByAccountNo(String accountNo);

}
