package com.rwy.spider.service.base;

import com.rwy.spider.web.common.QueryResult;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;


public interface DAO<T> {
    /**
     * 获取记录总数
     * @return
     */
    public long getCount();
    /**
     * 清除一级缓存的数据
     */
    public void clear();
    /**
     * 保存实体
     * @param entity 实体
     */
    public void save(T entity);

    /**
     * 批量保存实体
     * @param entitys 实体集合
     */
    public void save(List<T> entitys);
    /**
     * 更新实体
     * @param entity 实体id
     */
    public void update(T entity);
    /**
     * 批量更新实体
     * @param entitys 实体id
     */
    public void update(List<T> entitys);
    /**
     * 删除实体
     * @param entityids 实体id数组
     */
    public void delete(Serializable ... entityids);
    /**
     * 获取实体
     * @param entityId 实体id
     * @return
     */
    public T find(Serializable entityId);

    /**
     * 获取分页数据
     * @param firstindex 开始索引
     * @param maxresult 需要获取的记录数
     * @return
     */
    public QueryResult<T> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby);

    public QueryResult<T> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams);

    public QueryResult<T> getScrollData(int firstindex, int maxresult, LinkedHashMap<String, String> orderby);

    public QueryResult<T> getScrollData(int firstindex, int maxresult);

    /**
     * 根据自定义JPQL查询获取分页数据
     * @param firstindex
     * @param maxresult
     * @param wherejpql
     * @param queryParams
     * @param orderby
     * @return
     */
    public QueryResult<T> getCustomerScrollData(int firstindex, int maxresult, String wherejpql, String countJpql ,Object[] queryParams,LinkedHashMap<String, String> orderby);

    public QueryResult<T> getScrollData();
}
