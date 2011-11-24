package com.eleven7.imall.dao.base.sqlcondition;

public class BetweenCondition extends CompareCondition{

	public BetweenCondition(String propertyName, Object minObj,Object maxObj) {
        super(propertyName,minObj,maxObj);
    }
}
