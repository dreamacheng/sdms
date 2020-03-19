package com.pro.it.sdms.controller;

import com.pro.it.sdms.entity.dto.OrganizationApply;
import com.pro.it.sdms.service.OrganizationApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrganizationApplyController {

    @Autowired
    private OrganizationApplyService organizationApplyService;

    private class URL {
        private static final String APPLY_MENTION = "/apply/add";
    }


}
