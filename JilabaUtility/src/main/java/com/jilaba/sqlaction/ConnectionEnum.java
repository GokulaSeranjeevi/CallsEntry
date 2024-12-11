package com.jilaba.sqlaction;

public class ConnectionEnum {
	public enum ConnectionDbType {
		SQLMASTER,MAINDB,TRANDB,LOGDB,SCHEMEDB,MESSAGEDB,ADDRESSDB,COMPYDB;
	}
	public enum ConnectionType {
		SOURCE,DESTINATION,REDB
	}
	public enum ServerType {
		MSSQL,
		MYSQL,
		ORACLE
	}
}
