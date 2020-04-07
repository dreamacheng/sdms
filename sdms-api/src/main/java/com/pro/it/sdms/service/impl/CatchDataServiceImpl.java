package com.pro.it.sdms.service.impl;

import com.pro.it.sdms.controller.response.IndexListResponseEntity;
import com.pro.it.sdms.entity.vo.IndexDataVO;
import com.pro.it.sdms.service.CatchDataService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CatchDataServiceImpl implements CatchDataService {

    @Value("${data.school.prefix}")
    private String schoolURLPrefix;


    @Override
    public IndexListResponseEntity catchData() {
        try {
            Document doc = Jsoup.connect("http://www.hnit.edu.cn/").get();
            System.out.println(doc.title());
            Elements list = doc.getElementsByClass("list");
            Elements uls = list.select("li");
            Elements list2 = doc.getElementsByClass("list2");
            Elements uls2 = list2.select("li");
            Elements list3 = doc.getElementsByClass("list3");
            Elements uls3 = list3.select("li");
            IndexListResponseEntity indexListResponseEntity = IndexListResponseEntity.builder()
                    .schoolNews(uls.stream().map(item -> {
                            String date = item.select("span").first().text();
                            String title = item.select("a").first().attr("title");
                            String link = item.select("a").first().attr("href");
                            return IndexDataVO.builder().title(title).date(date).link(schoolURLPrefix + link).build();
                    }).collect(Collectors.toList()))
                    .notices(uls2.stream().map(item -> {
                        String date = item.select("span").first().text();
                        String title = item.select("a").first().attr("title");
                        String link = item.select("a").first().attr("href");
                        return IndexDataVO.builder().title(title).date(date).link(schoolURLPrefix + link).build();
                    }).collect(Collectors.toList()))
                    .mediaCollege(uls3.stream().map(item -> {
                        String date = item.select("span").first().text();
                        String title = item.select("a").first().attr("title");
                        String link = item.select("a").first().attr("href");
                        return IndexDataVO.builder().title(title).date(date).link(link).build();
                    }).collect(Collectors.toList())).build();
            return indexListResponseEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
