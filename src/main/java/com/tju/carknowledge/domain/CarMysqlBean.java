package com.tju.carknowledge.domain;

/**
 * @ClassName CarMysqlBean
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/7/6 9:29
 * @Version 1.0
 **/
public class CarMysqlBean {
    private String uuid;
    private String wikiTitle;
    private String wikiContent;
    private String regulationTitle;
    private String regulationContent;
    private String productTitle;
    private String productContent;

    public String getUuid() {
        return uuid;
    }

    public String getWikiTitle() {
        return wikiTitle;
    }

    public String getWikiContent() {
        return wikiContent;
    }

    public String getRegulationTitle() {
        return regulationTitle;
    }

    public String getRegulationContent() {
        return regulationContent;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public String getProductContent() {
        return productContent;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setWikiTitle(String wikiTitle) {
        this.wikiTitle = wikiTitle;
    }

    public void setWikiContent(String wikiContent) {
        this.wikiContent = wikiContent;
    }

    public void setRegulationTitle(String regulationTitle) {
        this.regulationTitle = regulationTitle;
    }

    public void setRegulationContent(String regulationContent) {
        this.regulationContent = regulationContent;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setProductContent(String productContent) {
        this.productContent = productContent;
    }

    @Override
    public String toString() {
        return "CarMysqlBean{" +
                "uuid='" + uuid + '\'' +
                ", wikiTitle='" + wikiTitle + '\'' +
                ", wikiContent='" + wikiContent + '\'' +
                ", regulationTitle='" + regulationTitle + '\'' +
                ", regulationContent='" + regulationContent + '\'' +
                ", productTitle='" + productTitle + '\'' +
                ", productContent='" + productContent + '\'' +
                '}';
    }
}
