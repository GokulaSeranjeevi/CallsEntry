package com.jilaba.calls.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CommonMethods;
import com.jilaba.calls.common.CommonValues;
import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.logic.LogicDevCallAssign;
import com.jilaba.calls.model.Operator;
import com.jilaba.calls.start.Applicationmain;
import com.jilaba.common.ReturnStatus;
import com.jilaba.control.JilabaButton;
import com.jilaba.control.JilabaComboBox;
import com.jilaba.control.JilabaSpinner;
import com.jilaba.control.JilabaTextField;
import com.jilaba.control.ListItem;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;
import com.jilaba.fonts.JilabaFonts.FontStyle;

@Component
@Scope("prototype")
public class FrmCallDialog extends JDialog implements ActionListener, KeyListener {

	private JLabel lblDiaExplantion;
	private JLabel lblDiaSugNo;
	private JLabel lblDiaDevHrs;
	private JLabel lblDiaAssnDate;
	private JLabel lblDiaPropDev;

	private JPanel panelCommonDialog;
	private JPanel panelTitle;

	private JilabaComboBox<String> cmbExplanation;
	private JilabaComboBox<Operator> cmbDiaSugTo;

	private JilabaSpinner spnAssnDate;
	private JilabaTextField txtDevHrs;

	private JButton btnUpdate;
	private JButton btnExit;

	private List<Operator> lstSugTo;

	@Autowired
	private LogicDevCallAssign logicDevCallAssign;

	private int diaCallNo;
	private int diatblSelectedRow;

	private Color color1 = Color.decode("#ff6666");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#ff9999");
	private Color color4 = Color.decode("#000000");
	private Color color5 = Color.decode("#e4dedf");
	private Color color6 = Color.decode("#C0C0C0");
	private Color color7 = Color.decode("#FADBD8");

	public FrmCallDialog(Container contenPane) throws JilabaException {

		setLayout(null);
		setTitle("Call Assign");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setUndecorated(true);
		setResizable(false);
		setSize(300, 200);
		setDefaultCloseOperation(0);
		// setLocation(280, 150);
		setLocationRelativeTo(contenPane);

		createControl();

	}

	public void getInitializeValue(int callNo, int tblSelectedRow) {

		diaCallNo = callNo;
		diatblSelectedRow = tblSelectedRow;

	}

	private ReturnStatus callUpdate() {

		try {
			logicDevCallAssign.devCallUpdate(String.valueOf(cmbExplanation.getSelectedItem()).substring(0, 2),
					cmbDiaSugTo.getSelectedItemValue(), txtDevHrs.getText(), spnAssnDate.getDateValue(), diaCallNo);

			JOptionPane.showMessageDialog(panelCommonDialog, "Call Assign Successfully !...");
			setVisible(false);
			getInitializeValue(diaCallNo, diatblSelectedRow);
			FrmDevCallAssign.tblCalls.removeRow(diatblSelectedRow);
			FrmDevCallAssign.tblCalls.requestFocus();
			FrmDevCallAssign.tblCalls.changeSelection(diatblSelectedRow, 1, false, false);

			return new ReturnStatus(true);
		} catch (Exception e) {

			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}

	}

	@PostConstruct
	private ReturnStatus loadDetails() {

		cmbExplanation.addListItem(new ListItem("Explained"));
		cmbExplanation.addListItem(new ListItem("UnExplained"));
		cmbExplanation.addListItem(new ListItem("Not Required"));

		lstSugTo = logicDevCallAssign.getDeveloper();

		for (Operator developer : lstSugTo) {

			cmbDiaSugTo.addListItem(new ListItem(developer.getStaffname(), developer.getStaffid()));
		}

		return new ReturnStatus(true);

	}

