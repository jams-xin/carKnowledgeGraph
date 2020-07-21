package com.tju.carknowledge.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PicService
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/6/29 21:32
 * @Version 1.0
 **/
public class PicService {
    public List getPicList(String uuid){
        String path = "D:\\xin\\13_project\\car01\\qcData\\" + uuid;		//要遍历的路径
        List<String> picList = new ArrayList<>();
        File file = new File(path);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中

        for(File f:fs){					//遍历File[]数组
            if(f != null) {
                String pic = "/img/" + uuid + "/" + f.getName();
                picList.add(pic);
//                System.out.println(pic);
            }
        }
        return picList;

    }
}
