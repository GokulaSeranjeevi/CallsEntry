package com.jilaba.print;

public class PrintControlValues {
	
	public static final String LINEFEED = Character.toString((char)10);
	public static final String VERTICALTAB  = Character.toString((char)11);
	public static final String SPACE = Character.toString((char)32);
	public static final String FORMFEED = Character.toString((char)12);
	public static final String CARRIAGERETURN = Character.toString((char)13);
	public static final String ONCOMPRESSEDFORMAT = Character.toString((char)15);
	public static final String OFFCOMPRESSEDFORMAT = Character.toString((char)18);
	public static final String ONCOMPRESSEDFORMATMED = Character.toString((char)27) + "M";
	
	public static final String DOUBLESTRIKESTART = Character.toString((char)27)+"G";
	public static final String DOUBLESTRIKEEND = Character.toString((char)27)+"H";
	
	public static final String ONBOLDFONT = Character.toString((char)27) + "E";
	public static final String OFFBOLDFONT = Character.toString((char)27) + "F";
	public static final String ONENLARGEDFONT = Character.toString((char)14);
	public static final String OFFENLARGEDFONT = Character.toString((char)20);
	public static final String SCROLLDOWN = Character.toString((char)27) + "j" + Character.toString((char)180);

	public static final String CUTTING = Character.toString((char)27) + Character.toString((char)105);
	public static final String BIGSTART = Character.toString((char)27) + "!" + Character.toString((char)16);
	public static final String BIGEND = Character.toString((char)27) + "!" + Character.toString((char)1);
	public static final String ORIGINALMODE = Character.toString((char)18) + Character.toString((char)20);

	public static final String BIG_BOLD = Character.toString((char)27) + "!" + Character.toString((char)12);

	public static final String UNDERLINESTART = Character.toString((char)27)+"-1";
	public static final String UNDERLINEEND = Character.toString((char)27)+"-0";
	
	/** Command for Width of the barcode */
	public static final String BARCODEWIDTH = Character.toString((char)29) + "w" + Character.toString((char)2);
	/**
	 * Command for Human readable data position
	 */
	public static final String BARCODEREADABLE = Character.toString((char)29) + "H" + Character.toString((char)2);
	/**
	 * Command for Height of the barcode
	 */
	public static final String BARCODEHEIGHT = Character.toString((char)29) + "h" + Character.toString((char)60);
	/**
	 * command for printing Text at last
	 */
	public static final String BARCODESTART = Character.toString((char)29) + "k" + Character.toString((char)4) + "";
	
	public static final String BARCODEEND = Character.toString((char)0);

	private PrintControlValues(){
		
	}
	
}
