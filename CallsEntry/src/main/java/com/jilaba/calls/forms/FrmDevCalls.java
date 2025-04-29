package com.jilaba.calls.forms;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.codehaus.jackson.map.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.common.ImageResource;
import com.jilaba.calls.common.LoginCredential;
import com.jilaba.calls.common.TimerJob;
import com.jilaba.calls.logic.LogicDevCalls;
import com.jilaba.calls.logic.LogicTaskAssignment;
import com.jilaba.calls.model.Calls;
import com.jilaba.calls.model.CallsImages;
import com.jilaba.calls.model.CustStaff;
import com.jilaba.calls.model.Customer;
import com.jilaba.calls.model.Department;
import com.jilaba.calls.model.Location;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.model.ReturnCalls;
import com.jilaba.calls.start.Applicationmain;
import com.jilaba.common.ReturnStatus;
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
public class FrmDevCalls extends JFrame implements ActionListener, KeyListener {

	private JPanel panelMain;
	private JPanel panelTitle;
	private JPanel panelLine;
	private JPanel panelDetail;
	private JPanel panelLine2;
	private JPanel panelDetail2;
	private JPanel panelTakenCall;
	private JPanel panelContent;
	private JPanel panelcallTransfer;
	private JPanel panelLine3;
	private JPanel panelCallDesc;

	private JLabel lblHeading;
	private JLabel lblCallHead;
	private boolean isSelected;

	private JLabel lblDevelopedby;
	private JLabel lblOperatorLabel;
	private JLabel lblVersion;
	private JLabel lblServerIpValue;
	private JLabel lblDateTime;

	private JLabel lblAsOnDate;
	private JLabel lblCallNo;
	private JLabel lblCustomer;
	private JLabel lblDeveloper;
	private JLabel lblCallCoOrd;
	private JLabel lblDepartment;
	private JLabel lblType;
	private JLabel lblDeptAuthorize;
	private JLabel lblAssignDate;
	private JLabel lblDueDate;
	private JLabel lblRecvFrom;
	private JLabel lblModule;
	private JLabel lblAssignTime;
	private JLabel lblCallTakenDate;
	private JLabel lblRecBy;
	private JLabel lblImage;
	private JLabel lblCallDetailTitle;
	private JLabel lblCallNature;
	private JLabel lblPressEsc;
	private JLabel lblShortcutkey;
	private JLabel lblProgressColourkey;
	private JLabel lblProgressColourkeyNote;
	private JLabel lblReturnColourkey;
	private JLabel lblReturnColourkeyNote;
	private JLabel lblCallDesc;
	private JLabel lblCallDate;
	private JLabel lblCallAndTicket;
	private JLabel lblTotalcall;
	private JLabel lblTotalcallVal;

	private JLabel lblCallAndTicketval;
	private JLabel lblCallDateVal;
	private JLabel lblAssignDateVal;
	private JLabel lblDueDateVal;
	private JLabel lblCallTakenDateVal;
	private JLabel lblAssignTimeVal;
	private JLabel lblRecByVal;
	private JLabel lblMinimize;

	private JTextArea txtCallDesc;

	private JilabaSpinner spnAsOnDate;

	private JilabaTextField txtCallNo;

	private int selectedRow;

	private JButton btnView;
	private JButton btnUpdate;
	private JButton btnImage;

	private JilabaTable tblDevCalls;

	private JScrollPane scrDevCalls;

	private JilabaTextField txtDevPriority;

	private boolean blnFrmDevCall = false;

	private JilabaComboBox<String> cmbType;
	private JilabaComboBox<Calls> cmbTicketNo;
	private JilabaComboBox<Customer> cmbCustomer;
	private JilabaComboBox<Operator> cmbDeveloper;
	private JilabaComboBox<Operator> cmbCallCoOrd;
	private JilabaComboBox<Department> cmbDepartment;
	private JilabaComboBox<Operator> cmbDeptAuthor;
	private JilabaComboBox<Operator> cmbDeptAuthorize;
	private JilabaComboBox<Operator> cmbRecvFrom;
	private JilabaComboBox<Module> cmbModule;
	private JilabaComboBox<Location> cmbLoaction;
	private JilabaComboBox<String> cmbCallNature;

	private int strDeveleoper;
	private int strDepartment;
	private int strModule;
	private int strCustomer;
	private int strType;
	private int strDeptAuthorize;
	private int strRecvFrom;
	private int strCallCoOrd;
	private int strCallNature;

	private long strCallNo;

	private List<Operator> lstDeveloper;
	private List<Department> lstDepartment;
	private List<com.jilaba.calls.model.Module> lstModule;
	private List<Customer> lstCustomer;
	private List<String> lstType;
	private List<CustStaff> lstCustCoOrd;
	private List<Operator> lstRecvFrom;
	private List<Operator> lstCallCoOrd;
	private List<String> lstNature;
	private List<Calls> lstDevCalls;

	private Color color1 = Color.decode("#808B96");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#2C3E50");
	private Color color4 = Color.decode("#AED6F1");
	private Color color5 = Color.decode("#e4dedf");
	private Color color6 = Color.decode("#A6ACAF");
	private Color color7 = Color.decode("#FF3933");
	private Color color8 = Color.decode("#008000");

	private ControlResize controlResize;

	private Color fontColor1 = Color.decode("#17202A");

	private JilabaFonts jilabaFonts = new JilabaFonts();

	private Operator operator;

	List<Calls> lstList;

	@Autowired
	private LogicDevCalls logicDevCalls;
	@Autowired
	private CommonMethods commonMethods;

	private boolean blnProgress = false;
	List<Calls> progress = new ArrayList<Calls>();

	public FrmDevCalls() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		getContentPane().setPreferredSize(new Dimension(958, 728));
		setLayout(null);
		pack();

		createControls();
		createKeyListeners();

