package com.jilaba.control;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.text.JTextComponent;

import com.jilaba.control.JTextFieldEnum.TextInputCase;

/**
 * @author MANOJKUMAR V
 */
public class JilabaComboBox<E> extends JComboBox<E> {

	private static final long serialVersionUID = 1L;

	private JTextComponent txtEditor;
	private List<ListItem> listItem = new ArrayList<>();
	private TextInputCase textInputCase = TextInputCase.SYSTEM;

	public JilabaComboBox() {
		super();
		txtEditor = (JTextComponent) this.getEditor().getEditorComponent();
		txtEditor.setDocument(new JilabaComboSearch<E>(this, this.textInputCase));
	}

	public JilabaComboBox(TextInputCase textInputCase) {
		super();
		this.textInputCase=textInputCase;
		txtEditor = (JTextComponent) this.getEditor().getEditorComponent();
		txtEditor.setDocument(new JilabaComboSearch<E>(this, this.textInputCase));
	}
	
	public JilabaComboBox(E[] arg0) {
		super(arg0);
		txtEditor = (JTextComponent) this.getEditor().getEditorComponent();
		txtEditor.setDocument(new JilabaComboSearch<E>(this, this.textInputCase));
	}

	public JilabaComboBox(ComboBoxModel<E> arg0) {
		super(arg0);
		txtEditor = (JTextComponent) this.getEditor().getEditorComponent();
		txtEditor.setDocument(new JilabaComboSearch<E>(this, this.textInputCase));
	}

	@SuppressWarnings("unchecked")
	public void setListItem(List<ListItem> mListItem) {
		this.removeAllItems();
		listItem.clear();
		listItem = mListItem;
		if (listItem != null) {
			for (ListItem item : listItem) {
				addItem((E) item.getText());
			}
		}
	}

	public List<ListItem> getListItem() {
		return listItem;
	}

	@SuppressWarnings("unchecked")
	public void addListItem(ListItem mListItem) {
		if (listItem == null || listItem.isEmpty())
			listItem = new ArrayList<>();
		if (mListItem != null) {
			listItem.add(mListItem);
			addItem((E) mListItem.getText());
		}
	}

	public ListItem getListItem(int position) throws ArrayIndexOutOfBoundsException {
		try {
			if (listItem == null || listItem.isEmpty())
				return null;
			return listItem.get(position);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw e;
		}
	}

	public Object getSelectedItemValue() throws ArrayIndexOutOfBoundsException {
		try {
			if (listItem == null || listItem.isEmpty())
				return null;
			if (getSelectedIndex() < 0)
				return null;
			return listItem.get(getSelectedIndex()).getValue();
		} catch (ArrayIndexOutOfBoundsException e) {
			throw e;
		}
	}

	public boolean setSelectedItemValue(Object selectedValue) throws ArrayIndexOutOfBoundsException {
		try {
			if (listItem == null || listItem.isEmpty())
				return false;
			for (ListItem item : listItem) {
				if (!item.getValue().equals(selectedValue))
					continue;
				setSelectedIndex(listItem.indexOf(item));
				break;
			}
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw e;
		}
	}

	public void selectionBackgroudColor(Color color) {
		BasicComboPopup popup = (BasicComboPopup) this.getAccessibleContext().getAccessibleChild(0);
		popup.getList().setSelectionBackground(color);
	}

	@Override
	public void removeAllItems() {
		super.removeAllItems();
		listItem.clear();
	}

	@Override
	public void removeItem(Object arg0) {
		super.removeItem(arg0);
		listItem.remove(arg0);
	}

	@Override
	public void removeItemAt(int arg0) {

		super.removeItemAt(arg0);
		listItem.remove(arg0);
	}

}
