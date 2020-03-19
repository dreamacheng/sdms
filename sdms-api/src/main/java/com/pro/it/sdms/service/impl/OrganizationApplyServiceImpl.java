package com.pro.it.sdms.service.impl;

import com.pro.it.sdms.dao.OrganizationApplyDAO;
import com.pro.it.sdms.entity.dto.OrganizationApply;
import com.pro.it.sdms.entity.vo.OrganizationApplyVO;
import com.pro.it.sdms.service.OrganizationApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationApplyServiceImpl implements OrganizationApplyService {

    @Autowired
    private OrganizationApplyDAO organizationApplyDAO;

    @Override
    public String addApply(OrganizationApplyVO vo) {
        return null;
    }

}
