package com.jilaba.versionhandler;

public class VersionHandler {
	public static final String VERSIONKEY = "VERSION";

	public static void showversiondetails(String[] args, String versionValue) {
		if (args[0].equals(VERSIONKEY)) {
			System.out.println(args[0] + ":" + versionValue + "");
			System.exit(0);
		}
	}

}
