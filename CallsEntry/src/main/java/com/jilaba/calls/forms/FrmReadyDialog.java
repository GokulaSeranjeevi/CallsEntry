package com.jilaba.calls.forms;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.logic.LogicDevCalls;
import com.jilaba.exception.JilabaException;
import com.jilaba.fonts.JilabaFonts;
import com.jilaba.fonts.JilabaFonts.FontStyle;

@Component
@Scope("prototype")
public class FrmReadyDialog extends JDialog implements ActionListener {

	private JPanel panelMain;
	private JPanel panelReady;

	private JLabel lblReadDesc;
	private JTextArea txtArea;
	private JButton btnReady;
	private JButton btnExit;

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

	public FrmReadyDialog(Container contenPane) throws JilabaException {

		setLayout(null);
		setTitle("Call Assign");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setUndecorated(true);
		setResizable(false);
		setSize(600, 280);
		setDefaultCloseOperation(0);
		// setLocation(280, 150);
		setLocationRelativeTo(contenPane);

		createControl();

	}

	private void createControl() {

		panelMain = new JPanel();
		panelMain.setBounds(0, 0, 600, 280);
		panelMain.setLayout(null);
		panelMain.setBackground(color2);
		panelMain.add(panelReadyCalls());

		getContentPane().add(panelMain);

	}

	private java.awt.Component panelReadyCalls() {

		panelReady = new JPanel();
		panelReady.setBounds(10, 30, 580, 200);
		panelReady.setLayout(null);
		panelReady.setBackground(color1);
		panelReady.setVisible(true);

		txtArea = new JTextArea(560, 180);
		txtArea.setEditable(true);
		txtArea.setLineWrap(true);
		txtArea.setBounds(5, 5, 570, 190);
		txtArea.setFont(CustomFonts.fontCalibriBold);
		txtArea.setBackground(color2);
		txtArea.setForeground(Color.BLACK);
		txtArea.setBorder(BorderFactory.createEtchedBorder(color4, color4));
		txtArea.setVisible(true);
		//		txtArea.addKeyListener(this);

		lblReadDesc = new JLabel("Ready Description ");
		lblReadDesc.setHorizontalAlignment(SwingConstants.LEFT);
		lblReadDesc.setBounds(10, 5, 130, 20);
		lblReadDesc.setFont(CustomFonts.fontCalibriBold);
		lblReadDesc.setBackground(color3);
		lblReadDesc.setVisible(true);

		btnReady = new JButton("Ready");
		btnReady.setBounds(220, 250, 60, 20);
		btnReady.setBackground(color3);
		btnReady.setForeground(Color.WHITE);
		btnReady.setMnemonic(KeyEvent.VK_R);
		btnReady.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		btnReady.setHorizontalAlignment(SwingConstants.CENTER);
		btnReady.setBorder(BorderFactory.createEmptyBorder());
		btnReady.setVisible(true);
		btnReady.setEnabled(true);
		btnReady.addActionListener(this);
		//		btnReady.addKeyListener(this);

		btnExit = new JButton("Exit");
		btnExit.setBounds(btnReady.getX() + 80, btnReady.getY(), 60, 20);
		btnExit.setBackground(color3);
		btnExit.setForeground(Color.WHITE);
		btnExit.setMnemonic(KeyEvent.VK_E);
		btnExit.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		btnExit.setHorizontalAlignment(SwingConstants.CENTER);
		btnExit.setBorder(BorderFactory.createEmptyBorder());
		btnExit.setVisible(true);
		btnExit.setEnabled(true);
		btnExit.addActionListener(this);
		//		btnReady.addKeyListener(this);

		panelMain.add(panelReady);
		panelMain.add(lblReadDesc);
		panelMain.add(btnReady);
		panelMain.add(btnExit);
		panelReady.add(txtArea);

		return panelReady;
	}

	public void intializeVariable(int Callno, int moduleId) {

		cNo = Callno;
		cModuleId = moduleId;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnReady) {

			String readyDesc = String.valueOf(txtArea.getText());

			logicDevCalls.updateReadyCalls(cNo, readyDesc);

			logicDevCalls.saveReadyCalls(cNo, readyDesc,cModuleId);

			readyDesc = "";
			txtArea.removeAll();
			setVisible(false);

			JOptionPane.showMessageDialog(panelMain, "Ready Call Marked !");
			
			

		}

		if (e.getSource() == btnExit) {

			setVisible(false);

		}

	}

}
