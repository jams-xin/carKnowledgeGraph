package com.tju.carknowledge.Controller;

import com.tju.carknowledge.domain.RetResponse;
import com.tju.carknowledge.domain.RetResult;
import com.tju.carknowledge.utils.Neo4jUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ProductController
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/7/6 9:33
 * @Version 1.0
 **/
@RestController
@RequestMapping(path = "/paper")
public class ProductController {
    @RequestMapping(path = "/product",method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<Map<String, List>> guidProductGraph() throws Exception {
        // 展示图谱
        System.out.println("导航框 guidProductGraph is ok");
        String value = "车载电话";  // 作为展示页面，选取确定数据展示

        Neo4jUtils neo4jUtils = new Neo4jUtils();
        Map<String, List> allMap = neo4jUtils.wikiGraphSearch(value);
        return RetResponse.makeOKRsp(allMap);
    }
}
