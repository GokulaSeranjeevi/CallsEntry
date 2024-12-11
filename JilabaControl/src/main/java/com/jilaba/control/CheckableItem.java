package com.jilaba.control;

import java.io.Serializable;

/**
 * 
 * @author MANOJKUMAR V
 * @version 1.0 
 *	
 */
public class CheckableItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String value;
	private String text;
	private boolean selected;
	private Object object;

	public CheckableItem(String text) {
		this.text = text;
		this.selected = false;
	}

	public CheckableItem(String text, String value) {
		this.text = text;
		this.value = value;
		this.selected = false;
	}

	public CheckableItem(String text, Object object) {
		this.text = text;
		this.object = object;
		this.selected = false;
	}

	public CheckableItem(String text, Object object, boolean selected) {
		this.text = text;
		this.object = value;
		this.selected = selected;
	}

	public CheckableItem(String text, String value, boolean selected) {
		this.text = text;
		this.value = value;
		this.selected = selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public String getText() {
		return text;
	}

	public String getValue() {
		return value;
	}

	public Object getObject() {
		return object;
	}

}
