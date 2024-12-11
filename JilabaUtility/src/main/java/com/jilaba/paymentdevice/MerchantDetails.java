package com.jilaba.paymentdevice;

public class MerchantDetails {

	private static String id = "";
	private static String key = "";
	private static String salt = "";
	private static String authHeader = "";
	private static String responseUrl = "";
	private static String name = "";

	public static String getId() {
		return id;
	}

	public static void setId(String id) {
		MerchantDetails.id = id;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		MerchantDetails.key = key;
	}

	public static String getSalt() {
		return salt;
	}

	public static void setSalt(String salt) {
		MerchantDetails.salt = salt;
	}

	public static String getAuthHeader() {
		return authHeader;
	}

	public static void setAuthHeader(String authHeader) {
		MerchantDetails.authHeader = authHeader;
	}

	public static String getResponseUrl() {
		return responseUrl;
	}

	public static void setResponseUrl(String responseUrl) {
		MerchantDetails.responseUrl = responseUrl;
	}

	public static String getResponseUrl(String transactionId) {

		return responseUrl.replace("<KEY>", key).replace("<ID>", transactionId);

	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		MerchantDetails.name = name;
	}

}
