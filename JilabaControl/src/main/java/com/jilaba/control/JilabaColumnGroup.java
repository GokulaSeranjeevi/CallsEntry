package com.jilaba.control;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class JilabaColumnGroup {

    protected TableCellRenderer renderer;

    protected List<TableColumn> columns;
    protected List<JilabaColumnGroup> groups;

    protected String text;
    protected int margin = 0;

    public JilabaColumnGroup(String text) {
        this(text, null);
    }

    public JilabaColumnGroup(String text, TableCellRenderer renderer) {
        this.text = text;
        this.renderer = renderer;
        this.columns = new ArrayList<TableColumn>();
        this.groups = new ArrayList<JilabaColumnGroup>();
    }

    public void add(TableColumn column) {
        columns.add(column);
    }

    public void add(JilabaColumnGroup group) {
        groups.add(group);
    }

    /**
     * @param column
     *            TableColumn
     */
    public List<JilabaColumnGroup> getColumnGroups(TableColumn column) {
        if (!contains(column)) {
            return Collections.emptyList();
        }
        List<JilabaColumnGroup> result = new ArrayList<JilabaColumnGroup>();
        result.add(this);
        if (columns.contains(column)) {
            return result;
        }
        for (JilabaColumnGroup columnGroup : groups) {
            result.addAll(columnGroup.getColumnGroups(column));
        }
        return result;
    }

    private boolean contains(TableColumn column) {
        if (columns.contains(column)) {
            return true;
        }
        for (JilabaColumnGroup group : groups) {
            if (group.contains(column)) {
                return true;
            }
        }
        return false;
    }

    public TableCellRenderer getHeaderRenderer() {
        return renderer;
    }

    public void setHeaderRenderer(TableCellRenderer renderer) {
        this.renderer = renderer;
    }

    public String getHeaderValue() {
        return text;
    }

    public Dimension getSize(JTable table) {
        TableCellRenderer renderer = this.renderer;
        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(table, getHeaderValue() == null || getHeaderValue().trim().isEmpty() ? " "
                : getHeaderValue(), false, false, -1, -1);
        int height = comp.getPreferredSize().height;
        int width = 0;
        for (JilabaColumnGroup columnGroup : groups) {
            width += columnGroup.getSize(table).width;
        }
        for (TableColumn tableColumn : columns) {
            width += tableColumn.getWidth();
            width += margin;
        }
        return new Dimension(width, height);
    }

    public void setColumnMargin(int margin) {
        this.margin = margin;
        for (JilabaColumnGroup columnGroup : groups) {
            columnGroup.setColumnMargin(margin);
        }
    }

}