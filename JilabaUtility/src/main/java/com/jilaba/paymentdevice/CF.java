package com.jilaba.paymentdevice;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;

public class CF {

	public static String PAYMENT_URL = "";
	public static String PAY_STATUS_CHECK_URL = "";
	public static String PAYMENT_CANCEL_URL = "";
	/*private static final String TXN_ALREADY_COMPLETED = "TXN ALREADY COMPLETED"; // 2nd sequence no will be generated
	public static final String TRANSACTION_NOT_FOUND = "TRANSACTION NOT FOUND";*/

	public static final String SAVE_WEB_TRANSACTION = "spsavewebtransaction";

	public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static final int PAYREQUEST_SUCCESS_CODE = 0;
	public static final int PAYSTATUS_SUCCESS_CODE = 1001;
	public static final int PAYCANCEL_SUCCESS_CODE = 0;
	public static final int INVALID_REFID = 1;
	public static final int ERR_PLUTUS_REFID = -1;
	public boolean TXN_ALREADY_COMPLETED = false; // 2nd sequence no will be generated
	public boolean TRANSACTION_NOT_FOUND = false;

	@SuppressWarnings("unchecked")
	public static int getResponseCode(Object object, String key) throws JSONException {

		if (null == object)
			return INVALID_REFID;

		Map<String, Object> map;
		JSONObject json;
		int responseCode = 0;

		if (object instanceof Map<?, ?>) {
			map = (Map<String, Object>) object;
			responseCode = Integer.parseInt(map.getOrDefault(key, 0).toString());
		}

		if (object instanceof JSONObject) {
			json = (JSONObject) object;
			responseCode = json.getInt(key);
		}

		return responseCode;
	}

	public interface ISwipingMachine {

		public void apiResponse(Map<String, Object> mapResponse);

		public ReturnStatus saveWebTransaction();

		public ReturnStatus updateWebTransaction();

		public void setPending(boolean isPending);

		public ReturnStatus updateTransactionAutoCancelled();

	}

	public class PineLabPayMode {

		public static final int Card = 1;
		public static final int Cash = 2;
		public static final int Points = 3;
		public static final int Wallets = 4;
		public static final int Brand_EMI = 6;
		public static final int Sodexo = 7;
		public static final int PhonePe = 8;
		public static final int UPI_PayTm = 9;
		public static final int UPI_Sale = 10;
		public static final int UPI_BharatQR = 11;
		public static final int Airtel_Bank = 12;
		public static final int Paper_POS = 19;
		public static final int Bank_EMI = 20;
		public static final int Amazon_Pay_Via_MobileQR_and_Barcode = 21;
	}

	public class YesNoType {
		public static final String YES = "Y";
		public static final String NO = "N";
	}

	public class PaymentStatus {
		public static final String INITIATE = "I";
		public static final String SUCCESS = "S";
		public static final String CANCELED = "C";
	}

	public enum CreditCardMachineName {
		PINELABS
	}

	public enum URLStatus {
		MAKE_PAY, CHECK_PAY, CANCEL_PAY
	}

	public static String toAmtFormat(double value) {
		return new DecimalFormat("#0.00").format(value);
	}

	public static String toWtFormat(double value) {
		return new DecimalFormat("#0.000").format(value);
	}