		controlResize = new ControlResize(this);
		setSize(controlResize.getRectangle().getSize());
		controlResize.reAlignControls();

	}

	private void enableTextField() {
		tblDevCalls.add(txtDevPriority); // Add the text field to the JFrame or JPanel containing the table
		// Set the text field position based on selected cell
		int selectedRow = tblDevCalls.getSelectedRow();
		int selectedColumn = 2;
		if (selectedRow != -1 && selectedColumn != -1) {
			Rectangle cellRect = tblDevCalls.getCellRect(selectedRow, selectedColumn, true);
			txtDevPriority.setBounds(cellRect);
			txtDevPriority.setVisible(true);
			txtDevPriority.requestFocus();
			txtDevPriority.addKeyListener(this);
		}
	}

	private void createKeyListeners() {

		txtCallNo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cmbDeveloper.requestFocus();
				}
			}

		});

		spnAsOnDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					txtCallNo.requestFocus();
				}

			}

		});

		lblMinimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setState(JFrame.ICONIFIED);
			}
		});

		txtDevPriority.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				System.out.println(txtDevPriority.getText());
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					String SelectedCallno = String
							.valueOf(tblDevCalls.getModel().getValueAt(tblDevCalls.getSelectedRow(), 0));

					logicDevCalls.updateDevPriority(txtDevPriority.getText(), SelectedCallno);

					txtDevPriority.setVisible(false);
					txtDevPriority.setText("");
					btnView();
					tblDevCalls.requestFocus();

				}

			}
		});
		txtDevPriority.setInputVerifier(new InputVerifier() {

			@Override
			public boolean verify(JComponent input) {
				System.out.println("Entered Value" + txtDevPriority.getText());
				return false;
			}
		});

		tblDevCalls.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (FrmLogin.designation == 3 && !cmbDeveloper.getSelectedItem().equals("All")) {
						enableTextField();

					} else {
						JOptionPane.showMessageDialog(panelMain, "You dont have Rights ... !");
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_P) {

					if (FrmLogin.OperCode == Integer.valueOf(String.valueOf(cmbDeveloper.getSelectedItemValue()))) {

						lstList = new ArrayList<Calls>();

						lstList = logicDevCalls.validateReadyCalls(
								String.valueOf(tblDevCalls.getModel().getValueAt(tblDevCalls.getSelectedRow(), 0)));

						if (!lstList.get(0).getReady().equalsIgnoreCase("Y")) {

							int response = JOptionPane.showConfirmDialog(panelMain, "Do you want to proceed?",
									"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

							if (response == JOptionPane.YES_OPTION) {
								selectedRow = tblDevCalls.getSelectedRow();

								int callNo = Integer.valueOf(String.valueOf(lblCallAndTicketval.getText()));

								// lstCheck = new ArrayList<Calls>();

								// lstCheck = logicDevCalls.validateProgressCall(FrmLogin.OperCode);

								if ((progress.size() == 0 && strType != 2) && (progress.size() == 0 && strType != 3)) {

									logicDevCalls.updateProgressCall(callNo);

									tblDevCalls.setRowColor(selectedRow, Color.decode("#CEAB8C"));
								} else {

									JOptionPane.showMessageDialog(panelContent, "Progress Call Already Found !");
								}
							} else if (response == JOptionPane.NO_OPTION) {
								tblDevCalls.requestFocus();
								return;
							}
						} else {

							JOptionPane.showMessageDialog(panelMain,
									"Call No- " + lstList.get(0).getCallno() + " Already Ready Marked ...!");
						}

					} else {

						JOptionPane.showMessageDialog(panelMain, "You Are Not Authorized For this ... !");
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_R) {

					if ((FrmLogin.OperCode == Integer.valueOf(String.valueOf(cmbDeveloper.getSelectedItemValue())))
							|| (FrmLogin.designation == 3)) {

						int callNo = Integer.valueOf(
								String.valueOf(tblDevCalls.getModel().getValueAt(tblDevCalls.getSelectedRow(), 0)));

						int moduleid = Integer.valueOf(
								String.valueOf(tblDevCalls.getModel().getValueAt(tblDevCalls.getSelectedRow(), 13)));

						FrmReadyDialog frmReadyDialog = Applicationmain.getAbstractApplicationContext()
								.getBean(FrmReadyDialog.class, new Object[] { getContentPane() });
						frmReadyDialog.intializeVariable(callNo, moduleid);
						frmReadyDialog.setVisible(true);

						btnView();
					} else {

						JOptionPane.showMessageDialog(panelContent, "You Are Not Authorized For this ...!");
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_I) {

					btnImageClick();
				}
				if (e.getKeyCode() == KeyEvent.VK_C) {

					if (FrmLogin.OperCode == Integer.valueOf(String.valueOf(cmbDeveloper.getSelectedItemValue()))) {

						lstList = new ArrayList<Calls>();

						lstList = logicDevCalls.validateReadyCalls(
								String.valueOf(tblDevCalls.getModel().getValueAt(tblDevCalls.getSelectedRow(), 0)));

						if (!lstList.get(0).getReady().equalsIgnoreCase("Y")) {

							int response = JOptionPane.showConfirmDialog(panelMain, "Are You Sure to Cancel Progress?",
									"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

							if (response == JOptionPane.YES_OPTION) {

								logicDevCalls.insertProgressCall(String
										.valueOf(tblDevCalls.getModel().getValueAt(tblDevCalls.getSelectedRow(), 0)));

								logicDevCalls.updateProgressCancel(String
										.valueOf(tblDevCalls.getModel().getValueAt(tblDevCalls.getSelectedRow(), 0)));
							}

						} else {

							JOptionPane.showMessageDialog(panelMain,
									"Call No- " + lstList.get(0).getCallno() + " Already Ready Marked ...!");
						}

					} else {

						JOptionPane.showMessageDialog(panelMain, "You Are Not Authorized For this ... !");
					}

					btnView();
				}

				if (e.getKeyCode() == KeyEvent.VK_T) {

					int callNo = Integer.valueOf(
							String.valueOf(tblDevCalls.getModel().getValueAt(tblDevCalls.getSelectedRow(), 0)));

					int moduleid = Integer.valueOf(
							String.valueOf(tblDevCalls.getModel().getValueAt(tblDevCalls.getSelectedRow(), 13)));

					FrmTransferCall frmTransferCall = Applicationmain.getAbstractApplicationContext()
							.getBean(FrmTransferCall.class, new Object[] { getContentPane() });
					frmTransferCall.intializeVariable(callNo, moduleid);
					frmTransferCall.setVisible(true);

					btnView();

				}

			}
		});

		cmbModule.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				lstModule = logicDevCalls
						.getModule(Integer.valueOf(String.valueOf(cmbDepartment.getSelectedItemValue())));

				cmbModule.removeAllItems();
				cmbModule.addListItem(new ListItem("All"));

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
		// panelMain.add(panelDevCallSummary());
		panelMain.add(panelContentInitialize());
		panelMain.add(panelLine3Inialize());

		getContentPane().add(panelMain);

		lblTotalcall = new JLabel("TOTAL CALLS   - ");
		lblTotalcall.setBounds(830, panelLine2.getY() + 110, 80, 20);
		lblTotalcall.setBackground(color7);
		lblTotalcall.setForeground(color7);
		lblTotalcall.setFont(jilabaFonts.getFont(FontStyle.BOLD, 18));
		lblTotalcall.setVisible(true);
		lblTotalcall.addKeyListener(this);

		lblTotalcallVal = new JLabel("");
		lblTotalcallVal.setBounds(lblTotalcall.getX() + 70, lblTotalcall.getY(), 80, 20);
		lblTotalcallVal.setBackground(color7);
		lblTotalcallVal.setForeground(color7);
		lblTotalcallVal.setFont(jilabaFonts.getFont(FontStyle.BOLD, 18));
		lblTotalcallVal.setVisible(true);
		lblTotalcallVal.addKeyListener(this);

		txtDevPriority = new JilabaTextField();
		txtDevPriority.setBackground(color2);
		txtDevPriority.setFont(CustomFonts.fontCalibriBold);
		txtDevPriority.setMaxLength(10);
		txtDevPriority.setVisible(false);

		panelMain.add(lblTotalcall);
		panelMain.add(lblTotalcallVal);

	}

	@PostConstruct
	private ReturnStatus loadInitialize() {

		lblServerIpValue.setText(commonMethods.getIpAddress());
		cmbDeveloper.removeAllItems();
		cmbDepartment.removeAllItems();
		cmbModule.removeAllItems();
		cmbCustomer.removeAllItems();
		cmbDeptAuthorize.removeAllItems();
		cmbCallCoOrd.removeAllItems();
		cmbRecvFrom.removeAllItems();
		cmbCallNature.removeAllItems();
		cmbType.removeAllItems();
		txtCallNo.setText("");

		cmbDeveloper.addListItem(new ListItem("All", 0));
		cmbDepartment.addListItem(new ListItem("All", 0));
		cmbModule.addListItem(new ListItem("All", 0));
		cmbCustomer.addListItem(new ListItem("All", 0));
		cmbType.addListItem(new ListItem("All", 0));
		cmbDeptAuthorize.addListItem(new ListItem("All", 0));
		cmbRecvFrom.addListItem(new ListItem("All", 0));
		cmbCallCoOrd.addListItem(new ListItem("All", 0));
		cmbCallNature.addListItem(new ListItem("All", 0));

		lstDeveloper = logicDevCalls.getDeveloper();

		for (Operator dev : lstDeveloper) {
			cmbDeveloper.addListItem(new ListItem(dev.getStaffname(), dev.getStaffid()));
		}

		lstDepartment = logicDevCalls.getDepartment();

		for (Department dept : lstDepartment) {
			cmbDepartment.addListItem(new ListItem(dept.getDepartment(), dept.getdNo()));
		}

		lstModule = logicDevCalls.getModule(0);

		for (com.jilaba.calls.model.Module module : lstModule) {
			cmbModule.addListItem(new ListItem(module.getModuleName(), module.getModuleId()));
		}

		lstCustomer = logicDevCalls.getCustomer();

		for (Customer customer : lstCustomer) {
			cmbCustomer.addListItem(new ListItem(customer.getcustName(), customer.getCustId()));
		}

		lstCustCoOrd = logicDevCalls.getDeptAuthorize();

		for (CustStaff deptAuthorize : lstCustCoOrd) {
			cmbDeptAuthorize
					.addListItem(new ListItem(deptAuthorize.getCustStaffName(), deptAuthorize.getCustStaffId()));
		}

		lstRecvFrom = logicDevCalls.getRecvFrom();

		for (Operator recvFrom : lstRecvFrom) {
			cmbRecvFrom.addListItem(new ListItem(recvFrom.getStaffname(), recvFrom.getStaffid()));
		}

		lstCallCoOrd = logicDevCalls.getCallCoOrd();

		for (Operator callCoOrd : lstCallCoOrd) {
			cmbCallCoOrd.addListItem(new ListItem(callCoOrd.getStaffname(), callCoOrd.getStaffid()));
		}

		cmbType.addListItem(new ListItem("Pending Calls", 1));
		cmbType.addListItem(new ListItem("Ready Calls", 2));
		cmbType.addListItem(new ListItem("Completed Calls", 3));

		cmbDeveloper.setSelectedItemValue(FrmLogin.OperCode);
		cmbType.setSelectedItemValue(1);

		cmbCallNature.addListItem(new ListItem("Error", 1));
		cmbCallNature.addListItem(new ListItem("Modification", 2));
		cmbCallNature.addListItem(new ListItem("Clarification", 3));
		cmbCallNature.addListItem(new ListItem("Development", 4));
		cmbCallNature.addListItem(new ListItem("General", 5));
		cmbCallNature.addListItem(new ListItem("Tallying", 6));

		// lstCheck= logicDevCalls.validateProgressCall();
		//
		// if (lstCheck != null) {
		//
		//// logicDevCalls.updateProgressCall(callNo);
		//
		// tblDevCalls.setRowColor(selectedRow, Color.decode("#CEAB8C"));
		// }
		//

		return new ReturnStatus(true);
	}

	private Component panelContentInitialize() {

		panelContent = new JPanel();
		panelContent.setBounds(panelDetail2.getX(), panelDetail2.getY() + panelDetail2.getHeight() + 20, 918, 430);
		panelContent.setLayout(null);
		panelContent.setBackground(color2);
		panelContent.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelContent.setVisible(true);

		lblCallHead = new JLabel("Pending Calls");
		lblCallHead.setBounds(900 / 2, 10, 130, 20);
		lblCallHead.setBackground(color2);
		lblCallHead.setFont(jilabaFonts.getFont(FontStyle.PLAIN, 18));
		lblCallHead.setVisible(true);
		lblCallHead.addKeyListener(this);

		tblDevCalls = new JilabaTable(getDevCalls());
		tblDevCalls.setAutoResizeMode(JilabaTable.AUTO_RESIZE_OFF);
		tblDevCalls.getTableHeader().setReorderingAllowed(false);
		tblDevCalls.getTableHeader().setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		tblDevCalls.setFont(CustomFonts.fontCalibriPlain15);
		tblDevCalls.getTableHeader().setBackground(Color.WHITE);
		tblDevCalls.getTableHeader().setForeground(color7);
		tblDevCalls.setForeground(Color.BLACK);
		tblDevCalls.setRowHeight(22);
		tblDevCalls.setVisible(true);
		tblDevCalls.addKeyListener(this);

		scrDevCalls = new JScrollPane(tblDevCalls);
		scrDevCalls.setBounds(10, 10, 680, 360);
		scrDevCalls.getViewport().setBackground(tblDevCalls.getTableHeader().getBackground());
		scrDevCalls.setVisible(true);

		// panelCallDetail = new JPanel();
		// panelCallDetail.setBounds(scrDevCalls.getX(), scrDevCalls.getY() +
		// scrDevCalls.getHeight() + 10, 900, 40);
		// panelCallDetail.setLayout(null);
		// panelCallDetail.setBackground(color6);
		// // panelCallDetail.setBorder(BorderFactory.createEtchedBorder(color3,
		// color3));
		// panelCallDetail.setVisible(true);

		txtCallDesc = new JTextArea("");
		txtCallDesc.setBounds(scrDevCalls.getX(), scrDevCalls.getY() + scrDevCalls.getHeight() + 5, 900, 50);
		txtCallDesc.setBackground(color6);
		txtCallDesc.setBorder(BorderFactory.createEtchedBorder(color6, color6));
		txtCallDesc.setFont(CustomFonts.fontCalibriBold);
		txtCallDesc.setForeground(Color.BLACK);
		txtCallDesc.setLineWrap(true);
		txtCallDesc.setEditable(false);
		txtCallDesc.setVisible(true);

		JScrollPane scrollReasonPane = new JScrollPane(txtCallDesc);
		scrollReasonPane.setBounds(scrDevCalls.getX(), scrDevCalls.getY() + scrDevCalls.getHeight() + 5, 900, 50);
		scrollReasonPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollReasonPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollReasonPane);

		lblCallDesc = new JLabel("");
		lblCallDesc.setBounds(scrDevCalls.getX(), scrDevCalls.getY() + scrDevCalls.getHeight(), 680, 30);
		lblCallDesc.setBackground(color2);
		lblCallDesc.setFont(jilabaFonts.getFont(FontStyle.PLAIN, 18));
		lblCallDesc.setVisible(true);
		lblCallDesc.addKeyListener(this);

		panelTakenCall = new JPanel();
		panelTakenCall.setBounds(700, 10, 210, 350);
		panelTakenCall.setLayout(null);
		panelTakenCall.setBackground(color2);
		panelTakenCall.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelTakenCall.setVisible(true);

		lblCallDetailTitle = new JLabel(" CALL DETAILS ");
		lblCallDetailTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblCallDetailTitle.setBounds(panelTakenCall.getWidth() / 3, 10, 130, 20);
		lblCallDetailTitle.setFont(CustomFonts.fontCalibriBold);
		lblCallDetailTitle.setBackground(color2);
		lblCallDetailTitle.setVisible(true);

		lblCallAndTicket = new JLabel(" Call No ");
		lblCallAndTicket.setHorizontalAlignment(SwingConstants.LEFT);
		lblCallAndTicket.setBounds(10, lblCallDetailTitle.getY() + lblCallDetailTitle.getHeight() + 10, 130, 20);
		lblCallAndTicket.setFont(CustomFonts.fontCalibriBold);
		lblCallAndTicket.setBackground(color2);
		lblCallAndTicket.setVisible(true);

		lblCallAndTicketval = new JLabel("");
		lblCallAndTicketval.setHorizontalAlignment(SwingConstants.LEFT);
		lblCallAndTicketval.setBounds(lblCallAndTicket.getX() + 50, lblCallAndTicket.getY(), 130, 20);
		lblCallAndTicketval.setFont(CustomFonts.fontCalibriBold);
		lblCallAndTicketval.setBackground(color2);
		lblCallAndTicketval.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCallAndTicketval.setVisible(true);

		lblCallDate = new JLabel(" Call Date ");
		lblCallDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblCallDate.setBounds(lblCallAndTicket.getX(), lblCallAndTicket.getY() + lblCallAndTicket.getHeight() + 20, 130,
				20);
		lblCallDate.setFont(CustomFonts.fontCalibriBold);
		lblCallDate.setBackground(color2);
		lblCallDate.setVisible(true);

		lblCallDateVal = new JLabel("");
		lblCallDateVal.setHorizontalAlignment(SwingConstants.LEFT);
		lblCallDateVal.setBounds(lblCallDate.getX() + 50, lblCallDate.getY(), 130, 20);
		lblCallDateVal.setFont(CustomFonts.fontCalibriBold);
		lblCallDateVal.setBackground(color2);
		lblCallDateVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCallDateVal.setVisible(true);

		lblAssignDate = new JLabel(" Assigned Date ");
		lblAssignDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblAssignDate.setBounds(lblCallDate.getX(), lblCallDate.getY() + lblCallDate.getHeight() + 20, 130, 20);
		lblAssignDate.setFont(CustomFonts.fontCalibriBold);
		lblAssignDate.setBackground(color2);
		lblAssignDate.setVisible(true);

		lblAssignDateVal = new JLabel("");
		lblAssignDateVal.setHorizontalAlignment(SwingConstants.LEFT);
		lblAssignDateVal.setBounds(lblAssignDate.getX() + 50, lblAssignDate.getY(), 130, 20);
		lblAssignDateVal.setFont(CustomFonts.fontCalibriBold);
		lblAssignDateVal.setBackground(color2);
		lblAssignDateVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAssignDateVal.setVisible(true);

		lblDueDate = new JLabel("Call Nature");
		lblDueDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblDueDate.setBounds(lblAssignDate.getX(), lblAssignDate.getY() + lblAssignDate.getHeight() + 20, 130, 20);
		lblDueDate.setFont(CustomFonts.fontCalibriBold);
		lblDueDate.setBackground(color2);
		lblDueDate.setVisible(true);

		lblDueDateVal = new JLabel("");
		lblDueDateVal.setHorizontalAlignment(SwingConstants.LEFT);
		lblDueDateVal.setBounds(lblDueDate.getX() + 50, lblDueDate.getY(), 130, 20);
		lblDueDateVal.setFont(CustomFonts.fontCalibriBold);
		lblDueDateVal.setBackground(color2);
		lblDueDateVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDueDateVal.setVisible(true);

		lblCallTakenDate = new JLabel(" Call Taken Date ");
		lblCallTakenDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblCallTakenDate.setBounds(lblDueDate.getX(), lblDueDate.getY() + lblDueDate.getHeight() + 20, 130, 20);
		lblCallTakenDate.setFont(CustomFonts.fontCalibriBold);
		lblCallTakenDate.setBackground(color2);
		lblCallTakenDate.setVisible(true);

		lblCallTakenDateVal = new JLabel("");
		lblCallTakenDateVal.setHorizontalAlignment(SwingConstants.LEFT);
		lblCallTakenDateVal.setBounds(lblCallTakenDate.getX() + 50, lblCallTakenDate.getY(), 130, 20);
		lblCallTakenDateVal.setFont(CustomFonts.fontCalibriBold);
		lblCallTakenDateVal.setBackground(color2);
		lblCallTakenDateVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCallTakenDateVal.setVisible(true);

		lblAssignTime = new JLabel("Assigned Time ");
		lblAssignTime.setHorizontalAlignment(SwingConstants.LEFT);
		lblAssignTime.setBounds(lblCallTakenDate.getX(), lblCallTakenDate.getY() + lblCallTakenDate.getHeight() + 20,
				130, 20);
		lblAssignTime.setFont(CustomFonts.fontCalibriBold);
		lblAssignTime.setBackground(color2);
		lblAssignTime.setVisible(true);

		lblAssignTimeVal = new JLabel("");
		lblAssignTimeVal.setHorizontalAlignment(SwingConstants.LEFT);
		lblAssignTimeVal.setBounds(lblAssignTime.getX() + 50, lblAssignTime.getY(), 130, 20);
		lblAssignTimeVal.setFont(CustomFonts.fontCalibriBold);
		lblAssignTimeVal.setBackground(color2);
		lblAssignTimeVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAssignTimeVal.setVisible(true);

		lblRecBy = new JLabel(" Received By");
		lblRecBy.setHorizontalAlignment(SwingConstants.LEFT);
		lblRecBy.setBounds(lblAssignTime.getX(), lblAssignTime.getY() + lblAssignTime.getHeight() + 20, 130, 20);
		lblRecBy.setFont(CustomFonts.fontCalibriBold);
		lblRecBy.setBackground(color2);
		lblRecBy.setVisible(true);

		lblRecByVal = new JLabel("");
		lblRecByVal.setHorizontalAlignment(SwingConstants.LEFT);
		lblRecByVal.setBounds(lblRecBy.getX() + 50, lblRecBy.getY(), 130, 20);
		lblRecByVal.setFont(CustomFonts.fontCalibriBold);
		lblRecByVal.setBackground(color2);
		lblRecByVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRecByVal.setVisible(true);

		lblImage = new JLabel(" Image Details");
		lblImage.setHorizontalAlignment(SwingConstants.LEFT);
		lblImage.setBounds(lblRecBy.getX(), lblRecBy.getY() + lblRecBy.getHeight() + 20, 130, 20);
		lblImage.setFont(CustomFonts.fontCalibriBold);
		lblImage.setBackground(color2);
		lblImage.setVisible(true);

		btnImage = new JButton("Not Attached");
		btnImage.setBounds(lblImage.getX() + 110, lblImage.getY(), 80, 20);
		btnImage.setBackground(color2);
		btnImage.setForeground(Color.BLACK);
		btnImage.setMnemonic(KeyEvent.VK_I);
		btnImage.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		btnImage.setHorizontalAlignment(SwingConstants.CENTER);
		btnImage.setBorder(BorderFactory.createEmptyBorder());
		btnImage.setVisible(true);
		btnImage.setEnabled(true);
		btnImage.addActionListener(this);
		btnImage.addKeyListener(this);

		lblPressEsc = new JLabel("Press Esc to Close ");
		lblPressEsc.setHorizontalAlignment(SwingConstants.LEFT);
		lblPressEsc.setBounds(panelContent.getX(), panelContent.getY() + panelContent.getHeight() + 10, 130, 20);
		lblPressEsc.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		lblPressEsc.setBackground(color2);
		lblPressEsc.setVisible(true);

		lblShortcutkey = new JLabel(
				" P -Progress Call Mark                           R - Call Ready Mark                           I - Image Details To Show                               C - Progress Call Cancel                             T-Transfer Call to Other");
		lblShortcutkey.setHorizontalAlignment(SwingConstants.LEFT);
		lblShortcutkey.setBounds(lblPressEsc.getX() + lblPressEsc.getWidth() + 20, lblPressEsc.getY(), 550, 20);
		lblShortcutkey.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		lblShortcutkey.setBackground(color2);
		lblShortcutkey.setVisible(true);

		lblProgressColourkey = new JLabel("");
		lblProgressColourkey.setHorizontalAlignment(SwingConstants.LEFT);
		lblProgressColourkey.setBounds(750, lblShortcutkey.getY(), 10, 10);
		lblProgressColourkey.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		// lblProgressColourkey.setBorder(BorderFactory.createEtchedBorder(color4,
		// color4));
		lblProgressColourkey.setBackground(Color.decode("#CEAB8C"));
		lblProgressColourkey.setOpaque(true);
		lblProgressColourkey.setForeground(Color.decode("#CEAB8C"));
		lblProgressColourkey.setVisible(true);

		lblProgressColourkeyNote = new JLabel("Progress Call");
		lblProgressColourkeyNote.setHorizontalAlignment(SwingConstants.LEFT);
		lblProgressColourkeyNote.setBounds(lblProgressColourkey.getX() + lblProgressColourkey.getWidth() + 10,
				lblShortcutkey.getY() - 5, 80, 20);
		lblProgressColourkeyNote.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		// lblProgressColourkeyNote.setBorder(BorderFactory.createEtchedBorder(color4,
		// color4));
		lblProgressColourkeyNote.setBackground(color2);
		lblProgressColourkeyNote.setVisible(true);

		lblReturnColourkey = new JLabel("");
		lblReturnColourkey.setHorizontalAlignment(SwingConstants.LEFT);
		lblReturnColourkey.setBounds(lblProgressColourkeyNote.getX() + lblProgressColourkeyNote.getWidth() + 10,
				lblProgressColourkeyNote.getY() + 5, 10, 10);
		lblReturnColourkey.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		// lblReturnColourkey.setBorder(BorderFactory.createEtchedBorder(color4,
		// color4));
		lblReturnColourkey.setBackground(Color.decode("#57b9ff"));
		lblReturnColourkey.setOpaque(true);
		lblReturnColourkey.setForeground(Color.decode("#57b9ff"));
		lblReturnColourkey.setVisible(true);

		lblReturnColourkeyNote = new JLabel("Return Call");
		lblReturnColourkeyNote.setHorizontalAlignment(SwingConstants.LEFT);
		lblReturnColourkeyNote.setBounds(lblReturnColourkey.getX() + lblReturnColourkey.getWidth() + 10,
				lblReturnColourkey.getY() - 5, 80, 20);
		lblReturnColourkeyNote.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		// lblReturnColourkeyNote.setBorder(BorderFactory.createEtchedBorder(color4,
		// color4));
		lblReturnColourkeyNote.setBackground(color2);
		lblReturnColourkeyNote.setVisible(true);

		panelMain.add(panelContent);
		// panelContent.add(panelCallDetail);
		panelContent.add(scrDevCalls);
		panelContent.add(scrollReasonPane);
		panelContent.add(panelTakenCall);
		panelTakenCall.add(lblCallAndTicket);
		panelTakenCall.add(lblCallDetailTitle);
		panelTakenCall.add(lblCallDate);
		panelTakenCall.add(lblAssignDate);
		panelTakenCall.add(lblAssignTime);
		panelTakenCall.add(lblDueDate);
		panelTakenCall.add(lblCallTakenDate);
		panelTakenCall.add(lblRecBy);
		panelTakenCall.add(lblImage);
		panelTakenCall.add(lblCallAndTicketval);
		panelTakenCall.add(lblCallDateVal);
		panelTakenCall.add(lblAssignDateVal);
		panelTakenCall.add(lblAssignTimeVal);
		panelTakenCall.add(lblDueDateVal);
		panelTakenCall.add(lblCallTakenDateVal);
		panelTakenCall.add(lblRecByVal);
		panelTakenCall.add(lblImage);
		panelTakenCall.add(btnImage);
		panelMain.add(lblPressEsc);
		panelMain.add(lblShortcutkey);
		panelMain.add(lblProgressColourkey);
		panelMain.add(lblProgressColourkeyNote);
		panelMain.add(lblReturnColourkey);
		panelMain.add(lblReturnColourkeyNote);

		return panelContent;
	}

	private List<JilabaColumn> getDevCalls() {

		List<JilabaColumn> jilabaColumnlist = new ArrayList<>();
		jilabaColumnlist.add(new JilabaColumn("CallNo", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Date ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Priority ", String.class, 200, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Description", String.class, 200, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Dev Hrs ", String.class, 200, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Dev Due Date ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("AssignDate ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Nature ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Module Name ", String.class, 100, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Cust Name ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Taken Date", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Ticket No ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("Call Co-Ord ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn("ModuleId ", Integer.class, 150, JLabel.LEFT));

		return jilabaColumnlist;
	}

	private Component panelDetail2Initialize() {
		int lblWidth = 130;
		int lblHeight = 30;
		int txtWidth = 120;
		int txtHeight = 20;

		panelDetail2 = new JPanel();
		panelDetail2.setBounds(20, panelLine2.getY() + 10, 918, 100);
		panelDetail2.setLayout(null);
		panelDetail2.setBackground(color2);
		panelDetail2.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelDetail2.setVisible(true);

		lblAsOnDate = new JLabel("As On Date");
		lblAsOnDate.setBounds(10, 10, lblWidth, lblHeight);
		lblAsOnDate.setBackground(color2);
		lblAsOnDate.setFont(CustomFonts.fontCalibriBold);
		lblAsOnDate.setVisible(true);
		lblAsOnDate.addKeyListener(this);

		lblCallNo = new JLabel("CallNo\\TicketNo");
		lblCallNo.setBounds(lblAsOnDate.getX(), lblAsOnDate.getY() + lblAsOnDate.getHeight(), lblWidth, lblHeight);
		lblCallNo.setBackground(color2);
		lblCallNo.setFont(CustomFonts.fontCalibriBold);
		lblCallNo.setVisible(true);
		lblCallNo.addKeyListener(this);

		lblDeveloper = new JLabel("Developer");
		lblDeveloper.setBounds(lblCallNo.getX(), lblCallNo.getY() + lblCallNo.getHeight(), lblWidth, lblHeight);
		lblDeveloper.setBackground(color2);
		lblDeveloper.setFont(CustomFonts.fontCalibriBold);
		lblDeveloper.setVisible(true);
		lblDeveloper.addKeyListener(this);

		spnAsOnDate = new JilabaSpinner();
		spnAsOnDate.setBounds(lblAsOnDate.getX() + 100, lblAsOnDate.getY(), txtWidth, txtHeight);
		spnAsOnDate.setBackground(color2);
		spnAsOnDate.setFont(CustomFonts.fontCalibriBold);
		spnAsOnDate.setVisible(true);
		spnAsOnDate.addKeyListener(this);

		txtCallNo = new JilabaTextField();
		txtCallNo.setBounds(spnAsOnDate.getX(), lblCallNo.getY(), txtWidth, txtHeight);
		txtCallNo.setBackground(color2);
		txtCallNo.setFont(CustomFonts.fontCalibriBold);
		txtCallNo.setMaxLength(10);
		txtCallNo.setVisible(true);
		txtCallNo.addKeyListener(this);

		cmbDeveloper = new JilabaComboBox<Operator>();
		cmbDeveloper.setBounds(txtCallNo.getX(), lblDeveloper.getY(), txtWidth, txtHeight);
		cmbDeveloper.setBackground(color2);
		cmbDeveloper.setFont(CustomFonts.fontCalibriBold);
		cmbDeveloper.setVisible(true);
		cmbDeveloper.addKeyListener(this);

		lblDepartment = new JLabel("Department");
		lblDepartment.setBounds(spnAsOnDate.getX() + 130, spnAsOnDate.getY(), lblWidth, lblHeight);
		lblDepartment.setBackground(color2);
		lblDepartment.setFont(CustomFonts.fontCalibriBold);
		lblDepartment.setVisible(true);
		lblDepartment.addKeyListener(this);

		lblModule = new JLabel("Module");
		lblModule.setBounds(lblDepartment.getX(), txtCallNo.getY(), lblWidth, lblHeight);
		lblModule.setBackground(color2);
		lblModule.setFont(CustomFonts.fontCalibriBold);
		lblModule.setVisible(true);
		lblModule.addKeyListener(this);

		lblCustomer = new JLabel("Customer ");
		lblCustomer.setBounds(lblModule.getX(), cmbDeveloper.getY(), lblWidth, lblHeight);
		lblCustomer.setBackground(color2);
		lblCustomer.setFont(CustomFonts.fontCalibriBold);
		lblCustomer.setVisible(true);
		lblCustomer.addKeyListener(this);

		cmbDepartment = new JilabaComboBox<Department>();
		cmbDepartment.setBounds(lblDepartment.getX() + 90, lblDepartment.getY(), txtWidth, txtHeight);
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

		cmbCustomer = new JilabaComboBox<Customer>();
		cmbCustomer.setBounds(cmbModule.getX(), lblCustomer.getY(), txtWidth, txtHeight);
		cmbCustomer.setBackground(color2);
		cmbCustomer.setFont(CustomFonts.fontCalibriBold);
		cmbCustomer.setVisible(true);
		cmbCustomer.addKeyListener(this);

		lblType = new JLabel("Type");
		lblType.setBounds(cmbDepartment.getX() + 130, cmbDepartment.getY(), lblWidth, lblHeight);
		lblType.setBackground(color2);
		lblType.setFont(CustomFonts.fontCalibriBold);
		lblType.setVisible(true);
		lblType.addKeyListener(this);

		lblDeptAuthorize = new JLabel("Dept Authorize");
		lblDeptAuthorize.setBounds(lblType.getX(), cmbModule.getY(), lblWidth, lblHeight);
		lblDeptAuthorize.setBackground(color2);
		lblDeptAuthorize.setFont(CustomFonts.fontCalibriBold);
		lblDeptAuthorize.setVisible(true);
		lblDeptAuthorize.addKeyListener(this);

		lblRecvFrom = new JLabel("Received From");
		lblRecvFrom.setBounds(lblDeptAuthorize.getX(), cmbCustomer.getY(), lblWidth, lblHeight);
		lblRecvFrom.setBackground(color2);
		lblRecvFrom.setFont(CustomFonts.fontCalibriBold);
		lblRecvFrom.setVisible(true);
		lblRecvFrom.addKeyListener(this);

		cmbType = new JilabaComboBox<String>();
		cmbType.setBounds(lblType.getX() + 100, lblType.getY(), txtWidth, txtHeight);
		cmbType.setBackground(color2);
		cmbType.setFont(CustomFonts.fontCalibriBold);
		cmbType.setVisible(true);
		cmbType.addKeyListener(this);

		cmbDeptAuthorize = new JilabaComboBox<Operator>();
		cmbDeptAuthorize.setBounds(cmbType.getX(), lblDeptAuthorize.getY(), txtWidth, txtHeight);
		cmbDeptAuthorize.setBackground(color2);
		cmbDeptAuthorize.setFont(CustomFonts.fontCalibriBold);
		cmbDeptAuthorize.setVisible(true);
		cmbDeptAuthorize.addKeyListener(this);

		cmbRecvFrom = new JilabaComboBox<Operator>();
		cmbRecvFrom.setBounds(cmbDeptAuthorize.getX(), lblRecvFrom.getY(), txtWidth, txtHeight);
		cmbRecvFrom.setBackground(color2);
		cmbRecvFrom.setFont(CustomFonts.fontCalibriBold);
		cmbRecvFrom.setVisible(true);
		cmbRecvFrom.addKeyListener(this);

		lblCallCoOrd = new JLabel("Call Co-Ord");
		lblCallCoOrd.setBounds(cmbType.getX() + 130, cmbType.getY(), lblWidth, lblHeight);
		lblCallCoOrd.setBackground(color2);
		lblCallCoOrd.setFont(CustomFonts.fontCalibriBold);
		lblCallCoOrd.setVisible(true);
		lblCallCoOrd.addKeyListener(this);

		lblCallNature = new JLabel("Call Nature");
		lblCallNature.setBounds(lblCallCoOrd.getX(), cmbDeptAuthorize.getY(), lblWidth, lblHeight);
		lblCallNature.setBackground(color2);
		lblCallNature.setFont(CustomFonts.fontCalibriBold);
		lblCallNature.setVisible(true);
		lblCallNature.addKeyListener(this);

		cmbCallCoOrd = new JilabaComboBox<Operator>();
		cmbCallCoOrd.setBounds(lblCallCoOrd.getX() + 100, lblCallCoOrd.getY(), txtWidth, txtHeight);
		cmbCallCoOrd.setBackground(color2);
		cmbCallCoOrd.setFont(CustomFonts.fontCalibriBold);
		cmbCallCoOrd.setVisible(true);
		cmbCallCoOrd.addKeyListener(this);

		cmbCallNature = new JilabaComboBox<String>();
		cmbCallNature.setBounds(cmbCallCoOrd.getX(), lblCallNature.getY(), txtWidth, txtHeight);
		cmbCallNature.setBackground(color2);
		cmbCallNature.setFont(CustomFonts.fontCalibriBold);
		cmbCallNature.setVisible(true);
		cmbCallNature.addKeyListener(this);

		btnView = new JButton("View");
		btnView.setBounds(cmbRecvFrom.getX() + 170, cmbRecvFrom.getY(), 50, txtHeight);
		btnView.setBackground(color3);
		btnView.setForeground(color2);
		btnView.setMnemonic(KeyEvent.VK_V);
		btnView.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		btnView.setVisible(true);
		btnView.addActionListener(this);
		btnView.addKeyListener(this);

		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(btnView.getX() + 80, btnView.getY(), 50, txtHeight);
		btnUpdate.setBackground(color3);
		btnUpdate.setForeground(color2);
		btnUpdate.setMnemonic(KeyEvent.VK_U);
		btnUpdate.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		btnUpdate.setVisible(true);
		btnUpdate.addActionListener(this);
		btnUpdate.addKeyListener(this);

		panelDetail2.add(lblAsOnDate);
		panelDetail2.add(lblCallNo);
		panelDetail2.add(lblDeveloper);
		panelDetail2.add(spnAsOnDate);
		panelDetail2.add(txtCallNo);
		panelDetail2.add(cmbDeveloper);
		panelDetail2.add(lblCallCoOrd);
		panelDetail2.add(lblDepartment);
		panelDetail2.add(lblCustomer);
		panelDetail2.add(lblDeptAuthorize);
		panelDetail2.add(cmbDepartment);
		panelDetail2.add(cmbDeptAuthorize);
		panelDetail2.add(lblRecvFrom);
		panelDetail2.add(lblModule);
		panelDetail2.add(lblCallNature);
		panelDetail2.add(cmbRecvFrom);
		panelDetail2.add(cmbModule);
		panelDetail2.add(cmbCallNature);
		panelDetail2.add(cmbCustomer);
		panelDetail2.add(cmbType);
		panelDetail2.add(cmbCallCoOrd);
		panelDetail2.add(lblType);
		panelDetail2.add(btnView);
		panelDetail2.add(btnUpdate);

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

		lblHeading = new JLabel("DEVELOPER CALLS");
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

			if (e.getSource() == spnAsOnDate) {
				txtCallNo.requestFocus();
			} else if (e.getSource() == txtCallNo) {
				String callNoStr = txtCallNo.getText().trim();

				if (!callNoStr.equals("")) {
					strCallNo = Long.valueOf(callNoStr);
					// strCallNo = Integer.valueOf(String.valueOf(txtCallNo.getText().trim()));
					cmbDeveloper.requestFocus();
				}

			} else if (e.getSource() == cmbDeveloper) {
				cmbDepartment.requestFocus();
			} else if (e.getSource() == cmbDepartment) {

				lstModule = logicDevCalls
						.getModule(Integer.valueOf(String.valueOf(cmbDepartment.getSelectedItemValue())));

				cmbModule.removeAllItems();
				cmbModule.addListItem(new ListItem("All"));

				for (com.jilaba.calls.model.Module module : lstModule) {
					cmbModule.addListItem(new ListItem(module.getModuleName(), module.getModuleId()));
				}

				cmbModule.requestFocus();
			} else if (e.getSource() == cmbModule) {
				cmbCustomer.requestFocus();
			} else if (e.getSource() == cmbCustomer) {
				cmbType.requestFocus();
			} else if (e.getSource() == cmbType) {
				cmbDeptAuthorize.requestFocus();
			} else if (e.getSource() == cmbDeptAuthorize) {
				cmbRecvFrom.requestFocus();
			} else if (e.getSource() == cmbRecvFrom) {
				cmbCallCoOrd.requestFocus();
			} else if (e.getSource() == cmbCallCoOrd) {
				cmbCallNature.requestFocus();
			} else if (e.getSource() == cmbCallNature) {
				btnView.requestFocus();
			} else if (e.getSource() == btnView) {

			}

		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

			setVisible(false);

			FrmMainMenu frmMainMenu = Applicationmain.getAbstractApplicationContext().getBean(FrmMainMenu.class);
			frmMainMenu.setVisible(true);

		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {

			if (e.getSource() == btnView) {

				tblDevCalls.requestFocus();
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

		}

		tblDevCalls.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (tblDevCalls.getRowCount() <= 0 || tblDevCalls.getSelectedRow() < 0 || event.getValueIsAdjusting())
					return;
				showProductDetails(lstDevCalls.get(tblDevCalls.getSelectedRow()));
			}

			private void showProductDetails(Calls calls) {

				lblCallAndTicketval.setText(String.valueOf(calls.getCallno()));
				lblCallDateVal.setText(calls.getCdate());
				lblAssignDateVal.setText(calls.getAssignDate());
				lblDueDateVal.setText(calls.getCallnature());
				if (calls.getCallTakenDate() == null) {
					lblCallTakenDateVal.setText(" Not Taken ");
				} else {
					lblCallTakenDateVal.setText(String.valueOf(calls.getCallTakenDate()));
				}

				if (calls.getDevDuetime() >= 1) {
					double devTime = calls.getDevDuetime();
					lblAssignTimeVal.setText(devTime + " hrs");
				} else {
					double devTime = calls.getDevDuetime() * 60;
					DecimalFormat df = new DecimalFormat("0");

					lblAssignTimeVal.setText(df.format(devTime) + " mins");
				}

				lblRecByVal.setText(String.valueOf(calls.getReceiverName()));

				if (calls.getImgAttach() == null) {

					btnImage.setText("Not Attached");
					btnImage.setForeground(Color.BLACK);

				} else {
					btnImage.setText("Attached");
					btnImage.setForeground(color8);
				}

				txtCallDesc.setText(
						" Option -> " + calls.getMoption() + "\r\n " + "Call -> " + calls.getDescription().trim());

			}
		});

		if (e.getSource() == btnImage) {

			btnImageClick();
		}

	}

	private void btnView() {

		chkSelectAll(); // Nature And type
		tblDevCalls.clear();

		lstDevCalls = logicDevCalls.getDeveloperCalls(spnAsOnDate.getDateValue(), strCallNo, strDeveleoper,
				strDepartment, strModule, strCustomer, strType, strDeptAuthorize, strRecvFrom, strCallCoOrd,
				strCallNature);

		lblTotalcallVal.setText(String.valueOf(lstDevCalls.size()));

		List<Object> lstObjects = null;

		int i = -1;
		int j = 0;
		int k = 0;

		for (Calls calls : lstDevCalls) {

			lstObjects = new ArrayList<Object>();

			lstObjects.add(calls.getCallno());
			lstObjects.add(calls.getCdate());
			lstObjects.add(calls.getPriority());
			lstObjects.add(calls.getDescription());
			lstObjects.add(calls.getDevDuetime());
			lstObjects.add(calls.getDuedate());
			lstObjects.add(calls.getAssignDate());
			lstObjects.add(calls.getCallnature());
			lstObjects.add(calls.getModuleName());
			lstObjects.add(calls.getCustName());
			lstObjects.add(calls.getCallTakenDate());
			lstObjects.add(calls.getTicketno());
			lstObjects.add(calls.getCallcoordinator());
			lstObjects.add(calls.getProgress());
			lstObjects.add(calls.getModuleid());

			i = i + 1;

			if ((calls.getProgress() == 1) && (calls.getReady().isEmpty())) {
				System.out.println(calls.getCallno());
				System.out.println(calls.getReady());

				j = i;
			}

			if (calls.getTestresult().equalsIgnoreCase("R")) {
				k = i;
			}

			// for (Calls cc : lstDevCalls) {
			//
			// if (cc.getTestresult().equalsIgnoreCase("R")) {
			// k = i;
			// }
			//
			// }

			tblDevCalls.addRow(lstObjects);

		}

		strCallNo = 0;

		if (lstDevCalls.size() == 0) {
			JOptionPane.showMessageDialog(panelMain, "Call Details Not Found !...");
			btnView.requestFocus();
			return;
		}

		progress = logicDevCalls.validateProgressCall(strDeveleoper);

		List<ReturnCalls> returnCalls = new ArrayList<ReturnCalls>();

		returnCalls = logicDevCalls.getReturnCall(strDeveleoper);

		/*
		 * if (progress.size() != 0 && progress.get(0).getSugto() ==
		 * Integer.valueOf(String.valueOf(cmbDeveloper.getSelectedItemValue()))) {
		 */
		if ((progress.size() != 0 && strType != 2) && (progress.size() != 0 && strType != 3)) {

			if (j != -1) {
				tblDevCalls.setRowColor(j, Color.decode("#CEAB8C"));
			}
		}
		// Particular Developer Only - Gokul, 09/09/24
		/*
		 * if (returnCalls.size() != 0 && returnCalls.get(0).getSugto() == Integer
		 * .valueOf(String.valueOf(cmbDeveloper.getSelectedItemValue()))) {
		 */

		// 28/10/2024 For Return Value 0
		if ((returnCalls.size() != 0 && strType != 2) && (returnCalls.size() != 0 && strType != 3)) {

			if (k != -1 && k != 0) {
				tblDevCalls.setRowColor(k, Color.decode("#57b9ff"));
			}
		}

		// if (j != 0) {
		// calls = new Calls();
		//
		// // if (calls.getReady() == null) {
		// z
		// tblDevCalls.setRowColor(j, Color.decode("#CEAB8C"));
		// // }
		// }

	}

	private void btnImageClick() {

		CallsImages callsImages = logicDevCalls.getImages(String.valueOf(lblCallAndTicketval.getText()));

		blnFrmDevCall = true;
		FrmImageDialog frmImageDialog = Applicationmain.getAbstractApplicationContext().getBean(FrmImageDialog.class,
				new Object[] { getContentPane() });
		frmImageDialog.booleanAssign(false, blnFrmDevCall, (String.valueOf(lblCallAndTicketval.getText())),
				callsImages);
		frmImageDialog.setVisible(true);

	}

	private ReturnStatus chkSelectAll() {

		// // Apply the method to each combo box
		// strDeveleoper = getComboBoxValue(cmbDeveloper);
		// strDepartment = getComboBoxValue(cmbDepartment);
		// strModule = getComboBoxValue(cmbModule);
		// strCustomer = getComboBoxValue(cmbCustomer);
		// strDeptAuthorize = getComboBoxValue(cmbDeptAuthorize);
		// strRecvFrom = getComboBoxValue(cmbRecvFrom);
		// strCallCoOrd = getComboBoxValue(cmbCallCoOrd);
		// strType = getComboBoxValue(cmbType);
		// strCallNature = getComboBoxValue(cmbCallNature);
		//
		// return new ReturnStatus(true);

		if (cmbDeveloper.getSelectedItem().equals("All")) {
			strDeveleoper = 0;
		} else {
			strDeveleoper = Integer.valueOf(String.valueOf(cmbDeveloper.getSelectedItemValue()));
		}
		if (cmbDepartment.getSelectedItem().equals("All")) {
			strDepartment = 0;
		} else {
			strDepartment = Integer.valueOf(String.valueOf(cmbDepartment.getSelectedItemValue()));
		}
		if (cmbModule.getSelectedItem().equals("All")) {
			strModule = 0;
		} else {
			strModule = Integer.valueOf(String.valueOf(cmbModule.getSelectedItemValue()));
		}
		if (cmbCustomer.getSelectedItem().equals("All")) {
			strCustomer = 0;
		} else {
			strCustomer = Integer.valueOf(String.valueOf(cmbCustomer.getSelectedItemValue()));
		}
		if (cmbDeptAuthorize.getSelectedItem().equals("All")) {
			strDeptAuthorize = 0;
		} else {
			strDeptAuthorize = Integer.valueOf(String.valueOf(cmbDeptAuthorize.getSelectedItemValue()));
		}
		if (cmbRecvFrom.getSelectedItem().equals("All")) {
			strRecvFrom = 0;
		} else {
			strRecvFrom = Integer.valueOf(String.valueOf(cmbRecvFrom.getSelectedItemValue()));
		}
		if (cmbCallCoOrd.getSelectedItem().equals("All")) {
			strCallCoOrd = 0;
		} else {
			strCallCoOrd = Integer.valueOf(String.valueOf(cmbCallCoOrd.getSelectedItemValue()));
		}

		if (cmbType.getSelectedItem().equals("All")) {
			strType = 0;
		} else {
			strType = Integer.valueOf(String.valueOf(cmbType.getSelectedItemValue()));
		}
		if (cmbCallNature.getSelectedItem().equals("All")) {
			strCallNature = 0;
		} else {
			strCallNature = Integer.valueOf(String.valueOf(cmbCallNature.getSelectedItemValue()));
		}

		return new ReturnStatus(true);

	}

}
