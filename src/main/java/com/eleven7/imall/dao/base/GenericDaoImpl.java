package com.eleven7.imall.dao.base;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.eleven7.imall.dao.base.sqlcondition.BetweenCondition;
import com.eleven7.imall.dao.base.sqlcondition.CompareCondition;
import com.eleven7.imall.dao.base.sqlcondition.GreaterThanCondition;
import com.eleven7.imall.dao.base.sqlcondition.LessThanCondition;
import com.eleven7.imall.dao.base.sqlcondition.NotCondition;


/**
 * Hibernate的范型基类.
 * 
 * 可以在service类中直接创建使用.也可以继承出DAO子类,在多个Service类中共享DAO操作.
 * 参考Spring2.5自带的Petlinc例子,取消了HibernateTemplate.
 * 通过Hibernate的sessionFactory.getCurrentSession()获得session,直接使用Hibernate原生API.
 * 
 * @param <T>
 *            DAO操作的对象类型
 * @param <PK>
 *            主键类型
 * 
 * @author cuichao
 */
@SuppressWarnings("unchecked")
public class GenericDaoImpl<T, PK extends Serializable> implements
        GenericDao<T, PK> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /** Hibernate Session工厂 */
    private HibernateTemplate hibernateTemplate;

    /** 是否允许 */
    private final boolean cachable;

    /** 实体类型 */
    private final Class<T> entityClass;

    /**
     * <p>
     * Sets the hibernateTemplate.
     * </p>
     *
     * @param hibernateTemplate the hibernateTemplate to set
     */
    @Autowired
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    /**
     * 构造器.
     * 
     * @param entityClass
     *            实体类型
     */
    public GenericDaoImpl(Class<T> entityClass) {
        this(entityClass, false);
    }

    /**
     * 构造器.
     * 
     * @param entityClass
     *            实体类型
     * @param cachable
     *            是否允许缓存
     */
    public GenericDaoImpl(Class<T> entityClass,
            boolean cachable) {
        this.entityClass = entityClass;
        this.cachable = cachable;
    }

    public void save(T entity) {
        hibernateTemplate.save(entity);
    }

    public void update(T entity) {
        hibernateTemplate.merge(entity);
    }

    public T merge(T entity) {
        return (T) hibernateTemplate.merge(entity);
    }

    public void saveOrUpdate(T entity) {
        hibernateTemplate.saveOrUpdate(entity);
    }

    public void saveOrUpdateAll(Collection<T> objects) {
        if (objects.isEmpty()) {
            return;
        }
        hibernateTemplate.saveOrUpdateAll(objects);
    }

    public void delete(T entity) {
        hibernateTemplate.delete(entity);
    }

    public void delete(PK id) {
        delete(get(id));
    }

    public void deleteBatch(Collection<T> objects) {
        if (objects.isEmpty()) {
            return;
        }
        hibernateTemplate.deleteAll(objects);
    }

    public T get(final PK id) {
        return (T) hibernateTemplate.get(entityClass, id);
    }

    public List<T> findAll() {
        return findAll(null);
    }

    public List<T> findAll(PageBean pageBean) {
        return this.findByProperties(pageBean, null);
    }

    public List<T> find(String hql, Object... values) {
        return find(null, hql, values);
    }

    public <R> List<R> findObjects(String hql, Object... values) {
        return findObjects(null, hql, values);
    }

    private Query setParams(Query query, Object... values) {
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    private Query setParams(Query query, Map<String, Object> properties) {
        if (properties != null) {
            for (Entry<String, Object> entry : properties.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (value instanceof Collection) {
                    Collection collection = (Collection) value;
                    query.setParameterList(key, collection);

                } else{
                    query.setParameter(key, value);
                }
            }
        }

        return query;
    }

    /**
     * 将hql转化为获取count的语句
     * 如果hql出现fetch，且select中未出现fetch后的对象，会出现org.hibernate.QueryException: query
     * specified join fetching, but the owner of the fetched association was not
     * present in the select list 在hql中将fetch滤掉 使用hsqldb进行测试的时候，order by
     * 子句会报错。这里把order by 删除掉
     * 
     * @param hql
     * @return
     */
    private String getCountHql(String hql) {
        int pos1 = hql.indexOf(" ");
        int pos2 = hql.indexOf("from", pos1);
        
        String target = "count(*)";
        
        if (pos1 >= 0 && pos2 >= 0) {
            String value = hql.substring(pos1, pos2).trim();

            if (value.contains("distinct")) {
                target = "count(" + value + ")";
            }   
        }
        
        String countHql = "select " + target + " "
                + hql.substring(hql.indexOf("from"));
        countHql = StringUtils.replace(countHql, " fetch ", " ");
        if (countHql.toUpperCase().indexOf(" ORDER ") >= 0) {
            countHql = StringUtils.substring(countHql, 0, countHql
                    .toUpperCase().indexOf(" ORDER ") + 1);
        }
        return countHql;
    }

    public List<T> find(final PageBean pageBean, final String hql, final Object... values) {
        return findObjects(pageBean, hql, values);
    }

    public <R> List<R> findObjects(final PageBean pageBean, final String hql, final Object... values) {
        return (List) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = null;
                
                if (pageBean != null) {
                    if (!pageBean.isShowAll() && pageBean.isAutoCount()) {
                        String countHql = getCountHql(hql);
                        
                        Query countQuery = session.createQuery(countHql);
                        countQuery.setCacheable(cachable); // 设置允许开启查询缓存
                        
                        countQuery = setParams(countQuery, values);
    
                        Integer count = Integer.parseInt(countQuery.uniqueResult() + "");
                        pageBean.setTotal(count);
                    }

                    StringBuilder orderBy = new StringBuilder();
                    
                    for (String ascOrder : pageBean.getAscOrders()) {
                        orderBy.append(ascOrder + " asc,");
                    }
                    for (String descOrder : pageBean.getDescOrders()) {
                        orderBy.append(descOrder + " desc,");
                    }
                    
                    if (orderBy.length() > 0) {
                        orderBy.delete(orderBy.length() - 1, orderBy.length());
                    }

                    query = session.createQuery(hql + (orderBy.length() == 0 ? "" : " order by " + orderBy)); 

                    if (!pageBean.isShowAll()) {
                        query.setFirstResult(pageBean.getFirst()).setMaxResults(pageBean.getSize());
                    }
                } else {
                    query = session.createQuery(hql); 
                } 

                query = setParams(query, values);
                query.setCacheable(cachable); // 设置允许开启查询缓存

                return query.list();
            }
        });
    }

    public <R> R findUnique(final String hql, final Object... values) {
        return (R) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setCacheable(cachable); // 设置允许开启查询缓存

                query = setParams(query, values);

                return (R) query.uniqueResult();
            }
        });
    }

    public Integer findInt(String hql, Object... values) {
        return (Integer) findUnique(hql, values);
    }

    public Long findLong(String hql, Object... values) {
        return (Long) findUnique(hql, values);
    }

    public List<T> findByProperty(String propertyName, Object value) {
        return findByProperty(null, propertyName, value);
    }

    public List<T> findByProperty(PageBean pageBean, String propertyName, Object value) {
        Map<String, Object> properties = new HashMap<String, Object>();
        
        properties.put(propertyName, value);
        
        return findByProperties(pageBean, properties);
    }

    public T findUniqueByProperty(String propertyName, Object value) {
        Map<String, Object> properties = new HashMap<String, Object>();
        
        properties.put(propertyName, value);
        
        return findUniqueByProperties(properties);
    }

    public List<T> findByProperties(Map<String, Object> properties) {
        return findByProperties(null, properties);
    }

    private Criterion constructCriteriaValue(String propertyName, Object value) {
        Criterion crit = null;
        
        if (value == null) {
            crit = Restrictions.isNull(propertyName);
        } else if (value instanceof Object[]) {
            crit = Restrictions.in(propertyName, (Object[]) value);
        } else if (value instanceof Collection) {
            crit = Restrictions.in(propertyName, (Collection) value);
        } else if (value instanceof CompareCondition) {
            if (value instanceof NotCondition) {
                crit = Restrictions.not(constructCriteriaValue(((NotCondition) value).getPropertyName(), ((NotCondition) value).getValue()));
            } else if (value instanceof LessThanCondition) {
                crit = Restrictions.lt(((LessThanCondition) value).getPropertyName(), ((LessThanCondition) value).getValue());
            } else if (value instanceof GreaterThanCondition) {
                crit = Restrictions.gt(((GreaterThanCondition) value).getPropertyName(), ((GreaterThanCondition) value).getValue());
            }else if(value instanceof BetweenCondition){
            	crit = Restrictions.between(((BetweenCondition) value).getPropertyName(), ((BetweenCondition) value).getMinValue(), ((BetweenCondition) value).getMaxValue());
            }       
        } else {
            crit = Restrictions.eq(propertyName, value);
        }
        
        return crit;
    }

    public List<T> findByProperties(final PageBean pageBean, final Map<String, Object> properties) {
        return (List) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(entityClass);

                criteria.setCacheable(cachable); // 设置允许开启查询缓存

                if (properties != null) {
                    for (Map.Entry<String,Object> entry : properties.entrySet()) {
                        String propertyName = entry.getKey();
                        Object value = entry.getValue();
                        criteria.add(constructCriteriaValue(propertyName, value));
                    }
                }
                
                if (pageBean != null) {
                    if (!pageBean.isShowAll()) {
                        if (pageBean.isAutoCount()) {
                            criteria.setProjection(Projections.rowCount());
                            Integer total = Integer.parseInt(criteria.uniqueResult()+ "");                       
                            pageBean.setTotal(total);
                            criteria.setProjection(null);
                        }
                        
                        criteria.setFirstResult(pageBean.getFirst()).setMaxResults(pageBean.getSize());
                    }
                    
                    for (String ascOrder : pageBean.getAscOrders()) {
                        criteria.addOrder(Order.asc(ascOrder));
                    }
                    for (String descOrder : pageBean.getDescOrders()) {
                        criteria.addOrder(Order.desc(descOrder));
                    }
                }
                
                return criteria.list();
            }
        });
    }

    public int findCountByProperties(final Map<String, Object> properties) {
        return (Integer) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(entityClass);

                criteria.setCacheable(cachable); // 设置允许开启查询缓存

                if (properties != null) {
                    for (Map.Entry<String,Object> entry : properties.entrySet()) {
                        String propertyName = entry.getKey();
                        Object value = entry.getValue();
                        criteria.add(constructCriteriaValue(propertyName, value));
                    }
                }

                criteria.setProjection(Projections.rowCount());
                
                Object value = criteria.uniqueResult();
                return value == null ? 0 : Integer.parseInt(value.toString());
            }
        });
    }

    public T findUniqueByProperties(final Map<String, Object> properties) {
        return (T) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(entityClass);

                criteria.setCacheable(cachable); // 设置允许开启查询缓存

                if (properties != null) {
                    for (Map.Entry<String,Object> entry : properties.entrySet()) {
                        String propertyName = entry.getKey();
                        Object value = entry.getValue();
                        criteria.add(constructCriteriaValue(propertyName, value));
                    }
                }
                
                return (T) criteria.uniqueResult();
            }
        });
    }

    public boolean isPropertyUnique(String propertyName, Object newValue,
            Object orgValue) {
        if (newValue == null || newValue.equals(orgValue)) {
            return true;
        }

        Object object = findUniqueByProperty(propertyName, newValue);
        return (object == null);
    }

    public int executeUpdate(final String hql, final Map<String, Object> properties) {
        return (Integer) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                
                query = setParams(query, properties);

                return query.executeUpdate();
            }
        });
    }

    public int executeNativeSql(final String sql) {
        return (Integer) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql);

                return query.executeUpdate();
            }
        });
    }
}