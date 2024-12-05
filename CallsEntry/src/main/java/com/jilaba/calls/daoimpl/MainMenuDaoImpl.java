package com.jilaba.calls.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jilaba.calls.dao.MainManuDao;
import com.jilaba.calls.model.Calls;
import com.jilaba.calls.query.MainMenuQuery;
import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;

@Component
@Scope("prototype")
public class MainMenuDaoImpl implements MainManuDao {

	@Autowired
	private MainMenuQuery mainMenuQuery;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public ReturnStatus loadPendingCalls() {

		try {

			List<Calls> lstCalls;

			lstCalls = jdbcTemplate.query(mainMenuQuery.loadPendingCalls(), new PendingCallsRowMapper());

			return new ReturnStatus(true, lstCalls);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class PendingCallsRowMapper implements RowMapper<Calls> {

		@Override
		public Calls mapRow(ResultSet rs, int rowNum) throws SQLException {

			Calls calls = new Calls();

			calls.setMnudescription(rs.getString("Description"));
			calls.setMnuNoOfCalls(rs.getInt("NoOfCalls"));

			return calls;

		}

	}

	@Override
	public ReturnStatus loadTodayCalls() {
		try {

			List<Calls> lstTodayCalls;

			lstTodayCalls = jdbcTemplate.query(mainMenuQuery.loadTodayCalls(), new TodayCallsRowMapper());

			return new ReturnStatus(true, lstTodayCalls);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class TodayCallsRowMapper implements RowMapper<Calls> {

		@Override
		public Calls mapRow(ResultSet rs, int rowNum) throws SQLException {

			Calls calls = new Calls();

			calls.setMnudescription(rs.getString("Description"));
			calls.setMnuNoOfCalls(rs.getInt("NoOfCalls"));

			return calls;

		}

	}

	@Override
	public ReturnStatus loadModuleCalls() {

		try {

			List<Calls> lstModuleCalls;

			lstModuleCalls = jdbcTemplate.query(mainMenuQuery.loadModuleCalls(), new ModuleCallsRowMapper());

			return new ReturnStatus(true, lstModuleCalls);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class ModuleCallsRowMapper implements RowMapper<Calls> {

		@Override
		public Calls mapRow(ResultSet rs, int rowNum) throws SQLException {

			Calls calls = new Calls();

			calls.setMnudescription(rs.getString("Description"));
			calls.setMnuNoOfCalls(rs.getInt("NoOfCalls"));

			return calls;

		}

	}

}
