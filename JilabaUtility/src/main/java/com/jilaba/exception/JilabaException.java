package com.jilaba.exception;

public class JilabaException extends RuntimeException{

	/**
	 * Jilaba Exception Handler
	 * @author jilaba
	 */
	private static final long serialVersionUID = 1L;
	
	private final JilabaExceptionLevel exceptionLevel;
	/**
	 * Constructor without parameter
	 */
	public JilabaException(){
		super();
		exceptionLevel = JilabaExceptionLevel.ERROR;
	}
	/**
	 * Constructor with one parameter
	 * @param arg0 is error message
	 */
	public JilabaException(String arg0){
		super(arg0);
		exceptionLevel = JilabaExceptionLevel.ERROR;
	}
	/**
	 * Constructor with two parameter
	 * @param jilabaExceptionLevel is jilaba exception level
	 * @param arg0 is error message
	 */
	public JilabaException(JilabaExceptionLevel jilabaExceptionLevel,String arg0){
		super(arg0);
		this.exceptionLevel = jilabaExceptionLevel;
	}
	
	public JilabaException(String arg0,Throwable arg1){
		super(arg0, arg1);
		exceptionLevel = JilabaExceptionLevel.ERROR;
	}
	
	public JilabaException(Throwable arg0){
		super(arg0.getMessage());
		exceptionLevel = JilabaExceptionLevel.ERROR;
	}
	
	/**
	 * 
	 * @return exceptionLevel
	 */
	public JilabaExceptionLevel getJilabaExceptionLevel(){
		return exceptionLevel;
	}
	
	
	
}
