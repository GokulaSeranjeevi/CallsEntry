package com.jilaba.calls.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.dao.DevCalls;
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
public class LogicDevCalls {

	private ReturnStatus returnStatus;
	@Autowired
	private DevCalls devCalls;

	public List<Operator> getDeveloper() {

		returnStatus = devCalls.getDeveloper();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstDeveloper = (List<Operator>) returnStatus.getReturnObject();
		return lstDeveloper;
	}

	public List<Department> getDepartment() {

		returnStatus = devCalls.getDepartment();

		CommonMethods.catchreturnstatus(returnStatus);

		List<Department> lstDept = (List<Department>) returnStatus.getReturnObject();

		return lstDept;
	}

	public List<Module> getModule(Integer deptNo) {

		returnStatus = devCalls.getModule(deptNo);
		CommonMethods.catchreturnstatus(returnStatus);

		List<Module> lstModules = (List<Module>) returnStatus.getReturnObject();

		return lstModules;
	}

	public List<Customer> getCustomer() {

		returnStatus = devCalls.getCustomer();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Customer> lstCustomer = (List<Customer>) returnStatus.getReturnObject();
		return lstCustomer;
	}

	public List<CustStaff> getDeptAuthorize() {
		returnStatus = devCalls.getCustCoOrd();
		CommonMethods.catchreturnstatus(returnStatus);

		List<CustStaff> lstDeptAuthorize = (List<CustStaff>) returnStatus.getReturnObject();
		return lstDeptAuthorize;
	}

	public List<Operator> getRecvFrom() {
		returnStatus = devCalls.getRecvFrom();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstRecvFrom = (List<Operator>) returnStatus.getReturnObject();
		return lstRecvFrom;
	}

	public List<Operator> getCallCoOrd() {
		returnStatus = devCalls.getCallCoOrd();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstCallCoOrd = (List<Operator>) returnStatus.getReturnObject();
		return lstCallCoOrd;

	}

	public List<Calls> getDeveloperCalls(String asOnDate, long strCallNo, int strDeveleoper, int strDepartment,
			int strModule, int strCustomer, int strType, int strDeptAuthorize, int strRecvFrom, int strCallCoOrd,
			int strCallNature) {

		returnStatus = devCalls.getDeveloperCalls(asOnDate, strCallNo, strDeveleoper, strDepartment, strModule,
				strCustomer, strType, strDeptAuthorize, strRecvFrom, strCallCoOrd, strCallNature);
		CommonMethods.catchreturnstatus(returnStatus);

		List<Calls> lstCalls = (List<Calls>) returnStatus.getReturnObject();

		return lstCalls;

	}

	public CallsImages getImages(String callNo) {

		returnStatus = devCalls.getCallImages(callNo);

		CallsImages lstCallsImages = (CallsImages) returnStatus.getReturnObject();

		return lstCallsImages;

	}

	public List<Calls> updateProgressCall(int callNo) {
		returnStatus = devCalls.updateProgressCall(callNo);

		List<Calls> lstCalls = (List<Calls>) returnStatus.getReturnObject();

		return lstCalls;

	}

	public List<Calls> validateProgressCall(int strDeveleoper) {

		returnStatus = devCalls.validateProgressCall(strDeveleoper);

		List<Calls> lstCall = (List<Calls>) returnStatus.getReturnObject();

		return lstCall;

	}

	public List<Calls> updateReadyCalls(int callNo, String readyDesc) {

		returnStatus = devCalls.updateReadyCalls(callNo, readyDesc);

		List<Calls> lstReadyCalls = (List<Calls>) returnStatus.getReturnObject();

		return lstReadyCalls;

	}

	public List<ReadyCalls> saveReadyCalls(int cNo, String readyDesc, int cModuleId) {

		returnStatus = devCalls.saveReadyCalls(cNo, readyDesc, cModuleId);

		List<ReadyCalls> lstReadyCalls = (List<ReadyCalls>) returnStatus.getReturnObject();

		return lstReadyCalls;

	}

	public List<ReturnCalls> getReturnCall(int strDeveleoper) {

		returnStatus = devCalls.getReturnCall(strDeveleoper);

		List<ReturnCalls> lstReturnCalls = (List<ReturnCalls>) returnStatus.getReturnObject();

		return lstReturnCalls;

	}

	public List<Calls> validateReadyCalls(String callNo) {

		returnStatus = devCalls.validateReadyCalls(callNo);

		List<Calls> lstCall = (List<Calls>) returnStatus.getReturnObject();

		return lstCall;

	}

	public List<Calls> insertProgressCall(String CallNo) {

		returnStatus = devCalls.insertProgressCall(CallNo);

		List<Calls> lstInsert = (List<Calls>) returnStatus.getReturnObject();

		return lstInsert;

	}

	public List<Calls> updateProgressCancel(String callNo) {

		returnStatus = devCalls.updateProgressCancel(callNo);

		List<Calls> lstProgressCancel = (List<Calls>) returnStatus.getReturnObject();

		return lstProgressCancel;

	}

	public void updateDevPriority(String DevPriority, String selectedCallno) {

		returnStatus = devCalls.updateDevPriority(DevPriority, selectedCallno);

	}

	public void updateDev(int cNo, int dev) {

		returnStatus = devCalls.updateDev(cNo, dev);
	}

}
