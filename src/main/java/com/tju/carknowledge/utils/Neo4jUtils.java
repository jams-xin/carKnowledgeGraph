package com.tju.carknowledge.utils;

import com.tju.carknowledge.domain.RetResponse;
import com.tju.carknowledge.domain.RetResult;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import java.util.*;

/**
 * @ClassName Neo4jUtils
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/6/28 19:23
 * @Version 1.0
 **/
public class Neo4jUtils {
    public Map<String, List> wikiGraphSearch(String value) throws Exception {
        //需要搜索词
        //搜索词在后面（正向获取）
        String cypher01 = "match (e1:entity{label: " + "\"" + value + "\"" +"})-[r1:relation]->(e2:entity) " +
                "WITH e1,r1,e2 " +
                "match (e2:entity)-[r2:relation]->(e3:entity) " +
                "return r1, e2, r2, e3";

        //搜索词在后面（反向获取）
        String cypher02 = "match (e1:entity)-[r1:relation]->(e2:entity{label: " + "\"" + value + "\"" + "}) " +
                "WITH e1,r1,e2 " +
                "match (e1:entity)-[r2:relation]->(e3:entity) " +
                "return r1, e1, r2, e3";
//        String cypher03 = "match (e1:entity{label: " + "\"" + value + "\"" +"})-[r1:relation]->(e2:entity) " +
////        "WITH e1,r1,e2 " +
////        "match (e2:entity)-[r2:relation]->(e3:entity) " +
////        " WITH e1,r1,e2,r2,e3 " +
////        "match (e4:entity)-[r3:relation]->(e2:entity) " +
////        "return r1, e2, r2, e3, r3, e4";

        List<Map<String, Object>> dataInfoList = new ArrayList<>();
        List<Map<String, Object>> linkInfoList = new ArrayList<>();
        List<String> entityList = new ArrayList<>();
        Map<String, List> allMap = new HashMap<>();

        List<String> allInfoList = getInfoListTest("0", value, cypher01);
        List<String> disAllInfoList = getInfoListTest("1", value, cypher02);

        System.out.println("allRelationList length is:"+ allInfoList.size());
        System.out.println("disAllRelationList length is:"+ disAllInfoList.size());

        // 后期修改数据 从mysql获取
        List<String> contentList = new ArrayList<>();
        contentList.add("内容简介");
        contentList.add("车载电话图谱");
        contentList.add("车载电话图谱详细详细介绍，包括多少个节点，多少种关系等等");
        allMap.put("content", contentList);

        int allInfoNum = 0;
        int disAllInfoNum = 0;
        for (String info : allInfoList){
            Map<String ,Object> link_map = new HashMap<>();   // 获取一条信息

            String entity1 = info.split(":")[0];
            String relation = info.split(":")[1];
            String entity2 = info.split(":")[2];

            link_map.put("source",entity1);
            link_map.put("target",entity2);
            link_map.put("relation",relation);
            link_map.put("value",3);
            entityList.add(entity1);
            entityList.add(entity2);
            linkInfoList.add(link_map);
            // 限制显示数目
            allInfoNum = allInfoNum + 1;
            if (allInfoNum >= 50){
                break;
            }
        }

        for (String disInfo : disAllInfoList){

            Map<String ,Object> link_map = new HashMap<>();   // 获取一条信息

            String entity1 = disInfo.split(":")[0];
            String relation = disInfo.split(":")[1];
            String entity2 = disInfo.split(":")[2];

            entityList.add(entity1);
            entityList.add(entity2);
            link_map.put("source",entity1);
            link_map.put("target",entity2);
            link_map.put("relation",relation);
            link_map.put("value",3);

            linkInfoList.add(link_map);
            // 限制显示数目
            disAllInfoNum = disAllInfoNum + 1;
            if (disAllInfoNum >= 50){
                break;
            }
        }

        // 实体关系去重
        HashSet h = new HashSet(entityList);
        entityList.clear();
        entityList.addAll(h);
        for (String entity : entityList){
            Map<String ,Object> data_map = new HashMap<>();   // 获取一条信息
            data_map.put("id", entity);
            dataInfoList.add(data_map);
        }
        HashSet h1 = new HashSet(linkInfoList);
        linkInfoList.clear();
        linkInfoList.addAll(h1);

        allMap.put("entity", dataInfoList);
        allMap.put("link", linkInfoList);
        return allMap;
    }


