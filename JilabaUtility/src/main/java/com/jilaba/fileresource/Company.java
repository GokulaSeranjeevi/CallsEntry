package com.jilaba.fileresource;

import com.jilaba.sqlaction.ConnectionEnum.ServerType;

public class Company {
	
	private static String companyCode = "";
	private static String companyName = "";
	private static String serverName = "";
	private static String userName = "";
	private static String password;
	private static long portNo = 0;
	private static String databasePath = "";
	private static ServerType serverType = ServerType.MSSQL; 
	
	
	private Company(){
		
	}
	
	public static ServerType getServerType() {
		return serverType;
	}
	public static void setServerType(ServerType serverType) {
		Company.serverType = serverType;
	}
	public static String getCompanyCode() {
		return companyCode;
	}
	public static void setCompanyCode(String strCompanyCode) {
		Company.companyCode = strCompanyCode;
	}
	public static String getCompanyName() {
		return companyName;
	}
	public static void setCompanyName(String strCompanyName) {
		Company.companyName = strCompanyName;
	}
	public static String getServerName() {
		return serverName;
	}
	public static void setServerName(String strServerName) {
		Company.serverName = strServerName;
	}
	public static String getUserName() {
		return userName;
	}
	public static void setUserName(String strUserName) {
		Company.userName = strUserName;
	}
	public static String getPassword() {
		if(password == null || password.isEmpty()){
			return "";
		}
		return password;
	}
	public static void setPassword(String strPassword) {
		Company.password = strPassword;
	}
	public static long getPortNo() {
		return portNo;
	}
	public static void setPortNo(long lngPortNo) {
		Company.portNo = lngPortNo;
	}
	public static String getDatabasePath() {
		return databasePath;
	}
	public static void setDatabasePath(String strDatabasePath) {
		Company.databasePath = strDatabasePath;
	}
}
