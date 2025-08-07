package com.jilaba.calls.query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.forms.FrmLogin;

@Component
@Scope("prototype")
public class TaskAssignmentQuery {

	private StringBuilder sb;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
	private LocalDateTime now = LocalDateTime.now();

	public String getDevelopers() {

		sb = new StringBuilder();

		sb.append("Select * from Staff Where Active='Y' and Designation Not In(4,5) ");

		return sb.toString();
	}

	public String getDepartment() {

		sb = new StringBuilder();
		sb.append("Select * from Department Where Active='Y' And dno<>6");

		return sb.toString();
	}

	public String getModules(Integer deptNo) {

		sb = new StringBuilder();
		if (deptNo != 0)
			sb.append("Select * from Module Where dno=" + deptNo);
		else
			sb.append("Select * from Module Where Active='Y'");

		return sb.toString();
	}

	public String getCustomer() {
		sb = new StringBuilder();
		sb.append("Select * from Customer Where Active='Y'");

		return sb.toString();
	}

	public String getCustCoOrd() {

		sb = new StringBuilder();
		sb.append("Select * from custStaff Where Active='Y'");

		return sb.toString();
	}

	public String getRecvFrom() {

		sb = new StringBuilder();
		sb.append("Select * from Staff Where Active='Y' And designation<>4");

		return sb.toString();
	}

	public String getCallCoOrd() {

		sb = new StringBuilder();
		sb.append("Select * from Staff Where Active='Y'");

		return sb.toString();
	}

	public String getDeveloperCalls(String asOnDate, int devCount, int deptCount, int modIdCount, int custCount,
			int strType, int recByCount, int callCoOrdcount, long strCallNo, int strCallNature) {

		sb = new StringBuilder();
		sb.append(
				"SELECT I.CallNo ImgAttach,C.callno, FORMAT(C.cdate, 'dd-MMM-yyyy') AS cdate, t.custname, m.modulename,C.DevPriority, (c.DevDuetime )DevDuetime,(Case when C.callnature='E' then 'Error'\r\n"
						+ "						when C.callnature='M' Then 'Modification'\r\n"
						+ "						when C.callnature='G' Then 'General' \r\n"
						+ "						when C.callnature='D' Then 'Development'\r\n"
						+ "						when C.callnature='C' Then 'Clarification'\r\n"
						+ "						when C.callnature='T' Then 'Tallying' else '' End)callNature,s3.staffname RecbyName, \r\n")
				.append("s1.staffname callcoordinator, C.custcordinator_name, C.moption, C.ticketno, C.description,c.Priority,C.duedate,FORMAT(c.Assigndate, 'dd-MMM-yyyy')Assigndate,C.moduleid,c.cusid,"
						+ "FORMAT(c.CallTakenDate, 'dd-MMM-yyyy') As CallTakenDate,c.progress,C.Testresult,C.Ready \r\n")
				.append("FROM Calls C ").append("LEFT JOIN customer t ON C.cusid = t.custid\r\n ")
				.append("LEFT JOIN module m ON C.moduleid = m.moduleid \r\n")
				.append("LEFT JOIN staff s ON C.sugto = S.staffid \r\n")
				.append("LEFT JOIN staff s1 ON C.callcoordinator = s1.staffid \r\n")
				.append("LEFT JOIN staff s3 ON C.receby = s3.staffid \r\n")
				.append("LEFT JOIN Callimages I on I.Callno = C.callno \r\n")
				.append("WHERE C.cdate <='" + asOnDate + "' And C.sugto<>0  And S.designation<>1 And C.testresult<>'C'\r\n");

		if (devCount != 0) {
			sb.append(" AND  C.Sugto= ?");
		}
		if (deptCount != 0) {
			sb.append(" AND C.dNo=? ");
		}
		if (modIdCount != 0) {
			sb.append(" AND C.moduleid=? ");
		}
		if (custCount != 0) {
			sb.append(" AND C.cusid=? ");
		}
		switch (strType) {
		case 1:
			sb.append(" AND C.Ready='' ");
			break;
		case 2:
			sb.append(" AND C.Ready='Y' ");
			break;
		case 3:
			sb.append(" AND C.testresult='C' ");
			break;
		default:
			// Handle unexpected values or do nothing
			break;
		}
		if (recByCount != 0) {
			sb.append(" AND C.receby=? ");
		}
		if (callCoOrdcount != 0) {
			sb.append(" AND C.callcoordinato=? ");
		}
		if (strCallNo != 0) {
			sb.append(" AND C.CallNo = ?");
		}
		switch (strCallNature) {
		case 1:
			sb.append(" AND C.callnature='E' ");
			break;
		case 2:
			sb.append(" AND C.callnature='M' ");
			break;
		case 3:
			sb.append(" AND C.callnature='G' ");
			break;
		case 4:
			sb.append(" AND C.callnature='D' ");
			break;
		case 5:
			sb.append(" AND C.callnature='C' ");
			break;
		case 6:
			sb.append(" AND C.callnature='T' ");
			break;
		default:
			// Handle unexpected values or do nothing
			break;
		}
		if (devCount != 0) {
			sb.append("\r\n Order by DevPriority ");
		} else {
			sb.append("\r\n Order by Callno ");
		}
		System.out.println("Constructed SQL: " + sb.toString());
		return sb.toString();

	}

