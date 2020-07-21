package com.tju.carknowledge.domain;

/**
 * @ClassName UserBean
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/7/1 10:27
 * @Version 1.0
 **/
public class UserBean {
    private String type;
    private String value;
    private int page;
    private String uuid;

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getPage() {
        return page;
    }

    public String getUuid() {
        return uuid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
