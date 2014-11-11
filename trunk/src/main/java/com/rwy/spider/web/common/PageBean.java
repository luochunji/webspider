package com.rwy.spider.web.common;

/**
 * Created by Luocj on 2014/10/29.
 */
public class PageBean {

    private int page;

    public int getPage() {
        return page<1?1:page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
