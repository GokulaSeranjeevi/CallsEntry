package com.jilaba.calls.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jilaba.calls.dao.OperatorDao;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.query.OperatorQuery;
import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;

@Component
@Scope("prototype")
public class OperatorDaoImpl implements OperatorDao {

	// private Applicationmain app;
	@Autowired
	private OperatorQuery operatorQuery;

	@Autowired
	private JdbcTemplate tranJdbcTemplate;

	@Override
	public ReturnStatus getOperator() {

		List<Operator> lstOperator;

		try {
			lstOperator = tranJdbcTemplate.query(operatorQuery.getOperator(), new OperatorRowMapper());

			return new ReturnStatus(true, lstOperator);

		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class OperatorRowMapper implements RowMapper<Operator> {

		@Override
		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {

			Operator operator = new Operator();

			operator.setStaffid(rs.getInt("Staffid"));
			operator.setStaffname(rs.getString("StaffName"));
			operator.setPwd(rs.getString("pwd"));
			operator.setActive(rs.getString("Active"));
			operator.setDesignation(rs.getInt("designation"));

			return operator;
		}
	}

	@Override
	public ReturnStatus getOperDetails(Integer staffId) {

		try {

			List<Operator> lstOperDetails;

			lstOperDetails = tranJdbcTemplate.query(operatorQuery.getOperDetails(), new OperatorRowMapper(),
					new Object[] { staffId });

			return new ReturnStatus(true, lstOperDetails);

		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	//	class OperDetailsRowMapper implements RowMapper<Operator> {
	//
	//		@Override
	//		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {
	//
	//			Operator operator = new Operator();
	//
	//			operator.setStaffid(rs.getInt("Staffid"));
	//			operator.setStaffname(rs.getString("StaffName"));
	//			operator.setPwd(rs.getString("pwd"));
	//			operator.setActive(rs.getString("Active"));
	//
	//			return operator;
	//		}
	//	}

}