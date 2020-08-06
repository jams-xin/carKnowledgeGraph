package com.tju.carknowledge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tju.carknowledge.Config;
import com.tju.carknowledge.domain.EsIndustryBean;
import com.tju.carknowledge.domain.EsStandardBean;
import com.tju.carknowledge.utils.EsBuilderUtils;
import org.elasticsearch.search.SearchHit;

import java.util.*;

/**
 * @ClassName IndustryService
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/7/2 10:12
 * @Version 1.0
 **/
public class IndustryService {

    private Config config = new Config();


    /**
     * @Description 搜索框
     * @function 搜索关键词返回查询的产业资讯信息
     * @value String type, String value, int page
     **/
    public List<Map<String, String>> industryInfo(String type, String value, int page) throws Exception {
        // 获取产业资讯文章列表

        List<Map<String, String>> industryInfoList = new ArrayList();
        EsBuilderUtils esBuilderUtils = new EsBuilderUtils();
//        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder("yx_qcarticle","yx_qcarticle", type, value, "None", page); // uuid 默认为空
        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder(config.INDEX, config.TYPE, type, value, "None", page); // uuid 默认为空

        Set<String> flagset = new HashSet<String>();

        for (SearchHit hit : searchHits) {
            Map<String, String> infoMap = new HashMap<>();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            String title = String.valueOf(sourceAsMap.get("title"));
            if (flagset.contains(title)){
                continue;
            }else {
                flagset.add(title);

                String uuid = String.valueOf(sourceAsMap.get("articleID"));
                String origin = String.valueOf(sourceAsMap.get("origin"));
                String articleUrl = String.valueOf(sourceAsMap.get("articleURL"));
                String originAuthor = String.valueOf(sourceAsMap.get("originAuthor"));
                String pubishTime = String.valueOf(sourceAsMap.get("pubishTime"));
                infoMap.put("uuid", uuid);
                infoMap.put("title", title);
                infoMap.put("origin", origin);
                infoMap.put("articleUrl", articleUrl);
                infoMap.put("originAuthor", originAuthor);
                infoMap.put("pubishTime", pubishTime);
                industryInfoList.add(infoMap);
            }
        }

        return industryInfoList;
    }

    public  List<Map<String, String>> CarIndustryInfo(String type,String value,int page) throws Exception {
        //
        RegService regService = new RegService();
        String title = new String();
        List<Map<String, String>> titleInfo;

        List<EsStandardBean> esStandardInfoList = regService.StandardInfoSearch(value, page);
        if (esStandardInfoList.isEmpty()){
            titleInfo = industryInfo(type, value, page);
        }else{
            for (EsStandardBean esStandardInfo1 : esStandardInfoList){
                title = esStandardInfo1.getTitle();
                break;
            }
            if (title.contains(value)){
                titleInfo = industryInfo(type, value, page);
            }else{
                titleInfo = industryInfo(type, title, page);
            }
        }
        return titleInfo;
    }

    /**
     * @Description 搜索框
     * @function 产业资讯文章详情页
     * @value String type, String uuid
     **/
    public List<EsIndustryBean> industryDetailInfo(String type, String uuid) throws Exception {
        //获取产业资讯文章详情页
        List<EsIndustryBean> esIndustryBeans = new ArrayList();
        EsBuilderUtils esBuilderUtils = new EsBuilderUtils();
        EsIndustryBean esIndustryBean = new EsIndustryBean();
//        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder("yx_qcarticle","yx_qcarticle", type, "None", uuid, 0);  // value  page 不使用默认
        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder(config.INDEX, config.TYPE, type, "None", uuid, 0);  // value  page 不使用默认

        for (SearchHit hit : searchHits) {

            //取_source字段值
            Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象

            String content = String.valueOf(sourceAsMap.get("content"));
            String html = String.valueOf(sourceAsMap.get("HTML"));
            String origin = String.valueOf(sourceAsMap.get("origin"));
            String articleUrl = String.valueOf(sourceAsMap.get("articleURL"));
            String originAuthor = String.valueOf(sourceAsMap.get("originAuthor"));
            String publishTime = String.valueOf(sourceAsMap.get("pubishTime"));

            String absPicURL01 = String.valueOf(sourceAsMap.get("absPicURL"))
                    .replace("\"","")
                    .replace("[","")
                    .replace("]","")
                    .replace(" ","");

            List<String> absList = Arrays.asList(absPicURL01.split(","));
            List<Map<String ,String>> absPictureUrl = new ArrayList();
            for (String abs : absList){
                Map<String ,String> pic_map = new HashMap<>();   // 获取一条信息
                pic_map.put("absPicUrl", abs);
                absPictureUrl.add(pic_map);
            }

            esIndustryBean.setContent(content);
            esIndustryBean.setHtml(html);
            esIndustryBean.setArticleUrl(articleUrl);
            esIndustryBean.setOrigin(origin);
            esIndustryBean.setOriginAuthor(originAuthor);
            esIndustryBean.setPublishTime(publishTime);
            esIndustryBean.setAbsPictureUrl(absPictureUrl);

            esIndustryBeans.add(esIndustryBean);

        }
        return esIndustryBeans;
    }

    /**
     * @Description 导航框
     * @function 所有产业资讯信息
     * @value int page
     **/
    public List<Map<String, String>> allIndustryInfo(int page) throws Exception {
        // 获取产业资讯文章列表
        List<Map<String, String>> allIndustryInfoList = new ArrayList();
        EsBuilderUtils esBuilderUtils = new EsBuilderUtils();
        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder(config.INDEX, config.TYPE, "3", "None", "None", page); // uuid 默认为空

        for (SearchHit hit : searchHits) {

            Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象
            Map<String, String> oneinfoMap = new HashMap<>();

            String origin = String.valueOf(sourceAsMap.get("origin"));
            String articleUrl = String.valueOf(sourceAsMap.get("articleURL"));
            String originAuthor = String.valueOf(sourceAsMap.get("originAuthor"));
            String publishTime = String.valueOf(sourceAsMap.get("pubishTime"));
            String uuid = String.valueOf(sourceAsMap.get("articleID"));
            String title = String.valueOf(sourceAsMap.get("title"));

            oneinfoMap.put("uuid", uuid);
            oneinfoMap.put("title", title);
            oneinfoMap.put("origin", origin);
            oneinfoMap.put("articleUrl", articleUrl);
            oneinfoMap.put("originAuthor", originAuthor);
            oneinfoMap.put("publishTime", publishTime);
            allIndustryInfoList.add(oneinfoMap);
        }
        return allIndustryInfoList;
    }
}
