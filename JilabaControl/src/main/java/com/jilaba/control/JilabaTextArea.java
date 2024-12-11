package com.jilaba.control;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

import com.jilaba.control.JTextFieldEnum.TextInputCase;
import com.jilaba.control.JTextFieldEnum.TextInputType;
import com.jilaba.control.JTextFieldEnum.TextSpaceReq;

/**
 * @author MANOJKUMAR V
 */
public class JilabaTextArea extends JTextArea implements KeyListener, FocusListener {

	private static final long serialVersionUID = 1L;

	private int maxLength = 0;
	private int afterDecimal = 0;
	private TextInputType textInputType = TextInputType.SYSTEM;
	private TextInputCase textInputCase = TextInputCase.SYSTEM;
	private TextSpaceReq textSpaceReq = TextSpaceReq.REQUIRED;

	private Color focusColor = Color.decode("#D6D6D6");
	private Color lostFocusColor;

	public JilabaTextArea() {
		super();
		initialize();
	}

	public JilabaTextArea(Document document) {
		super(document);
	}

	public JilabaTextArea(String text) {
		super(text);
		initialize();
	}

	public JilabaTextArea(int rows, int columns) {
		super(rows, columns);
		initialize();
	}

	public JilabaTextArea(String text, int rows, int columns) {
		super(text, rows, columns);
		initialize();
	}

	public JilabaTextArea(Document document, String text, int rows, int columns) {
		super(document, text, rows, columns);
		initialize();
	}

	private void initialize() {
		addKeyListener(this);
		addFocusListener(this);
		((AbstractDocument) getDocument()).setDocumentFilter(new JilabaTextAreaDocumentFilter(this));
		setBorder(BorderFactory.createEtchedBorder());
		lostFocusColor = this.getBackground();
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public TextInputType getTextInputType() {
		return textInputType;
	}

	public void setTextInputType(TextInputType textInputType) {
		this.textInputType = textInputType;
	}

	public TextInputCase getTextInputCase() {
		return textInputCase;
	}

	public void setTextInputCase(TextInputCase textInputCase) {
		this.textInputCase = textInputCase;
	}

	public TextSpaceReq getTextSpaceReq() {
		return textSpaceReq;
	}

	public void setTextSpaceReq(TextSpaceReq textSpaceReq) {
		this.textSpaceReq = textSpaceReq;
	}

	public Color getFocusColor() {
		return focusColor;
	}

	public void setFocusColor(Color focusColor) {
		this.focusColor = focusColor;
	}

	private boolean isSapceReq(char value) {
		boolean status = false;
		if (value == KeyEvent.VK_SPACE) {
			if (textSpaceReq == TextSpaceReq.REQUIRED) {
				status = true;
			}
		} else {
			status = true;
		}
		return status;
	}

	private boolean checkAfterDeciaml(char value) {
		int currentPosition = this.getCaretPosition();
		String afterDecimalText;
		int decimalIndex = this.getText().lastIndexOf(".");

		if (decimalIndex > -1 && (int) value == KeyEvent.VK_PERIOD)
			return false;

		if (afterDecimal == 0)
			return true;

		if (decimalIndex < 0)
			return true;

		if (currentPosition <= decimalIndex) {
			return true;
		}
		afterDecimalText = this.getText().substring(decimalIndex + 1);

		if (afterDecimalText.length() >= afterDecimal && this.getSelectedText() == null) {
			return false;
		}

		return true;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		/** This Method implemented By child class */
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		/** This Method implemented By child class */
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		if (maxLength > 0 && this.getText().length() >= maxLength && this.getSelectedText() == null) {
			arg0.consume();
			return;
		}

		if (textInputType == TextInputType.ALPHA) {
			if (!Character.isLowerCase(arg0.getKeyChar()) && !Character.isUpperCase(arg0.getKeyChar())) {
				if (arg0.getKeyChar() == KeyEvent.VK_SPACE && isSapceReq(arg0.getKeyChar())) {
					return;
				}
				arg0.consume();
			}
		} else if (textInputType == TextInputType.ALPHANUMERIC) {
			if (!Character.isLowerCase(arg0.getKeyChar()) && !Character.isUpperCase(arg0.getKeyChar())
					&& !Character.isDigit(arg0.getKeyChar()) && arg0.getKeyChar() != KeyEvent.VK_PERIOD) {
				if (arg0.getKeyChar() == KeyEvent.VK_SPACE && isSapceReq(arg0.getKeyChar())) {
					return;
				}
				arg0.consume();
			}
		} else if (textInputType == TextInputType.ALPHANUMBER) {
			if (!Character.isLowerCase(arg0.getKeyChar()) && !Character.isUpperCase(arg0.getKeyChar())
					&& !Character.isDigit(arg0.getKeyChar())) {
				if (arg0.getKeyChar() == KeyEvent.VK_SPACE && isSapceReq(arg0.getKeyChar())) {
					return;
				}
				arg0.consume();
			}
		} else if (textInputType == TextInputType.NUMBER) {
			if (!Character.isDigit(arg0.getKeyChar())) {
				arg0.consume();
				return;
			}
		} else if (textInputType == TextInputType.NUMERIC) {

			if (!checkAfterDeciaml(arg0.getKeyChar())) {
				arg0.consume();
				return;
			}

			if (!Character.isDigit(arg0.getKeyChar()) && arg0.getKeyChar() != KeyEvent.VK_PERIOD) {
				arg0.consume();
				return;
			}
		} else if (textInputType == TextInputType.SYSTEM) {
			if (!isSapceReq(arg0.getKeyChar())) {
				arg0.consume();
				return;
			}
		}

		if (textInputCase == TextInputCase.LOWER) {
			String strC = String.valueOf(arg0.getKeyChar());
			strC = strC.toLowerCase();
			char c = strC.charAt(0);
			arg0.setKeyChar(c);
		} else if (textInputCase == TextInputCase.UPPER) {
			String strC = String.valueOf(arg0.getKeyChar());
			strC = strC.toUpperCase();
			char c = strC.charAt(0);
			arg0.setKeyChar(c);
		}

	}

	@Override
	public void focusGained(FocusEvent arg0) {
		lostFocusColor = this.getBackground();
		this.setBackground(focusColor);
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		this.setBackground(lostFocusColor);
	}
}

class JilabaTextAreaDocumentFilter extends DocumentFilter {
	private JilabaTextArea jtextArea;

	JilabaTextAreaDocumentFilter(JilabaTextArea jtextArea) {
		this.jtextArea = jtextArea;
	}

	@Override
	public void insertString(FilterBypass arg0, int arg1, String arg2, AttributeSet arg3) throws BadLocationException {
		super.insertString(arg0, arg1, arg2, arg3);
	}

	@Override
	public void remove(FilterBypass arg0, int arg1, int arg2) throws BadLocationException {
		super.remove(arg0, arg1, arg2);
	}

	@Override
	public void replace(FilterBypass arg0, int offs, int length, String text, AttributeSet arg4)
			throws BadLocationException {
		int intReqChar;
		int offset = offs;
		String reqText = text;
		if (jtextArea.getMaxLength() > 0) {
			if (jtextArea.getText().length() >= jtextArea.getMaxLength()) {
				reqText = "";
			} else if (offset >= jtextArea.getMaxLength()) {
				reqText = "";
			} else if (offset < jtextArea.getMaxLength()) {

				intReqChar = (jtextArea.getMaxLength() - jtextArea.getText().length());

				if (intReqChar < reqText.length()) {
					reqText = reqText.substring(0, intReqChar);
				}
			}
		}
		super.replace(arg0, offset, length, reqText, arg4);
	}

}
