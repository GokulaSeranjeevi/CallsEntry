package com.jilaba.calls.dao;

import com.jilaba.common.ReturnStatus;

public interface DevCallAssign {

	public ReturnStatus getDevCallAssign();

	public ReturnStatus getCustomer();

	public ReturnStatus getCustCoOrd();

	public ReturnStatus getDepartment();

	public ReturnStatus getDeptAuthorize();

	public ReturnStatus getRecvFrom();

	public ReturnStatus getdeveloperCalls();

	public ReturnStatus getDeveloper();

	public ReturnStatus devCallUpdate(Object cmbExplanation, Object cmbSugTo, String txtDevHrs, String assnDate,
			int callNO);

	public ReturnStatus getCalls(int strDevCoOrd, long strCallNo, int strCustomer, int strCustCoOrd, int strDepartment,
			int strDeptAuthorize, int strRecvFrom, int strModule, int cmbCallNature);

	public ReturnStatus getModule(Integer deptno);

}
