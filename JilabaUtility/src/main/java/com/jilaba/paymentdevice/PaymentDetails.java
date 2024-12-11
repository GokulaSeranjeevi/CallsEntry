package com.jilaba.paymentdevice;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;

import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.paymentdevice.CF.PaymentStatus;

public class PaymentDetails {

	public static final String WEBTRANNO = "WEBTRANNO";

	private int tranNo = 0;
	private String tranDate = null;
	private String refUniqueKey = "";
	private String referenceKey = "";
	private String paymentStatus = "";
	private String responseCode = "";
	private String lastResponseAt = null;
	private String orgCompanyCode = "";
	private String companyCode = "";
	private String autoCancelled = "N";
	private double amount = 0;
	private int cardCode = 0;
	private int IPID = 0;
	private int payMode = 0;
	private String createdDate = null;
	private String createdTime = null;
	private String TID = "";
	private int sequenceNo = 1;
	private int moduleId = 0;

	private JdbcTemplate jdbcTemplate;
	private CreditCardMachineDAO cardMachineDAO;
	private ReturnStatus returnStatus;
	private CreditCardMachine cardMachine;

	public PaymentDetails(int cardCode, Double amount, int IPID, String orgCompCode, String compCode, int payMode,
			JdbcTemplate jdbcTemplate) {
		this.cardCode = cardCode;
		this.amount = amount * 100;
		this.IPID = IPID;
		this.orgCompanyCode = orgCompCode;
		this.companyCode = compCode;
		this.payMode = payMode;
		this.jdbcTemplate = jdbcTemplate;

		getDeviceDetails(jdbcTemplate);
	}

	private void getDeviceDetails(JdbcTemplate jdbcTemplate) {

		cardMachineDAO = new CreditCardMachineDAO();
		returnStatus = cardMachineDAO.getDeviceDetails(this.cardCode, this.IPID, jdbcTemplate);
		CF.statusCheck(returnStatus);

		cardMachine = (CreditCardMachine) returnStatus.getReturnObject();
	}

	public void preparePaymentDetails() {

		returnStatus = generateTranNo();
		CF.statusCheck(returnStatus);

		tranNo = (int) returnStatus.getReturnObject();
		tranDate = LocalDate.now().format(CF.dateFormat);
		lastResponseAt = LocalDateTime.now().format(CF.dateTimeFormat);
		createdDate = LocalDateTime.now().format(CF.dateFormat);
		createdTime = LocalDateTime.now().format(CF.dateTimeFormat);
		paymentStatus = PaymentStatus.INITIATE;
	}

	private ReturnStatus generateTranNo() {
		try {

			NumberControl numberControl = cardMachineDAO.getNumberControl(WEBTRANNO);
			int tranNo = numberControl.getCtrlLong();

			return new ReturnStatus(true, tranNo);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	public int getTranNo() {
		return tranNo;
	}

	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getRefUniqueKey() {
		return refUniqueKey;
	}

	public void setRefUniqueKey(String refUniqueKey) {
		this.refUniqueKey = refUniqueKey;
	}

	public String getReferenceKey() {
		return referenceKey;
	}

	public void setReferenceKey(String referenceKey) {
		this.referenceKey = referenceKey;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getLastResponseAt() {
		return lastResponseAt;
	}

	public void setLastResponseAt(String lastResponseAt) {
		this.lastResponseAt = lastResponseAt;
	}

	public String getOrgCompanyCode() {
		return orgCompanyCode;
	}

	public void setOrgCompanyCode(String orgCompanyCode) {
		this.orgCompanyCode = orgCompanyCode;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public CreditCardMachine getSwipingMachine() {
		return cardMachine;
	}

	public void setSwipingMachine(CreditCardMachine swipingMachine) {
		this.cardMachine = swipingMachine;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getCardCode() {
		return cardCode;
	}

	public void setCardCode(int cardCode) {
		this.cardCode = cardCode;
	}

	public int getIPID() {
		return IPID;
	}

	public void setIPID(int iPID) {
		IPID = iPID;
	}

	public int getPayMode() {
		return payMode;
	}

	public void setPayMode(int payMode) {
		this.payMode = payMode;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public String getAutoCancelled() {
		return autoCancelled;
	}

	public void setAutoCancelled(String autoCancelled) {
		this.autoCancelled = autoCancelled;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getTID() {
		return TID;
	}

	public void setTID(String tID) {
		TID = tID;
	}

	public int getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

}

class NumberControl {

	private String ctrlCode = "";
	private int ctrlLong = 0;
	private String ctrlDescription = "";
	private String typeCode = "";
	private String typeName = "";
	private String baseType = "";

	public String getCtrlCode() {
		return ctrlCode;
	}

	public void setCtrlCode(String ctrlCode) {
		this.ctrlCode = ctrlCode;
	}

	public int getCtrlLong() {
		return ctrlLong;
	}

	public void setCtrlLong(int ctrlLong) {
		this.ctrlLong = ctrlLong;
	}

	public String getCtrlDescription() {
		return ctrlDescription;
	}

	public void setCtrlDescription(String ctrlDescription) {
		this.ctrlDescription = ctrlDescription;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getBaseType() {
		return baseType;
	}

	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}

}