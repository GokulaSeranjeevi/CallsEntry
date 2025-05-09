package com.jilaba.calls.query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")

public class DailyActivityQuery {

	private StringBuilder sb;

	public String getOperator() {

		sb = new StringBuilder();

		sb.append("Select * from staff Where Active='Y' And designation In(1,2,3) And Staffid<>1");

		return sb.toString();
	}

	public String getGroupId() {

		sb = new StringBuilder();

		sb.append("Select Isnull(Max(GroupId),0)+1 from DailyActivity Where 1=1");

		return sb.toString();
	}

	public String getReport(String atnDate) {

		sb = new StringBuilder();

		sb.append(
				"Select S.Staffname, (Case When D.Leave='N' Then 'No' Else 'Yes' End )Leave , (Case When D.Permission='N' Then 'No' Else 'Yes' End )Permission, \r\n"
						+ " (Case When D.MonthOff='N' Then 'No' Else 'Yes' End )MonthOff,\r\n"
						+ " (Case When D.WeekOff='N' Then 'No' Else 'Yes' End )WeekOff ,\r\n"
						+ " (Case When D.ComboOff='N' Then 'No' Else 'Yes' End )ComboOff ,\r\n"
						+ "Isnull(S1.Staffname,'')Approvedby,Reason,PermissionTime from DailyActivity D \r\n"
						+ "Left Join staff S On S.staffid = D.StaffId \r\n"
						+ "Left Join staff S1 On S1.staffid = D.Approvedby\r\n" + "Where GroupId In(\r\n"
						+ "Select Max(GroupId)GroupId from DailyActivity  A Where Createddate='" + atnDate + "')");

		return sb.toString();
	}

}
