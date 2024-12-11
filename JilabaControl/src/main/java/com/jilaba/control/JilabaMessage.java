package com.jilaba.control;

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * 
 * @author MANOJKUMAR V
 *
 */

public class JilabaMessage {
	
	public static void showMessageDialog(Component parentComponent, Object message) {
		JOptionPane pane = new JOptionPane(message , JOptionPane.INFORMATION_MESSAGE);
		setForwardKey(pane);
		setBackwardKey(pane);
		JDialog dialog = pane.createDialog(parentComponent, "Message");
		dialog.setVisible(true);
	}
	public static void showMessageDialog(Component parentComponent, Object message, String title , int messageType) {
		JOptionPane pane = new JOptionPane(message , messageType);
		setForwardKey(pane);
		setBackwardKey(pane);
		JDialog dialog = pane.createDialog(parentComponent, title);
		dialog.setVisible(true);
	}
	public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType, Icon icon) {
		JOptionPane pane = new JOptionPane(message, messageType);
		pane.setIcon(icon);
		setForwardKey(pane);
		setBackwardKey(pane);
		JDialog dialog = pane.createDialog(parentComponent, title);
		dialog.setVisible(true);
	}
	public static int showConfirmDialog(Component parentComponent, Object message) {
		JOptionPane pane = new JOptionPane(message , JOptionPane.QUESTION_MESSAGE , JOptionPane.YES_NO_CANCEL_OPTION);
		setForwardKey(pane);
		setBackwardKey(pane);
		JDialog dialog = pane.createDialog(parentComponent, "Select an Option");
		dialog.setVisible(true);
		if (pane.getValue() instanceof Integer)
			return ((Integer) pane.getValue()).intValue();
		return -1;
	}
	public static int showConfirmDialog(Component parentComponent, Object message, String title , int optionType) {
		JOptionPane pane = new JOptionPane(message , JOptionPane.QUESTION_MESSAGE , optionType);
		setForwardKey(pane);
		setBackwardKey(pane);
		JDialog dialog = pane.createDialog(parentComponent, title);
		dialog.setVisible(true);
		if (pane.getValue() instanceof Integer)
			return ((Integer) pane.getValue()).intValue();
		return -1;
	}
	public static int showConfirmDialog(Component parentComponent, Object message, String title , int optionType ,int messageType) {
		JOptionPane pane = new JOptionPane(message , messageType , optionType);
		setForwardKey(pane);
		setBackwardKey(pane);
		JDialog dialog = pane.createDialog(parentComponent, title);
		dialog.setVisible(true);
		if (pane.getValue() instanceof Integer)
			return ((Integer) pane.getValue()).intValue();
		return -1;
	}
	public static int showConfirmDialog(Component parentComponent, Object message, String title , int optionType ,int messageType ,Icon icon) {
		JOptionPane pane = new JOptionPane(message, messageType, optionType, icon);
		setForwardKey(pane);
		setBackwardKey(pane);
		JDialog dialog = pane.createDialog(parentComponent, title);
		dialog.setVisible(true);
		if (pane.getValue() instanceof Integer)
			return ((Integer) pane.getValue()).intValue();
		return -1;
	}
	
	public static int showOptionDialog(Component parentComponent, Object message, String title,
			int optionType, int messageType, Icon icon, Object[] options, Object initialValue) {
		
		JOptionPane pane = new JOptionPane(message, messageType, optionType, icon, options, initialValue);
		setForwardKey(pane);
		setBackwardKey(pane);
		JDialog dialog = pane.createDialog(parentComponent, title);
		dialog.setVisible(true);

		if (pane.getValue() instanceof Integer)
			return ((Integer) pane.getValue()).intValue();
		else if(pane.getValue() instanceof Object){
			for(int i=0;i< options.length;i++) {
				if( options[i].equals(pane.getValue()))
					return i;
			}
			return -1;
		}
			
		return -1;
	}
	
	
	private static void setForwardKey(JOptionPane pane){
		Set<AWTKeyStroke> set = new HashSet<>(pane.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
		set.add(KeyStroke.getKeyStroke("RIGHT"));
		set.add(KeyStroke.getKeyStroke("UP"));
		pane.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
	}
	
	private static void setBackwardKey(JOptionPane pane){
		Set<AWTKeyStroke> set = new HashSet<>(pane.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
		set.add(KeyStroke.getKeyStroke("LEFT"));
		set.add(KeyStroke.getKeyStroke("DOWN"));
		pane.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, set);
	}
}
