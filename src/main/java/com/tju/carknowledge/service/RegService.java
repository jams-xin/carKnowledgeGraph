package com.tju.carknowledge.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tju.carknowledge.Config;
import com.tju.carknowledge.domain.EsRegulationBean;
import com.tju.carknowledge.domain.EsStandardBean;
import com.tju.carknowledge.utils.EsBuilderUtils;
import org.elasticsearch.search.SearchHit;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName RegService
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/7/1 11:14
 * @Version 1.0
 **/

public class RegService {

    private Config config = new Config();
    /**
     * @Description 搜索行业标准框，返回查询的标准行业数据
     * @param  value String value, int page
     **/
    public List<EsStandardBean> StandardInfoSearch(String value, int page) throws Exception {
        // 获取所有的相关信息
        EsBuilderUtils esBuilderUtils = new EsBuilderUtils();
        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder(config.CARNORMINDEX,config.CARNORMTYPE, "2", value, "None", page); // uuid 默认为空
        List<EsStandardBean> allStandardInfoBeans= new ArrayList<>();

        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            String uuid = String.valueOf(sourceAsMap.get("uuid"));
            String subuuid = String.valueOf(sourceAsMap.get("subuuid"));
            String title = String.valueOf(sourceAsMap.get("title"));
            String en_title = String.valueOf(sourceAsMap.get("en_title"));
            String standard_number = String.valueOf(sourceAsMap.get("standard_number"));
            String replace_standard = String.valueOf(sourceAsMap.get("replace_standard"));
            String pubdate = String.valueOf(sourceAsMap.get("pubdate"));
            String implement_date = String.valueOf(sourceAsMap.get("implement_date"));
            String pdf = String.valueOf(sourceAsMap.get("pdf"));
            String drafting_unit = String.valueOf(sourceAsMap.get("drafting_unit"));//
            String drafter = String.valueOf(sourceAsMap.get("drafter"));//

            String scope = String.valueOf(sourceAsMap.get("scope"));
            String reference_file = String.valueOf(sourceAsMap.get("reference_file"));
            String reference_file_pdf = String.valueOf(sourceAsMap.get("reference_file_pdf"));
            String term_definition = String.valueOf(sourceAsMap.get("term_definition"));

            // 数据处理
            //reference_file_pdf
            Gson gson = new Gson();
            Map<String, String> reference_file_map = new HashMap<String, String>();
            reference_file_map = gson.fromJson(reference_file_pdf, reference_file_map.getClass());
            for(Map.Entry<String, String> vo : reference_file_map.entrySet()){
                reference_file_pdf = vo.getValue();
            }

            drafting_unit = drafting_unit.replaceAll("'","")
                    .replaceAll("\"","")
                    .replace("[","")
                    .replace("]","");

            drafter = drafter.replaceAll("'","")
                    .replaceAll("\"","")
                    .replace("[","")
                    .replace("]","");

            replace_standard = replace_standard.replaceAll("'","")
                    .replaceAll("\"","")
                    .replace("[","")
                    .replace("]","");

            scope = scope.replaceAll("'","")
                    .replaceAll("\"","")
                    .replace("[","")
                    .replace("]","")
                    .replace("\n","")
                    .replace("\\n","");


            EsStandardBean esStandardBean= new EsStandardBean();

            esStandardBean.setUuid(uuid);
            esStandardBean.setSubuuid(subuuid);
            esStandardBean.setTitle(title);
            esStandardBean.setEn_title(en_title);
            esStandardBean.setStandard_number(standard_number);
            esStandardBean.setReplace_standard(replace_standard);
            esStandardBean.setPubdate(pubdate);
            esStandardBean.setImplement_date(implement_date);
            esStandardBean.setPdf(pdf);
            esStandardBean.setDrafting_unit(drafting_unit);
            esStandardBean.setDrafter(drafter);
            esStandardBean.setScope(scope);
            esStandardBean.setReference_file(reference_file);
            esStandardBean.setReference_file_pdf(reference_file_pdf);
            esStandardBean.setTerm_definition(term_definition);

            allStandardInfoBeans.add(esStandardBean);

        }

