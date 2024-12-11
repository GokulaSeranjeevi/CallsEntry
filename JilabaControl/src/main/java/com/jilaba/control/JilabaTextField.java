package com.jilaba.control;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

import com.jilaba.control.JTextFieldEnum.NumericDigits;
import com.jilaba.control.JTextFieldEnum.TextInputCase;
import com.jilaba.control.JTextFieldEnum.TextInputType;
import com.jilaba.control.JTextFieldEnum.TextSpaceReq;

/**
 * 
 * @author MANOJKUMAR V
 *
 */
public class JilabaTextField extends JTextField implements KeyListener, FocusListener {
	private static final long serialVersionUID = 1L;

	private int intMaxLength = 0;
	private int afterDecimal = 0;
	private TextInputType textInputType = TextInputType.SYSTEM;
	private TextInputCase textInputCase = TextInputCase.SYSTEM;
	private TextSpaceReq textSpaceReq = TextSpaceReq.REQUIRED;
	private NumericDigits numericDigits = NumericDigits.NONE;
	private boolean selectionOnFoucs = true;

	private DecimalFormat zeroDigit = new DecimalFormat("0");
	private DecimalFormat oneDigit = new DecimalFormat("0.0");
	private DecimalFormat twoDigit = new DecimalFormat("0.00");
	private DecimalFormat threeDigit = new DecimalFormat("0.000");
	private DecimalFormat fourDigit = new DecimalFormat("0.0000");

	private Color focusColor = Color.decode("#D6D6D6");
	private Color lostFocusColor;

	private boolean assigned = false;

	public JilabaTextField() {
		super();
		initialize();
	}

	public JilabaTextField(int arg0) {
		super(arg0);
		initialize();
	}

	public JilabaTextField(String arg0) {
		super(arg0);
		initialize();
	}

	public JilabaTextField(String arg0, int arg1) {
		super(arg0, arg1);
		initialize();
	}

	public JilabaTextField(Document arg0, String arg1, int arg2) {
		super(arg0, arg1, arg2);
		initialize();
	}

	private void initialize() {
		addKeyListener(this);
		addFocusListener(this);
		((AbstractDocument) getDocument()).setDocumentFilter(new JilabaDocumentFilter(this));
		setBorder(BorderFactory.createEtchedBorder());
		lostFocusColor = this.getBackground();
	}

	public void setMaxLength(int mMaxLength) {
		this.intMaxLength = mMaxLength;
	}

	public int getMaxLength() {
		return intMaxLength;
	}

	public void setNumericDigits(NumericDigits numericDigits) {
		this.numericDigits = numericDigits;
		if (NumericDigits.NONE == numericDigits || NumericDigits.ZERO == numericDigits) {
			afterDecimal = 0;
		} else if (NumericDigits.ONE == numericDigits) {
			afterDecimal = 1;
		} else if (NumericDigits.TWO == numericDigits) {
			afterDecimal = 2;
		} else if (NumericDigits.THREE == numericDigits) {
			afterDecimal = 3;
		} else if (NumericDigits.FOUR == numericDigits) {
			afterDecimal = 4;
		}
	}

	public NumericDigits getNumericDigits() {
		return numericDigits;
	}

	public void setTextCase(TextInputCase textInputCase) {
		this.textInputCase = textInputCase;
	}

	public void setTextType(TextInputType textInputType) {
		this.textInputType = textInputType;
	}

	public TextInputCase getTextCase() {
		return textInputCase;
	}

	public TextInputType getTextType() {
		return textInputType;
	}

	public void setTextSpaceReq(TextSpaceReq textSpaceReq) {
		this.textSpaceReq = textSpaceReq;
	}

	public TextSpaceReq getTextSpaceReq() {
		return textSpaceReq;
	}

	public Color getFocusColor() {
		return focusColor;
	}

	public void setFocusColor(Color focusColor) {
		this.focusColor = focusColor;
	}

	protected boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

	public boolean isSelectionOnFoucs() {
		return selectionOnFoucs;
	}

	public void setSelectionOnFoucs(boolean selectionOnFoucs) {
		this.selectionOnFoucs = selectionOnFoucs;
	}

