package com.teamwork.integrationproject.common.repository;

import com.teamwork.integrationproject.common.repository.orm.Page;

import java.util.List;


public interface IRepository<M, K>
{
    /**
     * 保存
     */
    void save(M model) throws Exception;

    /**
     * 批量保存
     */
    void save(List<M> model) throws Exception;

    /**
     * 更新
     */
    void update(M model) throws Exception;

    /**
     * 删除(根据主键)
     */
    void delete(M model) throws Exception;

    /**
     * 根据主键删除
     */
    void deleteByPrimaryKey(Class<M> clazz, K key) throws Exception;

    /**
     * 根据主键查询
     */
    M selectByPrimaryKey(Class<M> clazz, K key) throws Exception;


    /**
     * 自定义语句的查询
     *
     * @param clazz 自定义查询结果的类
     * @param sql   自定义查询的sql语句,支持参数，不允许使用SQL拼接
     * @throws Exception
     */
    <T> List<T> selectByNative(Class<T> clazz, String sql, Object... params) throws Exception;

    /**
     * 分页查询
     *
     * @param clazz      查询结果的类
     * @param current    当前页，正整数
     * @param pageSize   页大小，正整数
     * @param totalCount 总数，初始化时为-1，其他为整数，当为-1时查询总数
     */
    <T> Page<T> selectByPage(Class<T> clazz, int current, int pageSize, int totalCount, String orderBy) throws Exception;

    /**
     * 自定义分页查询
     *
     * @param clazz      查询结果的类
     * @param current    当前页，正整数
     * @param pageSize   页大小，正整数
     * @param totalCount 总数，初始化时为-1，其他为整数，当为-1时查询总数
     * @param sql        自定义查询的语句
     * @param params     查询语句的参数
     * @return
     * @throws Exception
     */
    <T> Page<T> selectByPageNative(Class<T> clazz, int current, int pageSize, int totalCount, String sql,
                                   Object... params) throws Exception;

    /**
     * 取所有
     *
     * @return
     */
    List<M> selectAll(Class<M> clazz) throws Exception;
}
