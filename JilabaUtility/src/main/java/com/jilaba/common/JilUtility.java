package com.jilaba.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Enumeration;

import org.apache.log4j.Logger;

import com.jilaba.exception.JilabaException;
import com.jilaba.fileresource.Register;
import com.jilaba.fileresource.Server;
import com.jilaba.sqlaction.ConnectionEnum.ServerType;

public class JilUtility {
	private ReturnStatus returnStatus = new ReturnStatus();
	private ResultSet resultSet;
	private Statement statement;
	Logger logger = Logger.getLogger(JilUtility.class);

	public ReturnStatus getLocalHostAddress() {
		String strIpAdd = "";
		try {

			Enumeration<NetworkInterface> enumNetworkInterface = NetworkInterface.getNetworkInterfaces();
			while (enumNetworkInterface.hasMoreElements()) {
				NetworkInterface networkInterface = enumNetworkInterface.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					InetAddress inetAddress = enumInetAddress.nextElement();
					if (inetAddress.isSiteLocalAddress()) {
						strIpAdd = inetAddress.getHostAddress();
						break;
					}
				}
			}

			return new ReturnStatus(true, strIpAdd, "", strIpAdd);
		} catch (RuntimeException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SocketException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus getLocalHostName() {
		String strHostName = "";
		try {

			Enumeration<NetworkInterface> enumNetworkInterface = NetworkInterface.getNetworkInterfaces();
			while (enumNetworkInterface.hasMoreElements()) {
				NetworkInterface networkInterface = enumNetworkInterface.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					InetAddress inetAddress = enumInetAddress.nextElement();
					if (inetAddress.isSiteLocalAddress()) {
						strHostName = inetAddress.getHostName();
						break;
					}
				}
			}
			return new ReturnStatus(true, strHostName, "", strHostName);
		} catch (RuntimeException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SocketException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public boolean isComputerExists(Connection connection) throws JilabaException {
		String sqlQuery, strIp = "";
		try {
			if (connection == null)
				throw new JilabaException("Connection Not Initialized");
			returnStatus = getLocalHostAddress();
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());
			strIp = (String) returnStatus.getReturnObject();

			sqlQuery = "Select * From computer Where IpAdd='" + strIp + "'";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);

			if (resultSet.next())
				return true;

			return false;
		} catch (JilabaException e) {
			throw e;
		} catch (SQLException e) {
			throw new JilabaException(e.getMessage(), e);
		}
	}

	public ReturnStatus noofuserCheck(Connection connection) {
		String sqlQuery = "";
		boolean blnComputerExists = false;
		int intNoOfUser = 0;
		try {
			if (connection == null)
				throw new JilabaException("Connection Not Initialized");

			blnComputerExists = isComputerExists(connection);

			if (ServerType.MSSQL == Server.getServerType()) {
				sqlQuery = "Select IsNull(Count(*),0) NoOfUser From computer";
			} else if (ServerType.MYSQL == Server.getServerType()) {
				sqlQuery = "Select IfNull(Count(*),0) NoOfUser From computer";
			} else {
				throw new JilabaException("Server Type Not valid");
			}

			statement = connection.createStatement();

			resultSet = statement.executeQuery(sqlQuery);
			if (!resultSet.next())
				throw new JilabaException("Error From NoOfUser Checking");

			intNoOfUser = resultSet.getInt("NoOfUser");

			if (intNoOfUser > Register.getNoOfUser()) {
				throw new JilabaException("Number Of User Limit Over");
			}
			if (intNoOfUser == Register.getNoOfUser()) {
				if (blnComputerExists == false) {
					throw new JilabaException("Number Of User Limit Over");
				}
			}
			return new ReturnStatus(true, "");
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus getFinYear() {
		int intCurMonth = 0, intCurYear = 0;
		int intPrvYear = 0;
		int intNextYear = 0;
		String strFinYear = "";
		try {
			Calendar calendar = Calendar.getInstance();

			intCurMonth = calendar.get(Calendar.MONTH) + 1;

			intPrvYear = calendar.get(Calendar.YEAR) - 1;
			intCurYear = calendar.get(Calendar.YEAR);
			intNextYear = calendar.get(Calendar.YEAR) + 1;

			if (intCurMonth >= 4) {
				strFinYear = String.valueOf(intCurYear).substring(2) + String.valueOf(intNextYear).substring(2);
			} else {
				strFinYear = String.valueOf(intPrvYear).substring(2) + String.valueOf(intCurYear).substring(2);
			}

			return new ReturnStatus(true, strFinYear, "", strFinYear);
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus getFinYearFromDate(Date serverDate) {
		int intCurMonth = 0, intCurYear = 0;
		int intPrvYear = 0;
		Calendar calendar, calendar1;
		try {
			calendar = Calendar.getInstance();
			calendar.setTime(serverDate);
			intCurMonth = calendar.get(Calendar.MONTH) + 1;
			intPrvYear = calendar.get(Calendar.YEAR) - 1;
			intCurYear = calendar.get(Calendar.YEAR);

			calendar1 = Calendar.getInstance();
			calendar1.set(Calendar.DATE, 1);
			calendar1.set(Calendar.MONTH, 3);
			if (intCurMonth >= 4)
				calendar1.set(Calendar.YEAR, intCurYear);
			else
				calendar1.set(Calendar.YEAR, intPrvYear);

			return new ReturnStatus(true, calendar1.getTime());
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus getFinYearToDate(Date serverDate) {
		int intCurMonth = 0, intCurYear = 0;

		int intNextYear = 0;
		Calendar calendar, calendar1;
		try {
			calendar = Calendar.getInstance();
			calendar.setTime(serverDate);

			intCurMonth = calendar.get(Calendar.MONTH) + 1;
			intCurYear = calendar.get(Calendar.YEAR);
			intNextYear = calendar.get(Calendar.YEAR) + 1;
			calendar1 = Calendar.getInstance();
			calendar1.set(Calendar.DATE, 31);
			calendar1.set(Calendar.MONTH, 2);

			if (intCurMonth >= 4)
				calendar1.set(Calendar.YEAR, intNextYear);
			else
				calendar1.set(Calendar.YEAR, intCurYear);

			return new ReturnStatus(true, calendar1.getTime());
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}	
}
