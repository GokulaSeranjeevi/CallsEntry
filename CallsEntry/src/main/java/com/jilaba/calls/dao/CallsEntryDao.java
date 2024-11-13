package com.jilaba.calls.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.jilaba.calls.model.Calls;
import com.jilaba.common.ReturnStatus;

public interface CallsEntryDao {

	public ReturnStatus getOperator();

	public ReturnStatus getClients();

	public ReturnStatus getDepartment();

	public ReturnStatus getCallcoOrd();

	public ReturnStatus getCustCoOrd();

	public ReturnStatus getModule(Integer dept);

	public ReturnStatus getDevCoOrd();

	public ReturnStatus getCallSave(Calls calls) throws Exception;

	public SimpleJdbcCall getSimpleJdbcCall(String string, JdbcTemplate tranjdbcTemplate);

	public String getLastCallNo();

	public void getsaveCallImages(String callNo, byte[] lblImage1Path, byte[] lblImage2Path, byte[] lblImage3Path,
			byte[] lblImage4Path);

	public ReturnStatus getCalls(String fromDate, String toDate, int strViewRecby, int strViewCallCoOrd,
			int strViewCustCoOrd, int strViewDevCoOrd, int strViewClient, int strViewDeptAuthorize,
			int strViewDepartment, int strViewModule, String strOrderby, String callNo);

	public ReturnStatus updateCallsEdit(String cmbCallFrom, String cmbCustomer, String cmbDepartment,
			String cmbCustCoOrd, String cmbCallCoOrd, String cmbModule, String txtRefNo, String txtOption,
			String cmbNature, String txtDesc, String cmbDevCoOrd, String txtEditCallNo);

	public ReturnStatus updateCallImage(String callNo, byte[] lblImage1, byte[] lblImage2, byte[] lblImage3,
			byte[] lblImage4, boolean blnNewImageAdd);

}
