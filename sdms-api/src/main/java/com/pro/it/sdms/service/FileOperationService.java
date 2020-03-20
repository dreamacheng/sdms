package com.pro.it.sdms.service;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface FileOperationService {


    String uploadFile(String objectType, BigDecimal id, MultipartFile file);

}
