package com.tju.carknowledge.Mapper;

import com.tju.carknowledge.domain.CarMysqlBean;
import com.tju.carknowledge.domain.PaperMysqlBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @ClassName CarMapper
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/7/6 9:16
 * @Version 1.0
 **/

@Mapper
@Repository
public interface CarMapper {
    @Select("SELECT * FROM `car_test` WHERE `id` = #{uuid}")
    CarMysqlBean getDataById(String uuid);
}
