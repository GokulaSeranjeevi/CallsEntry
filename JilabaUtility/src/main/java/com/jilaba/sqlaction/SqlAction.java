package com.jilaba.sqlaction;

import java.util.List;
import java.util.Map;

import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.JilabaException;
import com.jilaba.sqlaction.ConnectionEnum.ConnectionDbType;
import com.jilaba.sqlaction.ConnectionEnum.ConnectionType;
import com.jilaba.sqlaction.ConnectionEnum.ServerType;

public class SqlAction {
	private static SqlConnection sourceSqlMaster, sourceMainDb, sourceTranDb, sourceLogDb, sourceSchemeDb,
			sourceMessageDb, sourceAddressDb, sourceCompyDb;
	private static SqlConnection destSqlMaster, destMainDb, destTranDb, destLogDb, destSchemeDb, destMessageDb,
			destAddressDb, destCompyDb;
	private static SqlConnection reSqlMaster, reMainDb, reTranDb, reLogDb, reSchemeDb, reMessageDb, reAddressDb,
			reCompyDb;

	private ReturnStatus returnStatus = new ReturnStatus();

	private static final String strConnError = "Connection Not Initialized...";

	private static final String mssqlSchema = ".Dbo.";
	private static final String mysqlSchema = ".";

	private static SqlAction sqlAction;

	public static SqlAction getInstance() {
		if (sqlAction == null)
			sqlAction = new SqlAction();
		return sqlAction;
	}

	/**
	 * @param dbName	
	 * @param serverName
	 * @param portNo
	 * @param userName
	 * @param password
	 * @param connectionDbType	
	 * @param connectionType
	 * @param serverType
	 * @return ReturnStatus
	 */
	public static ReturnStatus dbConnect(String dbName, String serverName, String portNo, String userName,
			String password, ConnectionDbType connectionDbType, ConnectionType connectionType, ServerType serverType) {
		ReturnStatus returnStatus = new ReturnStatus();
		String schema = serverType == ServerType.MSSQL ? mssqlSchema : mysqlSchema;
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					sourceSqlMaster = new SqlConnection();
					returnStatus = sourceSqlMaster.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);

