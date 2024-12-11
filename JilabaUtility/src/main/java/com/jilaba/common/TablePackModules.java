package com.jilaba.common;

public class TablePackModules {
	private int intPackageCode;
	private String strPackageName;
	private int intModuleCode;
	private String strModuleName;
	
	public TablePackModules(){
		intPackageCode = 0;
		strPackageName = "";
		intModuleCode = 0;
		strModuleName = "";
	}
	public TablePackModules(int mPackageCode,String mPackageName,int mModuleCode,String mModuleName){
		intPackageCode = mPackageCode;
		strPackageName = mPackageName;
		intModuleCode = mModuleCode;
		strModuleName = mModuleName;
	}
	
	public int getIntPackageCode() {
		return intPackageCode;
	}
	public void setIntPackageCode(int intPackageCode) {
		this.intPackageCode = intPackageCode;
	}
	public String getStrPackageName() {
		return strPackageName;
	}
	public void setStrPackageName(String strPackageName) {
		this.strPackageName = strPackageName;
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
