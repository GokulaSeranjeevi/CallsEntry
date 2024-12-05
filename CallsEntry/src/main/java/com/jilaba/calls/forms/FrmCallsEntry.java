package com.jilaba.calls.forms;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.common.ImageResources;
import com.jilaba.calls.common.LoginCredential;
import com.jilaba.calls.common.TimerJob;
import com.jilaba.calls.logic.LogicCallsEntry;
import com.jilaba.calls.logic.LogicDevCalls;
import com.jilaba.calls.logic.LogicReadyCalls;
import com.jilaba.calls.model.Calls;
import com.jilaba.calls.model.CallsImages;
import com.jilaba.calls.model.CustStaff;
import com.jilaba.calls.model.Customer;
import com.jilaba.calls.model.Department;
import com.jilaba.calls.model.Module;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.start.Applicationmain;
import com.jilaba.common.ReturnStatus;
import com.jilaba.control.JTextFieldEnum.TextInputType;
import com.jilaba.control.JilabaColumn;
import com.jilaba.control.JilabaComboBox;
import com.jilaba.control.JilabaSpinner;
import com.jilaba.control.JilabaTable;
import com.jilaba.control.JilabaTextField;
import com.jilaba.control.ListItem;
import com.jilaba.design.ControlResize;
import com.jilaba.fonts.JilabaFonts;
import com.jilaba.fonts.JilabaFonts.FontStyle;
import com.jilaba.imagemanager.ImageCompressor;

@org.springframework.stereotype.Component
@Scope("prototype")
public class FrmCallsEntry extends JFrame implements ActionListener, KeyListener, FocusListener {
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
	private JLabel lblCallDate;
	private JLabel lblCallFrom;
	private JLabel lblLastCallNo;
	private JLabel lblLastCallNoVal;
	private JLabel lblClient;
	private JLabel lblDepartment;
	private JLabel lblCustCoOrd;
	private JLabel lblCallCoOrd;
	private JLabel lblModule;
	private JLabel lblRefNo;
	private JLabel lblCompanyCode;
	private JLabel lblOption;
	private JLabel lblCallNature;
	private JLabel lblCallRecMode;
	private JLabel lblDescription;
	private JLabel lblDevCoOrd;
	private JLabel lblPressEsc;
	private JLabel lblOperatorLabel;
	private JLabel lblMinimize;
	private JLabel lblEditCallno;

	private JLabel lblCallFromDate;
	private JLabel lblCallToDate;;
	private JLabel lblCall;
	private JLabel lblDeptAutorize;
	private JLabel lblViewDevCoOrd;
	private JLabel lblCallNo;
	private JLabel lblRecFrom;
	private JLabel lblViewCustCoOrd;
	private JLabel lblViewCallCoOrd;

	private JilabaComboBox<Operator> cmbCallFrom;
	private JilabaComboBox<Customer> cmbCustomer;
	private JilabaComboBox<Department> cmbDepartment;
	private JilabaComboBox<Operator> cmbCustStaff;
	private JilabaComboBox<CustStaff> cmbStaff;
	private JilabaComboBox<Module> cmbModule;
	private JilabaComboBox<String> cmbBranch;
	private JilabaComboBox<String> cmbNature;
	private JilabaComboBox<String> cmbRecvMode;
	private JilabaComboBox<Operator> cmbDevCoOrd;

	private List<Operator> callFrom;
	private List<Customer> client;
	private List<Department> department;
	private List<CustStaff> custCoOrd;
	private List<Operator> callCoOrd;
	private List<Module> module;
	private List<Operator> devCoOrd;

	private JilabaComboBox<Operator> cmbViewDevCoOrd;
	private JilabaComboBox<Operator> cmbViewRecby;
	private JilabaComboBox<Operator> cmbViewCallCoOrd;
	private JilabaComboBox<Operator> cmbViewCustCoOrd;
	private JilabaComboBox<Customer> cmbViewClient;
	private JilabaComboBox<Operator> cmbDeptAuthorize;
	private JilabaComboBox<Department> cmbViewDepartment;
	private JilabaComboBox<Module> cmbViewModule;

	private JilabaTextField txtRefNo;
	private JilabaTextField txtOption;

	// private JilabaTextField txtDesc;

	private JTextArea txtDesc;

	private JButton btnAdd;
	private JButton btnEntryView;
	private JButton btnSave;
	private JButton btnEdit;
	private JButton btnExit;
	private JButton btnCancel;
	private JButton btnImage;
	private JButton btnBack;
	private JButton btnUpdate;

	private JButton btnView;
	private JButton btnClear;

	private ButtonGroup btnGroup = new ButtonGroup();
	private ButtonGroup btnOrderbyGroup = new ButtonGroup();

	private Operator operator;
	private FrmImageDialog frmImageDialog;
	private Calls calls;

	private String callNo;
	private JilabaSpinner spnCallDate;

	private Color color1 = Color.decode("#3eb489");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#73bda2");
	private Color color4 = Color.decode("#cdcdcd");
	private Color color5 = Color.decode("#e4dedf");
	private Color color6 = Color.decode("#b43e69");
	private Color color7 = Color.decode("#ADD8E6");

	private Color fontColor1 = Color.decode("#17202A");

	private ControlResize controlResize;

	private JilabaFonts jilabaFonts = new JilabaFonts();

	private JFileChooser jFileChooser;
	private File previewFile;
	private JLabel lblPath;
	private ReturnStatus returnStatus;
	private JLabel lblPreview;
	private byte[] repImageFile;
	private ImageCompressor imageCompressor;
	private ClassLoader classLoader = FrmCallsEntry.class.getClassLoader();
	private ImageIcon previewIcon;
	private JPanel panelImage;
	private byte[] lblImage1 = null;
	private byte[] lblImage2 = null;
	private byte[] lblImage3 = null;
	private byte[] lblImage4 = null;
	@Autowired
	private LogicDevCalls logicDevCalls;

	private boolean blnFrmDevCall = false;
	private JilabaTextField txtCallNo;

	private JRadioButton rdpAsOnDate;
	private JRadioButton rdpBetweenDate;

	private JilabaTable tblEditCalls;
	private JScrollPane scrEditCalls;

	private JRadioButton rdpNone;
	private JRadioButton rdpModule;
	private JRadioButton rdpRecby;
	private JRadioButton rdpClient;
	private JRadioButton rdpCallDate;
	private JRadioButton rdpCallCoOrd;
	private JRadioButton rdpCallNo;

	private JilabaSpinner spnCallFromDate;
	private JilabaSpinner spnCallToDate;

	private String strCallFromDate;
	private String strCallToDate;
	private String strOrderby;
	private int strViewRecby;
	private int strViewCallCoOrd;
	private int strViewCustCoOrd;
	private int strViewDevCoOrd;
	private int strViewClient;
	private int strViewDeptAuthorize;
	private int strViewDepartment;
	private int strViewModule;

	private List<Calls> lstCalls;

	private boolean blnFrmCallEdit;
	private boolean blnAsOnDate = false;
	private JilabaTextField txtEditCallNo;
	private boolean blnNewImageAdd = false;

	@Autowired
	private LogicCallsEntry logicCallsEntry;

	@Autowired
	private LogicReadyCalls logicReadyCalls;
	@Autowired
	private CommonMethods commonMethods;

