package com.tju.carknowledge.Controller;

import com.tju.carknowledge.domain.*;
import com.tju.carknowledge.service.IndustryService;
import com.tju.carknowledge.service.NewsService;
import com.tju.carknowledge.service.RegService;
import com.tju.carknowledge.utils.EsUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName NewsController
 * @Description 新闻文章页面
 * @Author Yuan Yunxin
 * @Data 2020/7/2 20:32
 * @Version 1.0
 **/

@RestController
@RequestMapping(path = "/paper")
public class NewsController {
    /**
     * @Description 搜索框
     * 1.0:搜索关键词返回查询的新闻通知信息
     * 2.0:返回文章详情页信息
     **/
    @RequestMapping(path = "/search/news",  method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<List<Map<String, String>>> newsInfoSearchList(UserBean userBean) throws Exception {
        // 获取文章列表
        System.out.println("搜索框 newsInfoSearchList is ok");
        // 获取请求体
        String type = userBean.getType();
        String value = userBean.getValue();
        int page = userBean.getPage();

        NewsService newsService = new NewsService();

        RegService regService = new RegService();
        String title = new String();
        List<Map<String, String>> titleInfo;
        List<EsStandardBean> esStandardInfoList = regService.StandardInfoSearch(value, page);
        if (esStandardInfoList.isEmpty()){
            titleInfo = newsService.newsPaperSearch(type, value, page);
        }else{
            for (EsStandardBean esStandardInfo1 : esStandardInfoList){
                title = esStandardInfo1.getTitle();
                break;
            }

            if (title.contains(value)){
                titleInfo = newsService.newsPaperSearch(type, value, page);
            }else{
                titleInfo = newsService.newsPaperSearch(type, title, page);
            }
        }

        return RetResponse.makeOKRsp(titleInfo);
    }


    @RequestMapping(path = "/search/news/detail",method = RequestMethod.POST)
    // application/x-www-form-urlencoded类型
    public RetResult<List<EsIndustryBean>> newsDetailInfoSearch(UserBean userBean) throws Exception {
        // 获取详情页
        System.out.println("搜索框 newsDetailInfoSearch is ok");

        // 获取请求体
        String type = userBean.getType();
        String uuid = userBean.getUuid();

        NewsService newsService = new NewsService();
        List<EsIndustryBean> paperDetailInfo = newsService.newsPaperDetailSearch(type, uuid);
        return RetResponse.makeOKRsp(paperDetailInfo);
    }

    /**
     * @Description 导航框
     * 1.0:返回查询的所有新闻通知信息
     **/
    @RequestMapping(path = "/news",  method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<List<Map<String, String>>> AllNewsInfoSearchList(UserBean userBean) throws Exception {
        // 获取文章列表
        System.out.println("导航框 AllNewsInfoSearchList is ok");
        // 获取请求体
        int page = userBean.getPage();

        NewsService newsService = new NewsService();
        List<Map<String, String>> titleInfo = newsService.allNewsInfo(page);
        return RetResponse.makeOKRsp(titleInfo);
    }
}
