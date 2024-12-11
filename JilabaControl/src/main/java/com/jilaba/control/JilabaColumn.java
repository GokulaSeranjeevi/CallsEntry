package com.jilaba.control;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

/**
 * @author MANOJKUMAR V
 */

public class JilabaColumn {

	private String strColumnName = "";
	private Class<?> dataType;
	private int intWidth;

	private int cellAlignment;

	private List<TableColumn> columns = new ArrayList<>();

	private Boolean columnEditable;

	public JilabaColumn() {
		this.strColumnName = "";
		this.dataType = String.class;
		this.intWidth = 50;
		this.cellAlignment = SwingConstants.LEFT;
		this.columnEditable = false;
	}

	public JilabaColumn(String columnName) {
		this.strColumnName = columnName;
		this.dataType = String.class;
		this.intWidth = 50;
		this.cellAlignment = SwingConstants.LEFT;
		this.columnEditable = false;
	}

	public JilabaColumn(String columnName, Class<?> dataType) {
		this.strColumnName = columnName;
		this.dataType = dataType;
		this.intWidth = 50;
		this.cellAlignment = SwingConstants.LEFT;
		this.columnEditable = false;
	}

	public JilabaColumn(String columnName, Class<?> dataType, int width) {
		this.strColumnName = columnName;
		this.dataType = dataType;
		this.intWidth = width;
		this.cellAlignment = SwingConstants.LEFT;
		this.columnEditable = false;
	}

	public JilabaColumn(String columnName, Class<?> dataType, int width, int celAlignment) {
		this.strColumnName = columnName;
		this.dataType = dataType;
		this.intWidth = width;
		this.cellAlignment = celAlignment;
		this.columnEditable = false;
	}

	public JilabaColumn(String columnName, Class<?> dataType, int width, int celAlignment, boolean columnEditable) {
		this.strColumnName = columnName;
		this.dataType = dataType;
		this.intWidth = width;
		this.cellAlignment = celAlignment;
		this.columnEditable = columnEditable;
	}

	public void add(TableColumn column) {
		columns.add(column);
	}

	public String getColumnName() {
		return strColumnName;
	}

	public Class<?> getDataType() {
		return dataType;
	}

	public int getColumnWidth() {
		return intWidth;
	}

	public int getCellAlignment() {
		return cellAlignment;
	}

	public Boolean isColumnEditable() {
		return columnEditable;
	}

}
