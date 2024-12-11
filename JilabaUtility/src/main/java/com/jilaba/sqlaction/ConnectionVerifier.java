package com.jilaba.sqlaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.sqlaction.ConnectionEnum.ServerType;

public class ConnectionVerifier {

	/**
	 * @author sjmani
	 * @param serverType
	 *            [mysql or mssql] defined in ServerType enum
	 * @param serverName
	 * @param portNo
	 * @param userName
	 * @param password
	 *            Decrypted Password
	 * @return ReturnStatus
	 */
	public static ReturnStatus testConnection(ServerType serverType, String serverName, String portNo, String user,
			String password) {
		Connection connection;
		String url = "";
		String connSettings = "?allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true";
		try {
			if (serverType == ServerType.MYSQL) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				url = "jdbc:mysql://" + serverName;
				url += ":" + portNo + "/mysql" + connSettings;
			} else if (serverType == ServerType.MSSQL) {
				DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
				url = "jdbc:sqlserver://" + serverName;
				url += ":" + portNo + ";databaseName=master";
			}

			connection = DriverManager.getConnection(url, user, password);
			connection.close();

			return new ReturnStatus(true);
		} catch (SQLException e) {
			/*if (e.getErrorCode() == 0)
				return new ReturnStatus(false, "Incorrect Server Details (Or) Server Not Connected With Client System");*/
			if (e.getErrorCode() == 1045 || e.getErrorCode() == 18456)
				return new ReturnStatus(false, "Incorrect UserName / Password");

			return new ReturnStatus(false, ErrorHandling.handleError(e));

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}
}
