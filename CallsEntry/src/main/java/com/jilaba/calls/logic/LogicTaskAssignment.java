package com.jilaba.calls.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.dao.TaskAssignment;
import com.jilaba.calls.model.Calls;
import com.jilaba.calls.model.CallsImages;
import com.jilaba.calls.model.CustStaff;
import com.jilaba.calls.model.Customer;
import com.jilaba.calls.model.Department;
import com.jilaba.calls.model.Module;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.model.ReadyCalls;
import com.jilaba.calls.model.ReturnCalls;
import com.jilaba.common.ReturnStatus;

@Component
@Scope("prototype")
public class LogicTaskAssignment {

	private ReturnStatus returnStatus;
	@Autowired
	private TaskAssignment task;

	public List<Operator> getDeveloper() {

		returnStatus = task.getDeveloper();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstDeveloper = (List<Operator>) returnStatus.getReturnObject();
		return lstDeveloper;
	}

	public List<Department> getDepartment() {

		returnStatus = task.getDepartment();

		CommonMethods.catchreturnstatus(returnStatus);

		List<Department> lstDept = (List<Department>) returnStatus.getReturnObject();

		return lstDept;
	}

	public List<Module> getModule(Integer deptNo) {

		returnStatus = task.getModule(deptNo);
		CommonMethods.catchreturnstatus(returnStatus);

		List<Module> lstModules = (List<Module>) returnStatus.getReturnObject();

		return lstModules;
	}

	public List<Customer> getCustomer() {

		returnStatus = task.getCustomer();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Customer> lstCustomer = (List<Customer>) returnStatus.getReturnObject();
		return lstCustomer;
	}

	public List<CustStaff> getDeptAuthorize() {
		returnStatus = task.getCustCoOrd();
		CommonMethods.catchreturnstatus(returnStatus);

		List<CustStaff> lstDeptAuthorize = (List<CustStaff>) returnStatus.getReturnObject();
		return lstDeptAuthorize;
	}

	public List<Operator> getRecvFrom() {
		returnStatus = task.getRecvFrom();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstRecvFrom = (List<Operator>) returnStatus.getReturnObject();
		return lstRecvFrom;
	}

	public List<Operator> getCallCoOrd() {
		returnStatus = task.getCallCoOrd();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstCallCoOrd = (List<Operator>) returnStatus.getReturnObject();
		return lstCallCoOrd;

	}

	public List<Calls> getDeveloperCalls(String asOnDate, long strCallNo, int strDeveleoper, int strDepartment,
			int strModule, int strCustomer, int strType, int strDeptAuthorize, int strRecvFrom, int strCallCoOrd,
			int strCallNature) {

		returnStatus = task.getDeveloperCalls(asOnDate, strCallNo, strDeveleoper, strDepartment, strModule,
				strCustomer, strType, strDeptAuthorize, strRecvFrom, strCallCoOrd, strCallNature);
		CommonMethods.catchreturnstatus(returnStatus);

		List<Calls> lstCalls = (List<Calls>) returnStatus.getReturnObject();

		return lstCalls;

	}

	public CallsImages getImages(String callNo) {

		returnStatus = task.getCallImages(callNo);

		CallsImages lstCallsImages = (CallsImages) returnStatus.getReturnObject();

		return lstCallsImages;

	}

	public List<Calls> updateProgressCall(int callNo) {
		returnStatus = task.updateProgressCall(callNo);

		List<Calls> lstCalls = (List<Calls>) returnStatus.getReturnObject();

		return lstCalls;

	}

	public List<Calls> validateProgressCall(int strDeveleoper) {

		returnStatus = task.validateProgressCall(strDeveleoper);

		List<Calls> lstCall = (List<Calls>) returnStatus.getReturnObject();

		return lstCall;

	}

	public List<Calls> updateReadyCalls(int callNo, String readyDesc) {

		returnStatus = task.updateReadyCalls(callNo, readyDesc);

		List<Calls> lstReadyCalls = (List<Calls>) returnStatus.getReturnObject();

		return lstReadyCalls;

	}

	public List<ReadyCalls> saveReadyCalls(int cNo, String readyDesc, int cModuleId) {

		returnStatus = task.saveReadyCalls(cNo, readyDesc, cModuleId);

		List<ReadyCalls> lstReadyCalls = (List<ReadyCalls>) returnStatus.getReturnObject();

		return lstReadyCalls;

	}

	public List<ReturnCalls> getReturnCall(int strDeveleoper) {

		returnStatus = task.getReturnCall(strDeveleoper);

		List<ReturnCalls> lstReturnCalls = (List<ReturnCalls>) returnStatus.getReturnObject();

		return lstReturnCalls;

	}

	public List<Calls> validateReadyCalls(String callNo) {

		returnStatus = task.validateReadyCalls(callNo);

		List<Calls> lstCall = (List<Calls>) returnStatus.getReturnObject();

		return lstCall;

	}

	public List<Calls> insertProgressCall(String CallNo) {

		returnStatus = task.insertProgressCall(CallNo);

		List<Calls> lstInsert = (List<Calls>) returnStatus.getReturnObject();

		return lstInsert;

	}

	public List<Calls> updateProgressCancel(String callNo) {

		returnStatus = task.updateProgressCancel(callNo);

		List<Calls> lstProgressCancel = (List<Calls>) returnStatus.getReturnObject();

		return lstProgressCancel;

	}

	public void updateDevPriority(String DevPriority, String selectedCallno) {

		returnStatus = task.updateDevPriority(DevPriority, selectedCallno);

	}

	public void updateDev(int cNo, int dev) {

		returnStatus = task.updateDev(cNo, dev);
	}

}
