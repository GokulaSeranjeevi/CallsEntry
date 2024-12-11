package com.jilaba.control;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

/**
 * 
 * @author MANOJKUMAR V
 * @version 1.0
 * 
 */
public class CheckListRenderer extends JCheckBox implements ListCellRenderer<CheckableItem> {
	private static final long serialVersionUID = 1L;

	public CheckListRenderer() {
		setBackground(UIManager.getColor("List.textBackground"));
		setForeground(UIManager.getColor("List.textForeground"));
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends CheckableItem> arg0, CheckableItem arg1, int arg2,
			boolean arg3, boolean arg4) {
		setEnabled(arg0.isEnabled());
		setSelected(arg1.isSelected());
		setFont(arg0.getFont());
		setText(arg1.getText());
		setFocusPainted(false);

		if (arg3) {
			setBackground(arg0.getSelectionBackground());
			setForeground(arg0.getSelectionForeground());
		} else {
			setBackground(arg0.getBackground());
			setForeground(arg0.getForeground());
		}

		return this;
	}

}
