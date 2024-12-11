package com.jilaba.common;

import java.awt.Component;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;

public class PrevYearMenuDisplay {

	public static void initMenusForPrevYear(JMenuBar menuBar, List<String> prevYearAllowedMenuItems) {
		try {
			for (int i = 0; i < menuBar.getComponents().length; i++) {
				initMenusForPrevYear(menuBar.getMenu(i), prevYearAllowedMenuItems);
			}

		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	private static void initMenusForPrevYear(JMenu jilabaMenu, List<String> prevYearAllowedMenuItems) {

		int mnuCount = jilabaMenu.getItemCount();
		boolean isMenuAvailable = false;
		if (mnuCount == 0) {
			return;
		}

		JMenu menu;
		JMenuItem menuItem;
		for (int i = 0; i < mnuCount; i++) {
			Component component = jilabaMenu.getItem(i);

			if (component instanceof JMenu) {
				menu = (JMenu) component;
				initMenusForPrevYear(menu, prevYearAllowedMenuItems);
				if (menu.isVisible())
					isMenuAvailable = true;
				continue;
			}

			if (component instanceof JMenuItem) {
				menuItem = (JMenuItem) component;

				if (prevYearAllowedMenuItems.contains(menuItem.getName())) {
					isMenuAvailable = true;
					menuItem.setVisible(true);
					continue;
				}
				menuItem.setVisible(false);
			}
		}
		if (!isMenuAvailable)
			jilabaMenu.setVisible(false);

	}

}
