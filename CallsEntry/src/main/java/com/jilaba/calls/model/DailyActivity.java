package com.jilaba.calls.model;

public class DailyActivity {

	private int GroupId;
	private int StaffId;
	private String Leave;
	private String Permission;
	private String MonthOff;
	private String WeekOff;
	private String ComboOff;
	private int Approvedby;
	private String Reason;
	private String PermissionTime;
	private String StaffName;
	private String ApprovedName;

	public String getStaffName() {
		return StaffName;
	}

	public void setStaffName(String staffName) {
		StaffName = staffName;
	}

	public String getApprovedName() {
		return ApprovedName;
	}

	public void setApprovedName(String approvedName) {
		ApprovedName = approvedName;
	}

	public int getGroupId() {
		return GroupId;
	}

	public void setGroupId(int groupId) {
		GroupId = groupId;
	}

	public int getStaffId() {
		return StaffId;
	}

	public void setStaffId(int staffId) {
		StaffId = staffId;
	}

	public String getLeave() {
		return Leave;
	}

	public void setLeave(String leave) {
		Leave = leave;
	}

	public String getPermission() {
		return Permission;
	}

	public void setPermission(String permission) {
		Permission = permission;
	}

	public String getMonthOff() {
		return MonthOff;
	}

	public void setMonthOff(String monthOff) {
		MonthOff = monthOff;
	}

	public String getWeekOff() {
		return WeekOff;
	}

	public void setWeekOff(String weekOff) {
		WeekOff = weekOff;
	}

	public String getComboOff() {
		return ComboOff;
	}

	public void setComboOff(String comboOff) {
		ComboOff = comboOff;
	}

	public int getApprovedby() {
		return Approvedby;
	}

	public void setApprovedby(int approvedby) {
		Approvedby = approvedby;
	}

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}

	public String getPermissionTime() {
		return PermissionTime;
	}

	public void setPermissionTime(String permissionTime) {
		PermissionTime = permissionTime;
	}

}
