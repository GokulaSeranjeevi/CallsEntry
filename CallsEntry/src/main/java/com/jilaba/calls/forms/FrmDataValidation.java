package com.jilaba.calls.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.common.ImageResources;
import com.jilaba.calls.common.LoginCredential;
import com.jilaba.calls.common.TimerJob;
import com.jilaba.calls.logic.LogicDataValidation;
import com.jilaba.calls.model.BranchOffice;
import com.jilaba.calls.model.DataValidation;
import com.jilaba.calls.model.DatabaseName;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.start.Applicationmain;
import com.jilaba.control.JilabaComboBox;
import com.jilaba.control.JilabaSpinner;
import com.jilaba.control.JilabaTextField;
import com.jilaba.control.ListItem;
import com.jilaba.design.ControlResize;
import com.jilaba.fonts.JilabaFonts;
import com.jilaba.fonts.JilabaFonts.FontStyle;

@Component
public class FrmDataValidation extends JFrame implements ActionListener, KeyListener {

	private JPanel panelMain;
	private JPanel panelEntry;
	private JPanel panelView;
	private JPanel panelTitle;
	private JPanel panelLine;
	private JPanel panelDetail;
	private JPanel panelLine2;
	private JPanel panelContent;
	private JPanel panelLine3;
	private JPanel panelButton;
	private JPanel panelViewDetail;
	private JPanel panelCallDate;
	private JPanel panelOrderby;
	private JPanel panelEdit;

	private JLabel lblDevelopedby;
	private JLabel lblVersion;
	private JLabel lblServerIpValue;
	private JLabel lblDateTime;
	private JLabel lblCallMnuHead;
	private JLabel lblUpdateDate;
	private JLabel lblDatabase;
	private JLabel lblBranch;
	private JLabel lblLQuery;
	private JLabel lblReason;
	private JLabel lblUpdateby;
	private JLabel lblViewDatabase;
	private JLabel lblViewBranch;
	private JLabel lblViewUpdateDate;
	private JLabel lblViewUpdateby;
	private JLabel lblViewSearch;
	private JLabel lblOption;
	private JLabel lblCallNature;
	private JLabel lblCallRecMode;
	private JLabel lblDescription;
	private JLabel lblDevCoOrd;
	private JLabel lblPressEsc;
	private JLabel lblOperatorLabel;
	private JLabel lblMinimize;

	private JilabaComboBox<DatabaseName> cmbDatabaseName;
	private JilabaComboBox<BranchOffice> cmbBranchOffice;
	private JilabaComboBox<Operator> cmbUpdateby;

	private JilabaComboBox<DatabaseName> cmbViewDatabaseName;
	private JilabaComboBox<BranchOffice> cmbViewBranchOffice;
	private JilabaComboBox<Operator> cmbViewUpdateby;

	private JilabaSpinner spnUpdateDate;
	private JilabaSpinner spnViewUpdateDate;
	private JCheckBox chkDateCheck;

	private JTextArea txtQuery;
	private JTextArea txtReason;
	private JTextArea txtViewQuery;
	private JilabaTextField txtSearch;

	private JButton btnAdd;
	private JButton btnEntryView;
	private JButton btnSave;
	private JButton btnClear;
	private JButton btnClose;
	private JButton btnCancel;
	private JButton btnImage;
	private JButton btnBack;
	private JButton btnUpdate;
	private JButton btnLoad;

	private JilabaFonts jilabaFonts = new JilabaFonts();
	private ControlResize controlResize;

	private List<DatabaseName> lstDatabaseName;
	private List<BranchOffice> lstBranchOffice;
	private List<Operator> lstUpdateby;
	private Color fontColor1 = Color.decode("#17202A");

	@Autowired
	private LogicDataValidation logicDataValidation;

	private Color color1 = Color.decode("#023020");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#34594c");
	private Color color4 = Color.decode("#ccd5d2");
	private Color color5 = Color.decode("#e4dedf");
	private Color color6 = Color.decode("#b43e69");
	@Autowired
	private CommonMethods commonMethods;

