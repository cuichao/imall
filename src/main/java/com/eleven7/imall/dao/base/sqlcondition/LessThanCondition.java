package com.eleven7.imall.dao.base.sqlcondition;

/**
 * DOCUMENT ME!
 *
 * @author cuichao
 * @version 1.0
 */
public class LessThanCondition extends CompareCondition {
    /**
     * Creates a new LessThanCondition object.
     *
     * @param propertyName DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public LessThanCondition(String propertyName, Object value) {
        super(propertyName, value);
    }
}
