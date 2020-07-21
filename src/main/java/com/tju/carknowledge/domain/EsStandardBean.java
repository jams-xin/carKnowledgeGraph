package com.tju.carknowledge.domain;

import java.util.List;
import java.util.Map;

/**
 * @ClassName EsStandardBean
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/7/2 21:44
 * @Version 1.0
 **/
public class EsStandardBean {
    private String uuid;
    private String subuuid;
    private String title;
    private String en_title;
    private String standard_number;
    private String replace_standard;
    private String pubdate;
    private String implement_date;
    private String pdf;
    private String drafting_unit;
    private String drafter;
    private String scope;
    private String reference_file;
    private String reference_file_pdf;
    private List reference;
    private String term_definition;
    private List term_definition_list;

    public List getTerm_definition_list() {
        return term_definition_list;
    }

    public void setTerm_definition_list(List term_definition_list) {
        this.term_definition_list = term_definition_list;
    }

    public List getReference() {
        return reference;
    }

    public void setReference(List reference) {
        this.reference = reference;
    }

    public String getUuid() {
        return uuid;
    }

    public String getSubuuid() {
        return subuuid;
    }

    public String getTitle() {
        return title;
    }

    public String getEn_title() {
        return en_title;
    }

    public String getStandard_number() {
        return standard_number;
    }

    public String getReplace_standard() {
        return replace_standard;
    }

    public String getPubdate() {
        return pubdate;
    }

    public String getImplement_date() {
        return implement_date;
    }

    public String getPdf() {
        return pdf;
    }

    public String getDrafting_unit() {
        return drafting_unit;
    }

    public String getDrafter() {
        return drafter;
    }

    public String getScope() {
        return scope;
    }

    public String getReference_file() {
        return reference_file;
    }

    public String getReference_file_pdf() {
        return reference_file_pdf;
    }


    public String getTerm_definition() {
        return term_definition;
    }

//    public String getRequest() {
//        return request;
//    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setSubuuid(String subuuid) {
        this.subuuid = subuuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }

    public void setStandard_number(String standard_number) {
        this.standard_number = standard_number;
    }

    public void setReplace_standard(String replace_standard) {
        this.replace_standard = replace_standard;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public void setImplement_date(String implement_date) {
        this.implement_date = implement_date;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public void setDrafting_unit(String drafting_unit) {
        this.drafting_unit = drafting_unit;
    }

    public void setDrafter(String drafter) {
        this.drafter = drafter;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setReference_file(String reference_file) {
        this.reference_file = reference_file;
    }

    public void setReference_file_pdf(String reference_file_pdf) {
        this.reference_file_pdf = reference_file_pdf;
    }


    public void setTerm_definition(String term_definition) {
        this.term_definition = term_definition;
    }

//    public void setRequest(String request) {
//        this.request = request;
//    }


    @Override
    public String toString() {
        return "EsStandardBean{" +
                "uuid='" + uuid + '\'' +
                ", subuuid='" + subuuid + '\'' +
                ", title='" + title + '\'' +
                ", en_title='" + en_title + '\'' +
                ", standard_number='" + standard_number + '\'' +
                ", replace_standard='" + replace_standard + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", implement_date='" + implement_date + '\'' +
                ", pdf='" + pdf + '\'' +
                ", drafting_unit='" + drafting_unit + '\'' +
                ", drafter='" + drafter + '\'' +
                ", scope='" + scope + '\'' +
                ", reference_file='" + reference_file + '\'' +
                ", reference_file_pdf='" + reference_file_pdf + '\'' +
                ", reference=" + reference +
                ", term_definition='" + term_definition + '\'' +
                ", term_definition_list=" + term_definition_list +
                '}';
    }
}
