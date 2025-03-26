package com.jilaba.calls.forms;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.common.ImageResources;
import com.jilaba.calls.common.LoginCredential;
import com.jilaba.calls.common.TimerJob;
import com.jilaba.calls.logic.LogicLogin;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.start.Applicationmain;
import com.jilaba.control.JilabaColumn;
import com.jilaba.control.JilabaComboBox;
import com.jilaba.control.JilabaSpinner;
import com.jilaba.control.JilabaTable;
import com.jilaba.control.JilabaTextField;
import com.jilaba.control.ListItem;
import com.jilaba.design.ControlResize;
import com.jilaba.fonts.JilabaFonts;
import com.jilaba.fonts.JilabaFonts.FontStyle;

@org.springframework.stereotype.Component
@Scope("prototype")
public class FrmDailyActivity extends JFrame implements ActionListener, KeyListener {

	private JPanel panelMain;
	private JPanel panelEntry;
	private JPanel panelView;
	private JPanel panelTitle;
	private JPanel panelLine;
	private JPanel panelDetail;
	private JPanel panelLine2;
	private JPanel panelContent;
	private JPanel panelLine3;
	private JPanel panelAttendance;
	private JPanel panelViewDetail;
	private JPanel panelCallDate;
	private JPanel panelOrderby;
	private JPanel panelEdit;

	private JLabel lblDevelopedby;
	private JLabel lblVersion;
	private JLabel lblServerIpValue;
	private JLabel lblDateTime;
	private JLabel lblOperatorLabel;
	private JLabel lblCallMnuHead;
	private JLabel lblMinimize;

	private JButton btnAttendance;
	private JButton btnIndividualReport;
	private JButton btnAtnReport;

	private JLabel lblAtnDate;
	private JSpinner spnAtnDate;
	private JilabaTable tblAttendance;
	private JScrollPane scrAtn;
	private JButton btnAtnMark;
	private JButton btnExit;
	private JCheckBox chkAtmMark;
	private JilabaComboBox<Operator> cmbApprovedby;
	private JilabaTextField txtAtn;

	private List<Operator> lstoperator = new ArrayList<Operator>();

	private Color color1 = Color.decode("#F1C232");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#f9e6ac");
	private Color color4 = Color.decode("#cdcdcd");
	private Color color5 = Color.decode("#e4dedf");
	private Color color6 = Color.decode("#b43e69");
	private Color color7 = Color.decode("#ACDDDE");

	private Color fontColor1 = Color.decode("#17202A");
	private Color fontColor2 = Color.decode("#3B3B3B");

	private JilabaFonts jilabaFonts = new JilabaFonts();

	private ControlResize controlResize;

	@Autowired
	private LogicLogin logicLogin;

	public FrmDailyActivity() {

		setTitle("Calls");
		getContentPane().setPreferredSize(new Dimension(958, 728));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		setUndecorated(true);
		pack();

		createControls();
		createListeners();

		controlResize = new ControlResize(this);
		setSize(controlResize.getRectangle().getSize());
		controlResize.reAlignControls();

	}

	private void loadInitialize() {

		tblAttendance.clear();
		spnAtnDate.setValue(new java.util.Date());

	}

	private void enableTextBox() {
		tblAttendance.add(txtAtn);
		int selectedRow = tblAttendance.getSelectedRow();
		int selectedColumn = tblAttendance.getSelectedColumn();
		if (selectedRow != -1 && selectedColumn != -1 && selectedColumn == 8 || selectedColumn == 7) {
			Rectangle cellRect = tblAttendance.getCellRect(selectedRow, selectedColumn, true);
			txtAtn.setBounds(cellRect);
			txtAtn.setVisible(true);
			txtAtn.requestFocus();
			txtAtn.addKeyListener(this);
		}
	}

