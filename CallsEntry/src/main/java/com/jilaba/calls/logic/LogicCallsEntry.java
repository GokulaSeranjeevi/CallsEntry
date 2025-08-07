package com.jilaba.calls.logic;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.common.TransactionManager;
import com.jilaba.calls.dao.CallsEntryDao;
import com.jilaba.calls.model.Calls;
import com.jilaba.calls.model.CallsImages;
import com.jilaba.calls.model.CustStaff;
import com.jilaba.calls.model.Customer;
import com.jilaba.calls.model.Department;
import com.jilaba.calls.model.Designation;
import com.jilaba.calls.model.Operator;
import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.JilabaException;

@Component
@Scope("prototype")
public class LogicCallsEntry {

	private ReturnStatus returnStatus;

	@Autowired
	private CallsEntryDao callsEntryDao;
	@Autowired
	private JdbcTemplate tranJdbcTemplate;

	public List<Operator> getCallFrom(Integer desgId) {

		returnStatus = callsEntryDao.getOperator(desgId);
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstCallFrom = (List<Operator>) returnStatus.getReturnObject();

		return lstCallFrom;
	}

	public List<Customer> getCilent() {

		returnStatus = callsEntryDao.getClients();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Customer> lstClient = (List<Customer>) returnStatus.getReturnObject();

		return lstClient;
	}

	public List<Department> getDepartment() {

		returnStatus = callsEntryDao.getDepartment();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Department> lstdept = (List<Department>) returnStatus.getReturnObject();

		return lstdept;

	}

	public List<CustStaff> getCustCoOrd() {

		returnStatus = callsEntryDao.getCustCoOrd();
		CommonMethods.catchreturnstatus(returnStatus);

		List<CustStaff> lstStaffs = (List<CustStaff>) returnStatus.getReturnObject();
		return lstStaffs;
	}

	public List<Operator> getCallCoOrd() {

		returnStatus = callsEntryDao.getCallcoOrd();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstCallCoOrd = (List<Operator>) returnStatus.getReturnObject();
		return lstCallCoOrd;
	}

	public List<com.jilaba.calls.model.Module> getModule(Integer dept) {

		returnStatus = callsEntryDao.getModule(dept);
		CommonMethods.catchreturnstatus(returnStatus);

		List<com.jilaba.calls.model.Module> lstModule = (List<com.jilaba.calls.model.Module>) returnStatus
				.getReturnObject();
		return lstModule;
	}

	public List<Operator> getDevCoOrd() {

		returnStatus = callsEntryDao.getDevCoOrd();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstDevCoOrd = (List<Operator>) returnStatus.getReturnObject();
		return lstDevCoOrd;
	}

	@SuppressWarnings("unchecked")
	public List<Calls> getCallSave(Calls calls, String call, byte[] lblImage1, byte[] lblImage2, byte[] lblImage3,
			byte[] lblImage4) throws Exception {
		List<Calls> lstCalls = null;
		TransactionManager transactionManager = null;
		try {
			transactionManager = new TransactionManager();
			transactionManager.begin(tranJdbcTemplate.getDataSource());

			returnStatus = callsEntryDao.getCallSave(calls);
			CommonMethods.catchreturnstatus(returnStatus);
			lstCalls = (List<Calls>) returnStatus.getReturnObject();

			if (lblImage1 != null || lblImage2 != null || lblImage3 != null || lblImage4 != null) {
				saveCallImages(call, lblImage1, lblImage2, lblImage3, lblImage4);
			}

			transactionManager.commit();
		} catch (Exception e) {
			if (transactionManager != null)
				transactionManager.rollback();

			throw new JilabaException(e.getMessage());
		}

		return lstCalls;
	}

	public String getLastCallNo() {

		String callNo = callsEntryDao.getLastCallNo();

		return callNo;
	}

	public List<CallsImages> saveCallImages(String callNo, byte[] lblImage1Path, byte[] lblImage2Path,
			byte[] lblImage3Path, byte[] lblImage4Path) {

		callsEntryDao.getsaveCallImages(callNo, lblImage1Path, lblImage2Path, lblImage3Path, lblImage4Path);

		List<CallsImages> lstCallsImages = (List<CallsImages>) returnStatus.getReturnObject();

		return lstCallsImages;

	}

	public List<Calls> getCalls(String fromDate, String toDate, int strViewRecby, int strViewCallCoOrd,
			int strViewCustCoOrd, int strViewDevCoOrd, int strViewClient, int strViewDeptAuthorize,
			int strViewDepartment, int strViewModule, String strOrderby, String callNo, int strViewDesignation,
			int strViewType, int strViewNature) {

		returnStatus = callsEntryDao.getCalls(fromDate, toDate, strViewRecby, strViewCallCoOrd, strViewCustCoOrd,
				strViewDevCoOrd, strViewClient, strViewDeptAuthorize, strViewDepartment, strViewModule, strOrderby,
				callNo, strViewDesignation, strViewType, strViewNature);
		CommonMethods.catchreturnstatus(returnStatus);

		List<Calls> lstCalls = (List<Calls>) returnStatus.getReturnObject();

		return lstCalls;

	}

	public void updateCallsEdit(String cmbCallFrom, String cmbCustomer, String cmbDepartment, String cmbCustCoOrd,
			String cmbCallCoOrd, String cmbModule, String txtRefNo, String txtOption, String cmbNature, String txtDesc,
			String cmbDevCoOrd, String txtEditCallNo) {

		callsEntryDao.updateCallsEdit(cmbCallFrom, cmbCustomer, cmbDepartment, cmbCustCoOrd, cmbCallCoOrd, cmbModule,
				txtRefNo, txtOption, cmbNature, txtDesc, cmbDevCoOrd, txtEditCallNo);

	}

	public void updateCallImages(String callNo, byte[] lblImage1, byte[] lblImage2, byte[] lblImage3, byte[] lblImage4,
			boolean blnNewImageAdd) {

		callsEntryDao.updateCallImage(callNo, lblImage1, lblImage2, lblImage3, lblImage4, blnNewImageAdd);
	}

	public void insertJsonToSQL(String name, String jsonString, int Callno) throws SQLException {

		callsEntryDao.insertJsonToSQL(name, jsonString, Callno);
	}

	public String fetchJsonFromSQL(String filename) throws SQLException {

		return callsEntryDao.fetchJsonFromSQL(filename);
	}

	public String getFileName(String callNo) {
		return callsEntryDao.getFileName(callNo);
	}

	public List<Designation> getDesignation() {
		returnStatus = callsEntryDao.getDesignation();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Designation> lstdesignation = (List<Designation>) returnStatus.getReturnObject();

		return lstdesignation;
	}

}
