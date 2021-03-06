package com.pro.it.sdms.dao;

import com.pro.it.sdms.entity.dto.SchoolClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SchoolClubDAO extends JpaSpecificationExecutor<SchoolClub>, JpaRepository<SchoolClub, BigDecimal> {

}
