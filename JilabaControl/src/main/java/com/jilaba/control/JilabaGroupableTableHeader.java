package com.jilaba.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class JilabaGroupableTableHeader extends JTableHeader {

	@SuppressWarnings("unused")
	private static final String uiClassID = "GroupableTableHeaderUI";

	protected List<JilabaColumnGroup> columnGroups = new ArrayList<JilabaColumnGroup>();

	public JilabaGroupableTableHeader(TableColumnModel model) {
		super(model);
		setUI(new JilabaGroupableTableHeaderUI());
		setReorderingAllowed(false);
		// setDefaultRenderer(new MultiLineHeaderRenderer());
	}

	@Override
	public void updateUI() {
		setUI(new JilabaGroupableTableHeaderUI());
	}

	@Override
	public void setReorderingAllowed(boolean b) {
		super.setReorderingAllowed(false);
	}

	public void addColumnGroup(JilabaColumnGroup g) {
		columnGroups.add(g);
	}

	public List<JilabaColumnGroup> getColumnGroups(TableColumn col) {
		for (JilabaColumnGroup group : columnGroups) {
			List<JilabaColumnGroup> groups = group.getColumnGroups(col);
			if (!groups.isEmpty()) {
				return groups;
			}
		}
		return Collections.emptyList();
	}

	public void setColumnMargin() {
		int columnMargin = getColumnModel().getColumnMargin();
		for (JilabaColumnGroup group : columnGroups) {
			group.setColumnMargin(columnMargin);
		}
	}

}