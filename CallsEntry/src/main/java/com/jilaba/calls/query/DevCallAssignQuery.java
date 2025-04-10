package com.jilaba.calls.query;

import org.springframework.stereotype.Component;

@Component
public class DevCallAssignQuery {

	private StringBuilder sb;

	public String getDevCoOrd() {

		sb = new StringBuilder();

		sb.append("Select * from Staff Where Active='Y' And designation=3");

		return sb.toString();
	}

	public String getCustomer() {

		sb = new StringBuilder();
		sb.append("Select * from Customer Where Active='Y'");

		return sb.toString();
	}

	public String getCustCoOrd() {

		sb = new StringBuilder();
		sb.append("Select * from custstaff Where Active='Y'");

		return sb.toString();
	}

	public String getDepartment() {

		sb = new StringBuilder();
		sb.append("Select * from Department Where dno<>6 ");

		return sb.toString();
	}

	public String getDeptAuthorize() {

		sb = new StringBuilder();
		sb.append("Select * from Staff Where Active='Y'");

		return sb.toString();
	}

	public String getRecvFrom() {

		sb = new StringBuilder();
		sb.append("Select * from Staff Where Active='Y' And designation<>4");

		return sb.toString();
	}

	public String getModule(int dept) {

		sb = new StringBuilder();
		if (dept != 0) {
			sb.append("Select * from Module Where Active='Y' And ");
			sb.append(" dno=" + dept);
		} else {
			sb.append("Select * from Module Where Active='Y' ");
		}

		return sb.toString();
	}

	public String getDevCalls() {

		sb = new StringBuilder("");
		sb.append("Select sugto,S.StaffName DeveloperName, Count(*) DevCalls from Calls C\r\n"
				+ "Left join staff S on S.staffid = C.sugto\r\n" + "  Where Sugto<>0 And C.Ready='' \r\n  "
				+ "group by sugto,S.staffname\r\n" + "Order by sugto");

		return sb.toString();
	}

	public String getCalls(int devCoOrd, long callNo, int strCustomer, int strDepartment, int strRecvFrom,
			int strModule, int strCallNature) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(
					"SELECT  I.CallNo ImgAttach,C.callno, FORMAT(C.cdate, 'dd-MMM-yyyy') AS cdate, t.custname, m.modulename,s.staffname ReceiverName, "
							+ "(Case when C.callnature='E' then 'Error'\r\n"
							+ "when C.callnature='M' Then 'Modification'\r\n"
							+ "when C.callnature='G' Then 'General' \r\n"
							+ "when C.callnature='D' Then 'Development'\r\n"
							+ "when C.callnature='C' Then 'Clarification'\r\n"
							+ "when C.callnature='T' Then 'Tallying' else '' End)callNature, \r\n")
					.append("C.callcoordinator, C.custcordinator_name, C.moption, C.ticketno, C.description \r\n")
					.append("FROM Calls C ").append("LEFT JOIN customer t ON C.cusid = t.custid \r\n")
					.append("LEFT JOIN module m ON C.moduleid = m.moduleid \r\n")
					.append("LEFT JOIN staff s ON C.receby = S.staffid  \r\n")
					.append(" LEFT JOIN Callimages I on I.Callno = C.callno \r\n").append("WHERE C.SugTo = 0   \r\n");

			if (devCoOrd != 0) {
				sb.append(" AND C.callcoordinator= ?");
			}
			if (callNo != 0) {
				sb.append(" AND C.CallNo = ?");
			}
			if (strCustomer != 0) {
				sb.append(" AND C.cusid= ?");
			}
			if (strDepartment != 0) {
				sb.append(" AND C.dno= ?");
			}
			if (strRecvFrom != 0) {
				sb.append(" AND C.receby= ?");
			}
			if (strModule != 0) {
				sb.append(" AND C.moduleid= ?");
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
			sb.append(" Order by C.CallNo");
			System.out.println("Constructed SQL: " + sb.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private void appendInClause(StringBuilder sb, String columnName, int count) {
		sb.append(columnName).append(" IN (?");
		for (int index = 1; index < count; index++) {
			sb.append(",?");
		}
		sb.append(")");
	}

	/*
	 * public String getCalls(int devCallcount, int callNo, int custCount, int
	 * deptCount, int recByCount, int modIdCount) { try {
	 * 
	 * sb = new StringBuilder(); sb.append(
	 * "Select C.callno,format(C.cdate,'dd-MMM-yyyy')cdate, t.custname ,m.modulename ,C.callcoordinator,C.custcordinator_name,C.moption,C.ticketno,C.description from Calls C\r\n"
	 * ); sb.append("Left join customer t on C.cusid = t.custid\r\n");
	 * sb.append("Left join module m on C.moduleid = m.moduleid\r\n");
	 * sb.append("Left join staff s on C.sugto = S.staffid  \r\n");
	 * sb.append("Where C.SugTo=0 And ");
	 * 
	 * sb.append(" C.callcoordinator in (?"); for (int index = devCallcount - 1;
	 * index > 0; index--) sb.append(",?"); sb.append(" )"); sb.append("  And ");
	 * 
	 * sb.append("  C.cusid in (?"); for (int index = custCount - 1; index > 0;
	 * index--) sb.append(",?"); sb.append(" )"); sb.append("  And ");
	 * 
	 * sb.append(" C.dno in (?"); for (int index = deptCount - 1; index > 0;
	 * index--) sb.append(",?"); sb.append(" )"); sb.append("  And ");
	 * sb.append("C.receby in (?"); for (int index = recByCount - 1; index > 0;
	 * index--) sb.append(",?"); sb.append(" )"); sb.append(" And ");
	 * sb.append("C.moduleid in (?"); for (int index = modIdCount - 1; index > 0;
	 * index--) sb.append(",?"); sb.append(" )"); if
	 * (!String.valueOf(callNo).trim().isEmpty()) sb.append(" And C.CallNo =" +
	 * callNo);
	 * 
	 * System.out.println(sb);
	 * 
	 * } catch (Exception e) { JOptionPane.showMessageDialog(null, e.getMessage());
	 * 
	 * } return sb.toString();
	 * 
	 * }
	 */
	public String getDeveloper() {

		sb = new StringBuilder("");
		sb.append("Select * from Staff Where Active='Y' And designation=1");

		return sb.toString();
	}

	public String devCallUpdate(Object cmbExplanation, Object cmbSugTo, String txtDevHrs, String assnDate, int callNo) {

		sb = new StringBuilder("");
		sb.append("Update Calls Set ExpNature ='" + cmbExplanation + "',\r\n");
		sb.append("Sugto=" + cmbSugTo + ",\r\n");
		sb.append("DevPriority=50,\r\n");
		sb.append("DevDueTime=" + txtDevHrs + ",\r\n");
		sb.append("Assigndate='" + assnDate + "'\r\n");
		sb.append("Where Callno=" + callNo);

		System.out.println(sb);
		return sb.toString();

	}

}
