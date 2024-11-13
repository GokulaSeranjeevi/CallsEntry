package com.jilaba.calls.model;

import java.sql.Date;

public class DataValidation {

	private int DatabaseId;
	private int BranchId;
	private String UpdateDate;
	private String Updatetime;
	private String QueryDesc;
	private int Updateby;
	private String Reason;

	public int getDatabaseId() {
		return DatabaseId;
	}

	public void setDatabaseId(int databaseId) {
		DatabaseId = databaseId;
	}

	public int getBranchId() {
		return BranchId;
	}

	public void setBranchId(int branchId) {
		BranchId = branchId;
	}


	public String getUpdateDate() {
		return UpdateDate;
	}

	public void setUpdateDate(String updateDate) {
		UpdateDate = updateDate;
	}

	public String getUpdatetime() {
		return Updatetime;
	}

	public void setUpdatetime(String updatetime) {
		Updatetime = updatetime;
	}

	public String getQueryDesc() {
		return QueryDesc;
	}

	public void setQueryDesc(String queryDesc) {
		QueryDesc = queryDesc;
	}

	public int getUpdateby() {
		return Updateby;
	}

	public void setUpdateby(int updateby) {
		Updateby = updateby;
	}

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}

}
