package com.jilaba.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * @author JothiManikandan S
 */
public class JilabaCellAttribute implements CellAttribute, CellSpan, CellFont, ColoredCell {

	protected int rowSize;
	protected int columnSize;
	protected int[][][] span;
	protected Color[][] foreground;
	protected Color[][] background;
	protected Font[][] font;

	public JilabaCellAttribute() {
		this(1, 1);
		setSize(new Dimension(1, 1));
	}

	public JilabaCellAttribute(int numRows, int numColumns) {
		setSize(new Dimension(numColumns, numRows));
	}

	protected void initValue() {
		for (int i = 0; i < span.length; i++) {
			for (int j = 0; j < span[i].length; j++) {
				span[i][j][CellSpan.COLUMN] = 1;
				span[i][j][CellSpan.ROW] = 1;
			}
		}
	}

	//
	// CellSpan
	//
	public int[] getSpan(int row, int column) {
		if (isOutOfBounds(row, column)) {
			int[] ret_code = { 1, 1 };
			return ret_code;
		}
		return span[row][column];
	}

	public void setSpan(int[] span, int row, int column) {
		if (isOutOfBounds(row, column))
			return;
		this.span[row][column] = span;
	}

	public boolean isVisible(int row, int column) {
		if (isOutOfBounds(row, column))
			return false;
		if ((span[row][column][CellSpan.COLUMN] < 1) || (span[row][column][CellSpan.ROW] < 1))
			return false;
		return true;
	}

	public void merge(int[] rows, int[] columns) {
		if (isOutOfBounds(rows, columns))
			return;
		int rowSpan = rows.length;
		int columnSpan = columns.length;
		int startRow = rows[0];
		int startColumn = columns[0];
		for (int row = 0; row < rowSpan; row++) {
			for (int col = 0; col < columnSpan; col++) {
				if ((span[startRow + row][startColumn + col][CellSpan.COLUMN] != 1)
						|| (span[startRow + row][startColumn + col][CellSpan.ROW] != 1)) {
					return;
				}
			}
		}

		for (int row = 0, rowSpanValue = 0; row < rowSpan; row++, rowSpanValue--) {
			for (int col = 0, colSpanValue = 0; col < columnSpan; col++, colSpanValue--) {
				span[startRow + row][startColumn + col][CellSpan.ROW] = rowSpanValue;
				span[startRow + row][startColumn + col][CellSpan.COLUMN] = colSpanValue;
			}
		}

		span[startRow][startColumn][CellSpan.ROW] = rowSpan;
		span[startRow][startColumn][CellSpan.COLUMN] = columnSpan;

	}

	public void split(int row, int column) {
		if (isOutOfBounds(row, column))
			return;
		int columnSpan = span[row][column][CellSpan.COLUMN];
		int rowSpan = span[row][column][CellSpan.ROW];
		for (int i = 0; i < rowSpan; i++) {
			for (int j = 0; j < columnSpan; j++) {
				span[row + i][column + j][CellSpan.COLUMN] = 1;
				span[row + i][column + j][CellSpan.ROW] = 1;
			}
		}
	}

	//
	// ColoredCell
	//
	public Color getForeground(int row, int column) {
		if (isOutOfBounds(row, column))
			return null;
		return foreground[row][column];
	}

	public void setForeground(Color color, int row, int column) {
		if (isOutOfBounds(row, column))
			return;
		foreground[row][column] = color;
	}

	public void setForeground(Color color, int[] rows, int[] columns) {
		if (isOutOfBounds(rows, columns))
			return;
		setValues(foreground, color, rows, columns);
	}

	public Color getBackground(int row, int column) {
		if (isOutOfBounds(row, column))
			return null;
		return background[row][column];
	}

	public void setBackground(Color color, int row, int column) {
		if (isOutOfBounds(row, column))
			return;
		background[row][column] = color;
	}

	public void setBackground(Color color, int[] rows, int[] columns) {
		if (isOutOfBounds(rows, columns))
			return;
		setValues(background, color, rows, columns);
	}

	//
	// CellFont
	//
	public Font getFont(int row, int column) {
		if (isOutOfBounds(row, column))
			return null;
		return font[row][column];
	}

