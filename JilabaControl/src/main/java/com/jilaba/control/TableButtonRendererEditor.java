package com.jilaba.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class TableButtonRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	private static final long serialVersionUID = 1L;
	private JilabaButton butRenderer;
	private JilabaButton butEdit;
	private List<Integer> bounceIndices;
	private String buttonText = "";

	public void setCustomDesign(Font font, Border border, Color pressedBgColor) {
		if (font != null) {
			butRenderer.setFont(font);
			butEdit.setFont(font);
		}

		if (border != null) {
			butRenderer.setBorder(border);
			butEdit.setBorder(border);
		}

		if (pressedBgColor != null) {
			butRenderer.setPressedBackgroundColor(pressedBgColor);
			butEdit.setPressedBackgroundColor(pressedBgColor);
		}
	}

	public void setBounceIndices(List<Integer> bounceIndices) {
		this.bounceIndices = bounceIndices;
	}

	public TableButtonRendererEditor(JilabaTable jilabaTable, int columnIndex, ActionListener actionListener,
			String buttonName) {

		buttonText = buttonName;

		butRenderer = new JilabaButton(buttonText);
		butRenderer.setBackground(Color.decode("#eed5d3"));
		butRenderer.setForeground(Color.BLACK);

		butEdit = new JilabaButton(buttonText);
		butEdit.setBackground(Color.decode("#eed5d3"));
		butEdit.setForeground(Color.BLACK);

		TableColumnModel tableColumnModel = jilabaTable.getColumnModel();
		tableColumnModel.getColumn(columnIndex).setCellRenderer(this);
		tableColumnModel.getColumn(columnIndex).setCellEditor(this);

		butEdit.addActionListener((ActionEvent a) -> {
			ActionEvent actionEvent = new ActionEvent(jilabaTable, ActionEvent.ACTION_PERFORMED, "");
			actionListener.actionPerformed(actionEvent);
		});
	}

	public TableButtonRendererEditor(JilabaTable jilabaTable, int columnIndex, ActionListener actionListener,
			String buttonName, Color backgroundColor, Color foreGroundColor) {

		buttonText = buttonName;

		butRenderer = new JilabaButton(buttonText);
		butRenderer.setBackground(backgroundColor);
		butRenderer.setForeground(foreGroundColor);

		butEdit = new JilabaButton(buttonText);
		butEdit.setBackground(backgroundColor);
		butEdit.setForeground(foreGroundColor);

		TableColumnModel tableColumnModel = jilabaTable.getColumnModel();
		tableColumnModel.getColumn(columnIndex).setCellRenderer(this);
		tableColumnModel.getColumn(columnIndex).setCellEditor(this);

		butEdit.addActionListener((ActionEvent a) -> {
			ActionEvent actionEvent = new ActionEvent(jilabaTable, ActionEvent.ACTION_PERFORMED, "");
			actionListener.actionPerformed(actionEvent);
		});
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (bounceIndices != null) {
			if (bounceIndices.contains(row))
				return null;
		}

		if (null == buttonText || buttonText.isEmpty())
			butRenderer.setText(table.getColumnName(column));

		return butRenderer;
	}

	@Override
	public Object getCellEditorValue() {

		return "";
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (bounceIndices != null) {
			if (bounceIndices.contains(row))
				return null;
		}

		if (null == buttonText || buttonText.isEmpty())
			butEdit.setText(table.getColumnName(column));

		return butEdit;
	}

}
