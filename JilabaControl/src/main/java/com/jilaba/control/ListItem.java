package com.jilaba.control;

/**
 * 
 * @author MANOJKUMAR V
 *
 */

public class ListItem {
	private Object objText = "";
	private Object objValue = "";
	
	public ListItem(Object text){
		objText = text;
		objValue = "";
	}
	public ListItem(Object text,Object value){
		objText = text;
		objValue = value;
	}
	
	public Object getText() {
		return objText;
	}
	public Object getValue() {
		return objValue;
	}
}
