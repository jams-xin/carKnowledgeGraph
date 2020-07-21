package com.tju.carknowledge.domain;

import java.util.List;

/**
 * @ClassName EsIndustryBean
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/6/28 16:43
 * @Version 1.0
 **/
public class EsIndustryBean {
//    private String uuid;
//    private String title;
    private String content;
    private String html;
    private String articleUrl;
    private String origin;
    private String originAuthor;
    private String publishTime;
    private String pictureUrl;
    private List absPictureUrl;

    public String getHtml() {
        return html;
    }

    public List getAbsPictureUrl() {
        return absPictureUrl;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void setAbsPictureUrl(List absPictureUrl) {
        this.absPictureUrl = absPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() {

        return pictureUrl;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setOriginAuthor(String originAuthor) {
        this.originAuthor = originAuthor;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }


    public String getContent() {
        return content;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public String getOrigin() {
        return origin;
    }

    public String getOriginAuthor() {
        return originAuthor;
    }

    public String getPublishTime() {
        return publishTime;
    }

    @Override
    public String toString() {
        return "EsIndustryBean{" +
                "content='" + content + '\'' +
                ", html='" + html + '\'' +
                ", articleUrl='" + articleUrl + '\'' +
                ", origin='" + origin + '\'' +
                ", originAuthor='" + originAuthor + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", absPictureUrl=" + absPictureUrl +
                '}';
    }
}
