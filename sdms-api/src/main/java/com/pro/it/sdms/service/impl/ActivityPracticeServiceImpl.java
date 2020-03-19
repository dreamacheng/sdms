package com.pro.it.sdms.service.impl;

import com.pro.it.sdms.dao.ActivityPracticeDAO;
import com.pro.it.sdms.entity.dto.ActivityPractice;
import com.pro.it.sdms.entity.vo.ActivityPracticeVO;
import com.pro.it.sdms.service.ActivityPracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityPracticeServiceImpl implements ActivityPracticeService {

    @Autowired
    private ActivityPracticeDAO activityPracticeDAO;

    @Override
    public String activityAdd(ActivityPracticeVO vo) {
        return null;
    }
}
