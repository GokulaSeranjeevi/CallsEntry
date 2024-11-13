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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.common.ImageResources;
import com.jilaba.calls.common.LoginCredential;
import com.jilaba.calls.common.TimerJob;
import com.jilaba.calls.logic.LogicCallsCompleted;
import com.jilaba.calls.logic.LogicDevCalls;
import com.jilaba.calls.model.CallsCompleted;
import com.jilaba.calls.model.CallsImages;
import com.jilaba.calls.model.Customer;
import com.jilaba.calls.model.Department;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.model.ReadyCalls;
import com.jilaba.calls.start.Applicationmain;
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

@org.springframework.stereotype.Component
@Scope("prototype")
public class FrmCallsCompleted extends JFrame implements ActionListener, KeyListener {

	private JPanel panelMain;
	private JPanel panelTitle;
	private JPanel panelLine;
	private JPanel panelDetail;
	private JPanel panelLine2;
	private JPanel panelLine3;
	private JPanel panelDetail2;
	private JPanel panelCallDate;
	private JPanel panelCompletedDate;
	private JPanel panelOrderby;
	private JPanel panelCompleted;
	private JPanel panelCallDesc;

	private JLabel lblDevelopedby;
	private JLabel lblLocalIpValue;
	private JLabel lblVersion;
	private JLabel lblServerIpValue;
	private JLabel lblDateTime;

	private JLabel lblCallFromDate;
	private JLabel lblCallToDate;
	private JLabel lblCompletedFromDate;
	private JLabel lblCompletedToDate;
	private JLabel lblCall;
	private JLabel lblCompleted;
	private JLabel lblDeveloper;
	private JLabel lblClient;
	private JLabel lblTester;
	private JLabel lblDepartment;
	private JLabel lblDepartmentVal;
	private JLabel lblModule;
	private JLabel lblCallNo;
	private JLabel lblOperatorLabel;
	private JLabel lblMinimize;

	private JLabel lblDesc;
	private JLabel lblClCallNo;
	private JLabel lblClCallNoVal;
	private JLabel lblCallTime;
	private JLabel lblCallDate;
	private JLabel lblCallDateVal;
	private JLabel lblClModule;
	private JLabel lblClModuleVal;
	private JLabel lblCallDesc;
	private JLabel lblCallDescVal;
	private JLabel lblCompletedDesc;
	private JLabel lblCompletedDescVal;
	private JLabel lblPressEsc;

	private int strCmbDeveloper;
	private int strCmbClient;
	private int strCmbDeptAuthorize;
	private int strCmbDepartment;
	private int strCmbModule;
	private String strOrderby;
	private String strCallFromDate;
	private String strCallToDate;
	private long strCallNo;

	private String strCompletedFromDate;
	private String strCompletedToDate;

	private JilabaTextField txtCallNo;

	private JRadioButton rdpAsOnDate;
	private JRadioButton rdpBetweenDate;
	private JilabaSpinner spnCallFromDate;
	private JilabaSpinner spnCallToDate;
	private JilabaSpinner spnCompletedFromDate;
	private JilabaSpinner spnCompletedToDate;

	private JilabaTable tblCompletedCalls;
	private JScrollPane scrCompletedCalls;

	private JilabaComboBox<Operator> cmbDeveloper;
	private JilabaComboBox<Customer> cmbClient;
	private JilabaComboBox<Operator> cmbTester;
	private JilabaComboBox<Department> cmbDepartment;
	private JilabaComboBox<Module> cmbModule;

	private boolean blnAsOnDate = false;

	private JRadioButton rdpNone;
	private JRadioButton rdpModule;
	private JRadioButton rdpTester;
	private JRadioButton rdpClient;
	private JRadioButton rdpCallDate;
	private JRadioButton rdpCompletedDate;
	private JRadioButton rdpCallNo;

	private ButtonGroup btnGroup = new ButtonGroup();
	private ButtonGroup btnOrderbyGroup = new ButtonGroup();

	private List<Operator> lstDeveloper;
	private List<Customer> lstCustomer;
	private List<Operator> lstDeptAuthorize;
	private List<Department> lstDepartment;
	private List<com.jilaba.calls.model.Module> lstModule;
	private List<ReadyCalls> lstReadyCalls;

	private List<Object> strDeveloper;
	private List<Object> strCustomer;
	private List<Object> strDeptAuthorize;
	private List<Object> strDepartment;
	private List<Object> strModule;

	private JButton btnView;
	private JButton btnClear;

	private JLabel lblHeading;

	private JilabaFonts jilabaFonts = new JilabaFonts();
	private Color fontColor1 = Color.decode("#17202A");

	private ControlResize controlResize;
	private List<CallsCompleted> lstCompletedCalls;

	private Color color1 = Color.decode("#8E7460");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#A38D7D");
	private Color color4 = Color.decode("#F1E8E2");
	private Color color5 = Color.decode("#e4dedf");
	private Color color6 = Color.decode("#000000");

	@Autowired
	private LogicCallsCompleted logicCallsCompleted;
	@Autowired
	private LogicDevCalls logicDevCalls;
	@Autowired
	private CommonMethods commonMethods;

