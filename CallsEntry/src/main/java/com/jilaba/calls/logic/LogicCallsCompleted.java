package com.jilaba.calls.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.dao.CallsCompletedDao;
import com.jilaba.calls.model.CallsCompleted;
import com.jilaba.calls.model.Customer;
import com.jilaba.calls.model.Department;
import com.jilaba.calls.model.Module;
import com.jilaba.calls.model.Operator;
import com.jilaba.common.ReturnStatus;

@Component
@Scope("prototype")
public class LogicCallsCompleted {

	private ReturnStatus returnStatus;

	@Autowired
	private CallsCompletedDao callCompletedDao;

	public List<Operator> getDeveloper() {

		returnStatus = callCompletedDao.getDeveloper();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstDeveloper = (List<Operator>) returnStatus.getReturnObject();

		return lstDeveloper;
	}

	public List<Customer> getCustomer() {

		returnStatus = callCompletedDao.getCustomer();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Customer> lstCustomer = (List<Customer>) returnStatus.getReturnObject();

		return lstCustomer;
	}

	public List<Operator> getDeptAuthorize() {

		returnStatus = callCompletedDao.getDeptAuthorize();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstdeptAuthorize = (List<Operator>) returnStatus.getReturnObject();

		return lstdeptAuthorize;
	}

	public List<Department> getDepartment() {

		returnStatus = callCompletedDao.getDepartment();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Department> lstdept = (List<Department>) returnStatus.getReturnObject();

		return lstdept;
	}

	public List<Module> getModule(Integer dept) {
		returnStatus = callCompletedDao.getModule(dept);
		CommonMethods.catchreturnstatus(returnStatus);

		List<Module> lstModule = (List<Module>) returnStatus.getReturnObject();

		return lstModule;
	}

	public List<CallsCompleted> getCompletedCalls(String strCallFromDate, String strCallToDate,
			String strCompletedFromDate, String strCompletedToDate, int strCmbDeveloper, int strCmbClient,
			int strCmbDeptAuthorize, int strCmbDepartment, int strCmbModule, String strOrderby, String CallNo) {

		returnStatus = callCompletedDao.getCompletedCalls(strCallFromDate, strCallToDate, strCompletedFromDate,
				strCompletedToDate, strCmbDeveloper, strCmbClient, strCmbDeptAuthorize, strCmbDepartment, strCmbModule,
				strOrderby, CallNo);
		CommonMethods.catchreturnstatus(returnStatus);

		List<CallsCompleted> lstCallsCompleted = (List<CallsCompleted>) returnStatus.getReturnObject();

		return lstCallsCompleted;

	}

	public void updateDeliveredCalls(int cNo, String completedDesc) {

		callCompletedDao.updateDeliveredCalls(cNo, completedDesc);

	}

}
