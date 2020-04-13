package com.pro.it.sdms.service;

import com.pro.it.sdms.controller.request.CreateCertificateRequestEntity;
import com.pro.it.sdms.entity.vo.CertificateVO;

import java.util.List;

public interface CertificateService {

    List<CertificateVO> getCurCertificate(String accountNo);

    String certificateAdd(CreateCertificateRequestEntity createCertificateRequestEntity);
}
