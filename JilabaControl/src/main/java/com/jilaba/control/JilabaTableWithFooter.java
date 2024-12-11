package com.jilaba.control;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * @author MANOJKUMAR V
 */
public class JilabaTableWithFooter extends JPanel {

	private static final long serialVersionUID = 1L;

	private JilabaTable jilabaTableMain;
	private JilabaTable jilabaTableFooter;
	private JScrollPane mainScrollPane;
	private JScrollPane footerScrollPane;
	private List<JilabaColumn> listColumnProperty;

	public JilabaTableWithFooter(List<JilabaColumn> listColumnProperty) {
		super();
		this.listColumnProperty = listColumnProperty;
		initialize();
	}

	private void initialize() {
		jilabaTableMain = new JilabaTable(listColumnProperty);
		jilabaTableMain.getTableHeader().setReorderingAllowed(false);
		jilabaTableMain.getTableHeader().setResizingAllowed(false);
		jilabaTableMain.setVisible(true);

		mainScrollPane = new JScrollPane(jilabaTableMain);
		mainScrollPane.setVisible(true);

		jilabaTableFooter = new JilabaTable(listColumnProperty);
		jilabaTableMain.getTableHeader().setReorderingAllowed(false);
		jilabaTableMain.getTableHeader().setResizingAllowed(false);
		jilabaTableFooter.getTableHeader().setVisible(false);
		jilabaTableFooter.setRowHeight(20);
		jilabaTableFooter.setOpaque(false);
		jilabaTableFooter.setVisible(true);

		footerScrollPane = new JScrollPane(jilabaTableFooter);
		footerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		footerScrollPane.getHorizontalScrollBar().setVisible(false);
		footerScrollPane.setBorder(BorderFactory.createEmptyBorder());
		footerScrollPane.setVisible(true);

		this.setLayout(null);

		mainScrollPane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				footerScrollPane.getHorizontalScrollBar().setValue(e.getValue());
			}
		});
		footerClear();
		add(mainScrollPane);
		add(footerScrollPane);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		setChildSize();
	}

	@Override
	public void setSize(Dimension arg0) {
		super.setSize(arg0);
		setChildSize();
	}

	@Override
	public void setSize(int arg0, int arg1) {
		super.setSize(arg0, arg1);
		setChildSize();
	}

	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		setChildSize();
	}

	private void setChildSize() {
		mainScrollPane.setBounds(0, 0, this.getWidth(), this.getHeight() - jilabaTableFooter.getRowHeight());
		footerScrollPane.setBounds(2, this.getHeight() - 40, this.getWidth(), 45);
	}

	@Override
	public void setLocation(Point p) {
		super.setLocation(p);
		setChildSize();
	}

	/*public void setJilabaColumn(List<JilabaColumn> listColumnProperty) {
		this.listColumnProperty = listColumnProperty;
		jilabaTableMain.setJilabaColumn(listColumnProperty);
		jilabaTableFooter.setJilabaColumn(listColumnProperty);
	}*/

	public void addRow(List<Object> listData) {
		this.jilabaTableMain.addRow(listData);
	}

	public void setValueAt(Object value, int row, int column) {
		this.jilabaTableMain.setValueAt(value, row, column);
	}

	public void setValueAt(Object value, int row, String strColumnName) {
		this.jilabaTableMain.setValueAt(value, row, strColumnName);
	}

	public void removeRow(int row) {
		this.jilabaTableMain.removeRow(row);
	}

	public void clear() {
		this.jilabaTableMain.clear();
		this.footerClear();
	}

	public Object getValueAt(int row, int column) {
		return this.jilabaTableMain.getValueAt(row, column);
	}

	public Object getValueAt(int row, String strColumnName) {
		return this.jilabaTableMain.getValueAt(row, strColumnName);
	}

	public List<List<Object>> getRows() {
		return this.jilabaTableMain.getRows();
	}

	public List<Object> getRow(int row) {
		return this.jilabaTableMain.getRow(row);
	}

	public List<Map<String, Object>> getRowsWithName() {
		return this.jilabaTableMain.getRowsWithName();
	}

	public Map<String, Object> getRowWithName(int row) {
		return this.jilabaTableMain.getRowWithName(row);
	}

	public void setFooterValueAt(Object value, int row, int column) {
		this.jilabaTableFooter.setValueAt(value, row, column);
	}

	public void setFooterValueAt(Object value, int row, String strColumnName) {
		this.jilabaTableFooter.setValueAt(value, row, strColumnName);
	}

	public Object getFooterValueAt(int row, int column) {
		return this.jilabaTableFooter.getValueAt(row, column);
	}

	public Object getFooterValueAt(int row, String strColumnName) {
		return this.jilabaTableFooter.getValueAt(row, strColumnName);
	}

	public void footerClear() {
		this.jilabaTableFooter.clear();
		List<Object> row = new ArrayList<>();
		for (int i = 0; i < this.jilabaTableFooter.getModel().getColumnCount(); i++) {
			row.add("");
		}

		jilabaTableFooter.addRow(row);
	}

	public TableColumnModel getColumnModel() {
		return jilabaTableMain.getColumnModel();
	}

	public JTableHeader getTableHeader() {
		return jilabaTableMain.getTableHeader();
	}

	public JilabaTable getJilabaTableMain() {
		return jilabaTableMain;
	}

	public JilabaTable getJilabaTableFooter() {
		return jilabaTableFooter;
	}

	public JScrollPane getMainScrollPane() {
		return mainScrollPane;
	}

	public JScrollPane getFooterScrollPane() {
		return footerScrollPane;
	}

}
