package com.jilaba.calls.query;

import org.springframework.stereotype.Component;

@Component
public class CallsEntryQuery {

	private StringBuilder sb;

	public String getCallFrom() {

		sb = new StringBuilder();
		sb.append("Select * from Staff Where Active='Y' \r\n" + "Order by staffname");
		return sb.toString();
	}

	public String getClient() {

		sb = new StringBuilder();
		sb.append("Select * from Customer Where Active='Y'");
		return sb.toString();
	}

	public String getDepartment() {

		sb = new StringBuilder();
		sb.append("Select * from department Where Active='Y' and dno<>6");
		return sb.toString();
	}

	public String getCustStaff() {

		sb = new StringBuilder();
		sb.append("Select * from custstaff Where Active='Y' \r\n" + "Order by custstaffname");

		return sb.toString();
	}

	public String getCallCoOrd() {

		sb = new StringBuilder();
		sb.append("Select * from staff Where Active='Y' And designation in(3,5) \r\n" + "Order by staffname ");
		return sb.toString();
	}

	public String getModule(Integer dept) {

		sb = new StringBuilder();

		if (dept != 0)
			sb.append("Select * from module Where Active='Y' And dno=" + dept);
		else
			sb.append("Select * from module Where Active='Y'");

		return sb.toString();
	}

	public String getLastCallNo() {
		sb = new StringBuilder();
		sb.append("Select Coalesce(Max(CallNo),0) from calls ");

		return sb.toString();
	}

	public String getsaveCallsImages(int callNo, byte[] lblImage1Path, byte[] lblImage2Path, byte[] lblImage3Path,
			byte[] lblImage4Path) {
		sb = new StringBuilder();

		sb.append("Insert Into callImages (callNo,Image1,Image2,Image3,Image4) values (?,?,?,?,?)");

		System.out.println(sb);
		return sb.toString();
	}

	public String getCalls(String fromDate, String toDate, int strViewRecby, int strViewCallCoOrd, int strViewCustCoOrd,
			int strViewDevCoOrd, int strViewClient, int strViewDeptAuthorize, int strViewDepartment, int strViewModule,
			String strOrderby, String callNo) {

		sb = new StringBuilder("");

		sb.append("\r\n"
				+ "Select C.callno,Format(C.cdate,'dd-MMM-yyyy') CallDate,CU.custname CustomerName,C.moption, C.description CallDescription,s.staffname RecbyName,s1.staffname Authorised,D.department,M.modulename,"
				+ "(Case when C.callnature='E' then 'Error'\r\n" + "when C.callnature='M' Then 'Modification'\r\n"
				+ "when C.callnature='G' Then 'General' \r\n" + "when C.callnature='D' Then 'Development'\r\n"
				+ "when C.callnature='C' Then 'Clarification'\r\n"
				+ "when C.callnature='T' Then 'Tallying' else '' End)callNature,C.custcordinator_name,s2.staffname callcoordinator,C.RefNo,s2.staffname DevCoOrd,"
				+ "I.Image1,Image2,I.Image3,I.Image4   from Calls C\r\n"
				+ "Left join staff s On s.staffid = C.receby\r\n" + "Left join customer CU On CU.custid = C.cusid\r\n"
				+ "Left join staff s1 On s1.staffid = C.AUTHORISED\r\n" + "Left join department D On D.dno = C.dno\r\n "
				+ " Left join staff s2 On s2.staffid = C.callcoordinator\r\n"
				+ "Left join module M On M.moduleid = C.moduleid \r\n"
				+ "Left join Callimages I On I.Callno = C.callno \r\n");
		sb.append("Where testResult='' \r\n");

		if (toDate != null) {
			sb.append(" And C.Cdate between '" + fromDate + "' And '" + toDate + "'\r\n");

		} else {
			sb.append(" And C.Cdate<='" + fromDate + "'");
		}
		if (strViewRecby != 0) {
			sb.append(" And C.Receby= ?");
		}
		if (strViewCallCoOrd != 0) {
			sb.append(" And C.callcoordinator= ?");
		}
		if (strViewCustCoOrd != 0) {
			sb.append(" And C.custcoordinator= ?");
		}
		if (strViewDevCoOrd != 0) {
			sb.append(" And C.callcoordinator= ?");
		}
		if (strViewClient != 0) {
			sb.append(" And C.cusid= ?");
		}
		if (strViewDeptAuthorize != 0) {
			sb.append(" And C.AUTHORISED= ?");
		}
		if (strViewDepartment != 0) {
			sb.append(" And C.dNo= ?");
		}
		if (strViewModule != 0) {
			sb.append(" And C.Moduleid= ?");
		}
		if (!callNo.equalsIgnoreCase("")) {
			sb.append(" And C.callNo=" + callNo);
		}
		if (!strOrderby.equalsIgnoreCase("")) {
			sb.append("Order by " + strOrderby);
		}

		System.out.println(sb);
		return sb.toString();
	}

