package com.jilaba.calls.dao;

import com.jilaba.common.ReturnStatus;

public interface ReadyCallsDao {

	ReturnStatus getDeveloper();

	ReturnStatus getCustomer();

	ReturnStatus getDeptAuthorize();

	ReturnStatus getDepartment();

	ReturnStatus getModule();

	ReturnStatus getReadyCalls(String strCallFromDate, String strCallToDate, String strReadyFromDate,
			String strReadyToDate, int strCmbDeveloper, int strCmbClient, int strCmbDeptAuthorize, int strCmbDepartment,
			int strCmbModule, String strOrderby, String callno);

	ReturnStatus getCallImages(String callNo);

	ReturnStatus updateReadyCalls(int callNo, String completedDesc, String cModuleId);

	ReturnStatus updateReturnCalls(int callNo, String returnDesc);

	ReturnStatus saveReadyUnmark(int cNo, String returnDesc, String cModuleId);

	ReturnStatus updateReadyCancel(int callNo);

	ReturnStatus updateReadyProgress(String callNo);

	ReturnStatus validateProgressCall();

	int beforeProgressValidate();

}
