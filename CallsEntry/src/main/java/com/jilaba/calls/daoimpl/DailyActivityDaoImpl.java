package com.jilaba.calls.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
			LocalDate localDate = LocalDate.now();
			LocalDateTime now = LocalDateTime.now();

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(tranJdbcTemplate);
			simpleJdbcCall.setProcedureName("Sp_DailyActivity");

			int groupId = tranJdbcTemplate.queryForObject(dailyActivityQuery.getGroupId(), Integer.class);

			for (DailyActivity dailyActivity : dailyActivities) {
				Map<String, Object> mapDataSave = new HashMap<>();

				mapDataSave.put("GroupId", groupId);
				mapDataSave.put("Staffid", dailyActivity.getStaffId());
				mapDataSave.put("Leave", dailyActivity.getLeave());
				mapDataSave.put("Permission", dailyActivity.getPermission());
				mapDataSave.put("HalfDay", dailyActivity.getHalfDay());
				mapDataSave.put("WeekOff", dailyActivity.getWeekOff());
				mapDataSave.put("ComboOff", dailyActivity.getComboOff());
				mapDataSave.put("Approvedby", dailyActivity.getApprovedby());
				mapDataSave.put("Reason", dailyActivity.getReason());
				mapDataSave.put("Permissiontime", dailyActivity.getPermissionTime()); // Make sure this field exists in
																						// the `DailyActivity` class
				mapDataSave.put("Createddate", localDate);
				mapDataSave.put("Createdtime", formatter.format(now));

				// Execute the stored procedure for each DailyActivity object
				simpleJdbcCall.execute(mapDataSave);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public ReturnStatus getReport(String AtnDate) {
		List<DailyActivity> lstDailyActivities;

		try {
			lstDailyActivities = tranJdbcTemplate.query(dailyActivityQuery.getReport(AtnDate), new ReportRowMapper());

			return new ReturnStatus(true, lstDailyActivities);

		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	class ReportRowMapper implements RowMapper<DailyActivity> {

		@Override
		public DailyActivity mapRow(ResultSet rs, int rowNum) throws SQLException {

			DailyActivity Report = new DailyActivity();

			Report.setStaffName(rs.getString("StaffName"));
			Report.setLeave(rs.getString("Leave"));
			Report.setPermission(rs.getString("Permission"));
			Report.setHalfDay(rs.getString("HalfDay"));
			Report.setWeekOff(rs.getString("WeekOff"));
			Report.setComboOff(rs.getString("ComboOff"));
			Report.setApprovedName(rs.getString("Approvedby"));
			Report.setReason(rs.getString("Reason"));
			Report.setPermissionTime(rs.getString("Permissiontime"));

			return Report;
		}
	}

	@Override
	public List<Map<String, Object>> getStaff() {

		List<Map<String, Object>> lstStaff;

		lstStaff = tranJdbcTemplate.queryForList(dailyActivityQuery.getOperator(), new Object[] {});

		return lstStaff;
	}

}
