package com.jilaba.paymentdevice;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.jilaba.common.ReturnStatus;
import com.jilaba.design.JilabaDesign;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;
import com.jilaba.paymentdevice.CF.ISwipingMachine;
import com.jilaba.paymentdevice.CF.PaymentStatus;
import com.jilaba.paymentdevice.CF.YesNoType;

public class FrmCreditCardMachine extends JDialog implements ISwipingMachine, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClassLoader classLoader = FrmCreditCardMachine.class.getClassLoader();

	private JPanel panel;
	private JLabel lblImage;
	private JLabel lblAmt;
	private JLabel lblPlutusTranRefId;
	private JLabel lblPlutusTranRefIdVal;
	private JLabel lblTimerCaption;
	private JLabel lblTimer;
	private JButton btnCancel;

	private Thread timerThread;
	private PineLabs pineLabs;
	private PaymentDetails payDetails;
	private Map<String, Object> mapResponse;

	private static final String IMAGEPROCESSING = "pay_loading.gif";
	private boolean transactionCancelled = false;
	private boolean isPending = false;
	private boolean payFailed = false;

	/*public static void main(String[] args) {
		new FrmCreditCardMachine();
	}
	
	public FrmCreditCardMachine() {
		try {
	
			createControls();
			createEvents();
	
			lblAmt.setText(CF.toAmtFormat(10000.00));
			lblPlutusTranRefIdVal.setText("233357");
	
			Thread thread = new Thread(this);
			thread.start();
			setVisible(true);
	
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					transactionCancelled = false;
					setVisible(false);
				}
			});
	
		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}*/

	public FrmCreditCardMachine(PaymentDetails details) {
		try {

			if (null == details || null == details.getSwipingMachine())
				throw new JilabaException("CreditCard Machine Details Not Found.");

			payDetails = details;

			createControls();
			createEvents();

			lblAmt.setText(CF.toAmtFormat(details.getAmount() / 100));
			timerThread = new Thread(this);

			switch (payDetails.getSwipingMachine().getDeviceId()) {
			case 1:

				pineLabs = new PineLabs(this, payDetails, lblPlutusTranRefId, lblPlutusTranRefIdVal, timerThread);
				break;

			default:
				throw new JilabaException("CreditCard Machine Details Not Found.");
			}

			// timerThread.start();
			setVisible(true);

			this.addWindowListener(new WindowAdapter() {
				@SuppressWarnings("unchecked")
				@Override
				public void windowClosing(WindowEvent e) {
					try {

						timerThread = null;
						ReturnStatus returnStatus = pineLabs.cancelPayment();
						CF.statusCheck(returnStatus);

						transactionCancelled = true;
						setVisible(false);
						apiResponse((Map<String, Object>) returnStatus.getReturnObject());

					} catch (Exception e1) {
						transactionCancelled = true;
						setVisible(false);
						throw new JilabaException(ErrorHandling.handleError(e1));
					}
				}
			});

		} catch (Exception e) {
			JOptionPane.showMessageDialog(getContentPane(), e.getMessage());
			timerThread = null;
			payFailed = true;
			return;
		}
	}

	private void countDown() {
		try {

			int minutes = payDetails.getSwipingMachine().getAutoCancelDurationInMinutes();
			long startTime = System.currentTimeMillis();
			long endTime = startTime + (minutes * 60 * 1000);

			while (System.currentTimeMillis() < endTime) {
				// Thread.sleep(1000);
				String time = formatTime(endTime - System.currentTimeMillis());

				if (time == null || time.isEmpty())
					break;

				lblTimer.setText(time);
			}

		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	private String formatTime(long millis) {

		if (transactionCancelled) {
			timerThread.interrupt();
			timerThread = null;
			return "";
		}

		long seconds = millis / 1000;

		if (pineLabs != null)
			pineLabs.setDelayCheckNeed(true);

		if (seconds > 60) {
			long minutes = seconds / 60;
			seconds -= (minutes * 60);

			return String.format("%02d : %02d", minutes, seconds);

		}

		if (pineLabs != null && seconds <= 30)
			pineLabs.setDelayCheckNeed(false);

		return String.format("00 : %02d", seconds);
	}

	private void createControls() {
		try {

			int frameWidth = 330;
			int frameHeight = 330;
			int xPos = 50;
			int yPos = 10;
			int width = 215;
			int height = 25;
			int increment = 5;

			getContentPane().setPreferredSize(new Dimension(frameWidth, frameHeight));
			setResizable(false);
			//setUndecorated(true);
			pack();
			setTitle(payDetails.getSwipingMachine().getDeviceName());
			JilabaDesign.centerScreen(this);
			setModalityType(ModalityType.APPLICATION_MODAL);
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

			panel = new JPanel(null);
			panel.setBounds(0, 0, frameWidth, frameHeight);
			panel.setBackground(Color.WHITE);
			panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

			lblAmt = new JLabel("");
			lblAmt.setBounds(xPos, yPos, width, height);
			lblAmt.setForeground(Color.decode("#062c5e"));
			lblAmt.setHorizontalAlignment(SwingConstants.CENTER);
			lblAmt.setFont(new Font("Verdana", Font.BOLD, 22));

			yPos = lblAmt.getY() + lblAmt.getHeight() + increment;

			lblImage = new JLabel("");
			lblImage.setBounds(xPos, yPos, width, 190);
			lblImage.setOpaque(true);
			lblImage.setHorizontalAlignment(SwingConstants.CENTER);
			lblImage.setFont(lblImage.getFont().deriveFont(18.0f));
			lblImage.setBackground(Color.WHITE);

			yPos = lblImage.getY() + lblImage.getHeight() + increment;

			lblPlutusTranRefId = new JLabel("REF ID ");
			lblPlutusTranRefId.setBounds(20, yPos, 75, height);
			lblPlutusTranRefId.setForeground(Color.decode("#062c5e"));
			lblPlutusTranRefId.setHorizontalAlignment(SwingConstants.LEFT);
			lblPlutusTranRefId.setFont(new Font("Verdana", Font.BOLD, 12));
			lblPlutusTranRefId.setVisible(false);

			lblPlutusTranRefIdVal = new JLabel("");
			lblPlutusTranRefIdVal.setBounds(lblPlutusTranRefId.getX() + lblPlutusTranRefId.getWidth(), yPos, width,
					height);
			lblPlutusTranRefIdVal.setForeground(Color.RED);
			lblPlutusTranRefIdVal.setHorizontalAlignment(SwingConstants.RIGHT);
			lblPlutusTranRefIdVal.setFont(new Font("Verdana", Font.BOLD, 24));
			lblPlutusTranRefIdVal.setVisible(false);

			yPos = lblPlutusTranRefIdVal.getY() + lblPlutusTranRefIdVal.getHeight() + increment;

			lblTimerCaption = new JLabel("TIME LEFT ");
			lblTimerCaption.setBounds(20, yPos, 75, height);
			lblTimerCaption.setForeground(Color.decode("#062c5e"));
			lblTimerCaption.setHorizontalAlignment(SwingConstants.LEFT);
			lblTimerCaption.setFont(new Font("Verdana", Font.BOLD, 12));

			lblTimer = new JLabel("");
			lblTimer.setBounds(lblTimerCaption.getX() + lblTimerCaption.getWidth() + increment, yPos, width, height);
			lblTimer.setForeground(Color.decode("#32CD32"));
			lblTimer.setHorizontalAlignment(SwingConstants.CENTER);
			lblTimer.setFont(new Font("Verdana", Font.BOLD, 24));

			yPos = lblTimerCaption.getY() + lblTimerCaption.getHeight() + increment;

			btnCancel = new JButton("Cancel");
			btnCancel.setBounds(70, yPos, 185, height);
			btnCancel.setMnemonic(KeyEvent.VK_C);
			btnCancel.setForeground(Color.WHITE);
			btnCancel.setBackground(Color.decode("#062c5e"));
			btnCancel.setHorizontalAlignment(SwingConstants.CENTER);
			btnCancel.setFocusable(false);

			if (null != classLoader.getResource(IMAGEPROCESSING)) {
				URL url = classLoader.getResource(IMAGEPROCESSING);
				Icon imageIcon = new ImageIcon(url);
				lblImage.setIcon(imageIcon);
			}

			panel.add(lblAmt);
			panel.add(lblImage);
			panel.add(lblPlutusTranRefId);
			panel.add(lblPlutusTranRefIdVal);
			panel.add(lblTimerCaption);
			panel.add(lblTimer);
			panel.add(btnCancel);
			getContentPane().add(panel);
		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	@SuppressWarnings("unchecked")
	private void createEvents() {
		try {

			btnCancel.addActionListener((ActionEvent a) -> {
				try {

					ReturnStatus returnStatus = pineLabs.cancelPayment();
					CF.statusCheck(returnStatus);

					pineLabs.setInterrupted(true);
					transactionCancelled = true;
					setVisible(false);
					apiResponse((Map<String, Object>) returnStatus.getReturnObject());
					JOptionPane.showMessageDialog(getContentPane(), "Transaction Cancelled");

				} catch (Exception e) {
					if (!pineLabs.isCancelled()) {
						transactionCancelled = true;
						//setVisible(false);
					} else {
						transactionCancelled = false;
					}
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage());
				}
			});

		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	@Override
	public void apiResponse(Map<String, Object> mapResponse) {

		if (null == mapResponse)
			return;

		btnCancel.setEnabled(false);
		this.mapResponse = mapResponse;

		if (!transactionCancelled && mapResponse.get("TransactionData") == null)
			updateTransactionAutoCancelled();

		if (null != mapResponse.getOrDefault("TransactionData", null)) {

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> jsonArray = (List<Map<String, Object>>) mapResponse.get("TransactionData");

			String TID = "";
			for (Map<String, Object> m : jsonArray) {
				if (m.get("Tag").equals("TID")) {
					TID = m.get("Value").toString();
					break;
				}
			}

			updateTID(TID);
		}

		setVisible(false);
		dispose();
	}

	@Override
	public ReturnStatus saveWebTransaction() {
		Connection connection = null;
		try {

			payDetails.preparePaymentDetails();
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(payDetails.getJdbcTemplate());
			connection = payDetails.getJdbcTemplate().getDataSource().getConnection();
			simpleJdbcCall.withCatalogName(connection.getCatalog());
			simpleJdbcCall.withProcedureName(CF.SAVE_WEB_TRANSACTION);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("TranNo", payDetails.getTranNo());
			map.put("TranDate", payDetails.getTranDate());
			map.put("RefUniqueKey", payDetails.getRefUniqueKey());
			map.put("ReferenceKey", payDetails.getReferenceKey());
			map.put("PaymentStatus", payDetails.getPaymentStatus());
			map.put("ResponseCode", payDetails.getResponseCode());
			map.put("LastResponseAt", payDetails.getLastResponseAt());
			map.put("Amount", (payDetails.getAmount() / 100));
			map.put("CardCode", payDetails.getCardCode());
			map.put("IPID", payDetails.getIPID());
			map.put("PayMode", payDetails.getPayMode());
			map.put("MachineCode", payDetails.getSwipingMachine().getMachineCode());
			map.put("AutoCancelled", payDetails.getAutoCancelled());
			map.put("TID", payDetails.getTID());
			map.put("SequenceNo", payDetails.getSequenceNo());
			map.put("OrgCompanyCode", payDetails.getOrgCompanyCode());
			map.put("CompanyCode", payDetails.getCompanyCode());
			map.put("CreatedDate", payDetails.getCreatedDate());
			map.put("CreatedTime", payDetails.getCreatedTime());
			map.put("ModuleID", payDetails.getModuleId());
			simpleJdbcCall.execute(map);

			return new ReturnStatus(true, payDetails.getTranNo());
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (Exception e) {

			}
		}
	}

	@Override
	public ReturnStatus updateWebTransaction() {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" Update webtransaction Set ReferenceKey =?, ");
			sql.append(" PaymentStatus =?,ResponseCode =?,LastResponseAt =? ");
			sql.append(" Where TranNo=? And ReferenceKey=? ");

			payDetails.getJdbcTemplate().update(sql.toString(),
					new Object[] { payDetails.getReferenceKey(), payDetails.getPaymentStatus(),
							payDetails.getResponseCode(), payDetails.getLastResponseAt(), payDetails.getTranNo(),
							payDetails.getReferenceKey() });

			return new ReturnStatus(true);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	@Override
	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}

	@Override
	public ReturnStatus updateTransactionAutoCancelled() {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" Update webtransaction Set AutoCancelled =?,PaymentStatus=?, ");
			sql.append(" ResponseCode =?,LastResponseAt =? ");
			sql.append(" Where TranNo=? And ReferenceKey=? ");

			payDetails.getJdbcTemplate().update(sql.toString(), new Object[] { YesNoType.YES, PaymentStatus.CANCELED,
					//Integer.parseInt(mapResponse.get("ResponseCode").toString()),
					CF.getResponseCode(mapResponse, "ResponseCode"), LocalDateTime.now().format(CF.dateTimeFormat),
					payDetails.getTranNo(), payDetails.getReferenceKey() });

			return new ReturnStatus(true);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	private ReturnStatus updateTID(String TID) {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" Update webtransaction Set TID=? ");
			sql.append(" Where TranNo=? And ReferenceKey=? ");

			payDetails.getJdbcTemplate().update(sql.toString(),
					new Object[] { TID, payDetails.getTranNo(), payDetails.getReferenceKey() });

			return new ReturnStatus(true);
		} catch (Exception e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	public boolean isPending() {
		return isPending;
	}

	public boolean isTransactionCancelled() {
		return transactionCancelled;
	}

	public Map<String, Object> getResponse() {
		return mapResponse;
	}

	public JLabel getLblPlutusTranRefId() {
		return lblPlutusTranRefId;
	}

	public JLabel getLblPlutusTranRefIdVal() {
		return lblPlutusTranRefIdVal;
	}

	public boolean isPayFailed() {
		return payFailed;
	}

	@Override
	public void run() {
		countDown();

	}

}
