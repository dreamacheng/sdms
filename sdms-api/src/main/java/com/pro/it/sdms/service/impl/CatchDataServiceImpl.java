package com.pro.it.sdms.service.impl;

import com.pro.it.common.Constants;
import com.pro.it.common.exceptions.AuthenticationException;
import com.pro.it.sdms.controller.response.IndexListResponseEntity;
import com.pro.it.sdms.entity.vo.IndexDataVO;
import com.pro.it.sdms.service.CatchDataService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CatchDataServiceImpl implements CatchDataService {

    @Value("${data.school.prefix}")
    private String schoolURLPrefix;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static final String INDEX_CACHE = "index";

    public static final Function indexHandler =  o -> {
        Element item = (Element) o;
        String date = item.select("span").first().text();
        String link = item.select("a").first().attr("href");
        String title = item.select("a").text();
        return IndexDataVO.builder().title(title).date(date).link(link).build();
    };

    @Override
    public IndexListResponseEntity catchData() {
        try {
            IndexListResponseEntity indexListResponseEntity;
            if (redisTemplate.opsForHash().hasKey(INDEX_CACHE, INDEX_CACHE)) {
                indexListResponseEntity = (IndexListResponseEntity) redisTemplate.opsForHash().get(INDEX_CACHE, INDEX_CACHE);
                log.info("缓存命中：[{}]", INDEX_CACHE);
            } else {
                Document doc = Jsoup.connect(schoolURLPrefix).get();
                Elements list = doc.getElementsByClass("clyw_list_y fr");
                Elements uls = list.get(1).select("li");
                Elements uls1 = list.get(0).select("li");
                indexListResponseEntity = IndexListResponseEntity.builder()
                        .schoolNews((List<IndexDataVO>) uls1.stream().map(indexHandler).collect(Collectors.toList()))
                        .notices((List<IndexDataVO>) uls1.stream().map(indexHandler).collect(Collectors.toList()))
                        .mediaCollege((List<IndexDataVO>) uls1.stream().map(indexHandler).collect(Collectors.toList())).build();
                if (indexListResponseEntity == null) {
                    throw new AuthenticationException(Constants.Code.REMOTE_INVOKING_ERROR, "remote invoking error");
                }
                redisTemplate.opsForHash().put(INDEX_CACHE, INDEX_CACHE, indexListResponseEntity);
                redisTemplate.expire(INDEX_CACHE, 1, TimeUnit.DAYS);
            }

            return indexListResponseEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
