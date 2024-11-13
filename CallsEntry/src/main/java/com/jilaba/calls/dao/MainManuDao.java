package com.jilaba.calls.dao;

import com.jilaba.common.ReturnStatus;

public interface MainManuDao {

	ReturnStatus loadPendingCalls();

	ReturnStatus loadTodayCalls();

	ReturnStatus loadModuleCalls();

}