					SqlSourceDb.setMasterDb(dbName);
					SqlSourceDb.setMasterDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					sourceMainDb = new SqlConnection();
					returnStatus = sourceMainDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlSourceDb.setMainDb(dbName);
					SqlSourceDb.setMainDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					sourceTranDb = new SqlConnection();
					returnStatus = sourceTranDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlSourceDb.setTranDb(dbName);
					SqlSourceDb.setTranDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					sourceLogDb = new SqlConnection();
					returnStatus = sourceLogDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlSourceDb.setLogDb(dbName);
					SqlSourceDb.setLogDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					sourceSchemeDb = new SqlConnection();
					returnStatus = sourceSchemeDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlSourceDb.setSchemeDb(dbName);
					SqlSourceDb.setSchemeDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					sourceMessageDb = new SqlConnection();
					returnStatus = sourceMessageDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlSourceDb.setMessageDb(dbName);
					SqlSourceDb.setMessageDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					sourceAddressDb = new SqlConnection();
					returnStatus = sourceAddressDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlSourceDb.setAddressDb(dbName);
					SqlSourceDb.setAddressDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					sourceCompyDb = new SqlConnection();
					returnStatus = sourceCompyDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlSourceDb.setCompyDb(dbName);
					SqlSourceDb.setCompyDbo(dbName + schema);
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					destSqlMaster = new SqlConnection();
					returnStatus = destSqlMaster.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlDestinationDb.setMasterDb(dbName);
					SqlDestinationDb.setMasterDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					destMainDb = new SqlConnection();
					returnStatus = destMainDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlDestinationDb.setMainDb(dbName);
					SqlDestinationDb.setMainDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					destTranDb = new SqlConnection();
					returnStatus = destTranDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlDestinationDb.setTranDb(dbName);
					SqlDestinationDb.setTranDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					destLogDb = new SqlConnection();
					returnStatus = destLogDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlDestinationDb.setLogDb(dbName);
					SqlDestinationDb.setLogDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					destSchemeDb = new SqlConnection();
					returnStatus = destSchemeDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlDestinationDb.setSchemeDb(dbName);
					SqlDestinationDb.setSchemeDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					destMessageDb = new SqlConnection();
					returnStatus = destMessageDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlDestinationDb.setMessageDb(dbName);
					SqlDestinationDb.setMessageDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					destAddressDb = new SqlConnection();
					returnStatus = destAddressDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlDestinationDb.setAddressDb(dbName);
					SqlDestinationDb.setAddressDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					destCompyDb = new SqlConnection();
					returnStatus = destCompyDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlDestinationDb.setCompyDb(dbName);
					SqlDestinationDb.setCompyDbo(dbName + schema);
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					reSqlMaster = new SqlConnection();
					returnStatus = reSqlMaster.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlReDb.setMasterDb(dbName);
					SqlReDb.setMasterDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					reMainDb = new SqlConnection();
					returnStatus = reMainDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlReDb.setMainDb(dbName);
					SqlReDb.setMainDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					reTranDb = new SqlConnection();
					returnStatus = reTranDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlReDb.setTranDb(dbName);
					SqlReDb.setTranDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					reLogDb = new SqlConnection();
					returnStatus = reLogDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlReDb.setLogDb(dbName);
					SqlReDb.setLogDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					reSchemeDb = new SqlConnection();
					returnStatus = reSchemeDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlReDb.setSchemeDb(dbName);
					SqlReDb.setSchemeDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					reMessageDb = new SqlConnection();
					returnStatus = reMessageDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlReDb.setMessageDb(dbName);
					SqlReDb.setMessageDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					reAddressDb = new SqlConnection();
					returnStatus = reAddressDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlReDb.setAddressDb(dbName);
					SqlReDb.setAddressDbo(dbName + schema);
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					reCompyDb = new SqlConnection();
					returnStatus = reCompyDb.connect(dbName, serverName, portNo, userName, password, serverType);
					statusCheck(returnStatus);
					SqlReDb.setCompyDb(dbName);
					SqlReDb.setCompyDbo(dbName + schema);
				}
			}

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus execute(String sqlQuery, ConnectionDbType connectionDbType, ConnectionType connectionType) {
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.execute(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
			}
			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus execute(String sqlQuery, ConnectionDbType connectionDbType, ConnectionType connectionType,
			List<Object> listParam) {
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.execute(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			}
			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus execute(String sqlQuery, ConnectionDbType connectionDbType, ConnectionType connectionType,
			Map<String, Object> mapParam) {
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.execute(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			}
			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus executeQuery(String sqlQuery, ConnectionDbType connectionDbType,
			ConnectionType connectionType) {
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.executeQuery(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
			}

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus executeQuery(String sqlQuery, ConnectionDbType connectionDbType, ConnectionType connectionType,
			List<Object> listParam) {
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.executeQuery(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			}

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus executeUpdate(String sqlQuery, ConnectionDbType connectionDbType,
			ConnectionType connectionType) {
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.executeUpdate(sqlQuery);
					statusCheck(returnStatus);
					return returnStatus;
				}
			}

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus executeUpdate(String sqlQuery, ConnectionDbType connectionDbType, ConnectionType connectionType,
			List<Object> listParam) {
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.executeUpdate(sqlQuery, listParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			}

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus executeUpdate(String sqlQuery, ConnectionDbType connectionDbType, ConnectionType connectionType,
			Map<String, Object> mapParam) {
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.executeUpdate(sqlQuery, mapParam);
					statusCheck(returnStatus);
					return returnStatus;
				}
			}

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus beginTran(ConnectionDbType connectionDbType, ConnectionType connectionType) {
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.beginTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
			}

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus commitTran(ConnectionDbType connectionDbType, ConnectionType connectionType) {
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.commitTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
			}
			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus rollbackTran(ConnectionDbType connectionDbType, ConnectionType connectionType) {
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.rollbackTran();
					statusCheck(returnStatus);
					return returnStatus;
				}
			}

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus getConnection(ConnectionDbType connectionDbType, ConnectionType connectionType) {
		try {
			if (connectionType == ConnectionType.SOURCE) {

				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.getConnection();
					statusCheck(returnStatus);
					return returnStatus;
				}
			}

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus getDbName(ConnectionDbType connectionDbType, ConnectionType connectionType) {
		try {
			if (connectionType == ConnectionType.SOURCE) {

				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.getDbName();
					statusCheck(returnStatus);
					return returnStatus;
				}
			}
			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus beginEdit(ConnectionDbType connectionDbType, ConnectionType connectionType,
			SqlEditLock sqlEditLock) {
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.beginEdit(sqlEditLock);
					statusCheck(returnStatus);
					return returnStatus;
				}
			}
			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus resetEdit(ConnectionDbType connectionDbType, ConnectionType connectionType) {
		try {
			if (connectionType == ConnectionType.SOURCE) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(sourceSqlMaster);
					returnStatus = sourceSqlMaster.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(sourceMainDb);
					returnStatus = sourceMainDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(sourceTranDb);
					returnStatus = sourceTranDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(sourceLogDb);
					returnStatus = sourceLogDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(sourceSchemeDb);
					returnStatus = sourceSchemeDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(sourceMessageDb);
					returnStatus = sourceMessageDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(sourceAddressDb);
					returnStatus = sourceAddressDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(sourceCompyDb);
					returnStatus = sourceCompyDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.DESTINATION) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(destSqlMaster);
					returnStatus = destSqlMaster.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(destMainDb);
					returnStatus = destMainDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(destTranDb);
					returnStatus = destTranDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(destLogDb);
					returnStatus = destLogDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(destSchemeDb);
					returnStatus = destSchemeDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(destMessageDb);
					returnStatus = destMessageDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(destAddressDb);
					returnStatus = destAddressDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(destCompyDb);
					returnStatus = destCompyDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
			} else if (connectionType == ConnectionType.REDB) {
				if (connectionDbType == ConnectionDbType.SQLMASTER) {
					connectionCheck(reSqlMaster);
					returnStatus = reSqlMaster.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MAINDB) {
					connectionCheck(reMainDb);
					returnStatus = reMainDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.TRANDB) {
					connectionCheck(reTranDb);
					returnStatus = reTranDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.LOGDB) {
					connectionCheck(reLogDb);
					returnStatus = reLogDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.SCHEMEDB) {
					connectionCheck(reSchemeDb);
					returnStatus = reSchemeDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.MESSAGEDB) {
					connectionCheck(reMessageDb);
					returnStatus = reMessageDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.ADDRESSDB) {
					connectionCheck(reAddressDb);
					returnStatus = reAddressDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
				if (connectionDbType == ConnectionDbType.COMPYDB) {
					connectionCheck(reCompyDb);
					returnStatus = reCompyDb.resetEdit();
					statusCheck(returnStatus);
					return returnStatus;
				}
			}
			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private static void connectionCheck(SqlConnection sqlConnection) {
		if (null == sqlConnection) {
			throw new JilabaException(strConnError);
		}
	}

	private static void statusCheck(ReturnStatus returnStatus) {
		if (!returnStatus.isStatus())
			throw new JilabaException(returnStatus.getDescription());
	}
}