        List<EsStandardBean> standardInfoList = repeatStandardInfo(allStandardInfoBeans);
        List<EsStandardBean> newStandardInfoList= getAllStandardInfoList(allStandardInfoBeans,standardInfoList);
        return newStandardInfoList;
    }

    /**
     * @Description 去重标准行业数据
     * @value List<EsStandardBean> allStandardInfoBeans
     **/
    public List<EsStandardBean> repeatStandardInfo(List<EsStandardBean> allStandardInfoBeans) {
        List<EsStandardBean> newStandardInfoList = new ArrayList<>();
        Set<String> flagset = new HashSet<String>();

        for (EsStandardBean allStandardInfoBean: allStandardInfoBeans){
            String uuid = allStandardInfoBean.getUuid();
            if (flagset.contains(uuid)){
                continue;
            }else {
                flagset.add(uuid);
//                System.out.println("uuid is: " + uuid);
                String newuuid = allStandardInfoBean.getUuid();
                String newsubuuid = allStandardInfoBean.getSubuuid();
                String newtitle = allStandardInfoBean.getTitle();
                String newen_title = allStandardInfoBean.getEn_title();
                String newstandard_number = allStandardInfoBean.getStandard_number();
                String newreplace_standard = allStandardInfoBean.getReplace_standard();
                String newpubdate = allStandardInfoBean.getPubdate();
                String newimplement_date = allStandardInfoBean.getImplement_date();
                String newpdf = allStandardInfoBean.getPdf();
                String newdrafting_unit = allStandardInfoBean.getDrafting_unit();
                String newdrafter = allStandardInfoBean.getDrafter();
                String newscope = allStandardInfoBean.getScope();
                String newreference_file = allStandardInfoBean.getReference_file();
                String newreference_file_pdf = allStandardInfoBean.getReference_file_pdf();
                String newterm_definition = allStandardInfoBean.getTerm_definition();

                EsStandardBean newStandardBean = new EsStandardBean();

                newStandardBean.setUuid(newuuid);
                newStandardBean.setSubuuid(newsubuuid);
                newStandardBean.setTitle(newtitle);
                newStandardBean.setEn_title(newen_title);
                newStandardBean.setStandard_number(newstandard_number);
                newStandardBean.setReplace_standard(newreplace_standard);
                newStandardBean.setPubdate(newpubdate);
                newStandardBean.setImplement_date(newimplement_date);
                newStandardBean.setPdf(newpdf);
                newStandardBean.setDrafting_unit(newdrafting_unit);
                newStandardBean.setDrafter(newdrafter);
                newStandardBean.setScope(newscope);
                newStandardBean.setReference_file(newreference_file);
                newStandardBean.setReference_file_pdf(newreference_file_pdf);
                newStandardBean.setTerm_definition(newterm_definition);

                newStandardInfoList.add(newStandardBean);
            }
        }
        return newStandardInfoList;
    }


    /**
     * @Description 合并参考标准，获取所有标准数据
     * @value List<EsStandardBean> allStandardInfoList, List<EsStandardBean> standardInfoList
     **/
    public List<EsStandardBean> getAllStandardInfoList(List<EsStandardBean> allStandardInfoList, List<EsStandardBean> standardInfoList){
        //获取参考标准数据
        List<EsStandardBean> allInfoList = new ArrayList<>();
        List<String> reference = new ArrayList<>();
         for (EsStandardBean standardInfo: standardInfoList) {
            //遍历搜索关键词的标准信息
            EsStandardBean newStandardInfoBean = new EsStandardBean();

            List<Map<String, String>> referenceList = new ArrayList<>();

            String newuuid = standardInfo.getUuid();
            String newsubuuid = standardInfo.getSubuuid();
            String newtitle = standardInfo.getTitle();
            String newen_title = standardInfo.getEn_title();
            String newstandard_number = standardInfo.getStandard_number();
            String newreplace_standard = standardInfo.getReplace_standard();
            String newpubdate = standardInfo.getPubdate();
            String newimplement_date = standardInfo.getImplement_date();
            String newpdf = standardInfo.getPdf();
            String newdrafting_unit = standardInfo.getDrafting_unit();
            String newdrafter = standardInfo.getDrafter();
            String newscope = standardInfo.getScope();
            String newterm_definition = standardInfo.getTerm_definition();

             //term_definition_list
             List<Map<String, String>> term_definitionList = new ArrayList<>();
             Gson gson1 = new Gson();
             Map<String, String> map1 = new HashMap<String, String>();
             map1 = gson1.fromJson(newterm_definition, map1.getClass());
             int term_definition_num = 0;
             String allTerm_definitionInfo = new String();
             for (Map.Entry<String, String> entry : map1.entrySet()){
                 // 返回一个术语定义term_definitionInfo
                 Map<String, String> term_definitionInfo = new HashMap<>();
                 term_definitionInfo.put("term_definition_key", entry.getKey());
                 term_definitionInfo.put("term_definition_value",entry.getValue());
                 term_definitionList.add(term_definitionInfo);

                 term_definition_num = term_definition_num + 1;
                 if(term_definition_num == 3){
                     // 限制显示数目
                     break;
                 }
             }


            for (EsStandardBean allStandardInfo: allStandardInfoList){
                //遍历搜索关键词的所有信息——获取参考标准
                String uuid2 = allStandardInfo.getUuid();
                String subuuid2 = allStandardInfo.getSubuuid();

                if((newuuid.equals(uuid2) == true)&&(newsubuuid.equals(subuuid2) == false)){
                    Map<String, String> referenceMap = new HashMap<>();
                    referenceMap.put("reference_file_name", allStandardInfo.getReference_file());
                    referenceMap.put("reference_file_pdflink", allStandardInfo.getReference_file_pdf());
                    referenceList.add(referenceMap);
                }
            }

            newStandardInfoBean.setUuid(newuuid);
            newStandardInfoBean.setSubuuid(newsubuuid);
            newStandardInfoBean.setTitle(newtitle);
            newStandardInfoBean.setEn_title(newen_title);
            newStandardInfoBean.setStandard_number(newstandard_number);
            newStandardInfoBean.setReplace_standard(newreplace_standard);
            newStandardInfoBean.setPubdate(newpubdate);
            newStandardInfoBean.setImplement_date(newimplement_date);
            newStandardInfoBean.setPdf(newpdf);
            newStandardInfoBean.setDrafting_unit(newdrafting_unit);
            newStandardInfoBean.setDrafter(newdrafter);
            newStandardInfoBean.setScope(newscope);
//            newStandardInfoBean.setReference_file(newreference_file);
//            newStandardInfoBean.setReference_file_pdf(newreference_file_pdf);
            newStandardInfoBean.setReference(referenceList);
//            newStandardInfoBean.setTerm_definition(allTerm_definitionInfo);
            newStandardInfoBean.setTerm_definition_list(term_definitionList);

            allInfoList.add(newStandardInfoBean);
        }
        return allInfoList;
    }




    /**
     * @Description 搜索图谱框，搜索关键词返回标准图谱
     * @param value String value
     **/
    public Map<String, List> graphSearch(String value, int graphflag) throws Exception {
        // 获取所有的相关信息
        EsBuilderUtils esBuilderUtils = new EsBuilderUtils();
        SearchHit[] searchHits = esBuilderUtils.queryTextBuilder("carnorm","norm", "2", value, "None", 1); // uuid 默认为空
        List<EsRegulationBean> esRegulationList = new ArrayList<>();

        for (SearchHit hit : searchHits) {
            //取_source字段值
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            String uuid = String.valueOf(sourceAsMap.get("uuid"));
            String subuuid = String.valueOf(sourceAsMap.get("subuuid"));
            String title = String.valueOf(sourceAsMap.get("title"));
            String pdf = String.valueOf(sourceAsMap.get("pdf"));
            String standard_number = String.valueOf(sourceAsMap.get("standard_number"));
            String reference_file = String.valueOf(sourceAsMap.get("reference_file"));
            String reference_file_pdf = String.valueOf(sourceAsMap.get("reference_file_pdf"));
            String reference_file_number = String.valueOf(sourceAsMap.get("reference_file_number"));

            Gson gson = new Gson();
            Map<String, String> map = new HashMap<String, String>();
            map = gson.fromJson(reference_file_pdf, map.getClass());
            for(Map.Entry<String, String> vo : map.entrySet()){
                reference_file_pdf = vo.getValue();
            }

            EsRegulationBean esRegulationBean = new EsRegulationBean();
            esRegulationBean.setUuid(uuid);
            esRegulationBean.setSubuuid(subuuid);
            esRegulationBean.setTitle(title);
            esRegulationBean.setStandard_number(standard_number);
            esRegulationBean.setPdf(pdf);
            esRegulationBean.setReference_file(reference_file);
            esRegulationBean.setReference_file_pdf(reference_file_pdf);
            esRegulationBean.setReference_file_number(reference_file_number);

            esRegulationList.add(esRegulationBean);

        }

        List<EsRegulationBean> standardInfoList = getStandard(esRegulationList);
        List<EsRegulationBean> referenceInfoList = getReferenceStandard(esRegulationList,standardInfoList);

        Map<String, List> graphList = new HashMap<>();
        if(graphflag == 1){
            // 第一次搜索
            graphList = getRegGraph01(value, standardInfoList, referenceInfoList);
        }else{
            // 第二次搜索
            graphList = getRegGraph02(value, standardInfoList, referenceInfoList);
        }

        return graphList;
    }

    /**
     * @Description 获取关键字的标准
     * @value List<EsRegulationBean> esRegulationBeans
     **/
    public List<EsRegulationBean> getStandard(List<EsRegulationBean> esRegulationBeans) {
        // 获取相关标准数据
        List<EsRegulationBean> standardInfoList = new ArrayList<>();

        Set<String> flagset = new HashSet<String>();
        for (EsRegulationBean esRegulationBean: esRegulationBeans){
            String uuid = esRegulationBean.getUuid();
            if (flagset.contains(uuid)){
                continue;
            }else {
                flagset.add(uuid);
//                System.out.println("uuid is: " + uuid);
                String newuuid = esRegulationBean.getUuid();
                String newsubuuid = esRegulationBean.getSubuuid();
                String newtitle = esRegulationBean.getTitle();
                String newstandard_number = esRegulationBean.getStandard_number();
                String newpdf = esRegulationBean.getPdf();

                EsRegulationBean standardInfoBean = new EsRegulationBean();
                standardInfoBean.setUuid(newuuid);
                standardInfoBean.setSubuuid(newsubuuid);
                standardInfoBean.setTitle(newtitle);
                standardInfoBean.setStandard_number(newstandard_number);
                standardInfoBean.setPdf(newpdf);

                standardInfoList.add(standardInfoBean);
            }
        }
        return standardInfoList;
    }

    /**
     * @Description 获取参考标准
     * @value List<EsRegulationBean> esRegulationList, List<EsRegulationBean> standardInfoList
     **/
    public List<EsRegulationBean> getReferenceStandard(List<EsRegulationBean> esRegulationList, List<EsRegulationBean> standardInfoList){
        //获取参考标准数据
        List<EsRegulationBean> referenceInfoList = new ArrayList<>();

        for (EsRegulationBean standardInfo: standardInfoList) {
            //遍历搜索关键词的标准信息
            EsRegulationBean referenceInfoBean = new EsRegulationBean();

            List<String> reference_file_list = new ArrayList<>();
            List<String> reference_file_number_list = new ArrayList<>();
            List<String> reference_file_pdf_list = new ArrayList<>();

            String uuid = standardInfo.getUuid();
            String subuuid = standardInfo.getSubuuid();

            for (EsRegulationBean esRegulation: esRegulationList){
                //遍历搜索关键词的所有信息——获取参考标准
                String uuid2 = esRegulation.getUuid();
                String subuuid2 = esRegulation.getSubuuid();

                if((uuid.equals(uuid2) == true)&&(subuuid.equals(subuuid2) == false)){
                    reference_file_list.add('"' + esRegulation.getReference_file() +'"');
                    reference_file_number_list.add('"' + esRegulation.getReference_file_number() + '"');
                    reference_file_pdf_list.add('"' + esRegulation.getReference_file_pdf() + '"');
                }
            }
            referenceInfoBean.setReference_file(String.valueOf(reference_file_list));
            referenceInfoBean.setReference_file_number(String.valueOf(reference_file_number_list));
            referenceInfoBean.setReference_file_pdf(String.valueOf(reference_file_pdf_list));
            referenceInfoList.add(referenceInfoBean);

        }
        return referenceInfoList;
    }
    /**
     * @Description 获取第一次搜索图谱
     * @param value String value, List<EsRegulationBean> standardInfoList, List<EsRegulationBean> referenceInfoList
     **/
    public Map<String, List> getRegGraph01(String value, List<EsRegulationBean> standardInfoList, List<EsRegulationBean> referenceInfoList) throws IOException {
        // 获取标准图谱数据
        List<Map<String, Object>> dataInfoList = new ArrayList<>();
        List<Map<String, Object>> linkInfoList = new ArrayList<>();
        Map<String, List> allMap = new HashMap<>();

        // 后期修改数据，从mysql获取
        List<String> contentList = new ArrayList<>();
        contentList.add("内容简介");
        contentList.add("机动车图谱");
        contentList.add("机动车图谱详细详细介绍，包括多少个节点，多少种关系等等");

        int flag = 0;
        for(EsRegulationBean standardInfo : standardInfoList){
            /* 获取关键字的标准*/
            Map<String ,Object> standard_map = new HashMap<>();   // 获取一条信息
            String standard_number = standardInfo.getStandard_number();
            // 实体
            standard_map.put("id", standard_number.trim());
            dataInfoList.add(standard_map);

            // 字符串数组转列表eg: "["1","2"]"转["1","2"]
            ObjectMapper mapper = new ObjectMapper();
            String reference_file_numberList = referenceInfoList.get(flag).getReference_file_number();
            List<String> newreference_file_numberList = mapper.readValue(reference_file_numberList, List.class);

            flag = flag + 1;
            for (String reference_file_number: newreference_file_numberList) {
                /* 获取关键字的参考标准*/
                if (reference_file_number == null || reference_file_number == ""){
                    continue;
                }else {
                    Map<String ,Object> reference_map = new HashMap<>();   // 获取一条信息
                    Map<String ,Object> link_map = new HashMap<>();   // 获取一条信息
                    reference_map.put("id", reference_file_number.trim());

                    link_map.put("value",flag);
                    link_map.put("relation","REF");
                    link_map.put("source",standard_number);
                    link_map.put("target",reference_file_number.trim());

                    linkInfoList.add(link_map);
                    dataInfoList.add(reference_map);
                }
            }
            if (flag >= 5){
                break;
            }
        }
        allMap.put("entity", dataInfoList);
        allMap.put("link", linkInfoList);
        allMap.put("content", contentList);
        return allMap;
    }

    /**
     * @Description 获取第二次搜索图谱
     * @param value String value, List<EsRegulationBean> standardInfoList, List<EsRegulationBean> referenceInfoList
     **/
    public Map<String, List> getRegGraph02(String value, List<EsRegulationBean> standardInfoList, List<EsRegulationBean> referenceInfoList) throws IOException {
        // 获取标准图谱数据
        Map<String, String> valuemap = new HashMap<>();
//        List<Map<String, String>> graphList = new ArrayList<>();

        List<Map<String, Object>> dataInfoList = new ArrayList<>();
        List<Map<String, Object>> linkInfoList = new ArrayList<>();
        Map<String, List> allMap = new HashMap<>();

        // 后期修改数据，从mysql获取
        List<String> contentList = new ArrayList<>();
        contentList.add("内容简介");
        contentList.add("机动车图谱");
        contentList.add("机动车图谱详细详细介绍，包括多少个节点，多少种关系等等");

        Map<String ,Object> value_map = new HashMap<>();   // 获取一条信息
        value_map.put("id", value);
        dataInfoList.add(value_map);

        int flag = 0;
        for(EsRegulationBean standardInfo : standardInfoList){
            /* 获取关键字的标准*/
            Map<String ,Object> standard_map = new HashMap<>();   // 获取一条信息
            String standard_number = standardInfo.getStandard_number();
            standard_map.put("id", standard_number.trim());
            dataInfoList.add(standard_map);

            Map<String ,Object> link_map = new HashMap<>();   // 获取一条信息
            link_map.put("value",2);
            link_map.put("source",value);
            link_map.put("target",standard_number.trim());
            link_map.put("relation","SN");
            linkInfoList.add(link_map);

            // 字符串数组转列表eg: "["1","2"]"转["1","2"]
            ObjectMapper mapper = new ObjectMapper();
            String reference_file_numberList = referenceInfoList.get(flag).getReference_file_number();
            List<String> newreference_file_numberList = mapper.readValue(reference_file_numberList, List.class);

            flag = flag + 1;
            for (String reference_file_number: newreference_file_numberList) {
                /* 获取关键字的参考标准*/
                if (reference_file_number == null || reference_file_number == ""){
                    continue;
                }else {
                    Map<String ,Object> data_map = new HashMap<>();   // 获取一条信息
                    Map<String ,Object> link_map1 = new HashMap<>();   // 获取一条信息

                    data_map.put("id", reference_file_number.trim());

                    link_map1.put("value",flag);
                    link_map1.put("source",standard_number);
                    link_map1.put("target",reference_file_number.trim());
                    link_map1.put("relation","REF");
                    dataInfoList.add(data_map);
                    linkInfoList.add(link_map1);
                }
            }
            if (flag >= 5){
                break;
            }
        }
        allMap.put("entity", dataInfoList);
        allMap.put("link", linkInfoList);
        allMap.put("content", contentList);
        return allMap;
    }
}
