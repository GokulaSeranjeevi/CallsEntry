package com.jilaba.common;

public class TableModules {
	private int intModuleCode;
	private String strModuleName;
	
	public TableModules(){
		intModuleCode = 0;
		strModuleName = "";
	}
	public TableModules(int mModuleCode,String mModuleName){
		intModuleCode = mModuleCode;
		strModuleName = mModuleName;
	}
	
	public int getIntModuleCode() {
		return intModuleCode;
	}
	public void setIntModuleCode(int intModuleCode) {
		this.intModuleCode = intModuleCode;
	}
	public String getStrModuleName() {
		return strModuleName;
	}
	public void setStrModuleName(String strModuleName) {
		this.strModuleName = strModuleName;
	}
	
}