    public List<String> getInfoListTest(String type, String value, String cql) {
        Neo4jConf neo4jConf = new Neo4jConf();
        Driver driver = neo4jConf.driver;
        //用于给每个Set list赋值
        int listIndex = 0;
        // 一级实体关系
        List<Map<String, Map<String, Object>>> entityMapRel = new ArrayList<>();
        List<String> entityRelList = new ArrayList<>();
        // 二级实体关系
        List<Map<String, Map<String, Object>>> entityMapRel01 = new ArrayList<>();
        // 所有信息
        List<List<Map<String, Map<String, Object>>>> allList = new ArrayList();

        Map<String, Object> testmap = new HashMap<>();
        Map<String, Object> allmap = new HashMap<>();

        try {
            Session session = driver.session();
            StatementResult result = session.run(cql);  // 返回查询对象
            List<Record> list = result.list();
            for (Record r : list) {
                // 遍历获取相关化合物
                Map<String, Object> map = new HashMap<>();
                Map<String, Object> map1 = new HashMap<>();
                Map<String, Object> map2 = new HashMap<>();

                Map<String, Map<String, Object>> keywordmap = new HashMap<>();
                Map<String, Map<String, Object>> keywordmap1 = new HashMap<>();

                Map<String, Object> dismap = new HashMap<>();
                Map<String, Object> dismap1 = new HashMap<>();
                Map<String, Map<String, Object>> diskeywordmap = new HashMap<>();
                Map<String, Map<String, Object>> diskeywordmap1 = new HashMap<>();

                Object relation = "None";
                relation = r.get(r.keys().get(0)).asMap().get("relation");
                String entity= new String();
                entity = String.valueOf(r.get(r.keys().get(1)).asMap().get("label"));  //获取相关实体

                Object relation1 = "None";
                relation1 = r.get(r.keys().get(2)).asMap().get("relation");
                String entity1= new String();
                entity1 = String.valueOf(r.get(r.keys().get(3)).asMap().get("label"));  //获取相关实体

//                if (type =="3"){
//                    Object relation2 = "None";
//                    relation2 = r.get(r.keys().get(4)).asMap().get("relation");
//                    String entity2= new String();
//                    entity2 = String.valueOf(r.get(r.keys().get(5)).asMap().get("label"));  //获取相关实体
//                    System.out.println("relation IS :" + relation);
//                    System.out.println("entity IS :" + entity);
//                    System.out.println("relation1 IS :" + relation1);
//                    System.out.println("entity1 IS :" + entity1);
//                    System.out.println("relation2 IS :" + relation2);
//                    System.out.println("entity2 IS :" + entity2);
//
//                }

                if (type == "0"){
                    //正向
                    map.put(entity,relation);
                    keywordmap.put(value,map); // 一级实体关系
                    entityMapRel.add(keywordmap);

                    String disentityRel = value + ":" + relation + ":" + entity;

                    entityRelList.add(disentityRel);

                    if (entity.equals(value)){
//                        map1.put(entity1,relation1);
//                        keywordmap.put(value,map1); // 一级实体关系
//                        entityMapRel.add(keywordmap);

                        String disentityRel01 = value + ":" + relation1 + ":" + entity1;

                        entityRelList.add(disentityRel01);
                    }else{
//                        map2.put(entity1, relation1);
//                        keywordmap1.put(entity, map2); // 二级实体关系
//                        entityMapRel01.add(keywordmap1);

                        String disentityRel02 = entity + ":" + relation1 + ":" + entity1;
                        entityRelList.add(disentityRel02);
                    }
                }

                if (type == "1"){
                    //反向

                    // 一级实体关系
                    dismap.put(value, relation);
                    diskeywordmap.put(entity,dismap);
                    entityMapRel.add(diskeywordmap);

                    String disentityRel = entity + ":" + relation + ":" + value;
                    entityRelList.add(disentityRel);

                    if (entity.equals(value)){
                        continue;
                    }else{
                        // 二级实体关系
                        dismap1.put(entity1, relation1);
                        diskeywordmap1.put(entity,dismap1);
                        entityMapRel01.add(diskeywordmap1);

                        String disentityRel01 = entity + ":" + relation1 + ":" + entity1;
                        entityRelList.add(disentityRel01);
                    }
                }


            }

            // 去重
//            HashSet h1 = new HashSet(entityMapRel);
//            entityMapRel.clear();
//            entityMapRel.addAll(h1);
//
//            HashSet h2 = new HashSet(entityMapRel01);
//            entityMapRel01.clear();
//            entityMapRel01.addAll(h2);


            HashSet h3 = new HashSet(entityRelList);
            entityRelList.clear();
            entityRelList.addAll(h3);

//
//            System.out.println("entityMapRel is: "+entityMapRel);
//            System.out.println("entityMapRel length is: "+entityMapRel.size());
////
//            System.out.println("entityMapRel01 is: "+entityMapRel01);
//            System.out.println("entityMapRel01 length is: "+entityMapRel01.size());
//            allList.add(entityMapRel);
//            allList.add(entityMapRel01);
//            System.out.println("allList is: "+allList);
//            System.out.println("allList length is: "+allList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return entityRelList;
    }

    /**（可删除）
     * cql的return返回多种节点match (n)-[edge]-(n) return n,m,edge：限定返回关系时，关系的别名必须“包含”edge
     * @param cql 查询语句
     * @param lists 和cql的return返回节点顺序对应
     * @return List<Map<String,Object>>
     * cypher03 = 'match (e1:compoundInfo{labelName: $name})-[r1:rel_combine]->(e2:compoundInfo)'\
     *            'RETURN r1,e2'
     */
    public static <T> void getInfoList(String cql, Set<T>... lists) {
        Neo4jConf neo4jConf = new Neo4jConf();
        Driver driver = neo4jConf.driver;
        //用于给每个Set list赋值
        int listIndex = 0;
        Map<String, Object> keywordmap = new HashMap<>();
        Map<String, Object> keywordmap1 = new HashMap<>();
        Map<String, Object> allmap = new HashMap<>();

        try {
            Session session = driver.session();
            StatementResult result = session.run(cql);  // 返回查询对象
            List<Record> list = result.list();

            for (Record r : list) {
                if (r.size() != lists.length) {
                    System.out.println("节点数和lists数不匹配");
                    return;
                }
            }
            for (Record r : list) {
                // 遍历获取相关化合物

                Object relation = "None";
                relation = r.get(r.keys().get(0)).asMap().get("relation");

                String entity = String.valueOf(r.get(r.keys().get(1)).asMap().get("label"));  //获取相关实体

                keywordmap.put(entity, relation);
                System.out.println("keywordmap is: "+keywordmap);

                Object relation1 = "None";
                relation1 = r.get(r.keys().get(2)).asMap().get("relation");
                String entity1 = String.valueOf(r.get(r.keys().get(3)).asMap().get("label"));  //获取相关实体
                keywordmap1.put(entity1, relation1);
                System.out.println("keywordmap1 is: "+keywordmap1);

            }

            lists[listIndex++].add((T) keywordmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
