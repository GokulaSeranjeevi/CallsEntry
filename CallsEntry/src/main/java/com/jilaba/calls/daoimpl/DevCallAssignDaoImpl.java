package com.jilaba.calls.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jilaba.calls.dao.DevCallAssign;
import com.jilaba.calls.model.Calls;
import com.jilaba.calls.model.CustStaff;
import com.jilaba.calls.model.Customer;
import com.jilaba.calls.model.Department;
import com.jilaba.calls.model.Module;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.query.DevCallAssignQuery;
import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;

@Component
@Scope("prototype")
public class DevCallAssignDaoImpl implements DevCallAssign {

	@Autowired
	private JdbcTemplate tranjdbcTemplate;

	@Autowired
	private DevCallAssignQuery devCallAssignQuery;

	@Override
	public ReturnStatus getDevCallAssign() {

		List<Operator> lstOperator;

		try {

			lstOperator = tranjdbcTemplate.query(devCallAssignQuery.getDevCoOrd(), new cmbDevCoOrdRowMapper());

			return new ReturnStatus(true, lstOperator);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));

		}

	}

	class cmbDevCoOrdRowMapper implements RowMapper<Operator> {

		@Override
		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {

			Operator devCoOrd = new Operator();

			devCoOrd.setStaffname(rs.getString("StaffName"));
			devCoOrd.setStaffid(rs.getInt("Staffid"));
			devCoOrd.setActive(rs.getString("Active"));
			devCoOrd.setPwd(rs.getString("Pwd"));

			return devCoOrd;
		}

	}

	@Override
	public ReturnStatus getCustomer() {

		List<Customer> lstCustomer;

		try {

			lstCustomer = tranjdbcTemplate.query(devCallAssignQuery.getCustomer(), new CustomerRowMapper());

			return new ReturnStatus(true, lstCustomer);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class CustomerRowMapper implements RowMapper<Customer> {
		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

			Customer customer = new Customer();

			customer.setcustName(rs.getString("custName"));
			customer.setCustId(rs.getInt("CustId"));

			return customer;
		}
	}

	@Override
	public ReturnStatus getCustCoOrd() {

		List<CustStaff> lstCustCoOOrd;

		try {

			lstCustCoOOrd = tranjdbcTemplate.query(devCallAssignQuery.getCustCoOrd(), new CustCoOrdRowMapper());

			return new ReturnStatus(true, lstCustCoOOrd);
		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class CustCoOrdRowMapper implements RowMapper<CustStaff> {
		@Override
		public CustStaff mapRow(ResultSet rs, int rowNum) throws SQLException {

			CustStaff custCoOrd = new CustStaff();

			custCoOrd.setCustStaffName(rs.getString("custStaffName"));
			custCoOrd.setCustStaffId(rs.getString("custStaffid"));
//			custCoOrd.setActive(rs.getString("Active"));

			return custCoOrd;
		}
	}

	@Override
	public ReturnStatus getDepartment() {

		List<Department> lstDept;
		try {

			lstDept = tranjdbcTemplate.query(devCallAssignQuery.getDepartment(), new DepartmentRowMapper());

			return new ReturnStatus(true, lstDept);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class DepartmentRowMapper implements RowMapper<Department> {
		@Override
		public Department mapRow(ResultSet rs, int rowNum) throws SQLException {

			Department dept = new Department();

			dept.setDepartment(rs.getString("Department"));
			dept.setdNo(rs.getInt("dNo"));

			return dept;
		}
	}

	@Override
	public ReturnStatus getDeptAuthorize() {

		List<Operator> lstdeptAuthorize;
		try {

			lstdeptAuthorize = tranjdbcTemplate.query(devCallAssignQuery.getDeptAuthorize(), new DeptAuthorize());

			return new ReturnStatus(true, lstdeptAuthorize);
		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class DeptAuthorize implements RowMapper<Operator> {
		@Override
		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {

			Operator deptAuthorize = new Operator();

			deptAuthorize.setStaffname(rs.getString("StaffName"));
			deptAuthorize.setStaffid(rs.getInt("Staffid"));
			deptAuthorize.setActive(rs.getString("Active"));

			return deptAuthorize;
		}
	}

	@Override
	public ReturnStatus getRecvFrom() {

		List<Operator> lstRecvFrom;
		try {

			lstRecvFrom = tranjdbcTemplate.query(devCallAssignQuery.getRecvFrom(), new RecvFrom());

			return new ReturnStatus(true, lstRecvFrom);
		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class RecvFrom implements RowMapper<Operator> {
		@Override
		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {

			Operator recvFrom = new Operator();

			recvFrom.setStaffname(rs.getString("StaffName"));
			recvFrom.setStaffid(rs.getInt("Staffid"));
			recvFrom.setActive(rs.getString("Active"));

			return recvFrom;
		}
	}

	@Override
	public ReturnStatus getModule(Integer dept) {

		List<Module> lstModule;
		try {

			List<Object> params = new ArrayList<Object>();

			lstModule = tranjdbcTemplate.query(devCallAssignQuery.getModule(dept), new ModuleRowMapper());

			return new ReturnStatus(true, lstModule);
		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class ModuleRowMapper implements RowMapper<Module> {
		@Override
		public Module mapRow(ResultSet rs, int rowNum) throws SQLException {

			Module module = new Module();

			module.setModuleName(rs.getString("ModuleName"));
			module.setModuleId(rs.getInt("ModuleId"));

			return module;
		}
	}

	@Override
	public ReturnStatus getdeveloperCalls() {

		List<Calls> lstDevCalls;
		try {

			lstDevCalls = tranjdbcTemplate.query(devCallAssignQuery.getDevCalls(), new DevCallsRowMapper());

			return new ReturnStatus(true, lstDevCalls);
		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class DevCallsRowMapper implements RowMapper<Calls> {
		@Override
		public Calls mapRow(ResultSet rs, int rowNum) throws SQLException {

			Calls devCalls = new Calls();

			devCalls.setDeveloperName(rs.getString("DeveloperName"));
			devCalls.setDevCalls(rs.getInt("DevCalls"));

			return devCalls;
		}
	}

	/*
	 * @Override public ReturnStatus getCalls(List<Object> devCoOrd, int callNo,
	 * List<Object> customer, List<Object> custCoOrd, List<Object> cmbDept,
	 * List<Object> cmbDeptAuthorize, List<Object> cmbRecvFrom, List<Object>
	 * cmbModule) {
	 * 
	 * List<Calls> lstCalls; try {
	 * 
	 * List<Object> params = new ArrayList<Object>(); if
	 * (String.valueOf(callNo).isEmpty()) { params.addAll(devCoOrd);
	 * params.addAll(customer); params.addAll(cmbDept); params.addAll(cmbRecvFrom);
	 * params.addAll(cmbModule); } else { params.add(devCoOrd);
	 * params.add(customer); params.addAll(cmbDept); params.add(cmbRecvFrom);
	 * params.addAll(cmbModule); params.add(callNo); } // lstCalls =
	 * tranjdbcTemplate.query(devCallAssignQuery.getCalls(callNo, cmbDept.size()),
	 * new CallsRowMapper(), // params);
	 * 
	 * lstCalls =
	 * tranjdbcTemplate.query(devCallAssignQuery.getCalls(devCoOrd.size(), callNo,
	 * customer.size(), cmbDept.size(), cmbRecvFrom.size(), cmbModule.size()), new
	 * CallsRowMapper(), params.toArray());
	 * 
	 * return new ReturnStatus(true, lstCalls); } catch ( Exception e) {
	 * 
	 * return new ReturnStatus(false, ErrorHandling.handleError(e)); }
	 * 
	 * }
	 */
	@Override
	public ReturnStatus getCalls(int devCoOrd, long callNo, int strCustomer, int strCustCoOrd, int strDepartment,
			int strDeptAuthorize, int strRecvFrom, int strModule, int strCallNature) {
		List<Calls> lstCalls;
		try {
			List<Object> params = new ArrayList<>();

			if (devCoOrd != 0) {
				params.add(devCoOrd);
			}
			if (callNo != 0) {
				params.add(callNo);
			}
			if (strCustomer != 0) {
				params.add(strCustomer);
			}
			if (strDepartment != 0) {
				params.add(strDepartment);
			}
			if (strRecvFrom != 0) {
				params.add(strRecvFrom);
			}
			if (strModule != 0) {
				params.add(strModule);
			}

			// Ensure the correct number of parameters are passed
			System.out.println("Params: " + params);

			// Construct query
			String sqlQuery = devCallAssignQuery.getCalls(devCoOrd, callNo, strCustomer, strDepartment, strRecvFrom,
					strModule, strCallNature);

			lstCalls = tranjdbcTemplate.query(sqlQuery, new CallsRowMapper(), params.toArray());

			return new ReturnStatus(true, lstCalls);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class CallsRowMapper implements RowMapper<Calls> {
		@Override
		public Calls mapRow(ResultSet rs, int rowNum) throws SQLException {

			Calls calls = new Calls();

			calls.setCallno(rs.getInt("Callno"));
			calls.setCdate(rs.getString("Cdate"));
			calls.setDescription(rs.getString("Description"));
			calls.setCallcoordinator(rs.getString("Callcoordinator"));
			calls.setCustcordinator_name(rs.getString("Custcordinator_name"));
			calls.setMoption(rs.getString("Moption"));
			calls.setTicketno(rs.getString("Ticketno"));

			Customer customer = new Customer();

			customer.setcustName(rs.getString("custName"));
			calls.setLstcustCustomer(Arrays.asList(customer));

			Module module = new Module();

			module.setModuleName(rs.getString("ModuleName"));
			calls.setLstModule(Arrays.asList(module));
			calls.setImgAttach(rs.getString("imgAttach"));
			calls.setCallnature(rs.getString("Callnature"));
			calls.setReceiverName(rs.getString("ReceiverName"));

			return calls;
		}

	}

	@Override
	public ReturnStatus getDeveloper(boolean diaTask) {
		List<Operator> lstDeveloper;
		try {

			lstDeveloper = tranjdbcTemplate.query(devCallAssignQuery.getDeveloper(diaTask), new DeveloperRowMapper());

			return new ReturnStatus(true, lstDeveloper);
		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class DeveloperRowMapper implements RowMapper<Operator> {
		@Override
		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {

			Operator developer = new Operator();

			developer.setStaffname(rs.getString("StaffName"));
			developer.setStaffid(rs.getInt("Staffid"));

			return developer;

		}
	}

	@Override
	public ReturnStatus devCallUpdate(Object cmbExplanation, Object cmbSugTo, String txtDevHrs, String assnDate,
			int callNo, boolean diaTask) {

		try {
			tranjdbcTemplate.update(
					devCallAssignQuery.devCallUpdate(cmbExplanation, cmbSugTo, txtDevHrs, assnDate, callNo, diaTask),
					new Object[] {});

			return new ReturnStatus(true);
		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

}
