package com.jilaba.calls.query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MainMenuQuery {

	private StringBuilder sb;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
	private LocalDateTime now = LocalDateTime.now();

	public String loadPendingCalls() {
		sb = new StringBuilder();

		sb.append("");
		sb.append("\r\n" + "Select (Case when callnature='E' then 'Error'\r\n"
				+ "when callnature='M' Then 'Modification'\r\n" + "when callnature='G' Then 'General' \r\n"
				+ "when callnature='D' Then 'Development'\r\n" + "when callnature='C' Then 'Clarification'\r\n"
				+ "when callnature='T' Then 'Tallying' else '' End)Description,count(*)NoOfCalls  from  Calls Where Ready=''  \r\n"
				+ "group by callnature\r\n" + "Order by NoOfCalls Desc");

		System.out.println(sb);

		return sb.toString();
	}

	public String loadTodayCalls() {

		sb = new StringBuilder();

		sb.append("");
		sb.append("Select (Case when callnature='E' then 'Error'\r\n" + "when callnature='M' Then 'Modification'\r\n"
				+ "when callnature='G' Then 'General' \r\n" + "when callnature='D' Then 'Development'\r\n"
				+ "when callnature='C' Then 'Clarification'\r\n"
				+ "when callnature='T' Then 'Tallying' else '' End)Description,count(*)NoOfCalls  from  Calls Where Ready='' And cdate='"
				+ formatter.format(now) + "'\r\n" + " group by callnature\r\n" + "Order by NoOfCalls Desc\r\n" + "");
		return sb.toString();
	}

	public String loadModuleCalls() {
		sb = new StringBuilder("");
		sb.append("\r\n" + "Select m.modulename Description, count(*)NoOfCalls  from  Calls C \r\n"
				+ "Left join module m on m.moduleid = C.moduleid\r\n" + "Where Ready=''  \r\n"
				+ "group by C.moduleid,m.modulename\r\n" + "Order by NoOfCalls Desc");

		return sb.toString();
	}

}
