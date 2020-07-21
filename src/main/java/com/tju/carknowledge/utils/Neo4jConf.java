package com.tju.carknowledge.utils;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;


/**
 * @ClassName Neo4jConf
 * @Description Neo4j初始化配置
 * @Author Yuan Yunxin
 * @Data 2020/6/28 19:17
 * @Version 1.0
 **/
public class Neo4jConf {
    public Driver driver = null;
    private String url = "bolt://192.168.199.204:7687";
//    @Value("${neo4j.username}")
    private String username = "neo4j";
    //    @Value("${neo4j.password}")
    private String password = "tju123";

    public Neo4jConf(){
        try {
            Driver driver;
            //neo4j地址 账号 密码
            driver = GraphDatabase.driver(url, AuthTokens.basic(username, password));
            this.driver = driver;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
