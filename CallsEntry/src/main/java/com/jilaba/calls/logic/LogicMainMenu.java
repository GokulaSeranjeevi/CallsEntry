package com.jilaba.calls.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.dao.MainManuDao;
import com.jilaba.calls.model.Calls;
import com.jilaba.common.ReturnStatus;

@Component
@Scope("prototype")
public class LogicMainMenu {

	private ReturnStatus returnStatus;

	@Autowired
	private MainManuDao mainManuDao;

	public List<Calls> loadPendingCalls() {

		returnStatus = mainManuDao.loadPendingCalls();

		List<Calls> lstPendingCalls = (List<Calls>) returnStatus.getReturnObject();
		return lstPendingCalls;

	}

	public List<Calls> loadTodayCalls() {
		
		returnStatus = mainManuDao.loadTodayCalls();

		List<Calls> lstTodayCalls = (List<Calls>) returnStatus.getReturnObject();
		return lstTodayCalls;
	}

	public List<Calls> loadModuleCalls() {
		
		returnStatus = mainManuDao.loadModuleCalls();

		List<Calls> lstTodayCalls = (List<Calls>) returnStatus.getReturnObject();
		return lstTodayCalls;
	}

}
