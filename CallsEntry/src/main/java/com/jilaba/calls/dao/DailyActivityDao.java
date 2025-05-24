package com.jilaba.calls.dao;

import java.util.List;
import java.util.Map;

import com.jilaba.calls.model.DailyActivity;
import com.jilaba.common.ReturnStatus;

public interface DailyActivityDao {

	public ReturnStatus getOperator();

	public void saveDailyActivity(List<DailyActivity> dailyActivities) throws Exception;

	public ReturnStatus getReport(String atnDate);

	public List<Map<String, Object>> getStaff();

}
