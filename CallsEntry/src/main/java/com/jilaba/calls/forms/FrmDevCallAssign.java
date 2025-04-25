package com.jilaba.calls.forms;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.common.ImageResource;
import com.jilaba.calls.common.LoginCredential;
import com.jilaba.calls.common.TimerJob;
import com.jilaba.calls.logic.LogicDevCallAssign;
import com.jilaba.calls.logic.LogicDevCalls;
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
import com.jilaba.control.JilabaTable;
import com.jilaba.control.JilabaTextField;
import com.jilaba.control.ListItem;
import com.jilaba.design.ControlResize;
import com.jilaba.fonts.JilabaFonts;
import com.jilaba.fonts.JilabaFonts.FontStyle;

@org.springframework.stereotype.Component
@Scope("prototype")
public class FrmDevCallAssign extends JFrame implements ActionListener, KeyListener {

	private JPanel panelMain;
	private JPanel panelTitle;
	private JPanel panelLine;
	private JPanel panelDetail;
	private JPanel panelLine2;
	private JPanel panelDetail2;
	private JPanel panelDevSummary;
	private JPanel panelContent;
	private JPanel panelCallDetail;
	private JPanel panelLine3;
	private JPanel panelCallDesc;

	private JLabel lblHeading;
	private JLabel lblDevCoOrd;
	private JLabel lblCallNo;
	private JLabel lblCustomer;
	private JLabel lblCustCoOrd;
	private JLabel lblDepartment;
	private JLabel lblDeptAuthorize;
	private JLabel lblMinimize;

	private JLabel lblImage;
	private JButton btnImage;

	private JLabel lblRecvFrom;
	private JLabel lblModule;

	private JLabel lblCallNature;
	private JLabel lblPressEsc;
	private JLabel lblShortcutkey;

	private JTextArea txtCallDesc;

	private JLabel lblDevHead;
	private JLabel lblCallHead;
	private JLabel lblDevCallNo;
	private JLabel lblDevCallNoVal;
	private JLabel lblDevCallCoOrd;
	private JLabel lblDevCallCoOrdVal;
	private JLabel lblDevCustCoOrd;
	private JLabel lblDevCustCoOrdVal;
	private JLabel lblDevRecdBy;
	private JLabel lblDevRecdByVal;
	private JLabel lblDevClient;
	private JLabel lblDevClientVal;
	private JLabel lblDevModule;
	private JLabel lblDevModuleVal;
	private JLabel lblDevNature;
	private JLabel lblDevNatureVal;

	private JLabel lblDevelopedby;
	private JLabel lblOperatorLabel;
	private JLabel lblVersion;
	private JLabel lblServerIpValue;
	private JLabel lblDateTime;

	private JButton btnView;
	private JButton btnClear;

	private JilabaTable tblDevCalls;
	public static JilabaTable tblCalls;

	private JScrollPane scrDevCalls;
	private JScrollPane scrCalls;

	private JilabaComboBox<Operator> cmbDevCoOrd;
	private JilabaComboBox<Customer> cmbCustomer;
	private JilabaComboBox<Operator> cmbCustCoOrd;
	private JilabaComboBox<Department> cmbDepartment;
	private JilabaComboBox<Operator> cmbDeptAuthor;
	private JilabaComboBox<Operator> cmbRecvFrom;
	private JilabaComboBox<Module> cmbModule;
	private JilabaComboBox<String> cmbCallNature;

	private List<Operator> lstDevCoOrd;
	private List<Customer> lstCustomer;
	private List<CustStaff> lstCustCoOrd;
	private List<Department> lstDept;
	private List<Operator> lstDeptAuthor;
	private List<Operator> lstRecvFrom;
	private List<com.jilaba.calls.model.Module> lstModule;
	private List<Calls> lstDevCalls;
	private List<Calls> lstCalls;
	private long strCallNo = 0;
	private int strDevCoOrd = 0;
	private int strCustomer = 0;
	private int strCustCoOrd = 0;
	private int strDepartment = 0;
	private int strDeptAuthorize = 0;
	private int strRecvFrom = 0;
	private int strModule = 0;
	private int strCallNature = 0;

	private List<Object> lstDeptParam;

	List<Object> lstObject = null;

	private JilabaTextField txtCallNo;

	private ControlResize controlResize;
	private boolean blnFrmDevCall;

	private Color color1 = Color.decode("#ff6666");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#ff9999");
	private Color color4 = Color.decode("#000000");
	private Color color5 = Color.decode("#e4dedf");
	private Color color6 = Color.decode("#C0C0C0");
	private Color color7 = Color.decode("#FADBD8");
	private Color color8 = Color.decode("#008000");
	private Color color9 = Color.decode("#FF3933");

	private Color fontColor1 = Color.decode("#17202A");

	private JilabaFonts jilabaFonts = new JilabaFonts();

	@Autowired
	private LogicDevCallAssign logicDevCallAssign;
	@Autowired
	private LogicDevCalls logicDevCalls;
	@Autowired
	private CommonMethods commonMethods;
	protected Object returnStatus;

	private JLabel lblTotalcall;
	private JLabel lblTotalcallVal;

	public FrmDevCallAssign() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		getContentPane().setPreferredSize(new Dimension(958, 728));
		setLayout(null);
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

