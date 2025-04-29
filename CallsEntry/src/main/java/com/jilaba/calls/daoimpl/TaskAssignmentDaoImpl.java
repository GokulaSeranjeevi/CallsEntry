package com.jilaba.calls.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.jilaba.calls.dao.TaskAssignment;
import com.jilaba.calls.forms.FrmLogin;
import com.jilaba.calls.model.Calls;
import com.jilaba.calls.model.CallsImages;
import com.jilaba.calls.model.CustStaff;
import com.jilaba.calls.model.Customer;
import com.jilaba.calls.model.Department;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.model.ReturnCalls;
import com.jilaba.calls.query.TaskAssignmentQuery;
import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;

@Component
public class TaskAssignmentDaoImpl implements TaskAssignment {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
	private LocalDateTime now = LocalDateTime.now();

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private TaskAssignmentQuery task;

	@Override
	public ReturnStatus getDeveloper() {

		List<Operator> lstDevelopers;

		try {

			lstDevelopers = jdbcTemplate.query(task.getDevelopers(), new DeveloperRowMapper());

			return new ReturnStatus(true, lstDevelopers);
		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class DeveloperRowMapper implements RowMapper<Operator> {
		@Override
		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {

			Operator operator = new Operator();

			operator.setStaffname(rs.getString("StaffName"));
			operator.setStaffid(rs.getInt("Staffid"));
			operator.setActive("Active");

			return operator;

		}
	}

	@Override
	public ReturnStatus getDepartment() {

		List<Department> lstDept;

		try {

			lstDept = jdbcTemplate.query(task.getDepartment(), new DeptRowMapper());

			return new ReturnStatus(true, lstDept);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class DeptRowMapper implements RowMapper<Department> {
		@Override
		public Department mapRow(ResultSet rs, int rowNum) throws SQLException {

			Department dept = new Department();

			dept.setDepartment(rs.getString("Department"));
			dept.setdNo(rs.getInt("dNo"));

			return dept;

		}
	}

	@Override
	public ReturnStatus getModule(Integer deptNo) {

		List<com.jilaba.calls.model.Module> lstModules;

		try {

			lstModules = jdbcTemplate.query(task.getModules(deptNo), new ModuleRowMapper());

			return new ReturnStatus(true, lstModules);

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class ModuleRowMapper implements RowMapper<com.jilaba.calls.model.Module> {
		@Override
		public com.jilaba.calls.model.Module mapRow(ResultSet rs, int rowNum) throws SQLException {

			com.jilaba.calls.model.Module module = new com.jilaba.calls.model.Module();

			module.setModuleName(rs.getString("ModuleName"));
			module.setModuleId(rs.getInt("Moduleid"));

			return module;
		}

	}

	@Override
	public ReturnStatus getCustomer() {

		List<Customer> lstCustomer;

		try {

			lstCustomer = jdbcTemplate.query(task.getCustomer(), new CustomerRowMapper());

			return new ReturnStatus(true, lstCustomer);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));

		}

	}

	class CustomerRowMapper implements RowMapper<Customer> {

		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

			Customer customer = new Customer();

			customer.setcustName(rs.getString("CustName"));
			customer.setCustId(rs.getInt("CustId"));

			return customer;

		}
	}

	@Override
	public ReturnStatus getCustCoOrd() {

		List<CustStaff> lstDeptAuthorize;

		try {

			lstDeptAuthorize = jdbcTemplate.query(task.getCustCoOrd(), new DeptAuthorizeRowMapper());

			return new ReturnStatus(true, lstDeptAuthorize);

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class DeptAuthorizeRowMapper implements RowMapper<CustStaff> {

		@Override
		public CustStaff mapRow(ResultSet rs, int rowNum) throws SQLException {

			CustStaff deptAuthorize = new CustStaff();

			deptAuthorize.setCustStaffName(rs.getString("custStaffName"));
			deptAuthorize.setCustStaffId(rs.getString("custStaffid"));

			return deptAuthorize;

		}
	}

	@Override
	public ReturnStatus getRecvFrom() {

		List<Operator> lstRecvFrom;

		try {

			lstRecvFrom = jdbcTemplate.query(task.getRecvFrom(), new RecvFromRowMapper());

			return new ReturnStatus(true, lstRecvFrom);
		} catch (Exception e) {

			return new ReturnStatus();
		}

	}

	class RecvFromRowMapper implements RowMapper<Operator> {
		@Override
		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {

			Operator RecvFrom = new Operator();

			RecvFrom.setStaffname(rs.getString("StaffName"));
			RecvFrom.setStaffid(rs.getInt("Staffid"));

			return RecvFrom;

		}

	}

	@Override
	public ReturnStatus getCallCoOrd() {

		List<Operator> lstCallCoOrd;

		try {

			lstCallCoOrd = jdbcTemplate.query(task.getCallCoOrd(), new CallCoOrdRowMapper());

			return new ReturnStatus(true, lstCallCoOrd);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class CallCoOrdRowMapper implements RowMapper<Operator> {
		@Override
		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {

			Operator callCoOrd = new Operator();

			callCoOrd.setStaffname(rs.getString("StaffName"));
			callCoOrd.setStaffid(rs.getInt("Staffid"));

			return callCoOrd;

		}
	}

	@Override
	public ReturnStatus getDeveloperCalls(String asOnDate, long strCallNo, int strDeveleoper, int strDepartment,
			int strModule, int strCustomer, int strType, int strDeptAuthorize, int strRecvFrom, int strCallCoOrd,
			int strCallNature) {
		List<Calls> lstCalls;
		try {
			List<Object> params = new ArrayList<>();

			if (strDeveleoper != 0) {
				params.add(strDeveleoper);
			}
			if (strDepartment != 0) {
				params.add(strDepartment);
			}
			if (strModule != 0) {
				params.add(strModule);
			}
			if (strRecvFrom != 0) {
				params.add(strRecvFrom);
			}
			if (strCallCoOrd != 0) {
				params.add(strCallCoOrd);
			}
			if (strCallNo != 0) {
				params.add(strCallNo);
			}

			// Ensure the correct number of parameters are passed
			System.out.println("Params: " + params);

			// Construct query
			String sqlQuery = task.getDeveloperCalls(asOnDate, strDeveleoper, strDepartment, strModule,
					strCustomer, strType, strRecvFrom, strCallCoOrd, strCallNo, strCallNature);

			lstCalls = jdbcTemplate.query(sqlQuery, new DeveloperCallsRowMapper(), params.toArray());

			return new ReturnStatus(true, lstCalls);
		} catch (

		Exception e) {
//			e.printStackTrace();
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class DeveloperCallsRowMapper implements RowMapper<Calls> {
		@Override
		public Calls mapRow(ResultSet rs, int rowNum) throws SQLException {

			Calls calls = new Calls();

			calls.setCallno(rs.getInt("Callno"));
			calls.setCdate(rs.getString("Cdate"));
			calls.setPriority(rs.getDouble("DevPriority"));
			calls.setDescription(rs.getString("Description"));
			calls.setDevDuetime(rs.getDouble("DevDuetime"));
			calls.setDuedate(rs.getString("DueDate"));
			calls.setAssignDate(rs.getString("AssignDate"));
			calls.setCallnature(rs.getString("Callnature"));
			calls.setModuleid(rs.getInt("Moduleid"));
			calls.setModuleName(rs.getString("ModuleName"));
			calls.setCusid(rs.getString("Cusid"));
			calls.setCustName(rs.getString("CustName"));
			calls.setCallTakenDate(rs.getString("CallTakenDate"));
			calls.setTicketno(rs.getString("TicketNo"));
			calls.setCallcoordinator(rs.getString("Callcoordinator"));
			calls.setReceiverName(rs.getString("RecbyName"));
			calls.setProgress(rs.getInt("Progress"));
			calls.setImgAttach(rs.getString("imgAttach"));
			calls.setTestresult(rs.getString("TestResult"));
			calls.setReady(rs.getString("Ready"));
			calls.setMoption(rs.getString("mOption"));

			return calls;
		}

	}

	@Override
	public ReturnStatus getCallImages(String callno) {

		CallsImages lstCallsImages;

		try {

			lstCallsImages = jdbcTemplate.queryForObject(task.getCallsImages(callno),
					new callsImageRowMapper(), new Object[] { callno });

			return new ReturnStatus(true, lstCallsImages);

		} catch (EmptyResultDataAccessException e) {
			return new ReturnStatus(false, "Image Not Found ...!");
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new JilabaException("Multiple Images Found");
		} catch (Exception e) {

			e.printStackTrace();
			throw new JilabaException(ErrorHandling.handleError(e));

		}

	}

	class callsImageRowMapper implements RowMapper<CallsImages> {
		@Override
		public CallsImages mapRow(ResultSet rs, int rowNum) throws SQLException {

			CallsImages callsImages = new CallsImages();

			callsImages.setCallNo(rs.getInt("callno"));
			callsImages.setImage1(rs.getBytes("image1"));
			callsImages.setImage2(rs.getBytes("image2"));
			callsImages.setImage3(rs.getBytes("image3"));
			callsImages.setImage4(rs.getBytes("image4"));

			return callsImages;

		}
	}

	@Override
	public ReturnStatus updateProgressCall(int callNo) {

		try {

			jdbcTemplate.update(task.updateProgressCall(), new Object[] { callNo });
			return new ReturnStatus(true);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));

		}

	}

	@Override
	public ReturnStatus validateProgressCall(int Developer) {

		List<Calls> lstCall;
		try {
			lstCall = jdbcTemplate.query(task.validateProgressCall(Developer), new validateProgressCall());

			System.out.println(lstCall);

			return new ReturnStatus(true, lstCall);
		} catch (Exception e) {
			return new ReturnStatus(false);
		}

	}

	class validateProgressCall implements RowMapper<Calls> {
		@Override
		public Calls mapRow(ResultSet rs, int rowNum) throws SQLException {

			Calls calls = new Calls();

			// calls.setProgress(rs.getInt("Progress"));
			calls.setSugto(rs.getInt("sugto"));

			return calls;

		}
	}

	@Override
	public ReturnStatus updateReadyCalls(int callNo, String readyDesc) {
		try {

			jdbcTemplate.update(task.updateReadyCals(), new Object[] { readyDesc, callNo });
			return new ReturnStatus(true);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));

		}
	}

	@Override
	public ReturnStatus saveReadyCalls(int cNo, String readyDesc, int cModuleId) {

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
		simpleJdbcCall.setProcedureName("Sp_SaveReadyCalls");

		try {

			Map<String, Object> mapCallSave = new HashMap<String, Object>();

			mapCallSave.put("callno", cNo);
			mapCallSave.put("readydescription", readyDesc);
			mapCallSave.put("module", cModuleId);
			mapCallSave.put("opercode", FrmLogin.OperCode);
			mapCallSave.put("updated", now);
			mapCallSave.put("uptime", now);
			mapCallSave.put("cancel", "");
			mapCallSave.put("RDate", now);
			mapCallSave.put("readyby", FrmLogin.OperCode);
			mapCallSave.put("Version", "");

			simpleJdbcCall.execute(mapCallSave);

			return new ReturnStatus(true);

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	@Override
	public ReturnStatus getReturnCall(int Developer) {
		try {

			List<ReturnCalls> lstReturnCalls;
			lstReturnCalls = jdbcTemplate.query(task.getReturnCall(Developer), new validateReturnCall());

			return new ReturnStatus(true, lstReturnCalls);
		} catch (Exception e) {

			return new ReturnStatus(false, e.getMessage());
		}
	}

	class validateReturnCall implements RowMapper<ReturnCalls> {
		@Override
		public ReturnCalls mapRow(ResultSet rs, int rowNum) throws SQLException {

			ReturnCalls returnCalls = new ReturnCalls();

			// calls.setProgress(rs.getInt("Progress"));
			returnCalls.setCallno(rs.getInt("callNo"));
			returnCalls.setSugto(rs.getInt("Sugto"));

			return returnCalls;

		}
	}

	@Override
	public ReturnStatus validateReadyCalls(String callNo) {
		List<Calls> lstCall;
		try {
			lstCall = jdbcTemplate.query(task.validateReadyCalls(callNo), new validateReadyCalls());

			System.out.println(lstCall);

			return new ReturnStatus(true, lstCall);
		} catch (Exception e) {
			return new ReturnStatus(false);
		}

	}

	class validateReadyCalls implements RowMapper<Calls> {
		@Override
		public Calls mapRow(ResultSet rs, int rowNum) throws SQLException {

			Calls calls = new Calls();

			calls.setSugto(rs.getInt("sugto"));
			calls.setReady(rs.getString("Ready"));
			calls.setCallno(rs.getInt("Callno"));

			return calls;

		}
	}

	@Override
	public ReturnStatus insertProgressCall(String callNo) {

		try {

			jdbcTemplate.update(task.insertProgressCall(callNo), new Object[] {});
			return new ReturnStatus(true);
		} catch (Exception e) {

			return new ReturnStatus(false, e.getMessage());
		}
	}

	@Override
	public ReturnStatus updateProgressCancel(String callNo) {

		try {

			jdbcTemplate.update(task.updateProgressCancel(callNo), new Object[] {});

			return new ReturnStatus(true);

		} catch (Exception e) {

			return new ReturnStatus(false, e.getMessage());
		}

	}

	@Override
	public ReturnStatus updateDevPriority(String devPriority, String selectedCallno) {
		try {

			jdbcTemplate.update(task.updateDevPriority(devPriority, selectedCallno), new Object[] {});

			return new ReturnStatus(true);

		} catch (Exception e) {

			return new ReturnStatus(false, e.getMessage());
		}

	}

	@Override
	public ReturnStatus updateDev(int cNo, int dev) {
		try {

			jdbcTemplate.update(task.updateDevTransfer(cNo, dev), new Object[] {});

			return new ReturnStatus(true);

		} catch (Exception e) {

			return new ReturnStatus(false, e.getMessage());
		}
	}
}
