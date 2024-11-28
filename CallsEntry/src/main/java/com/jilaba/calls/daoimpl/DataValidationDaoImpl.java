package com.jilaba.calls.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.asn1.dvcs.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.jilaba.calls.dao.DataValidationDao;
import com.jilaba.calls.model.BranchOffice;
import com.jilaba.calls.model.DataValidation;
import com.jilaba.calls.model.DatabaseName;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.query.DataValidationQuery;
import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;

@Component
public class DataValidationDaoImpl implements DataValidationDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataValidationQuery dataValidationQuery;

	@Override
	public ReturnStatus getDatabaseType() {

		try {

			List<DatabaseName> lstDatabaseName;
			lstDatabaseName = jdbcTemplate.query(dataValidationQuery.getDatabaseName(), new DatabaseNameRowMapper());

			return new ReturnStatus(true, lstDatabaseName);

		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class DatabaseNameRowMapper implements RowMapper<DatabaseName> {
		@Override
		public DatabaseName mapRow(ResultSet rs, int rowNum) throws SQLException {

			DatabaseName databaseName = new DatabaseName();

			databaseName.setDatabaseId(rs.getInt("DatabaseId"));
			databaseName.setDatabaseName(rs.getString("DatabaseName"));

			return databaseName;
		}
	}

	@Override
	public ReturnStatus getBranchOffice() {

		try {

			List<BranchOffice> lstBranchOffice;

			lstBranchOffice = jdbcTemplate.query(dataValidationQuery.getBranchOffice(), new BranchOfficeRowMapper());

			return new ReturnStatus(true, lstBranchOffice);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class BranchOfficeRowMapper implements RowMapper<BranchOffice> {
		@Override
		public BranchOffice mapRow(ResultSet rs, int rowNum) throws SQLException {

			BranchOffice branchOffice = new BranchOffice();

			branchOffice.setBranchId(rs.getInt("BranchId"));
			branchOffice.setBranchCode(rs.getString("BranchCode"));
			branchOffice.setBranchName(rs.getString("BranchName"));
			branchOffice.setActive(rs.getString("Active"));

			return branchOffice;
		}
	}

	@Override
	public ReturnStatus getUpdateby() {

		try {

			List<Operator> lstOperator;

			lstOperator = jdbcTemplate.query(dataValidationQuery.getUpdateby(), new UpdatebyRowMapper());

			return new ReturnStatus(true, lstOperator);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class UpdatebyRowMapper implements RowMapper<Operator> {

		@Override
		public Operator mapRow(ResultSet rs, int rowNum) throws SQLException {

			Operator updateby = new Operator();

			updateby.setStaffid(rs.getInt("staffid"));
			updateby.setStaffname(rs.getString("staffName"));

			return updateby;
		}
	}

	@Override
	public void saveDataValidation(DataValidation dataValidation) throws Exception {

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
			simpleJdbcCall.setProcedureName("Sp_DataValidation");

			Map<String, Object> mapDataSave = new HashMap<String, Object>();

			mapDataSave.put("Databaseid", dataValidation.getDatabaseId());
			mapDataSave.put("BranchId", dataValidation.getBranchId());
			mapDataSave.put("UpdateDate", dataValidation.getUpdateDate());
			mapDataSave.put("Updatetime", formatter.format(now));
			mapDataSave.put("QueryDesc", dataValidation.getQueryDesc());
			mapDataSave.put("Updateby", dataValidation.getUpdateby());
			mapDataSave.put("Reason", dataValidation.getReason());

			simpleJdbcCall.execute(mapDataSave);

		} catch (Exception e) {

			throw new Exception(e.getMessage());
		}

	}

	@Override
	public ReturnStatus getData(Object dataId, Object branchId, String Date, String text2) {

		try {

			List<DataValidation> lstDataValidation;

			lstDataValidation = jdbcTemplate.query(dataValidationQuery.getData(dataId, branchId, Date, text2),
					new DataRowMapper());

			return new ReturnStatus(true, lstDataValidation);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	class DataRowMapper implements RowMapper<DataValidation> {
		@Override
		public DataValidation mapRow(ResultSet rs, int rowNum) throws SQLException {

			DataValidation dataValidation = new DataValidation();

			dataValidation.setQueryDesc(rs.getString("QueryDesc"));
			dataValidation.setReason(rs.getString("Reason"));

			return dataValidation;

		}
	}

}