	public void setFont(Font font, int row, int column) {
		if (isOutOfBounds(row, column))
			return;
		this.font[row][column] = font;
	}

	public void setFont(Font font, int[] rows, int[] columns) {
		if (isOutOfBounds(rows, columns))
			return;
		setValues(this.font, font, rows, columns);
	}

	//
	// CellAttribute
	//
	public void addColumn() {
		int[][][] oldSpan = span;
		int numRows = oldSpan.length;
		int numColumns = oldSpan[0].length;
		span = new int[numRows][numColumns + 1][2];
		System.arraycopy(oldSpan, 0, span, 0, numRows);
		for (int i = 0; i < numRows; i++) {
			span[i][numColumns][CellSpan.COLUMN] = 1;
			span[i][numColumns][CellSpan.ROW] = 1;
		}
	}

	public void addRow() {
		int[][][] oldSpan = span;
		int numRows = oldSpan.length;
		int numColumns = numRows > 0 ? oldSpan[0].length : 0;
		span = new int[numRows + 1][numColumns][2];
		System.arraycopy(oldSpan, 0, span, 0, numRows);
		for (int i = 0; i < numColumns; i++) {
			span[numRows][i][CellSpan.COLUMN] = 1;
			span[numRows][i][CellSpan.ROW] = 1;
		}
		rowSize += 1;
	}

	public void insertRow(int row) {
		int[][][] oldSpan = span;
		int numRows = oldSpan.length;
		int numColumns = oldSpan[0].length;
		span = new int[numRows + 1][numColumns][2];
		if (0 < row) {
			System.arraycopy(oldSpan, 0, span, 0, row - 1);
		}
		System.arraycopy(oldSpan, 0, span, row, numRows - row);
		for (int i = 0; i < numColumns; i++) {
			span[row][i][CellSpan.COLUMN] = 1;
			span[row][i][CellSpan.ROW] = 1;
		}
		rowSize += 1;
	}

	public Dimension getSize() {
		return new Dimension(rowSize, columnSize);
	}

	public void setSize(Dimension size) {
		columnSize = size.width;
		rowSize = size.height;
		span = new int[rowSize][columnSize][2]; // 2: COLUMN,ROW
		foreground = new Color[rowSize][columnSize];
		background = new Color[rowSize][columnSize];
		font = new Font[rowSize][columnSize];
		initValue();
	}

	protected boolean isOutOfBounds(int row, int column) {
		if ((row < 0) || (rowSize <= row) || (column < 0) || (columnSize <= column)) {
			return true;
		}
		return false;
	}

	protected boolean isOutOfBounds(int[] rows, int[] columns) {
		for (int i = 0; i < rows.length; i++) {
			if ((rows[i] < 0) || (rowSize <= rows[i]))
				return true;
		}
		for (int i = 0; i < columns.length; i++) {
			if ((columns[i] < 0) || (columnSize <= columns[i]))
				return true;
		}
		return false;
	}

	protected void setValues(Object[][] target, Object value, int[] rows, int[] columns) {
		for (int i = 0; i < rows.length; i++) {
			int row = rows[i];
			for (int j = 0; j < columns.length; j++) {
				int column = columns[j];
				target[row][column] = value;
			}
		}
	}

}

interface CellAttribute {

	public void addColumn();

	public void addRow();

	public void insertRow(int row);

	public Dimension getSize();

	public void setSize(Dimension size);

}

interface CellSpan {

	public final int ROW = 0;
	public final int COLUMN = 1;

	public int[] getSpan(int row, int column);

	public void setSpan(int[] span, int row, int column);

	public boolean isVisible(int row, int column);

	public void merge(int[] rows, int[] columns);

	public void split(int row, int column);

}

interface CellFont {

	public Font getFont(int row, int column);

	public void setFont(Font font, int row, int column);

	public void setFont(Font font, int[] rows, int[] columns);

}

interface ColoredCell {

	public Color getForeground(int row, int column);

	public void setForeground(Color color, int row, int column);

	public void setForeground(Color color, int[] rows, int[] columns);

	public Color getBackground(int row, int column);

	public void setBackground(Color color, int row, int column);

	public void setBackground(Color color, int[] rows, int[] columns);

}
