package com.jilaba.calls.query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.forms.FrmLogin;

@Component
@Scope("prototype")
public class CallsCompletedQuery {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
	private LocalDateTime now = LocalDateTime.now();
	private StringBuilder sb;

	public String getDeveloperCalls() {
		sb = new StringBuilder("");

		sb.append("Select * from staff where Active='Y' And designation=1");

		return sb.toString();
	}

	public String getCustomerCalls() {
		sb = new StringBuilder("");
		sb.append("Select * from Customer where active='Y'");

		return sb.toString();
	}

	public String getDeptAuthorize() {
		sb = new StringBuilder("");

		sb.append("Select * from staff where Active='Y' And designation=2");

		return sb.toString();
	}

	public String getDepartment() {
		sb = new StringBuilder("");

		sb.append("Select * from department where Active='Y'");

		return sb.toString();
	}

	public String getModule(Integer dept) {
		sb = new StringBuilder("");

		if (dept != 0)
			sb.append("Select * from module where Active='Y' And dno=" + dept);
		else
			sb.append("Select * from module where Active='Y' ");
		return sb.toString();
	}

	public String getComletedCalls(int strCmbDeveloper, int strCmbClient, int strCmbDeptAuthorize, int strCmbDepartment,
			int strCmbModule, String strOrderby, String callNo, String strCallFromDate, String strCallToDate,
			String strCompletedFromDate, String strCompletedToDate) {

		sb = new StringBuilder("");
		sb.append("\r\n");
		sb.append(
				"Select C.callno,Format( C.cdate,'dd-MMM-yyyy') CallDate,Format( C.TestDate ,'dd-MMM-yyyy')TestDate,s.staffname Testedby,L.custname ClientName,S1.staffname RecbyName,\r\n");
		sb.append(
				"S2.staffname AUTHORISED ,d.department,m.modulename Module,C.description CallDescription,C.testedremarks TestDescription from Calls C\r\n");
		sb.append("Left join staff S on S.staffid =C.Testing\r\n" + "Left join customer L on L.custid = C.cusid\r\n");
		sb.append("Left join staff S1 on S1.staffid =C.receby\r\n");
		sb.append("Left join staff S2 on S2.staffid =C.AUTHORISED\r\n" + "Left join department d on d.dno = C.dno\r\n");
		sb.append("Left join module m on m.moduleid = C.moduleid\r\n" + "Where testresult='C' ");

		if (strCallToDate != null) {
			sb.append(" And C.Cdate between '" + strCallFromDate + "' And '" + strCallToDate + "'\r\n");
		} else {
			sb.append(" And C.Cdate<='" + strCallFromDate + "'");
		}

		if (!callNo.equals("")) {
			sb.append(" And C.CallNo='" + callNo + "' ");
		}
		if (strCmbDeveloper != 0) {
			sb.append(" And C.Sugto= ? ");
		}
		if (strCmbClient != 0) {
			sb.append(" And C.cusid= ? ");
		}
		if (strCmbDeptAuthorize != 0) {
			sb.append(" And C.Authorised= ? ");
		}
		if (strCmbDepartment != 0) {
			sb.append(" And C.dNo= ? ");
		}
		if (strCmbModule != 0) {
			sb.append(" And C.moduleid= ? ");
		}
		if (!strOrderby.equals("")) {
			sb.append(" Order by " + strOrderby);
		}

		return sb.toString();

	}

	public String updateDeliveredCalls() {

		sb = new StringBuilder("");
		sb.append("update Calls Set CompDesc=?,compltedby=" + FrmLogin.OperCode + ",compon='" + formatter.format(now));
		sb.append("',testresult='D'  Where CallNo= ?");

		return sb.toString();
	}

}
