package com.tju.carknowledge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tju.carknowledge.Config;
import com.tju.carknowledge.domain.EsIndustryBean;
import com.tju.carknowledge.utils.EsBuilderUtils;
import org.elasticsearch.search.SearchHit;

import java.util.*;

/**
 * @ClassName NewsService
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/7/2 20:17
 * @Version 1.0
 **/
public class NewsService {
    private Config config = new Config();

    public List<Map<String, String>> newsPaperSearch(String type, String value, int page) throws Exception {
        // 获取新闻文章列表(uuid和title)
        EsBuilderUtils esBuilderUtils = new EsBuilderUtils();
        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder(config.INDEX, config.TYPE, type, value, "None", page); // uuid 默认为空
        List<Map<String, String>> newsInfoList = new ArrayList();

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
                String articleUrl = String.valueOf(sourceAsMap.get("articleURL"));
                String origin = String.valueOf(sourceAsMap.get("origin"));
                String originAuthor = String.valueOf(sourceAsMap.get("originAuthor"));
                String pubishTime = String.valueOf(sourceAsMap.get("pubishTime"));

                infoMap.put("uuid", uuid);
                infoMap.put("title", title);
                infoMap.put("articleUrl", articleUrl);
                infoMap.put("origin", origin);
                infoMap.put("originAuthor", originAuthor);
                infoMap.put("pubishTime", pubishTime);

                newsInfoList.add(infoMap);
            }
        }
        return newsInfoList;
    }

    public List<EsIndustryBean> newsPaperDetailSearch(String type, String uuid) throws Exception {
        //获取新闻通知文章详情页

        List<EsIndustryBean> esIndustryBeans = new ArrayList();
        EsBuilderUtils esBuilderUtils = new EsBuilderUtils();
        EsIndustryBean esIndustryBean = new EsIndustryBean();
        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder(config.INDEX, config.TYPE, type, "None", uuid, 0);  // value  page 不使用默认

        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象

            String articleUrl = String.valueOf(sourceAsMap.get("articleURL"));
            String content = String.valueOf(sourceAsMap.get("content"));
            String html = String.valueOf(sourceAsMap.get("HTML"));
            String origin = String.valueOf(sourceAsMap.get("origin"));
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

            // picURL需要判断
            List<String> absPicURList = (List<String>) sourceAsMap.get("absPicURL"); //
            String absPicURL = null;
            if ((absPicURList == null)){
                System.out.println("pic is null");
            }else{
                for (String pic : absPicURList) {
                    if ((pic.endsWith(".jpg") == true) && (pic.split("/")[2].equals(uuid))) {
                        absPicURL = pic;
                        break;
                    } else {
                        continue;
                    }
                }
            }

            esIndustryBean.setContent(content);
            esIndustryBean.setHtml(html);
            esIndustryBean.setArticleUrl(articleUrl);
            esIndustryBean.setOrigin(origin);
            esIndustryBean.setOriginAuthor(originAuthor);
            esIndustryBean.setPublishTime(publishTime);
            esIndustryBean.setPictureUrl(absPicURL); //测试完删除

            esIndustryBean.setAbsPictureUrl(absPictureUrl);

            esIndustryBeans.add(esIndustryBean);

        }
        return esIndustryBeans;
    }

    /**
     * @Description 导航框
     * @function 所有新闻通知
     * @function allNewsInfo()
     **/
    public List<Map<String, String>> allNewsInfo(int page) throws Exception {
        // 获取新闻通知文章列表
        List<Map<String, String>> allNewsInfoList = new ArrayList();
        EsBuilderUtils esBuilderUtils = new EsBuilderUtils();
        // 后期需要更改type值
        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder("yx_qcarticle","yx_qcarticle", "3", "None", "None", page); // uuid 默认为空

        for (SearchHit hit : searchHits) {

            Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象
            Map<String, String> oneinfoMap = new HashMap<>();

            String uuid = String.valueOf(sourceAsMap.get("articleID"));
            String title = String.valueOf(sourceAsMap.get("title"));
            String origin = String.valueOf(sourceAsMap.get("origin"));
            String articleUrl = String.valueOf(sourceAsMap.get("articleURL"));
            String originAuthor = String.valueOf(sourceAsMap.get("originAuthor"));
            String publishTime = String.valueOf(sourceAsMap.get("pubishTime"));

            oneinfoMap.put("uuid", uuid);
            oneinfoMap.put("title", title);
            oneinfoMap.put("origin", origin);
            oneinfoMap.put("articleUrl", articleUrl);
            oneinfoMap.put("originAuthor", originAuthor);
            oneinfoMap.put("publishTime", publishTime);
            allNewsInfoList.add(oneinfoMap);
        }

        return allNewsInfoList;
    }
}
