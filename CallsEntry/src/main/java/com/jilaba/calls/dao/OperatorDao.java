package com.jilaba.calls.dao;

import com.jilaba.common.ReturnStatus;

public interface OperatorDao {

	public ReturnStatus getOperator();

	public ReturnStatus getOperDetails(Integer staffId);

}
