package com.jilaba.calls.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.dao.ReadyCallsDao;
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
public class LogicReadyCalls {

	private ReturnStatus returnStatus;
	@Autowired
	private ReadyCallsDao readyCallsDao;

	public List<Operator> getDeveloper() {

		returnStatus = readyCallsDao.getDeveloper();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstDeveloper = (List<Operator>) returnStatus.getReturnObject();

		return lstDeveloper;
	}

	public List<Customer> getCustomer() {

		returnStatus = readyCallsDao.getCustomer();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Customer> lstCustomer = (List<Customer>) returnStatus.getReturnObject();

		return lstCustomer;
	}

	public List<CustStaff> getDeptAuthorize() {

		returnStatus = readyCallsDao.getDeptAuthorize();
		CommonMethods.catchreturnstatus(returnStatus);

		List<CustStaff> lstdeptAuthorize = (List<CustStaff>) returnStatus.getReturnObject();

		return lstdeptAuthorize;
	}

	public List<Department> getDepartment() {

		returnStatus = readyCallsDao.getDepartment();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Department> lstdept = (List<Department>) returnStatus.getReturnObject();

		return lstdept;
	}

	public List<Module> getModule() {
		returnStatus = readyCallsDao.getModule();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Module> lstModule = (List<Module>) returnStatus.getReturnObject();

		return lstModule;
	}

	public List<ReadyCalls> getReadyCalls(String strCallFromDate, String strCallToDate, String strReadyFromDate,
			String strReadyToDate, int strCmbDeveloper, int strCmbClient, int strCmbDeptAuthorize, int strCmbDepartment,
			int strCmbModule, String strOrderby, String Callno) {

		returnStatus = readyCallsDao.getReadyCalls(strCallFromDate, strCallToDate, strReadyFromDate, strReadyToDate,
				strCmbDeveloper, strCmbClient, strCmbDeptAuthorize, strCmbDepartment, strCmbModule, strOrderby, Callno);
		CommonMethods.catchreturnstatus(returnStatus);

		List<ReadyCalls> lstReadyCalls = (List<ReadyCalls>) returnStatus.getReturnObject();

		return lstReadyCalls;

	}

	public CallsImages getImages(String callNo) {

		returnStatus = readyCallsDao.getCallImages(callNo);

		CallsImages lstCallsImages = (CallsImages) returnStatus.getReturnObject();

		return lstCallsImages;
	}

	public List<Calls> updateCompletedCalls(int callNo, String completedDesc, String cModuleId) {

		returnStatus = readyCallsDao.updateReadyCalls(callNo, completedDesc, cModuleId);

		List<Calls> lstCompletedCalls = (List<Calls>) returnStatus.getReturnObject();

		return lstCompletedCalls;

	}

	public List<Calls> updateReturnCalls(int callNo, String returnDesc, String cModuleId) {

		returnStatus = readyCallsDao.updateReturnCalls(callNo, returnDesc);

		List<Calls> lstReturnCalls = (List<Calls>) returnStatus.getReturnObject();

		return lstReturnCalls;
	}

	public List<ReturnCalls> saveReadyUnmark(int cNo, String returnDesc, String cModuleId) {

		returnStatus = readyCallsDao.saveReadyUnmark(cNo, returnDesc, cModuleId);
		List<ReturnCalls> lstReturnCalls = (List<ReturnCalls>) returnStatus.getReturnObject();
		return lstReturnCalls;

	}

	public void updateReadyCancel(int cNo) {

		returnStatus = readyCallsDao.updateReadyCancel(cNo);

	}

	public void updateReadyProgress(String callNo) {

		returnStatus = readyCallsDao.updateReadyProgress(callNo);
	}

	public List<ReadyCalls> validateProgressCall() {

		returnStatus = readyCallsDao.validateProgressCall();

		List<ReadyCalls> lstReadyCalls = (List<ReadyCalls>) returnStatus.getReturnObject();

		return lstReadyCalls;

	}

	public int beforeProgressValidate() {
		return readyCallsDao.beforeProgressValidate();

	}

}
