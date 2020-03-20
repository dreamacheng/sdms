package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.BadRequestException;
import com.pro.it.sdms.dao.FileInfoDAO;
import com.pro.it.sdms.entity.dto.FileInfo;
import com.pro.it.sdms.service.FileOperationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
public class FileOperationServiceImpl implements FileOperationService {

    @Autowired
    private FileInfoDAO fileInfoDAO;

    @Value("${file.upload.filePath}")
    private String FILE_REPO;

    @Override
    public String uploadFile(String objectType, BigDecimal id, MultipartFile file) {
        FileInfo fileInfo = new FileInfo();
        if (StringUtils.isEmpty(objectType) || id == null || file == null) {
            throw new BadRequestException(Constants.Code.PARAM_REQUIRED, "upload parameter miss");
        }
        fileInfo.setObjectId(id);
        fileInfo.setObjectType(objectType);

        String fullName = file.getOriginalFilename();
        fileInfo.setFileName(fullName);
        // 以文件关联对象类型作为文件夹区分
        String accountNo = SecurityContextHolder.getContext().getAuthentication().getName();
        if(fullName.contains(".")){
            fullName = UUID.randomUUID().toString().replaceAll("-", "")
                    + fullName.substring(fullName.lastIndexOf("."));
        }
        String filePath = FILE_REPO + File.separator + objectType;
        createFile(filePath);
        // 文件位置
        String fileURL = filePath + fullName;
        File uploadFile = new File(fileURL);
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            log.error( "File \"{}\" upload failed. Cause:{}", file.getOriginalFilename(), e.getMessage() );
            throw new RuntimeException( e.getMessage() );
        }
        fileInfo.setFilePath(fileURL);
        fileInfoDAO.save(fileInfo);
        return fileURL;
    }

    /**
     * 创建文件夹
     * @param path 路径
     */
    private void createFile( String path ) {
        final File file = new File( path );
        if ( !file.exists() ) {
            synchronized ( this ) {
                if ( !file.mkdirs() ) {
                    if ( !file.isDirectory() ) {
                        throw new RuntimeException( String.format( "Failed to create file directory %s", path ) );
                    }
                }
            }
        }
    }
}
