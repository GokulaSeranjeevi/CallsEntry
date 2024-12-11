package com.jilaba.control;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import com.jilaba.control.JTextFieldEnum.TextInputCase;

public class JilabaComboSearch<E> extends PlainDocument {

	private static final long serialVersionUID = 1L;
	private JComboBox<E> thisCombo;
	private transient ComboBoxModel<E> thisModel;
	private JTextComponent txtEditor;

	private boolean selecting = false;
	private TextInputCase textInputCase = TextInputCase.SYSTEM;

	public JilabaComboSearch(JComboBox<E> comboBox,TextInputCase textInputCase) {
		this.textInputCase=textInputCase;
		thisCombo = comboBox;
		thisModel = thisCombo.getModel();
		txtEditor = (JTextComponent) thisCombo.getEditor().getEditorComponent();

		comboBox.addActionListener((ActionEvent a) -> {
			if (!selecting)
				highlightCompletedText(0);
		});

		txtEditor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (thisCombo.isDisplayable())
					thisCombo.setPopupVisible(true);
				if (e.getKeyCode() == 27) {
					thisCombo.setPopupVisible(false);
				}
			}
		});

		txtEditor.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (thisCombo.isDisplayable())
					thisCombo.setPopupVisible(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				thisCombo.setPopupVisible(false);
			}
		});

		/** Handle initially selected object */
		Object selected = thisCombo.getSelectedItem();
		if (selected != null)
			this.setText(selected.toString());
		highlightCompletedText(0);
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

		int offset = offs;

		if (selecting)
			return;

		super.insertString(offset, str, a);

		Object item = lookupItem(getText(0, getLength()));

		if (item != null) {
			setSelectedItem(item);
		} else {

			if (thisCombo.getItemCount() > 0) {
				item = thisCombo.getSelectedItem();
			}

			offset = offset - str.length();

			thisCombo.getToolkit().beep();
		}
		if (item != null && !"".equals(item)) {
			if (this.textInputCase == TextInputCase.UPPER)
				item = String.valueOf(item).toUpperCase();
			else if (this.textInputCase == TextInputCase.LOWER)
				item = String.valueOf(item).toLowerCase();
			setText(item.toString());
		}

		if (thisCombo.getItemCount() > 0) {

			highlightCompletedText(offset + str.length());
		}

	}

	private void setSelectedItem(Object item) {
		selecting = true;
		thisModel.setSelectedItem(item);
		selecting = false;
	}

	private void setText(String text) {
		try {
			/** remove all text and insert the completed string **/
			super.remove(0, getLength());
			super.insertString(0, text, null);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}

	private void highlightCompletedText(int start) {

		if (txtEditor.getText().trim().length() <= 0) {

			thisModel = thisCombo.getModel();
			txtEditor = (JTextComponent) thisCombo.getEditor().getEditorComponent();

			Object selected = thisCombo.getSelectedItem();
			if (selected != null)
				setText(selected.toString());
		}
		txtEditor.setCaretPosition(getLength());
		txtEditor.moveCaretPosition(start);
	}

	private Object lookupItem(String pattern) {

		Object selectedItem = thisModel.getSelectedItem();
		/** only search for a different item if the currently selected does not match */
		if (selectedItem != null && startsWithIgnoreCase(selectedItem.toString(), pattern)) {
			return selectedItem;
		} else {
			/** iterate over all items */
			for (int i = 0, n = thisModel.getSize(); i < n; i++) {
				Object currentItem = thisModel.getElementAt(i);
				/** current item starts with the pattern? */
				if (startsWithIgnoreCase(currentItem.toString(), pattern)) {
					return currentItem;
				}
			}
		}
		/** no item starts with the pattern => return null */
		return pattern;
	}

	private boolean startsWithIgnoreCase(String str1, String str2) {
		return str1.toUpperCase().startsWith(str2.toUpperCase());
	}

}
