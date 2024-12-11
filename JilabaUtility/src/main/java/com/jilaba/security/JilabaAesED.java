package com.jilaba.security;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.jilaba.exception.JilabaException;

public class JilabaAesED {

	private static final String ALGO = "AES";

	public static String encrypt(String Data, String secretKey) {
		String encryptedValue = null;
		try {

			String secret = encodeKey(secretKey);

			Key key = generateKey(secret);

			Cipher c = Cipher.getInstance(ALGO);

			c.init(Cipher.ENCRYPT_MODE, key);

			byte[] encVal = c.doFinal(Data.trim().getBytes());

			encryptedValue = Base64.getEncoder().encodeToString(encVal);

		} catch (Exception e) {
			throw new JilabaException(e.getMessage(), e);
		}

		return encryptedValue;
	}

	public static String decrypt(Object strToDecrypt, String secretKey) {
		try {

			String secret = encodeKey(secretKey);

			Key key = generateKey(secret);

			Cipher cipher = Cipher.getInstance(ALGO);

			cipher.init(Cipher.DECRYPT_MODE, key);

			return new String(cipher.doFinal(Base64.getDecoder().decode(String.valueOf(strToDecrypt).trim())));
		} catch (Exception e) {
			throw new JilabaException(e.getMessage(), e);
		}
	}

	private static Key generateKey(String secret) throws Exception {
		byte[] decoded = Base64.getDecoder().decode(secret.getBytes());
		Key key = new SecretKeySpec(decoded, ALGO);
		return key;
	}

	public static String decodeKey(String str) {
		byte[] decoded = Base64.getDecoder().decode(str.getBytes());
		return new String(decoded);
	}

	public static String encodeKey(String str) {
		byte[] encoded = Base64.getEncoder().encode(str.getBytes());
		return new String(encoded);
	}

}
