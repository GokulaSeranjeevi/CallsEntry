package com.jilaba.calls.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jilaba.calls.dao.CallsCompletedDao;
import com.jilaba.calls.daoimpl.ReadyCallsDaoImpl.ReadyCallsCallsRowMapper;
import com.jilaba.calls.model.CallsCompleted;
import com.jilaba.calls.model.Customer;
import com.jilaba.calls.model.Department;
import com.jilaba.calls.model.Module;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.model.ReadyCalls;
import com.jilaba.calls.query.CallsCompletedQuery;
import com.jilaba.calls.query.ReadyCallsQuery;
import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;

@Component
@Scope("prototype")
public class CallsCompletedDaoImpl implements CallsCompletedDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private CallsCompletedQuery callsCompletedQuery;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
	private LocalDateTime now = LocalDateTime.now();

	@Override
	public ReturnStatus getDeveloper() {

		try {

			List<Operator> lstDeveloper;

			lstDeveloper = jdbcTemplate.query(callsCompletedQuery.getDeveloperCalls(), new DeveloperCallsRowMapper());

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

			lstCustomer = jdbcTemplate.query(callsCompletedQuery.getCustomerCalls(), new CustomerCallsRowMapper());

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

			List<Operator> lstOperator;

			lstOperator = jdbcTemplate.query(callsCompletedQuery.getDeptAuthorize(), new OperatorCallsRowMapper());

			return new ReturnStatus(true, lstOperator);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class OperatorCallsRowMapper implements RowMapper<Operator> {
		@Override
		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {

			Operator deptAuthorize = new Operator();

			deptAuthorize.setStaffid(rs.getInt("staffid"));
			deptAuthorize.setStaffname(rs.getString("staffname"));
			deptAuthorize.setPwd(rs.getString("pwd"));
			deptAuthorize.setActive(rs.getString("Active"));

			return deptAuthorize;
		}
	}

	@Override
	public ReturnStatus getDepartment() {
		try {

			List<Department> lstDepartment;

			lstDepartment = jdbcTemplate.query(callsCompletedQuery.getDepartment(), new DepartmentCallsRowMapper());

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
	public ReturnStatus getModule(Integer dept) {
		try {

			List<Module> lstModule;

			lstModule = jdbcTemplate.query(callsCompletedQuery.getModule(dept), new ModuleCallsRowMapper());

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
	public ReturnStatus getCompletedCalls(String strCallFromDate, String strCallToDate, String strCompletedFromDate,
			String strCompletedToDate, int strCmbDeveloper, int strCmbClient, int strCmbDeptAuthorize,
			int strCmbDepartment, int strCmbModule, String strOrderby, String callNo) {
		try {

			List<CallsCompleted> lstCallsCompleted;
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

			String sqlQuery = callsCompletedQuery.getComletedCalls(strCmbDeveloper, strCmbClient, strCmbDeptAuthorize,
					strCmbDepartment, strCmbModule, strOrderby, callNo, strCallFromDate, strCallToDate,
					strCompletedFromDate, strCompletedToDate);

			lstCallsCompleted = jdbcTemplate.query(sqlQuery, new CompletedCallsCallsRowMapper(), params.toArray());

			return new ReturnStatus(true, lstCallsCompleted);

		} catch (Exception e) {

			e.printStackTrace();

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class CompletedCallsCallsRowMapper implements RowMapper<CallsCompleted> {
		@Override
		public CallsCompleted mapRow(ResultSet rs, int rowNum) throws SQLException {

			CallsCompleted callsCompleted = new CallsCompleted();

			callsCompleted.setCallNo(rs.getInt("callNo"));
			callsCompleted.setCallDate(rs.getString("CallDate"));
			callsCompleted.setTestedDate(rs.getString("TestDate"));
			callsCompleted.setTestedby(rs.getString("Testedby"));
			callsCompleted.setClientName(rs.getString("ClientName"));
			callsCompleted.setRecbyName(rs.getString("RecbyName"));
			callsCompleted.setAuthorized(rs.getString("Authorised"));
			callsCompleted.setDepartment(rs.getString("Department"));
			callsCompleted.setModule(rs.getString("Module"));
			callsCompleted.setCallDescription(rs.getString("CallDescription"));
			callsCompleted.setTestDescription(rs.getString("TestDescription"));

			return callsCompleted;
		}
	}

	@Override
	public ReturnStatus updateDeliveredCalls(int cNo, String completedDesc) {

		try {

			jdbcTemplate.update(callsCompletedQuery.updateDeliveredCalls(), new Object[] { completedDesc, cNo });

			return new ReturnStatus(true);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));

		}

	}

}
