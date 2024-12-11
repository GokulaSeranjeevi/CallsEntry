package com.jilaba.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.TableCellRenderer;

/**
 * @author JothiManikandan S
 */
public class JilabaTableUI extends BasicTableUI {
	private boolean hasScrollPan;

	@Override
	public void paint(Graphics g, JComponent c) {
		hasScrollPan = false;
		Rectangle oldClipBounds = g.getClipBounds();
		Rectangle clipBounds = new Rectangle(oldClipBounds);
		int tableWidth = table.getColumnModel().getTotalColumnWidth();
		clipBounds.width = Math.min(clipBounds.width, tableWidth);
		g.setClip(clipBounds);

		int firstIndex = table.rowAtPoint(new Point(0, clipBounds.y));
		int lastIndex = table.getRowCount() - 1;
		Rectangle rowRect = new Rectangle(0, 0, tableWidth, table.getRowHeight() + table.getRowMargin());
		rowRect.y = firstIndex * rowRect.height;

		if (table.getParent() instanceof JViewport && table.getParent().getParent() instanceof JScrollPane) {
			((JScrollPane) table.getParent().getParent()).getVerticalScrollBar().setUnitIncrement(16);
			hasScrollPan = true;
		}

		if (firstIndex >= 0 && lastIndex >= 0)
			for (int index = firstIndex; index <= lastIndex; index++) {
				if (rowRect.intersects(clipBounds) || hasScrollPan) {
					paintRow(g, index);
				}
				rowRect.y += rowRect.height;
			}
		g.setClip(oldClipBounds);
	}

	private void paintRow(Graphics g, int row) {
		JilabaDefaultModelTable tableModel = (JilabaDefaultModelTable) table.getModel();
		CellSpan cellAttribute = (CellSpan) tableModel.getCellAttribute();

		for (int column = 0; column < table.getColumnCount(); column++) {
			Rectangle cellRect = table.getCellRect(row, column, true);

			int cellRow, cellColumn;
			// || table.getTableHeader().getColumnModel().getColumn(column).getWidth() <= 0
			if (cellAttribute.isVisible(row, column)) {
				cellRow = row;
				cellColumn = column;
			} else {
				cellRow = row + cellAttribute.getSpan(row, column)[CellSpan.ROW];
				cellColumn = column + cellAttribute.getSpan(row, column)[CellSpan.COLUMN];
			}

			if (!cellRect.intersects(g.getClipBounds()) && !hasScrollPan)
				break;

			paintCell(g, cellRect, cellRow, cellColumn);

		}
	}

	private void paintCell(Graphics g, Rectangle cellRect, int row, int column) {
		int spacingHeight = table.getRowMargin();
		int spacingWidth = table.getColumnModel().getColumnMargin();

		Color c = g.getColor();
		g.setColor(table.getGridColor());
		g.drawRect(cellRect.x, cellRect.y, cellRect.width - 1, cellRect.height - 1);
		g.setColor(c);

		cellRect.setBounds(cellRect.x + spacingWidth / 2, cellRect.y + spacingHeight / 2, cellRect.width - spacingWidth,
				cellRect.height - spacingHeight);

		if (table.isEditing() && table.getEditingRow() == row && table.getEditingColumn() == column) {

			table.getEditorComponent().setBounds(cellRect);
			table.getEditorComponent().validate();

		} else {
			TableCellRenderer renderer = table.getCellRenderer(row, column);
			Component component = table.prepareRenderer(renderer, row, column);

			if (null!=component && component.getParent() == null) {
				rendererPane.add(component);
			}
			rendererPane.paintComponent(g, component, table, cellRect.x, cellRect.y, cellRect.width, cellRect.height,
					true);
		}
	}
}