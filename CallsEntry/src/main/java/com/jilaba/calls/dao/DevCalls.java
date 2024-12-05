package com.jilaba.calls.dao;

import com.jilaba.common.ReturnStatus;

public interface DevCalls {

	ReturnStatus getDeveloper();

	ReturnStatus getDepartment();

	ReturnStatus getModule(Integer deptNo);

	ReturnStatus getCustomer();

	ReturnStatus getCustCoOrd();

	ReturnStatus getRecvFrom();

	ReturnStatus getCallCoOrd();

	ReturnStatus getDeveloperCalls(String asOnDate, long strCallNo, int strDeveleoper, int strDepartment, int strModule,
			int strCustomer, int strType, int strDeptAuthorize, int strRecvFrom, int strCallCoOrd, int strCallNature);

	public ReturnStatus getCallImages(String callNo);

	ReturnStatus updateProgressCall(int callNo);

	ReturnStatus validateProgressCall(int strDeveleoper);

	ReturnStatus updateReadyCalls(int callNo, String readyDesc);

	ReturnStatus saveReadyCalls(int cNo, String readyDesc, int cModuleId);

	ReturnStatus getReturnCall(int strDeveleoper);

	ReturnStatus validateReadyCalls(String callNo);

	ReturnStatus insertProgressCall(String callNo);

	ReturnStatus updateProgressCancel(String callNo);

	ReturnStatus updateDevPriority(String devPriority, String selectedCallno);
}
