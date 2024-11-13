package com.jilaba.calls.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.dao.DataValidationDao;
import com.jilaba.calls.model.BranchOffice;
import com.jilaba.calls.model.DataValidation;
import com.jilaba.calls.model.DatabaseName;
import com.jilaba.calls.model.Operator;
import com.jilaba.common.ReturnStatus;

@Component
public class LogicDataValidation {

	private ReturnStatus returnStatus;
	@Autowired
	private DataValidationDao dataValidationDao;

	public List<DatabaseName> getDatabaseType() {

		returnStatus = dataValidationDao.getDatabaseType();
		CommonMethods.catchreturnstatus(returnStatus);

		List<DatabaseName> lstDatabaseName = (List<DatabaseName>) returnStatus.getReturnObject();

		return lstDatabaseName;

	}

	public List<BranchOffice> getBranchOffice() {

		returnStatus = dataValidationDao.getBranchOffice();
		CommonMethods.catchreturnstatus(returnStatus);

		List<BranchOffice> lstBranchOffice = (List<BranchOffice>) returnStatus.getReturnObject();

		return lstBranchOffice;

	}

	public List<Operator> getUpdateby() {

		returnStatus = dataValidationDao.getUpdateby();
		CommonMethods.catchreturnstatus(returnStatus);

		List<Operator> lstUpdateby = (List<Operator>) returnStatus.getReturnObject();

		return lstUpdateby;
	}

	public void saveDataValidation(DataValidation dataValidation) throws Exception {

		dataValidationDao.saveDataValidation(dataValidation);

	}

	public List<DataValidation> getData(Object DataId, Object BranchId, String Date, String text) {

		returnStatus = dataValidationDao.getData(DataId,BranchId,Date,text);
		CommonMethods.catchreturnstatus(returnStatus);

		List<DataValidation> lstDataValidation = (List<DataValidation>) returnStatus.getReturnObject();

		return lstDataValidation;

	}

}
