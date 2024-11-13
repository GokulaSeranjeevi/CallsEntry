package com.jilaba.calls.dao;

import java.util.List;

import com.jilaba.calls.model.DataValidation;
import com.jilaba.common.ReturnStatus;

public interface DataValidationDao {

	ReturnStatus getDatabaseType();

	ReturnStatus getBranchOffice();

	ReturnStatus getUpdateby();

	void saveDataValidation(DataValidation dataValidation) throws Exception;

	ReturnStatus getData(Object dataId, Object branchId, String Date, String text2);

}
