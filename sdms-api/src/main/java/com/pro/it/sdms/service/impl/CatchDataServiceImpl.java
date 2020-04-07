package com.pro.it.sdms.service.impl;

import com.pro.it.sdms.entity.vo.IndexDataVO;
import com.pro.it.sdms.service.CatchDataService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CatchDataServiceImpl implements CatchDataService {



    @Override
    public List<IndexDataVO> catchData() {

        try {
            Document doc = Jsoup.connect("http://www.hnit.edu.cn/").get();
            System.out.println(doc.title());
            Elements list = doc.getElementsByClass("list");
            Elements uls = list.select("li");
            List<IndexDataVO> voList = new ArrayList<>();
            for(Element item : uls) {
                String date = item.select("span").first().text();
                String title = item.select("a").first().attr("title");
                String link = item.select("a").first().attr("href");
                voList.add(IndexDataVO.builder().title(title).date(date).link(link).build());
            }
            Elements list2 = doc.getElementsByClass("list2");
            Elements uls2 = list2.select("li");
            for(Element item : uls2) {
                String date = item.select("span").first().text();
                String title = item.select("a").first().attr("title");
                String link = item.select("a").first().attr("href");
                voList.add(IndexDataVO.builder().title(title).date(date).link(link).build());
            }
            Elements list3 = doc.getElementsByClass("list3");
            Elements uls3 = list3.select("li");
            for(Element item : uls3) {
                String date = item.select("span").first().text();
                String title = item.select("a").first().attr("title");
                String link = item.select("a").first().attr("href");
                voList.add(IndexDataVO.builder().title(title).date(date).link(link).build());
            }
            return voList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