	public FrmCallsEntry() {

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

	private void createListeners() {

		lblMinimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setState(JFrame.ICONIFIED);
			}
		});

		spnCallFromDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (blnAsOnDate == true) {
						cmbViewRecby.requestFocus();
					} else {
						spnCallToDate.requestFocus();
					}

				}

			}

		});
		spnCallToDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					cmbViewRecby.requestFocus();

				}

			}

		});

		tblEditCalls.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_I) {
					btnImageCall();
				}

			}
		});

	}

	private void btnImageCall() {

		String callNo = (String.valueOf(tblEditCalls.getModel().getValueAt(tblEditCalls.getSelectedRow(), 0)));
		CallsImages callsImages = logicDevCalls.getImages(callNo);

		blnFrmDevCall = true;
		FrmImageDialog frmImageDialog = Applicationmain.getAbstractApplicationContext().getBean(FrmImageDialog.class,
				new Object[] { getContentPane() });
		frmImageDialog.booleanAssign(false, blnFrmDevCall, (callNo), callsImages);
		frmImageDialog.setVisible(true);

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
		panelEntry.add(panelContentInitoalize());
		panelMain.add(panelLine3Inialize());
		panelMain.add(panelButtonInialize());
		panelView.add(panelViewDetail());
		panelView.add(panelReadyDetail());

		createInputVerifiers();
		createActionListners();

		imageCompressor = new ImageCompressor();

		getContentPane().add(panelMain);
		panelMain.add(panelEntry);
		panelMain.add(panelView);

	}

	private Component panelReadyDetail() {

		int lblWidth = 130;
		int lblHeight = 30;
		int txtWidth = 120;
		int txtHeight = 20;

		panelEdit = new JPanel();
		panelEdit.setBounds(20, panelViewDetail.getY() + panelViewDetail.getHeight() + 10, 918, 400);
		panelEdit.setLayout(null);
		panelEdit.setBackground(color2);
		panelEdit.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelEdit.setVisible(true);

		tblEditCalls = new JilabaTable(getEditCalls());
		tblEditCalls.setAutoResizeMode(JilabaTable.AUTO_RESIZE_OFF);
		tblEditCalls.getTableHeader().setReorderingAllowed(false);
		tblEditCalls.getTableHeader().setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		tblEditCalls.setFont(CustomFonts.fontCalibriPlain15);
		tblEditCalls.setForeground(Color.BLACK);
		tblEditCalls.getTableHeader().setForeground(color6);
		tblEditCalls.getTableHeader().setBackground(Color.WHITE);
		tblEditCalls.setRowHeight(22);
		tblEditCalls.setVisible(true);
		tblEditCalls.addKeyListener(this);

		scrEditCalls = new JScrollPane(tblEditCalls);
		scrEditCalls.setBounds(10, 10, 900, 360);
		scrEditCalls.getViewport().setBackground(tblEditCalls.getTableHeader().getBackground());
		scrEditCalls.setVisible(true);

		lblPressEsc = new JLabel(" I - Image Details To Show    ");
		lblPressEsc.setHorizontalAlignment(SwingConstants.LEFT);
		lblPressEsc.setBounds(tblEditCalls.getX() + 20, 380, 900, 20);
		lblPressEsc.setBackground(color2);
		lblPressEsc.setForeground(Color.BLACK);
		lblPressEsc.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		lblPressEsc.setVisible(true);

		panelView.add(panelEdit);
		panelEdit.add(scrEditCalls);
		panelEdit.add(lblPressEsc);
		return panelEdit;

	}

	private List<JilabaColumn> getEditCalls() {

		List<JilabaColumn> jilabaColumnlist = new ArrayList<>();
		jilabaColumnlist.add(new JilabaColumn("CallNo", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Date ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Client Name ", String.class, 200, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Description ", String.class, 1000, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Option ", String.class, 250, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Recd By ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("call Nature ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Authorized ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Department ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Module ", String.class, 100, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Cust Co-Ord ", String.class, 100, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Co-Ord ", String.class, 100, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Ref No ", String.class, 100, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Developer Co-Ord ", String.class, 100, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Image1 ", Byte.class, 5, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Image2 ", Byte.class, 5, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Image3 ", Byte.class, 5, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Image4 ", Byte.class, 5, JLabel.LEFT));

		return jilabaColumnlist;

	}

	private Component panelViewDetail() {

		int lblWidth = 130;
		int lblHeight = 30;
		int txtWidth = 120;
		int txtHeight = 20;

		panelViewDetail = new JPanel();
		panelViewDetail.setBounds(20, panelLine2.getY() + 10, 918, 120);
		panelViewDetail.setLayout(null);
		panelViewDetail.setBackground(color2);
		panelViewDetail.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelViewDetail.setVisible(true);

		rdpAsOnDate = new JRadioButton("As On Date");
		rdpAsOnDate.setBounds(10, 10, txtWidth, txtHeight);
		rdpAsOnDate.setBackground(color2);
		rdpAsOnDate.setFont(CustomFonts.fontCalibriBold);
		rdpAsOnDate.setVisible(true);
		rdpAsOnDate.setSelected(false);
		rdpAsOnDate.addKeyListener(this);

		rdpBetweenDate = new JRadioButton("Between Date");
		rdpBetweenDate.setBounds(rdpAsOnDate.getX() + rdpAsOnDate.getWidth(), 10, txtWidth, txtHeight);
		rdpBetweenDate.setBackground(color2);
		rdpBetweenDate.setBackground(color2);
		rdpBetweenDate.setFont(CustomFonts.fontCalibriBold);
		rdpBetweenDate.setVisible(true);
		rdpBetweenDate.setSelected(false);
		rdpBetweenDate.addKeyListener(this);

		lblCall = new JLabel("Call Date");
		lblCall.setBounds(rdpAsOnDate.getX(), rdpAsOnDate.getY() + rdpAsOnDate.getHeight() + 10, lblWidth, lblHeight);
		lblCall.setBackground(color2);
		lblCall.setForeground(Color.BLACK);
		lblCall.setFont(CustomFonts.font);
		lblCall.setVisible(true);
		lblCall.addKeyListener(this);

		panelCallDate = new JPanel();
		panelCallDate.setBounds(rdpAsOnDate.getX(), rdpAsOnDate.getY() + rdpAsOnDate.getHeight() + 30, 200, 50);
		panelCallDate.setLayout(null);
		panelCallDate.setBackground(color2);
		panelCallDate.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		panelCallDate.setVisible(true);

		lblCallFromDate = new JLabel("From Date");
		lblCallFromDate.setBounds(10, 0, lblWidth, lblHeight);
		lblCallFromDate.setBackground(color2);
		lblCallFromDate.setVisible(true);
		lblCallFromDate.setFont(jilabaFonts.getFont(14));

		spnCallFromDate = new JilabaSpinner();
		spnCallFromDate.setBounds(lblCallFromDate.getX(), lblCallFromDate.getY() + lblCallFromDate.getHeight() - 5, 75,
				20);
		spnCallFromDate.setVisible(true);
		spnCallFromDate.setBackground(color2);
		spnCallFromDate.setFont(CustomFonts.fontCalibriBold);
		spnCallFromDate.addKeyListener(this);

		lblCallToDate = new JLabel("To Date");
		lblCallToDate.setBounds(rdpBetweenDate.getX() - 10, 0, lblWidth, lblHeight);
		lblCallToDate.setBackground(color2);
		lblCallToDate.setVisible(true);
		lblCallToDate.setFont(jilabaFonts.getFont(14));

		spnCallToDate = new JilabaSpinner();
		spnCallToDate.setBounds(lblCallToDate.getX(), lblCallToDate.getY() + lblCallToDate.getHeight() - 5, 75, 20);
		spnCallToDate.setVisible(true);
		spnCallToDate.setBackground(color2);
		spnCallToDate.addKeyListener(this);
		spnCallToDate.setFont(CustomFonts.fontCalibriBold);

		// DSsd

		lblRecFrom = new JLabel("Received From");
		lblRecFrom.setBounds(rdpBetweenDate.getX() + rdpBetweenDate.getWidth(), rdpBetweenDate.getY() - 5, lblWidth,
				lblHeight);
		lblRecFrom.setBackground(color2);
		lblRecFrom.setForeground(Color.BLACK);
		lblRecFrom.setFont(CustomFonts.font);
		lblRecFrom.setVisible(true);

		cmbViewRecby = new JilabaComboBox<Operator>();
		cmbViewRecby.setBounds(lblRecFrom.getX() + 80, lblRecFrom.getY(), txtWidth, txtHeight);
		cmbViewRecby.setBackground(color2);
		cmbViewRecby.setFont(CustomFonts.fontCalibriBold);
		cmbViewRecby.setVisible(true);
		cmbViewRecby.addKeyListener(this);

		lblViewCallCoOrd = new JLabel("Call Co-Ord");
		lblViewCallCoOrd.setBounds(lblRecFrom.getX(), lblRecFrom.getY() + lblRecFrom.getHeight(), lblWidth, lblHeight);
		lblViewCallCoOrd.setBackground(color2);
		lblViewCallCoOrd.setVisible(true);
		lblViewCallCoOrd.setFont(CustomFonts.font);
		lblViewCallCoOrd.setForeground(Color.BLACK);

		cmbViewCallCoOrd = new JilabaComboBox<Operator>();
		cmbViewCallCoOrd.setBounds(lblViewCallCoOrd.getX() + 80, lblViewCallCoOrd.getY(), txtWidth, txtHeight);
		cmbViewCallCoOrd.setBackground(color2);
		cmbViewCallCoOrd.setFont(CustomFonts.fontCalibriBold);
		cmbViewCallCoOrd.setVisible(true);
		cmbViewCallCoOrd.addKeyListener(this);

		lblViewCustCoOrd = new JLabel("Cust Co-Ord");
		lblViewCustCoOrd.setBounds(lblViewCallCoOrd.getX(), lblViewCallCoOrd.getY() + lblViewCallCoOrd.getHeight(),
				lblWidth, lblHeight);
		lblViewCustCoOrd.setBackground(color2);
		lblViewCustCoOrd.setVisible(true);
		lblViewCustCoOrd.setFont(CustomFonts.font);
		lblViewCustCoOrd.setForeground(Color.BLACK);

		cmbViewCustCoOrd = new JilabaComboBox<Operator>();
		cmbViewCustCoOrd.setBounds(lblViewCustCoOrd.getX() + 80, lblViewCustCoOrd.getY(), txtWidth, txtHeight);
		cmbViewCustCoOrd.setBackground(color2);
		cmbViewCustCoOrd.setFont(CustomFonts.fontCalibriBold);
		cmbViewCustCoOrd.setVisible(true);
		cmbViewCustCoOrd.addKeyListener(this);

		lblViewDevCoOrd = new JLabel("Dev Co-Ord");
		lblViewDevCoOrd.setBounds(lblViewCustCoOrd.getX(), lblViewCustCoOrd.getY() + lblViewCustCoOrd.getHeight(),
				lblWidth, lblHeight);
		lblViewDevCoOrd.setBackground(color2);
		lblViewDevCoOrd.setForeground(Color.BLACK);
		lblViewDevCoOrd.setFont(CustomFonts.font);
		lblViewDevCoOrd.setVisible(true);
		lblViewDevCoOrd.setFont(CustomFonts.font);

		cmbViewDevCoOrd = new JilabaComboBox<Operator>();
		cmbViewDevCoOrd.setBounds(lblViewDevCoOrd.getX() + 80, lblViewDevCoOrd.getY(), txtWidth, txtHeight);
		cmbViewDevCoOrd.setBackground(color2);
		cmbViewDevCoOrd.setFont(CustomFonts.fontCalibriBold);
		cmbViewDevCoOrd.setVisible(true);
		cmbViewDevCoOrd.addKeyListener(this);

		lblClient = new JLabel("Client");
		lblClient.setBounds(cmbViewRecby.getX() + cmbViewRecby.getWidth() + 30, cmbViewRecby.getY(), lblWidth,
				lblHeight);
		lblClient.setBackground(color2);
		lblClient.setForeground(Color.BLACK);
		lblClient.setFont(CustomFonts.font);
		lblClient.setVisible(true);
		lblClient.setFont(CustomFonts.font);

		lblDeptAutorize = new JLabel("Dept Authorize");
		lblDeptAutorize.setBounds(lblClient.getX(), lblClient.getY() + lblClient.getHeight(), lblWidth, lblHeight);
		lblDeptAutorize.setBackground(color2);
		lblDeptAutorize.setForeground(Color.BLACK);
		lblDeptAutorize.setFont(CustomFonts.font);
		lblDeptAutorize.setVisible(true);
		lblDeptAutorize.setFont(CustomFonts.font);

		lblDepartment = new JLabel("Department");
		lblDepartment.setBounds(lblDeptAutorize.getX(), lblDeptAutorize.getY() + lblDeptAutorize.getHeight(), lblWidth,
				lblHeight);
		lblDepartment.setBackground(color2);
		lblDepartment.setForeground(Color.BLACK);
		lblDepartment.setFont(CustomFonts.font);
		lblDepartment.setVisible(true);
		lblDepartment.setFont(CustomFonts.font);

		lblModule = new JLabel("Module");
		lblModule.setBounds(lblDepartment.getX(), lblDepartment.getY() + lblDepartment.getHeight(), lblWidth,
				lblHeight);
		lblModule.setBackground(color2);
		lblModule.setForeground(Color.BLACK);
		lblModule.setFont(CustomFonts.font);
		lblModule.setVisible(true);
		lblModule.setFont(CustomFonts.font);

		cmbViewClient = new JilabaComboBox<Customer>();
		cmbViewClient.setBounds(lblClient.getX() + 100, lblClient.getY(), txtWidth, txtHeight);
		cmbViewClient.setBackground(color2);
		cmbViewClient.setFont(CustomFonts.fontCalibriBold);
		cmbViewClient.setVisible(true);
		cmbViewClient.addKeyListener(this);

		cmbDeptAuthorize = new JilabaComboBox<Operator>();
		cmbDeptAuthorize.setBounds(cmbViewClient.getX(), lblDeptAutorize.getY(), txtWidth, txtHeight);
		cmbDeptAuthorize.setBackground(color2);
		cmbDeptAuthorize.setFont(CustomFonts.fontCalibriBold);
		cmbDeptAuthorize.setVisible(true);
		cmbDeptAuthorize.addKeyListener(this);

		cmbViewDepartment = new JilabaComboBox<Department>();
		cmbViewDepartment.setBounds(cmbDeptAuthorize.getX(), lblDepartment.getY(), txtWidth, txtHeight);
		cmbViewDepartment.setBackground(color2);
		cmbViewDepartment.setFont(CustomFonts.fontCalibriBold);
		cmbViewDepartment.setVisible(true);
		cmbViewDepartment.addKeyListener(this);

		cmbViewModule = new JilabaComboBox<Module>();
		cmbViewModule.setBounds(cmbViewDepartment.getX(), lblModule.getY(), txtWidth, txtHeight);
		cmbViewModule.setBackground(color2);
		cmbViewModule.setFont(CustomFonts.fontCalibriBold);
		cmbViewModule.setVisible(true);
		cmbViewModule.addKeyListener(this);

		lblCallNo = new JLabel("Call No");
		lblCallNo.setBounds(cmbViewClient.getX() + cmbViewClient.getWidth() + 20, cmbViewClient.getY(), lblWidth,
				lblHeight);
		lblCallNo.setBackground(color2);
		lblCallNo.setForeground(Color.BLACK);
		lblCallNo.setFont(CustomFonts.font);
		lblCallNo.setVisible(true);

		txtCallNo = new JilabaTextField();
		txtCallNo.setBounds(lblCallNo.getX() + 60, lblCallNo.getY() + 5, 100, txtHeight);
		txtCallNo.setVisible(true);
		txtCallNo.setFont(CustomFonts.fontCalibriPlain15);
		txtCallNo.addKeyListener(this);
		txtCallNo.setMaxLength(5);
		txtCallNo.setTextType(TextInputType.NUMBER);

		panelOrderby = new JPanel();
		panelOrderby.setBounds(lblCallNo.getX(), lblCallNo.getY() + lblCallNo.getY() + 30, 190, 50);
		panelOrderby.setLayout(null);
		panelOrderby.setBackground(color2);
		panelOrderby.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		panelOrderby.setVisible(true);

		rdpNone = new JRadioButton("None");
		rdpNone.setBounds(5, 5, 40, 20);
		rdpNone.setBackground(color2);
		rdpNone.setVisible(true);
		rdpNone.setFont(jilabaFonts.getFont(14));
		rdpNone.setSelected(true);
		rdpNone.addKeyListener(this);

		rdpModule = new JRadioButton("Module");
		rdpModule.setBounds(rdpNone.getX() + rdpNone.getWidth(), 5, 50, 20);
		rdpModule.setBackground(color2);
		rdpModule.setVisible(true);
		rdpModule.setFont(jilabaFonts.getFont(14));
		rdpModule.setSelected(false);
		rdpModule.addKeyListener(this);

		rdpRecby = new JRadioButton("Recby");
		rdpRecby.setBounds(rdpModule.getX() + rdpModule.getWidth(), 5, 50, 20);
		rdpRecby.setBackground(color2);
		rdpRecby.setVisible(true);
		rdpRecby.setFont(jilabaFonts.getFont(14));
		rdpRecby.setSelected(false);
		rdpRecby.addKeyListener(this);

		rdpClient = new JRadioButton("Client");
		rdpClient.setBounds(rdpRecby.getX() + rdpRecby.getWidth(), 5, 40, 20);
		rdpClient.setBackground(color2);
		rdpClient.setVisible(true);
		rdpClient.setFont(jilabaFonts.getFont(14));
		rdpClient.setSelected(false);
		rdpClient.addKeyListener(this);

		rdpCallDate = new JRadioButton("CallDate");
		rdpCallDate.setBounds(rdpNone.getX(), rdpNone.getY() + rdpNone.getHeight(), 50, 20);
		rdpCallDate.setBackground(color2);
		rdpCallDate.setVisible(true);
		rdpCallDate.setFont(jilabaFonts.getFont(14));
		rdpCallDate.setSelected(false);
		rdpCallDate.addKeyListener(this);

		rdpCallCoOrd = new JRadioButton("Call Co-Ord");
		rdpCallCoOrd.setBounds(rdpCallDate.getX() + rdpCallDate.getWidth(), rdpCallDate.getY(), 60, 20);
		rdpCallCoOrd.setBackground(color2);
		rdpCallCoOrd.setVisible(true);
		rdpCallCoOrd.setFont(jilabaFonts.getFont(14));
		rdpCallCoOrd.setSelected(false);
		rdpCallCoOrd.addKeyListener(this);

		rdpCallNo = new JRadioButton("CallNo");
		rdpCallNo.setBounds(rdpCallCoOrd.getX() + rdpCallCoOrd.getWidth(), rdpCallCoOrd.getY(), 60, 20);
		rdpCallNo.setBackground(color2);
		rdpCallNo.setVisible(true);
		rdpCallNo.setFont(jilabaFonts.getFont(14));
		rdpCallNo.setSelected(false);
		rdpCallNo.addKeyListener(this);

		btnView = new JButton("View");
		btnView.setBounds(panelOrderby.getX() + 30, panelOrderby.getY() + panelOrderby.getHeight() + 5, 50, 20);
		btnView.setBackground(color3);
		btnView.setForeground(color2);
		btnView.setVisible(true);
		btnView.setMnemonic(KeyEvent.VK_V);
		btnView.addKeyListener(this);
		btnView.addActionListener(this);

		btnClear = new JButton("Clear");
		btnClear.setBounds(btnView.getX() + 80, btnView.getY(), 50, 20);
		btnClear.setBackground(color3);
		btnClear.setForeground(color2);
		btnClear.setVisible(true);
		btnClear.setMnemonic(KeyEvent.VK_C);
		btnClear.addKeyListener(this);
		btnClear.addActionListener(this);

		btnGroup.add(rdpAsOnDate);
		btnGroup.add(rdpBetweenDate);
		panelViewDetail.add(rdpAsOnDate);
		panelViewDetail.add(rdpBetweenDate);

		panelViewDetail.add(lblCall);
		panelViewDetail.add(panelCallDate);
		panelCallDate.add(lblCallFromDate);
		panelCallDate.add(lblCallToDate);
		panelCallDate.add(spnCallFromDate);
		panelCallDate.add(spnCallToDate);
		panelViewDetail.add(lblRecFrom);
		panelViewDetail.add(lblViewCallCoOrd);
		panelViewDetail.add(lblViewCustCoOrd);
		panelViewDetail.add(lblViewDevCoOrd);
		panelViewDetail.add(cmbViewRecby);
		panelViewDetail.add(cmbViewCallCoOrd);
		panelViewDetail.add(cmbViewCustCoOrd);
		panelViewDetail.add(cmbViewDevCoOrd);
		panelViewDetail.add(lblClient);
		panelViewDetail.add(lblDeptAutorize);
		panelViewDetail.add(lblDepartment);
		panelViewDetail.add(lblModule);
		panelViewDetail.add(cmbViewClient);
		panelViewDetail.add(cmbDeptAuthorize);
		panelViewDetail.add(cmbViewDepartment);
		panelViewDetail.add(cmbViewModule);
		panelViewDetail.add(lblCallNo);
		panelViewDetail.add(txtCallNo);
		panelViewDetail.add(panelOrderby);
		btnOrderbyGroup.add(rdpNone);
		btnOrderbyGroup.add(rdpRecby);
		btnOrderbyGroup.add(rdpModule);
		btnOrderbyGroup.add(rdpClient);
		btnOrderbyGroup.add(rdpCallDate);
		btnOrderbyGroup.add(rdpCallCoOrd);
		btnOrderbyGroup.add(rdpCallNo);
		panelOrderby.add(rdpNone);
		panelOrderby.add(rdpRecby);
		panelOrderby.add(rdpModule);
		panelOrderby.add(rdpClient);
		panelOrderby.add(rdpCallDate);
		panelOrderby.add(rdpCallCoOrd);
		panelOrderby.add(rdpCallNo);
		panelViewDetail.add(btnView);
		panelViewDetail.add(btnClear);

		panelView.add(panelViewDetail);

		return panelViewDetail;
	}

	private Component panelButtonInialize() {
		int btnWidth = 50;
		int btnHeight = 20;
		panelButton = new JPanel();
		panelButton.setBounds(panelContent.getX(), panelContent.getY() + panelContent.getHeight() + 10, 850, 40);
		panelButton.setLayout(null);
		panelButton.setBackground(color2);
		// panelButton.setBorder(BorderFactory.createEtchedBorder(color3, color3));

		btnAdd = new JButton("Add");
		btnAdd.setHorizontalAlignment(SwingConstants.CENTER);
		btnAdd.setBounds(20, 10, btnWidth, btnHeight);
		btnAdd.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnAdd.setMnemonic(KeyEvent.VK_A);
		btnAdd.setBackground(color3);
		btnAdd.setForeground(Color.BLACK);
		btnAdd.setVisible(true);
		btnAdd.addActionListener(this);
		btnAdd.setVerifyInputWhenFocusTarget(false);
		btnAdd.addKeyListener(this);
		btnAdd.addFocusListener(this);

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
		btnEntryView.setVerifyInputWhenFocusTarget(false);
		btnEntryView.addKeyListener(this);

		btnEdit = new JButton("Edit");
		btnEdit.setHorizontalAlignment(SwingConstants.CENTER);
		btnEdit.setBounds(btnEntryView.getX() + btnWidth + 80, btnEntryView.getY(), btnWidth, btnHeight);
		btnEdit.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnEdit.setMnemonic(KeyEvent.VK_E);
		btnEdit.setBackground(color3);
		btnEdit.setForeground(Color.BLACK);
		btnEdit.setVisible(true);
		btnEdit.addActionListener(this);
		btnEdit.setVerifyInputWhenFocusTarget(false);
		btnEdit.addKeyListener(this);

		btnCancel = new JButton("Cancel");
		btnCancel.setHorizontalAlignment(SwingConstants.CENTER);
		btnCancel.setBounds(btnEdit.getX() + btnWidth + 80, btnEdit.getY(), btnWidth, btnHeight);
		btnCancel.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnCancel.setMnemonic(KeyEvent.VK_C);
		btnCancel.setBackground(color3);
		btnCancel.setForeground(Color.BLACK);
		btnCancel.setVisible(true);
		btnCancel.addActionListener(this);
		btnCancel.setVerifyInputWhenFocusTarget(false);
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
		btnBack.setVerifyInputWhenFocusTarget(false);
		btnBack.addKeyListener(this);

		btnExit = new JButton("Close");
		btnExit.setHorizontalAlignment(SwingConstants.CENTER);
		btnExit.setBounds(btnBack.getX() + btnWidth + 80, btnBack.getY(), btnWidth, btnHeight);
		btnExit.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnExit.setBackground(color3);
		btnExit.setForeground(Color.BLACK);
		btnExit.setMnemonic(KeyEvent.VK_L);
		btnExit.setMargin(new Insets(0, 0, 0, 0));
		btnExit.setVisible(true);
		btnExit.addActionListener(this);
		btnExit.setVerifyInputWhenFocusTarget(false);
		btnExit.addKeyListener(this);

		panelMain.add(panelButton);
		panelButton.add(btnAdd);
		panelButton.add(btnSave);
		panelButton.add(btnUpdate);
		panelButton.add(btnEntryView);
		panelButton.add(btnEdit);
		panelButton.add(btnCancel);
		panelButton.add(btnBack);
		panelButton.add(btnExit);
		return panelButton;

	}

	private void createActionListners() {

		btnImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// FrmImageDialog frmImageDialog;
				if (blnFrmCallEdit == true) {

					int row = tblEditCalls.getSelectedRow();

					if (tblEditCalls.getValueAt(row, 14) != null || tblEditCalls.getValueAt(row, 15) != null
							|| tblEditCalls.getValueAt(row, 16) != null || tblEditCalls.getValueAt(row, 17) != null) {

						CallsImages callsImages = logicReadyCalls
								.getImages(String.valueOf(tblEditCalls.getValueAt(row, 0).toString()));
						frmImageDialog = Applicationmain.getAbstractApplicationContext().getBean(FrmImageDialog.class,
								new Object[] { getContentPane() });
						frmImageDialog.booleanAssign(blnFrmCallEdit, false,
								(String.valueOf(tblEditCalls.getValueAt(row, 0).toString())), callsImages);
						frmImageDialog.setVisible(true);

						blnNewImageAdd = false;

					} else {

						frmImageDialog = Applicationmain.getAbstractApplicationContext().getBean(FrmImageDialog.class,
								new Object[] { getContentPane() });
						frmImageDialog.setVisible(true);

						blnNewImageAdd = true;

					}
					blnFrmCallEdit = false;
				} else {

					if (frmImageDialog.lblImage1 == null || frmImageDialog.lblImage2 == null
							|| frmImageDialog.lblImage3 == null || frmImageDialog.lblImage4 == null) {

						frmImageDialog = Applicationmain.getAbstractApplicationContext().getBean(FrmImageDialog.class,
								new Object[] { getContentPane() });
						frmImageDialog.setVisible(true);

						// lblImage1 = frmImageDialog.getLblImage1Path();
						// lblImage2 = frmImageDialog.getLblImage2Path();
						// lblImage3 = frmImageDialog.getLblImage3Path();
						// lblImage4 = frmImageDialog.getLblImage4Path();

					} else {
						// frmImageDialog =
						// Applicationmain.getAbstractApplicationContext().getBean(FrmImageDialog.class,
						// new Object[] { getContentPane() });
						frmImageDialog.setVisible(true);

						frmImageDialog.reloadImage();

						/*
						 * frmImageDialog =
						 * Applicationmain.getAbstractApplicationContext().getBean(FrmImageDialog.class,
						 * new Object[] { getContentPane() }); frmImageDialog.setVisible(true);
						 * lblImage1 = frmImageDialog.getLblImage1Path(); lblImage2 =
						 * frmImageDialog.getLblImage2Path(); lblImage3 =
						 * frmImageDialog.getLblImage3Path(); lblImage4 =
						 * frmImageDialog.getLblImage4Path();
						 */ }

				}

				lblImage1 = frmImageDialog.getLblImage1Path();
				lblImage2 = frmImageDialog.getLblImage2Path();
				lblImage3 = frmImageDialog.getLblImage3Path();
				lblImage4 = frmImageDialog.getLblImage4Path();

				System.out.println(lblImage4);

				/*
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * jFileChooser = new JFileChooser(); int retVal =
				 * jFileChooser.showOpenDialog(getContentPane()); try { if (retVal ==
				 * JFileChooser.APPROVE_OPTION) { previewFile = jFileChooser.getSelectedFile();
				 * // lblPath.setText(previewFile.getAbsolutePath()); returnStatus =
				 * changePreview(previewFile); if (!returnStatus.isStatus()) throw new
				 * JilabaException(returnStatus.getDescription()); } else { returnStatus =
				 * loadDefaultPreview(); CommonValues.returnStatusCatch(returnStatus); } } catch
				 * (Exception e2) { JOptionPane.showMessageDialog(getContentPane(),
				 * e2.getMessage(), "Repair", JOptionPane.ERROR_MESSAGE); loadDefaultPreview();
				 * }
				 */
			}
		});

		cmbModule.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				module = logicCallsEntry
						.getModule(Integer.valueOf(String.valueOf(cmbDepartment.getSelectedItemValue())));

				cmbModule.removeAllItems();

				for (Module mod : module) {
					cmbModule.addListItem(new ListItem(mod.getModuleName(), mod.getModuleId()));
					cmbViewModule.addListItem(new ListItem(mod.getModuleName(), mod.getModuleId()));

				}

			}
		});

	}

	/*
	 * private ReturnStatus changePreview(File file) { try { BufferedImage bi = new
	 * BufferedImage(lblPreview.getWidth(), lblPreview.getHeight(),
	 * BufferedImage.TYPE_INT_RGB); Graphics2D g = bi.createGraphics(); Image img =
	 * ImageIO.read(file); g.drawImage(img, 0, 0, lblPreview.getWidth(),
	 * lblPreview.getHeight(), null); g.dispose(); lblPreview.setIcon(new ImageIcon(
	 * img.getScaledInstance(lblPreview.getWidth(), lblPreview.getHeight(),
	 * BufferedImage.SCALE_SMOOTH)));
	 * 
	 * ByteArrayOutputStream baos = new ByteArrayOutputStream(); ImageIO.write(bi,
	 * "png", baos);
	 * 
	 * repImageFile = baos.toByteArray();
	 * 
	 * repImageFile = imageCompressor.compressBytes(repImageFile);
	 * 
	 * return new ReturnStatus(true);
	 * 
	 * } catch (Exception e) { return new ReturnStatus(false, e.getMessage()); } }
	 */
	/*
	 * private ReturnStatus loadDefaultPreview() {
	 * 
	 * try {
	 * 
	 * if (classLoader.getResource(ImageResource.IMAGEPREVIEW) != null &&
	 * previewIcon == null) { BufferedImage bi = new
	 * BufferedImage(lblPreview.getWidth(), lblPreview.getHeight(),
	 * BufferedImage.TYPE_INT_RGB); Graphics2D g = bi.createGraphics(); Image img =
	 * ImageIO.read(classLoader.getResource(ImageResource.IMAGEPREVIEW));
	 * g.drawImage(img, 0, 0, lblPreview.getWidth(), lblPreview.getHeight(), null);
	 * g.dispose();
	 * 
	 * previewIcon = new ImageIcon(img.getScaledInstance(lblPreview.getWidth(),
	 * lblPreview.getHeight(), BufferedImage.SCALE_SMOOTH)); }
	 * 
	 * lblPreview.setIcon(previewIcon);
	 * 
	 * // previewFile = null; // lblPath.setText("");
	 * 
	 * return new ReturnStatus(true);
	 * 
	 * } catch (IOException e) { return new ReturnStatus(false,
	 * ErrorHandling.handleError(e)); } }
	 */

	private void createInputVerifiers() {

		txtOption.setInputVerifier(new InputVerifier() {

			@Override
			public boolean verify(JComponent input) {

				if (txtOption.getText().isEmpty()) {

					JOptionPane.showMessageDialog(panelEntry, "Option Should Not be Empty!");

					txtOption.requestFocus();

					return false;

				}
				return true;

			}
		});

		txtDesc.setInputVerifier(new InputVerifier() {

			@Override
			public boolean verify(JComponent input) {

				if (txtDesc.getText().trim().isEmpty()) {

					JOptionPane.showMessageDialog(panelEntry, "Description Should Not be Empty !");
					txtDesc.requestFocus();

					return false;

				}

				return true;
			}
		});

	}

	private static void disableAllComponents(JPanel panel) {
		// Iterate through all components in the panel
		for (Component component : panel.getComponents()) {
			// Disable the component
			component.setEnabled(false);
			// If it's a JTextField, you can also make it non-editable
			if (component instanceof JTextField) {
				((JTextField) component).setEditable(false);
			}
			if (component instanceof JTextArea) {
				((JTextArea) component).setEditable(false);
			}
		}
	}

	private static void enableAllComponents(JPanel panel) {
		// Iterate through all components in the panel
		for (Component component : panel.getComponents()) {
			// Disable the component
			component.setEnabled(true);
			// If it's a JTextField, you can also make it non-editable
			if (component instanceof JTextField) {
				((JTextField) component).setEditable(true);
			}
			if (component instanceof JTextArea) {
				((JTextArea) component).setEditable(true);
			}
		}
	}

	@PostConstruct
	@Transactional
	private ReturnStatus loadDetails() {

		panelContent.setEnabled(false);
		lblServerIpValue.setText(commonMethods.getIpAddress());
		cmbCallFrom.removeAllItems();
		cmbCustomer.removeAllItems();
		cmbDepartment.removeAllItems();
		cmbCustStaff.removeAllItems();
		cmbStaff.removeAllItems();
		cmbModule.removeAllItems();
		cmbBranch.removeAllItems();
		txtRefNo.setText("");
		txtOption.setText("");
		cmbNature.removeAllItems();
		txtDesc.setText("");
		cmbDevCoOrd.removeAllItems();
		txtCallNo.setText("");

		btnAdd.setEnabled(true);
		btnSave.setEnabled(false);
		btnEntryView.setEnabled(true);
		btnEdit.setEnabled(false);
		btnCancel.setEnabled(false);
		btnBack.setEnabled(false);
		btnExit.setEnabled(true);
		btnUpdate.setVisible(false);
		btnSave.setVisible(true);
		btnImage.setEnabled(false);

		btnAdd.requestFocus();
		callNo = logicCallsEntry.getLastCallNo();
		lblLastCallNoVal.setText(callNo);

		spnCallDate.setValue(new java.util.Date());
		spnCallFromDate.setValue(new java.util.Date());
		spnCallToDate.setValue(new java.util.Date());

		panelEntry.setVisible(true);
		panelView.setVisible(false);
		tblEditCalls.clear();

		cmbDetailsLoad();

		cmbBranch.addListItem(new ListItem("AVR"));
		cmbBranch.addListItem(new ListItem("SVR"));

		cmbNature.addListItem(new ListItem("Error"));
		cmbNature.addListItem(new ListItem("Modification"));
		cmbNature.addListItem(new ListItem("Clarification"));
		cmbNature.addListItem(new ListItem("Development"));
		cmbNature.addListItem(new ListItem("General"));
		cmbNature.addListItem(new ListItem("Tallying"));

		devCoOrd = logicCallsEntry.getDevCoOrd();

		for (Operator DevCo : devCoOrd) {
			cmbDevCoOrd.addListItem(new ListItem(DevCo.getStaffname(), DevCo.getStaffid()));
			cmbViewDevCoOrd.addListItem(new ListItem(DevCo.getStaffname(), DevCo.getStaffid()));
		}

		disableAllComponents(panelContent);

		return new ReturnStatus(true);

	}

	private void cmbDetailsLoad() {

		cmbViewRecby.removeAllItems();
		cmbViewCallCoOrd.removeAllItems();
		cmbViewClient.removeAllItems();
		cmbViewCustCoOrd.removeAllItems();
		cmbViewDepartment.removeAllItems();
		cmbViewModule.removeAllItems();
		cmbDeptAuthorize.removeAllItems();
		cmbCustomer.removeAllItems();
		cmbCallFrom.removeAllItems();
		cmbViewDevCoOrd.removeAllItems();
		cmbCustStaff.removeAllItems();
		cmbDepartment.removeAllItems();
		cmbStaff.removeAllItems();

		cmbViewRecby.addListItem(new ListItem("All", 0));
		cmbViewCallCoOrd.addListItem(new ListItem("All", 0));
		cmbViewCustCoOrd.addListItem(new ListItem("All", 0));
		cmbViewDevCoOrd.addListItem(new ListItem("All", 0));
		cmbViewClient.addListItem(new ListItem("All", 0));
		cmbDeptAuthorize.addListItem(new ListItem("All", 0));
		cmbViewDepartment.addListItem(new ListItem("All", 0));
		cmbViewModule.addListItem(new ListItem("All", 0));

		callFrom = logicCallsEntry.getCallFrom();
		for (Operator oper : callFrom) {
			// cmbCallFrom.addListItem(new ListItem(oper.getStaffname(),
			// oper.getStaffid()));
			// cmbViewRecby.addListItem(new ListItem(oper.getStaffname(),
			// oper.getStaffid()));
			// cmbViewCallCoOrd.addListItem(new ListItem(oper.getStaffname(),
			// oper.getStaffid()));
			// cmbViewCustCoOrd.addListItem(new ListItem(oper.getStaffname(),
			// oper.getStaffid()));

			cmbDeptAuthorize.addListItem(new ListItem(oper.getStaffname(), oper.getStaffid()));

		}

		client = logicCallsEntry.getCilent();

		for (Customer cust : client) {
			cmbCustomer.addListItem(new ListItem(cust.getcustName(), cust.getCustId()));
			cmbViewClient.addListItem(new ListItem(cust.getcustName(), cust.getCustId()));

		}

		department = logicCallsEntry.getDepartment();

		for (Department dept : department) {
			cmbDepartment.addListItem(new ListItem(dept.getDepartment(), dept.getdNo()));
			cmbViewDepartment.addListItem(new ListItem(dept.getDepartment(), dept.getdNo()));

		}
		custCoOrd = logicCallsEntry.getCustCoOrd();

		for (CustStaff cust : custCoOrd) {

			cmbCustStaff.addListItem(new ListItem(cust.getCustStaffName(), cust.getCustStaffId()));
			cmbViewCustCoOrd.addListItem(new ListItem(cust.getCustStaffName(), cust.getCustStaffId()));
		}

		callCoOrd = logicCallsEntry.getCallCoOrd();

		for (Operator callCo : callCoOrd) {
			cmbStaff.addListItem(new ListItem(callCo.getStaffname(), callCo.getStaffid()));
			cmbViewCallCoOrd.addListItem(new ListItem(callCo.getStaffname(), callCo.getStaffid()));

		}

		module = logicCallsEntry.getModule(0);

		for (Module mod : module) {
			cmbModule.addListItem(new ListItem(mod.getModuleName(), mod.getModuleId()));
			cmbViewModule.addListItem(new ListItem(mod.getModuleName(), mod.getModuleId()));

		}

		for (Operator oper : callFrom) {
			cmbCallFrom.addListItem(new ListItem(oper.getStaffname(), oper.getStaffid()));
			cmbViewRecby.addListItem(new ListItem(oper.getStaffname(), oper.getStaffid()));

		}

	}

	private Component panelContentInitoalize() {

		int lblWidth = 150;
		int lblHeight = 50;
		int txtwidth = 150;
		int txtheight = 30;

		panelContent = new JPanel();
		panelContent.setBounds(panelLine2.getX() + 50, panelLine2.getY() + 40, 850, 500);
		panelContent.setLayout(null);
		panelContent.setBackground(color2);

		panelContent.setBorder(BorderFactory.createEtchedBorder(color3, color3));

		lblLastCallNo = new JLabel("Last Call No : ");
		lblLastCallNo.setBounds(panelContent.getX() + panelContent.getWidth() - 120, panelContent.getY() - 40, lblWidth,
				lblHeight);
		lblLastCallNo.setHorizontalAlignment(SwingConstants.LEFT);
		lblLastCallNo.setFont(CustomFonts.fontAxiformaBold16);
		lblLastCallNo.setForeground(Color.decode("#bf181d"));
		lblLastCallNo.setVisible(true);

		lblLastCallNoVal = new JLabel("");
		lblLastCallNoVal.setBounds(lblLastCallNo.getX(), lblLastCallNo.getY(), 120, lblHeight);
		lblLastCallNoVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLastCallNoVal.setFont(CustomFonts.fontAxiformaBold16);
		lblLastCallNoVal.setForeground(Color.decode("#bf181d"));
		lblLastCallNoVal.setVisible(true);

		lblCallDate = new JLabel("Call Date");
		lblCallDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblCallDate.setBounds(20, 20, lblWidth, lblHeight);
		lblCallDate.setFont(CustomFonts.font);
		lblCallDate.setForeground(Color.BLACK);
		lblCallDate.setBackground(color2);
		lblCallDate.setVisible(true);

		spnCallDate = new JilabaSpinner();
		spnCallDate.setBounds(lblCallDate.getX() + 130, lblCallDate.getY() + 10, 80, txtheight);
		spnCallDate.setFont(CustomFonts.fontCalibriBold);
		spnCallDate.setForeground(Color.BLACK);
		spnCallDate.setVisible(true);
		spnCallDate.addKeyListener(this);

		lblCallFrom = new JLabel("Call From");
		lblCallFrom.setHorizontalAlignment(SwingConstants.LEFT);
		lblCallFrom.setBounds(lblCallDate.getX(), lblCallDate.getY() + 50, lblWidth, lblHeight);
		lblCallFrom.setFont(CustomFonts.font);
		lblCallFrom.setBackground(color2);
		lblCallFrom.setForeground(Color.BLACK);
		lblCallFrom.setVisible(true);

		cmbCallFrom = new JilabaComboBox<Operator>();
		cmbCallFrom.setBounds(lblCallFrom.getX() + 130, lblCallFrom.getY() + 10, txtwidth, txtheight);
		cmbCallFrom.setFont(CustomFonts.fontCalibriBold);
		cmbCallFrom.setBackground(color2);
		cmbCallFrom.setForeground(Color.BLACK);
		cmbCallFrom.setVisible(true);
		cmbCallFrom.addKeyListener(this);

		lblClient = new JLabel("Client");
		lblClient.setHorizontalAlignment(SwingConstants.LEFT);
		lblClient.setBounds(lblCallFrom.getX(), lblCallFrom.getY() + 50, lblWidth, lblHeight);
		lblClient.setFont(CustomFonts.font);
		lblClient.setBackground(color2);
		lblClient.setForeground(Color.BLACK);
		lblClient.setVisible(true);

		cmbCustomer = new JilabaComboBox<Customer>();
		cmbCustomer.setBounds(lblClient.getX() + 130, lblClient.getY() + 10, txtwidth, txtheight);
		cmbCustomer.setFont(CustomFonts.fontCalibriBold);
		cmbCustomer.setBackground(color2);
		cmbCustomer.setForeground(Color.BLACK);
		cmbCustomer.setVisible(true);
		cmbCustomer.addKeyListener(this);

		lblDepartment = new JLabel("Department");
		lblDepartment.setHorizontalAlignment(SwingConstants.LEFT);
		lblDepartment.setBounds(lblClient.getX(), lblClient.getY() + 50, lblWidth, lblHeight);
		lblDepartment.setFont(CustomFonts.font);
		lblDepartment.setBackground(color2);
		lblDepartment.setForeground(Color.BLACK);
		lblDepartment.setVisible(true);

		cmbDepartment = new JilabaComboBox<Department>();
		cmbDepartment.setBounds(lblDepartment.getX() + 130, lblDepartment.getY() + 10, txtwidth, txtheight);
		cmbDepartment.setFont(CustomFonts.fontCalibriBold);
		cmbDepartment.setBackground(color2);
		cmbDepartment.setForeground(Color.BLACK);
		cmbDepartment.setVisible(true);
		cmbDepartment.addKeyListener(this);

		lblCustCoOrd = new JLabel("Customer Co-Ordinator");
		lblCustCoOrd.setHorizontalAlignment(SwingConstants.LEFT);
		lblCustCoOrd.setBounds(lblDepartment.getX(), lblDepartment.getY() + 50, lblWidth, lblHeight);
		lblCustCoOrd.setFont(CustomFonts.font);
		lblCustCoOrd.setBackground(color2);
		lblCustCoOrd.setForeground(Color.BLACK);
		lblCustCoOrd.setVisible(true);

		cmbCustStaff = new JilabaComboBox<Operator>();
		cmbCustStaff.setBounds(lblCustCoOrd.getX() + 130, lblCustCoOrd.getY() + 10, txtwidth, txtheight);
		cmbCustStaff.setFont(CustomFonts.fontCalibriBold);
		cmbCustStaff.setBackground(color2);
		cmbCustStaff.setForeground(Color.BLACK);
		cmbCustStaff.setVisible(true);
		cmbCustStaff.addKeyListener(this);

		lblCallCoOrd = new JLabel("Call Co-Ordinator");
		lblCallCoOrd.setHorizontalAlignment(SwingConstants.LEFT);
		lblCallCoOrd.setBounds(lblCustCoOrd.getX(), lblCustCoOrd.getY() + 50, lblWidth, lblHeight);
		lblCallCoOrd.setFont(CustomFonts.font);
		lblCallCoOrd.setBackground(color2);
		lblCallCoOrd.setForeground(Color.BLACK);
		lblCallCoOrd.setVisible(true);

		cmbStaff = new JilabaComboBox<CustStaff>();
		cmbStaff.setBounds(lblCallCoOrd.getX() + 130, lblCallCoOrd.getY() + 10, txtwidth, txtheight);
		cmbStaff.setFont(CustomFonts.fontCalibriBold);
		cmbStaff.setBackground(color2);
		cmbStaff.setForeground(Color.BLACK);
		cmbStaff.setVisible(true);
		cmbStaff.addKeyListener(this);

		lblModule = new JLabel("Module");
		lblModule.setHorizontalAlignment(SwingConstants.LEFT);
		lblModule.setBounds(lblCallCoOrd.getX(), lblCallCoOrd.getY() + 50, lblWidth, lblHeight);
		lblModule.setFont(CustomFonts.font);
		lblModule.setBackground(color2);
		lblModule.setForeground(Color.BLACK);
		lblModule.setVisible(true);

		cmbModule = new JilabaComboBox<Module>();
		cmbModule.setBounds(lblModule.getX() + 130, lblModule.getY() + 10, txtwidth, txtheight);
		cmbModule.setFont(CustomFonts.fontCalibriBold);
		cmbModule.setBackground(color2);
		cmbModule.setForeground(Color.BLACK);
		cmbModule.setVisible(true);
		cmbModule.addKeyListener(this);

		lblRefNo = new JLabel("Reference");
		lblRefNo.setHorizontalAlignment(SwingConstants.LEFT);
		lblRefNo.setBounds(lblModule.getX(), lblModule.getY() + 50, lblWidth, lblHeight);
		lblRefNo.setFont(CustomFonts.font);
		lblRefNo.setBackground(color2);
		lblRefNo.setForeground(Color.BLACK);
		lblRefNo.setVisible(true);

		txtRefNo = new JilabaTextField();
		txtRefNo.setBounds(lblRefNo.getX() + 130, lblRefNo.getY() + 10, txtwidth, txtheight);
		txtRefNo.setFont(CustomFonts.fontCalibriBold);
		txtRefNo.setBackground(color2);
		txtRefNo.setVisible(true);
		txtRefNo.setForeground(Color.BLACK);
		txtRefNo.setMaxLength(50);
		txtRefNo.setTextType(TextInputType.NUMBER);
		txtRefNo.addKeyListener(this);

		lblCompanyCode = new JLabel("Branch Code");
		lblCompanyCode.setHorizontalAlignment(SwingConstants.LEFT);
		lblCompanyCode.setBounds(lblRefNo.getX(), lblRefNo.getY() + 50, lblWidth, lblHeight);
		lblCompanyCode.setFont(CustomFonts.font);
		lblCompanyCode.setBackground(color2);
		lblCompanyCode.setForeground(Color.BLACK);
		lblCompanyCode.setVisible(true);

		cmbBranch = new JilabaComboBox<String>();
		cmbBranch.setBounds(lblCompanyCode.getX() + 130, lblCompanyCode.getY() + 10, txtwidth, txtheight);
		cmbBranch.setFont(CustomFonts.fontCalibriBold);
		cmbBranch.setBackground(color2);
		cmbBranch.setForeground(Color.BLACK);
		cmbBranch.setVisible(true);
		cmbBranch.addKeyListener(this);

		lblOption = new JLabel("Menu Path");
		lblOption.setHorizontalAlignment(SwingConstants.LEFT);
		lblOption.setBounds(spnCallDate.getX() + spnCallDate.getWidth() + 130, lblCallDate.getY(), lblWidth, lblHeight);
		lblOption.setFont(CustomFonts.font);
		lblOption.setForeground(Color.BLACK);
		lblOption.setBackground(color2);
		lblOption.setVisible(true);

		txtOption = new JilabaTextField();
		txtOption.setBounds(lblOption.getX() + 130, lblOption.getY() + 10, txtwidth + 200, txtheight);
		txtOption.setFont(CustomFonts.fontCalibriBold);
		txtOption.setBackground(color2);
		txtOption.setForeground(Color.BLACK);
		txtOption.setVisible(true);
		txtOption.setMaxLength(150);
		txtOption.addKeyListener(this);

		txtEditCallNo = new JilabaTextField();
		txtEditCallNo.setBounds(txtOption.getX() + 130, lblOption.getY() + 30, txtwidth, txtheight);
		txtEditCallNo.setFont(CustomFonts.fontCalibriBold);
		txtEditCallNo.setBackground(color2);
		txtEditCallNo.setForeground(Color.BLACK);
		txtEditCallNo.setVisible(false);
		txtEditCallNo.setMaxLength(50);
		txtEditCallNo.addKeyListener(this);

		lblCallNature = new JLabel("Nature");
		lblCallNature.setHorizontalAlignment(SwingConstants.LEFT);
		lblCallNature.setBounds(lblOption.getX(), lblOption.getY() + 50, lblWidth, lblHeight);
		lblCallNature.setFont(CustomFonts.font);
		lblCallNature.setBackground(color2);
		lblCallNature.setForeground(Color.BLACK);
		lblCallNature.setVisible(true);

		cmbNature = new JilabaComboBox<String>();
		cmbNature.setBounds(lblCallNature.getX() + 130, lblCallNature.getY() + 10, txtwidth, txtheight);
		cmbNature.setFont(CustomFonts.fontCalibriBold);
		cmbNature.setBackground(color2);
		cmbNature.setForeground(Color.BLACK);
		cmbNature.setVisible(true);
		cmbNature.addKeyListener(this);

		lblCallRecMode = new JLabel("Nature");
		lblCallRecMode.setHorizontalAlignment(SwingConstants.LEFT);
		lblCallRecMode.setBounds(lblOption.getX(), lblOption.getY() + 50, lblWidth, lblHeight);
		lblCallRecMode.setFont(CustomFonts.font);
		lblCallRecMode.setBackground(color2);
		lblCallRecMode.setForeground(Color.BLACK);
		lblCallRecMode.setVisible(true);

		cmbRecvMode = new JilabaComboBox<String>();
		cmbRecvMode.setBounds(lblCallRecMode.getX() + 130, lblCallRecMode.getY() + 10, txtwidth, txtheight);
		cmbRecvMode.setFont(CustomFonts.fontCalibriBold);
		cmbRecvMode.setBackground(color2);
		cmbRecvMode.setForeground(Color.BLACK);
		cmbRecvMode.setVisible(true);
		cmbRecvMode.addKeyListener(this);

		lblDescription = new JLabel("Error\\Modification");
		lblDescription.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescription.setBounds(lblCallRecMode.getX(), lblCallRecMode.getY() + 50, lblWidth, lblHeight);
		lblDescription.setFont(CustomFonts.font);
		lblDescription.setBackground(color2);
		lblDescription.setForeground(Color.BLACK);
		lblDescription.setVisible(true);

		txtDesc = new JTextArea(350, 200);
		txtDesc.setEditable(true);
		txtDesc.setLineWrap(true);
		txtDesc.setBounds(lblDescription.getX() + 130, lblDescription.getY() + 10, 350, 200);
		txtDesc.setFont(CustomFonts.fontCalibriBold);
		txtDesc.setBackground(color2);
		txtDesc.setForeground(Color.BLACK);
		txtDesc.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		txtDesc.setVisible(true);
		txtDesc.addKeyListener(this);

		lblDevCoOrd = new JLabel("Dev Co-Ordinator");
		lblDevCoOrd.setHorizontalAlignment(SwingConstants.LEFT);
		lblDevCoOrd.setBounds(lblDescription.getX(), txtDesc.getY() + txtDesc.getHeight() + 30, lblWidth, lblHeight);
		lblDevCoOrd.setFont(CustomFonts.font);
		lblDevCoOrd.setBackground(color2);
		lblDevCoOrd.setForeground(Color.BLACK);
		lblDevCoOrd.setVisible(true);

		cmbDevCoOrd = new JilabaComboBox<Operator>();
		cmbDevCoOrd.setBounds(lblDevCoOrd.getX() + 130, lblDevCoOrd.getY() + 10, txtwidth, txtheight);
		cmbDevCoOrd.setFont(CustomFonts.fontCalibriBold);
		cmbDevCoOrd.setBackground(color2);
		cmbDevCoOrd.setForeground(Color.BLACK);
		cmbDevCoOrd.setVisible(true);
		cmbDevCoOrd.addKeyListener(this);

		btnImage = new JButton("Image Attach ");
		btnImage.setHorizontalAlignment(SwingConstants.CENTER);
		btnImage.setBounds(cmbDevCoOrd.getX() + cmbDevCoOrd.getWidth(), cmbDevCoOrd.getY() - 40, 60, 25 - 10);
		btnImage.setFont(jilabaFonts.getFont(FontStyle.BOLD, 12));
		btnImage.setMnemonic(KeyEvent.VK_I);
		btnImage.setBackground(Color.decode("#ED2939"));
		btnImage.setForeground(Color.BLACK);
		btnImage.setBorder(BorderFactory.createBevelBorder(10));
		btnImage.setVisible(true);
		btnImage.addActionListener(this);
		btnImage.addKeyListener(this);

		lblPreview = new JLabel("");
		lblPreview.setHorizontalAlignment(SwingConstants.LEFT);
		lblPreview.setBounds(btnImage.getX() + 30, btnImage.getY() + 10, 150, 150);
		lblPreview.setFont(CustomFonts.font);
		lblPreview.setBackground(color2);
		lblPreview.setForeground(Color.BLACK);
		lblPreview.setVisible(true);

		lblPath = new JLabel("");
		lblPath.setHorizontalAlignment(SwingConstants.LEFT);
		lblPath.setBounds(btnImage.getX() + 30, btnImage.getY() + 10, 150, 150);
		lblPath.setFont(CustomFonts.font);
		lblPath.setBackground(color2);
		lblPath.setForeground(Color.BLACK);
		lblPath.setVisible(false);

		lblPressEsc = new JLabel("Press Esc to Close ");
		lblPressEsc.setHorizontalAlignment(SwingConstants.LEFT);
		lblPressEsc.setBounds(panelContent.getX(), panelContent.getY() + panelContent.getHeight(), lblWidth, lblHeight);
		lblPressEsc.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		lblPressEsc.setForeground(Color.decode("#E3262C"));
		lblPressEsc.setVisible(false);

		panelEntry.add(panelContent);
		panelContent.add(lblCallDate);
		panelContent.add(spnCallDate);
		panelContent.add(lblCallFrom);
		panelContent.add(lblClient);
		panelContent.add(cmbCustomer);
		panelContent.add(lblDepartment);
		panelContent.add(cmbCallFrom);
		panelContent.add(cmbCustomer);
		panelContent.add(cmbDepartment);
		panelContent.add(lblCustCoOrd);
		panelContent.add(cmbCustStaff);
		panelContent.add(lblCallCoOrd);
		panelContent.add(cmbStaff);
		panelContent.add(lblModule);
		panelContent.add(cmbModule);
		panelContent.add(lblModule);
		panelContent.add(lblRefNo);
		panelContent.add(txtRefNo);
		panelContent.add(lblCompanyCode);
		panelContent.add(cmbBranch);
		panelContent.add(lblOption);
		panelContent.add(txtOption);
		panelContent.add(txtEditCallNo);
		panelContent.add(lblCallNature);
		panelContent.add(cmbNature);
		panelContent.add(lblCallRecMode);
		panelContent.add(cmbRecvMode);
		panelContent.add(lblDescription);
		panelContent.add(txtDesc);
		panelContent.add(lblDevCoOrd);
		panelContent.add(cmbDevCoOrd);

		panelContent.add(btnImage);
		panelContent.add(lblPath);
		panelContent.add(lblPreview);
		panelContent.add(lblCompanyCode);

		panelEntry.add(lblPressEsc);
		panelEntry.add(lblLastCallNo);
		panelEntry.add(lblLastCallNoVal);

		return panelContent;
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

		lblCallMnuHead = new JLabel("CALLS ENTRY");
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

		if (e.getSource() == btnExit) {

			setVisible(false);

			// reLoadImageSetEmpty();

			FrmMainMenu frmMainMenu = Applicationmain.getAbstractApplicationContext().getBean(FrmMainMenu.class);
			frmMainMenu.setVisible(true);

		} else if (e.getSource() == btnSave) {

			if (txtOption.getText().equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(getContentPane(), "Option Should Not Empty !...");
				txtOption.requestFocus();
				return;
			}
			if (txtDesc.getText().equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(getContentPane(), "Description Should Not Empty !...");
				txtDesc.requestFocus();
				return;
			}

			btnSave.setEnabled(true);

			btnSave(calls);

			reLoadImageSetEmpty();

		} else if (e.getSource() == btnAdd) {

			enableAllComponents(panelContent);
			reLoadImageSetEmpty();

			btnImage.setEnabled(true);

			panelContent.setEnabled(true);
			btnCancel.setEnabled(true);
			spnCallDate.requestFocus();

		} else if (e.getSource() == btnCancel) {

			reLoadImageSetEmpty();
			loadDetails();

		} else if (e.getSource() == btnEntryView) {

			reLoadImageSetEmpty();

			rdpAsOnDate.setSelected(true);

			btnAdd.setEnabled(false);
			btnEntryView.setEnabled(false);
			btnEdit.setEnabled(true);
			btnBack.setEnabled(true);
			btnCancel.setEnabled(false);
			btnExit.setEnabled(true);
			panelEntry.setVisible(false);
			panelView.setVisible(true);
			rdpAsOnDate.requestFocus();

		} else if (e.getSource() == btnView) {

			tblEditCalls.setColumnSelectionInterval(0, 1);
			tblEditCalls.requestFocus();

			btnView();

		} else if (e.getSource() == btnClear) {

			clear();

		} else if (e.getSource() == btnBack) {

			reLoadImageSetEmpty();
			loadDetails();
			clear();
		} else if (e.getSource() == btnEdit) {

			enableAllComponents(panelContent);

			panelView.setVisible(false);
			panelEntry.setVisible(true);

			btnImage.setEnabled(true);
			btnSave.setVisible(false);
			btnUpdate.setVisible(true);
			btnBack.setEnabled(false);
			btnCancel.setEnabled(true);
			btnEdit.setEnabled(false);

			int row = tblEditCalls.getSelectedRow();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			String dateString = (String) tblEditCalls.getValueAt(row, 1);

			try {
				// Parse the date string to a Date object
				java.util.Date dateValue = formatter.parse(dateString);

				// Set the Date object in the JSpinner
				spnCallDate.setValue(dateValue);
			} catch (ParseException e1) {
				e1.printStackTrace(); // Handle parsing error
			}
			cmbCallFrom.setSelectedItem(tblEditCalls.getValueAt(row, 5).toString());
			cmbCustomer.setSelectedItem(tblEditCalls.getValueAt(row, 2).toString());
			cmbDepartment.setSelectedItem(tblEditCalls.getValueAt(row, 8).toString());
			cmbCustStaff.setSelectedItem(tblEditCalls.getValueAt(row, 10).toString());
			cmbStaff.setSelectedItem(tblEditCalls.getValueAt(row, 11).toString());
			txtRefNo.setText(tblEditCalls.getValueAt(row, 12).toString());
			txtOption.setText(tblEditCalls.getValueAt(row, 4).toString());
			cmbNature.setSelectedItem(tblEditCalls.getValueAt(row, 6).toString());
			cmbModule.setSelectedItem(tblEditCalls.getValueAt(row, 9).toString());
			txtDesc.setText(tblEditCalls.getValueAt(row, 3).toString());
			cmbDevCoOrd.setSelectedItem(tblEditCalls.getValueAt(row, 13).toString());
			txtEditCallNo.setText(tblEditCalls.getValueAt(row, 0).toString());

			// CallsImages callsImages = logicReadyCalls
			// .getImages(String.valueOf(tblEditCalls.getValueAt(row, 0).toString()));
			blnFrmCallEdit = true;
			// FrmImageDialog frmImageDialog =
			// Applicationmain.getAbstractApplicationContext()
			// .getBean(FrmImageDialog.class, new Object[] { getContentPane() });
			// frmImageDialog.booleanAssign(blnFrmCallEdit,
			// false,(String.valueOf(tblEditCalls.getValueAt(row, 0).toString())),
			// callsImages);
			// frmImageDialog.setVisible(true);

			spnCallDate.requestFocus();

		} else if (e.getSource() == btnUpdate) {

			logicCallsEntry.updateCallsEdit(cmbCallFrom.getSelectedItemValue().toString(),
					cmbCustomer.getSelectedItemValue().toString(), cmbDepartment.getSelectedItemValue().toString(),
					cmbCustStaff.getSelectedItemValue().toString(), cmbStaff.getSelectedItemValue().toString(),
					cmbModule.getSelectedItemValue().toString(), txtRefNo.getText(), txtOption.getText(),
					cmbNature.getSelectedItemValue().toString(), txtDesc.getText(),
					cmbDevCoOrd.getSelectedItemValue().toString(), txtEditCallNo.getText());

			if (FrmImageDialog.blnImageVerify == true) {
				logicCallsEntry.updateCallImages(txtEditCallNo.getText(), lblImage1, lblImage2, lblImage3, lblImage4,
						blnNewImageAdd);
			}

			JOptionPane.showMessageDialog(panelMain, "Call Updated ...!");

			frmImageDialog.lblImage1 = null;
			frmImageDialog.lblImage2 = null;
			frmImageDialog.lblImage3 = null;
			frmImageDialog.lblImage4 = null;

			frmImageDialog = Applicationmain.getAbstractApplicationContext().getBean(FrmImageDialog.class,
					new Object[] { getContentPane() });

			loadDetails();
		}

	}

	private void clear() {
		rdpBetweenDate.setVisible(true);
		lblCallToDate.setVisible(true);

		spnCallToDate.setVisible(true);
		try {
			spnCallToDate.setValue(spnCallToDate.getDateValue());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		cmbDetailsLoad();

	}

	private void reLoadImageSetEmpty() {

		frmImageDialog.lblImage1 = null;
		frmImageDialog.lblImage2 = null;
		frmImageDialog.lblImage3 = null;
		frmImageDialog.lblImage4 = null;

		frmImageDialog = Applicationmain.getAbstractApplicationContext().getBean(FrmImageDialog.class,
				new Object[] { getContentPane() });
	}

	public void hideColumn(int columnIndex) {
		// Ensure the column index is valid
		if (tblEditCalls == null || columnIndex < 0 || columnIndex >= tblEditCalls.getColumnCount()) {
			System.out.println("Invalid table or column index.");
			return;
		}

		// Get the TableColumnModel and TableColumn
		TableColumn column = tblEditCalls.getColumnModel().getColumn(columnIndex);

		// Hide the column by setting its width to zero
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setWidth(0);
		column.setPreferredWidth(0);
	}

	private void btnView() {

		selectAllCheck();

		orderByCheck();

		tblEditCalls.clear();

		lstCalls = logicCallsEntry.getCalls(strCallFromDate, strCallToDate, strViewRecby, strViewCallCoOrd,
				strViewCustCoOrd, strViewDevCoOrd, strViewClient, strViewDeptAuthorize, strViewDepartment,
				strViewModule, strOrderby, txtCallNo.getText());

		List<Object> lstObjects = null;

		for (Calls calls : lstCalls) {

			lstObjects = new ArrayList<Object>();
			CallsImages callsImages = new CallsImages();

			lstObjects.add(calls.getCallno());
			lstObjects.add(calls.getCdate());
			lstObjects.add(calls.getCustName().trim());
			lstObjects.add(calls.getDescription());
			lstObjects.add(calls.getMoption().trim());
			lstObjects.add(calls.getReceiverName());
			lstObjects.add(calls.getExpNature());
			lstObjects.add(calls.getAuthorizedName());
			lstObjects.add(calls.getDepartmentName());
			lstObjects.add(calls.getModuleName());
			lstObjects.add(calls.getCustcordinator_name());
			lstObjects.add(calls.getCallcoordinator());
			lstObjects.add(calls.getRefNo());
			lstObjects.add(calls.getDevCoOrdName());
			lstObjects.add(calls.getLstCallsImages().get(0).getImage1());
			lstObjects.add(calls.getLstCallsImages().get(0).getImage2());
			lstObjects.add(calls.getLstCallsImages().get(0).getImage3());
			lstObjects.add(calls.getLstCallsImages().get(0).getImage4());

			tblEditCalls.addRow(lstObjects);

		}

		if (lstCalls.size() == 0) {

			JOptionPane.showMessageDialog(panelMain, "Calls Details Not Found ... !");
		}

		FrmCallsEntry frmCallsEntry = new FrmCallsEntry();
		frmCallsEntry.tblEditCalls = tblEditCalls;

		// Hide the second column (index 1)
		frmCallsEntry.hideColumn(13);

	}

	private void orderByCheck() {

		if (rdpNone.isSelected() == true) {
			strOrderby = "";
		} else if (rdpModule.isSelected() == true) {
			strOrderby = "c.moduleid";
		} else if (rdpRecby.isSelected() == true) {
			strOrderby = "c.Receby";
		} else if (rdpClient.isSelected() == true) {
			strOrderby = "c.cusid";
		} else if (rdpCallDate.isSelected() == true) {
			strOrderby = "c.cdate";
		} else if (rdpCallDate.isSelected() == true) {
			strOrderby = "R.cdate";
		} else if (rdpCallNo.isSelected() == true) {
			strOrderby = "c.callno";
		}

	}

	private void selectAllCheck() {

		if (cmbViewRecby.getSelectedItem().equals("All")) {
			strViewRecby = 0;
		} else {
			strViewRecby = Integer.parseInt(String.valueOf(cmbViewRecby.getSelectedItemValue()));
		}
		if (cmbViewCallCoOrd.getSelectedItem().equals("All")) {
			strViewCallCoOrd = 0;
		} else {
			strViewCallCoOrd = Integer.parseInt(String.valueOf(cmbViewCallCoOrd.getSelectedItemValue()));
		}
		if (cmbViewCustCoOrd.getSelectedItem().equals("All")) {
			strViewCustCoOrd = 0;
		} else {
			strViewCustCoOrd = Integer.parseInt(String.valueOf(cmbViewCustCoOrd.getSelectedItemValue()));
		}
		if (cmbViewDevCoOrd.getSelectedItem().equals("All")) {
			strViewDevCoOrd = 0;
		} else {
			strViewDevCoOrd = Integer.parseInt(String.valueOf(cmbViewDevCoOrd.getSelectedItemValue()));
		}
		if (cmbViewClient.getSelectedItem().equals("All")) {
			strViewClient = 0;
		} else {
			strViewClient = Integer.parseInt(String.valueOf(cmbViewClient.getSelectedItemValue()));
		}
		if (cmbDeptAuthorize.getSelectedItem().equals("All")) {
			strViewDeptAuthorize = 0;
		} else {
			strViewDeptAuthorize = Integer.parseInt(String.valueOf(cmbDeptAuthorize.getSelectedItemValue()));
		}
		if (cmbViewDepartment.getSelectedItem().equals("All")) {
			strViewDepartment = 0;
		} else {
			strViewDepartment = Integer.parseInt(String.valueOf(cmbViewDepartment.getSelectedItemValue()));
		}
		if (cmbViewModule.getSelectedItem().equals("All")) {
			strViewModule = 0;
		} else {
			strViewModule = Integer.parseInt(String.valueOf(cmbViewModule.getSelectedItemValue()));
		}

		if (rdpAsOnDate.isSelected() == true) {
			strCallFromDate = spnCallFromDate.getDateValue();
			strCallToDate = null;
		} else {
			strCallFromDate = spnCallFromDate.getDateValue();
			strCallToDate = spnCallToDate.getDateValue();
		}

	}

	public void btnSave(Calls calls) {

		try {
			String call = (String.valueOf(Integer.valueOf(callNo) + 1));
			calls = new Calls();
			calls.setCallno(Integer.valueOf(callNo) + 1);
			calls.setCdate(spnCallDate.getDateValue());
			calls.setReceby(Integer.parseInt(String.valueOf(cmbCallFrom.getSelectedItemValue())));
			calls.setCusid(String.valueOf(cmbCustomer.getSelectedItemValue()));
			calls.setDno(Integer.parseInt(String.valueOf(cmbDepartment.getSelectedItemValue())));
			calls.setCustcoordinator(String.valueOf(cmbCustStaff.getSelectedItemValue()));
			calls.setCustcordinator_name(String.valueOf(cmbCustStaff.getSelectedItem()));
			calls.setCallcoordinator(String.valueOf(cmbStaff.getSelectedItemValue()));
			calls.setModuleid(Integer.parseInt(String.valueOf(cmbModule.getSelectedItemValue())));
			// calls.setRefNo(String.valueOf(txtRefNo.getText()));
			calls.setMoption(txtOption.getText());
			calls.setCallnature(String.valueOf(((String) cmbNature.getSelectedItem()).charAt(0)));
			calls.setDescription(txtDesc.getText());
			calls.setCallTime(String.valueOf(Instant.now()));

			if (FrmImageDialog.blnImageVerify == true) {
				logicCallsEntry.getCallSave(calls, call, lblImage1, lblImage2, lblImage3, lblImage4);
				// logicCallsEntry.saveCallImages(call, lblImage1, lblImage2, lblImage3,
				// lblImage4);
			} else {

				logicCallsEntry.getCallSave(calls, "", null, null, null, null);
			}

			JOptionPane.showMessageDialog(panelMain, "Call Raised Successfully...!");

			loadDetails();

		} catch (Exception e) {

			JOptionPane.showMessageDialog(panelMain, e.getMessage());
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		spnCallDate.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					cmbCallFrom.requestFocus();

				}

			}

		});

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			if (e.getSource() == spnCallDate) {

				cmbCallFrom.requestFocus();
			} else if (e.getSource() == cmbCallFrom) {

				cmbCustomer.requestFocus();
			} else if (e.getSource() == cmbCustomer) {

				cmbDepartment.requestFocus();
			} else if (e.getSource() == cmbDepartment) {

				module = logicCallsEntry
						.getModule(Integer.valueOf(String.valueOf(cmbDepartment.getSelectedItemValue())));

				cmbModule.removeAllItems();

				for (Module mod : module) {
					cmbModule.addListItem(new ListItem(mod.getModuleName(), mod.getModuleId()));
					cmbViewModule.addListItem(new ListItem(mod.getModuleName(), mod.getModuleId()));

				}

				cmbCustStaff.requestFocus();

			} else if (e.getSource() == cmbCustStaff) {

				cmbStaff.requestFocus();
			} else if (e.getSource() == cmbStaff) {

				cmbModule.requestFocus();
			} else if (e.getSource() == cmbModule) {

				txtRefNo.requestFocus();
			} else if (e.getSource() == txtRefNo) {

				cmbBranch.requestFocus();
			} else if (e.getSource() == cmbBranch) {

				txtOption.requestFocus();
			} else if (e.getSource() == txtOption) {

				cmbNature.requestFocus();
			} else if (e.getSource() == cmbNature) {

				txtDesc.requestFocus();
			} else if (e.getSource() == txtDesc) {

				cmbDevCoOrd.requestFocus();
			} else if (e.getSource() == cmbDevCoOrd) {

				btnImage.requestFocus();
			} else if (e.getSource() == btnImage) {

				btnSave.setEnabled(true);
				if (btnSave.isVisible() == true) {
					btnSave.requestFocus();
				} else {
					btnUpdate.requestFocus();
				}
			} else if (e.getSource() == btnSave) {

				btnSave(calls);
			} else if (e.getSource() == btnExit) {

				setVisible(false);

				FrmMainMenu frmMainMenu = Applicationmain.getAbstractApplicationContext().getBean(FrmMainMenu.class);
				frmMainMenu.setVisible(true);

			}

		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

			if (e.getSource() == btnImage) {
				btnSave.setEnabled(true);
				if (btnSave.isVisible() == true) {
					btnSave.requestFocus();
				} else {
					btnUpdate.requestFocus();
				}
			}

			if (e.getSource() == lblPreview) {

				btnSave.requestFocus();

			}

			/*
			 * setVisible(false);
			 * 
			 * FrmMainMenu frmMainMenu =
			 * Applicationmain.getAbstractApplicationContext().getBean(FrmMainMenu.class);
			 * frmMainMenu.setVisible(true);
			 */

		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

			if (e.getSource() == btnSave) {

				btnExit.requestFocus();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {

			if (e.getSource() == btnExit) {

				btnSave.requestFocus();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			if (e.getSource() == rdpAsOnDate) {

				blnAsOnDate = true;
				rdpBetweenDate.setVisible(false);
				lblCallToDate.setVisible(false);
				try {
					spnCallToDate.setValue(spnCallToDate.getDateValue());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				spnCallToDate.setVisible(false);
				lblCallFromDate.setText("As On");
				// spnCallToDate = null;
				spnCallFromDate.requestFocus();
			} else if (e.getSource() == rdpBetweenDate) {

				spnCallFromDate.requestFocus();
				blnAsOnDate = false;

			} else if (e.getSource() == cmbViewRecby) {
				cmbViewCallCoOrd.requestFocus();
			} else if (e.getSource() == cmbViewCallCoOrd) {
				cmbViewCustCoOrd.requestFocus();
			} else if (e.getSource() == cmbViewCustCoOrd) {
				cmbViewDevCoOrd.requestFocus();
			} else if (e.getSource() == cmbViewDevCoOrd) {
				cmbViewClient.requestFocus();
			} else if (e.getSource() == cmbViewClient) {
				cmbDeptAuthorize.requestFocus();
			} else if (e.getSource() == cmbDeptAuthorize) {
				cmbViewDepartment.requestFocus();
			} else if (e.getSource() == cmbViewDepartment) {
				cmbViewModule.requestFocus();
			} else if (e.getSource() == cmbViewModule) {
				txtCallNo.requestFocus();
			} else if (e.getSource() == txtCallNo) {
				rdpNone.requestFocus();
			} else if (e.getSource() == rdpNone) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpModule) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpRecby) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpClient) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpCallDate) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpCallCoOrd) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpCallNo) {
				btnView.requestFocus();
			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void btnImageSave(byte[] lblImage1Path, byte[] lblImage2Path, byte[] lblImage3Path, byte[] lblImage4Path) {

		setLblImage1(lblImage1Path);
		setLblImage2(lblImage2Path);
		setLblImage3(lblImage3Path);
		setLblImage4(lblImage4Path);

	}

	public byte[] getLblImage1() {
		return lblImage1;
	}

	public void setLblImage1(byte[] lblImage1) {
		this.lblImage1 = lblImage1;
	}

	public byte[] getLblImage2() {
		return lblImage2;
	}

	public void setLblImage2(byte[] lblImage2) {
		this.lblImage2 = lblImage2;
	}

	public byte[] getLblImage3() {
		return lblImage3;
	}

	public void setLblImage3(byte[] lblImage3) {
		this.lblImage3 = lblImage3;
	}

	public byte[] getLblImage4() {
		return lblImage4;
	}

	public void setLblImage4(byte[] lblImage4) {
		this.lblImage4 = lblImage4;
	}

	@Override
	public void focusGained(FocusEvent e) {

		if (e.getSource() == btnAdd) {
			btnAdd.setBackground(color7);
		} else if (e.getSource() == btnBack) {
			btnBack.setBackground(color7);
		} else if (e.getSource() == btnSave) {
			btnSave.setBackground(color7);
		} else if (e.getSource() == btnView) {
			btnView.setBackground(color7);
		} else if (e.getSource() == btnEdit) {
			btnEdit.setBackground(color7);
		} else if (e.getSource() == btnCancel) {
			btnCancel.setBackground(color7);
		} else if (e.getSource() == btnBack) {
			btnBack.setBackground(color7);
		} else if (e.getSource() == btnExit) {
			btnExit.setBackground(color7);
		}

	}

	@Override
	public void focusLost(FocusEvent e) {

		if (e.getSource() == btnAdd) {
			btnAdd.setBackground(color3);
		} else if (e.getSource() == btnBack) {
			btnBack.setBackground(color3);
		} else if (e.getSource() == btnSave) {
			btnSave.setBackground(color3);
		} else if (e.getSource() == btnView) {
			btnView.setBackground(color3);
		} else if (e.getSource() == btnEdit) {
			btnEdit.setBackground(color3);
		} else if (e.getSource() == btnCancel) {
			btnCancel.setBackground(color3);
		} else if (e.getSource() == btnBack) {
			btnBack.setBackground(color3);
		} else if (e.getSource() == btnExit) {
			btnExit.setBackground(color3);
		}

	}

}
