package com.jilaba.paymentdevice;

import org.springframework.jdbc.core.JdbcTemplate;

import com.jilaba.common.ReturnStatus;
import com.jilaba.paymentdevice.CF.CreditCardMachineName;

public interface CreditCardMachineImpl {

	public ReturnStatus makePayment();

	public void checkPaymentStatus(Object obj);

	public ReturnStatus checkPaymentStatus(int cardCode, String referenceKey, int ipId, JdbcTemplate jdbcTemplate);

	public ReturnStatus cancelPayment();

	public CreditCardMachineName getDeviceName();
}