		txtCallNo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					cmbCustomer.requestFocus();

				}

			}
		});

		cmbModule.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				lstModule = logicDevCallAssign
						.getModule(Integer.valueOf(String.valueOf(cmbDepartment.getSelectedItemValue())));

				cmbModule.removeAllItems();
				cmbModule.addListItem(new ListItem("All", 0));

				for (com.jilaba.calls.model.Module module : lstModule) {

					cmbModule.addListItem(new ListItem(module.getModuleName(), module.getModuleId()));
				}

			}
		});
	}

	private void createControls() {
		panelMain = new JPanel();
		panelMain.setBounds(0, 0, 958, 728);
		panelMain.setLayout(null);
		panelMain.setBackground(color2);
		panelMain.add(panelTitleInialize());
		panelMain.add(panelLineInialize());
		panelMain.add(panelDetailInitialize());
		panelMain.add(panelLine2Initialize());
		panelMain.add(panelDetail2Initialize());
		panelMain.add(panelDevCallSummary());
		panelMain.add(panelContentInitialize());
		panelMain.add(panelLine3Inialize());

		panelMain.addKeyListener(this);

		createKeyListners();

		getContentPane().add(panelMain);

		lblTotalcall = new JLabel("TOTAL CALLS   - ");
		lblTotalcall.setBounds(830, panelLine2.getY() + 120, 80, 20);
		lblTotalcall.setBackground(color9);
		lblTotalcall.setForeground(color9);
		lblTotalcall.setFont(jilabaFonts.getFont(FontStyle.BOLD, 18));
		lblTotalcall.setVisible(false);
		lblTotalcall.addKeyListener(this);

		lblTotalcallVal = new JLabel("");
		lblTotalcallVal.setBounds(lblTotalcall.getX() + 70, lblTotalcall.getY(), 80, 20);
		lblTotalcallVal.setBackground(color9);
		lblTotalcallVal.setForeground(color9);
		lblTotalcallVal.setFont(jilabaFonts.getFont(FontStyle.BOLD, 18));
		lblTotalcallVal.setVisible(true);
		lblTotalcallVal.addKeyListener(this);

		panelMain.add(lblTotalcall);
		panelMain.add(lblTotalcallVal);

	}

	private void loadDevCalls() {

		lblServerIpValue.setText(commonMethods.getIpAddress());
		tblDevCalls.clear();

		lstDevCalls = logicDevCallAssign.getDeveloperCalls();

		List<Object> lstObjects = null;

		for (Calls devCall : lstDevCalls) {

			lstObjects = new ArrayList<Object>();

			lstObjects.add(devCall.getDeveloperName());
			lstObjects.add(devCall.getDevCalls());

			tblDevCalls.addRow(lstObjects);

		}

	}

	private void createKeyListners() {

		tblCalls.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					e.consume();

				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {

					if (FrmLogin.designation == 3) {

						int callNo = Integer
								.valueOf(String.valueOf(tblCalls.getModel().getValueAt(tblCalls.getSelectedRow(), 0)));
						int tblSelectedRow = tblCalls.getSelectedRow();

						FrmCallDialog frmCallDialog = Applicationmain.getAbstractApplicationContext()
								.getBean(FrmCallDialog.class, new Object[] { getContentPane() });
						frmCallDialog.getInitializeValue(callNo, tblSelectedRow);
						frmCallDialog.setVisible(true);

						// tblCalls.removeRow(tblSelectedRow);
						// tblCalls.requestFocus();
						// tblCalls.changeSelection(tblSelectedRow, 1, false, false);
						loadDevCalls();

					} else {
						JOptionPane.showMessageDialog(panelMain, "You Are Not Authorized to Call Assign... !");
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_I) {

					btnImageCall();
				}

				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

					setVisible(false);

					FrmMainMenu frmMainMenu = Applicationmain.getAbstractApplicationContext()
							.getBean(FrmMainMenu.class);
					frmMainMenu.setVisible(true);
				}

			}

		});

		tblDevCalls.addKeyListener(new KeyAdapter() {
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
		panelMain.addKeyListener(new KeyAdapter() {
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

	}

	@PostConstruct
	private ReturnStatus loadDetails() {

		tblDevCalls.clear();
		tblCalls.clear();

		cmbDevCoOrd.removeAllItems();
		cmbCustomer.removeAllItems();
		cmbCustCoOrd.removeAllItems();
		cmbDepartment.removeAllItems();
		cmbDeptAuthor.removeAllItems();
		cmbRecvFrom.removeAllItems();
		cmbModule.removeAllItems();
		cmbCallNature.removeAllItems();
		// cmbExplanation.removeAllItems();

		cmbDevCoOrd.addListItem(new ListItem("All", 0));
		cmbCustomer.addListItem(new ListItem("All", 0));
		cmbCustCoOrd.addListItem(new ListItem("All", 0));
		cmbDepartment.addListItem(new ListItem("All", 0));
		cmbDeptAuthor.addListItem(new ListItem("All", 0));
		cmbRecvFrom.addListItem(new ListItem("All", 0));
		cmbModule.addListItem(new ListItem("All", 0));
		cmbCallNature.addListItem(new ListItem("All", 0));

		loadCmbValues();

		/*
		 * lstDevCoOrd = logicDevCallAssign.getDevCallCoOrd(); strDevCoOrd = new
		 * ArrayList<Object>(); for (Operator devcoOrd : lstDevCoOrd) {
		 * 
		 * strDevCoOrd.add(devcoOrd.getStaffid()); cmbDevCoOrd.addListItem(new
		 * ListItem(devcoOrd.getStaffname(), devcoOrd.getStaffid()));
		 * 
		 * }
		 * 
		 * lstCustomer = logicDevCallAssign.getCustomer();
		 * 
		 * strCustomer = new ArrayList<Object>(); for (Customer cust : lstCustomer) {
		 * 
		 * strCustomer.add(cust.getCustId()); cmbCustomer.addListItem(new
		 * ListItem(cust.getcustName().trim(), cust.getCustId())); }
		 * 
		 * lstCustCoOrd = logicDevCallAssign.getCustCoOrd(); strCustCoOrd = new
		 * ArrayList<Object>();
		 * 
		 * for (Operator custCoOrd : lstCustCoOrd) {
		 * 
		 * strCustCoOrd.add(custCoOrd.getStaffid()); cmbCustCoOrd.addListItem(new
		 * ListItem(custCoOrd.getStaffname(), custCoOrd.getStaffid()));
		 * 
		 * }
		 * 
		 * lstDept = logicDevCallAssign.getDepartment(); strDeptParam = new
		 * ArrayList<Object>();
		 * 
		 * for (Department dept : lstDept) { strDeptParam.add(dept.getdNo());
		 * cmbDepartment.addListItem(new ListItem(dept.getDepartment(), dept.getdNo()));
		 * }
		 * 
		 * lstDeptAuthor = logicDevCallAssign.getDeptAuthorize(); strDeptAuthorize = new
		 * ArrayList<Object>();
		 * 
		 * for (Operator deptAuthor : lstDeptAuthor) { strDeptAuthorize.add(deptAuthor);
		 * cmbDeptAuthor.addListItem(new ListItem(deptAuthor.getStaffname(),
		 * deptAuthor.getStaffid())); }
		 * 
		 * lstRecvFrom = logicDevCallAssign.getRecvFrom(); strRecvFromParam = new
		 * ArrayList<Object>();
		 * 
		 * for (Operator recvFrom : lstRecvFrom) {
		 * 
		 * strRecvFromParam.add(recvFrom.getStaffid()); cmbRecvFrom.addListItem(new
		 * ListItem(recvFrom.getStaffname(), recvFrom.getStaffid())); }
		 * 
		 * lstModule = logicDevCallAssign.getModule(strDeptParam); strModule = new
		 * ArrayList<Object>();
		 * 
		 * for (com.jilaba.calls.model.Module module : lstModule) {
		 * 
		 * strModule.add(module.getModuleId()); cmbModule.addListItem(new
		 * ListItem(module.getModuleName(), module.getModuleId())); }
		 * 
		 */ cmbCallNature.addListItem(new ListItem("Error", 1));
		cmbCallNature.addListItem(new ListItem("Modification", 2));
		cmbCallNature.addListItem(new ListItem("Clarification", 3));
		cmbCallNature.addListItem(new ListItem("Development", 4));
		cmbCallNature.addListItem(new ListItem("General", 5));
		cmbCallNature.addListItem(new ListItem("Tallying", 6));

		loadDevCalls();

		return new ReturnStatus(true);

	}

	private void loadCmbValues() {

		lstDevCoOrd = logicDevCallAssign.getDevCallCoOrd();

		for (Operator devcoOrd : lstDevCoOrd) {

			cmbDevCoOrd.addListItem(new ListItem(devcoOrd.getStaffname(), devcoOrd.getStaffid()));

		}

		lstCustomer = logicDevCallAssign.getCustomer();

		for (Customer cust : lstCustomer) {

			cmbCustomer.addListItem(new ListItem(cust.getcustName().trim(), cust.getCustId()));
		}

		lstCustCoOrd = logicDevCallAssign.getCustCoOrd();

		for (CustStaff custCoOrd : lstCustCoOrd) {

			cmbCustCoOrd.addListItem(new ListItem(custCoOrd.getCustStaffName(), custCoOrd.getCustStaffId()));

		}

		lstDept = logicDevCallAssign.getDepartment();

		// lstDeptParam = new ArrayList<Object>();
		for (Department dept : lstDept) {
			// lstDeptParam.add(dept.getdNo());
			cmbDepartment.addListItem(new ListItem(dept.getDepartment(), dept.getdNo()));
		}

		lstDeptAuthor = logicDevCallAssign.getDeptAuthorize();

		for (Operator deptAuthor : lstDeptAuthor) {
			cmbDeptAuthor.addListItem(new ListItem(deptAuthor.getStaffname(), deptAuthor.getStaffid()));
		}

		lstRecvFrom = logicDevCallAssign.getRecvFrom();

		for (Operator recvFrom : lstRecvFrom) {

			cmbRecvFrom.addListItem(new ListItem(recvFrom.getStaffname(), recvFrom.getStaffid()));
		}

		lstModule = logicDevCallAssign.getModule(0);

		for (com.jilaba.calls.model.Module module : lstModule) {

			cmbModule.addListItem(new ListItem(module.getModuleName(), module.getModuleId()));
		}

	}

	private Component panelContentInitialize() {

		panelContent = new JPanel();
		panelContent.setBounds(panelDevSummary.getX() + panelDevSummary.getWidth() + 20, panelDevSummary.getY(), 700,
				430);
		panelContent.setLayout(null);
		panelContent.setBackground(color2);
		panelContent.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelContent.setVisible(true);

		tblCalls = new JilabaTable(getCalls());
		tblCalls.setAutoResizeMode(JilabaTable.AUTO_RESIZE_OFF);
		tblCalls.getTableHeader().setReorderingAllowed(false);
		tblCalls.getTableHeader().setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		tblCalls.setFont(CustomFonts.fontCalibriPlain15);
		tblCalls.getTableHeader().setForeground(color1);
		tblCalls.getTableHeader().setBackground(Color.WHITE);
		tblCalls.setRowHeight(20);
		tblCalls.setForeground(color4);
		tblCalls.setVisible(true);

		// tblCalls.setCellEditor(new TableButtonRendererEditor(tblCalls, 4));

		scrCalls = new JScrollPane(tblCalls);
		scrCalls.setBounds(10, 10, 680, 360);
		scrCalls.getViewport().setBackground(tblCalls.getTableHeader().getBackground());
		scrCalls.setVisible(true);

		panelCallDesc = new JPanel();
		panelCallDesc.setBounds(scrCalls.getX(), scrCalls.getY() + scrCalls.getHeight() + 3, 680, 50);
		panelCallDesc.setLayout(null);
		panelCallDesc.setBackground(color7);
		// panelCallDesc.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelCallDesc.setVisible(true);

		txtCallDesc = new JTextArea("");
		txtCallDesc.setBounds(0, 0, 680, 50);
		txtCallDesc.setBackground(color7);
		txtCallDesc.setBorder(BorderFactory.createEtchedBorder(color7, color7));
		txtCallDesc.setFont(CustomFonts.fontCalibriBold);
		txtCallDesc.setForeground(color4);
		txtCallDesc.setLineWrap(true);
		txtCallDesc.setEditable(false);
		txtCallDesc.setVisible(true);

		JScrollPane scrollReasonPane = new JScrollPane(txtCallDesc);
		scrollReasonPane.setBounds(0, 0, 680, 50);
		scrollReasonPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollReasonPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollReasonPane);

		panelContent.add(scrCalls);
		panelContent.add(panelCallDesc);
		panelCallDesc.add(scrollReasonPane);

		return panelContent;

	}

	private List<JilabaColumn> getCalls() {

		List<JilabaColumn> jilabaColumnlist = new ArrayList<>();
		jilabaColumnlist.add(new JilabaColumn("CallNo", String.class, 150, JLabel.RIGHT));
		jilabaColumnlist.add(new JilabaColumn("Call Date ", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn("Client ", String.class, 200, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Module ", String.class, 150, JLabel.LEFT));
		// jilabaColumnlist.add(new JilabaColumn("Explanation ", String.class, 150,
		// JLabel.LEFT));
		// jilabaColumnlist.add(new JilabaColumn("Sug.To ", String.class, 150,
		// JLabel.LEFT));
		// jilabaColumnlist.add(new JilabaColumn("Dev Hrs ", String.class, 100,
		// JLabel.LEFT));
		// jilabaColumnlist.add(new JilabaColumn("Assn Date ", String.class, 150,
		// JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Prop Dev ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Dev Co-Ord ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Cust Co-Ord ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Option", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Ticket No", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Description", String.class, 200, JLabel.LEFT));

		return jilabaColumnlist;

	}

	private Component panelDevCallSummary() {

		panelDevSummary = new JPanel();
		panelDevSummary.setBounds(panelDetail2.getX(), panelDetail2.getY() + panelDetail2.getHeight() + 30, 200, 430);
		panelDevSummary.setLayout(null);
		panelDevSummary.setBackground(color2);
		panelDevSummary.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelDevSummary.setVisible(true);

		lblDevHead = new JLabel("DEVELOPER PENDING SUMMARY");
		lblDevHead.setBounds(40, 0, 130, 30);
		lblDevHead.setBackground(color2);
		lblDevHead.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		lblDevHead.setVisible(true);

		tblDevCalls = new JilabaTable(getDevCalls());
		tblDevCalls.setAutoResizeMode(JilabaTable.AUTO_RESIZE_OFF);
		tblDevCalls.getTableHeader().setReorderingAllowed(false);
		tblDevCalls.getTableHeader().setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		tblDevCalls.setFont(CustomFonts.fontCalibriPlain15);
		tblDevCalls.getTableHeader().setForeground(color3);
		tblDevCalls.getTableHeader().setBackground(Color.WHITE);
		tblDevCalls.setForeground(color4);
		tblDevCalls.setRowHeight(20);
		tblDevCalls.setVisible(true);
		tblDevCalls.addKeyListener(txtCallNo);

		scrDevCalls = new JScrollPane(tblDevCalls);
		scrDevCalls.setBounds(10, 30, 180, 120);
		scrDevCalls.getViewport().setBackground(tblDevCalls.getTableHeader().getBackground());
		scrDevCalls.setVisible(true);

		lblCallHead = new JLabel("CALL DETAILS");
		lblCallHead.setBounds(70, scrDevCalls.getY() + scrDevCalls.getHeight(), 130, 30);
		lblCallHead.setBackground(color2);
		lblCallHead.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		lblCallHead.setVisible(true);

		panelCallDetail = new JPanel();
		panelCallDetail.setBounds(10, lblCallHead.getY() + lblCallHead.getHeight(), 180, 245);
		panelCallDetail.setLayout(null);
		panelCallDetail.setBackground(color2);
		panelCallDetail.setBorder(BorderFactory.createEtchedBorder(color6, color6));
		panelCallDetail.setVisible(true);

		lblDevCallNo = new JLabel("CallNo\\TktNo");
		lblDevCallNo.setBounds(10, 0, 130, 30);
		lblDevCallNo.setBackground(color2);
		lblDevCallNo.setFont(CustomFonts.fontCalibriBold);
		lblDevCallNo.setForeground(color4);
		lblDevCallNo.setVisible(true);

		lblDevCallNoVal = new JLabel("0");
		lblDevCallNoVal.setBounds(lblDevCallNo.getX() + 20, lblDevCallNo.getY(), 130, 30);
		lblDevCallNoVal.setBackground(color2);
		lblDevCallNoVal.setForeground(color4);
		lblDevCallNoVal.setFont(CustomFonts.fontCalibriBold);
		lblDevCallNoVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDevCallNoVal.setVisible(true);

		lblDevCallCoOrd = new JLabel("Call Co-Ord");
		lblDevCallCoOrd.setBounds(10, lblDevCallNo.getY() + lblDevCallNo.getHeight(), 130, 30);
		lblDevCallCoOrd.setBackground(color2);
		lblDevCallCoOrd.setForeground(color4);
		lblDevCallCoOrd.setFont(CustomFonts.fontCalibriBold);
		lblDevCallCoOrd.setVisible(true);

		lblDevCallCoOrdVal = new JLabel("0");
		lblDevCallCoOrdVal.setBounds(lblDevCallCoOrd.getX() + 20, lblDevCallCoOrd.getY(), 130, 30);
		lblDevCallCoOrdVal.setBackground(color2);
		lblDevCallCoOrdVal.setFont(CustomFonts.fontCalibriBold);
		lblDevCallCoOrdVal.setForeground(color4);
		lblDevCallCoOrdVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDevCallCoOrdVal.setVisible(true);

		lblDevCustCoOrd = new JLabel("Cust Co-Ord");
		lblDevCustCoOrd.setBounds(10, lblDevCallCoOrd.getY() + lblDevCallCoOrd.getHeight(), 130, 30);
		lblDevCustCoOrd.setBackground(color2);
		lblDevCustCoOrd.setForeground(color4);
		lblDevCustCoOrd.setFont(CustomFonts.fontCalibriBold);
		lblDevCustCoOrd.setVisible(true);

		lblDevCustCoOrdVal = new JLabel("0");
		lblDevCustCoOrdVal.setBounds(lblDevCustCoOrd.getX() + 20, lblDevCustCoOrd.getY(), 130, 30);
		lblDevCustCoOrdVal.setBackground(color2);
		lblDevCustCoOrdVal.setForeground(color4);
		lblDevCustCoOrdVal.setFont(CustomFonts.fontCalibriBold);
		lblDevCustCoOrdVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDevCustCoOrdVal.setVisible(true);

		lblDevRecdBy = new JLabel("Received By");
		lblDevRecdBy.setBounds(10, lblDevCustCoOrd.getY() + lblDevCustCoOrd.getHeight(), 130, 30);
		lblDevRecdBy.setBackground(color2);
		lblDevRecdBy.setForeground(color4);
		lblDevRecdBy.setFont(CustomFonts.fontCalibriBold);
		lblDevRecdBy.setVisible(true);

		lblDevRecdByVal = new JLabel("0");
		lblDevRecdByVal.setBounds(lblDevRecdBy.getX() + 20, lblDevRecdBy.getY(), 130, 30);
		lblDevRecdByVal.setBackground(color2);
		lblDevRecdByVal.setForeground(color4);
		lblDevRecdByVal.setFont(CustomFonts.fontCalibriBold);
		lblDevRecdByVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDevRecdByVal.setVisible(true);

		lblDevClient = new JLabel("Client");
		lblDevClient.setBounds(10, lblDevRecdBy.getY() + lblDevRecdBy.getHeight(), 130, 30);
		lblDevClient.setBackground(color2);
		lblDevClient.setForeground(color4);
		lblDevClient.setFont(CustomFonts.fontCalibriBold);
		lblDevClient.setVisible(true);

		lblDevClientVal = new JLabel("0");
		lblDevClientVal.setBounds(lblDevClient.getX() + 20, lblDevClient.getY(), 130, 30);
		lblDevClientVal.setBackground(color2);
		lblDevClientVal.setForeground(color4);
		lblDevClientVal.setFont(CustomFonts.fontCalibriBold);
		lblDevClientVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDevClientVal.setVisible(true);

		lblDevModule = new JLabel("Module");
		lblDevModule.setBounds(10, lblDevClient.getY() + lblDevClient.getHeight(), 130, 30);
		lblDevModule.setBackground(color2);
		lblDevModule.setForeground(color4);
		lblDevModule.setFont(CustomFonts.fontCalibriBold);
		lblDevModule.setVisible(true);

		lblDevModuleVal = new JLabel("0");
		lblDevModuleVal.setBounds(lblDevModule.getX() + 20, lblDevModule.getY(), 130, 30);
		lblDevModuleVal.setBackground(color2);
		lblDevModuleVal.setForeground(color4);
		lblDevModuleVal.setFont(CustomFonts.fontCalibriBold);
		lblDevModuleVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDevModuleVal.setVisible(true);

		lblDevNature = new JLabel("Call Nature");
		lblDevNature.setBounds(10, lblDevModule.getY() + lblDevModule.getHeight(), 130, 30);
		lblDevNature.setBackground(color2);
		lblDevNature.setForeground(color4);
		lblDevNature.setFont(CustomFonts.fontCalibriBold);
		lblDevNature.setVisible(true);

		lblDevNatureVal = new JLabel("0");
		lblDevNatureVal.setBounds(lblDevNature.getX() + 20, lblDevNature.getY(), 130, 30);
		lblDevNatureVal.setBackground(color2);
		lblDevNatureVal.setForeground(color4);
		lblDevNatureVal.setFont(CustomFonts.fontCalibriBold);
		lblDevNatureVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDevNatureVal.setForeground(color4);
		lblDevNatureVal.setVisible(true);

		lblImage = new JLabel(" Image Details");
		lblImage.setHorizontalAlignment(SwingConstants.LEFT);
		lblImage.setBounds(lblDevNature.getX(), lblDevNature.getY() + lblDevNature.getHeight() + 5, 130, 20);
		lblImage.setFont(CustomFonts.fontCalibriBold);
		lblImage.setBackground(color2);
		lblImage.setForeground(color4);
		lblImage.setVisible(true);

		btnImage = new JButton("Not Attached");
		btnImage.setBounds(lblImage.getX() + 70, lblImage.getY(), 80, 20);
		btnImage.setBackground(color2);
		btnImage.setForeground(Color.BLACK);
		btnImage.setMnemonic(KeyEvent.VK_I);
		btnImage.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		btnImage.setHorizontalAlignment(SwingConstants.RIGHT);
		btnImage.setBorder(BorderFactory.createEmptyBorder());
		btnImage.setVisible(false);
		btnImage.setEnabled(true);
		btnImage.addActionListener(this);
		btnImage.addKeyListener(this);

		lblPressEsc = new JLabel("Press Esc to Close ");
		lblPressEsc.setHorizontalAlignment(SwingConstants.LEFT);
		lblPressEsc.setBounds(panelDevSummary.getX(), panelDevSummary.getY() + panelDevSummary.getHeight(), 130, 30);
		lblPressEsc.setFont(CustomFonts.fontCalibriBold);
		lblPressEsc.setBackground(color2);
		lblPressEsc.setForeground(color4);
		lblPressEsc.setVisible(true);

		lblShortcutkey = new JLabel("SpaceBar - Assign to Develeloper             I - Image Details To Show   ");
		lblShortcutkey.setHorizontalAlignment(SwingConstants.LEFT);
		lblShortcutkey.setBounds(lblPressEsc.getX() + lblPressEsc.getWidth() + 100, lblPressEsc.getY(), 500, 30);
		lblShortcutkey.setFont(CustomFonts.fontCalibriBold);
		lblShortcutkey.setBackground(color2);
		lblShortcutkey.setForeground(color4);
		lblShortcutkey.setVisible(true);

		panelMain.add(lblPressEsc);
		panelMain.add(lblShortcutkey);
		panelDevSummary.add(lblDevHead);
		panelDevSummary.add(scrDevCalls);
		panelDevSummary.add(lblCallHead);
		panelDevSummary.add(panelCallDetail);
		panelCallDetail.add(lblDevCallNo);
		panelCallDetail.add(lblDevCallCoOrd);
		panelCallDetail.add(lblDevCustCoOrd);
		panelCallDetail.add(lblDevRecdBy);
		panelCallDetail.add(lblDevClient);
		panelCallDetail.add(lblDevModule);
		panelCallDetail.add(lblDevNature);
		panelCallDetail.add(lblDevCallNoVal);
		panelCallDetail.add(lblDevCallCoOrdVal);
		panelCallDetail.add(lblDevCustCoOrdVal);
		panelCallDetail.add(lblDevRecdByVal);
		panelCallDetail.add(lblDevClientVal);
		panelCallDetail.add(lblDevModuleVal);
		panelCallDetail.add(lblDevNatureVal);
		panelCallDetail.add(lblImage);
		panelCallDetail.add(btnImage);

		return panelDevSummary;

	}

	private List<JilabaColumn> getDevCalls() {

		List<JilabaColumn> jilabaColumnlist = new ArrayList<>();
		jilabaColumnlist.add(new JilabaColumn("Developer Name", String.class, 197, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("No.Of.Calls ", String.class, 100, JLabel.LEFT));

		return jilabaColumnlist;

	}

	private Component panelDetail2Initialize() {

		int lblWidth = 130;
		int lblHeight = 30;
		int txtWidth = 120;
		int txtHeight = 25;

		panelDetail2 = new JPanel();
		panelDetail2.setBounds(20, panelLine2.getY() + 10, 918, 100);
		panelDetail2.setLayout(null);
		panelDetail2.setBackground(color2);
		panelDetail2.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelDetail2.setVisible(true);

		lblDevCoOrd = new JLabel("Dev Co-Ordinator");
		lblDevCoOrd.setBounds(10, 10, lblWidth, lblHeight);
		lblDevCoOrd.setBackground(color2);
		lblDevCoOrd.setForeground(Color.BLACK);
		lblDevCoOrd.setFont(CustomFonts.font);
		lblDevCoOrd.setVisible(true);
		lblDevCoOrd.addKeyListener(this);

		lblCallNo = new JLabel("CallNo\\TicketNo");
		lblCallNo.setBounds(lblDevCoOrd.getX(), lblDevCoOrd.getY() + lblDevCoOrd.getHeight(), lblWidth, lblHeight);
		lblCallNo.setBackground(color2);
		lblCallNo.setFont(CustomFonts.font);
		lblCallNo.setForeground(Color.BLACK);
		lblCallNo.setVisible(true);
		lblCallNo.addKeyListener(this);

		lblCustomer = new JLabel("Customer ");
		lblCustomer.setBounds(lblCallNo.getX(), lblCallNo.getY() + lblCallNo.getHeight(), lblWidth, lblHeight);
		lblCustomer.setBackground(color2);
		lblCustomer.setFont(CustomFonts.font);
		lblCustomer.setForeground(Color.BLACK);
		lblCustomer.setVisible(true);
		lblCustomer.addKeyListener(this);

		cmbDevCoOrd = new JilabaComboBox<Operator>();
		cmbDevCoOrd.setBounds(lblDevCoOrd.getX() + 100, lblDevCoOrd.getY(), txtWidth, txtHeight);
		cmbDevCoOrd.setBackground(color2);
		cmbDevCoOrd.setFont(CustomFonts.fontCalibriPlain15);
		cmbDevCoOrd.setVisible(true);
		cmbDevCoOrd.addKeyListener(this);

		txtCallNo = new JilabaTextField();
		txtCallNo.setBounds(cmbDevCoOrd.getX(), lblCallNo.getY(), txtWidth, txtHeight);
		txtCallNo.setBackground(color2);
		txtCallNo.setFont(CustomFonts.fontCalibriPlain15);
		txtCallNo.setVisible(true);
		txtCallNo.setTextType(TextInputType.NUMBER);
		txtCallNo.setMaxLength(10);
		txtCallNo.addKeyListener(this);

		cmbCustomer = new JilabaComboBox<Customer>();
		cmbCustomer.setBounds(txtCallNo.getX(), lblCustomer.getY(), txtWidth, txtHeight);
		cmbCustomer.setBackground(color2);
		cmbCustomer.setFont(CustomFonts.fontCalibriPlain15);
		cmbCustomer.setVisible(true);
		cmbCustomer.addKeyListener(this);

		lblCustCoOrd = new JLabel("Cust Co-Ordinator");
		lblCustCoOrd.setBounds(cmbDevCoOrd.getX() + 130, cmbDevCoOrd.getY(), lblWidth, lblHeight);
		lblCustCoOrd.setBackground(color2);
		lblCustCoOrd.setFont(CustomFonts.font);
		lblCustCoOrd.setForeground(Color.BLACK);
		lblCustCoOrd.setVisible(true);
		lblCustCoOrd.addKeyListener(this);

		lblDepartment = new JLabel("Department");
		lblDepartment.setBounds(lblCustCoOrd.getX(), txtCallNo.getY(), lblWidth, lblHeight);
		lblDepartment.setBackground(color2);
		lblDepartment.setFont(CustomFonts.font);
		lblDepartment.setForeground(Color.BLACK);
		lblDepartment.setVisible(true);
		lblDepartment.addKeyListener(this);

		lblDeptAuthorize = new JLabel("Dept Authorize");
		lblDeptAuthorize.setBounds(lblDepartment.getX(), cmbCustomer.getY(), lblWidth, lblHeight);
		lblDeptAuthorize.setBackground(color2);
		lblDeptAuthorize.setFont(CustomFonts.font);
		lblDeptAuthorize.setForeground(Color.BLACK);
		lblDeptAuthorize.setVisible(true);
		lblDeptAuthorize.addKeyListener(this);

		cmbCustCoOrd = new JilabaComboBox<Operator>();
		cmbCustCoOrd.setBounds(lblCustCoOrd.getX() + 100, lblCustCoOrd.getY(), txtWidth, txtHeight);
		cmbCustCoOrd.setBackground(color2);
		cmbCustCoOrd.setFont(CustomFonts.fontCalibriPlain15);
		cmbCustCoOrd.setVisible(true);
		cmbCustCoOrd.addKeyListener(this);

		cmbDepartment = new JilabaComboBox<Department>();
		cmbDepartment.setBounds(cmbCustCoOrd.getX(), lblDepartment.getY(), txtWidth, txtHeight);
		cmbDepartment.setBackground(color2);
		cmbDepartment.setFont(CustomFonts.fontCalibriPlain15);
		cmbDepartment.setVisible(true);
		cmbDepartment.addKeyListener(this);

		cmbDeptAuthor = new JilabaComboBox<Operator>();
		cmbDeptAuthor.setBounds(cmbDepartment.getX(), lblDeptAuthorize.getY(), txtWidth, txtHeight);
		cmbDeptAuthor.setBackground(color2);
		cmbDeptAuthor.setFont(CustomFonts.fontCalibriPlain15);
		cmbDeptAuthor.setVisible(true);
		cmbDeptAuthor.addKeyListener(this);

		/* KSJDK */

		lblRecvFrom = new JLabel("Received From");
		lblRecvFrom.setBounds(cmbCustCoOrd.getX() + 130, cmbCustCoOrd.getY(), lblWidth, lblHeight);
		lblRecvFrom.setBackground(color2);
		lblRecvFrom.setFont(CustomFonts.font);
		lblRecvFrom.setForeground(Color.BLACK);
		lblRecvFrom.setVisible(true);
		lblRecvFrom.addKeyListener(this);

		lblModule = new JLabel("Module");
		lblModule.setBounds(lblRecvFrom.getX(), txtCallNo.getY(), lblWidth, lblHeight);
		lblModule.setBackground(color2);
		lblModule.setFont(CustomFonts.font);
		lblModule.setForeground(Color.BLACK);
		lblModule.setVisible(true);
		lblModule.addKeyListener(this);

		lblCallNature = new JLabel("Call Nature");
		lblCallNature.setBounds(lblModule.getX(), cmbCustomer.getY(), lblWidth, lblHeight);
		lblCallNature.setBackground(color2);
		lblCallNature.setFont(CustomFonts.font);
		lblCallNature.setForeground(Color.BLACK);
		lblCallNature.setVisible(true);
		lblCallNature.addKeyListener(this);

		cmbRecvFrom = new JilabaComboBox<Operator>();
		cmbRecvFrom.setBounds(lblRecvFrom.getX() + 100, lblRecvFrom.getY(), txtWidth, txtHeight);
		cmbRecvFrom.setBackground(color2);
		cmbRecvFrom.setFont(CustomFonts.fontCalibriPlain15);
		cmbRecvFrom.setVisible(true);
		cmbRecvFrom.addKeyListener(this);

		cmbModule = new JilabaComboBox<Module>();
		cmbModule.setBounds(cmbRecvFrom.getX(), lblModule.getY(), txtWidth, txtHeight);
		cmbModule.setBackground(color2);
		cmbModule.setFont(CustomFonts.fontCalibriPlain15);
		cmbModule.setVisible(true);
		cmbModule.addKeyListener(this);

		cmbCallNature = new JilabaComboBox<String>();
		cmbCallNature.setBounds(cmbModule.getX(), lblCallNature.getY(), txtWidth, txtHeight);
		cmbCallNature.setBackground(color2);
		cmbCallNature.setFont(CustomFonts.fontCalibriPlain15);
		cmbCallNature.setVisible(true);
		cmbCallNature.addKeyListener(this);

		btnView = new JButton("View");
		btnView.setBounds(cmbCallNature.getX() + 170, cmbCallNature.getY(), 50, txtHeight);
		btnView.setBackground(color3);
		btnView.setMnemonic(KeyEvent.VK_V);
		btnView.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnView.setVisible(true);
		btnView.addKeyListener(this);
		btnView.addActionListener(this);

		btnClear = new JButton("Clear");
		btnClear.setBounds(btnView.getX() + 80, btnView.getY(), 50, txtHeight);
		btnClear.setBackground(color3);
		btnClear.setMnemonic(KeyEvent.VK_C);
		btnClear.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		btnClear.setVisible(true);
		btnClear.addKeyListener(this);
		btnClear.addActionListener(this);

		panelDetail2.add(lblDevCoOrd);
		panelDetail2.add(lblCallNo);
		panelDetail2.add(lblCustomer);
		panelDetail2.add(cmbDevCoOrd);
		panelDetail2.add(txtCallNo);
		panelDetail2.add(cmbCustomer);
		panelDetail2.add(lblCustCoOrd);
		panelDetail2.add(lblDepartment);
		panelDetail2.add(lblDeptAuthorize);
		panelDetail2.add(cmbCustCoOrd);
		panelDetail2.add(cmbDepartment);
		panelDetail2.add(cmbDeptAuthor);
		panelDetail2.add(lblRecvFrom);
		panelDetail2.add(lblModule);
		panelDetail2.add(lblCallNature);
		panelDetail2.add(cmbRecvFrom);
		panelDetail2.add(cmbModule);
		panelDetail2.add(cmbCallNature);
		panelDetail2.add(btnView);
		panelDetail2.add(btnClear);

		panelMain.add(panelDetail2);
		return panelDetail2;

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
		CommonMethods.setIcon(ImageResource.developedBy, lblDevelopedby);
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

		panelMain.add(panelLine2);

		return panelLine2;
	}

	private Component panelDetailInitialize() {

		panelDetail = new JPanel();
		panelDetail.setBounds(panelLine.getX(), panelLine.getY() + 30, 958, 30);
		panelDetail.setLayout(null);
		panelDetail.setBackground(color2);
		panelDetail.setVisible(true);

		lblHeading = new JLabel("DEVELOPER CALL ASSIGN");
		lblHeading.setBounds(20, -10, 170, 50);
		// lblHeading.setBounds(panelDetail.getWidth() / 2, panelDetail.getY() / 2, 20,
		// 20);
		lblHeading.setFont(jilabaFonts.getFont(FontStyle.BOLD, 23));
		lblHeading.setForeground(fontColor1);
		lblHeading.setVisible(true);

		panelDetail.add(lblHeading);

		panelMain.add(panelDetail);
		return panelLine;
	}

	private Component panelLineInialize() {

		panelLine = new JPanel();
		panelLine.setBounds(0, 30, 958, 6);
		panelLine.setLayout(null);
		panelLine.setBackground(color3);
		panelLine.setVisible(true);

		panelMain.add(panelLine);
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
		CommonMethods.setIcon(ImageResource.SERVERIPICON, lblOperatorLabel);
		lblOperatorLabel.setVisible(true);

		lblVersion = new JLabel(Applicationmain.VERSION);
		lblVersion.setBounds(170, 5, 100, 22);
		lblVersion.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		lblVersion.setForeground(color2);
		CommonMethods.setIcon(ImageResource.VERSIONICON, lblVersion);
		lblVersion.setVisible(true);

		lblServerIpValue = new JLabel(LoginCredential.getLocalIpAdd());
		lblServerIpValue.setBounds(620, 5, 120, 22);
		lblServerIpValue.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		lblServerIpValue.setForeground(color2);
		CommonMethods.setIcon(ImageResource.LOCALIPICON, lblServerIpValue);
		lblServerIpValue.setVisible(true);

		String localdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm:ss a"));
		lblDateTime = new JLabel(localdate);
		lblDateTime.setBounds(780, 5, 170, 22);
		lblDateTime.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		lblDateTime.setForeground(color2);
		lblDateTime.setHorizontalAlignment(JLabel.RIGHT);
		CommonMethods.setIcon(ImageResource.DATEICON, lblDateTime);
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

			if (e.getSource() == cmbDevCoOrd) {

				txtCallNo.requestFocus();

			} else if (e.getSource() == txtCallNo) {
				String callNoStr = txtCallNo.getText().trim();

				if (!callNoStr.equals("")) {
					strCallNo = Long.valueOf(callNoStr);
					// strCallNo = Integer.valueOf(String.valueOf(txtCallNo.getText().trim()));
					cmbCustomer.requestFocus();
				}
			} else if (e.getSource() == cmbCustomer) {
				cmbCustCoOrd.requestFocus();
			} else if (e.getSource() == cmbCustCoOrd) {
				cmbDepartment.requestFocus();

			} else if (e.getSource() == cmbDepartment) {

				/*
				 * if (cmbDepartment.getSelectedItem().equals("All")) {} else {
				 * strDeptParam.clear(); cmbModule.removeAllItems();
				 * strDeptParam.add(cmbDepartment.getSelectedItemValue()); }
				 * 
				 * lstModule = logicDevCallAssign.getModule(strDeptParam); strModule = new
				 * ArrayList<Object>();
				 * 
				 * for (com.jilaba.calls.model.Module module : lstModule) {
				 * 
				 * strModule.add(module.getModuleId());
				 * 
				 * cmbModule.addListItem(new ListItem(module.getModuleName(),
				 * module.getModuleId())); }
				 */

				lstModule = logicDevCallAssign
						.getModule(Integer.valueOf(String.valueOf(cmbDepartment.getSelectedItemValue())));

				cmbModule.removeAllItems();
				cmbModule.addListItem(new ListItem("All", 0));

				for (com.jilaba.calls.model.Module module : lstModule) {

					cmbModule.addListItem(new ListItem(module.getModuleName(), module.getModuleId()));
				}

				cmbDeptAuthor.requestFocus();

			} else if (e.getSource() == cmbDeptAuthor) {
				cmbRecvFrom.requestFocus();
			} else if (e.getSource() == cmbRecvFrom) {
				cmbModule.requestFocus();
			} else if (e.getSource() == cmbModule) {
				cmbCallNature.requestFocus();
			} else if (e.getSource() == cmbCallNature) {
				btnView.requestFocus();
			}

		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

			setVisible(false);

			FrmMainMenu frmMainMenu = Applicationmain.getAbstractApplicationContext().getBean(FrmMainMenu.class);
			frmMainMenu.setVisible(true);

		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

			if (e.getSource() == btnView) {
				btnClear.requestFocus();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {

			if (e.getSource() == btnClear) {
				btnView.requestFocus();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {

			if (e.getSource() == btnView) {
				tblCalls.requestFocus();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnView) {

			btnView();

		} else if (e.getSource() == btnClear) {
			loadDetails();
		}

		if (e.getSource() == btnImage) {

			btnImageCall();

		}

		tblCalls.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (tblCalls.getRowCount() <= 0 || tblCalls.getSelectedRow() < 0 || event.getValueIsAdjusting())
					return;
				showProductDetails(lstCalls.get(tblCalls.getSelectedRow()));
			}

			private void showProductDetails(Calls calls) {

				lblDevCallNoVal.setText(String.valueOf(calls.getCallno()));
				lblDevCallCoOrdVal.setText(calls.getCallcoordinator());
				lblDevCustCoOrdVal.setText(calls.getCustcordinator_name());
				lblDevRecdByVal.setText(String.valueOf(calls.getReceiverName()));
				lblDevClientVal.setText(calls.getLstcustCustomer().get(0).getcustName().trim());
				lblDevModuleVal.setText(calls.getLstModule().get(0).getModuleName());
				lblDevNatureVal.setText(calls.getCallnature());

				if (calls.getImgAttach() == null) {

					btnImage.setText("Not Attached");
					btnImage.setForeground(Color.BLACK);

				} else {
					btnImage.setText("Attached");
					btnImage.setForeground(color8);
				}

				btnImage.setVisible(true);
				txtCallDesc.setText(
						" Option -> " + calls.getMoption() + "\r\n " + "Call -> " + calls.getDescription().trim());

			}
		});

	}

	private void btnImageCall() {
		CallsImages callsImages = logicDevCalls.getImages(String.valueOf(lblDevCallNoVal.getText()));

		blnFrmDevCall = true;
		FrmImageDialog frmImageDialog = Applicationmain.getAbstractApplicationContext().getBean(FrmImageDialog.class,
				new Object[] { getContentPane() });
		frmImageDialog.booleanAssign(false, blnFrmDevCall, (String.valueOf(lblDevCallNoVal.getText())), callsImages);
		frmImageDialog.setVisible(true);

	}

	public void btnView() {

		// loadDetails();

		tblCalls.clear();
		tblDevCalls.clear();
		loadDevCalls();

		chkSelectAll();

		lstCalls = logicDevCallAssign.getCalls(strDevCoOrd, strCallNo, strCustomer, strCustCoOrd, strDepartment,
				strDeptAuthorize, strRecvFrom, strModule, strCallNature);

		lblTotalcall.setVisible(true);
		lblTotalcallVal.setText(String.valueOf(lstCalls.size() + 1));

		List<Object> lstObject = null;

		for (Calls call : lstCalls) {

			lstObject = new ArrayList<Object>();

			lstObject.add(call.getCallno());
			lstObject.add(call.getCdate());
			lstObject.add(call.getLstCustomer().get(0).getcustName().trim());
			lstObject.add(call.getLstModule().get(0).getModuleName().trim());
			// lstObject.add("");
			// lstObject.add("");
			// lstObject.add("0");
			// lstObject.add("");
			lstObject.add("");
			lstObject.add(call.getCallcoordinator().trim());
			lstObject.add(call.getCustcordinator_name().trim());
			lstObject.add(call.getMoption().trim());
			lstObject.add(call.getTicketno());
			lstObject.add(call.getDescription().trim());

			tblCalls.addRow(lstObject);

		}

		strCallNo = 0;

		if (lstCalls.size() == 0) {

			JOptionPane.showMessageDialog(panelMain, "Call Not Found !");
			loadDetails();
			btnView.requestFocus();
			return;

		}

	}

	private void chkSelectAll() {

		if (cmbDevCoOrd.getSelectedItem().equals("All")) {
			strDevCoOrd = 0;
		} else {
			strDevCoOrd = Integer.parseInt(String.valueOf(cmbDevCoOrd.getSelectedItemValue()));
		}
		if (cmbCustomer.getSelectedItem().equals("All")) {
			strCustomer = 0;
		} else {
			strCustomer = Integer.parseInt(String.valueOf(cmbCustomer.getSelectedItemValue()));
		}
		if (cmbCustCoOrd.getSelectedItem().equals("All")) {
			strCustCoOrd = 0;
		} else {
			strCustCoOrd = Integer.parseInt(String.valueOf(cmbCustCoOrd.getSelectedItemValue()));
		}
		if (cmbDepartment.getSelectedItem().equals("All")) {
			strDepartment = 0;
		} else {
			strDepartment = Integer.parseInt(String.valueOf(cmbDepartment.getSelectedItemValue()));
		}
		if (cmbDeptAuthor.getSelectedItem().equals("All")) {
			strDeptAuthorize = 0;
		} else {
			strDeptAuthorize = Integer.parseInt(String.valueOf(cmbDeptAuthor.getSelectedItemValue()));
		}
		if (cmbRecvFrom.getSelectedItem().equals("All")) {
			strRecvFrom = 0;
		} else {
			strRecvFrom = Integer.parseInt(String.valueOf(cmbRecvFrom.getSelectedItemValue()));
		}
		if (cmbModule.getSelectedItem().equals("All")) {
			strModule = 0;
		} else {
			strModule = Integer.parseInt(String.valueOf(cmbModule.getSelectedItemValue()));
		}
		if (cmbCallNature.getSelectedItem().equals("All")) {
			strCallNature = 0;
		} else {
			strCallNature = Integer.parseInt(String.valueOf(cmbCallNature.getSelectedItemValue()));
		}

	}

}
