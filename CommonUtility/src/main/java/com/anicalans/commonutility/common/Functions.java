package com.anicalans.commonutility.common;

public class Functions {
	public static boolean isNull(String string) {

		return null == string || string.trim().isEmpty() ? true : false;

	}

	public static boolean isNull(int value) {

		return value == 0 ? true : false;
	}
}
