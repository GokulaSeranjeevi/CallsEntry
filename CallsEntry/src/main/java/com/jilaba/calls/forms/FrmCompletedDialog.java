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
import com.jilaba.calls.logic.LogicCallsCompleted;
import com.jilaba.calls.logic.LogicReadyCalls;
import com.jilaba.exception.JilabaException;
import com.jilaba.fonts.JilabaFonts;
import com.jilaba.fonts.JilabaFonts.FontStyle;

@Component
@Scope("prototype")
public class FrmCompletedDialog extends JDialog implements ActionListener {

	private JPanel panelMain;
	private JPanel panelCompleted;

	private JLabel lblReadDesc;
	private JTextArea txtArea;
	private JButton btnCompleted;
	private JButton btnExit;

	private int cNo;
	private String cModuleId;
	private boolean blnCallCompleted;
	private JilabaFonts jilabaFonts = new JilabaFonts();

	private Color color1 = Color.decode("#66023c");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#843462");
	private Color color4 = Color.decode("#e0ccd8");
	private Color color5 = Color.decode("#8E7460");
	private Color color6 = Color.decode("#000000");
	private Color color7 = Color.decode("#B2809D");

	@Autowired
	private LogicReadyCalls logicReadyCalls;
	@Autowired
	private LogicCallsCompleted logicCallsCompleted;

	public FrmCompletedDialog(Container contenPane) throws JilabaException {

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
		panelMain.add(panelCompletedCalls());

		getContentPane().add(panelMain);
		
		
	}

	private java.awt.Component panelCompletedCalls() {

		panelCompleted = new JPanel();
		panelCompleted.setBounds(10, 30, 580, 200);
		panelCompleted.setLayout(null);
		panelCompleted.setBackground(color1);
		panelCompleted.setVisible(true);

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

		lblReadDesc = new JLabel("Testing Description");
		lblReadDesc.setHorizontalAlignment(SwingConstants.LEFT);
		lblReadDesc.setBounds(10, 5, 160, 20);
		lblReadDesc.setFont(CustomFonts.fontCalibriBold);
		lblReadDesc.setBackground(color3);
		lblReadDesc.setVisible(true);

		btnCompleted = new JButton("Completed");
		btnCompleted.setBounds(200, 250, 80, 20);
		btnCompleted.setBackground(color3);
		btnCompleted.setForeground(Color.WHITE);
		btnCompleted.setMnemonic(KeyEvent.VK_R);
		btnCompleted.setFont(jilabaFonts.getFont(FontStyle.BOLD, 15));
		btnCompleted.setHorizontalAlignment(SwingConstants.CENTER);
		btnCompleted.setBorder(BorderFactory.createEmptyBorder());
		btnCompleted.setVisible(true);
		btnCompleted.setEnabled(true);
		btnCompleted.addActionListener(this);
		//		btnReady.addKeyListener(this);

		btnExit = new JButton("Exit");
		btnExit.setBounds(btnCompleted.getX() + 110, btnCompleted.getY(), 60, 20);
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

		panelMain.add(panelCompleted);
		panelMain.add(lblReadDesc);
		panelMain.add(btnCompleted);
		panelMain.add(btnExit);
		panelCompleted.add(txtArea);

		return panelCompleted;
	}

	public void intializeVariable(int Callno, String moduleId, boolean blnFrmcompletedcall) {

		cNo = Callno;
		cModuleId = moduleId;
		blnCallCompleted = blnFrmcompletedcall;

		loadInialize();
	}

	private void loadInialize() {

		if (blnCallCompleted == true) {

			lblReadDesc.setText("Completed Description");
			btnCompleted.setText("Delivered");
			panelCompleted.setBackground(color5);
			btnCompleted.setBackground(color5);
			btnExit.setBackground(color5);
			//			txtArea.setBorder(BorderFactory.createEtchedBorder(color5, color5));

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCompleted) {

			String completedDesc = String.valueOf(txtArea.getText());

			if (blnCallCompleted == true) {

				logicCallsCompleted.updateDeliveredCalls(cNo, completedDesc);
				JOptionPane.showMessageDialog(panelMain, " Call Delivery Marked ...!");

			} else {
				logicReadyCalls.updateCompletedCalls(cNo, completedDesc, cModuleId);
				JOptionPane.showMessageDialog(panelMain, "Completed Call Marked ...!");
			}

			completedDesc = "";
			txtArea.removeAll();
			setVisible(false);

		}

		if (e.getSource() == btnExit) {

			setVisible(false);

		}

	}

}
