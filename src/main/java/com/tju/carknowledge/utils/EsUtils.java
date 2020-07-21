package com.tju.carknowledge.utils;


import com.tju.carknowledge.domain.EsIndustryBean;
import com.tju.carknowledge.service.PicService;
import org.elasticsearch.search.SearchHit;
import java.util.*;


/**
 * @ClassName EsUtils
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/6/28 16:41
 * @Version 1.0
 * 可删除
 **/

public class EsUtils {

    public List<Map<String, String>> paperSearch(String type, String value, int page) throws Exception {

        EsBuilderUtils esBuilderUtils = new EsBuilderUtils();
        List<Map<String, String>> industryInfoList = new ArrayList();

//        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder("yx_qcarticle","yx_qcarticle", type, value, "None", page); // uuid 默认为空
        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder("yx_qcarticle01","yx_qcarticle01", type, value, "None", page); // uuid 默认为空

        for (SearchHit hit : searchHits) {
            Map<String, String> infoMap = new HashMap<>();

            //取_source字段值
            String sourceAsString = hit.getSourceAsString(); //取成json串
            Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象

            String uuid = String.valueOf(sourceAsMap.get("articleID"));
            String title = String.valueOf(sourceAsMap.get("title"));
            infoMap.put("uuid", uuid);
            infoMap.put("title", title);
            industryInfoList.add(infoMap);

        }
        return industryInfoList;
    }

    public List<EsIndustryBean> searchDetail(String type, String uuid) throws Exception {

        EsBuilderUtils esBuilderUtils = new EsBuilderUtils();
        List<EsIndustryBean> esIndustryBeans = new ArrayList();
        EsIndustryBean esIndustryBean = new EsIndustryBean();
        PicService picService = new PicService();
//        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder("yx_qcarticle","yx_qcarticle", type, "None", uuid, 0);  // value  page 不使用默认
        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder("yx_qcarticle01","yx_qcarticle01", type, "None", uuid, 0);  // value  page 不使用默认

        for (SearchHit hit : searchHits) {

            //取_source字段值
            Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象

            String content = String.valueOf(sourceAsMap.get("content"));
            String articleUrl = String.valueOf(sourceAsMap.get("articleURL"));
            String origin = String.valueOf(sourceAsMap.get("origin"));
            String originAuthor = String.valueOf(sourceAsMap.get("originAuthor"));
            String publishTime = String.valueOf(sourceAsMap.get("pubishTime"));
//            String absPicURL = String.valueOf(sourceAsMap.get("absPicURL"));



            List<String> picList = picService.getPicList(uuid);  // 从本地获取图片url

            esIndustryBean.setContent(content);
            esIndustryBean.setArticleUrl(articleUrl);
            esIndustryBean.setOrigin(origin);
            esIndustryBean.setOriginAuthor(originAuthor);
            esIndustryBean.setPublishTime(publishTime);


//            Object picList = sourceAsMap.get("absPicURL");
//            getpicURL(picList,uuid);
//            esIndustryBean.setPictureUrl(absPicURL);
//            System.out.println(picList);

            if (picList != null) {
                esIndustryBean.setPictureUrl(picList.get(0));
            }else{
                esIndustryBean.setPictureUrl(null);
            }

            esIndustryBeans.add(esIndustryBean);

        }
        return esIndustryBeans;
    }

    public String getpicURL(List<String> picList, String uuid){
        String newpicURL = null;
//        boolean flag = false;
        String s = "/pdf/879af936-b5c3-11ea-9066-001a7dda7111/20100423204040750207.jpg";
        System.out.println(s.split("/")[1]);
        for (String pic : picList) {
            if (pic.endsWith(".jpg") && (pic.split("/")[1] == uuid)) {
                newpicURL = pic;
                break;
            } else {
                continue;
            }
        }
        return newpicURL;
    }
}