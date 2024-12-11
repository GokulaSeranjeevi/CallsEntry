package com.jilaba.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class JilabaGroupableTableHeaderUI extends BasicTableHeaderUI {

	protected JilabaGroupableTableHeader getHeader() {
		return (JilabaGroupableTableHeader) header;
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		Rectangle clipBounds = g.getClipBounds();
		if (header.getColumnModel().getColumnCount() == 0) {
			return;
		}
		int column = 0;
		Dimension size = header.getSize();
		Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
		Map<JilabaColumnGroup, Rectangle> groupSizeMap = new HashMap<JilabaColumnGroup, Rectangle>();

		for (Enumeration<TableColumn> enumeration = header.getColumnModel().getColumns(); enumeration
				.hasMoreElements();) {
			cellRect.height = size.height;
			cellRect.y = 0;
			TableColumn aColumn = enumeration.nextElement();
			List<JilabaColumnGroup> groups = getHeader().getColumnGroups(aColumn);
			int groupHeight = 0;
			for (JilabaColumnGroup group : groups) {
				Rectangle groupRect = groupSizeMap.get(group);
				if (groupRect == null) {
					groupRect = new Rectangle(cellRect);
					Dimension d = group.getSize(header.getTable());
					groupRect.width = d.width;
					groupRect.height = d.height;
					groupSizeMap.put(group, groupRect);
				}
				paintCell(g, groupRect, group, c.getBackground());
				groupHeight += groupRect.height;
				cellRect.height = size.height - groupHeight;
				cellRect.y = groupHeight;
			}
			cellRect.width = aColumn.getWidth();
			if (cellRect.intersects(clipBounds)) {
				paintCell(g, cellRect, column, c.getBackground());
			}
			cellRect.x += cellRect.width;
			column++;
		}
	}

	private void paintCell(Graphics g, Rectangle cellRect, int columnIndex, Color backColor) {
		TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
		TableCellRenderer renderer = aColumn.getHeaderRenderer();
		if (renderer == null) {
			renderer = getHeader().getDefaultRenderer();
		}
		Component c = renderer.getTableCellRendererComponent(header.getTable(), aColumn.getHeaderValue(), false, false,
				-1, columnIndex);

		// c.setBackground(UIManager.getColor("TableHeader.background"));
		c.setBackground(backColor);
		rendererPane.paintComponent(g, c, header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
	}

	private void paintCell(Graphics g, Rectangle cellRect, JilabaColumnGroup cGroup, Color backColor) {
		TableCellRenderer renderer = cGroup.getHeaderRenderer();
		if (renderer == null) {
			renderer = getHeader().getDefaultRenderer();
		}

		Component component = renderer.getTableCellRendererComponent(header.getTable(), cGroup.getHeaderValue(), false,
				false, -1, -1);
		//component.setBackground(UIManager.getColor("TableHeader.background"));
		component.setBackground(backColor);
		rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y, cellRect.width, cellRect.height,
				true);
	}

	private int getHeaderHeight() {

		int headerHeight = 0;
		TableColumnModel columnModel = header.getColumnModel();
		for (int column = 0; column < columnModel.getColumnCount(); column++) {
			TableColumn aColumn = columnModel.getColumn(column);
			TableCellRenderer renderer = aColumn.getHeaderRenderer();
			if (renderer == null) {
				renderer = getHeader().getDefaultRenderer();
			}

			Component comp = renderer.getTableCellRendererComponent(header.getTable(), aColumn.getHeaderValue(), false,
					false, -1, column);
			int cHeight = comp.getPreferredSize().height;
			List<JilabaColumnGroup> groups = getHeader().getColumnGroups(aColumn);
			for (JilabaColumnGroup group : groups) {
				cHeight += group.getSize(header.getTable()).height;
			}
			headerHeight = Math.max(headerHeight, cHeight);
		}
		return headerHeight;
	}

	@Override
	public Dimension getPreferredSize(JComponent c) {
		int width = 0;
		for (Enumeration<TableColumn> enumeration = header.getColumnModel().getColumns(); enumeration
				.hasMoreElements();) {
			TableColumn aColumn = enumeration.nextElement();
			width += aColumn.getPreferredWidth();
		}
		return createHeaderSize(width);
	}

	private Dimension createHeaderSize(int width) {
		TableColumnModel columnModel = header.getColumnModel();
		width += columnModel.getColumnMargin() * columnModel.getColumnCount();
		if (width > Integer.MAX_VALUE) {
			width = Integer.MAX_VALUE;
		}
		return new Dimension(width, getHeaderHeight());
	}

}