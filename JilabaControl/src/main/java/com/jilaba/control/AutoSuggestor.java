package com.jilaba.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AutoSuggestor {
	private final JilabaTextField textField;
	private final Window container;
	private JPanel suggestionsPanel;
	private JWindow autoSuggestionPopUpWindow;
	private String typedWord;
	private final ArrayList<String> dictionary = new ArrayList<>();
	private int currentIndexOfSpace, tW, tH;

	private DocumentListener documentListener = new DocumentListener() {
		@Override
		public void insertUpdate(DocumentEvent de) {
			checkForAndShowSuggestions();
		}

		@Override
		public void removeUpdate(DocumentEvent de) {
			checkForAndShowSuggestions();
		}

		@Override
		public void changedUpdate(DocumentEvent de) {
			checkForAndShowSuggestions();
		}

	};
	private final Font suggestionFont;
	private final Color suggestionsTextColor;
	private final Color suggestionFocusedColor;

	public AutoSuggestor(JilabaTextField textField, Window mainWindow, List<String> words, Color popUpBackground,
			Color textColor, Color suggestionFocusedColor, float opacity, Font suggestionFont) {
		this.textField = textField;
		this.suggestionsTextColor = textColor;
		this.container = mainWindow;
		this.suggestionFocusedColor = suggestionFocusedColor;
		this.textField.getDocument().addDocumentListener(documentListener);
		this.suggestionFont = suggestionFont;
		setDictionary(words);

		typedWord = "";
		currentIndexOfSpace = 0;
		tW = 0;
		tH = 0;

		autoSuggestionPopUpWindow = new JWindow(mainWindow);
		autoSuggestionPopUpWindow.setOpacity(opacity);

		suggestionsPanel = new JPanel();
		suggestionsPanel.setLayout(new GridLayout(0, 1));
		suggestionsPanel.setBackground(popUpBackground);
		suggestionsPanel.setBorder(BorderFactory.createEtchedBorder(1));

		addKeyBindingToRequestFocusInPopUpWindow();
	}

	private void addKeyBindingToRequestFocusInPopUpWindow() {
		textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true),
				"Down released");
		textField.getActionMap().put("Down released", new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent ae) {// focuses the first label on popwindow
				for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
					if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
						((SuggestionLabel) suggestionsPanel.getComponent(i)).setFocused(true);
						autoSuggestionPopUpWindow.toFront();
						autoSuggestionPopUpWindow.requestFocusInWindow();
						suggestionsPanel.requestFocusInWindow();
						suggestionsPanel.getComponent(i).requestFocusInWindow();
						break;
					}
				}
			}
		});
		suggestionsPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
		suggestionsPanel.getActionMap().put("Down released", new AbstractAction() {

			private static final long serialVersionUID = 1L;
			int lastFocusableIndex = 0;

			@Override
			public void actionPerformed(ActionEvent ae) {// allows scrolling of labels in pop window (I know very hacky
															// for now :))

				ArrayList<SuggestionLabel> sls = getAddedSuggestionLabels();
				int max = sls.size();

				if (max > 1) {// more than 1 suggestion
					for (int i = 0; i < max; i++) {
						SuggestionLabel sl = sls.get(i);
						if (sl.isFocused()) {
							if (lastFocusableIndex == max - 1) {
								lastFocusableIndex = 0;
								sl.setFocused(false);
								autoSuggestionPopUpWindow.setVisible(false);
								setFocusToTextField();
								checkForAndShowSuggestions();// fire method as if document listener change occured and
																// fired it

							} else {
								sl.setFocused(false);
								lastFocusableIndex = i;
							}
						} else if (lastFocusableIndex <= i) {
							if (i < max) {
								sl.setFocused(true);
								autoSuggestionPopUpWindow.toFront();
								autoSuggestionPopUpWindow.requestFocusInWindow();
								suggestionsPanel.requestFocusInWindow();
								suggestionsPanel.getComponent(i).requestFocusInWindow();
								lastFocusableIndex = i;
								break;
							}
						}
					}
				} else {// only a single suggestion was given
					autoSuggestionPopUpWindow.setVisible(false);
					setFocusToTextField();
					checkForAndShowSuggestions();// fire method as if document listener change occured and fired it
				}
			}
		});
	}

	private void setFocusToTextField() {
		container.toFront();
		container.requestFocusInWindow();
		textField.requestFocusInWindow();

		autoSuggestionPopUpWindow.setAutoRequestFocus(true);
	}

	public ArrayList<SuggestionLabel> getAddedSuggestionLabels() {
		ArrayList<SuggestionLabel> sls = new ArrayList<>();
		for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
			if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
				SuggestionLabel sl = (SuggestionLabel) suggestionsPanel.getComponent(i);
				sls.add(sl);
			}
		}
		return sls;
	}

	private void checkForAndShowSuggestions() {
		typedWord = getCurrentlyTypedWord();

		suggestionsPanel.removeAll();// remove previos words/jlabels that were added

		// used to calcualte size of JWindow as new Jlabels are added
		tW = 0;
		tH = 0;

		boolean added = wordTyped(typedWord);

		if (!added) {
			if (autoSuggestionPopUpWindow.isVisible()) {
				autoSuggestionPopUpWindow.setVisible(false);
			}
		} else {
			showPopUpWindow();
			setFocusToTextField();
		}
	}

	protected void addWordToSuggestions(String word) {
		SuggestionLabel suggestionLabel = new SuggestionLabel(word, suggestionFocusedColor, suggestionsTextColor, this);
		suggestionLabel.setFont(suggestionFont);

		calculatePopUpWindowSize(suggestionLabel);

		suggestionsPanel.add(suggestionLabel);
	}

	public String getCurrentlyTypedWord() {// get newest word after last white spaceif any or the first word if no white
											// spaces
		String text = textField.getText();
		String wordBeingTyped = "";
		if (text.contains(" ")) {
			int tmp = text.lastIndexOf(" ");
			if (tmp >= currentIndexOfSpace) {
				currentIndexOfSpace = tmp;
				wordBeingTyped = text.substring(text.lastIndexOf(" "));
			}
		} else {
			wordBeingTyped = text;
		}
		return wordBeingTyped.trim();
	}

	private void calculatePopUpWindowSize(JLabel label) {
		// so we can size the JWindow correctly
		if (tW < label.getPreferredSize().width) {
			tW = label.getPreferredSize().width;
		}
		tH += label.getPreferredSize().height;
	}

	private void showPopUpWindow() {
		autoSuggestionPopUpWindow.getContentPane().add(suggestionsPanel);
		autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textField.getWidth(), 30));
		autoSuggestionPopUpWindow.setSize(tW, tH);
		autoSuggestionPopUpWindow.setAutoRequestFocus(false);
		autoSuggestionPopUpWindow.setVisible(true);

		int windowX = 0;
		int windowY = 0;

		/**
		 * Commented By Mahalakshmi V On 2019-02-12 Purpose is to get the exact X & Y
		 * Position of a textfield from a screen
		 **/

		/*
		 * windowX = textField.getParent().getX() + container.getX() + textField.getX()
		 * + 5; if (suggestionsPanel.getHeight() >
		 * autoSuggestionPopUpWindow.getMinimumSize().height) { windowY =
		 * textField.getParent().getY() + container.getY() + textField.getY() +
		 * textField.getHeight() + autoSuggestionPopUpWindow.getMinimumSize().height; }
		 * else { windowY = textField.getParent().getY() + container.getY() +
		 * textField.getY() + textField.getHeight() +
		 * autoSuggestionPopUpWindow.getMinimumSize().height; }
		 */

		windowX = textField.getLocationOnScreen().x;
		windowY = textField.getLocationOnScreen().y + textField.getHeight();

		autoSuggestionPopUpWindow.setLocation(windowX, windowY);
		autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textField.getWidth(), 30));
		autoSuggestionPopUpWindow.revalidate();
		autoSuggestionPopUpWindow.repaint();
	}

	public void setDictionary(List<String> words) {
		dictionary.clear();
		if (words == null) {
			return;// so we can call constructor with null value for dictionary without exception
					// thrown
		}
		for (String word : words) {
			dictionary.add(word);
		}
	}

	public JWindow getAutoSuggestionPopUpWindow() {
		return autoSuggestionPopUpWindow;
	}

	public Window getContainer() {
		return container;
	}

	public JilabaTextField getTextField() {
		return textField;
	}

	public void addToDictionary(String word) {
		dictionary.add(word);
	}

	boolean wordTyped(String typedWord) {

		if (typedWord.isEmpty()) {
			return false;
		}
		// System.out.println("Typed word: " + typedWord);

		boolean suggestionAdded = false;

		for (String word : dictionary) {// get words in the dictionary which we added
			boolean fullymatches = true;
			for (int i = 0; i < typedWord.length(); i++) {// each string in the word

				if (typedWord.length() == i)
					break;

				if (!typedWord.toLowerCase().startsWith(String.valueOf(word.toLowerCase().charAt(i)), i)) {// check for
																											// match
					fullymatches = false;
					break;
				}
			}
			if (fullymatches) {
				addWordToSuggestions(word);
				suggestionAdded = true;
			}
		}
		return suggestionAdded;
	}
}

class SuggestionLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	private boolean focused = false;
	private final JWindow autoSuggestionsPopUpWindow;
	private final JilabaTextField textField;
	private final AutoSuggestor autoSuggestor;
	private Color suggestionsTextColor, suggestionBorderColor;

	public SuggestionLabel(String string, final Color borderColor, Color suggestionsTextColor,
			AutoSuggestor autoSuggestor) {
		super(string);

		this.suggestionsTextColor = suggestionsTextColor;
		this.autoSuggestor = autoSuggestor;
		this.textField = autoSuggestor.getTextField();
		this.suggestionBorderColor = borderColor;
		this.autoSuggestionsPopUpWindow = autoSuggestor.getAutoSuggestionPopUpWindow();

		initComponent();
	}

	private void initComponent() {
		setFocusable(true);
		setForeground(suggestionsTextColor);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				super.mouseClicked(me);

				replaceWithSuggestedText();

				autoSuggestionsPopUpWindow.setVisible(false);
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);

				if (KeyEvent.VK_ENTER != e.getKeyCode())
					return;

				replaceWithSuggestedText();

				autoSuggestionsPopUpWindow.setVisible(false);

			}
		});

		// Commented by Mahalakshmi V

		// getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
		// 0, true), "Enter released");
		// getActionMap().put("Enter released", new AbstractAction() {
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// replaceWithSuggestedText();
		// autoSuggestionsPopUpWindow.setVisible(false);
		// }
		// });
	}

	public void setFocused(boolean focused) {
		if (focused) {
			setBorder(new LineBorder(suggestionBorderColor));
		} else {
			setBorder(null);
		}
		repaint();
		this.focused = focused;
	}

	public boolean isFocused() {
		return focused;
	}

	private void replaceWithSuggestedText() {
		String suggestedWord = getText();
		String text = textField.getText();
		String typedWord = autoSuggestor.getCurrentlyTypedWord();
		String t = text.substring(0, text.lastIndexOf(typedWord));
		String tmp = t + text.substring(text.lastIndexOf(typedWord)).replace(typedWord, suggestedWord);
		textField.setText(tmp + " ");
		textField.requestFocus(); // Added by Mahalakshmi V
	}
}
