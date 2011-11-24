package com.eleven7.imall.dao.base;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * DAO基类接口.
 * 
 * @author cuichao
 *
 * @param <T>
 * @param <PK>
 */
public interface GenericDao<T, PK extends Serializable> {

    /**
     * 保存一个对象.
     * @param entity 待保存的实体
     */
    public void save(T entity);
    
    /**
     * 更新一个对象
     * @param entity
     */
    public void update(T entity);
    
    /**
     * 更新一个对象
     * @param entity
     */
    public T merge(T entity);
    
    /**
     * 更新一个对象
     * @param entity
     */
    public void saveOrUpdate(T entity);

    /**
     * 批量保存或更新
     * 
     * @param objects Collection
     */
    public void saveOrUpdateAll(Collection<T> objects);

    /**
     * 根据ID删除一个对象.
     * @param id 待删除的ID
     */
    public void delete(PK id);
    
    /**
     * 删除一个对象.
     * @param entity 待删除实体
     */
    public void delete(T entity);

    /**
     * 批量删除
     * 
     * @param objects Collection
     */
    public void deleteBatch(Collection<T> objects);

    /**
     * 按id获取对象.
     * @param id 待获取对象的ID
     * @return 一个对象
     */
    public T get(final PK id);

    /**
     * 获取所有对象列表.
     * @return 对象列表
     */
    public List<T> findAll();

    /**
     * 根据分页对象返回一页对象.
     * @param page 分页对象
     * @return 一页对象
     */
    public List<T> findAll(PageBean pageBean);

    /**
     * 按HQL查询对象列表.
     * @param hql hql语句
     * @param values 数量可变的参数
     * @return 对象列表
     */
    public List<T> find(String hql, Object... values);

    /**
     * 按HQL查询对象列表.
     * @param hql hql语句
     * @param values 数量可变的参数
     * @return 对象列表
     */
    public <R> List<R> findObjects(String hql, Object... values);
    
    /**
     * 按HQL分页查询.
     * 暂不支持自动获取总结果数,需对象另行执行查询.
     * @param page 分页参数.包括pageSize 和firstResult.
     * @param hql hql语句.
     * @param values 数量可变的参数.
     * @return 分页查询结果,附带结果列表及所有查询时的参数.
     */
    public List<T> find(PageBean pageBean, String hql, Object... values);
    
    /**
     * 按HQL分页查询.
     * 暂不支持自动获取总结果数,需对象另行执行查询.
     * @param page 分页参数.包括pageSize 和firstResult.
     * @param hql hql语句.
     * @param values 数量可变的参数.
     * @return 分页查询结果,附带结果列表及所有查询时的参数.
     */
    public <R> List<R> findObjects(PageBean pageBean, String hql, Object... values);

    /**
     * 按HQL查询唯一对象.
     * @param hql hql语句
     * @param values 数量可变的参数
     * @return 对象
     */
    public <R> R findUnique(String hql, Object... values);

    /**
     * 按HQL查询Intger类形结果. 
     * @param hql hql语句
     * @param values 数量可变的参数
     * @return 整数
     */
    public Integer findInt(String hql, Object... values);

    /**
     * 按HQL查询Long类型结果.
     * @param hql hql语句
     * @param values 数量可变的参数
     * @return 长整型数
     */
    public Long findLong(String hql, Object... values);

    /**
     * 按属性查找对象列表.
     * @param propertyName 查询属性名称
     * @param value 查询值
     * @return 对象列表
     */
    public List<T> findByProperty(String propertyName, Object value);

    /**
     * 按属性查找对象列表.
     * @param propertyName 查询属性名称
     * @param value 查询值
     * @return 对象列表
     */
    public List<T> findByProperty(PageBean pageBean, String propertyName, Object value);
    
    /**
     * 按属性查找唯一对象.
     * @param propertyName 查询属性名称
     * @param value 查询值
     * @return 对象
     */
    public T findUniqueByProperty(String propertyName, Object value);

    /**
     * 按属性查找对象列表.
     * @param properties 查询属性名称/查询值
     * @return 对象列表
     */
    public List<T> findByProperties(Map<String, Object> properties);

    /**
     * 按属性查找对象列表.
     * @param properties 查询属性名称/查询值
     * @return 对象列表
     */
    public List<T> findByProperties(PageBean pageBean, Map<String, Object> properties);
    
    /**
     * 按属性查找数目.
     * @param propertyName 查询属性名称
     * @param value 查询值
     * @return 数目
     */
    public int findCountByProperties(Map<String, Object> properties);
    
    /**
     * 按属性查找唯一对象.
     * @param propertyName 查询属性名称
     * @param value 查询值
     * @return 对象
     */
    public T findUniqueByProperties(Map<String, Object> properties);

    /**
     * 判断对象的属性值在数据库内是否唯一.
     * <p>
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原值(orgValue)则不作比较.
     * 传回orgValue的设计侧重于从页面上发出Ajax判断请求的场景.
     * 否则需要SS2里那种以对象ID作为第3个参数的isUnique函数.
     * </p>
     * @param propertyName 属性名称
     * @param newValue 新值
     * @param orgValue 原始值
     * @return 如果属性值唯一则返回true，否则返回false
     */
    public boolean isPropertyUnique(String propertyName, Object newValue, Object orgValue);
    
    /**
     * 执行一条更新语句.
     * 可以是批量更新或者批量删除.
     * @param hql HQL语句
     * @param valueMap 命名参数的映射
     * @return 作用条数
     */
    public int executeUpdate(String hql, Map<String, Object> properties);

    /**
     * 执行一条更新语句.
     * 可以是批量更新或者批量删除.
     * @param sql Native SQL语句
     * @return 作用条数
     */
    public int executeNativeSql(String sql);

}
