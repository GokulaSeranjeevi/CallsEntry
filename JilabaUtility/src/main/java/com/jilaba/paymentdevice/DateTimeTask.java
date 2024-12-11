package com.jilaba.paymentdevice;

import java.text.SimpleDateFormat;
import java.util.TimerTask;

import javax.swing.JLabel;

public class DateTimeTask extends TimerTask {

	SimpleDateFormat dateTime = new SimpleDateFormat("mm:ss");
	JLabel lblTimer;

	public DateTimeTask(JLabel lblTimer) {
		this.lblTimer = lblTimer;
	}

	@Override
	public void run() {
		try {
			lblTimer.setText(dateTime.format(System.currentTimeMillis()));
		} catch (RuntimeException e) {

		}
	}

}
