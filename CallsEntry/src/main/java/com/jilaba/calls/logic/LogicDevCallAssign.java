package com.jilaba.calls.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.dao.DevCallAssign;
import com.jilaba.calls.model.Calls;
import com.jilaba.calls.model.CustStaff;
import com.jilaba.calls.model.Customer;
import com.jilaba.calls.model.Department;
import com.jilaba.calls.model.Operator;
import com.jilaba.common.ReturnStatus;
import com.jilaba.control.JilabaTable;

@Component
@Scope("prototype")
public class LogicDevCallAssign {

	private ReturnStatus returnStatus;

	@Autowired
	private DevCallAssign devCallAssign;

	public List<Operator> getDevCallCoOrd() {

		returnStatus = devCallAssign.getDevCallAssign();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstDevCoOrd = (List<Operator>) returnStatus.getReturnObject();
		return lstDevCoOrd;
	}

	public List<Customer> getCustomer() {

		returnStatus = devCallAssign.getCustomer();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Customer> customer = (List<Customer>) returnStatus.getReturnObject();

		return customer;
	}

	public List<CustStaff> getCustCoOrd() {

		returnStatus = devCallAssign.getCustCoOrd();
		CommonMethods.catchreturnstatus(returnStatus);

		List<CustStaff> custCoOrd = (List<CustStaff>) returnStatus.getReturnObject();

		return custCoOrd;
	}

	public List<Department> getDepartment() {

		returnStatus = devCallAssign.getDepartment();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Department> dept = (List<Department>) returnStatus.getReturnObject();

		return dept;

	}

	public List<Operator> getDeptAuthorize() {

		returnStatus = devCallAssign.getDeptAuthorize();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> deptAuthorize = (List<Operator>) returnStatus.getReturnObject();

		return deptAuthorize;
	}

	public List<Operator> getRecvFrom() {

		returnStatus = devCallAssign.getRecvFrom();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> recvFrom = (List<Operator>) returnStatus.getReturnObject();

		return recvFrom;
	}

	public List<com.jilaba.calls.model.Module> getModule(Integer deptno) {

		returnStatus = devCallAssign.getModule(deptno);
		CommonMethods.catchreturnstatus(returnStatus);

		List<com.jilaba.calls.model.Module> module = (List<com.jilaba.calls.model.Module>) returnStatus
				.getReturnObject();

		return module;
	}

	public List<Calls> getDeveloperCalls() {

		returnStatus = devCallAssign.getdeveloperCalls();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Calls> devCalls = (List<Calls>) returnStatus.getReturnObject();

		return devCalls;
	}

	public List<Calls> getCalls(int strDevCoOrd, long strCallNo, int strCustomer,
			int strCustCoOrd, int strDepartment, int strDeptAuthorize,
			int strRecvFrom, int strModule, int cmbCallNature) {

		returnStatus = devCallAssign.getCalls(strDevCoOrd, strCallNo, strCustomer, strCustCoOrd, strDepartment, strDeptAuthorize,
				strRecvFrom, strModule,cmbCallNature);
		CommonMethods.catchreturnstatus(returnStatus);

		List<Calls> calls = (List<Calls>) returnStatus.getReturnObject();

		return calls;

	}

	public List<Operator> getDeveloper() {
		returnStatus = devCallAssign.getDeveloper();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstOperator = (List<Operator>) returnStatus.getReturnObject();

		return lstOperator;
	}

	public void devCallUpdate(Object cmbExplanation, Object cmbSugTo, String txtDevHrs, String assnDate, int callNo) {

		returnStatus = devCallAssign.devCallUpdate(cmbExplanation, cmbSugTo, txtDevHrs, assnDate, callNo);
		CommonMethods.catchreturnstatus(returnStatus);

	}

}
