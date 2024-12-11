package com.jilaba.paymentdevice;

public class PineLabProperties {

	private int TransactionNumber = 0;
	private int SequenceNumber = 1;		// like EntryOrder
	private int AllowedPaymentMode = 1;  // Card
	private String MerchantStorePosCode = "";
	private double Amount = 0;
	private String UserID = "";
	private String MerchantID = "";
	private String SecurityToken = "";
	private String IMEI = "";
	private int AutoCancelDurationInMinutes = 5;// Default From PineLabs
	private String payUrl = "";
	private String PlutusTransactionReferenceID = "";

	public String getPlutusTransactionReferenceID() {
		return PlutusTransactionReferenceID;
	}

	public void setPlutusTransactionReferenceID(String plutusTransactionReferenceID) {
		PlutusTransactionReferenceID = plutusTransactionReferenceID;
	}

	public int getTransactionNumber() {
		return TransactionNumber;
	}

	public void setTransactionNumber(int transactionNumber) {
		TransactionNumber = transactionNumber;
	}

	public int getSequenceNumber() {
		return SequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		SequenceNumber = sequenceNumber;
	}

	public int getAllowedPaymentMode() {
		return AllowedPaymentMode;
	}

	public void setAllowedPaymentMode(int allowedPaymentMode) {
		AllowedPaymentMode = allowedPaymentMode;
	}

	public String getMerchantStorePosCode() {
		return MerchantStorePosCode;
	}

	public void setMerchantStorePosCode(String merchantStorePosCode) {
		MerchantStorePosCode = merchantStorePosCode;
	}

	public double getAmount() {
		return Amount;
	}

	public void setAmount(double amount) {
		Amount = amount;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getMerchantID() {
		return MerchantID;
	}

	public void setMerchantID(String merchantID) {
		MerchantID = merchantID;
	}

	public String getSecurityToken() {
		return SecurityToken;
	}

	public void setSecurityToken(String securityToken) {
		SecurityToken = securityToken;
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public int getAutoCancelDurationInMinutes() {
		return AutoCancelDurationInMinutes;
	}

	public void setAutoCancelDurationInMinutes(int autoCancelDurationInMinutes) {
		AutoCancelDurationInMinutes = autoCancelDurationInMinutes;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

}
