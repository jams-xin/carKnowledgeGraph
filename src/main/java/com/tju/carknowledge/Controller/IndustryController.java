package com.tju.carknowledge.Controller;

import com.alibaba.fastjson.JSONObject;
import com.tju.carknowledge.domain.*;
import com.tju.carknowledge.service.IndustryService;
import com.tju.carknowledge.service.RegService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName IndustryController
 * @Description 返回产业资讯信息
 * @Author Yuan Yunxin
 * @Data 2020/6/28 17:44
 * @Version 1.0
 **/

@RestController
@RequestMapping(path = "/paper")
public class IndustryController {
    IndustryService industryService = new IndustryService();

    @RequestMapping(path = "/search/information",  method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<List<Map<String, String>>> IndustryInfoSearchList(UserBean userBean) throws Exception {
        /**
         * @Description 搜索框
         * 1.0:搜索关键词返回查询的产业资讯信息
         **/
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        System.out.println(formatter.format(date) + " IndustryInfoSearchList is ok");
        // 获取请求体
        String type = userBean.getType();
        String value = userBean.getValue();
        int page = userBean.getPage();

        List<Map<String, String>> titleInfo = industryService.CarIndustryInfo(type, value, page);
        return RetResponse.makeOKRsp(titleInfo);
    }


    @RequestMapping(path = "/search/information/detail",method = RequestMethod.POST)
    // application/x-www-form-urlencoded类型
    public RetResult<List<EsIndustryBean>> IndustryDetailInfoSearch(UserBean userBean) throws Exception {
        /**
         * @Description 搜索框
         * 2.0:返回文章详情页信息
         **/
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        System.out.println(formatter.format(date) + " IndustryDetailInfoSearch is ok");
        // 获取请求体
        String type = userBean.getType();
        String uuid = userBean.getUuid();

        List<EsIndustryBean> paperDetailInfo = industryService.industryDetailInfo(type, uuid);
        return RetResponse.makeOKRsp(paperDetailInfo);
    }


    /**
     * @Description 导航框
     * 1.0:返回查询的所有产业资讯信息
     **/
    @RequestMapping(path = "/information",  method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<List<Map<String, String>>> AllIndustryInfoSearchList(UserBean userBean) throws Exception {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        System.out.println(formatter.format(date) + " AllIndustryInfoSearchList is ok");
        // 获取请求体
        int page = userBean.getPage();

        List<Map<String, String>> titleInfo = industryService.allIndustryInfo(page);
        return RetResponse.makeOKRsp(titleInfo);
    }



    /**
     * 判断字符串是否可以转化为json对象
     * @param content
     * @return
     */
    public static boolean isJsonObject(String content) {
        // 此处应该注意，不要使用StringUtils.isEmpty(),因为当content为"  "空格字符串时，JSONObject.parseObject可以解析成功，
        // 实际上，这是没有什么意义的。所以content应该是非空白字符串且不为空，判断是否是JSON数组也是相同的情况。
        if(StringUtils.isBlank(content))
            return false;
        try {
            JSONObject jsonStr = JSONObject.parseObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
