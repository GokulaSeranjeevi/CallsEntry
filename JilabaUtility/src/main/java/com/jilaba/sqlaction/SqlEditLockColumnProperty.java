package com.jilaba.sqlaction;

public class SqlEditLockColumnProperty {
	private String columnName = "";
	private Object columnValue = null;
	
	public SqlEditLockColumnProperty(String columnName,Object columnValue){
		this.columnName = columnName;
		this.columnValue = columnValue;
	}

	public String getColumnName() {
		return columnName;
	}
	public Object getColumnValue() {
		return columnValue;
	}
}
