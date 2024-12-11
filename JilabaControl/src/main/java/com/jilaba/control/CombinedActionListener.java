package com.jilaba.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CombinedActionListener implements ActionListener{
	private ActionListener actionListener1;
	private ActionListener actionListener2;
	
	public CombinedActionListener(ActionListener actionListener1,ActionListener actionListener2){
		super();
		this.actionListener1 = actionListener1;
		this.actionListener2 = actionListener2;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.actionListener1.actionPerformed(arg0);
		this.actionListener2.actionPerformed(arg0);
	}
}
