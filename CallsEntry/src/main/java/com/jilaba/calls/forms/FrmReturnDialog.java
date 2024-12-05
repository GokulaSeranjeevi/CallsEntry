package com.jilaba.calls.forms;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
import com.jilaba.calls.logic.LogicReadyCalls;
import com.jilaba.exception.JilabaException;
import com.jilaba.fonts.JilabaFonts;
import com.jilaba.fonts.JilabaFonts.FontStyle;

@Component
@Scope("prototype")
public class FrmReturnDialog extends JDialog implements ActionListener {

	private JPanel panelMain;
	private JPanel panelReturn;

	private JLabel lblReadDesc;
	private JTextArea txtArea;
	private JButton btnReturn;
	private JButton btnExit;

	private int cNo;
	private String cModuleId;
	private JilabaFonts jilabaFonts = new JilabaFonts();

	private Color color1 = Color.decode("#66023c");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#843462");
	private Color color4 = Color.decode("#e0ccd8");
	private Color color5 = Color.decode("#e4dedf");
	private Color color6 = Color.decode("#000000");
	private Color color7 = Color.decode("#B2809D");

	@Autowired
	private LogicReadyCalls logicReadyCalls;

	public FrmReturnDialog(Container contenPane) throws JilabaException {

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
		panelMain.add(panelReturnCalls());

		getContentPane().add(panelMain);

	}

	private java.awt.Component panelReturnCalls() {

		panelReturn = new JPanel();
		panelReturn.setBounds(10, 30, 580, 200);
		panelReturn.setLayout(null);
		panelReturn.setBackground(color1);
		panelReturn.setVisible(true);

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

		lblReadDesc = new JLabel("Return Description ");
		lblReadDesc.setHorizontalAlignment(SwingConstants.LEFT);
		lblReadDesc.setBounds(10, 5, 130, 20);
		lblReadDesc.setFont(CustomFonts.fontCalibriBold);
		lblReadDesc.setBackground(color3);
		lblReadDesc.setVisible(true);

		btnReturn = new JButton("Return ");
		btnReturn.setBounds(220, 250, 60, 20);
		btnReturn.setBackground(color3);
		btnReturn.setForeground(Color.WHITE);
		btnReturn.setMnemonic(KeyEvent.VK_R);
		btnReturn.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		btnReturn.setHorizontalAlignment(SwingConstants.CENTER);
		btnReturn.setBorder(BorderFactory.createEmptyBorder());
		btnReturn.setVisible(true);
		btnReturn.setEnabled(true);
		btnReturn.addActionListener(this);
		//		btnReady.addKeyListener(this);

		btnExit = new JButton("Exit");
		btnExit.setBounds(btnReturn.getX() + 80, btnReturn.getY(), 60, 20);
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

		panelMain.add(panelReturn);
		panelMain.add(lblReadDesc);
		panelMain.add(btnReturn);
		panelMain.add(btnExit);
		panelReturn.add(txtArea);

		return panelReturn;
	}

	public void intializeVariable(int Callno, String moduleId) {

		cNo = Callno;
		cModuleId = moduleId;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnReturn) {

			String ReturnDesc = String.valueOf(txtArea.getText());

			logicReadyCalls.saveReadyUnmark(cNo, ReturnDesc, cModuleId);

			logicReadyCalls.updateReturnCalls(cNo, ReturnDesc, cModuleId);
			
			logicReadyCalls.updateReadyCancel(cNo);

			ReturnDesc = "";
			txtArea.removeAll();
			setVisible(false);

			JOptionPane.showMessageDialog(panelMain, " Return Call Marked !");

		}

		if (e.getSource() == btnExit) {

			setVisible(false);

		}

	}

}
