package com.jilaba.calls.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.TransactionManager;
import com.jilaba.calls.dao.CallsEntryDao;
import com.jilaba.calls.model.Calls;
import com.jilaba.calls.model.CallsImages;
import com.jilaba.calls.model.CustStaff;
import com.jilaba.calls.model.Customer;
import com.jilaba.calls.model.Department;
import com.jilaba.calls.model.Module;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.query.CallsEntryQuery;
import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;

@Component
public class CallsEntryDaoImpl implements CallsEntryDao {

	@Autowired
	private CallsEntryQuery callsEntryQuery;
	private TransactionManager transactionManager = null;

	@Autowired
	private JdbcTemplate tranJdbcTemplate;

	private Integer callno;

	@Override
	public ReturnStatus getOperator() {

		List<Operator> lstCallFrom;

		try {

			lstCallFrom = tranJdbcTemplate.query(callsEntryQuery.getCallFrom(), new CallFromRowmapper());

			return new ReturnStatus(true, lstCallFrom);

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	@Override
	public ReturnStatus getClients() {

		List<Customer> lstClient;

		try {

			lstClient = tranJdbcTemplate.query(callsEntryQuery.getClient(), new ClientRowmapper());

			return new ReturnStatus(true, lstClient);

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class ClientRowmapper implements RowMapper<Customer> {

		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

			Customer client = new Customer();

			client.setCustId(rs.getInt("custId"));
			client.setcustName(rs.getString("custName"));

			return client;
		}
	}

	class CallFromRowmapper implements RowMapper<Operator> {

		@Override
		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {

			Operator callFrom = new Operator();

			callFrom.setStaffid(rs.getInt("Staffid"));
			callFrom.setStaffname(rs.getString("StaffName"));
			callFrom.setPwd(rs.getString("pwd"));
			callFrom.setActive(rs.getString("Active"));

			return callFrom;
		}
	}

	@Override
	public ReturnStatus getDepartment() {

		List<Department> lstDeartment;

		try {

			lstDeartment = tranJdbcTemplate.query(callsEntryQuery.getDepartment(), new DepartmentRowmapper());

			return new ReturnStatus(true, lstDeartment);

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class DepartmentRowmapper implements RowMapper<Department> {

		@Override
		public Department mapRow(ResultSet rs, int rowNum) throws SQLException {

			Department department = new Department();

			department.setdNo(rs.getInt("dNo"));
			department.setDepartment(rs.getString("Department"));

			return department;
		}
	}

	@Override
	public ReturnStatus getCallcoOrd() {
		List<Operator> lstCallCoOrd;

		try {

			lstCallCoOrd = tranJdbcTemplate.query(callsEntryQuery.getCallCoOrd(), new CallCoOrdRowmapper());

			return new ReturnStatus(true, lstCallCoOrd);

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class CallCoOrdRowmapper implements RowMapper<Operator> {
		@Override
		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {

			Operator callCoOrd = new Operator();

			callCoOrd.setStaffid(rs.getInt("StaffId"));
			callCoOrd.setStaffname(rs.getString("StaffName"));

			return callCoOrd;
		}
	}

	@Override
	public ReturnStatus getCustCoOrd() {
		List<CustStaff> lstCustStaff;

		try {

			lstCustStaff = tranJdbcTemplate.query(callsEntryQuery.getCustStaff(), new staffRowmapper());

			return new ReturnStatus(true, lstCustStaff);

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class staffRowmapper implements RowMapper<CustStaff> {
		@Override
		public CustStaff mapRow(ResultSet rs, int rowNum) throws SQLException {

			CustStaff staff = new CustStaff();

			staff.setCustStaffId(rs.getString("custStaffId"));
			staff.setCustStaffName(rs.getString("custStaffName"));

			return staff;
		}
	}

	@Override
	public ReturnStatus getModule(Integer dept) {
		List<Module> lstModule;

		try {

			lstModule = tranJdbcTemplate.query(callsEntryQuery.getModule(dept), new ModuleRowmapper());

			return new ReturnStatus(true, lstModule);

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class ModuleRowmapper implements RowMapper<Module> {
		@Override
		public Module mapRow(ResultSet rs, int rowNum) throws SQLException {
			Module module = new Module();

			module.setModuleId(rs.getInt("ModuleId"));
			module.setModuleName(rs.getString("ModuleName"));

			return module;
		}
	}

	@Override
	public ReturnStatus getDevCoOrd() {
		List<Operator> lstDevCoOrd;

		try {

			lstDevCoOrd = tranJdbcTemplate.query(callsEntryQuery.getDevCoOrd(), new CallFromRowmapper());

			return new ReturnStatus(true, lstDevCoOrd);

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	@Override
	public ReturnStatus getCallSave(Calls calls) throws Exception {

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(tranJdbcTemplate);
		simpleJdbcCall.setProcedureName("Sp_SaveCallsEntry");

		callno = tranJdbcTemplate.queryForObject(callsEntryQuery.getCallno(), Integer.class);

		try {

			Map<String, Object> mapCallSave = new HashMap<String, Object>();
			mapCallSave.put("cusid", calls.getCusid());
			mapCallSave.put("cdate", calls.getCdate());
			mapCallSave.put("moduleid", calls.getModuleid());
			mapCallSave.put("moption", calls.getMoption());
			mapCallSave.put("description", calls.getDescription());
			mapCallSave.put("receby", calls.getReceby());
			mapCallSave.put("duedate", calls.getDuedate());
			mapCallSave.put("sugto", calls.getSugto());
			mapCallSave.put("compltedby", calls.getCompltedby());
			mapCallSave.put("compon", calls.getCompon());
			mapCallSave.put("note", calls.getNote());
			mapCallSave.put("apptime", calls.getApptime());
			mapCallSave.put("atsite", calls.getAtsite());
			mapCallSave.put("callnature", calls.getCallnature());
			mapCallSave.put("approved", calls.getApproved());
			mapCallSave.put("ApprovalBy", calls.getApprovalBy());
			mapCallSave.put("Ready", calls.getReady());
			mapCallSave.put("CallsId", calls.getCallsId());
			mapCallSave.put("Remind", calls.getRemind());
			mapCallSave.put("Modulecode", calls.getModulecode());
			mapCallSave.put("Compcode", calls.getCompcode());
			mapCallSave.put("Mandays", calls.getMandays());
			mapCallSave.put("Hours", calls.getHour());
			mapCallSave.put("WebActive", calls.getWebActive());
			mapCallSave.put("dno", calls.getDno());
			mapCallSave.put("ready_by", calls.getReady_by());
			mapCallSave.put("progress", calls.getProgress());
			mapCallSave.put("ready_date", calls.getReady_date());
			mapCallSave.put("ready_desc", calls.getReady_desc());
			mapCallSave.put("Testing", calls.getTesting());
			mapCallSave.put("TestDate", calls.getTestDate());
			mapCallSave.put("Targetdate", calls.getTargetdate());
			mapCallSave.put("ProcessingTime", calls.getProcessingTime());
			mapCallSave.put("targetHours", calls.getTargetHours());
			mapCallSave.put("AUTHORISED", calls.getAUTHOR0ISED());
			mapCallSave.put("OUTCALLS", calls.getOUTCALLS());
			mapCallSave.put("STKPICPATH", calls.getSTKPICPATH());
			mapCallSave.put("DeptNo", calls.getDeptNo());
			mapCallSave.put("RefNo", calls.getRefNo());
			mapCallSave.put("CompVersion", calls.getCompVersion());
			mapCallSave.put("CallTakenDate", calls.getCallTakenDate());
			mapCallSave.put("DevDuetime", calls.getDevDuetime());
			mapCallSave.put("Priority", calls.getPriority());
			mapCallSave.put("AgCallNo", calls.getAgCallNo());
			mapCallSave.put("callno", callno);
			mapCallSave.put("callentryIP", calls.getCallentryIP());
			mapCallSave.put("custcoordinator", calls.getCustcoordinator());
			mapCallSave.put("custcordinator_name", calls.getCustcordinator_name());
			mapCallSave.put("ready_time", calls.getReady_time());
			mapCallSave.put("AssignDate", calls.getAssignDate());
			mapCallSave.put("AssignTime", calls.getAssignTime());
			mapCallSave.put("Transferflag", calls.getTransferflag());
			mapCallSave.put("DevstartTime", calls.getDevstartTime());
			mapCallSave.put("DiffHours", calls.getDiffHours());
			mapCallSave.put("documentnumber", calls.getDocumentnumber());
			mapCallSave.put("uniquekey", calls.getUniquekey());
			mapCallSave.put("ticketrecby", calls.getTicketrecby());
			mapCallSave.put("testtime", calls.getTesttime());
			mapCallSave.put("ticketno", calls.getTicketno());
			mapCallSave.put("CallTime", calls.getCallTime(null));
			mapCallSave.put("CallsRecvMode", calls.getCallsRecvMode());
			mapCallSave.put("assignpriority", calls.getAssignpriority());
			mapCallSave.put("callcoordinator", calls.getCallcoordinator());
			mapCallSave.put("Transferdate", calls.getTransferdate());
			mapCallSave.put("CompDesc", calls.getCompDesc());
			mapCallSave.put("Version", calls.getVersion());
			mapCallSave.put("DevPriority", calls.getDevPriority());
			mapCallSave.put("testresult", calls.getTestresult());
			mapCallSave.put("testedmodules", calls.getTestedmodules());
			mapCallSave.put("testedremarks", calls.getTestedremarks());
			mapCallSave.put("testedmodules", calls.getTestedmodules());
			mapCallSave.put("ExpNature", calls.getExpNature());
			mapCallSave.put("ProposedDev", calls.getProposedDev());
			mapCallSave.put("OrgCallTakenDate", calls.getOrgCallTakenDate());
			mapCallSave.put("GrpRemID", calls.getGrpRemID());

			// for (Map.Entry<String, Object> entry : mapCallSave.entrySet()) {
			// String key = entry.getKey();
			// Object val = entry.getValue();
			// System.out.println(key + " | " + val);
			// }

			simpleJdbcCall.execute(mapCallSave);

			return new ReturnStatus(true);

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	@Override
	public SimpleJdbcCall getSimpleJdbcCall(String procedureName, JdbcTemplate jdbcTemplate) {

		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
			simpleJdbcCall.withSchemaName(jdbcTemplate.getDataSource().getConnection().getCatalog());
			simpleJdbcCall.withProcedureName(procedureName);

			return simpleJdbcCall;
		} catch (SQLException e) {

			throw new JilabaException(ErrorHandling.handleError(e));
		}

	}

	@Override
	public String getLastCallNo() {

		String callNo = tranJdbcTemplate.queryForObject(callsEntryQuery.getLastCallNo(), String.class, new Object[] {});

		return callNo;
	}

	@Override
	public void getsaveCallImages(String callNo, byte[] lblImage1Path, byte[] lblImage2Path, byte[] lblImage3Path,
			byte[] lblImage4Path) {
//		String clNo = tranJdbcTemplate.queryForObject(callsEntryQuery.getCallno(), String.class);

		try {
			tranJdbcTemplate.update(callsEntryQuery.getsaveCallsImages(callno, lblImage1Path, lblImage2Path,
					lblImage3Path, lblImage4Path), callno, lblImage1Path, lblImage2Path, lblImage3Path, lblImage4Path);

			// tranjdbcTemplate.queryForObject(
			// callsEntryQuery.getsaveCallsImages(callNo, lblImage1Path, lblImage2Path,
			// lblImage3Path,
			// lblImage4Path),
			// new Object[] { callNo, lblImage1Path, lblImage2Path, lblImage3Path,
			// lblImage4Path }, String.class);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}

	@Override
	public ReturnStatus getCalls(String fromDate, String toDate, int strViewRecby, int strViewCallCoOrd,
			int strViewCustCoOrd, int strViewDevCoOrd, int strViewClient, int strViewDeptAuthorize,
			int strViewDepartment, int strViewModule, String strOrderby, String callNo) {

		try {
			List<Calls> lstCalls;
			List<Object> params = new ArrayList<Object>();

			if (strViewRecby != 0) {
				params.add(strViewRecby);
			}
			if (strViewCallCoOrd != 0) {
				params.add(strViewCallCoOrd);
			}
			if (strViewCustCoOrd != 0) {
				params.add(strViewCustCoOrd);
			}
			if (strViewDevCoOrd != 0) {
				params.add(strViewDevCoOrd);
			}
			if (strViewClient != 0) {
				params.add(strViewClient);
			}
			if (strViewDeptAuthorize != 0) {
				params.add(strViewDeptAuthorize);
			}
			if (strViewDepartment != 0) {
				params.add(strViewDepartment);
			}
			if (strViewModule != 0) {
				params.add(strViewModule);
			}

			String strQuery = (callsEntryQuery.getCalls(fromDate, toDate, strViewRecby, strViewCallCoOrd,
					strViewCustCoOrd, strViewDevCoOrd, strViewClient, strViewDeptAuthorize, strViewDepartment,
					strViewModule, strOrderby, callNo));

			lstCalls = tranJdbcTemplate.query(strQuery, new CallsRowMapper(), params.toArray());

			return new ReturnStatus(true, lstCalls);

		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage());
		}

	}

	class CallsRowMapper implements RowMapper<Calls> {
		@Override
		public Calls mapRow(ResultSet rs, int rowNum) throws SQLException {

			Calls calls = new Calls();

			calls.setCallno(rs.getInt("CallNo"));
			calls.setCdate

			(rs.getString("CallDate"));
			calls.setCustName(rs.getString("CustomerName"));
			calls.setDescription(rs.getString("CallDescription"));
			calls.setMoption(rs.getString("MOption"));
			calls.setReceiverName(rs.getString("RecbyName"));
			calls.setExpNature(rs.getString("callNature"));
			calls.setAuthorizedName(rs.getString("Authorised"));
			calls.setDepartmentName(rs.getString("Department"));
			calls.setModuleName(rs.getString("modulename"));
			calls.setCustcordinator_name(rs.getString("custcordinator_name"));
			calls.setCallcoordinator(rs.getString("CallCoOrdinator"));
			calls.setRefNo(rs.getInt("RefNo"));
			calls.setDevCoOrdName(rs.getString("DevCoOrd"));
			CallsImages callsImages = new CallsImages();

			callsImages.setImage1(rs.getBytes("Image1"));
			callsImages.setImage2(rs.getBytes("Image2"));
			callsImages.setImage3(rs.getBytes("Image3"));
			callsImages.setImage4(rs.getBytes("Image4"));
			calls.setLstCallsImages(Arrays.asList(callsImages));

			return calls;
		}
	}

	@Override
	public ReturnStatus updateCallsEdit(String cmbCallFrom, String cmbCustomer, String cmbDepartment,
			String cmbCustCoOrd, String cmbCallCoOrd, String cmbModule, String txtRefNo, String txtOption,
			String cmbNature, String txtDesc, String cmbDevCoOrd, String txtCallNo) {

		try {

			tranJdbcTemplate.update(callsEntryQuery.updateCallEdit(cmbCallFrom, cmbCustomer, cmbDepartment,
					cmbCustCoOrd, cmbCallCoOrd, cmbModule, txtRefNo, txtOption, cmbNature.substring(0, 1), txtDesc,
					cmbDevCoOrd, txtCallNo));

			return new ReturnStatus(true);

		} catch (Exception e) {

			return new ReturnStatus(false, e.getMessage());
		}

	}

	@Override
	public ReturnStatus updateCallImage(String callNo, byte[] lblImage1, byte[] lblImage2, byte[] lblImage3,
			byte[] lblImage4, boolean blnNewImageAdd) {

		try {

			if (blnNewImageAdd == true) {
				tranJdbcTemplate.update(
						callsEntryQuery.getsaveCallsImages(callno, lblImage1, lblImage2, lblImage3, lblImage4), callno,
						lblImage1, lblImage2, lblImage3, lblImage4);
			} else {
				tranJdbcTemplate.update(
						callsEntryQuery.updateCallImages(callno, lblImage1, lblImage2, lblImage3, lblImage4), lblImage1,
						lblImage2, lblImage3, lblImage4, callno);
			}

			return new ReturnStatus(true);

		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage());
		}

	}

}