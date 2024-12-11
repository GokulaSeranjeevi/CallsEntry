package com.jilaba.control;

import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;


/**
 * Jilaba Spinner Control For Date input
 * @author MANOJKUMAR V
 */

public class JilabaSpinner extends JSpinner{

	private static final long serialVersionUID = 1L;
	
	private SpinnerDateModel spinnerDateModel;
	private JSpinner.DateEditor jDateEdtitor;
	private String dateFormatString = "dd-MMM-yyyy" ;
	private SimpleDateFormat simpleDateFormat;
	public JilabaSpinner(){
		super();
		spinnerDateModel= new SpinnerDateModel();
		this.setModel(spinnerDateModel);
		
		jDateEdtitor = new JSpinner.DateEditor(this, dateFormatString);
		this.setEditor(jDateEdtitor);
		this.setValue(new Date());
	}
	public JilabaSpinner(String formatString){
		super();
		spinnerDateModel= new SpinnerDateModel();
		this.setModel(spinnerDateModel);
		dateFormatString = formatString;
		jDateEdtitor = new JSpinner.DateEditor(this, dateFormatString);
		this.setEditor(jDateEdtitor);
		this.setValue(new Date());
	}
	
	public JilabaSpinner(SpinnerModel arg0){
		super(arg0);
		jDateEdtitor = new JSpinner.DateEditor(this, dateFormatString);
		
		this.setEditor(jDateEdtitor);
		this.setValue(new Date());
	}
	public JilabaSpinner(SpinnerModel arg0,String formatString){
		super(arg0);
		dateFormatString = formatString;
		jDateEdtitor = new JSpinner.DateEditor(this, dateFormatString);
		this.setEditor(jDateEdtitor);
		this.setValue(new Date());
	}
	
	@Override
	public void setValue(Object value) {
		super.setValue(value);
	}
	public void setValue(String date) throws ParseException{
		simpleDateFormat = new SimpleDateFormat(dateFormatString);
		setValue(simpleDateFormat.parse(date));
	}
	@Override
	public Object getValue() {
		return super.getValue();
	}
	
	public String getDateValue(){
		simpleDateFormat = new SimpleDateFormat(dateFormatString);
		return simpleDateFormat.format(super.getValue());
	}
	
	public String getDateValue(String formatString){
		simpleDateFormat = new SimpleDateFormat(formatString);
		return simpleDateFormat.format(super.getValue());
	}
	
	public String getDateFormatString() {
		return dateFormatString;
	}
	
	@Override
	public void setToolTipText(String text) {
		super.setToolTipText(text);
		((JSpinner.DefaultEditor)this.getEditor()).getTextField().setToolTipText(text);
	}
	@Override
	public void requestFocus() {
		super.requestFocus();
		((JSpinner.DefaultEditor)this.getEditor()).getTextField().requestFocus();
	}
	@Override
	public synchronized void addKeyListener(KeyListener l) {
		super.addKeyListener(l);
		((JSpinner.DefaultEditor)this.getEditor()).getTextField().addKeyListener(l);
	}
	@Override
	public synchronized void addFocusListener(FocusListener arg0) {
		super.addFocusListener(arg0);
		((JSpinner.DefaultEditor)this.getEditor()).getTextField().addFocusListener(arg0);
	}
	
}
