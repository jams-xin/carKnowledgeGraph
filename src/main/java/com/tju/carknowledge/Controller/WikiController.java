package com.tju.carknowledge.Controller;

import com.tju.carknowledge.Config;
import com.tju.carknowledge.Mapper.CarMapper;
import com.tju.carknowledge.Mapper.PaperMapper;
import com.tju.carknowledge.domain.CarMysqlBean;
import com.tju.carknowledge.domain.RetResponse;
import com.tju.carknowledge.domain.RetResult;
import com.tju.carknowledge.domain.UserBean;
import com.tju.carknowledge.utils.Neo4jUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static java.awt.SystemColor.info;

/**
 * @ClassName WikiController
 * @Description 返回百科图谱
 * @Author Yuan Yunxin
 * @Data 2020/6/28 19:37
 * @Version 1.0
 **/

@RestController
@RequestMapping(path = "/paper")
public class WikiController {

    public  Config config = new Config();
    @Autowired
    public CarMapper carMapper;

    /**
     * @Description 导航框（示例展示）
     * 1.0固定搜索词，返回固定百科图谱数据
     **/
    @RequestMapping(path = "/wiki",method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<Map<String, List>> guidWikiGraph() throws Exception {
        // 展示图谱

        System.out.println("导航框 guidWikiGraph is ok");
//        CarMysqlBean carMysqlBean = carMapper.getDataById("1");
//        System.out.println(carMysqlBean);
//        String value = "碳钢";  // 作为展示页面，选取确定数据展示
//        String value = "手柄座";  // 作为展示页面，选取确定数据展示
        String value = "发动机";  // 作为展示页面，选取确定数据展示

        Neo4jUtils neo4jUtils = new Neo4jUtils();
        Map<String, List> allMap = neo4jUtils.wikiGraphSearch(value);
        return RetResponse.makeOKRsp(allMap);
    }




    /**
     * @Description 搜索框(可删除)
     * 1.0搜索关键词返回百科图谱数据
     **/
    @RequestMapping(path = "/search/wiki",method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<Map<String, List>> wikiGraphsearch(UserBean userBean) throws Exception {
        // 搜索图谱
        System.out.println("搜索框 wikiGraphsearch is ok");

        // 获取请求体
        String value = userBean.getValue();
        Neo4jUtils neo4jUtils = new Neo4jUtils();

        //搜索词在后面（正向获取）
        String cypher01 = "match (e1:entity{label: " + "\"" + value + "\""+ "})-[r1:relation]->(e2:entity) " +
                "RETURN r1,e2";

        //搜索词在后面（反向获取）
        String cypher02 = "match (e2:entity)-[r1:relation]->(e1:entity{label:" + "\"" + value + "\"" + "}) " +
                "RETURN r1,e2";


        Set<Map<String ,Object>> keyword = new HashSet<>();
        Set<Map<String ,Object>> dis_keyword = new HashSet<>();

        Set<Map<String ,Object>> edge1 = new HashSet<>();
        Set<Map<String ,Object>> edge2 = new HashSet<>();


        neo4jUtils.getInfoList(cypher01, keyword, edge1);
        neo4jUtils.getInfoList(cypher02, dis_keyword,edge2);

        List<Map<String, Object>> dataInfoList = new ArrayList<>();
        List<Map<String, Object>> linkInfoList = new ArrayList<>();

        Map<String, List> allMap = new HashMap<>();
        Map<String ,Object> key_map = new HashMap<>();   // 获取value信息
        key_map.put("id", value);
        dataInfoList.add(key_map);  // 添加关键字

        for (Map<String, Object> info1:keyword){
            // 正向获取
            Iterator<Map.Entry<String,Object>> iter = info1.entrySet().iterator();
            while(iter.hasNext()){
                Map<String ,Object> data_map = new HashMap<>();   // 获取一条信息
                Map<String ,Object> link_map = new HashMap<>();   // 获取一条信息
                Map.Entry<String,Object> entry = iter.next();
                String entity = entry.getKey();
                Object relation = entry.getValue();

                data_map.put("id", entity);

                link_map.put("source",value);
                link_map.put("target",entity);
                link_map.put("relation",relation);
                link_map.put("value",2);

                dataInfoList.add(data_map);
                linkInfoList.add(link_map);

            }
            System.out.println("keyword length:"+info1.size());

        }

        for (Map<String, Object> info2:dis_keyword){
            // 反向获取
            Iterator<Map.Entry<String,Object>> iter = info2.entrySet().iterator();
            while (iter.hasNext()){
                Map<String ,Object> data_map = new HashMap<>();   // 获取一条信息
                Map<String ,Object> link_map = new HashMap<>();   // 获取一条信息
                Map.Entry<String,Object> entry = iter.next();
                Object relation = entry.getValue();
                String entity = entry.getKey();

                data_map.put("id", entity);

                link_map.put("source",entity);
                link_map.put("target",value);
                link_map.put("relation",relation);
                link_map.put("value",3);

                dataInfoList.add(data_map);
                linkInfoList.add(link_map);

            }
            System.out.println("dis_keyword length:" + info2.size());

        }

        allMap.put("entity", dataInfoList);
        allMap.put("link", linkInfoList);
//        System.out.println(infoList.size());
        return RetResponse.makeOKRsp(allMap);
    }
}
