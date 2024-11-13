package com.jilaba.calls.model;

import com.jilaba.common.ReturnStatus;

public class CallsCompleted {

	private int callNo;
	private String callDate;
	private String TestedDate;
	private String Testedby;
	private String ClientName;
	private String RecbyName;
	private String Authorized;
	private String department;

	public int getCallNo() {
		return callNo;
	}

	public void setCallNo(int callNo) {
		this.callNo = callNo;
	}

	public String getCallDate() {
		return callDate;
	}

	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}

	public String getTestedDate() {
		return TestedDate;
	}

	public void setTestedDate(String testedDate) {
		TestedDate = testedDate;
	}

	public String getTestedby() {
		return Testedby;
	}

	public void setTestedby(String testedby) {
		Testedby = testedby;
	}

	public String getClientName() {
		return ClientName;
	}

	public void setClientName(String clientName) {
		ClientName = clientName;
	}

	public String getRecbyName() {
		return RecbyName;
	}

	public void setRecbyName(String recbyName) {
		RecbyName = recbyName;
	}

	public String getAuthorized() {
		return Authorized;
	}

	public void setAuthorized(String authorized) {
		Authorized = authorized;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getCallDescription() {
		return callDescription;
	}

	public void setCallDescription(String callDescription) {
		this.callDescription = callDescription;
	}

	public String getTestDescription() {
		return TestDescription;
	}

	public void setTestDescription(String testDescription) {
		TestDescription = testDescription;
	}

	private String module;
	private String callDescription;
	private String TestDescription;

}
