package com.jilaba.sqlaction;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.JilabaException;
import com.jilaba.sqlaction.ConnectionEnum.ServerType;

public class SqlConnection {
	private Connection con = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private String strDriver = "";
	private String strUrl = "";
	private ReturnStatus returnStatus;

	private String strDatabaseName = "";
	private ServerType serverType;
	private SqlEditLock sqlEditLock;

	private PreparedStatement preparedStatement;
	private CallableStatement callableStatement;

	public ReturnStatus connect(String dbName, String serverName, String portNo, String userName, String password,
			ServerType serverType) {
		try {
			if (serverType == ServerType.MYSQL) {
				returnStatus = mySqlConnect(dbName, serverName, portNo, userName, password);
			} else if (serverType == ServerType.MSSQL) {
				returnStatus = msSqlConnect(dbName, serverName, portNo, userName, password);
			} else if (serverType == ServerType.ORACLE) {
				returnStatus = oracleConnect(dbName, serverName, portNo, userName, password);
			} else {
				throw new JilabaException("This Server Type Not usable");
			}

			this.serverType = serverType;

			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus msSqlConnect(String dbName, String serverName, String portNo, String userName,
			String password) {
		try {

			if (dbName == null || dbName.equals(""))
				throw new JilabaException("Database Name Should Not be Empty");
			if (serverName == null || serverName.equals(""))
				throw new JilabaException("ServerName Should Not be Empty");
			if (portNo == null || portNo.equals(""))
				throw new JilabaException("PortNo Should Not be Empty");
			if (userName == null || userName.equals(""))
				throw new JilabaException("UserName Should Not be Empty");
			if (password == null)
				throw new JilabaException("Password Should Not be Null Value");

			strDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

			strUrl = "jdbc:sqlserver://" + serverName + ":" + portNo + ";databaseName=" + dbName;

			Class.forName(strDriver).newInstance();

			con = DriverManager.getConnection(strUrl, userName, password);

			strDatabaseName = dbName;

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (InstantiationException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (IllegalAccessException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus mySqlConnect(String dbName, String serverName, String portNo, String userName,
			String password) {
		try {
			String connSettings = "?useSSL=false&allowPublicKeyRetrieval=true&autoReconnect=true";

			if (serverName == null || serverName.equals(""))
				throw new JilabaException("ServerName Should Not be Empty");
			if (portNo == null || portNo.equals(""))
				throw new JilabaException("PortNo Should Not be Empty");
			if (userName == null || userName.equals(""))
				throw new JilabaException("User Name Should Not be Empty");
			if (password == null)
				throw new JilabaException("Password Should Not be Null Value");

			strDriver = "com.mysql.cj.jdbc.Driver";

			strUrl = "jdbc:mysql://" + serverName + ":" + portNo + "/" + dbName + connSettings;

			Class.forName(strDriver).newInstance();

			con = DriverManager.getConnection(strUrl, userName, password);

			strDatabaseName = dbName;

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (InstantiationException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (IllegalAccessException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus oracleConnect(String dbName, String serverName, String portNo, String userName,
			String password) {
		try {

			if (serverName == null || serverName.equals(""))
				throw new JilabaException("ServerName Should Not be Empty");
			if (portNo == null || portNo.equals(""))
				throw new JilabaException("PortNo Should Not be Empty");
			if (userName == null || userName.equals(""))
				throw new JilabaException("User Name Should Not be Empty");
			if (password == null)
				throw new JilabaException("Password Should Not be Null Value");
			ResourceBundle.clearCache();
			strDriver = "oracle.jdbc.driver.OracleDriver";

			strUrl = "jdbc:oracle:thin:@" + serverName + ":" + portNo + ":" + dbName;

			Class.forName(strDriver).newInstance();

			con = DriverManager.getConnection(strUrl, userName, password);

			strDatabaseName = dbName;

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (InstantiationException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (IllegalAccessException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus execute(String sqlQuery) {
		boolean status = false;
		try {
			if (con == null)
				throw new JilabaException("Connection Not Initialized");
			statement = con.createStatement();
			status = statement.execute(sqlQuery);

			return new ReturnStatus(true, status);
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus execute(String sqlQuery, List<Object> listParam) {
		boolean status = false;
		int intPosition = 0;
		try {
			if (con == null)
				throw new JilabaException("Connection Not Initialized");
			preparedStatement = con.prepareStatement(sqlQuery);
			for (Object o : listParam) {
				intPosition = intPosition + 1;
				preparedStatement.setObject(intPosition, o);
			}
			status = preparedStatement.execute();
			return new ReturnStatus(true, status);
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus execute(String sqlQuery, Map<String, Object> mapParam) {
		boolean status = false;
		try {
			if (con == null)
				throw new JilabaException("Connection Not Initialized");
			if (mapParam == null || mapParam.isEmpty())
				throw new JilabaException("Parameter Should Not be Empty");

			callableStatement = con.prepareCall(sqlQuery);
			Set<String> keySet = mapParam.keySet();

			callableStatement.clearParameters();
			for (String keyString : keySet) {
				callableStatement.setObject(keyString, mapParam.get(keyString));
			}

			status = callableStatement.execute();
			return new ReturnStatus(true, status);
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus executeQuery(String sqlQuery) {

		try {
			if (con == null)
				throw new JilabaException("Connection Not Initialized");
			statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = statement.executeQuery(sqlQuery);

			return new ReturnStatus(true, resultSet);
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 54) {
				return new ReturnStatus(false, "Record Used By Another User", e);
			}
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus executeQuery(String sqlQuery, List<Object> listParam) {
		int intPosition = 0;
		try {
			if (con == null)
				throw new JilabaException("Connection Not Initialized");
			preparedStatement = con.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			for (Object o : listParam) {
				intPosition = intPosition + 1;
				preparedStatement.setObject(intPosition, o);
			}
			resultSet = preparedStatement.executeQuery();
			return new ReturnStatus(true, resultSet);
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			if (e.getErrorCode() == 54) {
				return new ReturnStatus(false, "Record Used By Another User", e);
			}
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus executeUpdate(String sqlQuery) {
		int intResult = 0;
		try {
			if (con == null)
				throw new JilabaException("Connection Not Initialized");
			statement = con.createStatement();
			intResult = statement.executeUpdate(sqlQuery);
			return new ReturnStatus(true, intResult);
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus executeUpdate(String sqlQuery, List<Object> listParam) {
		int intResult = 0;
		int intPosition = 0;
		try {
			if (con == null)
				throw new JilabaException("Connection Not Initialized");
			if (listParam == null || listParam.isEmpty())
				throw new JilabaException("Parameter Should Not be Empty");

			preparedStatement = con.prepareStatement(sqlQuery);
			for (Object o : listParam) {
				intPosition = intPosition + 1;
				preparedStatement.setObject(intPosition, o);
			}

			intResult = preparedStatement.executeUpdate();
			return new ReturnStatus(true, intResult);
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {

			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus executeUpdate(String sqlQuery, Map<String, Object> mapParam) {
		int intResult = 0;
		try {
			if (con == null)
				throw new JilabaException("Connection Not Initialized");
			if (mapParam == null || mapParam.isEmpty())
				throw new JilabaException("Parameter Should Not be Empty");

			callableStatement = con.prepareCall(sqlQuery);

			Set<String> keySet = mapParam.keySet();
			callableStatement.clearParameters();
			for (String keyString : keySet) {
				callableStatement.setObject(keyString, mapParam.get(keyString));
			}

			intResult = callableStatement.executeUpdate();
			return new ReturnStatus(true, intResult);
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (NullPointerException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus beginTran() {
		try {

			if (con == null)
				throw new JilabaException("Connection Not Initialized");
			con.setAutoCommit(false);

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus commitTran() {
		try {
			if (con == null)
				throw new JilabaException("Connection Not Initialized");
			con.commit();
			con.setAutoCommit(true);
			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus rollbackTran() {
		try {
			if (con == null)
				throw new JilabaException("Connection Not Initialized");

			if (con.getAutoCommit() == false) {
				con.rollback();
			}
			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus close() {
		try {
			if (con == null)
				throw new JilabaException("Connection Not Initialized");

			con.close();

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus beginEdit(SqlEditLock sqlEditLock) {
		try {

			if (sqlEditLock == null)
				throw new JilabaException("Invalid Edit Lock Object");
			if (sqlEditLock.getTableName() == null || sqlEditLock.getTableName().trim().isEmpty())
				throw new JilabaException("Invalid Edit Lock Table Name");
			if (sqlEditLock.getListSqlEditLockColumnProperty() == null
					|| sqlEditLock.getListSqlEditLockColumnProperty().isEmpty())
				throw new JilabaException("Invalid Edit Lock Column Object");

			this.sqlEditLock = sqlEditLock;
			if (ServerType.MYSQL == serverType) {
				returnStatus = getMySqlRowLock();
				if (!returnStatus.isStatus())
					throw new JilabaException(returnStatus.getDescription());
				return returnStatus;
			}
			if (ServerType.MSSQL == serverType) {
				returnStatus = getMsSqlRowLock();
				if (!returnStatus.isStatus())
					throw new JilabaException(returnStatus.getDescription());
				return returnStatus;
			}
			return new ReturnStatus(true);
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus resetEdit() {
		try {

			if (sqlEditLock == null)
				return new ReturnStatus(true);

			if (ServerType.MYSQL == serverType) {
				returnStatus = releaseMySqlRowLock();
				if (!returnStatus.isStatus())
					throw new JilabaException(returnStatus.getDescription());
			}
			if (ServerType.MSSQL == serverType) {
				returnStatus = releaseMsSqlRowLock();
				if (!returnStatus.isStatus())
					throw new JilabaException(returnStatus.getDescription());
			}
			return new ReturnStatus(true);
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus getMySqlRowLock() {
		boolean lockSuccess = false;
		int lockValue;
		String sqlQuery = "";
		String strSelect = "";
		List<Object> listParam;
		try {
			returnStatus = isMySqlFreeRowLock();
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());

			sqlQuery = "Select GET_LOCK('" + sqlEditLock.getTableName();

			strSelect = "Select * From " + sqlEditLock.getTableName() + " Where 1=1";

			listParam = new ArrayList<Object>();
			for (SqlEditLockColumnProperty sqlEditLockColumnProperty : sqlEditLock.getListSqlEditLockColumnProperty()) {
				sqlQuery += String.valueOf(sqlEditLockColumnProperty.getColumnValue());
				strSelect += " And " + sqlEditLockColumnProperty.getColumnName() + "=?";
				listParam.add(sqlEditLockColumnProperty.getColumnValue());
			}

			sqlQuery += "',0)  As GetLock";

			returnStatus = executeQuery(sqlQuery);
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());
			resultSet = (ResultSet) returnStatus.getReturnObject();

			if (!resultSet.next())
				return new ReturnStatus(true);

			lockValue = resultSet.getInt("GetLock");

			lockSuccess = (lockValue == 0 ? false : true);

			if (!lockSuccess) {
				throw new JilabaException("Record Not Locked...!");
			}

			returnStatus = executeQuery(strSelect, listParam);
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());

			return returnStatus;
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus isMySqlFreeRowLock() {
		boolean lockExist = false;
		int lockValue;
		String sqlQuery = "";
		try {
			sqlQuery = "Select IS_FREE_LOCK('" + sqlEditLock.getTableName();

			for (SqlEditLockColumnProperty sqlEditLockColumnProperty : sqlEditLock.getListSqlEditLockColumnProperty()) {
				sqlQuery += String.valueOf(sqlEditLockColumnProperty.getColumnValue());
			}

			sqlQuery += "')  As FreeLock";

			returnStatus = executeQuery(sqlQuery);
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());

			resultSet = (ResultSet) returnStatus.getReturnObject();

			if (!resultSet.next())
				return new ReturnStatus(true);

			lockValue = resultSet.getInt("FreeLock");

			lockExist = (lockValue == 0 ? true : false);

			if (lockExist) {
				throw new JilabaException("This Record Already Using By Another User.");
			}

			return new ReturnStatus(true);
		} catch (RuntimeException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} /*
			 * catch(JilabaException e){ return new ReturnStatus(false, e.getMessage() ,e);
			 * }
			 */catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus getMsSqlRowLock() {
		String strSelect = "";
		List<Object> listParam;
		try {
			returnStatus = isMsSqlFreeRowLock();
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());

			strSelect = "Select *  From  " + sqlEditLock.getTableName() + " WITH(UpdLock) where  1=1 ";

			listParam = new ArrayList<Object>();
			for (SqlEditLockColumnProperty sqlEditLockColumnProperty : sqlEditLock.getListSqlEditLockColumnProperty()) {

				strSelect += " And " + sqlEditLockColumnProperty.getColumnName() + "=?";
				listParam.add(sqlEditLockColumnProperty.getColumnValue());
			}

			returnStatus = beginTran();
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());

			returnStatus = executeQuery(strSelect, listParam);
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());

			return returnStatus;
		} catch (RuntimeException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} /*
			 * catch(JilabaException e){ return new ReturnStatus(false, e.getMessage() ,e);
			 * }
			 */
	}

	private ReturnStatus isMsSqlFreeRowLock() {

		String sqlQueryLock = "", sqlQuery;
		List<Object> listParam;
		try {
			listParam = new ArrayList<Object>();
			sqlQueryLock = "Select %%LockRes%% From  " + sqlEditLock.getTableName() + " WITH(NoLock) where  1=1 ";

			for (SqlEditLockColumnProperty sqlEditLockColumnProperty : sqlEditLock.getListSqlEditLockColumnProperty()) {
				sqlQueryLock += " And " + sqlEditLockColumnProperty.getColumnName() + "=?";
				listParam.add(sqlEditLockColumnProperty.getColumnValue());
			}

			sqlQuery = "Select Req_Mode from Master.dbo.syslockinfo where rsc_Objid=object_id('"
					+ sqlEditLock.getTableName() + "') ";
			sqlQuery += " And Req_Mode In (3,4,14,13)  And Rsc_Text =";
			sqlQuery += "(" + sqlQueryLock + ")";

			returnStatus = executeQuery(sqlQuery, listParam);
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());
			resultSet = (ResultSet) returnStatus.getReturnObject();
			if (resultSet.next())
				throw new JilabaException("This Record Already Using By Another User.");

			return new ReturnStatus(true);
		} catch (RuntimeException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} /*
			 * catch(JilabaException e){ return new ReturnStatus(false, e.getMessage() ,e);
			 * }
			 */catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus releaseMySqlRowLock() {
		boolean lockSuccess = false;
		int lockValue;
		String sqlQuery = "";
		try {

			sqlQuery = "Select RELEASE_LOCK('" + sqlEditLock.getTableName();

			for (SqlEditLockColumnProperty sqlEditLockColumnProperty : sqlEditLock.getListSqlEditLockColumnProperty()) {
				sqlQuery += String.valueOf(sqlEditLockColumnProperty.getColumnValue());
			}

			sqlQuery += "')  As ReleaseLock";

			returnStatus = executeQuery(sqlQuery);
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());

			resultSet = (ResultSet) returnStatus.getReturnObject();

			if (!resultSet.next())
				return new ReturnStatus(true);

			lockValue = resultSet.getInt("ReleaseLock");

			lockSuccess = (lockValue == 0 ? false : true);

			if (!lockSuccess) {
				throw new JilabaException("Record Lock not Released...!");
			}
			sqlEditLock = null;
			return new ReturnStatus(true);
		} catch (RuntimeException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} /*
			 * catch(JilabaException e){ return new ReturnStatus(false, e.getMessage() ,e);
			 * }
			 */catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus releaseMsSqlRowLock() {

		try {

			returnStatus = rollbackTran();
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());

			sqlEditLock = null;
			return new ReturnStatus(true);
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus getConnection() {
		try {
			if (con == null)
				throw new JilabaException("Connection Not Initialized");
			return new ReturnStatus(true, con);
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus getDbName() {
		try {
			Object objDb = strDatabaseName;
			return new ReturnStatus(true, objDb);
		} catch (RuntimeException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ServerType getServerType() {
		return this.serverType;
	}
}