	public double getDouble() {
		try {
			String value = getText();
			return value == null ? 0 : Double.valueOf(value.trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public int getInt() {
		try {
			String value = getText();
			return value == null ? 0 : Integer.valueOf(value.trim());
		} catch (NumberFormatException e) {
			return 0;
		}
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
	public void keyTyped(KeyEvent e) {

		if (intMaxLength > 0 && this.getText().length() >= intMaxLength && this.getSelectedText() == null) {
			e.consume();
			return;
		}

		if (textInputType == TextInputType.ALPHA) {
			if (!Character.isLowerCase(e.getKeyChar()) && !Character.isUpperCase(e.getKeyChar())) {
				if (e.getKeyChar() == KeyEvent.VK_SPACE && isSapceReq(e.getKeyChar())) {
					return;
				}
				e.consume();
			}
		} else if (textInputType == TextInputType.ALPHANUMERIC) {
			if (!Character.isLowerCase(e.getKeyChar()) && !Character.isUpperCase(e.getKeyChar())
					&& !Character.isDigit(e.getKeyChar()) && e.getKeyChar() != KeyEvent.VK_PERIOD) {
				if (e.getKeyChar() == KeyEvent.VK_SPACE && isSapceReq(e.getKeyChar())) {
					return;
				}
				e.consume();
			}
		} else if (textInputType == TextInputType.ALPHANUMBER) {
			if (!Character.isLowerCase(e.getKeyChar()) && !Character.isUpperCase(e.getKeyChar())
					&& !Character.isDigit(e.getKeyChar())) {
				if (e.getKeyChar() == KeyEvent.VK_SPACE && isSapceReq(e.getKeyChar())) {
					return;
				}
				e.consume();
			}
		} else if (textInputType == TextInputType.NUMBER) {
			if (!Character.isDigit(e.getKeyChar())) {
				e.consume();
				return;
			}
		} else if (textInputType == TextInputType.NUMERIC) {

			if (!checkAfterDeciaml(e.getKeyChar())) {
				e.consume();
				return;
			}

			if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != KeyEvent.VK_PERIOD) {
				e.consume();
				return;
			}
		} else if (textInputType == TextInputType.SYSTEM) {
			if (!isSapceReq(e.getKeyChar())) {
				e.consume();
				return;
			}
		}

		if (textInputCase == TextInputCase.LOWER) {
			String strC = String.valueOf(e.getKeyChar());
			strC = strC.toLowerCase();
			char c = strC.charAt(0);
			e.setKeyChar(c);
		} else if (textInputCase == TextInputCase.UPPER) {
			String strC = String.valueOf(e.getKeyChar());
			strC = strC.toUpperCase();
			char c = strC.charAt(0);
			e.setKeyChar(c);
		}
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

	@Override
	public void focusGained(FocusEvent arg0) {
		if (selectionOnFoucs) {
			this.setSelectionStart(0);
			this.setSelectionEnd(this.getText().length());
		} else {
			this.setCaretPosition(this.getText().length());
		}
		lostFocusColor = this.getBackground();
		this.setBackground(focusColor);
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		try {
			if (textInputType == TextInputType.NUMERIC) {
				String strValue = this.getText().trim();
				if (strValue.isEmpty())
					strValue = "0";
				if (numericDigits == NumericDigits.ZERO) {
					this.setText(zeroDigit.format(Double.parseDouble(strValue)));
				} else if (numericDigits == NumericDigits.ONE) {
					this.setText(oneDigit.format(Double.parseDouble(strValue)));
				} else if (numericDigits == NumericDigits.TWO) {
					this.setText(twoDigit.format(Double.parseDouble(strValue)));
				} else if (numericDigits == NumericDigits.THREE) {
					this.setText(threeDigit.format(Double.parseDouble(strValue)));
				} else if (numericDigits == NumericDigits.FOUR) {
					this.setText(fourDigit.format(Double.parseDouble(strValue)));
				}
			}
			this.setSelectionStart(0);
			this.setSelectionEnd(0);
			this.setBackground(lostFocusColor);
		} catch (NumberFormatException e) {
			/** Error Not Handled */
		}

	}

	@Override
	public void setText(String t) {
		assigned = true;
		super.setText(t);
	}

}

class JilabaDocumentFilter extends DocumentFilter {
	private JilabaTextField jtextField;

	JilabaDocumentFilter(JilabaTextField textField) {
		jtextField = textField;
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
		int requiredLength;
		int position = offs;
		String inputText = text;

		if (jtextField.getMaxLength() > 0) {
			if (jtextField.isAssigned()) {
				if (jtextField.getMaxLength() < inputText.length()) {
					inputText = inputText.substring(0, jtextField.getMaxLength());
				}
				jtextField.setAssigned(false);
			} else {
				if (position == 0 && length == 0) {
					if (jtextField.getText().length() >= jtextField.getMaxLength()) {
						inputText = "";
					} else {
						requiredLength = jtextField.getMaxLength() - jtextField.getText().length();
						if (requiredLength < inputText.length())
							inputText = inputText.substring(0, requiredLength);
					}
				} else if (position == 0 && length > 0) {
					requiredLength = jtextField.getMaxLength() - (jtextField.getText().length() - length);

					if (requiredLength < inputText.length())
						inputText = inputText.substring(0, requiredLength);
				} else if (position > 0 && length == 0) {
					if (jtextField.getText().length() >= jtextField.getMaxLength()) {
						inputText = "";
					} else {
						requiredLength = jtextField.getMaxLength() - jtextField.getText().length();
						if (requiredLength < inputText.length())
							inputText = inputText.substring(0, requiredLength);
					}
				} else if (position > 0 && length > 0) {
					requiredLength = jtextField.getMaxLength() - (jtextField.getText().length() - length);

					if (requiredLength < inputText.length())
						inputText = inputText.substring(0, requiredLength);
				}
			}
		}
		if (jtextField.getTextCase() == TextInputCase.LOWER) {
			inputText = inputText.toLowerCase();
		} else if (jtextField.getTextCase() == TextInputCase.UPPER) {
			inputText = inputText.toUpperCase();
		}
		super.replace(arg0, position, length, inputText, arg4);
	}

}
