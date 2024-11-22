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
import java.awt.event.MouseListener;
import java.text.ParseException;
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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.codehaus.jackson.map.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.common.ImageResources;
import com.jilaba.calls.common.LoginCredential;
import com.jilaba.calls.common.TimerJob;
import com.jilaba.calls.logic.LogicReadyCalls;
import com.jilaba.calls.model.Calls;
import com.jilaba.calls.model.CallsImages;
import com.jilaba.calls.model.CustStaff;
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
public class FrmReadyCalls extends JFrame implements ActionListener, KeyListener {

	private JPanel panelMain;
	private JPanel panelTitle;
	private JPanel panelLine;
	private JPanel panelDetail;
	private JPanel panelLine2;
	private JPanel panelLine3;
	private JPanel panelDetail2;
	private JPanel panelCallDate;
	private JPanel panelReadyDate;
	private JPanel panelOrderby;
	private JPanel panelReady;
	private JPanel panelCallDesc;

	private JLabel lblDevelopedby;
	private JLabel lblLocalIpValue;
	private JLabel lblVersion;
	private JLabel lblServerIpValue;
	private JLabel lblDateTime;

	private JLabel lblCallFromDate;
	private JLabel lblCallToDate;
	private JLabel lblReadyFromDate;
	private JLabel lblReadyToDate;
	private JLabel lblCall;
	private JLabel lblReady;
	private JLabel lblDeveloper;
	private JLabel lblClient;
	private JLabel lblDeptAutorize;
	private JLabel lblDepartment;
	private JLabel lblDepartmentVal;
	private JLabel lblModule;
	private JLabel lblCallNo;
	private JLabel lblOperatorLabel;
	private JLabel lblMinimize;

	private JLabel lblQc1Progress;
	private JLabel lblQc1Colourkey;
	private JLabel lblQc2Progress;
	private JLabel lblQc2Colourkey;
	private JLabel lblQc3Progress;
	private JLabel lblQc3Colourkey;

	private JLabel lblDesc;
	private JLabel lblClCallNo;
	private JLabel lblClCallNoVal;
	private JLabel lblCallTime;
	private JLabel lblCallDate;
	private JLabel lblCallDateVal;
	private JLabel lblClModule;
	private JLabel lblClModuleVal;
	private JLabel lblCallDesc;

	private JLabel lblReadyDesc;

	private JLabel lblPressEsc;

	private JTextArea txtCallDescVal;
	private JTextArea txtReadyDescVal;

	private JRadioButton rdpAsOnDate;
	private JRadioButton rdpBetweenDate;
	private boolean blnFrmDevCall;

	private JRadioButton rdpNone;
	private JRadioButton rdpModule;
	private JRadioButton rdpDeveloper;
	private JRadioButton rdpClient;
	private JRadioButton rdpCallDate;
	private JRadioButton rdpReadyDate;
	private JRadioButton rdpCallNo;

	private ButtonGroup btnGroup = new ButtonGroup();
	private ButtonGroup btnOrderbyGroup = new ButtonGroup();

	private JilabaSpinner spnCallFromDate;
	private JilabaSpinner spnCallToDate;
	private JilabaSpinner spnReadyFromDate;
	private JilabaSpinner spnReadyToDate;

	private int strCmbDeveloper;
	private int strCmbClient;
	private int strCmbDeptAuthorize;
	private int strCmbDepartment;
	private int strCmbModule;
	private String strOrderby;
	private String strCallFromDate;
	private String strCallToDate;

	private String strReadyFromDate;
	private String strReadyToDate;

	private JLabel lblHeading;

	private JilabaTextField txtCallNo;

	private List<Operator> lstDeveloper;
	private List<Customer> lstCustomer;
	private List<CustStaff> lstDeptAuthorize;
	private List<Department> lstDepartment;
	private List<com.jilaba.calls.model.Module> lstModule;
	private List<ReadyCalls> lstReadyCalls;

	private List<Object> strDeveloper;
	private List<Object> strCustomer;
	private List<Object> strDeptAuthorize;
	private List<Object> strDepartment;
	private List<Object> strModule;

	private JilabaComboBox<Operator> cmbDeveloper;
	private JilabaComboBox<Customer> cmbClient;
	private JilabaComboBox<Operator> cmbDeptAuthorize;
	private JilabaComboBox<Department> cmbDepartment;
	private JilabaComboBox<Module> cmbModule;

	private JButton btnView;
	private JButton btnClear;

	private Color color1 = Color.decode("#66023c");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#843462");
	private Color color4 = Color.decode("#e0ccd8");
	private Color color5 = Color.decode("#e4dedf");
	private Color color6 = Color.decode("#000000");
	private Color color7 = Color.decode("#B2809D");

	private ControlResize controlResize;

	private JilabaFonts jilabaFonts = new JilabaFonts();

	private JilabaTable tblReadyCalls;

	private JScrollPane scrReadyCalls;

	private Color fontColor1 = Color.decode("#17202A");
	private boolean blnAsOnDate = false;

