package com.jilaba.paymentdevice;

public class CreditCardMachine {

	// creditcardmachine properties
	private int cardCode = 0;
	private String iMEINo = "";
	private int deviceId = 0;
	private String merchantId = "";
	private String securityKey = "";
	private String merchantPOSCode = "";
	private int machineCode = 0;
	private String machineID = "";
	// creditcardmachineassign properties
	private int code = 0;
	private int iPID = 0;
	// fn_creditcardmachinedetails properties
	private String deviceName = "";
	private String payURL = "";
	private String statusURL = "";
	private String cancelURL = "";
	private int autoCancelDurationInMinutes = 0;

	private String cardName = "";
	private String acctCode = "";

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public int getCardCode() {
		return cardCode;
	}

	public void setCardCode(int cardCode) {
		this.cardCode = cardCode;
	}

	public String getiMEINo() {
		return iMEINo;
	}

	public void setiMEINo(String iMEINo) {
		this.iMEINo = iMEINo;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}

	public String getMerchantPOSCode() {
		return merchantPOSCode;
	}

	public void setMerchantPOSCode(String merchantPOSCode) {
		this.merchantPOSCode = merchantPOSCode;
	}

	public int getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(int machineCode) {
		this.machineCode = machineCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getiPID() {
		return iPID;
	}

	public void setiPID(int iPID) {
		this.iPID = iPID;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getPayURL() {
		return payURL;
	}

	public void setPayURL(String payURL) {
		this.payURL = payURL;
	}

	public String getStatusURL() {
		return statusURL;
	}

	public void setStatusURL(String statusURL) {
		this.statusURL = statusURL;
	}

	public String getCancelURL() {
		return cancelURL;
	}

	public void setCancelURL(String cancelURL) {
		this.cancelURL = cancelURL;
	}

	public int getAutoCancelDurationInMinutes() {
		return autoCancelDurationInMinutes;
	}

	public void setAutoCancelDurationInMinutes(int autoCancelDurationInMinutes) {
		this.autoCancelDurationInMinutes = autoCancelDurationInMinutes;
	}

	public String getMachineID() {
		return machineID;
	}

	public void setMachineID(String machineID) {
		this.machineID = machineID;
	}

}
