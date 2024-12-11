package com.jilaba.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jilaba.exception.JilabaException;
import com.jilaba.sqlaction.ConnectionEnum.ServerType;

public class DbUtils {
	private String  sqlQuery = ""; 
	private Statement statement = null;
	private ResultSet resultSet = null;
	ReturnStatus returnStatus = new ReturnStatus();
	public boolean isDbExists(String dbName,Connection connection,ServerType serverType) throws JilabaException{
		try{
			
			if(serverType == ServerType.MYSQL){
				returnStatus = checkMySqlDb(dbName, connection);
				statusCheck(returnStatus);
				return (Boolean) returnStatus.getReturnObject();
			}else if(serverType == ServerType.MSSQL){
				returnStatus = checkMsSqlDb(dbName, connection);
				statusCheck(returnStatus);
				return (Boolean) returnStatus.getReturnObject(); 
			}else{
				throw new JilabaException("This Server Option Not Available");
			}
			
		}catch(JilabaException e){
			throw e;
		}
	}
	
	private void statusCheck(ReturnStatus returnStatus){
		if(!returnStatus.isStatus()) 
			throw new JilabaException(returnStatus.getDescription());
	}
	
	private ReturnStatus checkMsSqlDb(String dbName,Connection connection){
		try{
			
			sqlQuery = "Select Name From Master.Dbo.SysDatabases Where Name='" + dbName + "'";
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery(sqlQuery);
			
			if(resultSet.next()) 
				return new ReturnStatus(true, true);
			
			return new ReturnStatus(true, false);
		}catch(SQLException e){
			return new ReturnStatus(false, e.getMessage(),e);
		}
	}
	private ReturnStatus checkMySqlDb(String dbName,Connection connection){
		try{
			
			sqlQuery = "SELECT * FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME='" + dbName + "'";
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery(sqlQuery);
			
			if(resultSet.next()) 
				return new ReturnStatus(true, true);
			
			return new ReturnStatus(true, false);
		}catch(SQLException e){
			return new ReturnStatus(false, e.getMessage(),e);
		}
	}
}
