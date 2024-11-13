package com.jilaba.calls.model;

import java.sql.Date;

public class ReturnCalls {

	private int Callno;
	private String Description;
	private int module;
	private int unmarkoper;
	private int opercode;
	private Date updated;
	private Date uptime;
	private String cancel;
	private Date RDate;
	private int readyby;
	private String Version;
	private int readysno;

	public int getSugto() {
		return Sugto;
	}

	public void setSugto(int sugto) {
		Sugto = sugto;
	}

	private int Sugto;

	public int getCallno() {
		return Callno;
	}

	public void setCallno(int callno) {
		Callno = callno;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public int getUnmarkoper() {
		return unmarkoper;
	}

	public void setUnmarkoper(int unmarkoper) {
		this.unmarkoper = unmarkoper;
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

	public Date getRDate() {
		return RDate;
	}

	public void setRDate(Date rDate) {
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

	public int getReadysno() {
		return readysno;
	}

	public void setReadysno(int readysno) {
		this.readysno = readysno;
	}

}
