package com.jilaba.calls.model;

import java.util.ArrayList;
import java.util.List;

public class Calls {

	private List<Customer> lstCustomer = new ArrayList<Customer>();
	private List<Module> lstModule = new ArrayList<Module>();
	private List<CallsImages> lstCallsImages = new ArrayList<CallsImages>();

	private String custName;
	private String moduleName;
	private String cusid = "";
	private String cdate = null;
	private int moduleid = 0;
	private String moption = "";
	private String description = "";
	private int receby = 0;
	private String duedate = null;
	private int sugto = 0;
	private int compltedby = 0;
	private String compon = null;
	private String note = "";
	private int apptime = 0;
	private String atsite = "";
	private String callnature = "";
	private String approved = "";
	private int ApprovalBy = 0;
	private String Ready = "";
	private String CallsId = "";
	private int Remind = 0;
	private int Modulecode = 0;
	private String Compcode = "";
	private int Mandays = 0;
	private double Hour = 0;
	private String WebActive = "";
	private int dno = 0;
	private int ready_by = 0;
	private int progress = 0;
	private String ready_date = null;
	private String ready_desc = "";
	private int Testing = 0;
	private String TestDate = null;
	private String Targetdate = null;
	private String ProcessingTime = null;
	private double targetHours = 0;
	private int AUTHOR0ISED = 0;
	private String OUTCALLS = "";
	private String STKPICPATH = "";
	private int DeptNo = 0;
	private double RefNo = 0;
	private String CompVersion = "";
	private String CallTakenDate = null;
	private double DevDuetime = 0.0;
	private double Priority = 0;
	private int AgCallNo = 0;
	private int callno = 0;
	private String callentryIP = "";
	private String custcoordinator = "";
	private String custcordinator_name = "";
	private String ready_time = null;
	private String AssignDate = null;
	private String AssignTime = null;
	private String Transferflag = "";
	private String DevstartTime = null;
	private String DiffHours = null;
	private String documentnumber = "";
	private String uniquekey = "";
	private int ticketrecby = 0;
	private String testtime = null;
	private String ticketno = "";
	private String CallTime;
	private String CallsRecvMode = "";
	private double assignpriority = 0;
	private String callcoordinator = "";
	private String Transferdate = null;
	private String CompDesc = "";
	private String Version = "";
	private double DevPriority = 0;
	private String testresult = "";
	private String testedmodules = "";
	private String testedremarks = "";
	private String ExpNature = "";
	private int ProposedDev = 0;
	private String OrgCallTakenDate = null;
	private int GrpRemID = 0;
	private String[] data;
	private String Mnudescription;
	private int MnuNoOfCalls;
	private String imgAttach;
	private String ReceiverName;
	private String DepartmentName;
	private String AuthorizedName;
	private String DevCoOrdName;

	public String getDevCoOrdName() {
		return DevCoOrdName;
	}

	public void setDevCoOrdName(String devCoOrdName) {
		DevCoOrdName = devCoOrdName;
	}

