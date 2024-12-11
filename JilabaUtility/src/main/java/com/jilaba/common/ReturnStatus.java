package com.jilaba.common;

public class ReturnStatus {
	private boolean status;
	private String description;
	private String innerDescription;
	private Object returnObject;

	public ReturnStatus() {
		status = false;
		description = "";
		innerDescription = "";
		returnObject = null;
	}

	public ReturnStatus(boolean jBlnStatus) {
		status = jBlnStatus;
		description = "";
		innerDescription = "";
		returnObject = null;
	}

	public ReturnStatus(boolean jBlnStatus, String jStrDesc) {
		status = jBlnStatus;
		description = jStrDesc;
		innerDescription = "";
		returnObject = null;
	}

	public ReturnStatus(boolean jBlnStatus, Object jObjRetObject) {
		status = jBlnStatus;
		description = "";
		innerDescription = "";
		returnObject = jObjRetObject;
	}

	public ReturnStatus(boolean jBlnStatus, String jStrDesc, Object jObjRetObject) {
		status = jBlnStatus;
		description = jStrDesc;
		innerDescription = "";
		returnObject = jObjRetObject;
	}

	public ReturnStatus(boolean jBlnStatus, String jStrDesc, String jStrInnDesc) {
		status = jBlnStatus;
		description = jStrDesc;
		innerDescription = jStrInnDesc;
		returnObject = null;
	}

	public ReturnStatus(boolean jBlnStatus, String jStrDesc, String jStrInnDesc, Object jObjRetObject) {
		status = jBlnStatus;
		description = jStrDesc;
		innerDescription = jStrInnDesc;
		returnObject = jObjRetObject;
	}

	public boolean isStatus() {
		return status;
	}

	public Object getReturnObject() {
		return returnObject;
	}

	public String getDescription() {
		return description;
	}

	public String getInnerDescription() {
		return innerDescription;
	}

}
