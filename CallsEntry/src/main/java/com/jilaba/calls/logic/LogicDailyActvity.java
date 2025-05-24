package com.jilaba.calls.logic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.dao.DailyActivityDao;
import com.jilaba.calls.dao.OperatorDao;
import com.jilaba.calls.model.DailyActivity;
import com.jilaba.calls.model.Operator;
import com.jilaba.common.ReturnStatus;

@Component
public class LogicDailyActvity {

	private ReturnStatus returnStatus;

	@Autowired
	private DailyActivityDao dailyActivityDao;

	public List<Operator> getOperators() {

		returnStatus = dailyActivityDao.getOperator();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstOperators = (List<Operator>) returnStatus.getReturnObject();

		return lstOperators;

	}

	public void saveDailyActivity(List<DailyActivity> dailyActivities) throws Exception {

		dailyActivityDao.saveDailyActivity(dailyActivities);
	}

	public List<DailyActivity> getAttendance(String AtnDate) {

		returnStatus = dailyActivityDao.getReport(AtnDate);
		CommonMethods.catchreturnstatus(returnStatus);

		List<DailyActivity> lstdailyActivity = (List<DailyActivity>) returnStatus.getReturnObject();

		return lstdailyActivity;
	}

	public List<Map<String, Object>> getStaff() {
		return dailyActivityDao.getStaff();
	}

}
