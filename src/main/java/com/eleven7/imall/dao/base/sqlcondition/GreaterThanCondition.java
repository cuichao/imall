package com.eleven7.imall.dao.base.sqlcondition;
/**
 * DOCUMENT ME!
 *
 * @author cuichao
 * @version 1.0
 */
public class GreaterThanCondition extends CompareCondition {
    /**
     * Creates a new GreaterThanCondition object.
     *
     * @param propertyName DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public GreaterThanCondition(String propertyName, Object value) {
        super(propertyName, value);
    }
}
