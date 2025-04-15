package com.jilaba.calls.forms;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.logic.LogicDevCalls;
import com.jilaba.calls.model.Operator;
import com.jilaba.common.ReturnStatus;
import com.jilaba.control.JilabaComboBox;
import com.jilaba.control.ListItem;
import com.jilaba.exception.JilabaException;
import com.jilaba.fonts.JilabaFonts;
import com.jilaba.fonts.JilabaFonts.FontStyle;

@Component
@Scope("prototype")
public class FrmTransferCall extends JDialog implements ActionListener {

	private JPanel panelMain;
	private JPanel panelTransfer;
	private List<Operator> lstDeveloper;

	private JLabel lblTransferDesc;
//	private JTextArea txtArea;
	private JButton btnTransfer;
	private JButton btnExit;
	private JilabaComboBox<Operator> cmbDeveloper;

	private int cNo;
	private int cModuleId;
	private JilabaFonts jilabaFonts = new JilabaFonts();

	private Color color1 = Color.decode("#808B96");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#2C3E50");
	private Color color4 = Color.decode("#AED6F1");
	private Color color5 = Color.decode("#e4dedf");
	private Color color6 = Color.decode("#A6ACAF");
	private Color color7 = Color.decode("#FF3933");

	@Autowired
	private LogicDevCalls logicDevCalls;

	public FrmTransferCall(Container contenPane) throws JilabaException {

		setLayout(null);
		setTitle("Call Assign");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setUndecorated(true);
		setResizable(false);
		setSize(300, 100);
		setDefaultCloseOperation(0);
		// setLocation(280, 150);
		setLocationRelativeTo(contenPane);

		createControl();

	}

	@PostConstruct
	private ReturnStatus loadInitialize() {

		lstDeveloper = logicDevCalls.getDeveloper();

		for (Operator dev : lstDeveloper) {
			cmbDeveloper.addListItem(new ListItem(dev.getStaffname(), dev.getStaffid()));
		}

		return new ReturnStatus(true);

	}

	private void createControl() {

		panelMain = new JPanel();
		panelMain.setBounds(0, 0, 300, 100);
		panelMain.setLayout(null);
		panelMain.setBackground(color2);
		panelMain.add(panelReadyCalls());

		getContentPane().add(panelMain);

	}

	private java.awt.Component panelReadyCalls() {

		panelTransfer = new JPanel();
		panelTransfer.setBounds(0, 0, 300, 100);
		panelTransfer.setLayout(null);
		panelTransfer.setBackground(color2);
		panelTransfer.setBorder(BorderFactory.createEtchedBorder(20, color1, color1));
		panelTransfer.setVisible(true);

//		txtArea = new JTextArea(560, 180);
//		txtArea.setEditable(true);
//		txtArea.setLineWrap(true);
//		txtArea.setBounds(5, 5, 570, 190);
//		txtArea.setFont(CustomFonts.fontCalibriBold);
//		txtArea.setBackground(color2);
//		txtArea.setForeground(Color.BLACK);
//		txtArea.setBorder(BorderFactory.createEtchedBorder(color4, color4));
//		txtArea.setVisible(true);
		// txtArea.addKeyListener(this);

		lblTransferDesc = new JLabel("Transfer To :");
		lblTransferDesc.setHorizontalAlignment(SwingConstants.LEFT);
		lblTransferDesc.setBounds(10, 20, 100, 20);
		lblTransferDesc.setFont(CustomFonts.fontCalibriBold);
		lblTransferDesc.setBackground(color3);
		lblTransferDesc.setVisible(true);

		cmbDeveloper = new JilabaComboBox<Operator>();
		cmbDeveloper.setBounds(lblTransferDesc.getX() + lblTransferDesc.getWidth() + 20, lblTransferDesc.getY(), 150,
				30);
		cmbDeveloper.setBackground(color2);
		cmbDeveloper.setFont(CustomFonts.fontCalibriBold);
		cmbDeveloper.setVisible(true);
//		cmbDeveloper.addKeyListener(this);

		btnTransfer = new JButton("Transfer");
		btnTransfer.setBounds(lblTransferDesc.getX() + 70, lblTransferDesc.getY() + lblTransferDesc.getHeight() + 20,
				60, 20);
		btnTransfer.setBackground(color3);
		btnTransfer.setForeground(Color.WHITE);
		btnTransfer.setMnemonic(KeyEvent.VK_R);
		btnTransfer.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		btnTransfer.setHorizontalAlignment(SwingConstants.CENTER);
		btnTransfer.setBorder(BorderFactory.createEmptyBorder());
		btnTransfer.setVisible(true);
		btnTransfer.setEnabled(true);
		btnTransfer.addActionListener(this);
		// btnReady.addKeyListener(this);

		btnExit = new JButton("Exit");
		btnExit.setBounds(btnTransfer.getX() + 80, btnTransfer.getY(), 60, 20);
		btnExit.setBackground(color3);
		btnExit.setForeground(Color.WHITE);
		btnExit.setMnemonic(KeyEvent.VK_E);
		btnExit.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		btnExit.setHorizontalAlignment(SwingConstants.CENTER);
		btnExit.setBorder(BorderFactory.createEmptyBorder());
		btnExit.setVisible(true);
		btnExit.setEnabled(true);
		btnExit.addActionListener(this);
		// btnReady.addKeyListener(this);

		panelMain.add(panelTransfer);
		panelTransfer.add(lblTransferDesc);
		panelTransfer.add(btnTransfer);
		panelTransfer.add(btnExit);
		panelTransfer.add(cmbDeveloper);
//		panelReady.add(txtArea);

		return panelTransfer;
	}

	public void intializeVariable(int Callno, int moduleId) {

		cNo = Callno;
		cModuleId = moduleId;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnTransfer) {

			int dev = Integer.parseInt(String.valueOf(cmbDeveloper.getSelectedItemValue()));

			logicDevCalls.updateDev(cNo, dev);

			dev = 0;

			setVisible(false);

			JOptionPane.showMessageDialog(panelMain, "Call Transfer...!");

		}

		if (e.getSource() == btnExit) {

			setVisible(false);

		}

	}

}