	private void enableComboBox() {
		tblAttendance.add(cmbApprovedby);

		lstoperator = logicLogin.getOperators();

		for (Operator oper : lstoperator) {

			cmbApprovedby.addListItem(new ListItem(oper.getStaffname()));
		}

		int selectedRow = tblAttendance.getSelectedRow();
		int selectedColumn = tblAttendance.getSelectedColumn();
		if (selectedRow != -1 && selectedColumn != -1 && selectedColumn == 6) {
			Rectangle cellRect = tblAttendance.getCellRect(selectedRow, selectedColumn, true);
			cmbApprovedby.setBounds(cellRect);
			cmbApprovedby.setVisible(true);
			cmbApprovedby.requestFocus();
			cmbApprovedby.addKeyListener(this);
		}
	}

	private void enableCheckBox() {
		tblAttendance.add(chkAtmMark);
		int selectedRow = tblAttendance.getSelectedRow();
		int selectedColumn = tblAttendance.getSelectedColumn();
		if (selectedRow != -1 && selectedColumn != -1 && selectedColumn != 0 && selectedColumn != 6
				&& selectedColumn != 7 && selectedColumn != 8) {
			Rectangle cellRect = tblAttendance.getCellRect(selectedRow, selectedColumn, true);
			chkAtmMark.setBounds(cellRect);
			chkAtmMark.setVisible(true);
			chkAtmMark.requestFocus();
			chkAtmMark.addKeyListener(this);
		}
	}

	private void createListeners() {
		lblMinimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setState(JFrame.ICONIFIED);
			}
		});

		panelContent.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

					setVisible(false);

					FrmMainMenu frmMainMenu = Applicationmain.getAbstractApplicationContext()
							.getBean(FrmMainMenu.class);
					frmMainMenu.setVisible(true);

				}
			}

		});

		tblAttendance.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_SPACE) {

					if (tblAttendance.getSelectedColumn() == 6) {
						enableComboBox();
					} else if (tblAttendance.getSelectedColumn() == 7 || tblAttendance.getSelectedColumn() == 8) {
						enableTextBox();
					} else {
						enableCheckBox();
					}
				}

			}

		});

		chkAtmMark.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					int selectedRow = tblAttendance.getSelectedRow();
					int selectedColumn = tblAttendance.getSelectedColumn();

					tblAttendance.setValueAt("Yes", selectedRow, selectedColumn);
					tblAttendance.setCellColor(selectedRow, selectedColumn, color7);

					chkAtmMark.setVisible(false);
					tblAttendance.requestFocus();

				}

			}
		});

		cmbApprovedby.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					int selectedRow = tblAttendance.getSelectedRow();
					int selectedColumn = tblAttendance.getSelectedColumn();

					tblAttendance.setValueAt(cmbApprovedby.getSelectedItem(), selectedRow, selectedColumn);
//					tblAttendance.setCellColor(selectedRow, selectedColumn, color7);FS

					cmbApprovedby.setVisible(false);
					tblAttendance.requestFocus();

				}

			}
		});
		txtAtn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					int selectedRow = tblAttendance.getSelectedRow();
					int selectedColumn = tblAttendance.getSelectedColumn();

					tblAttendance.setValueAt(txtAtn.getText(), selectedRow, selectedColumn);
