package com.jilaba.paymentdevice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;
import com.jilaba.paymentdevice.CF.CreditCardMachineName;
import com.jilaba.paymentdevice.CF.ISwipingMachine;
import com.jilaba.paymentdevice.CF.PaymentStatus;
import com.jilaba.paymentdevice.CF.URLStatus;

public class PineLabs implements CreditCardMachineImpl {

	public PineLabProperties pineLabProp;
	private Map<String, Object> mapPayResponse;
	public Map<String, Object> mapResponse;
	public ISwipingMachine iSwipingMachine;
	private ReturnStatus returnStatus;
	private boolean isInterrupted = false;
	private boolean isCancelled = false;
	private boolean isDelayCheckNeed = false;
	private PaymentDetails payDetails;

	public PineLabs() {

	}

	public PineLabs(ISwipingMachine iSwipingMachine, PaymentDetails details, JLabel lblPlutusTranRefId,
			JLabel lblPlutusTranRefIdVal, Thread timerThread) {
		try {

			PineLabProperties properties = new PineLabProperties();
			properties.setPayUrl(details.getSwipingMachine().getPayURL());
			properties.setTransactionNumber(details.getTranNo());
			// properties.setSequenceNumber(1);
			properties.setAllowedPaymentMode(details.getPayMode());
			properties.setMerchantStorePosCode(details.getSwipingMachine().getMerchantPOSCode());
			properties.setAmount(details.getAmount());
			// properties.setUserID("");
			properties.setMerchantID(details.getSwipingMachine().getMerchantId());
			properties.setSecurityToken(details.getSwipingMachine().getSecurityKey());
			properties.setIMEI(details.getSwipingMachine().getiMEINo());
			properties.setAutoCancelDurationInMinutes(details.getSwipingMachine().getAutoCancelDurationInMinutes());

			CF.PAYMENT_URL = details.getSwipingMachine().getPayURL();
			CF.PAY_STATUS_CHECK_URL = details.getSwipingMachine().getStatusURL();
			CF.PAYMENT_CANCEL_URL = details.getSwipingMachine().getCancelURL();

			this.iSwipingMachine = iSwipingMachine;
			pineLabProp = properties;
			payDetails = details;

			returnStatus = this.iSwipingMachine.saveWebTransaction();
			CF.statusCheck(returnStatus);
			int tranNo = (int) returnStatus.getReturnObject();

			pineLabProp.setTransactionNumber(tranNo);

			returnStatus = makePayment();
			CF.statusCheck(returnStatus);

			timerThread.start();

			if (pineLabProp.getPlutusTransactionReferenceID().isEmpty())
				lblPlutusTranRefId.setText("");
			else {
				lblPlutusTranRefIdVal.setText(pineLabProp.getPlutusTransactionReferenceID());
				lblPlutusTranRefId.setVisible(true);
				lblPlutusTranRefIdVal.setVisible(true);
			}
			details.setReferenceKey(String.valueOf(pineLabProp.getPlutusTransactionReferenceID()));

			checkStatusUntilTransactionAutoCancel(details);

		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	private void checkStatusUntilTransactionAutoCancel(PaymentDetails details) {
		try {

			/** Start thread*/
			final Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while (!Thread.currentThread().isInterrupted() && !isInterrupted) {
						try {

							checkPaymentStatus(details);

							if (isDelayCheckNeed)
								Thread.sleep(5000);

						} catch (InterruptedException e) {

						} catch (Exception e) {
							// isInterrupted = true;
							if (!isInterrupted)
								JOptionPane.showMessageDialog(null, ErrorHandling.handleError(e));
						}
					}
				}
			});
			t.start();

			/** Schedule task to terminate thread in AutoCancelDuration*/
			ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
			exec.schedule(new Runnable() {
				@Override
				public void run() {
					iSwipingMachine.apiResponse(mapResponse);
					isInterrupted = true;
					t.interrupt();
				}
			}, pineLabProp.getAutoCancelDurationInMinutes(), TimeUnit.MINUTES);

		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	@Override
	public ReturnStatus makePayment() {
		try {

			Map<String, Object> details = CF.convertToMap(pineLabProp);
			JSONObject requestJson = new JSONObject(details);
			String data = requestJson.toString();

			// connection establishment
			returnStatus = CF.createConnection(URLStatus.MAKE_PAY, data);
			// If Sequence No Same for Same Amount Transaction it will throw Txn Already Completed
			while (null == returnStatus.getReturnObject() && returnStatus.isStatus()
					&& Double.parseDouble(returnStatus.getDescription()) == -1) {

				pineLabProp.setSequenceNumber(pineLabProp.getSequenceNumber() + 1);
				payDetails.setSequenceNo(pineLabProp.getSequenceNumber());
				details = CF.convertToMap(pineLabProp);
				requestJson = new JSONObject(details);
				data = requestJson.toString();

				returnStatus = CF.createConnection(URLStatus.MAKE_PAY, data);
			}
			CF.statusCheck(returnStatus);

			JSONObject responseJson = (JSONObject) returnStatus.getReturnObject();
			String response = returnStatus.getDescription();

			if (null == responseJson || response.isEmpty())
				throw new JilabaException("Payment Failed For Transaction: " + pineLabProp.getTransactionNumber());

			if (responseJson.getInt("ResponseCode") == CF.INVALID_REFID) {
				StringBuilder msg = new StringBuilder();
				msg.append(responseJson.getString("ResponseMessage"));
				if (null != responseJson.get("AdditionalInfo")) {

					Map<String, Object> map = CF.toMap(responseJson);
					JSONObject json = new JSONObject(map);
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> msgData = (List<Map<String, Object>>) json.get("AdditionalInfo");

					for (Map<String, Object> m : msgData) {
						if (m.get("Tag").equals("ExistingReferenceId"))
							msg.append("\n" + m.get("Tag") + " : " + m.get("Value").toString());
						else if (m.get("Tag").equals("Amount"))
							msg.append("\n" + m.get("Tag") + " : "
									+ (Double.parseDouble(m.get("Value").toString()) / 100));
					}
				}
				throw new JilabaException(msg.toString());
			}

			mapPayResponse = CF.toMap(responseJson);
			pineLabProp.setPlutusTransactionReferenceID(responseJson.getString("PlutusTransactionReferenceID"));

			// response 0 - Txn Approved Successfully
			if (responseJson.getInt("ResponseCode") != CF.PAYREQUEST_SUCCESS_CODE
					&& null != responseJson.get("AdditionalInfo")) {

				double amount = 0;
				String refId = "";

				JSONArray obj = responseJson.getJSONArray("AdditionalInfo");
				for (int i = 0; i < obj.length(); i++) {
					if (obj.getJSONObject(i).get("Tag").equals("ExistingReferenceId"))
						refId = obj.getJSONObject(i).getString("Value");
					else if (obj.getJSONObject(i).get("Tag").equals("Amount"))
						amount = obj.getJSONObject(i).getDouble("Value");
				}
				this.iSwipingMachine.setPending(true);
				return new ReturnStatus(false,
						"Transaction Pending For RefId: " + refId + "\nAmount: " + CF.toAmtFormat(amount));
			}

			return new ReturnStatus(true, mapPayResponse);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	@Override
	public void checkPaymentStatus(Object obj) {
		try {

			PaymentDetails details = (PaymentDetails) obj;

			/*Map<String, Object> details = CF.convertToMap(payDetails);
			JSONObject requestJson = new JSONObject(details);*/
			returnStatus = CF.generateJSON(pineLabProp);
			JSONObject requestJson = (JSONObject) returnStatus.getReturnObject();

			String data = requestJson.toString();
			JSONObject responseJson = null;

			// connection establishment
			returnStatus = CF.createConnection(URLStatus.CHECK_PAY, data);
			CF.statusCheck(returnStatus);
			responseJson = (JSONObject) returnStatus.getReturnObject();

			mapResponse = CF.toMap(responseJson);

			/**
			 * Commented By : Lokesh Raj.B On 30-Aug-2023  
			 * Reason		: To avoid DB OPERATION FAILED error
			 */
			/*details.setResponseCode(mapResponse.get("ResponseCode").toString());
			details.setLastResponseAt(LocalDateTime.now().format(CF.dateTimeFormat));
			
			if (Arrays.asList(CF.PAYSTATUS_SUCCESS_CODE, CF.PAYREQUEST_SUCCESS_CODE)
					.contains(responseJson.getInt("ResponseCode")))
				details.setPaymentStatus(PaymentStatus.SUCCESS);
			else
				details.setPaymentStatus(PaymentStatus.CANCELED);
			
			returnStatus = updateWebTransaction();
			CF.statusCheck(returnStatus);*/

			String payStatus = PaymentStatus.CANCELED;

			if (Arrays.asList(CF.PAYSTATUS_SUCCESS_CODE, CF.PAYREQUEST_SUCCESS_CODE)
					.contains(responseJson.getInt("ResponseCode"))) {
				payStatus = PaymentStatus.SUCCESS;
			}

			if (!payDetails.getResponseCode().equalsIgnoreCase(mapResponse.get("ResponseCode").toString())
					|| !payDetails.getPaymentStatus().equalsIgnoreCase(payStatus)) {

				details.setResponseCode(mapResponse.get("ResponseCode").toString());
				details.setLastResponseAt(LocalDateTime.now().format(CF.dateTimeFormat));
				details.setPaymentStatus(payStatus);

				returnStatus = updateWebTransaction();
				CF.statusCheck(returnStatus);

			}

			if (responseJson.getInt("ResponseCode") == CF.INVALID_REFID
					&& responseJson.getString("ResponseMessage").equalsIgnoreCase(PineLabsErrorMsg.DB_OPERATION_FAILED))
				return;

			// response 1001 - Txn Uploaded Successfully
			if (!Arrays.asList(CF.PAYSTATUS_SUCCESS_CODE, CF.PAYREQUEST_SUCCESS_CODE)
					.contains(responseJson.getInt("ResponseCode")))
				throw new JilabaException("Payment Failed For RefId: " + pineLabProp.getPlutusTransactionReferenceID()
						+ "\nAmount: " + CF.toAmtFormat(pineLabProp.getAmount() / 100));

			if (responseJson.getInt("ResponseCode") == CF.PAYREQUEST_SUCCESS_CODE) {
				isInterrupted = true;
				iSwipingMachine.apiResponse(mapResponse);
			}

		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	public ReturnStatus updateWebTransaction() {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" Update webtransaction Set ReferenceKey =?, ");
			sql.append(" PaymentStatus =?,ResponseCode =?,LastResponseAt =?,SequenceNo=? ");
			sql.append(" Where TranNo=? ");

			payDetails.getJdbcTemplate().update(sql.toString(),
					new Object[] { payDetails.getReferenceKey(), payDetails.getPaymentStatus(),
							payDetails.getResponseCode(), payDetails.getLastResponseAt(), payDetails.getSequenceNo(),
							payDetails.getTranNo() });

			return new ReturnStatus(true);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	@Override
	public ReturnStatus cancelPayment() {
		try {

			Map<String, Object> details = CF.convertToMap(pineLabProp);
			JSONObject requestJson = new JSONObject(details);
			String data = requestJson.toString();

			// connection establishment
			returnStatus = CF.createConnection(URLStatus.CANCEL_PAY, data);
			CF.statusCheck(returnStatus);

			if (returnStatus.isStatus() && null == returnStatus.getReturnObject())
				return new ReturnStatus(true);

			JSONObject responseJson = (JSONObject) returnStatus.getReturnObject();
			Map<String, Object> mapResponse = CF.toMap(responseJson);

			payDetails.setPaymentStatus(PaymentStatus.CANCELED);
			payDetails.setResponseCode(responseJson.getString("ResponseCode"));
			updateWebTransaction();

			if (responseJson.getInt("ResponseCode") == CF.INVALID_REFID
					&& responseJson.getString("ResponseMessage").equalsIgnoreCase(PineLabsErrorMsg.DB_OPERATION_FAILED))
				return new ReturnStatus(true);

			// response 0 - Txn Cancelled Successfully
			if (responseJson.getInt("ResponseCode") != CF.PAYCANCEL_SUCCESS_CODE)
				return new ReturnStatus(false,
						"Cancellation Failed For RefId: " + pineLabProp.getPlutusTransactionReferenceID() + "\nAmount: "
								+ CF.toAmtFormat(pineLabProp.getAmount() / 100));

			return new ReturnStatus(true, "Transaction Cancelled", mapResponse);
		} catch (Exception e) {
			if (null != returnStatus.getReturnObject() && (boolean) returnStatus.getReturnObject())
				isCancelled = false;
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	@Override
	public CreditCardMachineName getDeviceName() {
		return CreditCardMachineName.PINELABS;
	}

	@Override
	public ReturnStatus checkPaymentStatus(int cardCode, String referenceKey, int ipId, JdbcTemplate jdbcTemplate) {
		try {

			CreditCardMachineDAO cardMachineDAO = new CreditCardMachineDAO();
			returnStatus = cardMachineDAO.getDeviceDetails(cardCode, ipId, jdbcTemplate);
			if (returnStatus.isStatus() && null == returnStatus.getReturnObject())
				return new ReturnStatus(true);
			CF.statusCheck(returnStatus);

			CreditCardMachine cardMachine = (CreditCardMachine) returnStatus.getReturnObject();

			PineLabProperties prop = new PineLabProperties();
			prop.setMerchantID(cardMachine.getMerchantId());
			prop.setSecurityToken(cardMachine.getSecurityKey());
			prop.setIMEI(cardMachine.getiMEINo());
			prop.setMerchantStorePosCode(cardMachine.getMerchantPOSCode());
			prop.setPlutusTransactionReferenceID(referenceKey);

			CF.PAYMENT_URL = cardMachine.getPayURL();
			CF.PAY_STATUS_CHECK_URL = cardMachine.getStatusURL();
			CF.PAYMENT_CANCEL_URL = cardMachine.getCancelURL();

			returnStatus = CF.generateJSON(prop);
			JSONObject requestJson = (JSONObject) returnStatus.getReturnObject();

			requestJson.remove("transactionNumber");
			String data = requestJson.toString();
			JSONObject responseJson = null;
			String TID = "";

			// connection establishment
			returnStatus = CF.createConnection(URLStatus.CHECK_PAY, data);
			CF.statusCheck(returnStatus);
			responseJson = (JSONObject) returnStatus.getReturnObject();

			if (responseJson.getInt("PlutusTransactionReferenceID") == CF.ERR_PLUTUS_REFID)
				return new ReturnStatus(true);

			Map<String, Object> mapResponse = CF.toMap(responseJson);
			mapResponse.put("ReferenceKey", referenceKey);
			mapResponse.put("CreditCardMachine", cardMachine);

			if (responseJson.getInt("ResponseCode") == CF.PAYREQUEST_SUCCESS_CODE
					&& null != responseJson.get("TransactionData")) {

				JSONArray obj = responseJson.getJSONArray("TransactionData");
				for (int i = 0; i < obj.length(); i++) {

					if (!obj.getJSONObject(i).get("Tag").equals("TID"))
						continue;

					TID = obj.getJSONObject(i).getString("Value");
					break;
				}
			}
			mapResponse.put("TID", TID);

			if (Arrays.asList(CF.PAYSTATUS_SUCCESS_CODE, CF.PAYREQUEST_SUCCESS_CODE)
					.contains(responseJson.getInt("ResponseCode")))
				mapResponse.put("PaymentStatus", PaymentStatus.SUCCESS);
			else
				mapResponse.put("PaymentStatus", PaymentStatus.CANCELED);

			returnStatus = updateWebTransaction(mapResponse, jdbcTemplate);
			CF.statusCheck(returnStatus);

			// response 1001 - Txn Uploaded Successfully
			if (!Arrays.asList(CF.PAYSTATUS_SUCCESS_CODE, CF.PAYREQUEST_SUCCESS_CODE)
					.contains(responseJson.getInt("ResponseCode")))
				throw new JilabaException("Transaction Details not found for RefId: " + referenceKey);

			return new ReturnStatus(true, mapResponse);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	private ReturnStatus updateWebTransaction(Map<String, Object> mapResponse, JdbcTemplate jdbcTemplate) {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" Update webtransaction Set PaymentStatus =?,ResponseCode =?,LastResponseAt =?,TID=? ");
			sql.append(" Where ReferenceKey=? ");

			jdbcTemplate.update(sql.toString(),
					new Object[] { mapResponse.get("PaymentStatus"), mapResponse.get("ResponseCode"),
							LocalDateTime.now().format(CF.dateTimeFormat), mapResponse.get("TID"),
							mapResponse.get("ReferenceKey") });

			return new ReturnStatus(true);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setInterrupted(boolean isInterrupted) {
		this.isInterrupted = isInterrupted;
	}

	public void setDelayCheckNeed(boolean isDelayCheckNeed) {
		this.isDelayCheckNeed = isDelayCheckNeed;
	}

}
