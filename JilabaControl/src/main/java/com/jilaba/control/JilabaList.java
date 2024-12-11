package com.jilaba.control;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.ListModel;

/**
 * Jilaba JList Component
 * 
 * @author MANOJKUMAR V
 */
public class JilabaList<E> extends JList<E> {

	private static final long serialVersionUID = 1L;
	private List<ListItem> listItem = new ArrayList<>();

	public JilabaList() {
		super();
	}

	public JilabaList(E[] arg0) {
		super(arg0);
	}

	public JilabaList(ListModel<E> arg0) {
		super(arg0);
	}

	@SuppressWarnings("unchecked")
	public JilabaList(List<ListItem> arg0) {
		super();
		listItem = arg0;
		if (listItem != null) {
			String strVal[] = new String[listItem.size()];
			for (ListItem lItem : listItem) {
				strVal[listItem.indexOf(lItem)] = String.valueOf(lItem.getText());
			}
			setListData((E[]) strVal);
		}
	}

	@Override
	public E getSelectedValue() {
		return super.getSelectedValue();
	}

	public Object getSelectItemValue() {
		if (listItem == null)
			return null;
		if (this.getSelectedIndex() < 0)
			return null;
		return listItem.get(this.getSelectedIndex()).getValue();
	}

	@Override
	public List<E> getSelectedValuesList() {
		return super.getSelectedValuesList();
	}

	public List<ListItem> getSelectItemValuesList() {

		if (listItem == null)
			return null;

		if (this.getSelectedIndices().length == 0)
			return null;

		List<ListItem> lstI = new ArrayList<>();
		for (int i = 0; i < this.getSelectedIndices().length; i++) {
			lstI.add(listItem.get(this.getSelectedIndices()[i]));
		}
		return lstI;
	}

	@SuppressWarnings("unchecked")
	public void setListData(List<ListItem> arg0) {
		listItem = arg0;
		if (listItem != null) {
			String strVal[] = new String[listItem.size()];
			for (ListItem lItem : listItem) {
				strVal[listItem.indexOf(lItem)] = String.valueOf(lItem.getText());
			}
			super.setListData((E[]) strVal);
		}
	}

	/**
	 * @author Mahalashmi V
	 * @since 2018-12-28
	 */
	public List<ListItem> getListData() {
		return listItem;
	}
}
