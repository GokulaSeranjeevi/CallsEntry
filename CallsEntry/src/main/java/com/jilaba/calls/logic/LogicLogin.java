package com.jilaba.calls.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.dao.OperatorDao;
import com.jilaba.calls.model.Operator;
import com.jilaba.common.ReturnStatus;

@Component
@Scope("prototype")
public class LogicLogin {

	private ReturnStatus returnStatus;

	@Autowired
	private OperatorDao operatorDao;

	@SuppressWarnings("unchecked")
	public List<Operator> getOperators() {

		//		returnStatus = new ReturnStatus();

		returnStatus = operatorDao.getOperator();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstOperators = (List<Operator>) returnStatus.getReturnObject();

		return lstOperators;

	}

	public List<Operator> getOperatorDetails(Integer staffId) {

		returnStatus = operatorDao.getOperDetails(staffId);

		List<Operator> lstOperator = (List<Operator>) returnStatus.getReturnObject();

		return lstOperator;

	}

}
