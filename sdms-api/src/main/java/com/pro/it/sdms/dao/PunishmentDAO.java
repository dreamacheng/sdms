package com.pro.it.sdms.dao;

import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.Punishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PunishmentDAO extends JpaSpecificationExecutor<Punishment>, JpaRepository<Punishment, BigDecimal>{

    List<Punishment> findAllByStudent(Account account);

}
