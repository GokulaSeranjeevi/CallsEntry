package com.jilaba.control;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

/**
 * @author MANOJKUMAR V
 * @author JothiManikandan S
 */
public class JilabaDefaultModelTable extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private List<String> listColumnName;
	private transient List<List<Object>> listData;
	private List<Class<?>> listClass;
	private List<Boolean> listEditable;
	private List<List<Color>> listColors;
	private JilabaCellAttribute cellAttribute;
	private List<List<Font>> listFonts;
	private List<List<Color>> listForeColor;

	public JilabaDefaultModelTable() {
		this.listColumnName = new ArrayList<>();
		this.listClass = new ArrayList<>();
		this.listData = new ArrayList<>();
		this.listEditable = new ArrayList<>();
		this.listColors = new ArrayList<>();
		this.listFonts=new ArrayList<>();
		this.listForeColor=new ArrayList<>();
		cellAttribute = new JilabaCellAttribute(0, 0);

	}

	public JilabaDefaultModelTable(List<String> listColumns) {
		this.listColumnName = listColumns;
		this.listClass = new ArrayList<>();
		this.listData = new ArrayList<>();
		this.listEditable = new ArrayList<>();
		this.listColors = new ArrayList<>();
		this.listFonts=new ArrayList<>();
		this.listForeColor=new ArrayList<>();
		cellAttribute = new JilabaCellAttribute(1, listColumnName.size());
	}

	public JilabaDefaultModelTable(List<String> listColumns, List<Class<?>> listClass) {
		this.listColumnName = listColumns;
		this.listClass = listClass;
		this.listData = new ArrayList<>();
		this.listEditable = new ArrayList<>();
		this.listColors = new ArrayList<>();
		this.listFonts=new ArrayList<>();
		this.listForeColor=new ArrayList<>();
		cellAttribute = new JilabaCellAttribute(1, listColumnName.size());
	}

	public JilabaDefaultModelTable(List<String> listColumns, List<Class<?>> listClass,
			List<Boolean> listColumnEditable) {
		this.listColumnName = listColumns;
		this.listClass = listClass;
		this.listData = new ArrayList<>();
		this.listEditable = listColumnEditable;
		this.listColors = new ArrayList<>();
		this.listFonts=new ArrayList<>();
		this.listForeColor=new ArrayList<>();
		cellAttribute = new JilabaCellAttribute(1, listColumnName.size());
	}

	public void addRow(List<Object> listObject,Font font,Color foreGround){
		if (listObject == null)
			return;

		listData.add(listObject);
		List<Color> lstC = new ArrayList<>();
		List<Font> lstF=new ArrayList<>();
		List<Color> lstFore=new ArrayList<>();
		for (int i = 0; i < listObject.size(); i++) {
			lstC.add(Color.WHITE);
			lstF.add(font);
			lstFore.add(foreGround);
		}
		listColors.add(lstC);
		listFonts.add(lstF);
		listForeColor.add(lstFore);
		cellAttribute.addRow();
		fireTableRowsInserted(listData.size() - 1, listData.size() - 1);
	}

	@Override
	public void addColumn(Object columnName) {
		listClass.add(String.class);
		listColumnName.add(String.valueOf(columnName));
		cellAttribute.addColumn();

	}

	public void addColumn(String columnName) {
		listClass.add(String.class);
		listColumnName.add(columnName);
		cellAttribute.addColumn();
	}

	public void addColumn(String strColumnName, Class<?> oClass) {
		listColumnName.add(strColumnName);
		listClass.add(oClass);
		cellAttribute.addColumn();
	}

	public void addColumn(List<String> listColumn) {
		for (String column : listColumn) {
			addColumn(column);
		}
	}

	public void setColumn(List<String> listColumn) {
		listColumnName.clear();
		listClass.clear();
		for (String column : listColumn) {
			addColumn(column);
		}
	}

	@Override
	public int getColumnCount() {
		if (listColumnName == null)
			return 0;
		return listColumnName.size();
	}

	@Override
	public String getColumnName(int column) {
		return listColumnName.get(column);
	}

	@Override
	public int getRowCount() {
		if (listData == null)
			return 0;
		return listData.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (listData.isEmpty())
			return null;
		return listData.get(row).get(column);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (listEditable.isEmpty())
			return false;
		return listEditable.get(column);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if (listData.isEmpty() || listData.size() <= row)
			return;

		listData.get(row).set(column, aValue);
		fireTableRowsUpdated(0, listData.size());
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return listClass.get(columnIndex);
	}

	@Override
	public void removeRow(int arg0) {
		if (listData.isEmpty())
			return;
		listData.remove(arg0);
		listColors.remove(arg0);
		listFonts.remove(arg0);
		listForeColor.remove(arg0);

		fireTableRowsDeleted(0, listData.size());
		fireTableDataChanged();
	}

	public void removeAll() {
		if (listData.isEmpty())
			return;
		listData.clear();
		listColors.clear();
		listFonts.clear();
		listForeColor.clear();
		cellAttribute = new JilabaCellAttribute(1, listColumnName.size());
		fireTableRowsDeleted(listData.size(), listData.size());
		fireTableDataChanged();
	}

	public List<List<Object>> getRows() {
		return listData;
	}

	public List<Object> getRow(int row) {
		if (listData.isEmpty())
			return null;
		return listData.get(row);
	}

	public Color getCellColor(int row, int column) {
		if (listColors.isEmpty())
			return null;
		return listColors.get(row).get(column);
	}

	public void setRowColor(int row, Color color) {
		List<Color> lstC = listColors.get(row);
		if (lstC == null)
			return;
		for (int i = 0; i < lstC.size(); i++) {
			lstC.set(i, color);
		}
		listColors.set(row, lstC);

		fireTableRowsUpdated(row, row);
	}
	
	public void setRowForeColor(int row,Color clr) {
		List<Color> lstc=listForeColor.get(row);
		if (lstc == null)
			return;
		for (int i = 0; i < lstc.size(); i++) {
			lstc.set(i, clr);
		}
		listForeColor.set(row, lstc);

		fireTableRowsUpdated(row, row);
	}
	
	public void setRowFont(int row, Font font) {
		List<Font> lstFont = listFonts.get(row);
		if (lstFont == null)
			return;
		for (int i = 0; i < lstFont.size(); i++) {
			lstFont.set(i, font);
		}
		listFonts.set(row, lstFont);
		fireTableRowsUpdated(row, row);
	}
	
	public void setCellColor(int row, int column, Color color) {
		if (listColors.isEmpty() || listColors.get(row) == null)
			return;

		listColors.get(row).set(column, color);
		fireTableCellUpdated(row, column);
	}
		
	public void setCellFont(int row,int column,Font font) {
		if (null==listFonts || listFonts.isEmpty() || listFonts.get(row) == null)
			return;
		/*List<Font> lstFont=new ArrayList<>();
		lstFont.add(font);
		listFonts.add(lstFont);*/
		listFonts.get(row).set(column, font);
		fireTableCellUpdated(row, column);
	}
	
	public void setCellForeColor(int row,int column,Color color) {
		if(null==listForeColor || listForeColor.isEmpty() || null==listForeColor.get(row))
			return;
		listForeColor.get(row).set(column, color);
		fireTableCellUpdated(row, column);
	}
	
	public Font getCellFont(int row,int column) {		
		if (null==listFonts || listFonts.isEmpty())
			return null;
		return listFonts.get(row).get(column);		
	}
	
	public Color getCellForeColor(int row,int column) {
		if(null==listForeColor || listForeColor.isEmpty())
			return null;
		return listForeColor.get(row).get(column);
	}
	
	public Color getRowForeColor(int row) {
		return listForeColor.get(row).get(0);
	}
	
	public List<Map<String, Object>> getRowsWithName() {
		if (listData.isEmpty())
			return null;
		List<Map<String, Object>> listMap = new ArrayList<>();

		Map<String, Object> data;

		for (List<Object> liData : listData) {
			data = new LinkedHashMap<>();
			for (int i = 0; i < liData.size(); i++) {
				data.put(listColumnName.get(i), liData.get(i));
			}
			listMap.add(data);
		}
		return listMap;
	}

	public Map<String, Object> getRowWithName(int row) {
		if (listData.isEmpty())
			return null;
		Map<String, Object> data = new LinkedHashMap<>();
		List<Object> ldata = listData.get(row);

		for (int i = 0; i < ldata.size(); i++) {
			data.put(listColumnName.get(i), ldata.get(i));
		}
		return data;
	}

	public JilabaCellAttribute getCellAttribute() {
		return cellAttribute;
	}

}