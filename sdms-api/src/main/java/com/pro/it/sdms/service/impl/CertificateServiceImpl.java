package com.pro.it.sdms.service.impl;

import com.pro.it.sdms.dao.CertificateDAO;
import com.pro.it.sdms.entity.vo.CertificateVO;
import com.pro.it.sdms.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private CertificateDAO certificateDAO;

    @Override
    public List<CertificateVO> getCurCertificate() {
        String accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        return null;
    }

}