	public static Map<String, Object> convertFromStringToMap(String data) {
		try {
			Map<String, Object> mapResponse = new HashMap<String, Object>();

			String datas[] = data.split(",");
			for (String d : datas) {

				String s[] = d.split(":");
				mapResponse.put(s[0].trim().replace("{", ""), s[1].trim().replace("}", ""));
			}
			return mapResponse;
		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(JSONObject object) throws JSONException {

		String key = "";
		Object value = "";
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			Iterator<String> keys = object.keys();

			while (keys.hasNext()) {
				key = keys.next();
				value = object.get(key);

				if (value instanceof JSONArray) {
					value = toList((JSONArray) value);
				} else if (value instanceof JSONObject) {
					value = toMap((JSONObject) value);
				} else if (JSONObject.NULL == value) {
					value = null;
				}

				map.put(key, value);
			}

			return map;
		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

	public static ReturnStatus generateJSON(PineLabProperties payDetails) throws RuntimeException {

		Map<String, Object> map = convertToMap(payDetails);
		JSONObject jsonObject = new JSONObject(map);
		return new ReturnStatus(true, jsonObject);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertToMap(Object object) {
		// object -> Map
		ObjectMapper oMapper = new ObjectMapper();
		return oMapper.convertValue(object, Map.class);
	}

	public static void statusCheck(ReturnStatus returnStatus) {
		if (!returnStatus.isStatus())
			throw new JilabaException(returnStatus.getDescription());
	}

	// Unused
	public static HttpURLConnection createConnection(String requestUrl) {
		try {

			//requestUrl = "https://stackoverflow.com/";

			URL url = new URL(requestUrl);
			HttpURLConnection postConnection = (HttpURLConnection) url.openConnection();
			postConnection.setRequestMethod("POST");
			postConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			postConnection.setDoInput(true);

			for (int i = 1; i <= 5; i++) {
				System.out.println(postConnection.getHeaderFieldKey(i) + " = " + postConnection.getHeaderField(i));
			}

			return postConnection;
		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	public static ReturnStatus createConnection(URLStatus urlType, String data) {

		String url = "";
		try {

			switch (urlType) {
			case MAKE_PAY:
				url = PAYMENT_URL;
				break;
			case CHECK_PAY:
				url = PAY_STATUS_CHECK_URL;
				break;
			case CANCEL_PAY:
				url = PAYMENT_CANCEL_URL;
				break;
			default:
				throw new JilabaException("URL Not Defined.");
			}

			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/json");

			StringEntity entity = new StringEntity(data);
			httpPost.setEntity(entity);

			HttpClient httpClient = HttpClientBuilder.create().build();
			BasicResponseHandler responseHandler = new BasicResponseHandler();
			String response = (String) httpClient.execute(httpPost, responseHandler);
			JSONObject responseJson = new JSONObject(response);

			if (responseJson.getInt("ResponseCode") == INVALID_REFID && urlType == URLStatus.CANCEL_PAY)
				return new ReturnStatus(false, responseJson.getString("ResponseMessage"), true);

			if (responseJson.getInt("ResponseCode") == PAYREQUEST_SUCCESS_CODE && urlType == URLStatus.CANCEL_PAY)
				return new ReturnStatus(true, responseJson);

			/** If Sequence No Same for Same Amount Transaction it will throw Txn Already Completed*/
			if (responseJson.getDouble("PlutusTransactionReferenceID") == ERR_PLUTUS_REFID
					// && responseJson.getString("ResponseMessage").equalsIgnoreCase(TXN_ALREADY_COMPLETED)
					&& urlType == URLStatus.MAKE_PAY)
				return new ReturnStatus(true, responseJson.getString("PlutusTransactionReferenceID"), null);

			if (responseJson.getInt("ResponseCode") == INVALID_REFID
					&& Arrays.asList(URLStatus.CHECK_PAY, URLStatus.CANCEL_PAY).contains(urlType) && responseJson
							.getString("ResponseMessage").equalsIgnoreCase(PineLabsErrorMsg.DB_OPERATION_FAILED)) {
				// JOptionPane.showMessageDialog(null, responseJson.getString("ResponseMessage"));
				return new ReturnStatus(true, response, responseJson);
			}

			if (!Arrays.asList(CF.PAYSTATUS_SUCCESS_CODE, CF.PAYREQUEST_SUCCESS_CODE)
					.contains(responseJson.getInt("ResponseCode")))
				return new ReturnStatus(false,
						responseJson.getString("ResponseMessage").isEmpty()
								? "Connection Failure...!\nCheck Device Details In CreditCardMachine Master"
								: responseJson.getString("ResponseMessage"));

			return new ReturnStatus(true, response, responseJson);
		} catch (JSONException e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		} catch (HttpResponseException e) {
			e.printStackTrace();
			return new ReturnStatus(false, "Connection Failure...!");
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

}
