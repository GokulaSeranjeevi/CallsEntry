package com.jilaba.common;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
public class TransactionManager {
	private PlatformTransactionManager transactionManager;
	private TransactionStatus tranStatus;
	private DefaultTransactionDefinition defaultTranDef;

	public boolean begin(JdbcTemplate jdbcTemplate) throws Exception {
		return begin(jdbcTemplate.getDataSource());
	}
	public boolean begin(DataSource dataSource) throws Exception {
		if (null != tranStatus)
			throw new Exception("Transaction Already Begin");
	
		transactionManager = new DataSourceTransactionManager(dataSource);
		defaultTranDef = new DefaultTransactionDefinition();
		defaultTranDef.setIsolationLevel(DefaultTransactionDefinition.ISOLATION_READ_COMMITTED);
		tranStatus = transactionManager.getTransaction(defaultTranDef);
		return true;
	}
	public boolean commit() throws Exception {
		if (null == tranStatus)
			throw new Exception("No Transaction Were Begin To Commit");

		transactionManager.commit(tranStatus);
		tranStatus = null;
		return true;
	}

	public void rollback() {
		if (null != tranStatus) {
			transactionManager.rollback(tranStatus);
			tranStatus = null;
		}
	}
}
