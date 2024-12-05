package com.jilaba.calls.model;

import java.sql.Date;

public class ReadyCalls {

	private int Callno;
	private String readydescription;
	private int module;
	private int opercode;
	private Date updated;
	private Date uptime;
	private String cancel;
	private String RDate;
	private int readyby;
	private String Version;
	private String Cdate;
	private String CustName;
	private String Recdby;
	private String Department;
	private String ModuleName;
	private String description;
	private String readyByName;
	private String Authorized;
	private int QcOperator;

	public int getQcOperator() {
		return QcOperator;
	}

	public void setQcOperator(int qcOperator) {
		QcOperator = qcOperator;
	}

	public String getAuthorized() {
		return Authorized;
	}

	public void setAuthorized(String authorized) {
		Authorized = authorized;
	}

	public String getReadyByName() {
		return readyByName;
	}

	public void setReadyByName(String readyByName) {
		this.readyByName = readyByName;
	}

	public String getCdate() {
		return Cdate;
	}

	public void setCdate(String string) {
		Cdate = string;
	}

	public String getCustName() {
		return CustName;
	}

	public void setCustName(String custName) {
		CustName = custName;
	}

	public String getRecdby() {
		return Recdby;
	}

	public void setRecdby(String recdby) {
		Recdby = recdby;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public String getModuleName() {
		return ModuleName;
	}

	public void setModuleName(String moduleName) {
		ModuleName = moduleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCallno() {
		return Callno;
	}

	public void setCallno(int callno) {
		Callno = callno;
	}

	public String getReadydescription() {
		return readydescription;
	}

	public void setReadydescription(String readydescription) {
		this.readydescription = readydescription;
	}

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public int getOpercode() {
		return opercode;
	}

	public void setOpercode(int opercode) {
		this.opercode = opercode;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public String getCancel() {
		return cancel;
	}

	public void setCancel(String cancel) {
		this.cancel = cancel;
	}

	public String getRDate() {
		return RDate;
	}

	public void setRDate(String rDate) {
		RDate = rDate;
	}

	public int getReadyby() {
		return readyby;
	}

	public void setReadyby(int readyby) {
		this.readyby = readyby;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

}
