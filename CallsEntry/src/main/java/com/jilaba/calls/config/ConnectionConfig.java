package com.jilaba.calls.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.jilaba.calls.common.CommonMethods;
//import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.start.Applicationmain;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;
import com.jilaba.fileresource.Server;
import com.jilaba.security.Validation;

public class ConnectionConfig {

	private Applicationmain app = Applicationmain.getApp();

	private String url = "";

	@Bean(name = "tranJdbcTemplate")
	private JdbcTemplate trandbTemplate() {

		try {

			DriverManagerDataSource trandbDataSource = new DriverManagerDataSource();
			url = CommonMethods.getUrl(Applicationmain.tranDbName);

			trandbDataSource.setDriverClassName(CommonMethods.getDriverName());
			trandbDataSource.setUrl(url);
			trandbDataSource.setUsername(CommonMethods.strLogin);
			trandbDataSource.setPassword(Validation.decrypt(CommonMethods.strPassword));

			JdbcTemplate tranJdbcTemplate = new JdbcTemplate(trandbDataSource);

			Applicationmain.setTranDBJdbcTemplate(tranJdbcTemplate);
			return tranJdbcTemplate;

		} catch (Exception e) {
			throw new JilabaException("Connection Configured Failed ...!");
		}

	}

}
