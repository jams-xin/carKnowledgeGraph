package com.tju.carknowledge.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

/**
 * @ClassName EsConf
 * @Description ES初始化配置
 * @Author Yuan Yunxin
 * @Data 2020/6/28 16:35
 * @Version 1.0
 **/

@Component
public class EsConf {
    public RestHighLevelClient client = null;
    private String IP = "192.168.199.162";
    private int PORT = 9200;

    public EsConf(){
        try{
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(IP, PORT, "http")));
            this.client = client;
        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public RestHighLevelClient getConnection(){
        return  client;
    }


}
