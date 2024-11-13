package com.jilaba.calls.model;

public class BranchOffice {

	private int BranchId;
	private String BranchCode;
	private String BranchName;
	private String Active;

	public String getActive() {
		return Active;
	}

	public void setActive(String active) {
		Active = active;
	}

	public int getBranchId() {
		return BranchId;
	}

	public void setBranchId(int branchId) {
		BranchId = branchId;
	}

	public String getBranchCode() {
		return BranchCode;
	}

	public void setBranchCode(String branchCode) {
		BranchCode = branchCode;
	}

	public String getBranchName() {
		return BranchName;
	}

	public void setBranchName(String branchName) {
		BranchName = branchName;
	}

}
