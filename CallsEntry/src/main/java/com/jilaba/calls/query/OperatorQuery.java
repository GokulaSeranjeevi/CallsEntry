package com.jilaba.calls.query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class OperatorQuery {

	private StringBuilder sb;

	public String getOperator() {

		sb = new StringBuilder();

		sb.append("Select * from staff Where Active='Y'");

		return sb.toString();
	}

	public String getOperDetails() {
		sb = new StringBuilder();

		sb.append("Select * from staff Where Staffid= ?");

		return sb.toString();
	}

}
