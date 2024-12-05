package com.jilaba.calls.query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.forms.FrmLogin;

@Component
@Scope("prototype")
public class ReadyCallsQuery {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
	private LocalDateTime now = LocalDateTime.now();

	private StringBuilder sb;

	public String getDeveloperCalls() {

		sb = new StringBuilder("");

		sb.append("Select * from staff where Active='Y' And designation in(1,3)");

		return sb.toString();

	}

	public String getCustomerCalls() {

		sb = new StringBuilder("");
		sb.append("Select * from Customer where active='Y'");

		return sb.toString();
	}

	public String getDeptAuthorize() {
		sb = new StringBuilder("");

		sb.append("Select * from custstaff where Active='Y' ");

		return sb.toString();
	}

	public String getDepartment() {
		sb = new StringBuilder("");

		sb.append("Select * from department where Active='Y' And dno<>6");

		return sb.toString();
	}

	public String getModule() {
		sb = new StringBuilder("");

		sb.append("Select * from module where Active='Y'");

		return sb.toString();
	}

	public String getReadyCalls(int strCmbDeveloper, int strCmbClient, int strCmbDeptAuthorize, int strCmbDepartment,
			int strCmbModule, String strOrderby, String callno, String strCallFromDate, String strCallToDate,
			String strReadyFromDate, String strReadyToDate) {
		sb = new StringBuilder("");

		sb.append(
				"Select R.Callno, format(C.CDate,'dd-MMM-yyyy')CDate,format(R.RDate,'dd-MMM-yyyy')RDate,S.staffname As Readyby ,CS.custname ,S2.staffname RecdBy,S3.staffname Authorised, d.department,m.modulename,C.description,R.readydescription,R.QcOper from ReadyMark R \r\n");
		sb.append("Left join Calls C on R.Callno = C.callno \r\n");
		sb.append("Left join staff S on S.staffid = R.readyby \r\n");
		sb.append("Left join staff S2 on S2.staffid = C.receby \r\n");
		sb.append("Left join staff S3 on S3.staffid = C.AUTHORISED \r\n");
		sb.append("Left join customer CS on CS.custid = C.cusid \r\n");
		sb.append("Left join department d on d.dno = C.dno \r\n");
		sb.append("Left join module m on m.moduleid = C.moduleid \r\n");
		sb.append(
				"Where cancel='' And C.testresult<>'C' And C.testdate is null And R.Callstatus<>'C' And C.Callno>999 \r\n");
		if (strCallToDate != null) {
			sb.append(" And C.Cdate between '" + strCallFromDate + "' And '" + strCallToDate + "'\r\n");
			//			sb.append(
			//					" And R.Rdate between '" + strReadyFromDate + "' And '" + strReadyToDate + "'\r\n");
		} else {
			sb.append(" And C.Cdate<='" + strCallFromDate + "'");
			//			sb.append(" And R.RdSate<='" + strReadyFromDate + "'");
		}

		if (!callno.equals("")) {
			sb.append(" And C.CallNo='" + callno + "' ");
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

		System.out.println(sb);
		return sb.toString();
	}

	public String getCallsImages(String callNo) {
		sb = new StringBuilder();
		sb.append("Select callno, image1, image2, image3, image4 from  Callimages Where Callno = ? And Callno>999 ");

		System.out.println(sb);
		return sb.toString();
	}

	public String updateCompletedCalls() {

		sb = new StringBuilder();

		sb.append("update Calls Set TestDate='" + formatter.format(now) + "',testedmodules=?,testedremarks=?,Testing="
				+ FrmLogin.OperCode + ",testresult='C',testtime='" + formatter.format(now) + "' Where callno=?");

		return sb.toString();

	}

	public String updateReturnCalls() {

		sb = new StringBuilder();

		sb.append(
				"update Calls Set  Ready='',ready_date=Null,ready_desc='',ready_time=Null,testresult='R',Progress=0 Where callno=?");

		return sb.toString();
	}

	public String saveReadyUnmark() {

		sb = new StringBuilder("");
		sb.append(
				"Insert Into Readyunmark (Callno, Description, module, unmarkoper, opercode, updated, uptime, cancel, RDate, readyby, Version, readysno)\r\n"
						+ "Select Callno, readydescription,  module," + FrmLogin.OperCode
						+ ", opercode, updated, uptime, cancel, RDate, readyby, Version,sno from ReadyMark Where Callno= ?");

		return sb.toString();
	}

	public String updateReadyCancel() {

		sb = new StringBuilder("");
		sb.append("Update ReadyMark Set Cancel='Y' Where CallNo= ? ");

		return sb.toString();

	}

	public String updateReadyProgress(String callNo) {

		sb = new StringBuilder("");

		sb.append("Update ReadyMark Set QcTakentime='" + formatter.format(now) + "',Progress =1,Qcoper="
				+ FrmLogin.OperCode + " Where CallNo=" + callNo);

		return sb.toString();
	}

	public String validateProgressCall() {

		sb = new StringBuilder("");

		sb.append("Select  * from ReadyMark Where progress=1 And CallStatus<>'C' And Callno>999 Order by QcOPer");

		return sb.toString();
	}

	public String updateReadyMarkTable() {

		sb = new StringBuilder("");

		sb.append(
				"update Readymark set callstatus='C',QcCompletetime='" + formatter.format(now) + "'  Where CallNo=? ");

		return sb.toString();
	}

	public String beforeProgressValidate() {

		sb = new StringBuilder("");

		sb.append("Select  progress from ReadyMark Where progress=1 And CallStatus<>'C'  And Callno>999 And Qcoper="
				+ FrmLogin.OperCode);

		return sb.toString();
	}

	public String updateReturnReadyMark() {

		sb = new StringBuilder("");

		sb.append("Update ReadyMark Set CallStatus='R' Where CallNo=? ");

		return sb.toString();
	}

}
