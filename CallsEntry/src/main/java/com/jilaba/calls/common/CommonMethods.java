package com.jilaba.calls.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

import com.jilaba.calls.start.Applicationmain;
import com.jilaba.common.ReturnStatus;
import com.jilaba.control.JilabaButton;
import com.jilaba.control.JilabaMenu;
import com.jilaba.control.JilabaMenuItem;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;
import com.jilaba.fileresource.Server;

@Component
public class CommonMethods {

	private static ClassLoader classLoader = CommonMethods.class.getClassLoader();
	private static CodeSource codeSource = CommonMethods.class.getProtectionDomain().getCodeSource();
	private static String strServerName;
	public static String strLogin;
	public static String strPassword;
	private static String strPortNo;
	public String ipAddress;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/*@PostConstruct
	private void IpAddress() {
	
		InetAddress localAddress;
		try {
			localAddress = InetAddress.getLocalHost();
			ipAddress = localAddress.getHostAddress();
		} catch (UnknownHostException e) {
	
			e.printStackTrace();
		}
	}*/

	@PostConstruct
	public void IpAddress() {
		try {

			Enumeration<NetworkInterface> enumNetworkInterface = NetworkInterface.getNetworkInterfaces();
			while (enumNetworkInterface.hasMoreElements()) {
				NetworkInterface networkInterface = enumNetworkInterface.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					InetAddress inetAddress = enumInetAddress.nextElement();
					if (inetAddress.isSiteLocalAddress()) {
						ipAddress = inetAddress.getHostAddress();
						break;
					}
				}
			}

		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	private static void fileRead() {
		try {
			File sourceFile = new File(codeSource.getLocation().toURI().getPath());
			String strFilePath = sourceFile.getParentFile().getPath();
			File file = new File(strFilePath + File.separator + "Server.txt");

			//File file = new File("Server.txt");
			if (!file.exists()) {
				JOptionPane.showMessageDialog(null, "Server File Not Found On Given Path!...");
				System.exit(1);
			}

			List<String> lstString = Files.readAllLines(Paths.get(strFilePath + File.separator + "Server.txt"));

			int count = 1;
			for (String s : lstString) {
				String[] Details = s.split("-");

				switch (count) {
				case 1:
					strServerName = Details[1].trim();
					break;
				case 2:
					strLogin = Details[1].trim();
					break;
				case 3:
					strPassword = Details[1].trim();
					break;
				case 4:
					strPortNo = Details[1].trim();
					break;

				default:
					break;
				}
				count++;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}

	public static final String getUrl(String dbName) {

		String url = "";

		fileRead();

		if (Applicationmain.JTDS)
			url = "jdbc:jtds:sqlserver://" + strServerName;
		else
			url = "jdbc:sqlserver://" + strServerName;

		url += ":" + strPortNo + ";databaseName=" + dbName
				+ ";authentication=NotSpecified;encrypt=false;sslProtocol=TLSv1.2;applicationName=CALLSENTRY";

		return url;
	}

	public void readFile() {

		try {

			CodeSource codeSource = CommonMethods.class.getProtectionDomain().getCodeSource();
			File file = new File(codeSource.getLocation().toURI().getPath());

		} catch (URISyntaxException e) {

			e.printStackTrace();
		}

	}

	public static final String getDriverName() {

		String mssql_driverclassname = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String mysql_driverclassname = "com.mysql.cj.jdbc.Driver";
		String jtds_driverclassname = "net.sourceforge.jtds.jdbc.Driver";

		//		return "com.mysql.cj.jdbc.Driver";
		return Applicationmain.JTDS ? jtds_driverclassname : mssql_driverclassname;
	}

	public static void setIcon(String imgResName, JComponent jComponent) {
		try {
			BufferedImage imgMenu = null;
			if (jComponent == null)
				return;
			if (classLoader.getResource(imgResName) != null)
				imgMenu = ImageIO.read(classLoader.getResource(imgResName));
			if (imgMenu == null)
				return;

			if (jComponent instanceof JilabaMenu)
				((JilabaMenu) jComponent).setIcon(new ImageIcon(imgMenu));
			else if (jComponent instanceof JilabaMenuItem)
				((JilabaMenuItem) jComponent).setIcon(new ImageIcon(imgMenu));
			else if (jComponent instanceof JLabel)
				((JLabel) jComponent).setIcon(new ImageIcon(imgMenu));
			else if (jComponent instanceof JButton)
				((JButton) jComponent).setIcon(new ImageIcon(imgMenu));
			else if (jComponent instanceof JilabaButton)
				((JilabaButton) jComponent).setIcon(new ImageIcon(imgMenu));

		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	@SuppressWarnings("unused")
	public static void catchreturnstatus(ReturnStatus returnStatus) {

		if (!returnStatus.isStatus()) {
			throw new JilabaException(returnStatus.getDescription());
		}
	}

}