	// private void appendInClause(StringBuilder sb, String columnName, int count) {
	// sb.append(columnName).append(" IN (?");
	// for (int index = 1; index < count; index++) {
	// sb.append(",?");
	// }
	// sb.append(")");
	// }

	public String getCallsImages(String callNo) {

		sb = new StringBuilder();
		sb.append("Select callno, image1, image2, image3, image4 from  Callimages Where Callno = ?");

		System.out.println(sb);
		return sb.toString();
	}

	public String updateProgressCall() {

		StringBuilder sb = new StringBuilder();

		sb.append("Update calls Set callTakenDate='").append(formatter.format(now))
				.append("', Progress=1 Where CallNo=?");

		System.out.println(sb);
		return sb.toString();
	}

	public String validateProgressCall(int developer) {

		sb = new StringBuilder("");
		sb.append(
				"Select sugto from JCalls.Dbo.Calls Where CallTakendate is not null and Ready=''  And progress=1 And Sugto="
						+ developer);

		System.out.println(sb);
		return sb.toString();
	}

	public String updateReadyCals() {

		sb = new StringBuilder("");

		sb.append("update Calls Set TestDate='" + formatter.format(now) + "',testedmodules=?,testedremarks=?,Testing="
				+ FrmLogin.OperCode + ",testresult='C',testtime='" + formatter.format(now) + "' Where callno=?");

		System.out.println(sb);
		return sb.toString();

	}

	public String getReturnCall(int developer) {

		sb = new StringBuilder("");
		sb.append("Select callno,Sugto from Calls Where testresult='R' And Ready='' And sugto=" + developer);

		return sb.toString();
	}

	public String validateReadyCalls(String callNo) {

		sb = new StringBuilder("");
		sb.append(" Select * from Calls Where Callno='" + callNo + "'");

		return sb.toString();
	}

	public String insertProgressCall(String callNo) {

		sb = new StringBuilder("");
		sb.append(
				"Insert Into Progress (Callno, Callby, Moduleid, Calldate, Calltime, Givenby, Remarks, Ready, READYDATE, CANCELOPER, ReadyTime)\r\n"
						+ "Select callno,receby,moduleid,'" + formatter.format(now)
						+ "',CallTime,callcoordinator,'',Ready,ready_date," + FrmLogin.OperCode
						+ ",ready_time from Calls Where callno=" + callNo);

		return sb.toString();
	}

	public String updateProgressCancel(String callNo) {

		sb = new StringBuilder("");
		sb.append(" update Calls Set Progress=0 Where CallNo=" + callNo);

		return sb.toString();
	}

	public String updateDevPriority(String devPriority, String selectedCallno) {

		sb = new StringBuilder("");
		if (devPriority.equalsIgnoreCase(""))
			sb.append("Update Calls Set DevPriority=0 Where CallNo='" + selectedCallno + "'");
		else
			sb.append("Update Calls Set DevPriority=0" + devPriority + " Where CallNo='" + selectedCallno + "'");

		return sb.toString();
	}

	public String updateDevTransfer(int cNo, int dev) {

		sb = new StringBuilder("");

		sb.append(" Update Calls Set Sugto=" + dev + " Where Callno=" + cNo);

		return sb.toString();
	}
}
