package com.tju.carknowledge.Controller;

import com.tju.carknowledge.domain.*;
import com.tju.carknowledge.service.NewsService;
import com.tju.carknowledge.service.RegService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    NewsService newsService = new NewsService();

    @RequestMapping(path = "/search/news",  method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<List<Map<String, String>>> newsInfoSearchList(UserBean userBean) throws Exception {
        /**
         * @Description 搜索框
         * 1.0:搜索关键词返回查询的新闻通知信息
         **/
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        System.out.println(formatter.format(date) + " newsInfoSearchList is ok");
        // 获取请求体
        String type = userBean.getType();
        String value = userBean.getValue();
        int page = userBean.getPage();

        List<Map<String, String>> titleInfo = newsService.CarNewsPaper(type, value, page);

        return RetResponse.makeOKRsp(titleInfo);
    }


    @RequestMapping(path = "/search/news/detail",method = RequestMethod.POST)
    // application/x-www-form-urlencoded类型
    public RetResult<List<EsIndustryBean>> newsDetailInfoSearch(UserBean userBean) throws Exception {
        /**
         * @Description 搜索框
         * 2.0:返回文章详情页信息
         **/
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        System.out.println(formatter.format(date) + " newsDetailInfoSearch is ok");
        // 获取请求体
        String type = userBean.getType();
        String uuid = userBean.getUuid();

        List<EsIndustryBean> paperDetailInfo = newsService.newsPaperDetailSearch(type, uuid);
        return RetResponse.makeOKRsp(paperDetailInfo);
    }


    @RequestMapping(path = "/news",  method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<List<Map<String, String>>> AllNewsInfoSearchList(UserBean userBean) throws Exception {
        /**
         * @Description 导航框
         * 1.0:返回查询的所有新闻通知信息
         **/
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        System.out.println(formatter.format(date) + " AllNewsInfoSearchList is ok");
        // 获取请求体
        int page = userBean.getPage();

        List<Map<String, String>> titleInfo = newsService.allNewsInfo(page);
        return RetResponse.makeOKRsp(titleInfo);
    }
}
