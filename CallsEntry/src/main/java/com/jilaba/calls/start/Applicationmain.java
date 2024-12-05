package com.jilaba.calls.start;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jilaba.calls.forms.FrmLogin;

public class Applicationmain {

	private static Applicationmain app = new Applicationmain();
	public static final String applicationName = "Calls";
	private static JdbcTemplate tranJdbcTemplate;
	private static AbstractApplicationContext abstractApplicationContext;
	private static final int MAJOR = 24;
	private static final int MINOR = 10;
	private static final int REVISION = 29;
	private static final int BUILD = 2;
	public static final String VERSION = BUILD + "." + MAJOR + "." + MINOR + "." + REVISION;
	public static final boolean JTDS = false;

	public final String connAddlSettings = "?allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true&connectionAttributes=program_name:"
			+ applicationName;

	public static String tranDbName = "JCalls";

	public static FrmLogin frmLogin;

	public static FrmLogin getFrmLogin() {
		return frmLogin;
	}

	public static void setFrmLogin(FrmLogin frmLogin) {
		Applicationmain.frmLogin = frmLogin;
	}

	private String url = "";

	public static AbstractApplicationContext getAbstractApplicationContext() {
		return abstractApplicationContext;
	}

	public static void setAbstractApplicationContext(AbstractApplicationContext abstractApplicationContext) {
		Applicationmain.abstractApplicationContext = abstractApplicationContext;
	}

	public static Applicationmain getApp() {
		return app;
	}

	public static JdbcTemplate getTranDBJdbcTemplate() {
		return tranJdbcTemplate;
	}

	public static void setTranDBJdbcTemplate(JdbcTemplate tranJdbcTemplate) {
		Applicationmain.tranJdbcTemplate = tranJdbcTemplate;
	}

	public static void setApp(Applicationmain app) {
		Applicationmain.app = app;
	}

}
