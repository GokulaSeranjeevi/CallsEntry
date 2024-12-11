package com.jilaba.common;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.jilaba.design.CombinedActionListener;
import com.jilaba.design.JilabaDesign;
import com.jilaba.exception.JilabaException;
import com.jilaba.fileresource.Register;
import com.jilaba.fileresource.Server;
import com.jilaba.fonts.JilabaFonts;
import com.jilaba.fonts.JilabaFonts.FontStyle;
import com.jilaba.sqlaction.ConnectionEnum.ServerType;
import com.jilaba.sqlaction.SqlAction;

public class FrmRegComputer extends JDialog {

	private static final long serialVersionUID = 1L;

	private JLabel lblKey, lblCorrect, lblWrong;
	private JButton butRegister, butCancel;
	private JTextField txtKey;
	private ReturnStatus returnStatus = new ReturnStatus();
	private transient ClassLoader classLoader = FrmRegComputer.class.getClassLoader();
	private JDesktopPane desktoppane;
	private transient BufferedImage bgimg, imgcorrect, imgwrong;
	private JPanel panMain;

	private JilUtility jilUtility = new JilUtility();

	private Connection conn;

	private Logger logger = Logger.getLogger(FrmRegComputer.class);
	private JilabaFonts jilabaFonts = new JilabaFonts();

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public FrmRegComputer(Connection conn) throws JilabaException {
		try {
			this.conn = conn;
			setLayout(null);
			setSize(600, 358);
			setUndecorated(true);
			setModalityType(ModalityType.APPLICATION_MODAL);
			JilabaDesign.centerScreen(this);
			if (classLoader.getResource("systemregbg.png") != null) {
				bgimg = ImageIO.read(classLoader.getResource("systemregbg.png"));
			}

			if (classLoader.getResource("correct.png") != null) {
				imgcorrect = ImageIO.read(classLoader.getResource("correct.png"));
			}
			if (classLoader.getResource("wrong.png") != null) {
				imgwrong = ImageIO.read(classLoader.getResource("wrong.png"));
			}

			desktoppane = new JDesktopPane() {
				private static final long serialVersionUID = 1L;

				@Override
				protected void paintComponent(Graphics grapics) {
					super.paintComponent(grapics);
					grapics.drawImage(bgimg, 0, 0, 600, 358, null);

				}
			};

			addWindowListener(new WindowAdapter() {

				@Override
				public void windowOpened(WindowEvent e) {
					txtKey.requestFocus();
				}

			});

			setContentPane(desktoppane);
			returnStatus = formInitialize();
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());

		} catch (JilabaException e) {
			throw e;
		} catch (IOException e) {
			throw new JilabaException(e.getMessage(), e);
		}
	}

	private ReturnStatus formInitialize() {
		try {

			panMain = new JPanel(null);
			panMain.setBounds(0, 0, 600, 358);
			panMain.setBorder(BorderFactory.createEmptyBorder());
			panMain.setOpaque(false);
			panMain.setVisible(true);

			lblKey = new JLabel("Enter Key");
			lblKey.setFont(jilabaFonts.getFont(FontStyle.BOLD, 17));
			lblKey.setBounds(40, 100, 90, 30);
			lblKey.setVisible(true);

			txtKey = new JTextField();
			txtKey.setBounds(40, 180, 270, 25);
			txtKey.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));

			txtKey.setVisible(true);

			lblCorrect = new JLabel(new ImageIcon(imgcorrect));
			lblCorrect.setBounds(315, 180, 50, 25);
			lblCorrect.setVisible(false);

			lblWrong = new JLabel(new ImageIcon(imgwrong));
			lblWrong.setBounds(315, 180, 50, 25);
			lblWrong.setVisible(false);

			butRegister = new JButton("Register");
			butRegister.setMnemonic(KeyEvent.VK_R);
			butRegister.setBounds(115, 230, 90, 25);
			butRegister.setBackground(Color.decode("#0FB9AF"));
			butRegister.setForeground(Color.white);
			butRegister.setFont(jilabaFonts.getFont(15));
			butRegister.setHorizontalTextPosition(JButton.CENTER);
			butRegister.setVerticalTextPosition(JButton.CENTER);
			butRegister.setVisible(true);

			butCancel = new JButton("Cancel");
			butCancel.setMnemonic(KeyEvent.VK_C);
			butCancel.setBounds(220, 230, 90, 25);
			butCancel.setBackground(Color.decode("#FF5151"));
			butCancel.setForeground(Color.white);
			butCancel.setFont(jilabaFonts.getFont(15));
			butCancel.setHorizontalTextPosition(JButton.CENTER);
			butCancel.setVerticalTextPosition(JButton.CENTER);
			butCancel.setVisible(true);

			desktoppane.add(txtKey);
			desktoppane.add(butRegister);
			desktoppane.add(butCancel);
			desktoppane.add(lblCorrect);
			desktoppane.add(lblWrong);

			txtKey.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent arg0) {
					txtKeyKeyListener(arg0);
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					returnStatus = keyValidate();
				}

				@Override
				public void keyPressed(KeyEvent arg0) {
					if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
						butRegister.requestFocus();
						return;
					}
				}
			});

			KeyStroke ctrlVKey = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK);

			ActionListener actCtrlV = txtKey.getActionForKeyStroke(ctrlVKey);
			txtKey.registerKeyboardAction(new CombinedActionListener(actCtrlV, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String strkey = txtKey.getText().trim().toUpperCase();
					strkey = strkey.replaceAll("-", "");
					strkey = strkey.replaceAll(" ", "");

					if (strkey.length() > 16) {
						strkey = strkey.substring(0, 16);
					}

					txtKey.setText(strkey);
					returnStatus = keyValidate();
				}
			}), ctrlVKey, JComponent.WHEN_FOCUSED);

			butRegister.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						returnStatus = dataSave();
						if (!returnStatus.isStatus())
							throw new JilabaException(returnStatus.getDescription());
						returnStatus = new ReturnStatus(true, "Computer/System Registered Successfully");
						dispose();
					} catch (JilabaException e) {
						logger.error(e);
						JOptionPane.showMessageDialog(getContentPane(), e.getMessage());
					}
				}
			});

			butRegister.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent arg0) {

					try {
						if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
							butRegister.doClick();
						} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
							butCancel.requestFocus();
						}
					} catch (JilabaException e) {
						logger.error(e);
						JOptionPane.showMessageDialog(getContentPane(), e.getMessage());
					}
				}

			});

			butCancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					returnStatus = new ReturnStatus(false, "Computer/System Not Registered");
					dispose();
				}
			});
			butCancel.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						butCancel.doClick();
					} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						butRegister.requestFocus();
					}
				}
			});

			return new ReturnStatus(true, "");
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus keyValidate() {
		String strKey = "";
		String strRegKey = "";
		try {
			strKey = txtKey.getText().trim();

			strRegKey = Register.getKeyCode().replaceAll("-", "");

			if (strKey.isEmpty()) {
				lblCorrect.setVisible(false);
				lblWrong.setVisible(true);
				return new ReturnStatus(false, "Enter product key");
			}

			if (strKey.equals(strRegKey)) {
				lblCorrect.setVisible(true);
				lblWrong.setVisible(false);
			} else {
				lblCorrect.setVisible(false);
				lblWrong.setVisible(true);
				throw new JilabaException("Incorrect Key");
			}

			return new ReturnStatus(true, "");
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus dataSave() {
		List<TableComputer> listComputer;
		TableComputer tableComputer;
		String strHostAddress, hostName, finYearFromDate = "";
		int intIpId = 0;
		try {

			returnStatus = keyValidate();
			if (!returnStatus.isStatus()) {
				txtKey.requestFocus();
				throw new JilabaException(returnStatus.getDescription());
			}

			returnStatus = jilUtility.getLocalHostAddress();
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());
			strHostAddress = (String) returnStatus.getReturnObject();

			returnStatus = jilUtility.getLocalHostName();
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());
			hostName = (String) returnStatus.getReturnObject();

			returnStatus = getServerDate();
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());
			java.sql.Date serverDate = (java.sql.Date) returnStatus.getReturnObject();

			returnStatus = jilUtility.getFinYearFromDate(serverDate);
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());
			Date date = (Date) returnStatus.getReturnObject();

			finYearFromDate = dateFormat.format(date);

			listComputer = new ArrayList<TableComputer>();
			tableComputer = new TableComputer();
			tableComputer.setIntIpId(0);
			tableComputer.setIntLanguageCode(0);
			tableComputer.setIntLogId(0);
			tableComputer.setIntOperCode(0);
			tableComputer.setIntXPos(0);
			tableComputer.setIntYPos(0);
			tableComputer.setStrCompCode("");
			tableComputer.setStrFinYearFromDate(finYearFromDate);
			tableComputer.setStrIpAdd(strHostAddress);
			tableComputer.setStrIpName(hostName);
			tableComputer.setStrKeyCode(Register.getKeyCode());

			listComputer.add(tableComputer);

			conn.setAutoCommit(false);

			returnStatus = numberGen();
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());
			intIpId = (Integer) returnStatus.getReturnObject();
			
			returnStatus = checkForDuplicateIp(strHostAddress);
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());

			for (TableComputer o : listComputer) {
				o.setIntIpId(intIpId);
			}
			tableComputer = new TableComputer();
			returnStatus = tableComputer.dataSave(listComputer, new SqlAction());
			if (!returnStatus.isStatus())
				throw new JilabaException(returnStatus.getDescription());

			conn.setAutoCommit(true);

			return new ReturnStatus(true, "");
		} catch (Exception e) {
			try {
				if (!conn.getAutoCommit())
					conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus getServerDate() {
		String query = "";
		Date date = null;
		try {
			Statement statement = conn.createStatement();
			if (ServerType.MSSQL == Server.getServerType()) {
				query = "Select GetDate() as ServerDate ";
			} else if (ServerType.MYSQL == Server.getServerType()) {
				query = "Select CurDate() as ServerDate ";
			}
			ResultSet rs = statement.executeQuery(query);
			while (rs.next())
				date = rs.getDate("ServerDate");

			return new ReturnStatus(true, date);
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private void txtKeyKeyListener(KeyEvent arg0) {
		try {
			if (txtKey.getText().trim().length() >= 16) {
				if (txtKey.getSelectedText() == null) {
					arg0.consume();
					return;
				}
			}

			if (!(((int) arg0.getKeyChar() >= 65) && (((int) arg0.getKeyChar() <= 90)))
					&& !(((int) arg0.getKeyChar() >= 97) && (((int) arg0.getKeyChar() <= 122)))
					&& !(((int) arg0.getKeyChar() >= 48) && (((int) arg0.getKeyChar() <= 57)))) {

				arg0.consume();
				return;

			}
			String strC = String.valueOf(arg0.getKeyChar());
			strC = strC.toUpperCase();
			char c = strC.charAt(0);
			arg0.setKeyChar(c);
		} catch (RuntimeException e) {
			logger.error(e);
		}
	}

	private ReturnStatus numberGen() {
		String sqlQuery = "";
		ResultSet resultSet;
		int intIpId = 0;
		try {
			if (ServerType.MYSQL == Server.getServerType()) {
				sqlQuery = "Select IfNull(Max(IpId),0)+1 IpId From computer";
			} else if (ServerType.MSSQL == Server.getServerType()) {
				sqlQuery = "Select IsNull(Max(IpId),0)+1 IpId From computer";
			} else {
				throw new JilabaException("Server Type not Correct");
			}

			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
			if (!resultSet.next())
				throw new JilabaException("Error From IpId Generation");

			intIpId = resultSet.getInt("IpId");
			if (intIpId == 0)
				throw new JilabaException("Error From IpId Generation");
			return new ReturnStatus(true, intIpId);
		} catch (JilabaException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (SQLException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus checkForDuplicateIp(String ip) {
		String sqlQuery = "";
		ResultSet resultSet;
		try {

			sqlQuery = "Select IpAdd From computer Where IpAdd = '" + ip + "'";

			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
			if (!resultSet.next())
				return new ReturnStatus(true);

			return new ReturnStatus(false, "Ip Details Already Exist For Ip " + ip);
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage());
		}
	}

	public ReturnStatus getReturnStatus() {
		return returnStatus;
	}
}
