package com.jilaba.security;

import com.jilaba.exception.JilabaException;

public class Validation {
	private Validation() {

	}

	/**
	 * 
	 * @param strPass
	 *            string value to encrypt
	 * @return encrypted string 
	 * @throws JilabaException
	 */
	public static String encrypt(String strPass) throws JilabaException {
		try {
			StringBuilder strText = new StringBuilder();
			String strValue = "";
			for (int i = 0; i < strPass.length(); i++) {
				char character = strPass.charAt(i);
				float asciiValue = (float) character;
				char chrEncript = (char) (Math.round(asciiValue * 5 / 3));
				strText = strText.append(chrEncript);
			}
			strValue = String.valueOf(strText.reverse());
			return strValue;
		} catch (ArithmeticException e) {
			throw new JilabaException(e.getMessage(), e);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new JilabaException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param strPass string value to decript
	 * @return decrypted string 
	 * @throws JilabaException
	 */
	public static String decrypt(String strPass) throws JilabaException {
		try {
			StringBuilder strText = new StringBuilder();
			String strValue = "";
			for (int i = 0; i < strPass.length(); i++) {
				char character = strPass.charAt(i);
				float asciiValue = (float) character;
				char chrDecript = (char) (Math.round(asciiValue * 3 / 5));
				strText = strText.append(chrDecript);
			}
			strValue = String.valueOf(strText.reverse());
			return strValue;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new JilabaException(e.getMessage(), e);
		} catch (ArithmeticException e) {
			throw new JilabaException(e.getMessage(), e);
		}
	}
}
