package com.jilaba.calls.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

import com.jilaba.calls.dao.ReadyCallsDao;
import com.jilaba.calls.forms.FrmLogin;
import com.jilaba.calls.model.CallsImages;
import com.jilaba.calls.model.CustStaff;
import com.jilaba.calls.model.Customer;
import com.jilaba.calls.model.Department;
import com.jilaba.calls.model.Module;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.model.ReadyCalls;
import com.jilaba.calls.model.ReturnCalls;
import com.jilaba.calls.query.ReadyCallsQuery;
import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;

@Component
public class ReadyCallsDaoImpl implements ReadyCallsDao {

	@Autowired
	private JdbcTemplate tranJdbcTemplate;

	@Autowired
	private ReadyCallsQuery readyCallsQuery;

	private LocalDateTime now = LocalDateTime.now();

	@Override
	public ReturnStatus getDeveloper() {

		try {

			List<Operator> lstDeveloper;

			lstDeveloper = tranJdbcTemplate.query(readyCallsQuery.getDeveloperCalls(), new DeveloperCallsRowMapper());

			return new ReturnStatus(true, lstDeveloper);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class DeveloperCallsRowMapper implements RowMapper<Operator> {
		@Override
		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {

			Operator operator = new Operator();

			operator.setStaffid(rs.getInt("staffid"));
			operator.setStaffname(rs.getString("staffname"));
			operator.setPwd(rs.getString("pwd"));
			operator.setActive(rs.getString("Active"));

			return operator;
		}
	}

	@Override
	public ReturnStatus getCustomer() {
		try {

			List<Customer> lstCustomer;

			lstCustomer = tranJdbcTemplate.query(readyCallsQuery.getCustomerCalls(), new CustomerCallsRowMapper());

			return new ReturnStatus(true, lstCustomer);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class CustomerCallsRowMapper implements RowMapper<Customer> {
		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

			Customer customer = new Customer();

			customer.setCustId(rs.getInt("custid"));
			customer.setcustName(rs.getString("custName"));

			return customer;
		}
	}

	@Override
	public ReturnStatus getDeptAuthorize() {
		try {

			List<CustStaff> lstOperator;

			lstOperator = tranJdbcTemplate.query(readyCallsQuery.getDeptAuthorize(), new OperatorCallsRowMapper());

			return new ReturnStatus(true, lstOperator);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class OperatorCallsRowMapper implements RowMapper<CustStaff> {
		@Override
		public CustStaff mapRow(ResultSet rs, int rowNum) throws SQLException {

			CustStaff deptAuthorize = new CustStaff();

			deptAuthorize.setCustStaffId(rs.getString("custstaffid"));
			deptAuthorize.setCustStaffName(rs.getString("custstaffname"));
			//			deptAuthorize.setPwd(rs.getString("pwd"));
			//			deptAuthorize.setActive(rs.getString("Active"));

			return deptAuthorize;
		}
	}

	@Override
	public ReturnStatus getDepartment() {
		try {

			List<Department> lstDepartment;

			lstDepartment = tranJdbcTemplate.query(readyCallsQuery.getDepartment(), new DepartmentCallsRowMapper());

			return new ReturnStatus(true, lstDepartment);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class DepartmentCallsRowMapper implements RowMapper<Department> {
		@Override
		public Department mapRow(ResultSet rs, int rowNum) throws SQLException {

			Department dept = new Department();

			dept.setdNo(rs.getInt("dNO"));
			dept.setDepartment(rs.getString("department"));

			return dept;
		}
	}

	@Override
	public ReturnStatus getModule() {
		try {

			List<Module> lstModule;

			lstModule = tranJdbcTemplate.query(readyCallsQuery.getModule(), new ModuleCallsRowMapper());

			return new ReturnStatus(true, lstModule);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class ModuleCallsRowMapper implements RowMapper<Module> {
		@Override
		public Module mapRow(ResultSet rs, int rowNum) throws SQLException {

			Module module = new Module();

			module.setModuleId(rs.getInt("moduleid"));
			module.setModuleName(rs.getString("modulename"));

			return module;
		}
	}

	@Override
	public ReturnStatus getReadyCalls(String strCallFromDate, String strCallToDate, String strReadyFromDate,
			String strReadyToDate, int strCmbDeveloper, int strCmbClient, int strCmbDeptAuthorize, int strCmbDepartment,
			int strCmbModule, String strOrderby, String Callno) {
		try {

			List<ReadyCalls> lstReadyCalls;
			List<Object> params = new ArrayList<>();

			if (strCmbDeveloper != 0) {
				params.add(strCmbDeveloper);
			}
			if (strCmbClient != 0) {
				params.add(strCmbClient);
			}
			if (strCmbDeptAuthorize != 0) {
				params.add(strCmbDeptAuthorize);
			}
			if (strCmbDepartment != 0) {
				params.add(strCmbDepartment);
			}
			if (strCmbModule != 0) {
				params.add(strCmbModule);
			}
			//			if (!strOrderby.equals("")) {
			//				params.add(strOrderby);
			//			}
			//			if (!Callno.equals("")) {
			//				params.add(Callno);
			//			}

			String sqlQuery = readyCallsQuery.getReadyCalls(strCmbDeveloper, strCmbClient, strCmbDeptAuthorize,
					strCmbDepartment, strCmbModule, strOrderby, Callno, strCallFromDate, strCallToDate,
					strReadyFromDate, strReadyToDate);

			lstReadyCalls = tranJdbcTemplate.query(sqlQuery, new ReadyCallsCallsRowMapper(), params.toArray());

			return new ReturnStatus(true, lstReadyCalls);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class ReadyCallsCallsRowMapper implements RowMapper<ReadyCalls> {
		@Override
		public ReadyCalls mapRow(ResultSet rs, int rowNum) throws SQLException {

			ReadyCalls readyCalls = new ReadyCalls();

			readyCalls.setCallno(rs.getInt("callNo"));
			readyCalls.setCdate(rs.getString("CDate"));
			readyCalls.setRDate(rs.getString("RDate"));
			readyCalls.setReadyByName(rs.getString("ReadyBy"));
			readyCalls.setCustName(rs.getString("custname"));
			readyCalls.setRecdby(rs.getString("RecdBy"));
			readyCalls.setAuthorized(rs.getString("Authorised"));
			readyCalls.setDepartment(rs.getString("Department"));
			readyCalls.setModuleName(rs.getString("ModuleName"));
			readyCalls.setDescription(rs.getString("description"));
			readyCalls.setReadydescription(rs.getString("ReadyDescription"));
			readyCalls.setQcOperator(rs.getInt("QcOper"));

			return readyCalls;
		}
	}

	@Override
	public ReturnStatus getCallImages(String callNo) {

		CallsImages lstCallsImages;

		try {

			lstCallsImages = tranJdbcTemplate.queryForObject(readyCallsQuery.getCallsImages(callNo),
					new callsImageRowMapper(), new Object[] { callNo });

			return new ReturnStatus(true, lstCallsImages);

		} catch (EmptyResultDataAccessException e) {
			throw new JilabaException("Image Not Found");
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new JilabaException("Multiple Images Found");
		} catch (Exception e) {

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
	public ReturnStatus updateReadyCalls(int callNo, String completedDesc, String moduleId) {
		try {

			tranJdbcTemplate.update(readyCallsQuery.updateCompletedCalls(),
					new Object[] { moduleId, completedDesc, callNo });

			tranJdbcTemplate.update(readyCallsQuery.updateReadyMarkTable(), new Object[] { callNo });

			return new ReturnStatus(true);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));

		}

	}

	@Override
	public ReturnStatus updateReturnCalls(int callNo, String returnDesc) {
		try {

			tranJdbcTemplate.update(readyCallsQuery.updateReturnCalls(), new Object[] { callNo });
			tranJdbcTemplate.update(readyCallsQuery.updateReturnReadyMark(), new Object[] { callNo });
			return new ReturnStatus(true);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));

		}
	}

	@Override
	public ReturnStatus saveReadyUnmark(int cNo, String readyDesc, String moduleid) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(tranJdbcTemplate);
		simpleJdbcCall.setProcedureName("Sp_ReturnCallSave");

		try {

			Map<String, Object> mapCallSave = new HashMap<String, Object>();

			mapCallSave.put("callno", cNo);
			mapCallSave.put("Description", readyDesc);
			mapCallSave.put("module", 0);
			mapCallSave.put("unmarkoper", FrmLogin.OperCode);
			mapCallSave.put("opercode", FrmLogin.OperCode);
			mapCallSave.put("updated", now);
			mapCallSave.put("uptime", now);
			mapCallSave.put("cancel", "");
			mapCallSave.put("RDate", now);
			mapCallSave.put("readyby", FrmLogin.OperCode);
			mapCallSave.put("Version", "");
			mapCallSave.put("readysno", "");

			simpleJdbcCall.execute(mapCallSave);

			return new ReturnStatus(true);

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class ReturnCallsRowMapper implements RowMapper<ReturnCalls> {
		@Override
		public ReturnCalls mapRow(ResultSet rs, int rowNum) throws SQLException {

			ReturnCalls returnCalls = new ReturnCalls();

			returnCalls.setCallno(rs.getInt("Callno"));
			returnCalls.setDescription(rs.getString("Description"));
			returnCalls.setModule(rs.getInt("module"));
			returnCalls.setUnmarkoper(rs.getInt("unmarkoper"));
			returnCalls.setOpercode(rs.getInt("Opercode"));
			returnCalls.setUpdated(rs.getDate("updated"));
			returnCalls.setUptime(rs.getDate("uptime"));
			returnCalls.setCancel(rs.getString("Cancel"));
			returnCalls.setRDate(rs.getDate("RDate"));
			returnCalls.setReadyby(rs.getInt("Readyby"));
			returnCalls.setVersion(rs.getString("Version"));
			returnCalls.setReadysno(rs.getInt("readysno"));

			return returnCalls;

		}
	}

	@Override
	public ReturnStatus updateReadyCancel(int callNo) {
		try {

			tranJdbcTemplate.update(readyCallsQuery.updateReadyCancel(), new Object[] { callNo });
			return new ReturnStatus(true);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));

		}
	}

	@Override
	public ReturnStatus updateReadyProgress(String callNo) {

		try {

			tranJdbcTemplate.update(readyCallsQuery.updateReadyProgress(callNo));

			return new ReturnStatus(true);
		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	@Override
	public ReturnStatus validateProgressCall() {

		String query = readyCallsQuery.validateProgressCall();

		try {

			List<ReadyCalls> progress;

			progress = tranJdbcTemplate.query(query, new validateProgressCall());

			return new ReturnStatus(true, progress);
		} catch (EmptyResultDataAccessException e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class validateProgressCall implements RowMapper<ReadyCalls> {

		@Override
		public ReadyCalls mapRow(ResultSet rs, int rowNum) throws SQLException {

			ReadyCalls readycalls = new ReadyCalls();

			readycalls.setQcOperator(rs.getInt("QcOper"));

			return readycalls;
		}

	}

	@Override
	public int beforeProgressValidate() {

		try {
			Integer progress;

			progress = tranJdbcTemplate.queryForObject(readyCallsQuery.beforeProgressValidate(), Integer.class);

			return (progress != null) ? progress : 0;
		} catch (EmptyResultDataAccessException e) {

			return 0;
		}

	}

}
