package com.tju.carknowledge.utils;


import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName EsBuilderUtils
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/6/29 13:17
 * @Version 1.0
 **/
public class EsBuilderUtils {
    public SearchHit[] queryTextBuilder(String ESindex, String EStype, String Searchflag, String value, String uuid, int page) throws Exception {

        EsConf esConf = new EsConf();
        RestHighLevelClient client = esConf.client;

        // 1、创建search请求
        SearchRequest searchRequest = new SearchRequest(ESindex);
        searchRequest.types(EStype);

        // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        if (Searchflag.equals("0")){
            // 从文章题目和内容进行匹配关键字
            sourceBuilder.query(QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchQuery("title", value))
                    .should(QueryBuilders.matchQuery("content", value))
                    .must(QueryBuilders.existsQuery("title"))
                    .must(QueryBuilders.existsQuery("content")));
            sourceBuilder.from(page - 1);   //起始位置从page - 1开始
            sourceBuilder.size(10);   //每页10个数据
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        }else if(Searchflag.equals("1")) {
            //根据UUID查询文章详情信息
            sourceBuilder.query(QueryBuilders.matchQuery("articleID", uuid));
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        }else if(Searchflag.equals("2")) {
            // 标准图谱查询Es条件
            sourceBuilder.query(QueryBuilders.boolQuery()
                    .should(QueryBuilders.termQuery("title", value))
                    .should(QueryBuilders.termQuery("term_definition", value))
                    .should(QueryBuilders.matchQuery("reference_file", value))
                    .should(QueryBuilders.termQuery("drafting_unit", value))
                    .should(QueryBuilders.termQuery("scope", value))
                    .should(QueryBuilders.termQuery("drafter", value)));




//            sourceBuilder.query(QueryBuilders.boolQuery()
//                    .should(QueryBuilders.wildcardQuery("title", "*"+value+"*"))
//                    .should(QueryBuilders.wildcardQuery("standard_number", "*"+value+"*"))
//                    .should(QueryBuilders.wildcardQuery("drafting_unit", "*"+value+"*"))
//                    .should(QueryBuilders.wildcardQuery("drafter", "*"+value+"*"))
//                    .should(QueryBuilders.wildcardQuery("scope", "*"+value+"*"))
//                    .should(QueryBuilders.wildcardQuery("reference_file", "*"+value+"*")));
//                    .should(QueryBuilders.wildcardQuery("term_definition", "*"+value+"*")));


            sourceBuilder.from(page - 1);   //起始位置从page - 1开始
            sourceBuilder.size(200);   //每页10个数据
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        }else if (Searchflag.equals("3")){
            // 查询产业资讯所有信息
            sourceBuilder.query(QueryBuilders.matchAllQuery());
            sourceBuilder.from(page - 1);   //起始位置从page - 1开始
            sourceBuilder.size(20);   //每页10个数据
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        } else {
            System.out.println("nothing search");
        }

        //将请求体加入到请求中
        searchRequest.source(sourceBuilder);
        //3、发送请求
        SearchResponse searchResponse = client.search(searchRequest);
        //4.处理搜索命中文档结
        SearchHit[] searchHits = searchResponse.getHits().getHits();
//        System.out.println(searchHits.length);
        return searchHits;
    }
}
