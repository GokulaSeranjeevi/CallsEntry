package com.jilaba.calls.forms;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.common.ImageResource;
import com.jilaba.calls.common.LoginCredential;
import com.jilaba.calls.common.TimerJob;
import com.jilaba.calls.daoimpl.MainMenuDaoImpl;
import com.jilaba.calls.logic.LogicDailyActvity;
import com.jilaba.calls.model.Calls;
import com.jilaba.calls.model.DailyActivity;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.model.ReadyCalls;
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
import com.jilaba.security.Validation;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

@org.springframework.stereotype.Component
@Scope("prototype")
public class FrmDailyActivity extends JFrame implements ActionListener, KeyListener {

	private final MainMenuDaoImpl mainMenuDaoImpl;

	private JPanel panelMain;
	private JPanel panelEntry;
	private JPanel panelAtnView;
	private JPanel panelTitle;
	private JPanel panelLine;
	private JPanel panelDetail;
	private JPanel panelLine2;
	private JPanel panelContent;
	private JPanel panelLine3;
	private JPanel panelAttendance;
	private JPanel panelAtnReport;
	private JPanel panelReportView;
	private JPanel panelIndReport;
	private JPanel panelIndRepView;
	private ChartPanel panelCallsChart;
	private ChartPanel panelCallsBar;
	private JPanel panelMonthAtn;

	private JLabel lblDevelopedby;
	private JLabel lblVersion;
	private JLabel lblServerIpValue;
	private JLabel lblDateTime;
	private JLabel lblOperatorLabel;
	private JLabel lblCallMnuHead;
	private JLabel lblMinimize;
	private JLabel lblStaffName;
	private JLabel lblJobrole;
	private JLabel lblJoinDate;

	private JButton btnAttendance;
	private JButton btnIndividualReport;
	private JButton btnAtnReport;

	private JLabel lblAtnDate;
	private JilabaSpinner spnAtnDate;
	private JLabel lblRepDate;
	private JLabel lblYearHead;
	private JLabel lblIndStaff;
	private JilabaSpinner spnRepDate;
	private JilabaTable tblAttendance;
	private JilabaTable tblReport;
	private JilabaTable tblYearReport;
	private JScrollPane scrAtn;
	private JScrollPane scrRep;
	private JScrollPane scrYearRep;
	private JButton btnAtnMark;
	private JButton btnExit;
	private JButton btnRepView;
	private JButton btnRepExport;
	private JButton btnRepExit;
	private JButton btnIndView;
	private JCheckBox chkAtmMark;
	private JilabaComboBox<Operator> cmbApprovedby;
	private JilabaComboBox<Operator> cmbIndRepStaff;
	private JilabaTextField txtAtn;

	private List<Operator> lstoperator = new ArrayList<Operator>();
	private List<DailyActivity> lstDailyActivity;

	private Color color1 = Color.decode("#F1C232");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#f9e6ac");
	private Color color4 = Color.decode("#000000");
	private Color color5 = Color.decode("#e4dedf");
	private Color color6 = Color.decode("#b43e69");
	private Color color7 = Color.decode("#ACDDDE");

	private Color fontColor1 = Color.decode("#17202A");
	private Color fontColor2 = Color.decode("#3B3B3B");

	private JilabaFonts jilabaFonts = new JilabaFonts();

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");

	private ControlResize controlResize;
	private List<Map<String, Object>> staffList = new ArrayList<>();

	@Autowired
	private LogicDailyActvity logicDailyActvity;

