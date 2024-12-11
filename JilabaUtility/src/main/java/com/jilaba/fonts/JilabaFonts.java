package com.jilaba.fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.jilaba.exception.JilabaException;

public class JilabaFonts {
	private Font fontcalibri;
	private Font fontcalibrib;

	public enum FontStyle {
		PLAIN, BOLD;
	}

	public enum FontFamily {
		CALIBRI, TIMES_NEW_ROMAN;
	}

	private Logger logger = Logger.getLogger(JilabaFonts.class);

	public JilabaFonts() {
		InputStream inputStream = JilabaFonts.class.getClassLoader().getResourceAsStream("calibri.ttf");
		InputStream inputStreamb = JilabaFonts.class.getClassLoader().getResourceAsStream("calibrib.ttf");
		try {
			fontcalibri = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			fontcalibrib = Font.createFont(Font.TRUETYPE_FONT, inputStreamb);
		} catch (FontFormatException e) {
			logger.info(e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e);
		}
	}

	public JilabaFonts(FontFamily fontFamily) {
		String regularFontFileName = "";
		String boldFontFileName = "";

		switch (fontFamily) {
		case CALIBRI:
			regularFontFileName = "calibri.ttf";
			boldFontFileName = "calibrib.ttf";
			break;
		case TIMES_NEW_ROMAN:
			regularFontFileName = "timesnewroman.ttf";
			boldFontFileName = "timesnewroman_bold.ttf";
			break;
		default:
			throw new JilabaException("FontFamily Not Found");
		}

		InputStream inputStream = JilabaFonts.class.getClassLoader().getResourceAsStream(regularFontFileName);
		InputStream inputStreamb = JilabaFonts.class.getClassLoader().getResourceAsStream(boldFontFileName);
		try {
			fontcalibri = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			fontcalibrib = Font.createFont(Font.TRUETYPE_FONT, inputStreamb);
		} catch (FontFormatException e) {
			logger.info(e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e);
		}
	}

	public Font getFont(FontStyle fontStyle, int fontSize) {
		Font font;

		if (FontStyle.BOLD == fontStyle) {
			if (null == fontcalibrib)
				throw new JilabaException("Font Not Found");
			font = fontcalibrib.deriveFont(Font.BOLD, Float.parseFloat(String.valueOf(fontSize)));
		} else if (FontStyle.PLAIN == fontStyle) {
			if (null == fontcalibri)
				throw new JilabaException("Font Not Found");
			font = fontcalibri.deriveFont(Font.PLAIN, Float.parseFloat(String.valueOf(fontSize)));
		} else {
			font = fontcalibri.deriveFont(Float.parseFloat(String.valueOf(fontSize)));
		}

		return font;
	}

	public Font getFont(int fontSize) {

		if (null == fontcalibri)
			throw new JilabaException("Font Not Found");
		Font font = fontcalibri.deriveFont(Float.parseFloat(String.valueOf(fontSize)));
		return font;
	}
}
