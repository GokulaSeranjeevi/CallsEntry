package com.jilaba.common;

import java.util.ArrayList;
import java.util.List;

import com.jilaba.exception.JilabaException;
import com.jilaba.sqlaction.ConnectionEnum.ConnectionDbType;
import com.jilaba.sqlaction.ConnectionEnum.ConnectionType;
import com.jilaba.sqlaction.SqlAction;

public class TableComputer {
	private String strKeyCode;
	private String strIpAdd;
	private int intOperCode;
	private int intLogId;
	private String strCompCode;
	private int intXPos;
	private int intYPos;
	private int intIpId;
	private String strIpName;
	private int intLanguageCode;
	private String strFinYearFromDate;
	private String defaultCompCode;

	public TableComputer() {
		strKeyCode = "";
		strIpAdd = "";
		intOperCode = 0;
		intLogId = 0;
		strCompCode = "";
		intXPos = 0;
		intYPos = 0;
		intIpId = 0;
		strIpName = "";
		intLanguageCode = 0;
		strFinYearFromDate = "";
		defaultCompCode = "";
	}

	public String getStrKeyCode() {
		return strKeyCode;
	}

	public void setStrKeyCode(String strKeyCode) {
		this.strKeyCode = strKeyCode;
	}

	public String getStrIpAdd() {
		return strIpAdd;
	}

	public void setStrIpAdd(String strIpAdd) {
		this.strIpAdd = strIpAdd;
	}

	public int getIntOperCode() {
		return intOperCode;
	}

	public void setIntOperCode(int intOperCode) {
		this.intOperCode = intOperCode;
	}

	public int getIntLogId() {
		return intLogId;
	}

	public void setIntLogId(int intLogId) {
		this.intLogId = intLogId;
	}

	public String getStrCompCode() {
		return strCompCode;
	}

	public void setStrCompCode(String strCompCode) {
		this.strCompCode = strCompCode;
	}

	public int getIntXPos() {
		return intXPos;
	}

	public void setIntXPos(int intXPos) {
		this.intXPos = intXPos;
	}

	public int getIntYPos() {
		return intYPos;
	}

	public void setIntYPos(int intYPos) {
		this.intYPos = intYPos;
	}

	public int getIntIpId() {
		return intIpId;
	}

	public void setIntIpId(int intIpId) {
		this.intIpId = intIpId;
	}

	public String getStrIpName() {
		return strIpName;
	}

	public void setStrIpName(String strIpName) {
		this.strIpName = strIpName;
	}

	public int getIntLanguageCode() {
		return intLanguageCode;
	}

	public void setIntLanguageCode(int intLanguageCode) {
		this.intLanguageCode = intLanguageCode;
	}

	public String getStrFinYearFromDate() {
		return strFinYearFromDate;
	}

	public void setStrFinYearFromDate(String strFinYearFromDate) {
		this.strFinYearFromDate = strFinYearFromDate;
	}

	public String getDefaultCompCode() {
		return defaultCompCode;
	}

	public void setDefaultCompCode(String defaultCompCode) {
		this.defaultCompCode = defaultCompCode;
	}

	public ReturnStatus dataSave(List<TableComputer> listComputer, SqlAction sqlAction) {
		String sqlQuery = "";
		ReturnStatus returnStatus = new ReturnStatus();
		List<Object> listParam;
		try {

			if (sqlAction == null)
				throw new JilabaException("Connection Not Initialized");
			if (listComputer == null || listComputer.isEmpty())
				throw new JilabaException("No Details to Save");

			sqlQuery = "Insert Into computer (KeyCode,IpAdd,OperCode,LogId,CompCode,";
			sqlQuery += "XPos,YPos,IpName,LanguageCode,FinYearFromDate,DefaultCompCode,IpId)";
			sqlQuery += "Values(?,?,?,?,?,";
			sqlQuery += "?,?,?,?,?,?,?)";

			for (TableComputer tableComputer : listComputer) {

				listParam = new ArrayList<Object>();
				listParam.add(tableComputer.getStrKeyCode());
				listParam.add(tableComputer.getStrIpAdd());
				listParam.add(tableComputer.getIntOperCode());
				listParam.add(tableComputer.getIntLogId());
				listParam.add(tableComputer.getStrCompCode());
				listParam.add(tableComputer.getIntXPos());
				listParam.add(tableComputer.getIntYPos());
				listParam.add(tableComputer.getStrIpName());
				listParam.add(tableComputer.getIntLanguageCode());
				if ("".equals(tableComputer.getStrFinYearFromDate())) {
					listParam.add(null);
				} else {
					listParam.add(tableComputer.getStrFinYearFromDate());
				}
				listParam.add(tableComputer.getDefaultCompCode());
				listParam.add(tableComputer.getIntIpId());
				returnStatus = sqlAction.executeUpdate(sqlQuery, ConnectionDbType.COMPYDB, ConnectionType.SOURCE,
						listParam);
				if (!returnStatus.isStatus())
					throw new JilabaException(returnStatus.getDescription());
			}
			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			e.printStackTrace();
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

}
