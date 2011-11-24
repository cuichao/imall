package com.eleven7.imall.dao.base.sqlcondition;

/**
 * DOCUMENT ME!
 *
 * @author cuichao
 * @version 1.0
 */
public class NotCondition extends CompareCondition {
    /**
     * Creates a new NotCondition object.
     *
     * @param propertyName DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public NotCondition(String propertyName, Object value) {
        super(propertyName, value);
    }
}