	private void createControl() {

		//		JOptionPane jop = new JOptionPane();
		//		JDialog dialog = jop.createDialog("Call Assign");
		//		dialog.setSize(400, 400);

		panelTitle = new JPanel(null);
		panelTitle.setBounds(0, 0, 750, 30);
		panelTitle.setBackground(Color.lightGray);
		// panelTitle.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

		panelTitle.setVisible(true);

		panelCommonDialog = new JPanel(null);
		panelCommonDialog.setBounds(0, 0, 300, 200);
		panelCommonDialog.setBackground(color2);
		panelCommonDialog.setVisible(true);
		panelCommonDialog.addKeyListener(this);

		lblDiaExplantion = new JLabel("Explanation");
		lblDiaExplantion.setBounds(20, 30, 100, 35);
		lblDiaExplantion.setForeground(Color.BLACK);
		lblDiaExplantion.setFont(CustomFonts.fontCalibriPlain15);
		lblDiaExplantion.setVisible(true);

		cmbExplanation = new JilabaComboBox<String>();
		cmbExplanation.setBounds(lblDiaExplantion.getX() + lblDiaExplantion.getWidth() + 10,
				lblDiaExplantion.getY() + 5, 140, 25);
		cmbExplanation.setForeground(Color.BLACK);
		cmbExplanation.setFont(CustomFonts.fontCalibriPlain15);
		cmbExplanation.setVisible(true);
		cmbExplanation.addKeyListener(this);

		lblDiaSugNo = new JLabel("SugTo");
		lblDiaSugNo.setBounds(lblDiaExplantion.getX(), lblDiaExplantion.getY() + 35, 100, 30);
		lblDiaSugNo.setForeground(Color.BLACK);
		lblDiaSugNo.setFont(CustomFonts.fontCalibriPlain15);
		lblDiaSugNo.setVisible(true);

		cmbDiaSugTo = new JilabaComboBox<Operator>();
		cmbDiaSugTo.setBounds(cmbExplanation.getX(), lblDiaSugNo.getY() + 5, 140, 25);
		cmbDiaSugTo.setForeground(Color.BLACK);
		cmbDiaSugTo.setFont(CustomFonts.fontCalibriPlain15);
		cmbDiaSugTo.setVisible(true);
		cmbDiaSugTo.addKeyListener(this);

		lblDiaDevHrs = new JLabel("DevHrs");
		lblDiaDevHrs.setBounds(lblDiaSugNo.getX(), lblDiaSugNo.getY() + 35, 100, 30);
		lblDiaDevHrs.setForeground(Color.BLACK);
		lblDiaDevHrs.setFont(CustomFonts.fontCalibriPlain15);
		lblDiaDevHrs.setVisible(true);

		txtDevHrs = new JilabaTextField();
		txtDevHrs.setBounds(cmbExplanation.getX(), lblDiaDevHrs.getY() + 5, 140, 25);
		txtDevHrs.setForeground(Color.BLACK);
		txtDevHrs.setFont(CustomFonts.fontCalibriPlain15);
		txtDevHrs.setVisible(true);
		txtDevHrs.addKeyListener(this);

		lblDiaAssnDate = new JLabel("AssignDate");
		lblDiaAssnDate.setBounds(lblDiaDevHrs.getX(), lblDiaDevHrs.getY() + 35, 100, 30);
		lblDiaAssnDate.setForeground(Color.BLACK);
		lblDiaAssnDate.setFont(CustomFonts.fontCalibriPlain15);
		lblDiaAssnDate.setVisible(true);

		spnAssnDate = new JilabaSpinner();
		spnAssnDate.setBounds(cmbExplanation.getX(), lblDiaAssnDate.getY() + 5, 140, 25);
		spnAssnDate.setForeground(Color.BLACK);
		spnAssnDate.setFont(CustomFonts.fontCalibriPlain15);
		spnAssnDate.setVisible(true);
		spnAssnDate.addKeyListener(this);

		btnUpdate = new JButton("Assign");
		btnUpdate.setBounds(lblDiaAssnDate.getX() + 20, lblDiaAssnDate.getY() + 40, 80, 20);
		btnUpdate.setBackground(Color.GRAY);
		btnUpdate.setForeground(Color.BLACK);
		btnUpdate.setMnemonic(KeyEvent.VK_A);
		btnUpdate.setFont(CustomFonts.fontCalibriBold);
		btnUpdate.setVisible(true);
		btnUpdate.addKeyListener(this);
		btnUpdate.addActionListener(this);

		btnExit = new JButton("Exit");
		btnExit.setBounds(btnUpdate.getX() + btnUpdate.getWidth() + 50, btnUpdate.getY(), 80, 20);
		btnExit.setBackground(Color.GRAY);
		btnExit.setForeground(Color.BLACK);
		btnExit.setMnemonic(KeyEvent.VK_E);
		btnExit.setFont(CustomFonts.fontCalibriBold);
		btnExit.setVisible(true);
		btnExit.addKeyListener(this);
		btnExit.addActionListener(this);

		getContentPane().add(panelCommonDialog);
		panelCommonDialog.add(panelTitle);

		panelCommonDialog.add(lblDiaExplantion);
		panelCommonDialog.add(lblDiaSugNo);
		panelCommonDialog.add(lblDiaDevHrs);
		panelCommonDialog.add(lblDiaAssnDate);
		panelCommonDialog.add(cmbExplanation);
		panelCommonDialog.add(cmbDiaSugTo);
		panelCommonDialog.add(txtDevHrs);
		panelCommonDialog.add(btnUpdate);
		panelCommonDialog.add(btnExit);
		panelCommonDialog.add(spnAssnDate);

	}

	public Object showCallUpdate() {

		return null;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			if (e.getSource() == cmbExplanation) {
				cmbDiaSugTo.requestFocus();

			} else if (e.getSource() == cmbDiaSugTo) {
				txtDevHrs.requestFocus();

			} else if (e.getSource() == txtDevHrs) {
				spnAssnDate.requestFocus();

			} else if (e.getSource() == spnAssnDate) {
				btnUpdate.requestFocus();

			}

		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

			if (e.getSource() == btnUpdate) {

				btnExit.requestFocus();
			}

		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {

			if (e.getSource() == btnExit) {

				setVisible(false);
			}

		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

			setVisible(false);

			//			FrmDevCallAssign frmDevCallAssign = Applicationmain.getAbstractApplicationContext()
			//					.getBean(FrmDevCallAssign.class);
			//			frmDevCallAssign.setVisible(true);

			FrmDevCallAssign.tblCalls.requestFocus();

		}

		spnAssnDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					btnUpdate.requestFocus();
				}

			}
		});

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnUpdate) {

			if (txtDevHrs.getText().equals("")) {
				JOptionPane.showMessageDialog(btnExit, "Developer Hours Required!...");
				txtDevHrs.requestFocus();
				return;

			}

			int response = JOptionPane.showConfirmDialog(panelCommonDialog, "Do you want to proceed?", "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (response == JOptionPane.YES_OPTION) {
				callUpdate();
			} else if (response == JOptionPane.NO_OPTION) {
				cmbExplanation.requestFocus();
				return;
			}

			;

		} else if (e.getSource() == btnExit) {

			setVisible(false);

		}

	}

}