	public FrmCallsCompleted() {

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

	@PostConstruct
	private void loadDetails() {

		lblServerIpValue.setText(commonMethods.getIpAddress());
		rdpAsOnDate.setSelected(true);
		rdpBetweenDate.setSelected(false);
		rdpBetweenDate.setVisible(true);
		lblCallFromDate.setVisible(true);
		lblCallFromDate.setText("From Date");
		lblCallToDate.setVisible(true);
		lblCompletedFromDate.setText("From Date");
		lblCompletedFromDate.setVisible(true);
		lblCompletedToDate.setVisible(true);
		spnCallToDate.setVisible(true);
		spnCompletedToDate.setVisible(true);
		blnAsOnDate = false;
		cmbDeveloper.removeAllItems();
		cmbClient.removeAllItems();
		cmbTester.removeAllItems();
		cmbDepartment.removeAllItems();
		cmbModule.removeAllItems();
		txtCallNo.setText("");
		rdpFalse();
		rdpNone.setSelected(true);

		cmbDeveloper.addListItem(new ListItem("All"));
		cmbClient.addListItem(new ListItem("All"));
		cmbDepartment.addListItem(new ListItem("All"));
		cmbTester.addListItem(new ListItem("All"));
		cmbModule.addListItem(new ListItem("All"));

		lstDeveloper = logicCallsCompleted.getDeveloper();
		//		strDeveloper = new ArrayList<Object>();

		for (Operator dev : lstDeveloper) {

			//			strDeveloper.add(dev.getStaffid());
			cmbDeveloper.addListItem(new ListItem(dev.getStaffname(), dev.getStaffid()));

		}

		lstCustomer = logicCallsCompleted.getCustomer();
		//		strCustomer = new ArrayList<Object>();

		for (Customer cus : lstCustomer) {

			//			strCustomer.add(cus.getCustId());
			cmbClient.addListItem(new ListItem(cus.getcustName().trim(), cus.getCustId()));

		}

		lstDeptAuthorize = logicCallsCompleted.getDeptAuthorize();
		//		strDeptAuthorize = new ArrayList<Object>();

		for (Operator deptAuth : lstDeptAuthorize) {

			//			strDeptAuthorize.add(deptAuth.getStaffid());
			cmbTester.addListItem(new ListItem(deptAuth.getStaffname(), deptAuth.getStaffid()));

		}

		lstDepartment = logicCallsCompleted.getDepartment();
		//		strDepartment = new ArrayList<Object>();

		for (Department dept : lstDepartment) {

			//			strDepartment.add(dept.getdNo());
			cmbDepartment.addListItem(new ListItem(dept.getDepartment(), dept.getdNo()));

		}
		lstModule = logicCallsCompleted.getModule(0);
		//		strModule = new ArrayList<Object>();

		for (com.jilaba.calls.model.Module module : lstModule) {

			//			strModule.add(module.getModuleId());
			cmbModule.addListItem(new ListItem(module.getModuleName(), module.getModuleId()));

		}

	}

	private void rdpFalse() {

		rdpNone.setSelected(false);
		rdpModule.setSelected(false);
		rdpTester.setSelected(false);
		rdpClient.setSelected(false);
		rdpCallDate.setSelected(false);
		rdpCompletedDate.setSelected(false);
		rdpCallNo.setSelected(false);
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

					if (blnAsOnDate == false) {
						spnCallToDate.requestFocus();
					} else {

						lblCompletedFromDate.setText("As On Date");
						lblCompletedToDate.setVisible(false);
						spnCompletedToDate.setVisible(false);
						spnCompletedFromDate.requestFocus();
					}

				}

			}
		});

		spnCallToDate.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					spnCompletedFromDate.requestFocus();

				}

			}
		});
		spnCompletedFromDate.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (blnAsOnDate == false) {
						spnCompletedToDate.requestFocus();
					} else {
						cmbDeveloper.requestFocus();
						blnAsOnDate = false;
					}

				}

			}
		});
		spnCompletedToDate.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cmbDeveloper.requestFocus();

				}

			}
		});

		tblCompletedCalls.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent event) {

				if (tblCompletedCalls.getRowCount() <= 0 || tblCompletedCalls.getSelectedRow() < 0
						|| event.getValueIsAdjusting())
					return;
				showProductDetails(lstCompletedCalls.get(tblCompletedCalls.getSelectedRow()));

			}

			private void showProductDetails(CallsCompleted callCompleted) {

				lblDepartmentVal.setText(callCompleted.getDepartment());
				lblClCallNoVal.setText(String.valueOf(callCompleted.getCallNo()));
				lblCallDateVal.setText(String.valueOf(callCompleted.getCallDate()));
				lblClModuleVal.setText(callCompleted.getModule());
				lblCallDescVal.setText(callCompleted.getCallDescription());
				lblCompletedDescVal.setText(callCompleted.getTestDescription());

			}
		});

		rdpAsOnDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				rdpBetweenDate.setSelected(false);
				rdpAsOnDate.requestFocus();
				rdpAsOnDate.setSelected(true);

			}
		});

		rdpBetweenDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				rdpAsOnDate.setSelected(false);
				rdpBetweenDate.requestFocus();
				rdpBetweenDate.setSelected(true);
			}
		});

		rdpAsOnDate.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				rdpBetweenDate.setSelected(false);
				rdpAsOnDate.requestFocus();
				rdpAsOnDate.setSelected(true);
			}

		});

		rdpBetweenDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				rdpAsOnDate.setSelected(false);
				rdpBetweenDate.requestFocus();
				rdpBetweenDate.setSelected(true);
			}
		});

		rdpNone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				selectRadioButton(rdpNone);

			}
		});
		rdpModule.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				selectRadioButton(rdpModule);

			}
		});
		rdpTester.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				selectRadioButton(rdpTester);
			}
		});
		rdpClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				selectRadioButton(rdpClient);

			}
		});
		rdpCallDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				selectRadioButton(rdpCallDate);

			}
		});
		rdpCompletedDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				selectRadioButton(rdpCompletedDate);

			}
		});
		rdpCallNo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				selectRadioButton(rdpCallNo);
			}
		});

		tblCompletedCalls.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_I) {

					btnImage();
				} else if (e.getKeyCode() == KeyEvent.VK_D) {

					int callNo = Integer.valueOf(String
							.valueOf(tblCompletedCalls.getModel().getValueAt(tblCompletedCalls.getSelectedRow(), 0)));

					//					String moduleid = String
					//							.valueOf(tblCompletedCalls.getModel().getValueAt(tblCompletedCalls.getSelectedRow(), 8));

					boolean blnFrmcompletedcall = true;
					FrmCompletedDialog frmCompletedDialog = Applicationmain.getAbstractApplicationContext()
							.getBean(FrmCompletedDialog.class, new Object[] { getContentPane() });
					frmCompletedDialog.intializeVariable(callNo, "", blnFrmcompletedcall);
					frmCompletedDialog.setVisible(true);

					blnFrmcompletedcall = false;

					btnView();

				}

				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

					setVisible(false);
					FrmMainMenu frmMainMenu = Applicationmain.getAbstractApplicationContext()
							.getBean(FrmMainMenu.class);
					frmMainMenu.setVisible(true);

				}

			}
		});
		/*panelMain.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
		
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		
					setVisible(false);
					FrmMainMenu frmMainMenu = Applicationmain.getAbstractApplicationContext()
							.getBean(FrmMainMenu.class);
					frmMainMenu.setVisible(true);
		
				}
		
			}
		});*/

	}

	protected void btnImage() {
		CallsImages callsImages = logicDevCalls.getImages(String.valueOf(lblClCallNoVal.getText()));

		boolean blnFrmDevCall = true;
		FrmImageDialog frmImageDialog = Applicationmain.getAbstractApplicationContext().getBean(FrmImageDialog.class,
				new Object[] { getContentPane() });
		frmImageDialog.booleanAssign(false, blnFrmDevCall, (String.valueOf(lblClCallNoVal.getText())), callsImages);
		frmImageDialog.setVisible(true);

	}

	protected void btnView() {

		dateCheck();

		selectAllCheck();

		orderByCheck();

		tblCompletedCalls.clear();

		lstCompletedCalls = logicCallsCompleted.getCompletedCalls(strCallFromDate, strCallToDate, strCompletedFromDate,
				strCompletedToDate, strCmbDeveloper, strCmbClient, strCmbDeptAuthorize, strCmbDepartment, strCmbModule,
				strOrderby, txtCallNo.getText());

		List<Object> lstObjects = null;

		for (CallsCompleted callCompleted : lstCompletedCalls) {

			lstObjects = new ArrayList<Object>();

			lstObjects.add(callCompleted.getCallNo());
			lstObjects.add(callCompleted.getCallDate());
			lstObjects.add(callCompleted.getTestedDate());
			lstObjects.add(callCompleted.getTestedby());
			lstObjects.add(callCompleted.getClientName().trim());
			lstObjects.add(callCompleted.getRecbyName());
			lstObjects.add(callCompleted.getAuthorized());
			lstObjects.add(callCompleted.getDepartment());
			lstObjects.add(callCompleted.getModule());
			lstObjects.add(callCompleted.getCallDescription());
			lstObjects.add(callCompleted.getTestDescription());

			tblCompletedCalls.addRow(lstObjects);
		}

		strCallNo = 0;
		if (lstCompletedCalls.size() == 0) {
			JOptionPane.showMessageDialog(panelMain, "Call Details Not Found !...");
			btnView.requestFocus();
			return;
		}

	}

	private void orderByCheck() {

		if (rdpNone.isSelected() == true) {
			strOrderby = "";
		} else if (rdpModule.isSelected() == true) {
			strOrderby = "c.moduleid";
		} else if (rdpTester.isSelected() == true) {
			strOrderby = "c.sugto";
		} else if (rdpClient.isSelected() == true) {
			strOrderby = "c.cusid";
		} else if (rdpCallDate.isSelected() == true) {
			strOrderby = "c.cdate";
		} else if (rdpCompletedDate.isSelected() == true) {
			strOrderby = "c.testdate";
		} else if (rdpCallNo.isSelected() == true) {
			strOrderby = "c.callno";
		}

	}

	private void selectAllCheck() {

		if (cmbDeveloper.getSelectedItem().equals("All")) {
			strCmbDeveloper = 0;
		} else {
			strCmbDeveloper = Integer.parseInt(String.valueOf(cmbDeveloper.getSelectedItemValue()));
		}

		if (cmbClient.getSelectedItem().equals("All")) {
			strCmbClient = 0;
		} else {
			strCmbClient = Integer.parseInt(String.valueOf(cmbClient.getSelectedItemValue()));
		}

		if (cmbTester.getSelectedItem().equals("All")) {
			strCmbDeptAuthorize = 0;
		} else {
			strCmbDeptAuthorize = Integer.parseInt(String.valueOf(cmbTester.getSelectedItemValue()));
		}

		if (cmbDepartment.getSelectedItem().equals("All")) {
			strCmbDepartment = 0;
		} else {
			strCmbDepartment = Integer.parseInt(String.valueOf(cmbDepartment.getSelectedItemValue()));
		}
		if (cmbModule.getSelectedItem().equals("All")) {
			strCmbModule = 0;
		} else {
			strCmbModule = Integer.parseInt(String.valueOf(cmbModule.getSelectedItemValue()));
		}

		if (!txtCallNo.getText().equalsIgnoreCase("")) {
			strCallNo = Integer.valueOf(String.valueOf(txtCallNo.getText()));
		}

	}

	private void dateCheck() {

		if (rdpAsOnDate.isSelected() == true) {
			strCallFromDate = spnCallFromDate.getDateValue();
		} else {
			strCallFromDate = spnCallFromDate.getDateValue();
			strCallToDate = spnCallToDate.getDateValue();
		}
		if (rdpCompletedDate.isSelected() == true) {
			strCompletedFromDate = spnCompletedFromDate.getDateValue();
		} else {
			strCompletedFromDate = spnCompletedFromDate.getDateValue();
			strCompletedToDate = spnCompletedToDate.getDateValue();
		}

	}

	private void selectRadioButton(JRadioButton selectedButton) {
		// Disable mouse press events for all radio buttons
		for (JRadioButton button : Arrays.asList(rdpNone, rdpModule, rdpTester, rdpClient, rdpCallDate,
				rdpCompletedDate, rdpCallNo)) {
			button.setSelected(button == selectedButton);
		}

		// Set the selected radio button as selected and focus on it
		selectedButton.setSelected(true);
		selectedButton.requestFocus();
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
		panelMain.add(panelCompletedDetail());
		//		//		panelMain.add(panelContentInitialize());
		panelMain.add(panelLine3Inialize());
		panelMain.addKeyListener(this);

		getContentPane().add(panelMain);

	}

	private Component panelCompletedDetail() {
		int lblWidth = 130;
		int lblHeight = 30;
		int txtWidth = 120;
		int txtHeight = 20;

		panelCompleted = new JPanel();
		panelCompleted.setBounds(20, panelDetail2.getY() + panelDetail2.getHeight() + 10, 918, 450);
		panelCompleted.setLayout(null);
		panelCompleted.setBackground(color2);
		panelCompleted.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelCompleted.setVisible(true);

		tblCompletedCalls = new JilabaTable(getCompletedCalls());
		tblCompletedCalls.setAutoResizeMode(JilabaTable.AUTO_RESIZE_OFF);
		tblCompletedCalls.getTableHeader().setReorderingAllowed(false);
		tblCompletedCalls.getTableHeader().setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		tblCompletedCalls.setFont(CustomFonts.fontCalibriPlain15);
		tblCompletedCalls.getTableHeader().setForeground(color6);
		tblCompletedCalls.getTableHeader().setBackground(Color.WHITE);
		tblCompletedCalls.setForeground(Color.BLACK);
		tblCompletedCalls.setRowHeight(20);
		tblCompletedCalls.setVisible(true);

		scrCompletedCalls = new JScrollPane(tblCompletedCalls);
		scrCompletedCalls.setBounds(10, 10, 900, 330);
		scrCompletedCalls.getViewport().setBackground(tblCompletedCalls.getTableHeader().getBackground());
		scrCompletedCalls.setVisible(true);

		panelCallDesc = new JPanel();
		panelCallDesc.setBounds(scrCompletedCalls.getX(), scrCompletedCalls.getY() + scrCompletedCalls.getHeight() + 10,
				900, 90);
		panelCallDesc.setLayout(null);
		panelCallDesc.setBackground(color4);
		//		panelCallDesc.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelCallDesc.setVisible(true);

		lblDepartment = new JLabel("Department : ");
		lblDepartment.setBounds(panelCallDesc.getX(), 0, lblWidth, lblHeight);
		lblDepartment.setBackground(color2);
		lblDepartment.setForeground(Color.BLACK);
		lblDepartment.setFont(CustomFonts.font);
		lblDepartment.setVisible(true);

		lblDepartmentVal = new JLabel(" ");
		lblDepartmentVal.setBounds(lblDepartment.getX() + lblWidth - 50, lblDepartment.getY(), lblWidth, lblHeight);
		lblDepartmentVal.setBackground(color2);
		lblDepartmentVal.setForeground(Color.BLACK);
		lblDepartmentVal.setFont(CustomFonts.fontCalibriBold);
		lblDepartmentVal.setVisible(true);

		lblClCallNo = new JLabel("Call No : ");
		lblClCallNo.setBounds(panelCallDesc.getX() + 200, 0, lblWidth, lblHeight);
		lblClCallNo.setBackground(color2);
		lblClCallNo.setForeground(Color.BLACK);
		lblClCallNo.setFont(CustomFonts.font);
		lblClCallNo.setVisible(true);

		lblClCallNoVal = new JLabel(" ");
		lblClCallNoVal.setBounds(lblClCallNo.getX() + lblWidth - 50, lblClCallNo.getY(), lblWidth, lblHeight);
		lblClCallNoVal.setBackground(color2);
		lblClCallNoVal.setForeground(Color.BLACK);
		lblClCallNoVal.setFont(CustomFonts.fontCalibriBold);
		lblClCallNoVal.setVisible(true);

		lblCallDate = new JLabel("Call Date : ");
		lblCallDate.setBounds(lblClCallNo.getX() + 200, 0, lblWidth, lblHeight);
		lblCallDate.setBackground(color2);
		lblCallDate.setBackground(color2);
		lblCallDate.setForeground(Color.BLACK);
		lblCallDate.setFont(CustomFonts.font);
		lblCallDate.setVisible(true);

		lblCallDateVal = new JLabel("");
		lblCallDateVal.setBounds(lblCallDate.getX() + lblWidth - 50, lblCallDate.getY(), lblWidth, lblHeight);
		lblCallDateVal.setBackground(color2);
		lblCallDateVal.setForeground(Color.BLACK);
		lblCallDateVal.setFont(CustomFonts.fontCalibriBold);
		lblCallDateVal.setVisible(true);

		lblClModule = new JLabel("Module : ");
		lblClModule.setBounds(lblCallDate.getX() + 200, 0, lblWidth, lblHeight);
		lblClModule.setBackground(color2);
		lblClModule.setForeground(Color.BLACK);
		lblClModule.setFont(CustomFonts.font);
		lblClModule.setVisible(true);

		lblClModuleVal = new JLabel("");
		lblClModuleVal.setBounds(lblClModule.getX() + lblWidth - 50, lblClModule.getY(), lblWidth, lblHeight);
		lblClModuleVal.setBackground(color2);
		lblClModuleVal.setForeground(Color.BLACK);
		lblClModuleVal.setFont(CustomFonts.fontCalibriBold);
		lblClModuleVal.setVisible(true);

		lblCallDesc = new JLabel("Call Desc : ");
		lblCallDesc.setBounds(lblDepartment.getX(), lblDepartment.getY() + lblDepartment.getHeight(), lblWidth,
				lblHeight);
		lblCallDesc.setBackground(color2);
		lblCallDesc.setForeground(Color.BLACK);
		lblCallDesc.setFont(CustomFonts.font);
		lblCallDesc.setVisible(true);

		lblCallDescVal = new JLabel("");
		lblCallDescVal.setBounds(lblCallDesc.getX() + lblWidth - 50, lblCallDesc.getY(), 600, lblHeight);
		lblCallDescVal.setBackground(color2);
		lblCallDescVal.setForeground(Color.BLACK);
		lblCallDescVal.setFont(CustomFonts.fontCalibriBold);
		lblCallDescVal.setVisible(true);

		lblCompletedDesc = new JLabel("Test Desc : ");
		lblCompletedDesc.setBounds(lblCallDesc.getX(), lblCallDesc.getY() + lblCallDesc.getHeight(), lblWidth,
				lblHeight);
		lblCompletedDesc.setBackground(color2);
		lblCompletedDesc.setForeground(Color.BLACK);
		lblCompletedDesc.setFont(CustomFonts.font);
		lblCompletedDesc.setVisible(true);

		lblCompletedDescVal = new JLabel(" ");
		lblCompletedDescVal.setBounds(lblCompletedDesc.getX() + lblWidth - 50, lblCompletedDesc.getY(), 600, lblHeight);
		lblCompletedDescVal.setBackground(color2);
		lblCompletedDescVal.setForeground(Color.BLACK);
		lblCompletedDescVal.setFont(CustomFonts.fontCalibriBold);
		lblCompletedDescVal.setVisible(true);

		lblPressEsc = new JLabel(
				"Press Esc to Close                            I - Image Details To Show                         D - Call Delivery Process       "
						+ "                                                                                                                              "
						+ "                                                                                                   Press Enter Only For Date Validation  ");
		lblPressEsc.setHorizontalAlignment(SwingConstants.LEFT);
		lblPressEsc.setBounds(panelCompleted.getX(), panelCompleted.getY() + panelCompleted.getHeight(), 900, 20);
		lblPressEsc.setBackground(color2);
		lblPressEsc.setForeground(Color.BLACK);
		lblPressEsc.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		lblPressEsc.setVisible(true);

		panelMain.add(panelCompleted);
		panelCompleted.add(scrCompletedCalls);
		panelCompleted.add(panelCallDesc);
		panelCallDesc.add(lblDepartment);
		panelCallDesc.add(lblDepartmentVal);
		panelCallDesc.add(lblClCallNo);
		panelCallDesc.add(lblClCallNoVal);
		panelCallDesc.add(lblCallDate);
		panelCallDesc.add(lblCallDateVal);
		panelCallDesc.add(lblClModule);
		panelCallDesc.add(lblClModuleVal);
		panelCallDesc.add(lblCallDesc);
		panelCallDesc.add(lblCallDescVal);
		panelCallDesc.add(lblCompletedDesc);
		panelCallDesc.add(lblCompletedDescVal);

		panelMain.add(lblPressEsc);
		return panelCompleted;

	}

	private List<JilabaColumn> getCompletedCalls() {

		List<JilabaColumn> jilabaColumnlist = new ArrayList<>();
		jilabaColumnlist.add(new JilabaColumn("CallNo", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Date ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Test Date ", String.class, 200, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Test By", String.class, 200, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Client Name ", String.class, 200, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Recd By ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Authorized ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Department ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Module ", String.class, 100, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Description ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Tested Description", String.class, 150, JLabel.LEFT));

		return jilabaColumnlist;

	}

	private Component panelDetail2Initialize() {

		int lblWidth = 130;
		int lblHeight = 30;
		int txtWidth = 120;
		int txtHeight = 20;

		panelDetail2 = new JPanel();
		panelDetail2.setBounds(20, panelLine2.getY() + 10, 918, 120);
		panelDetail2.setLayout(null);
		panelDetail2.setBackground(color2);
		panelDetail2.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelDetail2.setVisible(true);

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
		lblCallFromDate.setFont(CustomFonts.fontCalibriBold);

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

		//		DSsd

		lblCompleted = new JLabel("Tested Date");
		lblCompleted.setBounds(rdpBetweenDate.getX() + rdpBetweenDate.getWidth(), rdpBetweenDate.getY() - 5, lblWidth,
				lblHeight);
		lblCompleted.setBackground(color2);
		lblCompleted.setForeground(Color.BLACK);
		lblCompleted.setFont(CustomFonts.fontCalibriBold);
		lblCompleted.setVisible(true);

		panelCompletedDate = new JPanel();
		panelCompletedDate.setBounds(rdpBetweenDate.getX() + rdpBetweenDate.getWidth(), rdpBetweenDate.getY() + 15, 200,
				50);
		panelCompletedDate.setLayout(null);
		panelCompletedDate.setBackground(color2);
		panelCompletedDate.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		panelCompletedDate.setVisible(true);

		lblCompletedFromDate = new JLabel("From Date");
		lblCompletedFromDate.setBounds(10, 0, lblWidth, lblHeight);
		lblCompletedFromDate.setBackground(color2);
		lblCompletedFromDate.setVisible(true);
		lblCompletedFromDate.setFont(CustomFonts.fontCalibriBold);

		spnCompletedFromDate = new JilabaSpinner();
		spnCompletedFromDate.setBounds(lblCallFromDate.getX(), lblCallFromDate.getY() + lblCallFromDate.getHeight() - 5,
				75, 20);
		spnCompletedFromDate.setVisible(true);
		spnCompletedFromDate.setBackground(color2);
		spnCompletedFromDate.setFont(CustomFonts.fontCalibriBold);
		spnCompletedFromDate.addKeyListener(this);

		lblCompletedToDate = new JLabel("To Date");
		lblCompletedToDate.setBounds(rdpBetweenDate.getX() - 10, 0, lblWidth, lblHeight);
		lblCompletedToDate.setBackground(color2);
		lblCompletedToDate.setVisible(true);
		lblCompletedToDate.setFont(CustomFonts.fontCalibriBold);

		spnCompletedToDate = new JilabaSpinner();
		spnCompletedToDate.setBounds(lblCallToDate.getX(), lblCallToDate.getY() + lblCallToDate.getHeight() - 5, 75,
				20);
		spnCompletedToDate.setVisible(true);
		spnCompletedToDate.setBackground(color2);
		spnCompletedToDate.setFont(CustomFonts.fontCalibriBold);
		spnCompletedToDate.addKeyListener(this);

		lblDeveloper = new JLabel("Developer");
		lblDeveloper.setBounds(panelCompletedDate.getX(),
				panelCompletedDate.getY() + panelCompletedDate.getHeight() + 10, lblWidth, lblHeight);
		lblDeveloper.setBackground(color2);
		lblDeveloper.setForeground(Color.BLACK);
		lblDeveloper.setFont(CustomFonts.fontCalibriBold);
		lblDeveloper.setVisible(true);

		cmbDeveloper = new JilabaComboBox<Operator>();
		cmbDeveloper.setBounds(lblDeveloper.getX() + 80, lblDeveloper.getY() + 5, txtWidth, txtHeight);
		cmbDeveloper.setBackground(color2);
		cmbDeveloper.setFont(CustomFonts.fontCalibriBold);
		cmbDeveloper.setVisible(true);
		cmbDeveloper.addKeyListener(this);

		lblClient = new JLabel("Client");
		lblClient.setBounds(panelCompletedDate.getX() + panelCompletedDate.getWidth() + 30, lblCompleted.getY(),
				lblWidth, lblHeight);
		lblClient.setBackground(color2);
		lblClient.setForeground(Color.BLACK);
		lblClient.setFont(CustomFonts.fontCalibriBold);
		lblClient.setVisible(true);

		lblTester = new JLabel("Tested By");
		lblTester.setBounds(lblClient.getX(), lblClient.getY() + lblClient.getHeight(), lblWidth, lblHeight);
		lblTester.setBackground(color2);
		lblTester.setForeground(Color.BLACK);
		lblTester.setFont(CustomFonts.fontCalibriBold);
		lblTester.setVisible(true);

		lblDepartment = new JLabel("Department");
		lblDepartment.setBounds(lblTester.getX(), lblTester.getY() + lblTester.getHeight(), lblWidth, lblHeight);
		lblDepartment.setBackground(color2);
		lblDepartment.setForeground(Color.BLACK);
		lblDepartment.setFont(CustomFonts.fontCalibriBold);
		lblDepartment.setVisible(true);

		lblModule = new JLabel("Module");
		lblModule.setBounds(lblDepartment.getX(), lblDepartment.getY() + lblDepartment.getHeight(), lblWidth,
				lblHeight);
		lblModule.setBackground(color2);
		lblModule.setForeground(Color.BLACK);
		lblModule.setFont(CustomFonts.fontCalibriBold);
		lblModule.setVisible(true);

		cmbClient = new JilabaComboBox<Customer>();
		cmbClient.setBounds(lblClient.getX() + 100, lblClient.getY(), txtWidth, txtHeight);
		cmbClient.setBackground(color2);
		cmbClient.setFont(CustomFonts.fontCalibriBold);
		cmbClient.setVisible(true);
		cmbClient.addKeyListener(this);

		cmbTester = new JilabaComboBox<Operator>();
		cmbTester.setBounds(cmbClient.getX(), lblTester.getY(), txtWidth, txtHeight);
		cmbTester.setBackground(color2);
		cmbTester.setFont(CustomFonts.fontCalibriBold);
		cmbTester.setVisible(true);
		cmbTester.addKeyListener(this);

		cmbDepartment = new JilabaComboBox<Department>();
		cmbDepartment.setBounds(cmbTester.getX(), lblDepartment.getY(), txtWidth, txtHeight);
		cmbDepartment.setBackground(color2);
		cmbDepartment.setFont(CustomFonts.fontCalibriBold);
		cmbDepartment.setVisible(true);
		cmbDepartment.addKeyListener(this);

		cmbModule = new JilabaComboBox<Module>();
		cmbModule.setBounds(cmbDepartment.getX(), lblModule.getY(), txtWidth, txtHeight);
		cmbModule.setBackground(color2);
		cmbModule.setFont(CustomFonts.fontCalibriBold);
		cmbModule.setVisible(true);
		cmbModule.addKeyListener(this);

		lblCallNo = new JLabel("Call No");
		lblCallNo.setBounds(cmbClient.getX() + cmbClient.getWidth() + 20, cmbClient.getY(), lblWidth, lblHeight);
		lblCallNo.setBackground(color2);
		lblCallNo.setForeground(Color.BLACK);
		lblCallNo.setFont(CustomFonts.fontCalibriBold);
		lblCallNo.setVisible(true);

		txtCallNo = new JilabaTextField();
		txtCallNo.setBounds(lblCallNo.getX() + 60, lblCallNo.getY() + 5, 100, txtHeight);
		txtCallNo.setVisible(true);
		txtCallNo.setFont(CustomFonts.fontCalibriBold);
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
		rdpNone.setFont(CustomFonts.fontCalibriBold);
		rdpNone.setSelected(true);
		rdpNone.addKeyListener(this);

		rdpModule = new JRadioButton("Module");
		rdpModule.setBounds(rdpNone.getX() + rdpNone.getWidth(), 5, 50, 20);
		rdpModule.setBackground(color2);
		rdpModule.setVisible(true);
		rdpModule.setFont(CustomFonts.fontCalibriBold);
		rdpModule.setSelected(false);
		rdpModule.addKeyListener(this);

		rdpTester = new JRadioButton("Tester");
		rdpTester.setBounds(rdpModule.getX() + rdpModule.getWidth(), 5, 50, 20);
		rdpTester.setBackground(color2);
		rdpTester.setVisible(true);
		rdpTester.setFont(CustomFonts.fontCalibriBold);
		rdpTester.setSelected(false);
		rdpTester.addKeyListener(this);

		rdpClient = new JRadioButton("Client");
		rdpClient.setBounds(rdpTester.getX() + rdpTester.getWidth(), 5, 40, 20);
		rdpClient.setBackground(color2);
		rdpClient.setVisible(true);
		rdpClient.setFont(CustomFonts.fontCalibriBold);
		rdpClient.setSelected(false);
		rdpClient.addKeyListener(this);

		rdpCallDate = new JRadioButton("CallDate");
		rdpCallDate.setBounds(rdpNone.getX(), rdpNone.getY() + rdpNone.getHeight(), 50, 20);
		rdpCallDate.setBackground(color2);
		rdpCallDate.setVisible(true);
		rdpCallDate.setFont(CustomFonts.fontCalibriBold);
		rdpCallDate.setSelected(false);
		rdpCallDate.addKeyListener(this);

		rdpCompletedDate = new JRadioButton("TestedDate");
		rdpCompletedDate.setBounds(rdpCallDate.getX() + rdpCallDate.getWidth(), rdpCallDate.getY(), 60, 20);
		rdpCompletedDate.setBackground(color2);
		rdpCompletedDate.setVisible(true);
		rdpCompletedDate.setFont(CustomFonts.fontCalibriBold);
		rdpCompletedDate.setSelected(false);
		rdpCompletedDate.addKeyListener(this);

		rdpCallNo = new JRadioButton("CallNo");
		rdpCallNo.setBounds(rdpCompletedDate.getX() + rdpCompletedDate.getWidth(), rdpCompletedDate.getY(), 60, 20);
		rdpCallNo.setBackground(color2);
		rdpCallNo.setVisible(true);
		rdpCallNo.setFont(CustomFonts.fontCalibriBold);
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
		panelDetail2.add(rdpAsOnDate);
		panelDetail2.add(rdpBetweenDate);
		panelDetail2.add(lblCall);
		panelDetail2.add(lblCompleted);
		panelDetail2.add(panelCallDate);
		panelDetail2.add(panelCompletedDate);
		panelCallDate.add(lblCallFromDate);
		panelCallDate.add(lblCallToDate);
		panelCallDate.add(spnCallFromDate);
		panelCallDate.add(spnCallToDate);
		panelCompletedDate.add(lblCompletedFromDate);
		panelCompletedDate.add(lblCompletedToDate);
		panelCompletedDate.add(spnCompletedFromDate);
		panelCompletedDate.add(spnCompletedToDate);
		panelDetail2.add(lblDeveloper);
		panelDetail2.add(cmbDeveloper);
		panelDetail2.add(lblClient);
		panelDetail2.add(lblTester);
		panelDetail2.add(lblDepartment);
		panelDetail2.add(lblModule);
		panelDetail2.add(cmbClient);
		panelDetail2.add(cmbTester);
		panelDetail2.add(cmbDepartment);
		panelDetail2.add(cmbModule);
		panelDetail2.add(lblCallNo);
		panelDetail2.add(txtCallNo);
		panelDetail2.add(panelOrderby);
		btnOrderbyGroup.add(rdpNone);
		btnOrderbyGroup.add(rdpTester);
		btnOrderbyGroup.add(rdpModule);
		btnOrderbyGroup.add(rdpClient);
		btnOrderbyGroup.add(rdpCallDate);
		btnOrderbyGroup.add(rdpCompletedDate);
		btnOrderbyGroup.add(rdpCallNo);
		panelOrderby.add(rdpNone);
		panelOrderby.add(rdpTester);
		panelOrderby.add(rdpModule);
		panelOrderby.add(rdpClient);
		panelOrderby.add(rdpCallDate);
		panelOrderby.add(rdpCompletedDate);
		panelOrderby.add(rdpCallNo);

		panelDetail2.add(btnView);
		panelDetail2.add(btnClear);

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

		panelMain.add(panelLine2);

		return panelLine2;
	}

	private Component panelDetailInitialize() {

		panelDetail = new JPanel();
		panelDetail.setBounds(panelLine.getX(), panelLine.getY() + 10, 958, 30);
		panelDetail.setLayout(null);
		panelDetail.setBackground(color2);

		panelDetail.setVisible(true);
		lblHeading = new JLabel("CALL COMPLETION PROCESS");
		lblHeading.setBounds(20, -10, 170, 50);
		//		lblHeading.setBounds(panelDetail.getWidth() / 2, panelDetail.getY() / 2, 20, 20);
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
			if (e.getSource() == rdpAsOnDate) {

				rdpBetweenDate.setVisible(false);
				blnAsOnDate = true;
				strCallToDate = null;
				lblCallFromDate.setText("As On Date");
				lblCallToDate.setVisible(false);
				spnCallToDate.setVisible(false);
				spnCallFromDate.requestFocus();

			} else if (e.getSource() == rdpBetweenDate) {

				spnCallFromDate.requestFocus();

			} else if (e.getSource() == cmbDeveloper) {
				cmbClient.requestFocus();
			} else if (e.getSource() == cmbClient) {
				cmbTester.requestFocus();
			} else if (e.getSource() == cmbTester) {
				cmbDepartment.requestFocus();
			} else if (e.getSource() == cmbDepartment) {

				lstModule = logicCallsCompleted
						.getModule(Integer.valueOf(String.valueOf(cmbDepartment.getSelectedItemValue())));
				cmbModule.removeAllItems();
				cmbModule.addListItem(new ListItem("All"));

				for (com.jilaba.calls.model.Module module : lstModule) {

					cmbModule.addListItem(new ListItem(module.getModuleName(), module.getModuleId()));

				}
				cmbModule.requestFocus();
			} else if (e.getSource() == cmbModule) {
				txtCallNo.requestFocus();
			} else if (e.getSource() == txtCallNo) {
				rdpNone.requestFocus();
			} else if (e.getSource() == rdpNone) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpModule) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpTester) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpClient) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpCallDate) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpCompletedDate) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpCallNo) {
				btnView.requestFocus();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (e.getSource() == rdpAsOnDate) {
				rdpBetweenDate.requestFocus();
				rdpAsOnDate.setSelected(false);
				rdpBetweenDate.setSelected(true);
			} else if (e.getSource() == rdpNone) {
				rdpModule.requestFocus();
				rdpFalse();
				rdpModule.setSelected(true);
			} else if (e.getSource() == rdpModule) {
				rdpTester.requestFocus();
				rdpFalse();
				rdpTester.setSelected(true);
			} else if (e.getSource() == rdpTester) {
				rdpClient.requestFocus();
				rdpFalse();
				rdpClient.setSelected(true);
			} else if (e.getSource() == rdpClient) {
				rdpCallDate.requestFocus();
				rdpFalse();
				rdpCallDate.setSelected(true);
			} else if (e.getSource() == rdpCallDate) {
				rdpCompletedDate.requestFocus();
				rdpFalse();
				rdpCompletedDate.setSelected(true);
			} else if (e.getSource() == rdpCompletedDate) {
				rdpCallNo.requestFocus();
				rdpFalse();
				rdpCallNo.setSelected(true);
			} else if (e.getSource() == rdpCallNo) {
				rdpNone.requestFocus();
				rdpFalse();
				rdpNone.setSelected(true);
			}

		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (e.getSource() == rdpBetweenDate) {
				rdpAsOnDate.requestFocus();
				rdpAsOnDate.setSelected(true);
				rdpBetweenDate.setSelected(false);
			} else if (e.getSource() == rdpNone) {
				rdpCallNo.requestFocus();
				rdpFalse();
				rdpCallNo.setSelected(true);
			} else if (e.getSource() == rdpModule) {
				rdpNone.requestFocus();
				rdpFalse();
				rdpNone.setSelected(true);
			} else if (e.getSource() == rdpTester) {
				rdpModule.requestFocus();
				rdpFalse();
				rdpModule.setSelected(true);
			} else if (e.getSource() == rdpClient) {
				rdpTester.requestFocus();
				rdpFalse();
				rdpTester.setSelected(true);
			} else if (e.getSource() == rdpCallDate) {
				rdpClient.requestFocus();
				rdpFalse();
				rdpClient.setSelected(true);
			} else if (e.getSource() == rdpCompletedDate) {
				rdpCallDate.requestFocus();
				rdpFalse();
				rdpCallDate.setSelected(true);
			} else if (e.getSource() == rdpCallNo) {
				rdpCompletedDate.requestFocus();
				rdpFalse();
				rdpCompletedDate.setSelected(true);
			}

		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

			setVisible(false);

			FrmMainMenu frmMainMenu = Applicationmain.getAbstractApplicationContext().getBean(FrmMainMenu.class);
			frmMainMenu.setVisible(true);

		}

		if (e.getKeyCode() == KeyEvent.VK_D) {

			FrmCompletedDialog frmCompletedDialog = Applicationmain.getAbstractApplicationContext()
					.getBean(FrmCompletedDialog.class);
			frmCompletedDialog.setVisible(true);

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

		}

		if (e.getSource() == btnClear) {

			loadDetails();

			rdpAsOnDate.requestFocus();
		}
	}

}
