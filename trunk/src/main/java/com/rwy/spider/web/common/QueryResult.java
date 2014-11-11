package com.rwy.spider.web.common;

import java.util.List;

public class QueryResult<T> {
    private List<T> resultlist;
    private long totalrecord;
    private T singleResult;

    public List<T> getResultlist() {
        return resultlist;
    }
    public void setResultlist(List<T> resultlist) {
        this.resultlist = resultlist;
    }
    public long getTotalrecord() {
        return totalrecord;
    }
    public void setTotalrecord(long totalrecord) {
        this.totalrecord = totalrecord;
    }

    public T getSingleResult() {
        return resultlist.get(0);
    }
    public void setSingleResult(T singleResult) {
        this.singleResult = singleResult;
    }
}
