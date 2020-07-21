package com.tju.carknowledge.domain;

import java.util.List;

/**
 * @ClassName EsRegulationBean
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/7/1 20:04
 * @Version 1.0
 **/
public class EsRegulationBean {
    private String uuid;
    private String subuuid;
    private String title;
    private String standard_number;
    private String pdf;
    private String reference_file;
    private String reference_file_pdf;
    private String reference_file_number;

    public String getUuid() {
        return uuid;
    }

    public String getSubuuid() {
        return subuuid;
    }

    public String getTitle() {
        return title;
    }

    public String getPdf() {
        return pdf;
    }

    public String getReference_file() {
        return reference_file;
    }

    public String getReference_file_pdf() {
        return reference_file_pdf;
    }

    public String getReference_file_number() {
        return reference_file_number;
    }

    public String getStandard_number() {
        return standard_number;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setSubuuid(String subuuid) {
        this.subuuid = subuuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public void setReference_file(String reference_file) {
        this.reference_file = reference_file;
    }

    public void setReference_file_pdf(String reference_file_pdf) {
        this.reference_file_pdf = reference_file_pdf;
    }

    public void setReference_file_number(String reference_file_number) {
        this.reference_file_number = reference_file_number;
    }

    public void setStandard_number(String standard_number) {
        this.standard_number = standard_number;
    }

    @Override
    public String toString() {
        return "EsRegulationBean{" +
                "uuid='" + uuid + '\'' +
                ", subuuid='" + subuuid + '\'' +
                ", title='" + title + '\'' +
                ", standard_number='" + standard_number + '\'' +
                ", pdf='" + pdf + '\'' +
                ", reference_file='" + reference_file + '\'' +
                ", reference_file_pdf='" + reference_file_pdf + '\'' +
                ", reference_file_number='" + reference_file_number + '\'' +
                '}';
    }



}
