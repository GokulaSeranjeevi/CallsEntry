package com.jilaba.common;

import java.util.ArrayList;
import java.util.List;

public class PackageModules {
	public ReturnStatus getModules(){
		List<TableModules> listModules = new ArrayList<TableModules>();
		
		try{
			listModules.add(new TableModules(1, "MASTER"));
			listModules.add(new TableModules(2, "STOCK"));
			listModules.add(new TableModules(3, "ESTIMATE"));
			listModules.add(new TableModules(4, "BILLING"));
			listModules.add(new TableModules(5, "REPORTS"));
			listModules.add(new TableModules(6, "CRM"));
			listModules.add(new TableModules(7, "ACCOUNTS"));
			listModules.add(new TableModules(8, "SCHEME"));
			listModules.add(new TableModules(9, "UPDATION"));
			listModules.add(new TableModules(10, "MAINSCREEN"));
			listModules.add(new TableModules(11, "MISREPORT"));
			listModules.add(new TableModules(12, "YEAREND"));
			listModules.add(new TableModules(13, "CLIENTUTIL"));
			listModules.add(new TableModules(14, "HRPAYROLL"));
			listModules.add(new TableModules(15, "MAILSOFT"));
			listModules.add(new TableModules(16, "STORE"));
			listModules.add(new TableModules(17, "ORDER"));
			listModules.add(new TableModules(18, "CASH POINT"));
			
			return new ReturnStatus(true, listModules);
		}catch(RuntimeException e){
			return new ReturnStatus(false, e.getMessage(),e);
		}
	}
	public ReturnStatus getPackages(){
		List<TablePackages> listPackages = new ArrayList<TablePackages>();
		try{
			
			listPackages.add(new TablePackages(1, "JEWELLERY"));
			listPackages.add(new TablePackages(2, "RETAIL STORE"));
			listPackages.add(new TablePackages(3, "ADDRESS"));
			listPackages.add(new TablePackages(4, "MANUFACTURING"));
			listPackages.add(new TablePackages(5, "HRPAYROLL"));
			
			return new ReturnStatus(true, "");
		}catch(RuntimeException e){
			return new ReturnStatus(false, e.getMessage(),e);
		}
	}
	public ReturnStatus getPackModules(){
		List<TablePackModules> listPackModule = new ArrayList<TablePackModules>();
		try{
			
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 1, "MASTER"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 2, "STOCK"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 3, "ESTIMATE"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 4, "BILLING"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 5, "REPORTS"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 6, "CRM"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 7, "ACCOUNTS"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 8, "SCHEME"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 9, "UPDATION"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 10, "MAINSCREEN"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 11, "MISREPORT"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 12, "YEAREND"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 13, "CLIENTUTIL"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 14, "HRPAYROLL"));
			listPackModule.add(new TablePackModules(1, "JEWELLERY", 15, "MAILSOFT"));
			
			
			listPackModule.add(new TablePackModules(2, "RETAIL STORE", 1, "MASTER"));
			listPackModule.add(new TablePackModules(2, "RETAIL STORE", 2, "STOCK"));
			listPackModule.add(new TablePackModules(2, "RETAIL STORE", 3, "ESTIMATE"));
			listPackModule.add(new TablePackModules(2, "RETAIL STORE", 4, "BILLING"));
			listPackModule.add(new TablePackModules(2, "RETAIL STORE", 5, "REPORTS"));
			listPackModule.add(new TablePackModules(2, "RETAIL STORE", 6, "CRM"));
			listPackModule.add(new TablePackModules(2, "RETAIL STORE", 7, "ACCOUNTS"));
			listPackModule.add(new TablePackModules(2, "RETAIL STORE", 10, "MAINSCREEN"));
			listPackModule.add(new TablePackModules(2, "RETAIL STORE", 12, "YEAREND"));
			listPackModule.add(new TablePackModules(2, "RETAIL STORE", 14, "HRPAYROLL"));
			listPackModule.add(new TablePackModules(2, "RETAIL STORE", 15, "MAILSOFT"));
			listPackModule.add(new TablePackModules(2, "RETAIL STORE", 18, "CASH POINT"));
			
			listPackModule.add(new TablePackModules(3, "ADDRESS", 15, "MAILSOFT"));

			listPackModule.add(new TablePackModules(4, "MANUFACTURING", 1, "MASTER"));
			listPackModule.add(new TablePackModules(4, "MANUFACTURING", 2, "STOCK"));
			listPackModule.add(new TablePackModules(4, "MANUFACTURING", 4, "BILLING"));
			listPackModule.add(new TablePackModules(4, "MANUFACTURING", 10, "MAINSCREEN"));
			listPackModule.add(new TablePackModules(4, "MANUFACTURING", 16, "STORE"));
			listPackModule.add(new TablePackModules(4, "MANUFACTURING", 17, "ORDER"));

			listPackModule.add(new TablePackModules(5, "HRPAYROLL", 14, "HRPAYROLL"));
			
			return new ReturnStatus(true, listPackModule);
		}catch(RuntimeException e){
			return new ReturnStatus(false, e.getMessage(),e);
		}
	}
}
