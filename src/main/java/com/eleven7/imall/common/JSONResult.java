package com.eleven7.imall.common;

import com.eleven7.imall.dao.base.PageBean;


/**
 * DOCUMENT ME!
 *
 * @version 1.0
 */
public class JSONResult {
    private final boolean error;
    private final String message;
    private final Object data;
    private final PageBean pageBean;

    /**
     * Creates a new JSONResult object.
     *
     * @param data DOCUMENT ME!
     */
    public JSONResult(Object data) {
        this(data, null);
    }

    /**
     * Creates a new JSONResult object.
     *
     * @param message DOCUMENT ME!
     */
    public JSONResult(String message) {
        this(true, message, null);
    }

    /**
     * Creates a new JSONResult object.
     *
     * @param data DOCUMENT ME!
     * @param pageBean DOCUMENT ME!
     */
    public JSONResult(Object data, PageBean pageBean) {
        this(false, null, data, pageBean);
    }

    /**
     * Creates a new JSONResult object.
     *
     * @param error DOCUMENT ME!
     * @param message DOCUMENT ME!
     * @param data DOCUMENT ME!
     */
    public JSONResult(boolean error, String message, Object data) {
        this(error, message, data, null);
    }

    /**
     * Creates a new JSONResult object.
     *
     * @param error DOCUMENT ME!
     * @param message DOCUMENT ME!
     * @param data DOCUMENT ME!
     * @param pageBean DOCUMENT ME!
     */
    public JSONResult(boolean error, String message, Object data, PageBean pageBean) {
        this.error = error;
        this.message = message;
        this.data = data;
        this.pageBean = pageBean;
    }

    /**
     * <p>
     * Gets the error.
     * </p>
     *
     * @return the error
     */
    public boolean isError() {
        return error;
    }

    /**
     * <p>
     * Gets the message.
     * </p>
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * <p>
     * Gets the data.
     * </p>
     *
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * <p>
     * Gets the pageBean.
     * </p>
     *
     * @return the pageBean
     */
    public PageBean getPageBean() {
        return pageBean;
    }
}
