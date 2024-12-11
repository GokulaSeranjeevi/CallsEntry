package com.jilaba.paymentdevice;

public class CreditCardMachineQuery {

	StringBuilder query;

	public String getDeviceDetails() {
		query = new StringBuilder();
		query.append("\n Select A.Code,A.IPID,M.IMEINo,M.DeviceId,M.MerchantId,M.CardCode, ");
		query.append("\n M.SecurityKey,M.MerchantPOSCode,M.Code As MachineCode,F.DeviceName, ");
		query.append("\n F.PayURL,F.StatusURL,F.CancelURL,F.AutoCancelDurationInMinutes,M.MachineID ");
		query.append("\n From creditcardmachineassign As A ");
		// query.append("\n Inner Join creditcardmachine As M On M.Code = A.MachineCode ");
		query.append("\n Inner Join creditcardmachine As M On M.DeviceSno = A.DeviceSno ");
		query.append("\n And M.Active = A.Active ");
		query.append("\n Inner Join fn_creditcardmachinedetails As F On F.DeviceId = M.DeviceId ");
		query.append("\n Where M.Active ='Y' And A.Active ='Y' And M.CardCode=? And A.IPID=? ");
		return query.toString();
	}

	public String getNumberControl() {
		query = new StringBuilder();
		query.append(" Select * from numbercontrol where ctrl_code=? ");
		return query.toString();
	}

	public String updateNumberControl() {
		query = new StringBuilder();
		query.append(" Update numbercontrol set Ctrl_Long=ifnull(Ctrl_Long,0)+1 Where Ctrl_Code=? ");
		return query.toString();
	}

	public String getCardDetails() {
		query = new StringBuilder();
		query.append("\nSelect C.CardCode,C.CardName,C.AcctCode,M.MachineID ");
		query.append("\nFrom creditcardmachine M ");
		query.append("\nInner Join creditcard C On C.CardCode=M.CardCode ");
		query.append("\nWhere M.Active='Y' And M.MachineId=? ");
		return query.toString();
	}

}