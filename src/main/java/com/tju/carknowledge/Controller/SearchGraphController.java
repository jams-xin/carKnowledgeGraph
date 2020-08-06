package com.tju.carknowledge.Controller;

import com.tju.carknowledge.domain.EsStandardBean;
import com.tju.carknowledge.domain.RetResponse;
import com.tju.carknowledge.domain.RetResult;
import com.tju.carknowledge.domain.UserBean;
import com.tju.carknowledge.service.RegService;
import com.tju.carknowledge.utils.Neo4jConf;
import com.tju.carknowledge.utils.Neo4jUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName SearchGraphController
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/7/5 10:31
 * @Version 1.0
 **/

@RestController
@RequestMapping(path = "/paper")
public class SearchGraphController {

    RegService regService = new RegService();


    @RequestMapping(path = "/search/firstgraph",method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<Map<String, List>> GraphFirstSearch(UserBean userBean) throws Exception {
        /**
         * @Description 搜索框
         * 1.0第一次搜索关键词返回标准图谱
         **/
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

        String value = userBean.getValue();

        Map<String, List> allGraphInfo = regService.CarGraphFirstSearch(value);
        return RetResponse.makeOKRsp(allGraphInfo);
    }

    @RequestMapping(path = "/search/secondgraph",method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<Map<String, List>> GraphSecondSearch(UserBean userBean) throws Exception {
        /**
         * @Description 搜索框
         * 2.0第二次搜索关键词返回图谱
         **/
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        System.out.println(formatter.format(date) + " GraphSecondSearch is ok");
        String value = userBean.getValue();

        Map<String, List> allGraphInfo = regService.CarGraphSecondSearch(value);
        return RetResponse.makeOKRsp(allGraphInfo);
    }

}
