package com.anicalans.commonutility.anicalans;

import java.io.File;
import java.security.CodeSource;

import javax.swing.JOptionPane;

import com.anicalans.commonutility.common.RunTimeCommands;

public class Anicalans {
	public static void show(int fromModuleId) {
		
		show(fromModuleId, "", "", 0);
		
	}

	public static void show(int fromModuleId, String fileName,String companyCode,int operCode) {
		String path;
		CodeSource codeSource = Anicalans.class.getProtectionDomain().getCodeSource();
		try {
			File sourceFile = new File(codeSource.getLocation().toURI().getPath());
			path = sourceFile.getParentFile().getPath();
			path += File.separator + "AVRAnicalans.jar";
			File jarFile = new File(path);

			String commandLineParams=" "+RunTimeCommands.CALLANICALANSFROMMODULES+" "+fromModuleId;
			
			if (!fileName.isEmpty()) {
				commandLineParams=" " + RunTimeCommands.CALLANICALANSFROMALLBRANCHESMODULES + " " + fromModuleId;
				commandLineParams += " " + fileName + " " + companyCode+" "+operCode;
			}
			
			if (jarFile.exists())
				Runtime.getRuntime().exec("java -jar " + path + commandLineParams);
			
			System.exit(0);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

}
