package com.jilaba.fileresource;

import com.jilaba.sqlaction.ConnectionEnum.ServerType;

public class Server {
	
	private static String strServerName = "";
	private static String strUserName = "";
	private static String strPassword ;
	private static long lngPortNo = 0;
	private static String strDatabasePath = "";
	private static ServerType serverType = ServerType.MYSQL;
	
	private Server(){
		
	}
	
	public static String getServerName() {
		return strServerName;
	}
	public static void setServerName(String strServerName) {
		Server.strServerName = strServerName;
	}
	public static String getUserName() {
		return strUserName;
	}
	public static void setUserName(String strUserName) {
		Server.strUserName = strUserName;
	}
	public static String getPassword() {
		if(strPassword == null || strPassword.isEmpty()){
			return "";
		}
		return strPassword;
	}
	public static void setPassword(String strPassword) {
		Server.strPassword = strPassword;
	}
	public static long getPortNo() {
		return lngPortNo;
	}
	public static void setPortNo(long lngPortNo) {
		Server.lngPortNo = lngPortNo;
	}
	public static String getDatabasePath() {
		return strDatabasePath;
	}
	public static void setDatabasePath(String strDatabasePath) {
		Server.strDatabasePath = strDatabasePath;
	}
	public static ServerType getServerType() {
		return serverType;
	}
	public static void setServerType(ServerType serverType) {
		Server.serverType = serverType;
	}
	
	
}