	private long strCallNo;
	private int selectedRow;

	List<Calls> lstList;

	private int Qc1 = 4;
	private int Qc2 = 5;
	private int Qc3 = 22;
	private List<ReadyCalls> progress;

	@Autowired
	private LogicReadyCalls logicReadyCalls;
	@Autowired
	private CommonMethods commonMethods;

	public FrmReadyCalls() {

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

	private void selectRadioButton(JRadioButton selectedButton) {
		// Disable mouse press events for all radio buttons
		for (JRadioButton button : Arrays.asList(rdpNone, rdpModule, rdpDeveloper, rdpClient, rdpCallDate, rdpReadyDate,
				rdpCallNo)) {
			button.setSelected(button == selectedButton);
		}

		// Set the selected radio button as selected and focus on it
		selectedButton.setSelected(true);
		selectedButton.requestFocus();
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
		lblReadyFromDate.setText("From Date");
		lblReadyFromDate.setVisible(true);
		lblReadyToDate.setVisible(true);
		spnCallToDate.setVisible(true);
		spnReadyToDate.setVisible(true);
		blnAsOnDate = false;
		cmbDeveloper.removeAllItems();
		cmbClient.removeAllItems();
		cmbDeptAuthorize.removeAllItems();
		cmbDepartment.removeAllItems();
		cmbModule.removeAllItems();
		txtCallNo.setText("");
		rdpFalse();
		rdpNone.setSelected(true);

		cmbDeveloper.addListItem(new ListItem("All"));
		cmbClient.addListItem(new ListItem("All"));
		cmbDepartment.addListItem(new ListItem("All"));
		cmbDeptAuthorize.addListItem(new ListItem("All"));
		cmbModule.addListItem(new ListItem("All"));

		lstDeveloper = logicReadyCalls.getDeveloper();
		strDeveloper = new ArrayList<Object>();

		for (Operator dev : lstDeveloper) {

			strDeveloper.add(dev.getStaffid());
			cmbDeveloper.addListItem(new ListItem(dev.getStaffname(), dev.getStaffid()));

		}

		lstCustomer = logicReadyCalls.getCustomer();
		strCustomer = new ArrayList<Object>();

		for (Customer cus : lstCustomer) {

			strCustomer.add(cus.getCustId());
			cmbClient.addListItem(new ListItem(cus.getcustName().trim(), cus.getCustId()));

		}

		lstDeptAuthorize = logicReadyCalls.getDeptAuthorize();
		strDeptAuthorize = new ArrayList<Object>();

		for (CustStaff deptAuth : lstDeptAuthorize) {

			strDeptAuthorize.add(deptAuth.getCustStaffId());
			cmbDeptAuthorize.addListItem(new ListItem(deptAuth.getCustStaffName(), deptAuth.getCustStaffId()));

		}

		lstDepartment = logicReadyCalls.getDepartment();
		strDepartment = new ArrayList<Object>();

		for (Department dept : lstDepartment) {

			strDepartment.add(dept.getdNo());
			cmbDepartment.addListItem(new ListItem(dept.getDepartment(), dept.getdNo()));

		}
		lstModule = logicReadyCalls.getModule();
		strModule = new ArrayList<Object>();

		for (com.jilaba.calls.model.Module module : lstModule) {

			strModule.add(module.getModuleId());
			cmbModule.addListItem(new ListItem(module.getModuleName(), module.getModuleId()));

		}

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

						lblReadyFromDate.setText("As On Date");
						lblReadyToDate.setVisible(false);
						spnReadyToDate.setVisible(false);
						spnReadyFromDate.requestFocus();
					}

				}

			}
		});

		spnCallFromDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (blnAsOnDate == true) {
						spnReadyFromDate.requestFocus();
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

					spnReadyFromDate.requestFocus();

				}

			}

		});

		/*	spnCallToDate.addKeyListener(new KeyAdapter() {
		
				@Override
				public void keyPressed(KeyEvent e) {
		
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						spnReadyFromDate.requestFocus();
		
					}
		
				}
			});*/
		spnReadyFromDate.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (blnAsOnDate == false) {
						spnReadyToDate.requestFocus();
					} else {
						cmbDeveloper.requestFocus();
						blnAsOnDate = false;
					}

				}

			}
		});
		spnReadyToDate.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cmbDeveloper.requestFocus();

				}

			}
		});

		tblReadyCalls.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent event) {

				if (tblReadyCalls.getRowCount() <= 0 || tblReadyCalls.getSelectedRow() < 0
						|| event.getValueIsAdjusting())
					return;
				showProductDetails(lstReadyCalls.get(tblReadyCalls.getSelectedRow()));

			}

			private void showProductDetails(ReadyCalls readyCalls) {

				lblDepartmentVal.setText(readyCalls.getDepartment());
				lblClCallNoVal.setText(String.valueOf(readyCalls.getCallno()));
				lblCallDateVal.setText(String.valueOf(readyCalls.getCdate()));
				lblClModuleVal.setText(readyCalls.getModuleName());
				txtCallDescVal.setText(readyCalls.getDescription().trim());
				txtReadyDescVal.setText(readyCalls.getReadydescription().trim());

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
		rdpDeveloper.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				selectRadioButton(rdpDeveloper);
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
		rdpReadyDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				selectRadioButton(rdpReadyDate);

			}
		});
		rdpCallNo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				selectRadioButton(rdpCallNo);
			}
		});

		tblReadyCalls.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_P) {

					if (FrmLogin.designation == 2) {

						int progressValidate = logicReadyCalls.beforeProgressValidate();

						if (progressValidate == 0) {

							int response = JOptionPane.showConfirmDialog(panelMain, "Do you want to proceed?",
									"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

							if (response == JOptionPane.YES_OPTION) {
								selectedRow = tblReadyCalls.getSelectedRow();

								logicReadyCalls.updateReadyProgress(String.valueOf(
										tblReadyCalls.getModel().getValueAt(tblReadyCalls.getSelectedRow(), 0)));

								//								if (FrmLogin.OperCode == Qc1) {
								//									tblReadyCalls.setRowColor(selectedRow, Color.decode("#CEAB8C"));
								//								} else if (FrmLogin.OperCode == Qc2) {
								//									tblReadyCalls.setRowColor(selectedRow, Color.decode("#F3E5AB"));
								//								} else if (FrmLogin.OperCode == Qc3) {
								//									tblReadyCalls.setRowColor(selectedRow, Color.decode("#C9CC3F"));
								//								}

							} else if (response == JOptionPane.NO_OPTION) {
								tblReadyCalls.requestFocus();
								return;
							}

						} else {

							JOptionPane.showMessageDialog(panelMain,
									FrmLogin.Operator + " Progress Call Already Found ...! ");
						}

						btnView();
					} else {

						JOptionPane.showMessageDialog(panelMain, "You Are Not Authorized ... !");
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_I) {

					btnImage();
				}
				if (e.getKeyCode() == KeyEvent.VK_C) {
					if (FrmLogin.designation == 3 || FrmLogin.designation == 2) {

						int callNo = Integer.valueOf(
								String.valueOf(tblReadyCalls.getModel().getValueAt(tblReadyCalls.getSelectedRow(), 0)));

						String moduleid = String
								.valueOf(tblReadyCalls.getModel().getValueAt(tblReadyCalls.getSelectedRow(), 8));

						boolean blnFrmCallcompletd = false;
						FrmCompletedDialog frmCompletedDialog = Applicationmain.getAbstractApplicationContext()
								.getBean(FrmCompletedDialog.class, new Object[] { getContentPane() });
						frmCompletedDialog.intializeVariable(callNo, moduleid, blnFrmCallcompletd);
						frmCompletedDialog.setVisible(true);

						btnView();
					} else {
						JOptionPane.showMessageDialog(panelMain, "You Are Not Authorized ... !");
					}

				}
				if (e.getKeyCode() == KeyEvent.VK_R) {

					int callNo = Integer.valueOf(
							String.valueOf(tblReadyCalls.getModel().getValueAt(tblReadyCalls.getSelectedRow(), 0)));

					String moduleid = String
							.valueOf(tblReadyCalls.getModel().getValueAt(tblReadyCalls.getSelectedRow(), 8));

					FrmReturnDialog frmReturnDialog = Applicationmain.getAbstractApplicationContext()
							.getBean(FrmReturnDialog.class, new Object[] { getContentPane() });
					frmReturnDialog.intializeVariable(callNo, moduleid);
					frmReturnDialog.setVisible(true);

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

	}

	protected void btnImage() {

		CallsImages callsImages = logicReadyCalls.getImages(String.valueOf(lblClCallNoVal.getText()));

		blnFrmDevCall = true;
		FrmImageDialog frmImageDialog = Applicationmain.getAbstractApplicationContext().getBean(FrmImageDialog.class,
				new Object[] { getContentPane() });
		frmImageDialog.booleanAssign(false, blnFrmDevCall, (String.valueOf(lblClCallNoVal.getText())), callsImages);
		frmImageDialog.setVisible(true);

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
		panelMain.add(panelReadyDetail());
		//		panelMain.add(panelContentInitialize());
		panelMain.add(panelLine3Inialize());

		getContentPane().add(panelMain);
		panelMain.addKeyListener(this);
	}

	private Component panelReadyDetail() {
		int lblWidth = 130;
		int lblHeight = 30;
		int txtWidth = 120;
		int txtHeight = 20;

		panelReady = new JPanel();
		panelReady.setBounds(20, panelDetail2.getY() + panelDetail2.getHeight() + 10, 918, 450);
		panelReady.setLayout(null);
		panelReady.setBackground(color2);
		panelReady.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelReady.setVisible(true);

		tblReadyCalls = new JilabaTable(getReadyCalls());
		tblReadyCalls.setAutoResizeMode(JilabaTable.AUTO_RESIZE_OFF);
		tblReadyCalls.getTableHeader().setReorderingAllowed(false);
		tblReadyCalls.getTableHeader().setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		tblReadyCalls.setFont(CustomFonts.fontCalibriPlain15);
		tblReadyCalls.getTableHeader().setForeground(color6);
		tblReadyCalls.getTableHeader().setBackground(Color.WHITE);
		tblReadyCalls.setForeground(Color.BLACK);
		tblReadyCalls.setRowHeight(20);

		tblReadyCalls.setVisible(true);

		scrReadyCalls = new JScrollPane(tblReadyCalls);
		scrReadyCalls.setBounds(10, 10, 900, 300);
		scrReadyCalls.getViewport().setBackground(tblReadyCalls.getTableHeader().getBackground());
		scrReadyCalls.setVisible(true);

		panelCallDesc = new JPanel();
		panelCallDesc.setBounds(scrReadyCalls.getX(), scrReadyCalls.getY() + scrReadyCalls.getHeight() + 10, 900, 120);
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
		lblCallDesc.setBounds(lblDepartment.getX(), lblDepartment.getY() + lblDepartment.getHeight() + 10, lblWidth,
				lblHeight);
		lblCallDesc.setBackground(color2);
		lblCallDesc.setForeground(Color.BLACK);
		lblCallDesc.setFont(CustomFonts.font);
		lblCallDesc.setVisible(true);

		txtCallDescVal = new JTextArea("");
		txtCallDescVal.setBounds(lblCallDesc.getX() + lblWidth - 50, lblCallDesc.getY() - 15, 800, lblHeight + 10);
		txtCallDescVal.setBackground(color4);
		txtCallDescVal.setLineWrap(true);
		txtCallDescVal.setForeground(Color.BLACK);
		txtCallDescVal.setFont(CustomFonts.fontCalibriBold);
		txtCallDescVal.setEditable(false);
		txtCallDescVal.setVisible(true);

		JScrollPane scrollcall = new JScrollPane(txtCallDescVal);
		scrollcall.setBounds(lblCallDesc.getX() + lblWidth - 50, lblCallDesc.getY() - 15, 800, lblHeight + 10);
		scrollcall.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollcall.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollcall);

		lblReadyDesc = new JLabel("Ready Desc : ");
		lblReadyDesc.setBounds(lblCallDesc.getX(), lblCallDesc.getY() + lblCallDesc.getHeight() + 10, lblWidth,
				lblHeight);
		lblReadyDesc.setBackground(color2);
		lblReadyDesc.setForeground(Color.BLACK);
		lblReadyDesc.setFont(CustomFonts.font);
		lblReadyDesc.setVisible(true);

		txtReadyDescVal = new JTextArea(" ");
		txtReadyDescVal.setBounds(lblReadyDesc.getX() + lblWidth - 50, lblReadyDesc.getY() - 5, 800, lblHeight + 10);
		txtReadyDescVal.setBackground(color4);
		txtReadyDescVal.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		txtReadyDescVal.setLineWrap(true);
		txtReadyDescVal.setForeground(Color.BLACK);
		txtReadyDescVal.setFont(CustomFonts.fontCalibriBold);
		txtReadyDescVal.setEditable(false);
		txtReadyDescVal.setVisible(true);

		JScrollPane scrollReasonPane = new JScrollPane(txtReadyDescVal);
		scrollReasonPane.setBounds(lblReadyDesc.getX() + lblWidth - 50, lblReadyDesc.getY() - 5, 800, lblHeight + 10);
		scrollReasonPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollReasonPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollReasonPane);

		lblPressEsc = new JLabel(
				"Press Esc to Close                            I - Image Details To Show                         C - Call Completion Process                            R - Call Return To Developer                        P- Progress Call Mark ");
		lblPressEsc.setHorizontalAlignment(SwingConstants.LEFT);
		lblPressEsc.setBounds(panelReady.getX(), panelReady.getY() + panelReady.getHeight(), 900, 20);
		lblPressEsc.setBackground(color2);
		lblPressEsc.setForeground(Color.BLACK);
		lblPressEsc.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		lblPressEsc.setVisible(true);

		lblQc1Progress = new JLabel("");
		lblQc1Progress.setHorizontalAlignment(SwingConstants.LEFT);
		lblQc1Progress.setBounds(750, lblPressEsc.getY() + 5, 10, 10);
		lblQc1Progress.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		//		lblProgressColourkey.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		lblQc1Progress.setBackground(Color.decode("#79addc"));
		lblQc1Progress.setOpaque(true);
		lblQc1Progress.setForeground(Color.decode("#79addc"));
		lblQc1Progress.setVisible(true);

		lblQc1Colourkey = new JLabel("QC 1");
		lblQc1Colourkey.setHorizontalAlignment(SwingConstants.LEFT);
		lblQc1Colourkey.setBounds(lblQc1Progress.getX() + lblQc1Progress.getWidth(), lblPressEsc.getY(), 50, 20);
		lblQc1Colourkey.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		//		lblProgressColourkeyNote.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		lblQc1Colourkey.setBackground(color2);
		lblQc1Colourkey.setVisible(true);

		lblQc2Progress = new JLabel("");
		lblQc2Progress.setHorizontalAlignment(SwingConstants.LEFT);
		lblQc2Progress.setBounds(lblQc1Colourkey.getX() + lblQc1Colourkey.getWidth() + 10, lblQc1Colourkey.getY() + 5,
				10, 10);
		lblQc2Progress.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		//		lblReturnColourkey.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		lblQc2Progress.setBackground(Color.decode("#ffee93"));
		lblQc2Progress.setOpaque(true);
		lblQc2Progress.setForeground(Color.decode("#ffee93"));
		lblQc2Progress.setVisible(true);

		lblQc2Colourkey = new JLabel("QC 2 ");
		lblQc2Colourkey.setHorizontalAlignment(SwingConstants.LEFT);
		lblQc2Colourkey.setBounds(lblQc2Progress.getX() + lblQc2Progress.getWidth() + 10, lblQc2Progress.getY() - 5, 50,
				20);
		lblQc2Colourkey.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		//		lblReturnColourkeyNote.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		lblQc2Colourkey.setBackground(Color.decode("#F3E5AB"));
		lblQc2Colourkey.setVisible(true);

		lblQc3Progress = new JLabel("");
		lblQc3Progress.setHorizontalAlignment(SwingConstants.LEFT);
		lblQc3Progress.setBounds(lblQc2Colourkey.getX() + lblQc2Colourkey.getWidth() + 10, lblQc2Colourkey.getY() + 5,
				10, 10);
		lblQc3Progress.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		//		lblReturnColourkey.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		lblQc3Progress.setBackground(Color.decode("#adf7b6"));
		lblQc3Progress.setOpaque(true);
		lblQc3Progress.setForeground(Color.decode("#adf7b6"));
		lblQc3Progress.setVisible(true);

		lblQc3Colourkey = new JLabel("QC 3 ");
		lblQc3Colourkey.setHorizontalAlignment(SwingConstants.LEFT);
		lblQc3Colourkey.setBounds(lblQc3Progress.getX() + lblQc3Progress.getWidth() + 10, lblQc3Progress.getY() - 5, 50,
				20);
		lblQc3Colourkey.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		//		lblReturnColourkeyNote.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		lblQc3Colourkey.setBackground(Color.decode("#adf7b6"));
		lblQc3Colourkey.setVisible(true);

		panelMain.add(panelReady);
		panelReady.add(scrReadyCalls);
		panelReady.add(panelCallDesc);
		panelCallDesc.add(lblDepartment);
		panelCallDesc.add(lblDepartmentVal);
		panelCallDesc.add(lblClCallNo);
		panelCallDesc.add(lblClCallNoVal);
		panelCallDesc.add(lblCallDate);
		panelCallDesc.add(lblCallDateVal);
		panelCallDesc.add(lblClModule);
		panelCallDesc.add(lblClModuleVal);
		panelCallDesc.add(lblCallDesc);
		panelCallDesc.add(scrollcall);
		panelCallDesc.add(lblReadyDesc);
		panelCallDesc.add(scrollReasonPane);

		panelMain.add(lblPressEsc);
		panelMain.add(lblQc1Progress);
		panelMain.add(lblQc1Colourkey);
		panelMain.add(lblQc2Progress);
		panelMain.add(lblQc2Colourkey);
		panelMain.add(lblQc3Progress);
		panelMain.add(lblQc3Colourkey);
		return panelReady;

	}

	private List<JilabaColumn> getReadyCalls() {

		List<JilabaColumn> jilabaColumnlist = new ArrayList<>();
		jilabaColumnlist.add(new JilabaColumn("CallNo", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Date ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Ready Date ", String.class, 200, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Ready By", String.class, 200, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Client Name ", String.class, 200, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Recd By ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Authorized ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Department ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Module ", String.class, 100, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Description ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Ready Description", String.class, 150, JLabel.LEFT));

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

		//		DSsd

		lblReady = new JLabel("Ready Date");
		lblReady.setBounds(rdpBetweenDate.getX() + rdpBetweenDate.getWidth(), rdpBetweenDate.getY() - 5, lblWidth,
				lblHeight);
		lblReady.setBackground(color2);
		lblReady.setForeground(Color.BLACK);
		lblReady.setFont(CustomFonts.font);
		lblReady.setVisible(true);

		panelReadyDate = new JPanel();
		panelReadyDate.setBounds(rdpBetweenDate.getX() + rdpBetweenDate.getWidth(), rdpBetweenDate.getY() + 15, 200,
				50);
		panelReadyDate.setLayout(null);
		panelReadyDate.setBackground(color2);
		panelReadyDate.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		panelReadyDate.setVisible(true);

		lblReadyFromDate = new JLabel("From Date");
		lblReadyFromDate.setBounds(10, 0, lblWidth, lblHeight);
		lblReadyFromDate.setBackground(color2);
		lblReadyFromDate.setVisible(true);
		lblReadyFromDate.setFont(jilabaFonts.getFont(14));

		spnReadyFromDate = new JilabaSpinner();
		spnReadyFromDate.setBounds(lblCallFromDate.getX(), lblCallFromDate.getY() + lblCallFromDate.getHeight() - 5, 75,
				20);
		spnReadyFromDate.setVisible(true);
		spnReadyFromDate.setBackground(color2);
		spnReadyFromDate.setFont(CustomFonts.fontCalibriBold);
		spnReadyFromDate.addKeyListener(this);

		lblReadyToDate = new JLabel("To Date");
		lblReadyToDate.setBounds(rdpBetweenDate.getX() - 10, 0, lblWidth, lblHeight);
		lblReadyToDate.setBackground(color2);
		lblReadyToDate.setVisible(true);
		lblReadyToDate.setFont(jilabaFonts.getFont(14));

		spnReadyToDate = new JilabaSpinner();
		spnReadyToDate.setBounds(lblCallToDate.getX(), lblCallToDate.getY() + lblCallToDate.getHeight() - 5, 75, 20);
		spnReadyToDate.setVisible(true);
		spnReadyToDate.setBackground(color2);
		spnReadyToDate.setFont(CustomFonts.fontCalibriBold);
		rdpAsOnDate.addKeyListener(this);

		lblDeveloper = new JLabel("Developer");
		lblDeveloper.setBounds(panelReadyDate.getX(), panelReadyDate.getY() + panelReadyDate.getHeight() + 10, lblWidth,
				lblHeight);
		lblDeveloper.setBackground(color2);
		lblDeveloper.setForeground(Color.BLACK);
		lblDeveloper.setFont(CustomFonts.font);
		lblDeveloper.setVisible(true);
		lblDeveloper.setFont(CustomFonts.font);

		cmbDeveloper = new JilabaComboBox<Operator>();
		cmbDeveloper.setBounds(lblDeveloper.getX() + 80, lblDeveloper.getY() + 5, txtWidth, txtHeight);
		cmbDeveloper.setBackground(color2);
		cmbDeveloper.setFont(CustomFonts.fontCalibriBold);
		cmbDeveloper.setVisible(true);
		cmbDeveloper.addKeyListener(this);

		lblClient = new JLabel("Client");
		lblClient.setBounds(panelReadyDate.getX() + panelReadyDate.getWidth() + 30, lblReady.getY(), lblWidth,
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

		cmbClient = new JilabaComboBox<Customer>();
		cmbClient.setBounds(lblClient.getX() + 100, lblClient.getY(), txtWidth, txtHeight);
		cmbClient.setBackground(color2);
		cmbClient.setFont(CustomFonts.fontCalibriBold);
		cmbClient.setVisible(true);
		cmbClient.addKeyListener(this);

		cmbDeptAuthorize = new JilabaComboBox<Operator>();
		cmbDeptAuthorize.setBounds(cmbClient.getX(), lblDeptAutorize.getY(), txtWidth, txtHeight);
		cmbDeptAuthorize.setBackground(color2);
		cmbDeptAuthorize.setFont(CustomFonts.fontCalibriBold);
		cmbDeptAuthorize.setVisible(true);
		cmbDeptAuthorize.addKeyListener(this);

		cmbDepartment = new JilabaComboBox<Department>();
		cmbDepartment.setBounds(cmbDeptAuthorize.getX(), lblDepartment.getY(), txtWidth, txtHeight);
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

		rdpDeveloper = new JRadioButton("Developer");
		rdpDeveloper.setBounds(rdpModule.getX() + rdpModule.getWidth(), 5, 50, 20);
		rdpDeveloper.setBackground(color2);
		rdpDeveloper.setVisible(true);
		rdpDeveloper.setFont(jilabaFonts.getFont(14));
		rdpDeveloper.setSelected(false);
		rdpDeveloper.addKeyListener(this);

		rdpClient = new JRadioButton("Client");
		rdpClient.setBounds(rdpDeveloper.getX() + rdpDeveloper.getWidth(), 5, 40, 20);
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

		rdpReadyDate = new JRadioButton("ReadyDate");
		rdpReadyDate.setBounds(rdpCallDate.getX() + rdpCallDate.getWidth(), rdpCallDate.getY(), 60, 20);
		rdpReadyDate.setBackground(color2);
		rdpReadyDate.setVisible(true);
		rdpReadyDate.setFont(jilabaFonts.getFont(14));
		rdpReadyDate.setSelected(false);
		rdpReadyDate.addKeyListener(this);

		rdpCallNo = new JRadioButton("CallNo");
		rdpCallNo.setBounds(rdpReadyDate.getX() + rdpReadyDate.getWidth(), rdpReadyDate.getY(), 60, 20);
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
		panelDetail2.add(rdpAsOnDate);
		panelDetail2.add(rdpBetweenDate);
		panelDetail2.add(lblCall);
		panelDetail2.add(lblReady);
		panelDetail2.add(panelCallDate);
		panelDetail2.add(panelReadyDate);
		panelCallDate.add(lblCallFromDate);
		panelCallDate.add(lblCallToDate);
		panelCallDate.add(spnCallFromDate);
		panelCallDate.add(spnCallToDate);
		panelReadyDate.add(lblReadyFromDate);
		panelReadyDate.add(lblReadyToDate);
		panelReadyDate.add(spnReadyFromDate);
		panelReadyDate.add(spnReadyToDate);
		panelDetail2.add(lblDeveloper);
		panelDetail2.add(cmbDeveloper);
		panelDetail2.add(lblClient);
		panelDetail2.add(lblDeptAutorize);
		panelDetail2.add(lblDepartment);
		panelDetail2.add(lblModule);
		panelDetail2.add(cmbClient);
		panelDetail2.add(cmbDeptAuthorize);
		panelDetail2.add(cmbDepartment);
		panelDetail2.add(cmbModule);
		panelDetail2.add(lblCallNo);
		panelDetail2.add(txtCallNo);
		panelDetail2.add(panelOrderby);
		btnOrderbyGroup.add(rdpNone);
		btnOrderbyGroup.add(rdpDeveloper);
		btnOrderbyGroup.add(rdpModule);
		btnOrderbyGroup.add(rdpClient);
		btnOrderbyGroup.add(rdpCallDate);
		btnOrderbyGroup.add(rdpCallDate);
		btnOrderbyGroup.add(rdpReadyDate);
		btnOrderbyGroup.add(rdpCallNo);
		panelOrderby.add(rdpNone);
		panelOrderby.add(rdpDeveloper);
		panelOrderby.add(rdpModule);
		panelOrderby.add(rdpClient);
		panelOrderby.add(rdpCallDate);
		panelOrderby.add(rdpReadyDate);
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

		lblHeading = new JLabel("QC PROCESS");
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

		new Timer().scheduleAtFixedRate(new TimerJob(lblDateTime), Calendar.getInstance().getTime(), 1);
		lblDateTime.setVisible(true);

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
				//				spnCallToDate = null;
				spnCallFromDate.requestFocus();
			} else if (e.getSource() == rdpBetweenDate) {

				spnCallFromDate.requestFocus();
				blnAsOnDate = false;

			} else if (e.getSource() == spnReadyToDate) {
				cmbDeveloper.requestFocus();
			} else if (e.getSource() == cmbDeveloper) {
				cmbClient.requestFocus();
			} else if (e.getSource() == cmbClient) {
				cmbDeptAuthorize.requestFocus();
			} else if (e.getSource() == cmbDeptAuthorize) {
				cmbDepartment.requestFocus();
			} else if (e.getSource() == cmbDepartment) {
				cmbModule.requestFocus();
			} else if (e.getSource() == cmbModule) {
				txtCallNo.requestFocus();
			} else if (e.getSource() == txtCallNo) {
				rdpNone.requestFocus();
			} else if (e.getSource() == rdpNone) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpModule) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpDeveloper) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpClient) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpCallDate) {
				btnView.requestFocus();
			} else if (e.getSource() == rdpReadyDate) {
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
				rdpDeveloper.requestFocus();
				rdpFalse();
				rdpDeveloper.setSelected(true);
			} else if (e.getSource() == rdpDeveloper) {
				rdpClient.requestFocus();
				rdpFalse();
				rdpClient.setSelected(true);
			} else if (e.getSource() == rdpClient) {
				rdpCallDate.requestFocus();
				rdpFalse();
				rdpCallDate.setSelected(true);
			} else if (e.getSource() == rdpCallDate) {
				rdpReadyDate.requestFocus();
				rdpFalse();
				rdpReadyDate.setSelected(true);
			} else if (e.getSource() == rdpReadyDate) {
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
			} else if (e.getSource() == rdpDeveloper) {
				rdpModule.requestFocus();
				rdpFalse();
				rdpModule.setSelected(true);
			} else if (e.getSource() == rdpClient) {
				rdpDeveloper.requestFocus();
				rdpFalse();
				rdpDeveloper.setSelected(true);
			} else if (e.getSource() == rdpCallDate) {
				rdpClient.requestFocus();
				rdpFalse();
				rdpClient.setSelected(true);
			} else if (e.getSource() == rdpReadyDate) {
				rdpCallDate.requestFocus();
				rdpFalse();
				rdpCallDate.setSelected(true);
			} else if (e.getSource() == rdpCallNo) {
				rdpReadyDate.requestFocus();
				rdpFalse();
				rdpReadyDate.setSelected(true);
			}

		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

			setVisible(false);

			FrmMainMenu frmMainMenu = Applicationmain.getAbstractApplicationContext().getBean(FrmMainMenu.class);
			frmMainMenu.setVisible(true);

		}

	}

	private void rdpFalse() {

		rdpNone.setSelected(false);
		rdpModule.setSelected(false);
		rdpDeveloper.setSelected(false);
		rdpClient.setSelected(false);
		rdpCallDate.setSelected(false);
		rdpReadyDate.setSelected(false);
		rdpCallNo.setSelected(false);
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

			rdpBetweenDate.setVisible(true);
			lblCallToDate.setVisible(true);

			spnCallToDate.setVisible(true);
			try {
				spnCallToDate.setValue(spnCallToDate.getDateValue());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			loadDetails();

			rdpAsOnDate.requestFocus();
		}

	}

	private void btnView() {

		dateCheck();

		selectAllCheck();

		orderByCheck();

		tblReadyCalls.clear();

		lstReadyCalls = logicReadyCalls.getReadyCalls(strCallFromDate, strCallToDate, strReadyFromDate, strReadyToDate,
				strCmbDeveloper, strCmbClient, strCmbDeptAuthorize, strCmbDepartment, strCmbModule, strOrderby,
				txtCallNo.getText());

		List<Object> lstObjects = null;

		int i = -1;
		int Q1 = 0;
		int Q2 = 0;
		int Q3 = 0;

		progress = logicReadyCalls.validateProgressCall();

		for (ReadyCalls readyCalls : lstReadyCalls) {

			lstObjects = new ArrayList<Object>();

			lstObjects.add(readyCalls.getCallno());
			lstObjects.add(readyCalls.getCdate());
			lstObjects.add(readyCalls.getRDate());
			lstObjects.add(readyCalls.getReadyByName());
			lstObjects.add(readyCalls.getCustName().trim());
			lstObjects.add(readyCalls.getRecdby());
			lstObjects.add(readyCalls.getAuthorized());
			lstObjects.add(readyCalls.getDepartment());
			lstObjects.add(readyCalls.getModuleName());
			lstObjects.add(readyCalls.getDescription());
			lstObjects.add(readyCalls.getReadydescription());

			i = i + 1;

			//			if (readyCalls.getQcOperator() == Qc1 && progress.get(0).getQcOperator() == Qc1) {
			//				Q1 = i;
			//			} else if (readyCalls.getQcOperator() == Qc2 && progress.get(1).getQcOperator() == Qc2) {
			//				Q2 = i;
			//			} else if (readyCalls.getQcOperator() == Qc3 && progress.get(2).getQcOperator() == Qc3) {
			//				Q3 = i;
			//			}

			for (ReadyCalls rc : progress) {
				if (readyCalls.getQcOperator() == Qc1 && rc.getQcOperator() == Qc1) {
					Q1 = i;
				} else if (readyCalls.getQcOperator() == Qc2 && rc.getQcOperator() == Qc2) {
					Q2 = i;
				} else if (readyCalls.getQcOperator() == Qc3 && rc.getQcOperator() == Qc3) {
					Q3 = i;
				}
			}

//			System.out.println(readyCalls.getQcOperator());

			tblReadyCalls.addRow(lstObjects);

		}

		strCallNo = 0;

		if (Q1 != 0)
			tblReadyCalls.setRowColor(Q1, Color.decode("#79addc"));
		if (Q2 != 0)
			tblReadyCalls.setRowColor(Q2, Color.decode("#ffee93"));
		if (Q3 != 0)
			tblReadyCalls.setRowColor(Q3, Color.decode("#adf7b6"));

		Q1 = 0;
		Q2 = 0;
		Q3 = 0;
		if (lstReadyCalls.size() == 0) {
			JOptionPane.showMessageDialog(panelMain, "Call Details Not Found !...");
			btnView.requestFocus();
			return;
		}

	}

	private void dateCheck() {

		if (rdpAsOnDate.isSelected() == true) {
			strCallFromDate = spnCallFromDate.getDateValue();
			strCallToDate = null;
		} else {
			strCallFromDate = spnCallFromDate.getDateValue();
			strCallToDate = spnCallToDate.getDateValue();
		}
		if (rdpReadyDate.isSelected() == true) {
			strReadyFromDate = spnReadyFromDate.getDateValue();
			strReadyToDate = null;
		} else {
			strReadyFromDate = spnReadyFromDate.getDateValue();
			strReadyToDate = spnReadyToDate.getDateValue();
		}

	}

	private void orderByCheck() {

		if (rdpNone.isSelected() == true) {
			strOrderby = "";
		} else if (rdpModule.isSelected() == true) {
			strOrderby = "c.moduleid";
		} else if (rdpDeveloper.isSelected() == true) {
			strOrderby = "c.sugto";
		} else if (rdpClient.isSelected() == true) {
			strOrderby = "c.cusid";
		} else if (rdpCallDate.isSelected() == true) {
			strOrderby = "c.cdate";
		} else if (rdpReadyDate.isSelected() == true) {
			strOrderby = "R.rdate";
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

		if (cmbDeptAuthorize.getSelectedItem().equals("All")) {
			strCmbDeptAuthorize = 0;
		} else {
			strCmbDeptAuthorize = Integer.parseInt(String.valueOf(cmbDeptAuthorize.getSelectedItemValue()));
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

}
