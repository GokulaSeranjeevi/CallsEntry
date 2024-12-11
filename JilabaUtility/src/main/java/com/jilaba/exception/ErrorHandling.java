package com.jilaba.exception;

public class ErrorHandling {
	public static String handleError(Throwable throwable) {
		String msg = "";
		if (throwable.getCause() != null) {
			msg = handleError(throwable.getCause());
		} else {
			msg = throwable.getMessage();
		}
		return msg;
	}
}