	public FrmDataValidation() {

		setTitle("DataValidation");
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

	private void createListeners() {

		spnUpdateDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtQuery.requestFocus();
				}

			}
		});
		spnViewUpdateDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtSearch.requestFocus();
				}
			}
		});
		chkDateCheck.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					spnViewUpdateDate.requestFocus();
				}
			}
		});

	}

	@PostConstruct
	private void loadDetails() {

		panelEntry.setVisible(true);
		panelView.setVisible(false);

		lblServerIpValue.setText(commonMethods.getIpAddress());
		btnAdd.requestFocus();

		btnAdd.setEnabled(true);
		btnEntryView.setEnabled(true);
		btnSave.setEnabled(false);
		btnClear.setEnabled(false);
		btnCancel.setEnabled(true);
		btnBack.setEnabled(false);
		btnClose.setEnabled(true);

		cmbDatabaseName.removeAllItems();
		cmbBranchOffice.removeAllItems();
		cmbUpdateby.removeAllItems();
		cmbViewDatabaseName.removeAllItems();
		cmbViewBranchOffice.removeAllItems();
		//		cmbViewUpdateby.removeAllItems();

		txtQuery.setText("");
		txtReason.setText("");
		txtSearch.setText("");

		lstDatabaseName = new ArrayList<DatabaseName>();

		lstDatabaseName = logicDataValidation.getDatabaseType();

		for (DatabaseName databaseName : lstDatabaseName) {

			cmbDatabaseName.addListItem(new ListItem(databaseName.getDatabaseName(), databaseName.getDatabaseId()));
			cmbViewDatabaseName.addListItem(new ListItem(databaseName.getDatabaseName(), databaseName.getDatabaseId()));

		}

		lstBranchOffice = new ArrayList<BranchOffice>();

		lstBranchOffice = logicDataValidation.getBranchOffice();

		for (BranchOffice branchOffice : lstBranchOffice) {

			cmbBranchOffice.addListItem(new ListItem(branchOffice.getBranchName(), branchOffice.getBranchId()));
			cmbViewBranchOffice.addListItem(new ListItem(branchOffice.getBranchName(), branchOffice.getBranchId()));

		}

		lstUpdateby = new ArrayList<Operator>();

		lstUpdateby = logicDataValidation.getUpdateby();

		for (Operator oper : lstUpdateby) {

			cmbUpdateby.addListItem(new ListItem(oper.getStaffname(), oper.getStaffid()));
			//			cmbViewUpdateby.addListItem(new ListItem(oper.getStaffname(), oper.getStaffid()));
		}

	}

	private void createControls() {

		panelMain = new JPanel();
		panelMain.setBounds(0, 0, 958, 728);
		panelMain.setLayout(null);
		panelMain.setBackground(color2);

		panelEntry = new JPanel();
		panelEntry.setBounds(0, 0, 958, 650);
		panelEntry.setLayout(null);
		panelEntry.setBackground(color2);

		panelView = new JPanel();
		panelView.setBounds(0, 0, 958, 650);
		panelView.setLayout(null);
		panelView.setBackground(color2);

		panelMain.add(panelTitleInialize());
		panelMain.add(panelLineInialize());
		panelMain.add(panelDetailInitialize());
		panelMain.add(panelLine2Initialize());
		panelEntry.add(panelContentInitialize());
		panelMain.add(panelLine3Inialize());
		panelMain.add(panelButtonInialize());
		panelView.add(panelViewDetail());
		//		panelView.add(panelReadyDetail());

		//		createInputVerifiers();
		//		createActionListners();
		//
		//		imageCompressor = new ImageCompressor();

		getContentPane().add(panelMain);
		panelMain.add(panelEntry);
		panelMain.add(panelView);

	}

	private java.awt.Component panelViewDetail() {
		int lblWidth = 130;
		int lblHeight = 30;
		int txtWidth = 120;
		int txtHeight = 20;

		panelViewDetail = new JPanel();
		panelViewDetail.setBounds(20, panelLine2.getY() + 10, 918, 500);
		panelViewDetail.setLayout(null);
		panelViewDetail.setBackground(color2);
		panelViewDetail.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelViewDetail.setVisible(true);

		lblViewDatabase = new JLabel("Database Type");
		lblViewDatabase.setBounds(20, 25, lblWidth, lblHeight);
		lblViewDatabase.setBackground(color2);
		lblViewDatabase.setForeground(Color.BLACK);
		lblViewDatabase.setFont(CustomFonts.font);
		lblViewDatabase.setVisible(true);
		lblViewDatabase.setFont(CustomFonts.font);

		cmbViewDatabaseName = new JilabaComboBox<DatabaseName>();
		cmbViewDatabaseName.setBounds(lblViewDatabase.getX() + 80, lblViewDatabase.getY() + 5, txtWidth, txtHeight);
		cmbViewDatabaseName.setBackground(color2);
		cmbViewDatabaseName.setFont(CustomFonts.fontCalibriBold);
		cmbViewDatabaseName.setVisible(true);
		cmbViewDatabaseName.addKeyListener(this);

		lblViewBranch = new JLabel("Branch ");
		lblViewBranch.setBounds(cmbViewDatabaseName.getX() + 130, lblViewDatabase.getY(), lblWidth, lblHeight);
		lblViewBranch.setBackground(color2);
		lblViewBranch.setForeground(Color.BLACK);
		lblViewBranch.setFont(CustomFonts.font);
		lblViewBranch.setVisible(true);
		lblViewBranch.setFont(CustomFonts.font);

		cmbViewBranchOffice = new JilabaComboBox<BranchOffice>();
		cmbViewBranchOffice.setBounds(lblViewBranch.getX() + 70, lblViewBranch.getY() + 5, txtWidth, txtHeight);
		cmbViewBranchOffice.setBackground(color2);
		cmbViewBranchOffice.setFont(CustomFonts.fontCalibriBold);
		cmbViewBranchOffice.setVisible(true);
		cmbViewBranchOffice.addKeyListener(this);

		chkDateCheck = new JCheckBox("Date ");
		chkDateCheck.setBounds(cmbViewBranchOffice.getX() + 130, lblViewBranch.getY(), lblWidth, lblHeight);
		chkDateCheck.setBackground(color2);
		chkDateCheck.setForeground(Color.BLACK);
		chkDateCheck.setFont(CustomFonts.font);
		chkDateCheck.setVisible(true);
		chkDateCheck.setFont(CustomFonts.font);

		lblViewUpdateDate = new JLabel("Update Date");
		lblViewUpdateDate.setBounds(cmbViewBranchOffice.getX() + 180, lblViewDatabase.getY(), lblWidth, lblHeight);
		lblViewUpdateDate.setBackground(color2);
		lblViewUpdateDate.setForeground(Color.BLACK);
		lblViewUpdateDate.setFont(CustomFonts.font);
		lblViewUpdateDate.setVisible(true);
		lblViewUpdateDate.setFont(CustomFonts.font);

		spnViewUpdateDate = new JilabaSpinner();
		spnViewUpdateDate.setBounds(lblViewUpdateDate.getX() + 80, lblViewUpdateDate.getY() + 5, 75, 20);
		spnViewUpdateDate.setVisible(true);
		spnViewUpdateDate.setBackground(color2);
		spnViewUpdateDate.addKeyListener(this);
		spnViewUpdateDate.setFont(CustomFonts.fontCalibriBold);

		lblViewSearch = new JLabel("Search");
		lblViewSearch.setBounds(spnViewUpdateDate.getX() + 80, lblViewUpdateDate.getY(), lblWidth, lblHeight);
		lblViewSearch.setBackground(color2);
		lblViewSearch.setForeground(Color.BLACK);
		lblViewSearch.setFont(CustomFonts.font);
		lblViewSearch.setVisible(true);
		lblViewSearch.setFont(CustomFonts.font);

		txtSearch = new JilabaTextField();
		txtSearch.setBounds(lblViewSearch.getX() + 40, lblViewSearch.getY(), lblWidth + 40, lblHeight);
		txtSearch.setBackground(color2);
		txtSearch.setForeground(Color.BLACK);
		txtSearch.setFont(CustomFonts.font);
		txtSearch.setVisible(true);
		txtSearch.setFont(CustomFonts.font);
		txtSearch.addKeyListener(this);

		btnLoad = new JButton("Load");
		btnLoad.setHorizontalAlignment(SwingConstants.CENTER);
		btnLoad.setBounds(txtSearch.getX() + 180, txtSearch.getY() + 5, 50, 20);
		btnLoad.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnLoad.setBackground(color3);
		btnLoad.setForeground(Color.BLACK);
		btnLoad.setMnemonic(KeyEvent.VK_L);
		btnLoad.setMargin(new Insets(0, 0, 0, 0));
		btnLoad.setVisible(true);
		btnLoad.addActionListener(this);
		btnLoad.addKeyListener(this);

		txtViewQuery = new JTextArea(350, 200);
		txtViewQuery.setEditable(true);
		//		txtQuery.setLineWrap(true);
		txtViewQuery.setBounds(lblViewDatabase.getX(), lblViewDatabase.getY() + 50, 1200, 280);
		txtViewQuery.setFont(CustomFonts.fontCalibriBold);
		txtViewQuery.setBackground(color2);
		txtViewQuery.setForeground(Color.BLACK);
		txtViewQuery.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		txtViewQuery.setVisible(true);
		txtViewQuery.addKeyListener(this);

		JScrollPane scrollPane = new JScrollPane(txtViewQuery);
		scrollPane.setBounds(lblViewDatabase.getX(), lblViewDatabase.getY() + 50, 870, 400);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);

		panelView.add(panelViewDetail);
		panelViewDetail.add(lblViewDatabase);
		panelViewDetail.add(cmbViewDatabaseName);
		panelViewDetail.add(lblViewBranch);
		panelViewDetail.add(cmbViewBranchOffice);
		panelViewDetail.add(lblViewUpdateDate);
		panelViewDetail.add(spnViewUpdateDate);
		panelViewDetail.add(chkDateCheck);
		panelViewDetail.add(lblViewSearch);
		panelViewDetail.add(txtSearch);
		panelViewDetail.add(scrollPane);
		panelViewDetail.add(btnLoad);

		return panelViewDetail;

	}

	private java.awt.Component panelContentInitialize() {

		int lblWidth = 150;
		int lblHeight = 50;
		int txtwidth = 120;
		int txtheight = 30;

		panelContent = new JPanel();
		panelContent.setBounds(panelLine2.getX() + 30, panelLine2.getY() + 40, 900, 500);
		panelContent.setLayout(null);
		panelContent.setBackground(color2);
		panelContent.setBorder(BorderFactory.createEtchedBorder(color3, color3));

		lblDatabase = new JLabel("Database Type");
		lblDatabase.setBounds(30, 10, lblWidth, lblHeight);
		lblDatabase.setBackground(color2);
		lblDatabase.setForeground(Color.BLACK);
		lblDatabase.setFont(CustomFonts.font);
		lblDatabase.setVisible(true);
		lblDatabase.setFont(CustomFonts.font);

		cmbDatabaseName = new JilabaComboBox<DatabaseName>();
		cmbDatabaseName.setBounds(lblDatabase.getX() + 120, lblDatabase.getY() + 10, txtwidth, txtheight);
		cmbDatabaseName.setBackground(color2);
		cmbDatabaseName.setFont(CustomFonts.fontCalibriBold);
		cmbDatabaseName.setVisible(true);
		cmbDatabaseName.addKeyListener(this);

		lblBranch = new JLabel("Branch ");
		lblBranch.setBounds(lblDatabase.getX(), lblDatabase.getY() + lblDatabase.getHeight() + 10, lblWidth, lblHeight);
		lblBranch.setBackground(color2);
		lblBranch.setForeground(Color.BLACK);
		lblBranch.setFont(CustomFonts.font);
		lblBranch.setVisible(true);
		lblBranch.setFont(CustomFonts.font);

		cmbBranchOffice = new JilabaComboBox<BranchOffice>();
		cmbBranchOffice.setBounds(cmbDatabaseName.getX(), lblBranch.getY() + 10, txtwidth, txtheight);
		cmbBranchOffice.setBackground(color2);
		cmbBranchOffice.setFont(CustomFonts.fontCalibriBold);
		cmbBranchOffice.setVisible(true);
		cmbBranchOffice.addKeyListener(this);

		lblUpdateDate = new JLabel("Update Date");
		lblUpdateDate.setBounds(lblBranch.getX(), lblBranch.getY() + lblBranch.getHeight() + 10, lblWidth, lblHeight);
		lblUpdateDate.setBackground(color2);
		lblUpdateDate.setForeground(Color.BLACK);
		lblUpdateDate.setFont(CustomFonts.font);
		lblUpdateDate.setVisible(true);
		lblUpdateDate.setFont(CustomFonts.font);

		spnUpdateDate = new JilabaSpinner();
		spnUpdateDate.setBounds(cmbBranchOffice.getX(), lblUpdateDate.getY() + 10, 75, 20);
		spnUpdateDate.setVisible(true);
		spnUpdateDate.setBackground(color2);
		spnUpdateDate.addKeyListener(this);
		spnUpdateDate.setFont(CustomFonts.fontCalibriBold);

		lblLQuery = new JLabel("Query Details ");
		lblLQuery.setBounds(lblUpdateDate.getX(), lblUpdateDate.getY() + lblUpdateDate.getHeight() + 10, lblWidth,
				lblHeight);
		lblLQuery.setBackground(color2);
		lblLQuery.setForeground(Color.BLACK);
		lblLQuery.setFont(CustomFonts.font);
		lblLQuery.setVisible(true);
		lblLQuery.setFont(CustomFonts.font);

		txtQuery = new JTextArea(350, 200);
		txtQuery.setEditable(true);
		//		txtQuery.setLineWrap(true);
		txtQuery.setBounds(spnUpdateDate.getX(), lblLQuery.getY() + 10, 1200, 280);
		txtQuery.setFont(CustomFonts.fontCalibriBold);
		txtQuery.setBackground(color2);
		txtQuery.setForeground(Color.BLACK);
		txtQuery.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		txtQuery.setVisible(true);
		txtQuery.addKeyListener(this);

		JScrollPane scrollPane = new JScrollPane(txtQuery);
		scrollPane.setBounds(spnUpdateDate.getX(), lblLQuery.getY() + 10, 700, 280);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);

		lblUpdateby = new JLabel("Update by ");
		lblUpdateby.setBounds(cmbDatabaseName.getX() + cmbDatabaseName.getWidth() + 70, lblDatabase.getY(), lblWidth,
				lblHeight);
		lblUpdateby.setBackground(color2);
		lblUpdateby.setForeground(Color.BLACK);
		lblUpdateby.setFont(CustomFonts.font);
		lblUpdateby.setVisible(true);
		lblUpdateby.setFont(CustomFonts.font);

		cmbUpdateby = new JilabaComboBox<Operator>();
		cmbUpdateby.setBounds(lblUpdateby.getX() + 120, lblUpdateby.getY() + 10, txtwidth, txtheight);
		cmbUpdateby.setBackground(color2);
		cmbUpdateby.setFont(CustomFonts.fontCalibriBold);
		cmbUpdateby.setVisible(true);
		cmbUpdateby.addKeyListener(this);

		lblReason = new JLabel("Reason ");
		lblReason.setBounds(lblUpdateby.getX(), lblUpdateby.getY() + lblUpdateby.getHeight() + 10, lblWidth, lblHeight);
		lblReason.setBackground(color2);
		lblReason.setForeground(Color.BLACK);
		lblReason.setFont(CustomFonts.font);
		lblReason.setVisible(true);
		lblReason.setFont(CustomFonts.font);

		txtReason = new JTextArea(350, 200);
		txtReason.setEditable(true);
		txtReason.setLineWrap(true);
		txtReason.setBounds(cmbUpdateby.getX(), lblReason.getY() + 10, 390, 100);
		txtReason.setFont(CustomFonts.fontCalibriBold);
		txtReason.setBackground(color2);
		txtReason.setForeground(Color.BLACK);
		txtReason.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		txtReason.setVisible(true);
		txtReason.addKeyListener(this);

		JScrollPane scrollReasonPane = new JScrollPane(txtReason);
		scrollReasonPane.setBounds(cmbUpdateby.getX(), lblReason.getY() + 10, 390, 100);
		scrollReasonPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollReasonPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollReasonPane);

		panelContent.add(lblDatabase);
		panelContent.add(cmbDatabaseName);
		panelContent.add(lblBranch);
		panelContent.add(cmbBranchOffice);
		panelContent.add(lblUpdateDate);
		panelContent.add(spnUpdateDate);
		panelContent.add(lblLQuery);
		panelContent.add(scrollPane);
		panelContent.add(lblUpdateby);
		panelContent.add(cmbUpdateby);
		panelContent.add(lblReason);
		panelContent.add(scrollReasonPane);
		panelEntry.add(panelContent);

		return panelContent;
	}

	private java.awt.Component panelButtonInialize() {
		int btnWidth = 50;
		int btnHeight = 20;
		panelButton = new JPanel();
		panelButton.setBounds(0, panelContent.getY() + panelContent.getHeight() + 10, 958, 40);
		panelButton.setLayout(null);
		panelButton.setBackground(color4);
		//		panelButton.setBorder(BorderFactory.createEtchedBorder(color3, color3));

		btnAdd = new JButton("Add");
		btnAdd.setHorizontalAlignment(SwingConstants.CENTER);
		btnAdd.setBounds(40, 10, btnWidth, btnHeight);
		btnAdd.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnAdd.setMnemonic(KeyEvent.VK_A);
		btnAdd.setBackground(color3);
		btnAdd.setForeground(Color.BLACK);
		btnAdd.setVisible(true);
		btnAdd.addActionListener(this);
		btnAdd.addKeyListener(this);

		btnSave = new JButton("Save");
		btnSave.setHorizontalAlignment(SwingConstants.CENTER);
		btnSave.setBounds(btnAdd.getX() + btnWidth + 80, btnAdd.getY(), btnWidth, btnHeight);
		btnSave.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.setBackground(color3);
		btnSave.setForeground(Color.BLACK);
		btnSave.setVisible(true);
		btnSave.addActionListener(this);
		btnSave.addKeyListener(this);

		btnUpdate = new JButton("Update");
		btnUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		btnUpdate.setBounds(btnAdd.getX() + btnWidth + 80, btnAdd.getY(), btnWidth + 10, btnHeight);
		btnUpdate.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnUpdate.setMnemonic(KeyEvent.VK_U);
		btnUpdate.setBackground(color3);
		btnUpdate.setForeground(Color.BLACK);
		btnUpdate.setVisible(false);
		btnUpdate.addActionListener(this);
		btnUpdate.addKeyListener(this);

		btnEntryView = new JButton("View");
		btnEntryView.setHorizontalAlignment(SwingConstants.CENTER);
		btnEntryView.setBounds(btnSave.getX() + btnWidth + 80, btnSave.getY(), btnWidth, btnHeight);
		btnEntryView.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnEntryView.setMnemonic(KeyEvent.VK_V);
		btnEntryView.setBackground(color3);
		btnEntryView.setForeground(Color.BLACK);
		btnEntryView.setVisible(true);
		btnEntryView.addActionListener(this);
		btnEntryView.addKeyListener(this);

		btnClear = new JButton("Clear");
		btnClear.setHorizontalAlignment(SwingConstants.CENTER);
		btnClear.setBounds(btnEntryView.getX() + btnWidth + 80, btnEntryView.getY(), btnWidth, btnHeight);
		btnClear.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnClear.setMnemonic(KeyEvent.VK_E);
		btnClear.setBackground(color3);
		btnClear.setForeground(Color.BLACK);
		btnClear.setVisible(true);
		btnClear.addActionListener(this);
		btnClear.addKeyListener(this);

		btnCancel = new JButton("Cancel");
		btnCancel.setHorizontalAlignment(SwingConstants.CENTER);
		btnCancel.setBounds(btnClear.getX() + btnWidth + 80, btnClear.getY(), btnWidth, btnHeight);
		btnCancel.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnCancel.setMnemonic(KeyEvent.VK_C);
		btnCancel.setBackground(color3);
		btnCancel.setForeground(Color.BLACK);
		btnCancel.setVisible(true);
		btnCancel.addActionListener(this);
		btnCancel.addKeyListener(this);

		btnBack = new JButton("Back");
		btnBack.setHorizontalAlignment(SwingConstants.CENTER);
		btnBack.setBounds(btnCancel.getX() + btnWidth + 80, btnCancel.getY(), btnWidth, btnHeight);
		btnBack.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnBack.setMnemonic(KeyEvent.VK_B);
		btnBack.setBackground(color3);
		btnBack.setForeground(Color.BLACK);
		btnBack.setVisible(true);
		btnBack.addActionListener(this);
		btnBack.addKeyListener(this);

		btnClose = new JButton("Close");
		btnClose.setHorizontalAlignment(SwingConstants.CENTER);
		btnClose.setBounds(btnBack.getX() + btnWidth + 80, btnBack.getY(), btnWidth, btnHeight);
		btnClose.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnClose.setBackground(color3);
		btnClose.setForeground(Color.BLACK);
		btnClose.setMnemonic(KeyEvent.VK_L);
		btnClose.setMargin(new Insets(0, 0, 0, 0));
		btnClose.setVisible(true);
		btnClose.addActionListener(this);
		btnClose.addKeyListener(this);

		panelMain.add(panelButton);
		panelButton.add(btnAdd);
		panelButton.add(btnSave);
		panelButton.add(btnUpdate);
		panelButton.add(btnEntryView);
		panelButton.add(btnClear);
		panelButton.add(btnCancel);
		panelButton.add(btnBack);
		panelButton.add(btnClose);
		return panelButton;

	}

	private java.awt.Component panelLine3Inialize() {

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

	private java.awt.Component panelLine2Initialize() {
		panelLine2 = new JPanel();
		panelLine2.setBounds(panelDetail.getX(), panelDetail.getY() + 30, 958, 3);
		panelLine2.setLayout(null);
		panelLine2.setBackground(color5);
		panelLine2.setVisible(true);

		panelEntry.add(panelLine2);

		return panelLine2;
	}

	private java.awt.Component panelDetailInitialize() {
		panelDetail = new JPanel();
		panelDetail.setBounds(panelLine.getX(), panelLine.getY() + 30, 958, 30);
		panelDetail.setLayout(null);
		panelDetail.setBackground(color2);
		panelDetail.setVisible(true);

		//		lblHeading = new JLabel("CALLS REGISTER");
		//		lblHeading.setBounds(865, -10, 170, 50);
		//		//		lblHeading.setBounds(panelDetail.getWidth() / 2, panelDetail.getY() / 2, 20, 20);
		//		lblHeading.setFont(jilabaFonts.getFont(FontStyle.BOLD, 23));
		//		lblHeading.setForeground(fontColor1);
		//		lblHeading.setVisible(true);

		lblCallMnuHead = new JLabel("DATA VALIDATION");
		lblCallMnuHead.setBounds(10, -10, 170, 50);
		//		lblHeading.setBounds(panelDetail.getWidth() / 2, panelDetail.getY() / 2, 20, 20);
		lblCallMnuHead.setFont(jilabaFonts.getFont(FontStyle.BOLD, 23));
		lblCallMnuHead.setForeground(fontColor1);
		lblCallMnuHead.setVisible(true);

		//		panelDetail.add(lblHeading);
		panelDetail.add(lblCallMnuHead);
		panelMain.add(panelDetail);
		return panelLine;
	}

	private java.awt.Component panelLineInialize() {
		panelLine = new JPanel();
		panelLine.setBounds(0, 30, 958, 6);
		panelLine.setLayout(null);
		panelLine.setBackground(color3);
		panelLine.setVisible(true);

		panelEntry.add(panelLine);
		return panelLine;
	}

	private java.awt.Component panelTitleInialize() {

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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			if (e.getSource() == cmbDatabaseName) {
				cmbBranchOffice.requestFocus();
			} else if (e.getSource() == cmbBranchOffice) {
				spnUpdateDate.requestFocus();
			} else if (e.getSource() == txtQuery) {
				cmbUpdateby.requestFocus();
			} else if (e.getSource() == cmbUpdateby) {
				txtReason.requestFocus();
			} else if (e.getSource() == txtReason) {
				btnSave.setEnabled(true);
				btnSave.requestFocus();
			} else if (e.getSource() == cmbViewDatabaseName) {
				cmbViewBranchOffice.requestFocus();
			} else if (e.getSource() == cmbViewBranchOffice) {
				chkDateCheck.requestFocus();
			} else if (e.getSource() == chkDateCheck) {
				spnViewUpdateDate.requestFocus();
			} else if (e.getSource() == spnViewUpdateDate) {
				txtSearch.requestFocus();
			} else if (e.getSource() == txtSearch) {
				btnLoad.requestFocus();
			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnClose) {

			setVisible(false);

			FrmMainMenu frmMainMenu = Applicationmain.getAbstractApplicationContext().getBean(FrmMainMenu.class);
			frmMainMenu.setVisible(true);
		} else if (e.getSource() == btnAdd) {
			cmbDatabaseName.requestFocus();
		} else if (e.getSource() == btnSave) {

			DataValidation dataValidation = new DataValidation();

			dataValidation.setDatabaseId(Integer.valueOf(String.valueOf(cmbDatabaseName.getSelectedItemValue())));
			dataValidation.setBranchId(Integer.valueOf(String.valueOf(cmbBranchOffice.getSelectedItemValue())));
			dataValidation.setUpdateDate(String.valueOf(spnUpdateDate.getDateValue()));
			dataValidation.setQueryDesc(txtQuery.getText());
			dataValidation.setUpdateby(Integer.valueOf(String.valueOf(cmbUpdateby.getSelectedItemValue())));
			dataValidation.setReason(txtReason.getText());

			try {
				logicDataValidation.saveDataValidation(dataValidation);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			loadDetails();
			JOptionPane.showMessageDialog(panelMain, "Record Saved ...!");

		} else if (e.getSource() == btnEntryView) {

			panelEntry.setVisible(false);
			panelView.setVisible(true);
			btnBack.setEnabled(true);
			btnClear.setEnabled(true);

		} else if (e.getSource() == btnBack) {
			loadDetails();
		} else if (e.getSource() == btnClear) {

			cmbViewDatabaseName.setSelectedItemValue(1);
			cmbViewBranchOffice.setSelectedItemValue(1);
			txtSearch.setText("");
			chkDateCheck.setSelected(false);

		} else if (e.getSource() == btnLoad) {

//			List<DataValidation> query = logicDataValidation.getData(cmbViewDatabaseName.getSelectedItemValue(),
//					cmbViewBranchOffice.getSelectedItemValue(), spnViewUpdateDate.getDateValue(), txtSearch.getText());
//
//			txtViewQuery.setText(query);

		}

	}

}
