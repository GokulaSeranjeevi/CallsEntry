package com.jilaba.calls.query;

import org.springframework.stereotype.Component;

@Component
public class DataValidationQuery {

	private StringBuilder sb;

	public String getDatabaseName() {

		sb = new StringBuilder("");

		sb.append("Select * from Fn_Database()");

		return sb.toString();
	}

	public String getBranchOffice() {

		sb = new StringBuilder("");

		sb.append("Select * from Fn_Branch()");

		return sb.toString();
	}

	public String getUpdateby() {

		sb = new StringBuilder("");
		sb.append("Select * from Staff Where Active='Y' And designation In(1,2,3)");

		return sb.toString();
	}

	public String getData(Object dataId, Object branchId, String Date, String text) {

		sb = new StringBuilder("");

		sb.append("Select * from DataValidation Where Databaseid=" + dataId + " and BranchId=" + branchId);
		if (Date!=null)
			sb.append(" And UpdateDate='" + Date + "'");
		if (!text.equalsIgnoreCase(""))
			sb.append(" And QueryDesc like '%" + text + "%'");
		
		
		System.out.println(sb);
		return sb.toString();
	}

}