	public FrmDailyActivity(MainMenuDaoImpl mainMenuDaoImpl) {

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
		this.mainMenuDaoImpl = mainMenuDaoImpl;

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

		lstoperator = logicDailyActvity.getOperators();

		for (Operator oper : lstoperator) {

			cmbApprovedby.addListItem(new ListItem(oper.getStaffname(), oper.getStaffid()));
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

		spnRepDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					btnRepView.requestFocus();
				}

			}
		});

		panelIndReport.setFocusable(true);
		panelIndReport.requestFocusInWindow();

		panelIndReport.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"escapePressed");

		panelIndReport.getActionMap().put("escapePressed", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				panelIndReport.setVisible(false);
				panelEntry.setVisible(true);
				panelContent.setFocusable(true);
				panelContent.requestFocusInWindow();
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

		panelAtnView = new JPanel();
		panelAtnView.setBounds(0, 90, 958, 590);
		panelAtnView.setLayout(null);
		panelAtnView.setBackground(color2);
		panelAtnView.setVisible(false);

		panelAtnReport = new JPanel();
		panelAtnReport.setBounds(0, 90, 958, 590);
		panelAtnReport.setLayout(null);
		panelAtnReport.setBackground(color2);
		panelAtnReport.setVisible(false);

		panelIndReport = new JPanel();
		panelIndReport.setBounds(0, 90, 958, 590);
		panelIndReport.setLayout(null);
		panelIndReport.setBackground(color2);
		panelIndReport.setVisible(false);

		panelMain.add(panelTitleInialize());
		panelMain.add(panelLineInialize());
		panelMain.add(panelDetailInitialize());
		panelMain.add(panelLine2Initialize());
		panelEntry.add(panelContentInitalize());
		panelMain.add(panelLine3Inialize());
//		panelMain.add(panelButtonInialize());
		panelAtnView.add(panelViewDetail());
		panelAtnReport.add(panelAtnReport());
		panelIndReport.add(panelIndReport());

//		createInputVerifiers();
//		createActionListners();

		getContentPane().add(panelMain);
		panelMain.add(panelEntry);
		panelMain.add(panelAtnView);
		panelMain.add(panelAtnReport);
		panelMain.add(panelIndReport);

	}

	private Component panelIndReport() {

		panelIndRepView = new JPanel();
		panelIndRepView.setBounds(30, 30, 900, 530);
		panelIndRepView.setLayout(null);
		panelIndRepView.setBackground(color2);
		panelIndRepView.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelIndRepView.addKeyListener(this);
		panelIndRepView.setVisible(true);

		lblIndStaff = new JLabel("Staff Name");
		lblIndStaff.setBounds(20, 10, 130, 50);
		lblIndStaff.setBackground(color2);
		lblIndStaff.setVisible(true);
		lblIndStaff.setFont(CustomFonts.font20);
		lblIndStaff.setForeground(Color.BLACK);
		lblIndStaff.setBackground(color2);

		cmbIndRepStaff = new JilabaComboBox<Operator>();
		cmbIndRepStaff.setBounds(lblIndStaff.getX() + 80, lblIndStaff.getY() + 15, 130, 20);
		cmbIndRepStaff.setBackground(color2);
		cmbIndRepStaff.setFont(CustomFonts.fontCalibriPlain15);
		cmbIndRepStaff.setVisible(true);
		cmbIndRepStaff.addKeyListener(this);

		btnIndView = new JButton("View ");
		btnIndView.setHorizontalAlignment(SwingConstants.CENTER);
		btnIndView.setBounds(cmbIndRepStaff.getX() + 150, cmbIndRepStaff.getY(), 60, 20);
		btnIndView.setFont(jilabaFonts.getFont(FontStyle.BOLD, 17));
		btnIndView.setMnemonic(KeyEvent.VK_X);
		btnIndView.setBackground(Color.decode("#f08080"));
		btnIndView.setForeground(Color.BLACK);
		btnIndView.setVisible(true);
		btnIndView.addActionListener(this);
		btnIndView.setVerifyInputWhenFocusTarget(false);
		btnIndView.setCursor(new Cursor(Cursor.HAND_CURSOR));
		CommonMethods.setIcon(ImageResource.ATTENDANCEEXIT, btnExit);
		btnIndView.addActionListener(this);
		btnIndView.setIconTextGap(10);
		btnIndView.addKeyListener(this);

		panelIndRepView.add(lblIndStaff);
		panelIndRepView.add(cmbIndRepStaff);
		panelIndRepView.add(btnIndView);

		panelMain.add(panelIndRepView);

		return panelIndRepView;
	}

	private Component panelAtnReport() {

		panelReportView = new JPanel();
		panelReportView.setBounds(30, 30, 900, 530);
		panelReportView.setLayout(null);
		panelReportView.setBackground(color2);
		panelReportView.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelReportView.addKeyListener(this);

		lblRepDate = new JLabel("Attendance Date");
		lblRepDate.setBounds(20, 10, 130, 50);
		lblRepDate.setBackground(color2);
		lblRepDate.setVisible(true);
		lblRepDate.setFont(CustomFonts.font20);
		lblRepDate.setForeground(Color.BLACK);
		lblRepDate.setBackground(color2);

		spnRepDate = new JilabaSpinner();
		spnRepDate.setBounds(lblRepDate.getX() + 100, lblRepDate.getY() + 15, 70, 20);
		spnRepDate.setFont(CustomFonts.fontCalibriBold16);
		spnRepDate.setForeground(Color.BLACK);
		spnRepDate.setVisible(true);

		btnRepView = new JButton("View");
		btnRepView.setHorizontalAlignment(SwingConstants.CENTER);
		btnRepView.setBounds(spnRepDate.getX() + 150, spnRepDate.getY(), 60, 20);
		btnRepView.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnRepView.setMnemonic(KeyEvent.VK_V);
		btnRepView.setBackground(color3);
		btnRepView.setForeground(Color.BLACK);
		btnRepView.setVisible(true);
		btnRepView.addActionListener(this);
		btnRepView.setVerifyInputWhenFocusTarget(false);
		btnRepView.setCursor(new Cursor(Cursor.HAND_CURSOR));
//		CommonMethods.setIcon(ImageResource.MARK, btnAtnMark);
		btnRepView.setIconTextGap(10);
		btnRepView.addKeyListener(this);

		tblReport = new JilabaTable(getAtnReport());
		tblReport.setAutoResizeMode(JilabaTable.AUTO_RESIZE_OFF);
		tblReport.getTableHeader().setReorderingAllowed(false);
		tblReport.getTableHeader().setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		tblReport.setFont(CustomFonts.fontCalibriPlain15);
		tblReport.setForeground(Color.BLACK);
		tblReport.getTableHeader().setForeground(color6);
		tblReport.getTableHeader().setBackground(Color.WHITE);
		tblReport.setRowHeight(22);
		tblReport.setVisible(true);
		tblReport.addKeyListener(this);

		TableColumnModel columnModel = tblReport.getColumnModel();
		int lastColumnIndex = columnModel.getColumnCount() - 1;
		TableColumn lastColumn = columnModel.getColumn(lastColumnIndex);

		// Set width to 0 to hide
		lastColumn.setMinWidth(0);
		lastColumn.setMaxWidth(0);
		lastColumn.setPreferredWidth(0);
		lastColumn.setResizable(false);

		scrRep = new JScrollPane(tblReport);
		scrRep.setBounds(lblRepDate.getX(), lblRepDate.getY() + lblRepDate.getHeight() + 10, 850, 400);
		scrRep.getViewport().setBackground(tblReport.getTableHeader().getBackground());
		scrRep.setVisible(true);

		/*
		 * chkAtmMark = new JCheckBox("Yes"); chkAtmMark.setVisible(true);
		 * chkAtmMark.setBackground(color2);
		 * chkAtmMark.setHorizontalAlignment(SwingConstants.CENTER);
		 * chkAtmMark.setSelected(true); chkAtmMark.setFont(CustomFonts.FONT16);
		 * chkAtmMark.setForeground(Color.BLACK);
		 * 
		 * cmbApprovedby = new JilabaComboBox<>(); cmbApprovedby.setVisible(true);
		 * cmbApprovedby.setBackground(color2);
		 * cmbApprovedby.setFont(CustomFonts.FONT16);
		 * cmbApprovedby.setForeground(Color.BLACK);
		 * 
		 * txtAtn = new JilabaTextField(); txtAtn.setVisible(true);
		 * txtAtn.setBackground(color2); txtAtn.setFont(CustomFonts.FONT16);
		 * txtAtn.setForeground(Color.BLACK);
		 */

		btnRepExport = new JButton("Export");
		btnRepExport.setHorizontalAlignment(SwingConstants.CENTER);
		btnRepExport.setBounds(scrRep.getX() + 330, scrRep.getY() + scrRep.getHeight() + 18, 60, 20);
		btnRepExport.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
		btnRepExport.setMnemonic(KeyEvent.VK_E);
		btnRepExport.setBackground(Color.decode("#90EE90"));
		btnRepExport.setForeground(Color.BLACK);
		btnRepExport.setVisible(true);
		btnRepExport.addActionListener(this);
		btnRepExport.setVerifyInputWhenFocusTarget(false);
		btnRepExport.setCursor(new Cursor(Cursor.HAND_CURSOR));
		CommonMethods.setIcon(ImageResource.MARK, btnAtnMark);
		btnRepExport.setIconTextGap(10);
		btnRepExport.addKeyListener(this);

		btnRepExit = new JButton("Exit ");
		btnRepExit.setHorizontalAlignment(SwingConstants.CENTER);
		btnRepExit.setBounds(btnAtnMark.getX() + 120, btnAtnMark.getY(), 60, 20);
		btnRepExit.setFont(jilabaFonts.getFont(FontStyle.BOLD, 17));
		btnRepExit.setMnemonic(KeyEvent.VK_X);
		btnRepExit.setBackground(Color.decode("#f08080"));
		btnRepExit.setForeground(Color.BLACK);
		btnRepExit.setVisible(true);
		btnRepExit.addActionListener(this);
		btnRepExit.setVerifyInputWhenFocusTarget(false);
		btnRepExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		CommonMethods.setIcon(ImageResource.ATTENDANCEEXIT, btnExit);
		btnRepExit.addActionListener(this);
		btnRepExit.setIconTextGap(10);
		btnRepExit.addKeyListener(this);

		panelReportView.add(scrRep);
		panelReportView.add(lblRepDate);
		panelReportView.add(spnRepDate);
		panelReportView.add(btnRepExport);
		panelReportView.add(btnRepView);
		panelReportView.add(btnRepExit);
		panelMain.add(panelReportView);

		return panelReportView;

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

		TableColumnModel columnModel = tblAttendance.getColumnModel();
		int lastColumnIndex = columnModel.getColumnCount() - 1;
		TableColumn lastColumn = columnModel.getColumn(lastColumnIndex);

		// Set width to 0 to hide
		lastColumn.setMinWidth(0);
		lastColumn.setMaxWidth(0);
		lastColumn.setPreferredWidth(0);
		lastColumn.setResizable(false);

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
		CommonMethods.setIcon(ImageResource.MARK, btnAtnMark);
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
		CommonMethods.setIcon(ImageResource.ATTENDANCEEXIT, btnExit);
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
		jilabaColumnlist.add(new JilabaColumn(" HalfDay ", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" WeekOff ", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" ComboOff ", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" Approvedby ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn(" Reason ", String.class, 300, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn(" PermissionTime ", String.class, 200, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" StaffId ", String.class, 200, JLabel.CENTER));

		return jilabaColumnlist;

	}

	private List<JilabaColumn> getAtnReport() {

		List<JilabaColumn> jilabaColumnlist = new ArrayList<>();
		jilabaColumnlist.add(new JilabaColumn(" StaffName ", String.class, 250, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn(" Leave", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" Permission ", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" HalfDay ", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" WeekOff ", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" ComboOff ", String.class, 150, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" Approvedby ", String.class, 150, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn(" Reason ", String.class, 300, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn(" PermissionTime ", String.class, 200, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" StaffId ", String.class, 200, JLabel.CENTER));

		return jilabaColumnlist;

	}

	private List<JilabaColumn> getYearlyReport() {

		List<JilabaColumn> jilabaColumnlist = new ArrayList<>();
		jilabaColumnlist.add(new JilabaColumn(" Month ", String.class, 200, JLabel.LEFT));
		jilabaColumnlist.add(new JilabaColumn(" NoofLeave", String.class, 120, JLabel.CENTER));
		jilabaColumnlist.add(new JilabaColumn(" NoofPermission ", String.class, 127, JLabel.CENTER));

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
		CommonMethods.setIcon(ImageResource.ATTENDANCE, btnAttendance);
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
		CommonMethods.setIcon(ImageResource.INDIVIDUALREPORT, btnIndividualReport);
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
		CommonMethods.setIcon(ImageResource.ATTENDANCEREPORT, btnAtnReport);
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
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnAttendance) {

			btnAtnView();

		} else if (e.getSource() == btnExit) {

			panelAtnView.setVisible(false);
			panelEntry.setVisible(true);
			panelContent.setFocusable(true);
			panelContent.requestFocusInWindow();
			loadInitialize();

		} else if (e.getSource() == btnRepExit) {
			tblReport.clear();
			spnRepDate.setValue(new Date());
			panelAtnReport.setVisible(false);
			panelEntry.setVisible(true);
			panelContent.setFocusable(true);
			panelContent.requestFocusInWindow();
			loadInitialize();

		} else if (e.getSource() == btnAtnMark) {

			List<DailyActivity> dailyActivities = new ArrayList<>();

			for (int row = 0; row < tblAttendance.getRowCount(); row++) {
				DailyActivity dailyActivity = new DailyActivity();

				dailyActivity.setLeave(String.valueOf(tblAttendance.getValueAt(row, 1).equals("No") ? "N" : "Y"));
				dailyActivity.setPermission(String.valueOf(tblAttendance.getValueAt(row, 2).equals("No") ? "N" : "Y"));
				dailyActivity.setHalfDay(String.valueOf(tblAttendance.getValueAt(row, 3).equals("No") ? "N" : "Y"));
				dailyActivity.setWeekOff(String.valueOf(tblAttendance.getValueAt(row, 4).equals("No") ? "N" : "Y"));
				dailyActivity.setComboOff(String.valueOf(tblAttendance.getValueAt(row, 5).equals("No") ? "N" : "Y"));
				dailyActivity.setApprovedby(Integer.valueOf(String
						.valueOf(tblAttendance.getValueAt(row, 6) == "" ? "0" : cmbApprovedby.getSelectedItemValue())));
				dailyActivity.setReason(String.valueOf(tblAttendance.getValueAt(row, 7)));
				dailyActivity.setPermissionTime(String.valueOf(tblAttendance.getValueAt(row, 8)));
				dailyActivity.setStaffId(Integer.parseInt(String.valueOf(tblAttendance.getValueAt(row, 9))));

				dailyActivities.add(dailyActivity);
			}

			try {

				logicDailyActvity.saveDailyActivity(dailyActivities);
				JOptionPane.showMessageDialog(panelMain, "Attendance Marked ...!");

				panelAtnView.setVisible(false);
				panelEntry.setVisible(true);
				panelContent.setFocusable(true);
				panelContent.requestFocusInWindow();
				loadInitialize();

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == btnAtnReport) {

			btnAtnReport();
		} else if (e.getSource() == btnRepView) {

			tblReport.clear();

			lstDailyActivity = logicDailyActvity.getAttendance(spnRepDate.getDateValue());

			if (lstDailyActivity.isEmpty()) {
				JOptionPane.showMessageDialog(panelMain, "No Information to View ...!");
				return;
			} else {

				List<Object> lstObjects = null;

				for (DailyActivity dailyActivity : lstDailyActivity) {

					lstObjects = new ArrayList<Object>();

					lstObjects.add(dailyActivity.getStaffName());
					lstObjects.add(dailyActivity.getLeave());
					lstObjects.add(dailyActivity.getPermission());
					lstObjects.add(dailyActivity.getHalfDay());
					lstObjects.add(dailyActivity.getWeekOff());
					lstObjects.add(dailyActivity.getComboOff());
					lstObjects.add(dailyActivity.getApprovedName());
					lstObjects.add(dailyActivity.getReason());
					lstObjects.add(dailyActivity.getPermissionTime());

					tblReport.addRow(lstObjects);

				}
			}
		} else if (e.getSource() == btnRepExport) {
			try {

				String url = CommonMethods.getUrl(Applicationmain.tranDbName);
				String user = CommonMethods.strLogin;
				String password = Validation.decrypt(CommonMethods.strPassword);
				Connection con = DriverManager.getConnection(url, user, password);

				String query = "Select S.Staffname, (Case When D.Leave='N' Then 'No' Else 'Yes' End )Leave , (Case When D.Permission='N' Then 'No' Else 'Yes' End )Permission, \r\n"
						+ "						 (Case When D.HalfDay='N' Then 'No' Else 'Yes' End )HalfDay,\r\n"
						+ "						 (Case When D.WeekOff='N' Then 'No' Else 'Yes' End )WeekOff ,\r\n"
						+ "						(Case When D.ComboOff='N' Then 'No' Else 'Yes' End )ComboOff ,\r\n"
						+ "						PermissionTime from DailyActivity D \r\n"
						+ "						Left Join staff S On S.staffid = D.StaffId \r\n"
						+ "						Left Join staff S1 On S1.staffid = D.Approvedby "
						+ "Where GroupId In(\r\n"
						+ "						Select Max(GroupId)GroupId from DailyActivity  A Where Createddate='"
						+ (spnRepDate.getDateValue()) + "')";

				Statement stmt = con.createStatement();

				ResultSet rs = stmt.executeQuery(query);

				exportToPDF(rs, "LalithaaTeam -" + spnRepDate.getDateValue() + ".pdf");

				JOptionPane.showMessageDialog(null, "Data exported successfully.");

				rs.close();
				stmt.close();
				con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
			}
		} else if (e.getSource() == btnIndividualReport) {

			panelEntry.setVisible(false);
			panelIndReport.setVisible(true);

			cmbIndRepStaff.removeAllItems();

			lstoperator = logicDailyActvity.getOperators();

			for (Operator oper : lstoperator) {

				cmbIndRepStaff.addListItem(new ListItem(oper.getStaffname(), oper.getStaffid()));
			}

		} else if (e.getSource() == btnIndView) {

			DefaultPieDataset dataset = new DefaultPieDataset();
			DefaultCategoryDataset datasetBar = new DefaultCategoryDataset();

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;

			try {
				String url = CommonMethods.getUrl(Applicationmain.tranDbName);
				String user = CommonMethods.strLogin;
				String password = Validation.decrypt(CommonMethods.strPassword);
				con = DriverManager.getConnection(url, user, password);

				staffList = logicDailyActvity.getStaff();

				int des = getDesignationByStaffId(staffList, String.valueOf(cmbIndRepStaff.getSelectedItemValue()));

				StringBuilder queryPie = new StringBuilder("");
				queryPie.append("SELECT (CASE WHEN C.callnature = 'E' THEN 'Error'\r\n");
				queryPie.append(
						"WHEN C.callnature = 'M' THEN 'Modification' WHEN C.callnature = 'G' THEN 'General'\r\n");
				queryPie.append("WHEN C.callnature = 'D' THEN 'Development'\r\n");
				queryPie.append("WHEN C.callnature = 'C' THEN 'Clarification'\r\n");
				queryPie.append("WHEN C.callnature = 'T' THEN 'Tallying' ELSE '' END) AS callNature, "
						+ "COUNT(*) AS total \r\n");
				queryPie.append("FROM Calls C \r\n ");
				queryPie.append("	Left Join ReadyMark R On R.Callno = C.Callno \r\n");
				if (des == 1) {
					queryPie.append("WHERE Readyby = " + cmbIndRepStaff.getSelectedItemValue() + " ");
				} else {
					queryPie.append("WHERE Testing = " + cmbIndRepStaff.getSelectedItemValue() + " ");
				}
				queryPie.append("GROUP BY callnature");

				stmt = con.createStatement();
				rs = stmt.executeQuery(queryPie.toString());

				while (rs.next()) {
					String type = rs.getString("callNature");
					int count = rs.getInt("total");
					dataset.setValue(type, count);
				}

				rs.close();

				StringBuilder bldr = new StringBuilder();
				bldr.append("-- 1. Generate Calendar Dates for Financial Year\n ");
				bldr.append("With CalendarDates AS (\n ");
				bldr.append("    SELECT CAST('2025-04-01' AS DATE) AS CalendarDate\n ");
				bldr.append("    UNION ALL\n ");
				bldr.append("    SELECT DATEADD(DAY, 1, CalendarDate)\n ");
				bldr.append("    FROM CalendarDates\n ");
				bldr.append("    WHERE CalendarDate < CAST(GETDATE() AS DATE )\n ");
				bldr.append("),\n ");
				bldr.append("\n ");
				bldr.append("-- 2. Extract Attendance Records for the Staff\n ");
				bldr.append("Attendance AS (\n ");
				bldr.append("    SELECT \n ");
				bldr.append("        CAST(CreatedDate AS DATE) AS AttendanceDate,\n ");
				bldr.append("        StaffId,\n ");
				bldr.append("        Leave\n ");
				bldr.append("    FROM DailyActivity\n ");
				bldr.append("    WHERE StaffId =" + cmbIndRepStaff.getSelectedItemValue() + " \n ");
				bldr.append(")\n ");
				bldr.append("\n ");
				bldr.append("-- 3. Final Report Query\n ");
				bldr.append("SELECT \n ");
				bldr.append("    FORMAT(c.CalendarDate, 'MMM') AS Month,\n ");
				bldr.append("    MONTH(c.CalendarDate) AS MonthNumber,\n ");
				bldr.append("    YEAR(c.CalendarDate) AS YearNumber,\n ");
				bldr.append("\n ");
				bldr.append("    COUNT(DISTINCT CASE \n ");
				bldr.append("        WHEN a.StaffId IS NOT NULL AND a.Leave = 'N' THEN c.CalendarDate \n ");
				bldr.append("        ELSE NULL \n ");
				bldr.append("    END) AS PresentDays,\n ");
				bldr.append("\n ");
				bldr.append("    COUNT(*) AS TotalDaysInMonth,\n ");
				bldr.append("\n ");
				bldr.append("    COUNT(CASE \n ");
				bldr.append("        WHEN DATENAME(WEEKDAY, c.CalendarDate) = 'Sunday' THEN 1 \n ");
				bldr.append("        ELSE NULL \n ");
				bldr.append("    END) AS Sundays\n ");
				bldr.append("\n ");
				bldr.append("FROM CalendarDates c\n ");
				bldr.append("LEFT JOIN Attendance a\n ");
				bldr.append("    ON c.CalendarDate = a.AttendanceDate\n ");
				bldr.append("\n ");
				bldr.append("GROUP BY \n ");
				bldr.append("    FORMAT(c.CalendarDate, 'MMM'), \n ");
				bldr.append("    MONTH(c.CalendarDate), \n ");
				bldr.append("    YEAR(c.CalendarDate)\n ");
				bldr.append("\n ");
				bldr.append("ORDER BY YearNumber, MonthNumber\n ");
				bldr.append("\n ");
				bldr.append("-- Allow recursion up to 365 days\n ");
				bldr.append("OPTION (MAXRECURSION 400);\n ");

				rs = stmt.executeQuery(bldr.toString());

				while (rs.next()) {
					String month = rs.getString("Month");
					int presentDays = rs.getInt("PresentDays");
					int sundays = rs.getInt("Sundays");

					if (presentDays > 0)
						datasetBar.addValue(presentDays + sundays, "Days Present", month);
					else
						datasetBar.addValue(presentDays, "Days Present", month);
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error fetching data: " + ex.getMessage());
				return;
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (stmt != null)
						stmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			JFreeChart pieChart = ChartFactory.createPieChart("Calls Completion", // Chart title
					dataset, // Data
					true, // Include legend
					true, false); // Tooltips, URLs

			TextTitle title = pieChart.getTitle();
			title.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));

			if (pieChart.getLegend() != null) {
				pieChart.getLegend().setItemFont(new java.awt.Font("Calibri", java.awt.Font.PLAIN, 14));
			}

			PiePlot plot = (PiePlot) pieChart.getPlot();
			plot.setLabelFont(new java.awt.Font("Calibri", java.awt.Font.PLAIN, 12));

			if (panelCallsChart != null) {
				panelIndRepView.remove(panelCallsChart);
				panelCallsChart.setVisible(false);
				panelCallsChart = null;
			}

			panelCallsChart = new ChartPanel(pieChart);
			panelCallsChart.setBounds(30, 150, 600, 400); // Adjust positioning
			panelCallsChart.setLayout(null);
			panelCallsChart.setBackground(color2);
			panelCallsChart.setBorder(BorderFactory.createEtchedBorder(color3, color3));
			panelCallsChart.addKeyListener(this);
			panelCallsChart.setFont(CustomFonts.fontCalibriPlain15);
			panelCallsChart.setVisible(true);

			panelIndRepView.add(panelCallsChart);

			JFreeChart barChart = ChartFactory.createBarChart("Monthly Attendance", // Chart title
					"Month", // X-Axis Label
					"Days Present", // Y-Axis Label
					datasetBar, // Data
					PlotOrientation.VERTICAL, true, // Include legend
					true, false); // Tooltips, URLs

			// 🔹 Set title font
			TextTitle chartTitle = barChart.getTitle();
			chartTitle.setFont(new java.awt.Font("Calibri", Font.BOLD, 18));

			// 🔹 Set legend font (if it exists)
			if (barChart.getLegend() != null) {
				barChart.getLegend().setItemFont(new java.awt.Font("Calibri", java.awt.Font.PLAIN, 14));
			}

			// 🔹 Set axis fonts
			CategoryPlot plot1 = barChart.getCategoryPlot();

			// X-axis
			CategoryAxis xAxis = plot1.getDomainAxis();
			xAxis.setLabelFont(new java.awt.Font("Calibri", Font.BOLD, 14)); // Axis label: "Month"
			xAxis.setTickLabelFont(new java.awt.Font("Calibri", java.awt.Font.PLAIN, 12)); // Tick labels: "Jan",
																							// "Feb"...

			// Y-axis
			ValueAxis yAxis = plot1.getRangeAxis();
			yAxis.setLabelFont(new java.awt.Font("Calibri", Font.BOLD, 14)); // Axis label: "Days Present"
			yAxis.setTickLabelFont(new java.awt.Font("Calibri", java.awt.Font.PLAIN, 12)); // Tick labels: 1, 2, 3...

			if (panelCallsBar != null) {
				panelIndRepView.remove(panelCallsBar);
				panelCallsBar.setVisible(false);
				panelCallsBar = null;
			}

			panelCallsBar = new ChartPanel(barChart);
			panelCallsBar.setBounds(650, 150, 600, 400); // Adjust positioning
			panelCallsBar.setLayout(null);
			panelCallsBar.setBackground(color2);
			panelCallsBar.setBorder(BorderFactory.createEtchedBorder(color3, color3));
			panelCallsBar.addKeyListener(this);
			panelCallsBar.setFont(CustomFonts.fontCalibriPlain15);
			panelCallsBar.setVisible(true);

			panelIndRepView.add(panelCallsBar);

			panelIndRepView.revalidate();
			panelIndRepView.repaint();

			lblStaffName = new JLabel("STAFF NAME : ");
			lblStaffName.setBounds(panelCallsChart.getX(), panelCallsChart.getY() + panelCallsChart.getHeight() + 40,
					100, 22);
			lblStaffName.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
			lblStaffName.setForeground(color4);
			lblStaffName.setVisible(true);

			lblJobrole = new JLabel("JOB ROLE : ");
			lblJobrole.setBounds(lblStaffName.getX(), lblStaffName.getY() + lblStaffName.getHeight() + 20, 100, 22);
			lblJobrole.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
			lblJobrole.setForeground(color4);
			lblJobrole.setVisible(true);

			lblJoinDate = new JLabel("JOINING DATE : ");
			lblJoinDate.setBounds(lblJobrole.getX(), lblJobrole.getY() + lblJobrole.getHeight() + 20, 120, 22);
			lblJoinDate.setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
			lblJoinDate.setForeground(color4);
			lblJoinDate.setVisible(true);

			panelIndRepView.add(lblStaffName);
			panelIndRepView.add(lblJobrole);
			panelIndRepView.add(lblJoinDate);

			panelMonthAtn = new JPanel();
			panelMonthAtn.setBounds(1270, 150, 500, 400); // Adjust positioning
			panelMonthAtn.setLayout(null);
			panelMonthAtn.setBackground(color2);
			panelMonthAtn.setBorder(BorderFactory.createEtchedBorder(color3, color3));
			panelMonthAtn.addKeyListener(this);
			panelMonthAtn.setFont(getFont());
			panelMonthAtn.setVisible(true);

			panelIndRepView.add(panelMonthAtn);

			lblYearHead = new JLabel("Yealy Attendance");
			lblYearHead.setBounds(180, 0, 200, 50);
			lblYearHead.setBackground(color2);
			lblYearHead.setVisible(true);
			lblYearHead.setFont(CustomFonts.fontCalibriBold18);
			lblYearHead.setForeground(Color.BLACK);
			lblYearHead.setBackground(color2);

			panelMonthAtn.add(lblYearHead);

			tblYearReport = new JilabaTable(getYearlyReport());
			tblYearReport.setAutoResizeMode(JilabaTable.AUTO_RESIZE_OFF);
			tblYearReport.getTableHeader().setReorderingAllowed(false);
			tblYearReport.getTableHeader().setFont(jilabaFonts.getFont(FontStyle.BOLD, 16));
			tblYearReport.setFont(CustomFonts.fontCalibriPlain15);
			tblYearReport.setForeground(Color.BLACK);
			tblYearReport.getTableHeader().setForeground(color6);
			tblYearReport.getTableHeader().setBackground(Color.WHITE);
			tblYearReport.setRowHeight(22);
			tblYearReport.setVisible(true);
			tblYearReport.addKeyListener(this);

			scrYearRep = new JScrollPane(tblYearReport);
			scrYearRep.setBounds(25, lblYearHead.getY() + lblYearHead.getHeight(), 450, 320);
			scrYearRep.getViewport().setBackground(tblYearReport.getTableHeader().getBackground());
			scrYearRep.setVisible(true);

			panelMonthAtn.add(scrYearRep);

			/*
			 * DefaultPieDataset dataset = new DefaultPieDataset();
			 * 
			 * try { String url = CommonMethods.getUrl(Applicationmain.tranDbName); String
			 * user = CommonMethods.strLogin; String password =
			 * Validation.decrypt(CommonMethods.strPassword); Connection con =
			 * DriverManager.getConnection(url, user, password);
			 * 
			 * String query = "\r\n" + "Select (Case when C.callnature='E' then 'Error'\r\n"
			 * + "							when C.callnature='M' Then 'Modification'\r\n" +
			 * "							when C.callnature='G' Then 'General' \r\n" +
			 * "							when C.callnature='D' Then 'Development'\r\n" +
			 * "							when C.callnature='C' Then 'Clarification'\r\n"
			 * +
			 * "							when C.callnature='T' Then 'Tallying' else '' End)callNature ,Count(*) total from Calls C  \r\n"
			 * + " Where Sugto =" + cmbIndRepStaff.getSelectedItemValue() + "\r\n" +
			 * "Group by callnature"; Statement stmt = con.createStatement(); ResultSet rs =
			 * stmt.executeQuery(query);
			 * 
			 * while (rs.next()) { String type = rs.getString("callNature"); int count =
			 * rs.getInt("total"); dataset.setValue(type, count); }
			 * 
			 * rs.close(); stmt.close(); con.close();
			 * 
			 * } catch (SQLException ex) { ex.printStackTrace();
			 * JOptionPane.showMessageDialog(null, "Error fetching data: " +
			 * ex.getMessage()); return; }
			 * 
			 * // Create Pie Chart JFreeChart chart =
			 * ChartFactory.createPieChart("Calls Completion ", // chart title dataset, //
			 * data true, // include legend true, false);
			 * 
			 * // 🧹 Remove old chart panel if it exists if (panelCallsChart != null) {
			 * panelIndRepView.remove(panelCallsChart); panelCallsChart.setVisible(false);
			 * panelCallsChart = null; }
			 * 
			 * // Display chart in a frame
			 * 
			 * panelCallsChart = new ChartPanel(chart); panelCallsChart.setBounds(30, 150,
			 * 600, 400); panelCallsChart.setLayout(null);
			 * panelCallsChart.setBackground(color2);
			 * panelCallsChart.setBorder(BorderFactory.createEtchedBorder(color3, color3));
			 * panelCallsChart.addKeyListener(this); panelCallsChart.setFont(getFont());
			 * panelCallsChart.setVisible(true);
			 * 
			 * panelIndRepView.add(panelCallsChart);
			 * 
			 * panelIndRepView.revalidate(); panelIndRepView.repaint();
			 * 
			 * // ChartPanel chartPanel = new ChartPanel(chart); // JFrame chartFrame = new
			 * JFrame("Pie Chart"); //
			 * chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //
			 * chartFrame.add(panelCallsChart); // chartFrame.setSize(600, 400); //
			 * chartFrame.setLocationRelativeTo(null); // chartFrame.setVisible(true);
			 */ }

	}

	private int getDesignationByStaffId(List<Map<String, Object>> lstStaff, String staffId) {
		for (Map<String, Object> row : lstStaff) {
			if (staffId.equals(String.valueOf(row.get("StaffId")))) {
				return Integer.parseInt(String.valueOf(row.get("Designation")));
			}
		}
		return 0;
	}

	private void exportToPDF(ResultSet rs, String fileName) throws Exception {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(fileName));
		document.open();

		BaseFont calibri = BaseFont.createFont("C:/Windows/Fonts/calibri.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font titleFont = new Font(calibri, 12, Font.BOLD);

		Paragraph title = new Paragraph(("Lalitha Jewellery Jilaba Team Date - " + spnRepDate.getDateValue()),
				titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingAfter(10f);
		document.add(title);

		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();

		PdfPTable table = new PdfPTable(columnCount);
		table.setWidthPercentage(100);
		table.setSpacingBefore(10f);

		float[] columnWidths = new float[columnCount];
		columnWidths[0] = 3.5f; // First column wider
		for (int i = 1; i < columnCount; i++) {
			columnWidths[i] = 1.5f; // Other columns
		}
		table.setWidths(columnWidths);

		// Header row
		Font headerFont = new Font(calibri, 12, Font.BOLD);
		for (int i = 1; i <= columnCount; i++) {
			PdfPCell headerCell = new PdfPCell(new Phrase(metaData.getColumnName(i), headerFont));
			headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(headerCell);
		}

		// Data rows
		Font dataFont = new Font(calibri, 12, Font.NORMAL);
		while (rs.next()) {
			for (int i = 1; i <= columnCount; i++) {
				String value = rs.getString(i);
				PdfPCell cell = new PdfPCell(new Phrase(value != null ? value : "", dataFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
			}
		}

		// Data rows
		while (rs.next()) {
			for (int i = 1; i <= columnCount; i++) {
				String value = rs.getString(i);
				PdfPCell cell = new PdfPCell(new Phrase(value != null ? value : "", headerFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
			}
		}

		Font bottom = new Font(calibri, 12, Font.NORMAL);
		PdfPCell mergedCell = new PdfPCell(new Phrase("Prepared by " + FrmLogin.Operator, bottom));
		mergedCell.setColspan(columnCount);
		mergedCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		mergedCell.setPadding(10f);
		table.addCell(mergedCell);

		document.add(table);
		document.close();
	}

	public void exportToExcel(ResultSet rs, String fileName) throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");

		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();

		Row headRow = sheet.createRow(0);
		Cell headCell = headRow.createCell(0);
		headCell.setCellValue("Lalitha Jewellery Jilaba Team Date - " + spnAtnDate.getDateValue());

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount - 1));

		sheet.createRow(1);

		Row headerRow = sheet.createRow(2);
		for (int i = 1; i <= columnCount; i++) {
			Cell cell = headerRow.createCell(i - 1);
			cell.setCellValue(metaData.getColumnName(i));
		}

		int rowIndex = 3;
		while (rs.next()) {
			Row dataRow = sheet.createRow(rowIndex++);
			for (int i = 1; i <= columnCount; i++) {
				Cell cell = dataRow.createCell(i - 1);
				cell.setCellValue(rs.getString(i));
			}
		}

		try (FileOutputStream fos = new FileOutputStream(fileName)) {
			workbook.write(fos);
		}
		workbook.close();
	}

	private void btnAtnReport() {

		spnRepDate.requestFocus();
		panelEntry.setVisible(false);
		panelAtnReport.setVisible(true);

	}

	private void btnAtnView() {

		panelEntry.setVisible(false);
		panelAtnView.setVisible(true);

		lstoperator = logicDailyActvity.getOperators();

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
			lstobject.add(oper.getStaffid());

			tblAttendance.addRow(lstobject);

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (e.getSource() == tblAttendance) {
				btnAtnMark.requestFocus();

			} else if (e.getSource() == tblReport) {
				btnRepExport.requestFocus();
			} else if (e.getSource() == cmbIndRepStaff) {
				btnIndividualReport.requestFocus();
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
