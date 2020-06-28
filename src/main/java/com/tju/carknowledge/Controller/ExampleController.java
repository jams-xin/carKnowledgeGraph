package com.tju.carknowledge.Controller;


import com.tju.carknowledge.Mapper.PaperMapper;
import com.tju.carknowledge.domain.PaperMysqlBean;
import com.tju.carknowledge.domain.RetResponse;
import com.tju.carknowledge.domain.RetResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

}
