package com.jilaba.fileresource;

import com.jilaba.sqlaction.ConnectionEnum.ServerType;

public class HOServer {
	
	private static String strServerName = "";
	private static String strUserName = "";
	private static String strPassword ;
	private static long lngPortNo = 0;
	private static String strDatabasePath = "";
	private static ServerType serverType = ServerType.MYSQL;
	
	private HOServer(){
		
	}
	
	public static String getServerName() {
		return strServerName;
	}
	public static void setServerName(String strServerName) {
		HOServer.strServerName = strServerName;
	}
	public static String getUserName() {
		return strUserName;
	}
	public static void setUserName(String strUserName) {
		HOServer.strUserName = strUserName;
	}
	public static String getPassword() {
		if(strPassword == null || strPassword.isEmpty()){
			return "";
		}
		return strPassword;
	}
	public static void setPassword(String strPassword) {
		HOServer.strPassword = strPassword;
	}
	public static long getPortNo() {
		return lngPortNo;
	}
	public static void setPortNo(long lngPortNo) {
		HOServer.lngPortNo = lngPortNo;
	}
	public static String getDatabasePath() {
		return strDatabasePath;
	}
	public static void setDatabasePath(String strDatabasePath) {
		HOServer.strDatabasePath = strDatabasePath;
	}
	public static ServerType getServerType() {
		return serverType;
	}
	public static void setServerType(ServerType serverType) {
		HOServer.serverType = serverType;
	}
	
	
}
