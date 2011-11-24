package com.eleven7.imall.dao.base;

import java.util.LinkedList;
import java.util.List;


/**
 * 分页对象
 *
 * @author cuichao
 */
public class PageBean {
    /** 默认页面大小 */
    public static final int DEFAULT_SIZE = 10;

    /** DOCUMENT ME! */
    public static final int MIN_PAGESIZE = 4;

    /** DOCUMENT ME! */
    public static final int MAX_PAGESIZE = 5000;

    /** 总记录数，初始值-1以便之后进行校验 */
    private int total = -1;

    /** 当前页面号 */
    private int page;

    /** 每页大小 */
    private int size;

    /** 正向排序的属性列表 */
    private List<String> ascOrderList = new LinkedList<String>();

    /** 逆向排序的属性列表 */
    private List<String> descOrderList = new LinkedList<String>();

    /** 自动计数 */
    private boolean autoCount = true;

    /** 显示所有 */
    private boolean showAll = false;

    /**
     * 空值构造器.
     */
    public PageBean() {
        this(DEFAULT_SIZE, 1);
    }

    /**
     * 构造器.
     *
     * @param page 当前页面号
     */
    public PageBean(Integer page) {
        this(DEFAULT_SIZE, page);
    }

    /**
     * 构造器.
     *
     * @param size 页面大小
     * @param page 当前页面号
     */
    public PageBean(int size, Integer page) {
        this.size = (size < 1) ? DEFAULT_SIZE : size;
        this.page = ((page != null) && (page > 0)) ? page : 1;
    }

    /**
     * 获取总记录数.
     *
     * @return 总记录数
     */
    public int getTotal() {
        return total;
    }

    /**
     * 设置总记录数.
     *
     * @param total 总记录数
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * 获取总页数.
     *
     * @return 总页数
     */
    public int getTotalPages() {
        // 校验total值是否已被设置
        if (total <= 0) {
            return 0;
        }

        int count = total / size;

        if ((total % size) > 0) {
            count++;
        }

        return count;
    }

    /**
     * 获得每页的记录数量.
     *
     * @return 每页的记录数
     */
    public int getSize() {
        return size;
    }

    /**
     * <p>
     * Sets the size.
     * </p>
     *
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;

        if (size < MIN_PAGESIZE) {
            this.size = MIN_PAGESIZE;
        }

        if (size > MAX_PAGESIZE) {
            this.size = MAX_PAGESIZE;
        }
    }

    /**
     * 获得当前页的页号.
     *
     * @return 当前页号
     */
    public int getPage() {
        return page;
    }

    /**
     * <p>
     * Sets the page.
     * </p>
     *
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;

        if (page < 1) {
            this.page = 1;
        }
    }

    /**
     * 当前页第一条记录在总结果集中的位置,序号从0开始.
     *
     * @return 当前页第一条记录在结果集中的位置
     */
    public int getFirst() {
        int first = (page - 1) * size;

        return (first > total) ? total : first;
    }

    /**
     * 当前页最后一条记录在总结果集中的位置,序号从0开始.
     *
     * @return 当前页最后一条记录在结果集中的位置
     */
    public int getLast() {
        int last = page * size;

        return (last > total) ? total : last;
    }

    /**
     * 获得正向排序字段.
     *
     * @return 正向排序字段列表
     */
    public List<String> getAscOrders() {
        return ascOrderList;
    }

    /**
     * 加入一个正向排序字段.
     *
     * @param field 字段名称
     */
    public void addAscOrder(String field) {
        ascOrderList.add(field);
    }

    /**
     * 获得反向排序字段.
     *
     * @return 反向排序字段列表
     */
    public List<String> getDescOrders() {
        return descOrderList;
    }

    /**
     * 加入一个逆向排序字段.
     *
     * @param field 字段名称
     */
    public void addDescOrder(String field) {
        descOrderList.add(field);
    }

    /**
     * 翻转排序方向.
     */
    public void getInverseOrder() {
        List<String> swapOrderList = descOrderList;
        ascOrderList = descOrderList;
        descOrderList = swapOrderList;
    }

    /**
     * <p>
     * Gets the autoCount.
     * </p>
     *
     * @return the autoCount
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    /**
     * <p>
     * Sets the autoCount.
     * </p>
     *
     * @param autoCount the autoCount to set
     */
    public void setAutoCount(boolean autoCount) {
        this.autoCount = autoCount;
    }

    /**
     * <p>
     * Gets the showAll.
     * </p>
     *
     * @return the showAll
     */
    public boolean isShowAll() {
        return showAll;
    }

    /**
     * <p>
     * Sets the showAll.
     * </p>
     *
     * @param showAll the showAll to set
     */
    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isHasPre() {
        return this.page > 1;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isHasNext() {
        return this.page < this.getTotalPages();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @Override
    public String toString() {
        return new StringBuilder("PageBean: [").append("page=").append(page).append("size=").append(size)
                                               .append("total=").append(total).append("results=").append(getFirst())
                                               .append("-").append(getLast()).append("]").toString();
    }
}
