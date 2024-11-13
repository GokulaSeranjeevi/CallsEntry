package com.jilaba.calls.dao;

import com.jilaba.common.ReturnStatus;

public interface CallsCompletedDao {

	ReturnStatus getDeveloper();

	ReturnStatus getCustomer();

	ReturnStatus getDeptAuthorize();

	ReturnStatus getDepartment();

	ReturnStatus getModule(Integer dept);

	ReturnStatus getCompletedCalls(String strCallFromDate, String strCallToDate, String strCompletedFromDate,
			String strCompletedToDate, int strCmbDeveloper, int strCmbClient, int strCmbDeptAuthorize,
			int strCmbDepartment, int strCmbModule, String strOrderby, String callNo);

	ReturnStatus updateDeliveredCalls(int cNo, String completedDesc);

}
