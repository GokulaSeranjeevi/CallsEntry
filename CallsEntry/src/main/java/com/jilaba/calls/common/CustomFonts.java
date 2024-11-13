package com.jilaba.calls.common;

import java.awt.Font;

import com.jilaba.fonts.JilabaFonts;
import com.jilaba.fonts.JilabaFonts.FontStyle;

public class CustomFonts {

	private static JilabaFonts jilabaFonts = new JilabaFonts();
	public final static Font FONT12 = jilabaFonts.getFont(12);
	public final static Font FONT14 = jilabaFonts.getFont(14);
	public final static Font FONT15 = jilabaFonts.getFont(15);
	public final static Font FONT16 = jilabaFonts.getFont(16);
	public final static Font BOLDFONT14 = jilabaFonts.getFont(FontStyle.BOLD, 14);
	public final static Font FONT22 = jilabaFonts.getFont(22);

	public final static Font font = new Font("Calibri", Font.PLAIN, 17);
	public final static Font fontCalibriPlain15 = new Font("Calibri", Font.PLAIN, 15);
	public final static Font fontCalibriBold = new Font("Calibri", Font.BOLD, 15);
	public final static Font fontCalibriBold16 = new Font("Calibri", Font.BOLD, 16);
	public final static Font fontAxiformaBold16 = new Font("Axiforma", Font.BOLD, 18);
}