	public String updateCallEdit(String cmbCallFrom, String cmbCustomer, String cmbDepartment, String cmbCustCoOrd,
			String cmbCallCoOrd, String cmbModule, String txtRefNo, String txtOption, String cmbNature, String txtDesc,
			String cmbDevCoOrd, String txtCallNo) {

		sb = new StringBuilder("");
		sb.append("Update Calls Set cusid=" + cmbCustomer + ",\r\n");
		sb.append("cdate=cdate,\r\n");
		sb.append("moduleid=" + cmbModule + ",\r\n");
		sb.append("moption='" + txtOption + "',\r\n");
		sb.append("description='" + txtDesc + "',\r\n");
		sb.append("receby=" + cmbCallFrom + ",\r\n");
		sb.append("duedate=duedate,\r\n");
		sb.append("sugto =sugto,\r\n");
		sb.append("compltedby =compltedby,\r\n");
		sb.append("compon = compon,\r\n");
		sb.append("note = note,\r\n");
		sb.append("apptime= apptime,\r\n");
		sb.append("atsite=atsite,\r\n");
		sb.append("callnature='" + cmbNature + "',\r\n");
		sb.append("approved = approved,\r\n");
		sb.append("ApprovalBy = ApprovalBy,\r\n");
		sb.append("Ready = Ready,\r\n");
		sb.append("CallsId =CallsId,\r\n");
		sb.append("Remind = Remind,\r\n");
		sb.append("Modulecode = Modulecode,\r\n");
		sb.append("Compcode= Compcode,\r\n");
		sb.append("Mandays = Mandays,\r\n");
		sb.append("Hours =Hours,\r\n");
		sb.append("WebActive = WebActive,\r\n");
		sb.append("dno = " + cmbDepartment + ",\r\n");
		sb.append("ready_by = ready_by,\r\n");
		sb.append("progress = progress,\r\n");
		sb.append("ready_date= ready_date,\r\n");
		sb.append("ready_desc = ready_desc,\r\n");
		sb.append("Testing = Testing,\r\n");
		sb.append("TestDate = TestDate,\r\n");
		sb.append("Targetdate = Targetdate,\r\n");
		sb.append("ProcessingTime = ProcessingTime,\r\n");
		sb.append("targetHours = targetHours,\r\n");
		sb.append("AUTHORISED = AUTHORISED,\r\n");
		sb.append("OUTCALLS = OUTCALLS,\r\n");
		sb.append("STKPICPATH = STKPICPATH,\r\n");
		sb.append("DeptNo = DeptNo,\r\n");
		sb.append("RefNo =" + txtRefNo + ",\r\n");
		sb.append("CompVersion = CompVersion,\r\n");
		sb.append("CallTakenDate = CallTakenDate,\r\n");
		sb.append("DevDuetime = DevDuetime,\r\n");
		sb.append("Priority = Priority,\r\n");
		sb.append("AgCallNo= AgCallNo,\r\n");
		sb.append("callno = callno,\r\n");
		sb.append("callentryIP = callentryIP,\r\n");
		sb.append("custcoordinator ='" + cmbCustCoOrd + "' ,\r\n");
		sb.append("custcordinator_name = custcordinator_name,\r\n");
		sb.append("ready_time = ready_time,\r\n");
		sb.append("AssignDate = AssignDate,\r\n");
		sb.append("AssignTime = AssignTime,\r\n");
		sb.append("Transferflag = Transferflag,\r\n");
		sb.append("DevstartTime = DevstartTime,\r\n");
		sb.append("DiffHours = DiffHours,\r\n");
		sb.append("documentnumber = documentnumber,\r\n");
		sb.append("uniquekey = uniquekey,\r\n");
		sb.append("ticketrecby = ticketrecby,\r\n");
		sb.append("testtime = testtime,\r\n");
		sb.append("ticketno =ticketno,\r\n");
		sb.append("CallTime =CallTime,\r\n");
		sb.append("CallsRecvMode = CallsRecvMode,\r\n");
		sb.append("assignpriority = assignpriority,\r\n");
		sb.append("callcoordinator =" + cmbCallCoOrd + ",\r\n");
		sb.append("Transferdate = Transferdate,\r\n");
		sb.append("CompDesc = CompDesc,\r\n");
		sb.append("Version = Version,\r\n");
		sb.append("DevPriority = DevPriority,\r\n");
		sb.append("testresult = testresult,\r\n");
		sb.append("testedmodules = testedmodules,\r\n");
		sb.append("testedremarks = testedremarks,\r\n");
		sb.append("ExpNature = ExpNature,\r\n");
		sb.append("ProposedDev = ProposedDev,\r\n");
		sb.append("OrgCallTakenDate = OrgCallTakenDate,\r\n");
		sb.append("GrpRemID = GrpRemID   \r\n");
		sb.append("Where Callno=" + txtCallNo);

		System.out.println(sb);

		return sb.toString();
	}

	public String updateCallImages(int callNo, byte[] lblImage1, byte[] lblImage2, byte[] lblImage3, byte[] lblImage4) {

		sb = new StringBuilder("");

		sb.append("Update Callimages Set Image1=?, Image2 = ?," + "Image3 =?,Image4=? Where Callno=?");

		return sb.toString();
	}

	public String getDevCoOrd() {
		sb = new StringBuilder();
		sb.append("Select * from Staff Where Active='Y' And designation=3");
		return sb.toString();
	}

	public String getCallno() {

		sb = new StringBuilder("");

		sb.append("Select Coalesce(Max(CallNo),0)+1 from calls with (Nolock) ");
		return sb.toString();

	}

	public String getExcelFileName(String callNo) {

		sb = new StringBuilder("");

		sb.append("Select FileName from ExcelJsonStore Where CallNo=" + callNo);

		return sb.toString();
	}

}
