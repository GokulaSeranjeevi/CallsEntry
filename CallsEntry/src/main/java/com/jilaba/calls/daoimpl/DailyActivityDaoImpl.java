package com.jilaba.calls.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.jilaba.calls.dao.DailyActivityDao;
import com.jilaba.calls.daoimpl.OperatorDaoImpl.OperatorRowMapper;
import com.jilaba.calls.forms.FrmLogin;
import com.jilaba.calls.model.DailyActivity;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.query.DailyActivityQuery;
import com.jilaba.calls.query.OperatorQuery;
import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;

@Component
public class DailyActivityDaoImpl implements DailyActivityDao {

	@Autowired
	private DailyActivityQuery dailyActivityQuery;

	@Autowired
	private JdbcTemplate tranJdbcTemplate;

	@Override
	public ReturnStatus getOperator() {

		List<Operator> lstOperator;

		try {
			lstOperator = tranJdbcTemplate.query(dailyActivityQuery.getOperator(), new StaffRowMapper());

			return new ReturnStatus(true, lstOperator);

		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class StaffRowMapper implements RowMapper<Operator> {

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
	public void saveDailyActivity(List<DailyActivity> dailyActivities) throws Exception {

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(tranJdbcTemplate);
			simpleJdbcCall.setProcedureName("Sp_DailyActivity");

			int groupId = tranJdbcTemplate.queryForObject(dailyActivityQuery.getGroupId(), Integer.class);

			// Iterate over all DailyActivity objects in the list
			for (DailyActivity dailyActivity : dailyActivities) {
				Map<String, Object> mapDataSave = new HashMap<>();

				// Set parameters for the stored procedure
				mapDataSave.put("GroupId", groupId);
				mapDataSave.put("Staffid", dailyActivity.getStaffId());
				mapDataSave.put("Leave", dailyActivity.getLeave());
				mapDataSave.put("Permission", dailyActivity.getPermission());
				mapDataSave.put("MonthOff", dailyActivity.getMonthOff());
				mapDataSave.put("WeekOff", dailyActivity.getWeekOff());
				mapDataSave.put("ComboOff", dailyActivity.getComboOff());
				mapDataSave.put("Approvedby", dailyActivity.getApprovedby());
				mapDataSave.put("Reason", dailyActivity.getReason());
				mapDataSave.put("Permissiontime", dailyActivity.getPermissionTime()); // Make sure this field exists in
																						// the `DailyActivity` class
				mapDataSave.put("Createddate", formatter.format(now));
				mapDataSave.put("Createdtime", formatter.format(now));

				// Execute the stored procedure for each DailyActivity object
				simpleJdbcCall.execute(mapDataSave);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

}
