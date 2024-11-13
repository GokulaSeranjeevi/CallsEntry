package com.jilaba.calls.common;

import java.text.SimpleDateFormat;
import java.util.TimerTask;

import javax.swing.JLabel;

public class TimerJob extends TimerTask {

	private JLabel lblDateTime;

	public TimerJob(JLabel lblDateTime) {
		this.lblDateTime = lblDateTime;
	}

	/*public TimerJob() {
	
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateDateTime();
			}
		});
	
		timer.start();
	
	}
	
	public void updateDateTime() {
		String localDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm:ss a"));
		lblDateTime.setText(localDate);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new TimerJob());
	}*/

	@Override
	public void run() {
		lblDateTime.setText(new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a").format(System.currentTimeMillis()));
	}
}
