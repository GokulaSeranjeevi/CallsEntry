package com.jilaba.control;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JList;
import javax.swing.ListModel;

public class JilabaCheckedList<E> extends JList<CheckableItem> implements KeyListener, MouseListener {

	/**
	 * @author MANOJKUMAR V
	 * @author MAHALAKSHMI V
	 * @since 2019-02-14
	 *
	 */

	private static final long serialVersionUID = 1L;

	private String selectValues = "";
	private String selectText = "";
	private List<CheckableItem> selectItem;
	private boolean checkAll = false;

	public void setCheckAll(boolean checkAll) {
		this.checkAll = checkAll;
	}

	public JilabaCheckedList() {
		super();
		addKeyListener(this);
		addMouseListener(this);
		this.setCellRenderer(new CheckListRenderer());
	}

	public JilabaCheckedList(CheckableItem[] arg0) {
		super(arg0);
		addKeyListener(this);
		addMouseListener(this);
		this.setCellRenderer(new CheckListRenderer());
	}

	@Override
	public void setListData(CheckableItem[] listData) {

		if (null != listData && listData.length > 0 && checkAll) {

			List<CheckableItem> list = new ArrayList<>();
			list.add(new CheckableItem("ALL", ""));
			list.addAll(Arrays.asList(listData));
			listData = list.toArray(new CheckableItem[listData.length + 1]);
		}

		super.setListData(listData);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (!this.isEnabled())
			return;
		if (!this.isFocusable())
			return;
		int index = this.locationToIndex(arg0.getPoint());
		checkUnCheckItem(index);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		/** This method Not used */
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		/** This method Not used */
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		/** This method Not used */
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		/** This method Not used */
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			int index = this.getSelectedIndex();
			if (index < 0)
				return;
			checkUnCheckItem(index);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		/** This method Not used */
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		/** This method Not used */
	}

	private void checkUnCheckItem(int index) {
		if (index < 0)
			return;
		CheckableItem item = this.getModel().getElementAt(index);
		item.setSelected(!item.isSelected());
		Rectangle rect = this.getCellBounds(index, index);
		this.repaint(rect);
		if (!checkAll)
			return;
		if (item.getText().equalsIgnoreCase("ALL") && item.getValue().trim().isEmpty())
			checkUnCheckList(item.isSelected());
		else
			checkUnCheckAllInList();
	}

	public void checkUnCheckList(boolean blnCheck) {
		ListModel<CheckableItem> lstChecked = (ListModel<CheckableItem>) this.getModel();
		for (int i = 0; i < lstChecked.getSize(); i++) {
			CheckableItem item = lstChecked.getElementAt(i);
			item.setSelected(blnCheck);
		}
		this.repaint();
	}

	public void checkUnCheckAllInList() {
		getSelectItem();
		this.getModel().getElementAt(0).setSelected(selectItem.size() == this.getModel().getSize() - 1);
		this.repaint(this.getCellBounds(0, 0));
	}

	public boolean isSelectedAll() {
		if (!checkAll)
			return false;
		return this.getModel().getElementAt(0).isSelected();
	}

	public List<CheckableItem> getSelectItem() {
		selectItem = new ArrayList<>();
		ListModel<CheckableItem> lstChecked = (ListModel<CheckableItem>) this.getModel();
		for (int i = 0; i < lstChecked.getSize(); i++) {
			CheckableItem item = lstChecked.getElementAt(i);
			if (item.isSelected()) {
				if (item.getText().equalsIgnoreCase("ALL") && item.getValue().trim().isEmpty())
					continue;
				selectItem.add(item);
			}
		}
		return selectItem;
	}

	public void setSelectItem(List<CheckableItem> selectItem) {
		ListModel<CheckableItem> lstChecked = (ListModel<CheckableItem>) this.getModel();
		for (int i = 0; i < lstChecked.getSize(); i++) {
			CheckableItem item = lstChecked.getElementAt(i);
			for (CheckableItem oItem : selectItem) {
				if (item.getValue().equals(oItem.getValue())) {
					item.setSelected(oItem.isSelected());
				}
			}
		}
		this.selectItem = selectItem;
	}

	/*
	 * public void setSelectValues(String selectValues) { this.selectValues =
	 * selectValues; }
	 */

	public String getSelectValues() {
		selectValues = "";
		ListModel<CheckableItem> lstChecked = this.getModel();
		for (int i = 0; i < lstChecked.getSize(); i++) {
			CheckableItem item = lstChecked.getElementAt(i);
			if (item.isSelected())
				selectValues = selectValues + item.getValue() + ",";
		}
		if (selectValues.length() > 0)
			selectValues = selectValues.substring(0, selectValues.length() - 1);
		return selectValues;
	}

	public List<Object> getSelectValuesList() {
		List<Object> listObject = new ArrayList<>();
		for (int i = 0; i < this.getModel().getSize(); i++) {
			if (this.getModel().getElementAt(i).isSelected())
				listObject.add(this.getModel().getElementAt(i).getValue());
		}
		return listObject;
	}

	public List<Object> getSelectObjectList() {
		List<Object> listObject = new ArrayList<>();
		for (int i = 0; i < this.getModel().getSize(); i++) {
			if (this.getModel().getElementAt(i).isSelected())
				listObject.add(this.getModel().getElementAt(i).getObject());
		}
		return listObject;
	}

	public List<Object> getSelectTextList() {
		List<Object> listObject = new ArrayList<>();
		for (int i = 0; i < this.getModel().getSize(); i++) {
			if (this.getModel().getElementAt(i).isSelected())
				listObject.add(this.getModel().getElementAt(i).getText());
		}
		return listObject;
	}

	/** Added By Mahalaskhmi V On 2018-12-25 **/
	public String getSelectText() {
		selectText = "";
		ListModel<CheckableItem> lstChecked = this.getModel();
		for (int i = 0; i < lstChecked.getSize(); i++) {
			CheckableItem item = lstChecked.getElementAt(i);
			if (item.isSelected())
				selectText = selectText + item.getText() + ",";
		}
		if (selectText.length() > 0)
			selectText = selectText.substring(0, selectText.length() - 1);
		return selectText;
	}

	/** Added By Mahalakshmi V **/
	public int getIndexByText(String text) {
		for (int i = 0; i < this.getModel().getSize(); i++) {
			if (this.getModel().getElementAt(i).getText().equalsIgnoreCase(text.trim()))
				return i;
		}
		return -1;
	}

}
