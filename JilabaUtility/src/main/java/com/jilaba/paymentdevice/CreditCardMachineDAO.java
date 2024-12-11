package com.jilaba.paymentdevice;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;

public class CreditCardMachineDAO {

	private CreditCardMachineQuery query = new CreditCardMachineQuery();
	private JdbcTemplate jdbcTemplate;

	public ReturnStatus getDeviceDetails(int cardCode, int ipid, JdbcTemplate jdbcTemplate) {
		try {

			this.jdbcTemplate = jdbcTemplate;

			CreditCardMachine machine = this.jdbcTemplate.queryForObject(query.getDeviceDetails(),
					new Object[] { cardCode, ipid }, new CardMachineMapper());

			return new ReturnStatus(true, machine);
		} catch (EmptyResultDataAccessException e) {
			return new ReturnStatus(true);
			/*return new ReturnStatus(false,
					"CreditCardMachine Details not found for CardCode: " + cardCode + " And IpId: " + ipid);*/
		} catch (IncorrectResultSizeDataAccessException e) {
			return new ReturnStatus(true);
			/*return new ReturnStatus(false,
					"Duplicate CreditCardMachine Details found for CardCode: " + cardCode + " And IpId: " + ipid);*/
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	public ReturnStatus getCardDetailsByMachineId(String machineId, JdbcTemplate jdbcTemplate) {
		try {

			this.jdbcTemplate = jdbcTemplate;

			CreditCardMachine machine = this.jdbcTemplate.queryForObject(query.getCardDetails(),
					new Object[] { machineId }, new CreditCardMapper());

			return new ReturnStatus(true, machine);
		} catch (EmptyResultDataAccessException e) {
			return new ReturnStatus(false, "Account Details not found for MachineId: " + machineId);
		} catch (IncorrectResultSizeDataAccessException e) {
			return new ReturnStatus(false, "Duplicate Account Details found for MachineId: " + machineId);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	public NumberControl getNumberControl(String ctrlcode) {
		try {

			jdbcTemplate.update(query.updateNumberControl(), ctrlcode);

			return jdbcTemplate.queryForObject(query.getNumberControl(), new Object[] { ctrlcode },
					new NumberControlRowMapper());

		} catch (EmptyResultDataAccessException e) {
			throw new JilabaException("Control Code Not Found [" + ctrlcode + "]");
		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	public class CardMachineMapper implements RowMapper<CreditCardMachine> {

		@Override
		public CreditCardMachine mapRow(ResultSet rs, int rowNum) throws SQLException {
			CreditCardMachine machine = new CreditCardMachine();
			machine.setCode(rs.getInt("Code"));
			machine.setiPID(rs.getInt("IPID"));
			machine.setiMEINo(rs.getString("IMEINo"));
			machine.setDeviceId(rs.getInt("DeviceId"));
			machine.setMerchantId(rs.getString("MerchantId"));
			machine.setCardCode(rs.getInt("CardCode"));
			machine.setSecurityKey(rs.getString("SecurityKey"));
			machine.setMerchantPOSCode(rs.getString("MerchantPOSCode"));
			machine.setMachineCode(rs.getInt("MachineCode"));
			machine.setDeviceName(rs.getString("DeviceName"));
			machine.setPayURL(rs.getString("PayURL"));
			machine.setStatusURL(rs.getString("StatusURL"));
			machine.setCancelURL(rs.getString("CancelURL"));
			machine.setAutoCancelDurationInMinutes(rs.getInt("AutoCancelDurationInMinutes"));
			machine.setMachineID(rs.getString("MachineID"));
			return machine;
		}
	}

	public class CreditCardMapper implements RowMapper<CreditCardMachine> {

		@Override
		public CreditCardMachine mapRow(ResultSet rs, int rowNum) throws SQLException {
			CreditCardMachine machine = new CreditCardMachine();
			machine.setCardCode(rs.getInt("CardCode"));
			machine.setCardName(rs.getString("CardName"));
			machine.setAcctCode(rs.getString("AcctCode"));
			machine.setMachineID(rs.getString("MachineID"));
			return machine;
		}
	}

	public class NumberControlRowMapper implements RowMapper<NumberControl> {
		@Override
		public NumberControl mapRow(ResultSet rs, int rowNum) throws SQLException {
			NumberControl numbercontrol = new NumberControl();
			numbercontrol.setCtrlCode(rs.getString("ctrl_code"));
			numbercontrol.setCtrlLong(rs.getInt("ctrl_long"));
			numbercontrol.setCtrlDescription(rs.getString("ctrl_description"));
			numbercontrol.setTypeCode(rs.getString("typecode"));
			numbercontrol.setTypeName(rs.getString("typename"));
			numbercontrol.setBaseType(rs.getString("basetype"));
			return numbercontrol;
		}
	}

}
