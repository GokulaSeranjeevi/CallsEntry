package com.jilaba.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * 
 * @author MANOJKUMAR V
 * @author JothiManikandan S
 */
public class JilabaTable extends JTable {
	private static final long serialVersionUID = 1L;

	private List<JilabaColumn> listColumn = new ArrayList<>();
	private List<String> listColumnName = new ArrayList<>();
	private List<Class<?>> listDataType = new ArrayList<>();
	private List<Boolean> listColumnEditable = new ArrayList<>();

	private JilabaDefaultModelTable tableModel;
	private JilabaTableCellRenderer cellRenderer;

	public JilabaTable() {
		super();
		initialize(this.listColumn);
	}

	/**
	 * @param tableModel
	 */
	public JilabaTable(TableModel tableModel) {
		super(tableModel);
		initialize(this.listColumn);
	}

	/**
	 * @param numRows
	 *            the number of rows the table holds
	 * @param numColumns
	 *            the number of columns the table holds
	 */
	public JilabaTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		initialize(this.listColumn);
	}

	/**
	 * @param rowData
	 *            the data for the new table
	 * @param columnNames
	 *            names of each column
	 */
	public JilabaTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		initialize(this.listColumn);
	}

	/**
	 * @param tm
	 *            the data model for the table
	 * @param cm
	 *            the column model for the table
	 */
	public JilabaTable(TableModel tm, TableColumnModel cm) {
		super(tm, cm);
		initialize(this.listColumn);
	}

	/**
	 * @param tm
	 *            the data model for the table
	 * @param cm
	 *            the column model for the table
	 * @param sm
	 *            the row selection model for the table
	 */
	public JilabaTable(TableModel tm, TableColumnModel cm, ListSelectionModel sm) {
		super(tm, cm, sm);
		initialize(this.listColumn);
	}

	public JilabaTable(List<JilabaColumn> listColumnProperty) {
		super();
		this.listColumn = listColumnProperty;
		initialize(listColumnProperty);
	}

	public void initialize(List<JilabaColumn> listColumnProperty) {

		this.listColumn = listColumnProperty;
		this.listDataType = new ArrayList<>();
		this.listColumnName = new ArrayList<>();
		this.listColumnEditable = new ArrayList<>();
		for (JilabaColumn jColumn : this.listColumn) {
			this.listDataType.add(jColumn.getDataType());
			this.listColumnName.add(jColumn.getColumnName());
			this.listColumnEditable.add(jColumn.isColumnEditable());
		}
		this.tableModel = new JilabaDefaultModelTable(this.listColumnName, this.listDataType, this.listColumnEditable);

		setModel(this.tableModel);
		setUI(new JilabaTableUI());

		TableColumn tableColumn;
		for (JilabaColumn o : this.listColumn) {
			tableColumn = getColumnModel().getColumn(this.listColumn.indexOf(o));
			if (o.getColumnWidth() == 0) {
				tableColumn.setMinWidth(o.getColumnWidth());
			}
			tableColumn.setPreferredWidth(o.getColumnWidth());
			this.cellRenderer = new JilabaTableCellRenderer();
			this.cellRenderer.setHorizontalAlignment(o.getCellAlignment());
			if (o.getDataType() != Boolean.class) {
				tableColumn.setCellRenderer(this.cellRenderer);
			}
		}
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setBackground(Color.LIGHT_GRAY);
	}

	public void columnSelectionChanged(ListSelectionEvent e) {
		repaint();
	}

	public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
		Rectangle sRect = super.getCellRect(row, column, includeSpacing);
		if ((row < 0) || (column < 0) || (getRowCount() <= row) || (getColumnCount() <= column)) {
			return sRect;
		}
		CellSpan cellAttribute = (CellSpan) ((JilabaDefaultModelTable) getModel()).getCellAttribute();
		if (!cellAttribute.isVisible(row, column)) {
			int temp_row = row;
			int temp_column = column;
			row += cellAttribute.getSpan(temp_row, temp_column)[CellSpan.ROW];
			column += cellAttribute.getSpan(temp_row, temp_column)[CellSpan.COLUMN];
		}
		int[] span = cellAttribute.getSpan(row, column);
		int index = 0;
		// int columnMargin = getColumnModel().getColumnMargin();
		Rectangle rect = new Rectangle();
		int cellHeight = rowHeight + rowMargin;

		rect.y = row * cellHeight;
		rect.height = span[CellSpan.ROW] * cellHeight;

		@SuppressWarnings("rawtypes")
		Enumeration enumeration = getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn tableColumn = (TableColumn) enumeration.nextElement();
			rect.width = tableColumn.getWidth();
			if (index == column)
				break;

			rect.x += rect.width;
			index++;
		}
		for (int i = 0; i < span[CellSpan.COLUMN] - 1; i++) {
			TableColumn spannedColumn = (TableColumn) enumeration.nextElement();
			rect.width += spannedColumn.getWidth();
		}

		if (!includeSpacing) {
			Dimension s = getIntercellSpacing();
			rect.setBounds(rect.x + s.width / 2, rect.y + s.height / 2, rect.width - s.width, rect.height - s.height);
		}
		return rect;
	}

	private int[] rowColumnAtPoint(Point point) {
		int[] retValue = { -1, -1 };
		int row = point.y / (rowHeight + rowMargin);
		if ((row < 0) || (getRowCount() <= row))
			return retValue;
		int column = getColumnModel().getColumnIndexAtX(point.x);

		CellSpan cellAttribute = (CellSpan) ((JilabaDefaultModelTable) getModel()).getCellAttribute();

		if (cellAttribute.isVisible(row, column)) {
			retValue[CellSpan.COLUMN] = column;
			retValue[CellSpan.ROW] = row;
			return retValue;
		}
		retValue[CellSpan.COLUMN] = column + cellAttribute.getSpan(row, column)[CellSpan.COLUMN];
		retValue[CellSpan.ROW] = row + cellAttribute.getSpan(row, column)[CellSpan.ROW];
		return retValue;

	}

	public int rowAtPoint(Point point) {
		return rowColumnAtPoint(point)[CellSpan.ROW];
	}

	public int columnAtPoint(Point point) {
		return rowColumnAtPoint(point)[CellSpan.COLUMN];
	}

	public void valueChanged(ListSelectionEvent e) {
		// super.valueChanged(e);
		int firstIndex = e.getFirstIndex();
		int lastIndex = e.getLastIndex();
		if (firstIndex == -1 && lastIndex == -1) {
			repaint();
		}
		Rectangle dirtyRegion = getCellRect(firstIndex, 0, false);
		int numCoumns = getColumnCount();
		for (int i = 0; i < numCoumns; i++) {
			dirtyRegion.add(getCellRect(firstIndex, i, false));
		}
		for (int i = 0; i < numCoumns; i++) {
			dirtyRegion.add(getCellRect(lastIndex, i, false));
		}
		repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
	}

	public void rowSpan(int[] rows, int[] columns) {
		tableModel.getCellAttribute().merge(rows, columns);
	}

	public void colSpan(int[] rows, int[] columns) {
		tableModel.getCellAttribute().merge(rows, columns);
	}

	public boolean isSpanned(int row, int column) {
		if (getRowCount() < row || getColumnCount() < column)
			return false;
		return tableHeader.getHeaderRect(column).width != this.getColumnModel().getColumn(column).getWidth();
	}

	public void colSpan(int row, int fromCol, int toColumn) {
		int rows[] = { row };
		int columns[] = new int[toColumn - fromCol];
		int i = 0;
		for (int col = fromCol; col < toColumn; col++)
			columns[i++] = col;
		colSpan(rows, columns);
	}

	public void rowSpan(int column, int fromRow, int toRow) {
		int columns[] = { column };
		int rows[] = new int[toRow - fromRow];
		int i = 0;
		for (int row = fromRow; row < toRow; row++)
			rows[i++] = row;
		rowSpan(rows, columns);
	}

	@Override
	protected JTableHeader createDefaultTableHeader() {
		return new JilabaGroupableTableHeader(columnModel);
	}

	public void setJilabaColumn(List<JilabaColumn> listColumn) {
		this.listColumn = listColumn;
		initialize(this.listColumn);
	}

	public void addRow(List<Object> listData) {
		this.tableModel.addRow(listData, this.getFont(), this.getForeground());
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		this.tableModel.setValueAt(value, row, column);
		// this.changeSelection(row, column, false, false);
	}

	public void setValueAt(Object value, int row, String strColumnName) {
		int column = this.tableModel.findColumn(strColumnName);
		this.tableModel.setValueAt(value, row, column);
		// this.changeSelection(row, column, false, false);
	}

	public void removeRow(int row) {
		this.tableModel.removeRow(row);
	}

	public void clear() {
		this.tableModel.removeAll();
	}

	@Override
	public Object getValueAt(int row, int column) {
		return this.tableModel.getValueAt(row, column);
	}

	public Object getValueAt(int arg0, String strColumnName) {
		int column = this.tableModel.findColumn(strColumnName);
		return this.tableModel.getValueAt(arg0, column);
	}

	public List<List<Object>> getRows() {
		return this.tableModel.getRows();
	}

	public List<Object> getRow(int row) {
		return this.tableModel.getRow(row);
	}

	public List<Map<String, Object>> getRowsWithName() {
		return this.tableModel.getRowsWithName();
	}

	public Map<String, Object> getRowWithName(int row) {
		return this.tableModel.getRowWithName(row);
	}

	public void setRowColor(int row, Color color) {
		this.tableModel.setRowColor(row, color);
	}

	public void setRowFont(int row, Font font) {
		this.tableModel.setRowFont(row, font);
	}

	public void setCellColor(int row, int column, Color color) {
		this.tableModel.setCellColor(row, column, color);
	}

	public void setCellFont(int row, int column, Font font) {
		this.tableModel.setCellFont(row, column, font);
	}

	public void setCellForeColor(int row, int column, Color color) {
		this.tableModel.setCellForeColor(row, column, color);
	}

	public void setRowForeColor(int row, Color clr) {
		this.tableModel.setRowForeColor(row, clr);
	}

}

class JilabaTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JilabaDefaultModelTable jilabaDefaultModelTable = (JilabaDefaultModelTable) table.getModel();
		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (jilabaDefaultModelTable.getCellColor(row, column) != null && !isSelected)
			component.setBackground(jilabaDefaultModelTable.getCellColor(row, column));

		// ((JComponent) component).setBorder(new EmptyBorder(2, 2, 2, 2));

		if (jilabaDefaultModelTable.getCellFont(row, column) != null && !isSelected)
			component.setFont(jilabaDefaultModelTable.getCellFont(row, column));

		if (jilabaDefaultModelTable.getCellForeColor(row, column) != null && !isSelected)
			component.setForeground(jilabaDefaultModelTable.getCellForeColor(row, column));

		return component;
	}

}