	public String getDepartmentName() {
		return DepartmentName;
	}

	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}

	public String getAuthorizedName() {
		return AuthorizedName;
	}

	public void setAuthorizedName(String authorizedName) {
		AuthorizedName = authorizedName;
	}

	public String getCallTime() {
		return CallTime;
	}

	public String getReceiverName() {
		return ReceiverName;
	}

	public void setReceiverName(String receiverName) {
		ReceiverName = receiverName;
	}

	public String getImgAttach() {
		return imgAttach;
	}

	public void setImgAttach(String imgAttach) {
		this.imgAttach = imgAttach;
	}

	public String getCustName() {
		return custName;
	}

	public String getMnudescription() {
		return Mnudescription;
	}

	public void setMnudescription(String mnudescription) {
		Mnudescription = mnudescription;
	}

	public int getMnuNoOfCalls() {
		return MnuNoOfCalls;
	}

	public void setMnuNoOfCalls(int mnuNoOfCalls) {
		MnuNoOfCalls = mnuNoOfCalls;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<Customer> getLstcustCustomer() {
		return lstCustomer;
	}

	public void setLstcustCustomer(List<Customer> lstCustomer) {
		this.lstCustomer = lstCustomer;
	}

	public String getDeveloperName() {
		return developerName;
	}

	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

	public int getDevCalls() {
		return devCalls;
	}

	public void setDevCalls(int devCalls) {
		this.devCalls = devCalls;
	}

	private String developerName = "";
	private int devCalls = 0;

	public String getCusid() {
		return cusid;
	}

	public void setCusid(String cusid) {
		this.cusid = cusid;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public int getModuleid() {
		return moduleid;
	}

	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
	}

	public String getMoption() {
		return moption;
	}

	public void setMoption(String moption) {
		this.moption = moption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getReceby() {
		return receby;
	}

	public void setReceby(int receby) {
		this.receby = receby;
	}

	public String getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public int getSugto() {
		return sugto;
	}

	public void setSugto(int sugto) {
		this.sugto = sugto;
	}

	public int getCompltedby() {
		return compltedby;
	}

	public void setCompltedby(int compltedby) {
		this.compltedby = compltedby;
	}

	public String getCompon() {
		return compon;
	}

	public void setCompon(String compon) {
		this.compon = compon;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getApptime() {
		return apptime;
	}

	public void setApptime(int apptime) {
		this.apptime = apptime;
	}

	public String getAtsite() {
		return atsite;
	}

	public void setAtsite(String atsite) {
		this.atsite = atsite;
	}

	public String getCallnature() {
		return callnature;
	}

	public void setCallnature(String callnature) {
		this.callnature = callnature;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public int getApprovalBy() {
		return ApprovalBy;
	}

	public void setApprovalBy(int approvalBy) {
		ApprovalBy = approvalBy;
	}

	public String getReady() {
		return Ready;
	}

	public void setReady(String ready) {
		Ready = ready;
	}

	public String getCallsId() {
		return CallsId;
	}

	public void setCallsId(String callsId) {
		CallsId = callsId;
	}

	public int getRemind() {
		return Remind;
	}

	public void setRemind(int remind) {
		Remind = remind;
	}

	public int getModulecode() {
		return Modulecode;
	}

	public void setModulecode(int modulecode) {
		Modulecode = modulecode;
	}

	public String getCompcode() {
		return Compcode;
	}

	public void setCompcode(String compcode) {
		Compcode = compcode;
	}

	public int getMandays() {
		return Mandays;
	}

	public void setMandays(int mandays) {
		Mandays = mandays;
	}

	public double getHour() {
		return Hour;
	}

	public void setHour(double hour) {
		Hour = hour;
	}

	public String getWebActive() {
		return WebActive;
	}

	public void setWebActive(String webActive) {
		WebActive = webActive;
	}

	public int getDno() {
		return dno;
	}

	public void setDno(int dno) {
		this.dno = dno;
	}

	public int getReady_by() {
		return ready_by;
	}

	public void setReady_by(int ready_by) {
		this.ready_by = ready_by;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getReady_date() {
		return ready_date;
	}

	public void setReady_date(String ready_date) {
		this.ready_date = ready_date;
	}

	public String getReady_desc() {
		return ready_desc;
	}

	public void setReady_desc(String ready_desc) {
		this.ready_desc = ready_desc;
	}

	public int getTesting() {
		return Testing;
	}

	public void setTesting(int testing) {
		Testing = testing;
	}

	public String getTestDate() {
		return TestDate;
	}

	public void setTestDate(String testDate) {
		TestDate = testDate;
	}

	public String getTargetdate() {
		return Targetdate;
	}

	public void setTargetdate(String targetdate) {
		Targetdate = targetdate;
	}

	public String getProcessingTime() {
		return ProcessingTime;
	}

	public void setProcessingTime(String processingTime) {
		ProcessingTime = processingTime;
	}

	public double getTargetHours() {
		return targetHours;
	}

	public void setTargetHours(double targetHours) {
		this.targetHours = targetHours;
	}

	public int getAUTHOR0ISED() {
		return AUTHOR0ISED;
	}

	public void setAUTHOR0ISED(int aUTHOR0ISED) {
		AUTHOR0ISED = aUTHOR0ISED;
	}

	public String getOUTCALLS() {
		return OUTCALLS;
	}

	public void setOUTCALLS(String oUTCALLS) {
		OUTCALLS = oUTCALLS;
	}

	public String getSTKPICPATH() {
		return STKPICPATH;
	}

	public void setSTKPICPATH(String sTKPICPATH) {
		STKPICPATH = sTKPICPATH;
	}

	public int getDeptNo() {
		return DeptNo;
	}

	public void setDeptNo(int deptNo) {
		DeptNo = deptNo;
	}

	public double getRefNo() {
		return RefNo;
	}

	public void setRefNo(double refNo) {
		RefNo = refNo;
	}

	public String getCompVersion() {
		return CompVersion;
	}

	public void setCompVersion(String compVersion) {
		CompVersion = compVersion;
	}

	public String getCallTakenDate() {
		return CallTakenDate;
	}

	public void setCallTakenDate(String callTakenDate) {
		CallTakenDate = callTakenDate;
	}

	public double getDevDuetime() {
		return DevDuetime;
	}

	public void setDevDuetime(double devDuetime) {
		DevDuetime = devDuetime;
	}

	public double getPriority() {
		return Priority;
	}

	public void setPriority(double priority) {
		Priority = priority;
	}

	public int getAgCallNo() {
		return AgCallNo;
	}

	public void setAgCallNo(int agCallNo) {
		AgCallNo = agCallNo;
	}

	public int getCallno() {
		return callno;
	}

	public void setCallno(int callno) {
		this.callno = callno;
	}

	public String getCallentryIP() {
		return callentryIP;
	}

	public void setCallentryIP(String callentryIP) {
		this.callentryIP = callentryIP;
	}

	public String getCustcoordinator() {
		return custcoordinator;
	}

	public void setCustcoordinator(String custcoordinator) {
		this.custcoordinator = custcoordinator;
	}

	public String getCustcordinator_name() {
		return custcordinator_name;
	}

	public void setCustcordinator_name(String custcordinator_name) {
		this.custcordinator_name = custcordinator_name;
	}

	public String getReady_time() {
		return ready_time;
	}

	public void setReady_time(String ready_time) {
		this.ready_time = ready_time;
	}

	public String getAssignDate() {
		return AssignDate;
	}

	public void setAssignDate(String assignDate) {
		AssignDate = assignDate;
	}

	public String getAssignTime() {
		return AssignTime;
	}

	public void setAssignTime(String assignTime) {
		AssignTime = assignTime;
	}

	public String getTransferflag() {
		return Transferflag;
	}

	public void setTransferflag(String transferflag) {
		Transferflag = transferflag;
	}

	public String getDevstartTime() {
		return DevstartTime;
	}

	public void setDevstartTime(String devstartTime) {
		DevstartTime = devstartTime;
	}

	public String getDiffHours() {
		return DiffHours;
	}

	public void setDiffHours(String diffHours) {
		DiffHours = diffHours;
	}

	public String getDocumentnumber() {
		return documentnumber;
	}

	public void setDocumentnumber(String documentnumber) {
		this.documentnumber = documentnumber;
	}

	public String getUniquekey() {
		return uniquekey;
	}

	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}

	public int getTicketrecby() {
		return ticketrecby;
	}

	public void setTicketrecby(int ticketrecby) {
		this.ticketrecby = ticketrecby;
	}

	public String getTesttime() {
		return testtime;
	}

	public void setTesttime(String testtime) {
		this.testtime = testtime;
	}

	public String getTicketno() {
		return ticketno;
	}

	public void setTicketno(String ticketno) {
		this.ticketno = ticketno;
	}

	public String getCallTime(String string) {
		return CallTime;
	}

	public void setCallTime(String callTime) {
		CallTime = callTime;
	}

	public String getCallsRecvMode() {
		return CallsRecvMode;
	}

	public void setCallsRecvMode(String callsRecvMode) {
		CallsRecvMode = callsRecvMode;
	}

	public double getAssignpriority() {
		return assignpriority;
	}

	public void setAssignpriority(double assignpriority) {
		this.assignpriority = assignpriority;
	}

	public String getCallcoordinator() {
		return callcoordinator;
	}

	public void setCallcoordinator(String string) {
		this.callcoordinator = string;
	}

	public String getTransferdate() {
		return Transferdate;
	}

	public void setTransferdate(String transferdate) {
		Transferdate = transferdate;
	}

	public String getCompDesc() {
		return CompDesc;
	}

	public void setCompDesc(String compDesc) {
		CompDesc = compDesc;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public double getDevPriority() {
		return DevPriority;
	}

	public void setDevPriority(double devPriority) {
		DevPriority = devPriority;
	}

	public String getTestresult() {
		return testresult;
	}

	public void setTestresult(String testresult) {
		this.testresult = testresult;
	}

	public String getTestedmodules() {
		return testedmodules;
	}

	public void setTestedmodules(String testedmodules) {
		this.testedmodules = testedmodules;
	}

	public String getTestedremarks() {
		return testedremarks;
	}

	public void setTestedremarks(String testedremarks) {
		this.testedremarks = testedremarks;
	}

	public String getExpNature() {
		return ExpNature;
	}

	public void setExpNature(String expNature) {
		ExpNature = expNature;
	}

	public int getProposedDev() {
		return ProposedDev;
	}

	public void setProposedDev(int proposedDev) {
		ProposedDev = proposedDev;
	}

	public String getOrgCallTakenDate() {
		return OrgCallTakenDate;
	}

	public void setOrgCallTakenDate(String orgCallTakenDate) {
		OrgCallTakenDate = orgCallTakenDate;
	}

	public int getGrpRemID() {
		return GrpRemID;
	}

	public void setGrpRemID(int grpRemID) {
		GrpRemID = grpRemID;
	}

	public List<Customer> getLstCustomer() {
		return lstCustomer;
	}

	public void setLstCustomer(List<Customer> lstCustomer) {
		this.lstCustomer = lstCustomer;
	}

	public List<Module> getLstModule() {
		return lstModule;
	}

	public void setLstModule(List<Module> lstModule) {
		this.lstModule = lstModule;
	}

	public List<CallsImages> getLstCallsImages() {
		return lstCallsImages;
	}

	public void setLstCallsImages(List<CallsImages> lstCallsImages) {
		this.lstCallsImages = lstCallsImages;
	}

}
