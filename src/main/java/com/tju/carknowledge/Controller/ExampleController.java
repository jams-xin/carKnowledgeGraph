package com.tju.carknowledge.Controller;


import com.tju.carknowledge.Mapper.PaperMapper;
import com.tju.carknowledge.domain.PaperMysqlBean;
import com.tju.carknowledge.domain.RetResponse;
import com.tju.carknowledge.domain.RetResult;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.neo4j.driver.v1.Values.parameters;

@RestController
@RequestMapping("/paper")
public class ExampleController {
    @Autowired
    PaperMapper paperMapper;
    @RequestMapping(value = "/searchList",method = RequestMethod.POST)
    public RetResult<List> searchListResponse(@RequestBody Map<String,String> map) {
        System.out.println(map.get("username"));
        List<PaperMysqlBean> list = paperMapper.getData();
        return RetResponse.makeOKRsp(list);
    }

    @RequestMapping(value = "/shortestPath",method = RequestMethod.POST)
    public RetResult<List> ShortestPath(@RequestBody Map<String,String> json){
//        System.out.println(map);
        String name1 = json.get("author1");
        String name2 = json.get("author2");
        List<List> resultList = new ArrayList<>();
        try{
            Driver driver = GraphDatabase.driver( "bolt://192.168.199.205:7687", AuthTokens.basic( "neo4j", "tju123" ) );
            Session session = driver.session();
//            String name1 = "KR Foltz";
//            String name2 = "WJ Lennarz";
            StatementResult result = session.run( "MATCH n=allshortestPaths((a:author{title:{name1}})-[*]-(b:author{title:{name2}})) return n",
                    parameters( "name1", name1, "name2", name2));

            int count = 0;
            while ( result.hasNext() )
            {
                List<String> list = new ArrayList<>();
                Record record = result.next();
                Iterable<Node> nodes = record.get("n").asPath().nodes();

                for (Node node : nodes){
                    list.add(node.get("title").asString());
                    System.out.println(list);
                }

                list.remove(name1);
                list.remove(name2);
                resultList.add(list);
                count++;

            }
            session.close();
            driver.close();

            if (count == 0){
                return RetResponse.makeOKRsp(null);
            }
            if (resultList.get(0).size() == 0){
                return RetResponse.makeOKRsp(new ArrayList());
            }

            System.out.println("*******");
            System.out.println(resultList);

        }catch (Exception e){
            System.out.println(e);
        }

        return RetResponse.makeOKRsp(resultList);

    }

}
