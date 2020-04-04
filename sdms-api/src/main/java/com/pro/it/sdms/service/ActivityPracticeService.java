package com.pro.it.sdms.service;

import com.pro.it.sdms.entity.dto.ActivityPractice;
import com.pro.it.sdms.entity.vo.ActivityPracticeVO;
import com.pro.it.sdms.entity.vo.ActivityResultVO;

import java.math.BigDecimal;
import java.util.List;

public interface ActivityPracticeService {

    String activityAdd(ActivityPracticeVO vo);

    List<ActivityPracticeVO> queryAll();

    ActivityPracticeVO query(BigDecimal id);

    String join(BigDecimal activityId);

    List<ActivityResultVO> queryJoin();

    String summary(BigDecimal activityResultId, String summary);
}
