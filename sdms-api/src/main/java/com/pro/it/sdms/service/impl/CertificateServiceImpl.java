package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.controller.request.CreateCertificateRequestEntity;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.dao.CertificateDAO;
import com.pro.it.sdms.entity.dto.Account;
import com.pro.it.sdms.entity.dto.Certificate;
import com.pro.it.sdms.entity.vo.CertificateVO;
import com.pro.it.sdms.service.CertificateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private CertificateDAO certificateDAO;

    @Autowired
    private AccountDAO accountDAO;

    /**
     * 获取当前用户证书列表
     * @return
     */
    @Override
    public List<CertificateVO> getCurCertificate(String accountNo) {
        if (StringUtils.isEmpty(accountNo)) {
            accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        Account accountByAccountNo = accountDAO.getAccountByAccountNo(accountNo);
        return certificateDAO.getAllByAccount(accountByAccountNo).stream()
                .map(Certificate::toVO).collect(Collectors.toList());
    }

    /**
     * 添加证书
     * @param createCertificateRequestEntity
     * @return
     */
    @Override
    public String certificateAdd(CreateCertificateRequestEntity createCertificateRequestEntity) {
        if (createCertificateRequestEntity == null
                || StringUtils.isEmpty(createCertificateRequestEntity.getGrade())
                || StringUtils.isEmpty(createCertificateRequestEntity.getName())) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "parameter required");
        }
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountDAO.getAccountByAccountNo(name);
        Certificate build = Certificate.builder()
                .name(createCertificateRequestEntity.getName())
                .grade(createCertificateRequestEntity.getGrade())
                .account(account).build();
        return certificateDAO.save(build).getName();
    }

}
