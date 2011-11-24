package com.eleven7.imall.dao.base.sqlcondition;

/**
 * DOCUMENT ME!
 *
 * @author cuichao
 * @version 1.0
 */
public class CompareCondition {
    private String propertyName;
    private Object minValue;
    private Object maxValue;
    private Object value;

    /**
     * Creates a new CompareCondition object.
     *
     * @param propertyName DOCUMENT ME!
     */
    protected CompareCondition(String propertyName, Object...objs) {
        this.propertyName = propertyName;

        switch (objs.length) {
        case 1:
            value = objs[0];

            break;

        case 2:
            minValue = objs[0];
            maxValue = objs[1];

            break;

        default:
                 throw new IllegalArgumentException("构建器参数个数不支持！");
        }
    }

    public Object getMaxValue() {
        return maxValue;
    }

    public Object getMinValue() {
        return minValue;
    }

    public Object getValue() {
        return value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setMaxValue(Object maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(Object minValue) {
        this.minValue = minValue;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
