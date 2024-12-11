package com.jilaba.control;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * Password input for jilaba
 * @author MANOJKUMAR V
 */
public class JilabaPasswordField extends JPasswordField implements KeyListener,FocusListener{

	private static final long serialVersionUID = 1L;
	
	private int maxLength = 0;
	
	public JilabaPasswordField(){
		super();
		initialize();
	}
	public JilabaPasswordField(int arg0){
		super(arg0);
		initialize();
	}
	public JilabaPasswordField(String arg0){
		super(arg0);
		initialize();
	}
	public JilabaPasswordField(String arg0,int arg1){
		super(arg0,arg1);
		initialize();
	}
	public JilabaPasswordField(Document arg0,String arg1,int arg2){
		super(arg0,arg1,arg2);
		initialize();
	}
	
	private void initialize(){
		addKeyListener(this);
		addFocusListener(this);
		((AbstractDocument)getDocument()).setDocumentFilter(new JilabaPasswordDocumentFilter(this));
		setBorder(BorderFactory.createEtchedBorder());
	}
	
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		/** This Method implemented By child class*/
	}
	@Override
	public void keyReleased(KeyEvent e) {
		/** This Method implemented By child class*/
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
		if(this.maxLength >0 && this.getPassword().length>=this.maxLength && this.getSelectedText() == null){
			e.consume();
			return;
		}
		
	}
	@Override
	public void focusGained(FocusEvent arg0) {
		this.setSelectionStart(0);
		this.setSelectionEnd(this.getPassword().length);
	}
	@Override
	public void focusLost(FocusEvent arg0) {
		this.setSelectionStart(0);
		this.setSelectionEnd(0);
	}
}
class JilabaPasswordDocumentFilter extends DocumentFilter{
	private JilabaPasswordField jilabaPasswordField;
	JilabaPasswordDocumentFilter(JilabaPasswordField textField){
		jilabaPasswordField = textField;
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
		if(jilabaPasswordField.getMaxLength()>0){
			if(offset >= jilabaPasswordField.getMaxLength()) {
				reqText = "";
			}else if(offset<jilabaPasswordField.getMaxLength()){
				intReqChar = jilabaPasswordField.getMaxLength()-offset;
				if(intReqChar<reqText.length()){
					reqText = reqText.substring(0,intReqChar);
				}
			}
		}
		super.replace(arg0, offset, length, reqText, arg4);
	}
	
}