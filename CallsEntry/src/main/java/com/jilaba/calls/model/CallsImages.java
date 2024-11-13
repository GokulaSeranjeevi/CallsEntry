package com.jilaba.calls.model;

public class CallsImages {
	private int callNo = 0;
	private String callImagePath = "";
	private byte[] image1;
	private byte[] image2;

	public byte[] getImage1() {
		return image1;
	}

	public void setImage1(byte[] image1) {
		this.image1 = image1;
	}

	public byte[] getImage2() {
		return image2;
	}

	public void setImage2(byte[] image2) {
		this.image2 = image2;
	}

	public byte[] getImage3() {
		return image3;
	}

	public void setImage3(byte[] image3) {
		this.image3 = image3;
	}

	public byte[] getImage4() {
		return image4;
	}

	public void setImage4(byte[] image4) {
		this.image4 = image4;
	}

	private byte[] image3;
	private byte[] image4;

	public int getCallNo() {
		return callNo;
	}

	public void setCallNo(int callNo) {
		this.callNo = callNo;
	}

	public String getCallImagePath() {
		return callImagePath;
	}

	public void setCallImagePath(String callImagePath) {
		this.callImagePath = callImagePath;
	}

}
