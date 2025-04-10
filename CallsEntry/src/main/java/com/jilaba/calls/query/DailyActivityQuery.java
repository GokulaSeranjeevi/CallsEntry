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

}
