package com.jilaba.sqlaction;

import java.util.List;

public class SqlEditLock {
	private String tableName = "";
	private List<SqlEditLockColumnProperty> listSqlEditLockColumnProperty = null;
	
	public SqlEditLock(String tableName,List<SqlEditLockColumnProperty> listSqlEditLockColumnProperty){
		this.tableName = tableName;
		this.listSqlEditLockColumnProperty = listSqlEditLockColumnProperty;
	}

	public String getTableName() {
		return tableName;
	}

	public List<SqlEditLockColumnProperty> getListSqlEditLockColumnProperty() {
		return listSqlEditLockColumnProperty;
	}
}
