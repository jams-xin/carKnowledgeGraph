package com.tju.carknowledge.Controller;

import com.tju.carknowledge.domain.EsStandardBean;
import com.tju.carknowledge.domain.RetResponse;
import com.tju.carknowledge.domain.RetResult;
import com.tju.carknowledge.domain.UserBean;
import com.tju.carknowledge.service.RegService;
import com.tju.carknowledge.utils.Neo4jConf;
import com.tju.carknowledge.utils.Neo4jUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.logging.Handler;

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
    Neo4jUtils neo4jUtils = new Neo4jUtils();
    RegService regService = new RegService();

    /**
     * @Description 搜索框
     * 1.0搜索关键词返回合并图谱（百科 + 标准图谱）
     **/
    @RequestMapping(path = "/search/graph",method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<Map<String, List>> combineGraphSearch(UserBean userBean) throws Exception {
        System.out.println("搜索框 combineGraphSearch is ok");
        String value = userBean.getValue();

        String title = new String();
        Map<String, List> wikiMap;
        Map<String, List> regulationsMap;
        Map<String, List> allGraphInfo = new HashMap<>();
        List allDataInfoList = new ArrayList<>();
        List allLinkInfoList = new ArrayList<>();

        List<EsStandardBean> esStandardInfoList = regService.StandardInfoSearch(value, 1);
        if (esStandardInfoList.isEmpty()){
            // 判断搜索标准列表是否为空
            wikiMap = neo4jUtils.wikiGraphSearch(value);
            regulationsMap = regService.graphSearch(value);
        }else{
            for (EsStandardBean esStandardInfo1 : esStandardInfoList){
                title = esStandardInfo1.getTitle();
                break;
            }
            if (title.contains(value)){
                wikiMap = neo4jUtils.wikiGraphSearch(value);
                regulationsMap = regService.graphSearch(value);
            }else{
                wikiMap = neo4jUtils.wikiGraphSearch(title);
                regulationsMap = regService.graphSearch(title);
            }
        }


        allDataInfoList.addAll(wikiMap.get("entity"));
        allDataInfoList.addAll(regulationsMap.get("entity"));

        allLinkInfoList.addAll(wikiMap.get("link"));
        allLinkInfoList.addAll(regulationsMap.get("link"));

        System.out.println("combine entityNum is :"+allDataInfoList.size());
        System.out.println("combine relationNum is :"+allLinkInfoList.size());

        //图谱实体去重
        HashSet h = new HashSet(allDataInfoList);
        allDataInfoList.clear();
        allDataInfoList.addAll(h);

        allGraphInfo.put("entity", allDataInfoList);
        allGraphInfo.put("link", allLinkInfoList);

        return RetResponse.makeOKRsp(allGraphInfo);
    }

}