//					tblAttendance.setCellColor(selectedRow, selectedColumn, color7);FS

					txtAtn.setText("");
					txtAtn.setVisible(false);
					tblAttendance.requestFocus();

				}

			}
		});
	}

	private void createControls() {

		panelMain = new JPanel();
		panelMain.setBounds(0, 0, 958, 728);
		panelMain.setLayout(null);
		panelMain.setBackground(color2);
		panelMain.addKeyListener(this);

		panelEntry = new JPanel();
		panelEntry.setBounds(0, 90, 958, 590);
		panelEntry.setLayout(null);
		panelEntry.setBackground(color2);
		panelEntry.addKeyListener(this);
//		panelEntry.setBorder(BorderFactory.createEtchedBorder(color3, color3));

		panelView = new JPanel();
		panelView.setBounds(0, 90, 958, 590);
		panelView.setLayout(null);
		panelView.setBackground(color2);
		panelView.setVisible(false);

		panelMain.add(panelTitleInialize());
		panelMain.add(panelLineInialize());
		panelMain.add(panelDetailInitialize());
		panelMain.add(panelLine2Initialize());
		panelEntry.add(panelContentInitalize());
		panelMain.add(panelLine3Inialize());
//		panelMain.add(panelButtonInialize());
		panelView.add(panelViewDetail());
//		panelView.add(panelReadyDetail());

//		createInputVerifiers();
//		createActionListners();

		getContentPane().add(panelMain);
		panelMain.add(panelEntry);
		panelMain.add(panelView);

	}

	private Component panelViewDetail() {

		panelAttendance = new JPanel();
		panelAttendance.setBounds(30, 30, 900, 530);
		panelAttendance.setLayout(null);
		panelAttendance.setBackground(color2);
		panelAttendance.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelAttendance.addKeyListener(this);

		lblAtnDate = new JLabel("Attendance Date");
		lblAtnDate.setBounds(20, 10, 130, 50);
		lblAtnDate.setBackground(color2);
		lblAtnDate.setVisible(true);
		lblAtnDate.setFont(CustomFonts.font20);
		lblAtnDate.setForeground(Color.BLACK);
		lblAtnDate.setBackground(color2);

		spnAtnDate = new JilabaSpinner();
		spnAtnDate.setBounds(lblAtnDate.getX() + 100, lblAtnDate.getY() + 15, 70, 20);
		spnAtnDate.setFont(CustomFonts.fontCalibriBold16);
		spnAtnDate.setForeground(Color.BLACK);
		spnAtnDate.setVisible(true);

		tblAttendance = new JilabaTable(getAttendance());
		tblAttendance.setAutoResizeMode(JilabaTable.AUTO_RESIZE_OFF);
		tblAttendance.getTableHeader().setReorderingAllowed(false);
		tblAttendance.getTableHeader().setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		tblAttendance.setFont(CustomFonts.fontCalibriPlain15);
		tblAttendance.setForeground(Color.BLACK);
		tblAttendance.getTableHeader().setForeground(color6);
		tblAttendance.getTableHeader().setBackground(Color.WHITE);
		tblAttendance.setRowHeight(22);
		tblAttendance.setVisible(true);
		tblAttendance.addKeyListener(this);

		scrAtn = new JScrollPane(tblAttendance);
		scrAtn.setBounds(lblAtnDate.getX(), lblAtnDate.getY() + lblAtnDate.getHeight() + 10, 850, 400);
		scrAtn.getViewport().setBackground(tblAttendance.getTableHeader().getBackground());
		scrAtn.setVisible(true);

		chkAtmMark = new JCheckBox("Yes");
		chkAtmMark.setVisible(true);
		chkAtmMark.setBackground(color2);
		chkAtmMark.setHorizontalAlignment(SwingConstants.CENTER);
		chkAtmMark.setSelected(true);
		chkAtmMark.setFont(CustomFonts.FONT16);
		chkAtmMark.setForeground(Color.BLACK);

		cmbApprovedby = new JilabaComboBox<>();
		cmbApprovedby.setVisible(true);
		cmbApprovedby.setBackground(color2);
		cmbApprovedby.setFont(CustomFonts.FONT16);
		cmbApprovedby.setForeground(Color.BLACK);

		txtAtn = new JilabaTextField();
		txtAtn.setVisible(true);
		txtAtn.setBackground(color2);
		txtAtn.setFont(CustomFonts.FONT16);
		txtAtn.setForeground(Color.BLACK);

		btnAtnMark = new JButton("Attendance Mark");
		btnAtnMark.setHorizontalAlignment(SwingConstants.CENTER);
		btnAtnMark.setBounds(scrAtn.getX() + 330, scrAtn.getY() + scrAtn.getHeight() + 18, 90, 20);
		btnAtnMark.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnAtnMark.setMnemonic(KeyEvent.VK_M);
		btnAtnMark.setBackground(Color.decode("#90EE90"));
		btnAtnMark.setForeground(Color.BLACK);
		btnAtnMark.setVisible(true);
		btnAtnMark.addActionListener(this);
		btnAtnMark.setVerifyInputWhenFocusTarget(false);
		btnAtnMark.setCursor(new Cursor(Cursor.HAND_CURSOR));
		CommonMethods.setIcon(ImageResources.MARK, btnAtnMark);
		btnAtnMark.addActionListener(this);
		btnAtnMark.setIconTextGap(10);
		btnAtnMark.addKeyListener(this);

		btnExit = new JButton("Exit ");
		btnExit.setHorizontalAlignment(SwingConstants.CENTER);
		btnExit.setBounds(btnAtnMark.getX() + 120, btnAtnMark.getY(), 60, 20);
		btnExit.setFont(jilabaFonts.getFont(FontStyle.BOLD, 17));
		btnExit.setMnemonic(KeyEvent.VK_X);
		btnExit.setBackground(Color.decode("#f08080"));
		btnExit.setForeground(Color.BLACK);
		btnExit.setVisible(true);
		btnExit.addActionListener(this);
		btnExit.setVerifyInputWhenFocusTarget(false);
		btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		CommonMethods.setIcon(ImageResources.ATTENDANCEEXIT, btnExit);
		btnExit.addActionListener(this);
		btnExit.setIconTextGap(10);
		btnExit.addKeyListener(this);

		panelAttendance.add(scrAtn);
		panelAttendance.add(lblAtnDate);
		panelAttendance.add(spnAtnDate);
		panelAttendance.add(btnAtnMark);
		panelAttendance.add(btnExit);
		panelMain.add(panelAttendance);

		return panelAttendance;

	}

	private List<JilabaColumn> getAttendance() {

		List<JilabaColumn> jilabaColumnlist = new ArrayList<>();
		jilabaColumnlist.add(new JilabaColumn(" StaffName ", String.class, 250, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn(" Leave", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" Permission ", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" MonthOff ", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" WeekOff ", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" ComboOff ", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" Approvedby ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn(" Reason ", String.class, 300, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn(" PermissionTime ", String.class, 200, JLabel.CENTER));

		return jilabaColumnlist;

	}

	private Component panelLine3Inialize() {

		panelLine3 = new JPanel();
		panelLine3.setBounds(0, 680, 958, 6);
		panelLine3.setLayout(null);
		panelLine3.setBackground(color1);
		panelLine3.setVisible(true);

		lblDevelopedby = new JLabel();
		lblDevelopedby.setBounds(825, panelLine3.getY(), 150, 50);
		lblDevelopedby.setBackground(color1);
		CommonMethods.setIcon(ImageResources.developedBy, lblDevelopedby);
		lblDevelopedby.setVisible(true);

		lblMinimize = new JLabel("");
		lblMinimize.setBounds(lblDevelopedby.getX() + 120, lblDevelopedby.getY() + 8, 10, 40);
		lblMinimize.setBackground(color1);
		lblMinimize.setOpaque(true);
		lblMinimize.setVisible(true);

		panelMain.add(panelLine3);
		panelMain.add(lblDevelopedby);
		panelMain.add(lblMinimize);

		return panelLine3;
	}

	private Component panelContentInitalize() {

		panelContent = new JPanel();
		panelContent.setBounds(300, 110, 350, 350);
		panelContent.setLayout(null);
		panelContent.setBackground(color2);
		panelContent.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelContent.requestFocusInWindow();
		panelContent.setFocusable(true);
		panelContent.addKeyListener(this);

		btnAttendance = new JButton("Attendance Entry");
		btnAttendance.setOpaque(false);
		btnAttendance.setContentAreaFilled(false);
		btnAttendance.setHorizontalTextPosition(JButton.RIGHT);
		btnAttendance.setVerticalTextPosition(JButton.BOTTOM);
		btnAttendance.setFocusable(false);
		btnAttendance.setBorderPainted(true);
		btnAttendance.setForeground(fontColor2);
		btnAttendance.setBorder(BorderFactory.createLineBorder(Color.decode("#D3D3D3")));
		btnAttendance.setMnemonic(KeyEvent.VK_A);
		btnAttendance.setBounds(50, 30, 250, 80);
		btnAttendance.setCursor(new Cursor(Cursor.HAND_CURSOR));
		CommonMethods.setIcon(ImageResources.ATTENDANCE, btnAttendance);
		btnAttendance.addActionListener(this);
		btnAttendance.setIconTextGap(80);
		btnAttendance.setFont(CustomFonts.FONT62);

		btnIndividualReport = new JButton("Individual Report");
		btnIndividualReport.setOpaque(false);
		btnIndividualReport.setContentAreaFilled(false);
		btnIndividualReport.setHorizontalTextPosition(JButton.RIGHT);
		btnIndividualReport.setVerticalTextPosition(JButton.BOTTOM);
		btnIndividualReport.setFocusable(false);
		btnIndividualReport.setBorderPainted(true);
		btnIndividualReport.setForeground(fontColor2);
		btnIndividualReport.setBorder(BorderFactory.createLineBorder(Color.decode("#D3D3D3")));
		btnIndividualReport.setMnemonic(KeyEvent.VK_I);
		btnIndividualReport.setBounds(50, btnAttendance.getY() + btnAttendance.getHeight() + 30, 250, 80);
		btnIndividualReport.setCursor(new Cursor(Cursor.HAND_CURSOR));
		CommonMethods.setIcon(ImageResources.INDIVIDUALREPORT, btnIndividualReport);
		btnIndividualReport.addActionListener(this);
		btnIndividualReport.setIconTextGap(80);
		btnIndividualReport.setFont(CustomFonts.FONT62);
		btnIndividualReport.addActionListener(this);

		btnAtnReport = new JButton("Attendance Report");
		btnAtnReport.setOpaque(false);
		btnAtnReport.setContentAreaFilled(false);
		btnAtnReport.setHorizontalTextPosition(JButton.RIGHT);
		btnAtnReport.setVerticalTextPosition(JButton.BOTTOM);
		btnAtnReport.setFocusable(false);
		btnAtnReport.setBorderPainted(true);
		btnAtnReport.setForeground(fontColor2);
		btnAtnReport.setBorder(BorderFactory.createLineBorder(Color.decode("#D3D3D3")));
		btnAtnReport.setMnemonic(KeyEvent.VK_R);
		btnAtnReport.setBounds(50, btnIndividualReport.getY() + btnIndividualReport.getHeight() + 30, 250, 80);
		btnAtnReport.setCursor(new Cursor(Cursor.HAND_CURSOR));
		CommonMethods.setIcon(ImageResources.ATTENDANCEREPORT, btnAtnReport);
		btnAtnReport.addActionListener(this);
		btnAtnReport.setIconTextGap(70);
		btnAtnReport.setFont(CustomFonts.FONT62);
		btnAtnReport.addActionListener(this);

		panelContent.add(btnAttendance);
		panelContent.add(btnIndividualReport);
		panelContent.add(btnAtnReport);
		panelMain.add(panelContent);

		return panelContent;

	}

	private Component panelLine2Initialize() {
		panelLine2 = new JPanel();
		panelLine2.setBounds(panelDetail.getX(), panelDetail.getY() + 30, 958, 3);
		panelLine2.setLayout(null);
		panelLine2.setBackground(color5);
		panelLine2.setVisible(true);

		panelEntry.add(panelLine2);

		return panelLine2;
	}

	private Component panelDetailInitialize() {
		panelDetail = new JPanel();
		panelDetail.setBounds(panelLine.getX(), panelLine.getY() + 30, 958, 30);
		panelDetail.setLayout(null);
		panelDetail.setBackground(color2);
		panelDetail.setVisible(true);

		// lblHeading = new JLabel("CALLS REGISTER");
		// lblHeading.setBounds(865, -10, 170, 50);
		// // lblHeading.setBounds(panelDetail.getWidth() / 2, panelDetail.getY() / 2,
		// 20, 20);
		// lblHeading.setFont(jilabaFonts.getFont(FontStyle.BOLD, 23));
		// lblHeading.setForeground(fontColor1);
		// lblHeading.setVisible(true);

		lblCallMnuHead = new JLabel("DAILY ACTIVITY");
		lblCallMnuHead.setBounds(10, -10, 170, 50);
		// lblHeading.setBounds(panelDetail.getWidth() / 2, panelDetail.getY() / 2, 20,
		// 20);
		lblCallMnuHead.setFont(jilabaFonts.getFont(FontStyle.BOLD, 23));
		lblCallMnuHead.setForeground(fontColor1);
		lblCallMnuHead.setVisible(true);

		// panelDetail.add(lblHeading);
		panelDetail.add(lblCallMnuHead);
		panelMain.add(panelDetail);
		return panelLine;
	}

	private Component panelLineInialize() {
		panelLine = new JPanel();
		panelLine.setBounds(0, 30, 958, 6);
		panelLine.setLayout(null);
		panelLine.setBackground(color3);
		panelLine.setVisible(true);

		panelEntry.add(panelLine);
		return panelLine;
	}

	private Component panelTitleInialize() {

		panelTitle = new JPanel();
		panelTitle.setBounds(0, 0, 958, 30);
		panelTitle.setLayout(null);
		panelTitle.setBackground(color1);
		panelTitle.setVisible(true);

		lblOperatorLabel = new JLabel(FrmLogin.Operator);
		lblOperatorLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblOperatorLabel.setBounds(10, 5, 120, 22);
		lblOperatorLabel.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		lblOperatorLabel.setForeground(color2);
		CommonMethods.setIcon(ImageResources.SERVERIPICON, lblOperatorLabel);
		lblOperatorLabel.setVisible(true);

		lblVersion = new JLabel(Applicationmain.VERSION);
		lblVersion.setBounds(170, 5, 100, 22);
		lblVersion.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		lblVersion.setForeground(color2);
		CommonMethods.setIcon(ImageResources.VERSIONICON, lblVersion);
		lblVersion.setVisible(true);

		lblServerIpValue = new JLabel(LoginCredential.getLocalIpAdd());
		lblServerIpValue.setBounds(620, 5, 120, 22);
		lblServerIpValue.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		lblServerIpValue.setForeground(color2);
		CommonMethods.setIcon(ImageResources.LOCALIPICON, lblServerIpValue);
		lblServerIpValue.setVisible(true);

		String localdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm:ss a"));
		lblDateTime = new JLabel(localdate);
		lblDateTime.setBounds(780, 5, 170, 22);
		lblDateTime.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		lblDateTime.setForeground(color2);
		lblDateTime.setHorizontalAlignment(JLabel.RIGHT);
		CommonMethods.setIcon(ImageResources.DATEICON, lblDateTime);
		lblDateTime.setVisible(true);
		new Timer().scheduleAtFixedRate(new TimerJob(lblDateTime), Calendar.getInstance().getTime(), 1);

		panelTitle.add(lblOperatorLabel);
		panelTitle.add(lblServerIpValue);
		panelTitle.add(lblDateTime);

		panelTitle.add(lblVersion);

		getContentPane().add(panelTitle);

		return panelTitle;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnAttendance) {

			panelEntry.setVisible(false);
			panelView.setVisible(true);

			lstoperator = logicLogin.getOperators();

			List<Object> lstobject;

			for (Operator oper : lstoperator) {

				lstobject = new ArrayList<>();

				lstobject.add(oper.getStaffname());
				lstobject.add("No");
				lstobject.add("No");
				lstobject.add("No");
				lstobject.add("No");
				lstobject.add("No");
				lstobject.add("");
				lstobject.add("");
				lstobject.add("");

				tblAttendance.addRow(lstobject);

			}

		} else if (e.getSource() == btnExit) {
			panelView.setVisible(false);
			panelEntry.setVisible(true);
			panelContent.setFocusable(true);
			panelContent.requestFocusInWindow();
			loadInitialize();

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getSource() == tblAttendance) {

			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

				btnAtnMark.requestFocus();

			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
