package com.jilaba.common;

public class TablePackages {
	private int intPackageCode;
	private String strPackageName;
	public TablePackages(){
		intPackageCode = 0;
		strPackageName = "";
	}
	public TablePackages(int mPackageCode,String mPackageName){
		intPackageCode = mPackageCode;
		strPackageName = mPackageName;
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
	
	
}
