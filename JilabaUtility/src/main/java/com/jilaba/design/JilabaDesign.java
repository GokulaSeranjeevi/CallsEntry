package com.jilaba.design;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import com.jilaba.common.ReturnStatus;
import com.jilaba.design.DesignEnum.OperatingSystemType;

import sun.java2d.SunGraphicsEnvironment;

public class JilabaDesign {
	private static int x, y;

	private JilabaDesign() {

	}

	public static ReturnStatus centerScreen(JFrame jFrame) {
		try {

			GraphicsConfiguration graphicsConfiguration = jFrame.getGraphicsConfiguration();

			y = getYPositionForContainer(graphicsConfiguration, jFrame.getSize().height);
			x = getXPositionForContainer(graphicsConfiguration, jFrame.getSize().width);

			jFrame.setLocation(x, y);

			return new ReturnStatus(true, "");
		} catch (ArithmeticException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public static ReturnStatus centerScreen(JInternalFrame jInternalFrame) {
		try {
			GraphicsConfiguration graphicsConfiguration = jInternalFrame.getGraphicsConfiguration();
			y = getYPositionForContainer(graphicsConfiguration, jInternalFrame.getSize().height);
			x = getXPositionForContainer(graphicsConfiguration, jInternalFrame.getSize().width);
			jInternalFrame.setLocation(x, y);

			return new ReturnStatus(true, "");
		} catch (ArithmeticException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public static ReturnStatus centerScreen(JDialog jDialog) {

		try {

			GraphicsConfiguration graphicsConfiguration = jDialog.getGraphicsConfiguration();
			y = getYPositionForContainer(graphicsConfiguration, jDialog.getSize().height);
			x = getXPositionForContainer(graphicsConfiguration, jDialog.getSize().width);
			jDialog.setLocation(x, y);

			return new ReturnStatus(true, "");
		} catch (ArithmeticException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private static int getYPositionForContainer(GraphicsConfiguration graphicsConfiguration, int containerHeight) {

		int y, diffY = 0;
		Dimension screenSize = getDeviceBounds();
		Rectangle rectangle = SunGraphicsEnvironment.getUsableBounds(graphicsConfiguration.getDevice());
		OperatingSystemType operatingSystemType = SystemProperties.getOsType();
		if (operatingSystemType == OperatingSystemType.LINUX) {
			diffY = (int) screenSize.getHeight() - rectangle.height;
		}
		y = (rectangle.height / 2 - containerHeight / 2) + diffY;
		return y;
	}

	private static int getXPositionForContainer(GraphicsConfiguration graphicsConfiguration, int containerWidth) {

		int x, diffX;
		Dimension screenSize = getDeviceBounds();

		Rectangle rectangle = SunGraphicsEnvironment.getUsableBounds(graphicsConfiguration.getDevice());
		diffX = (int) screenSize.getWidth() - rectangle.width;
		x = (rectangle.width / 2 - containerWidth / 2) + diffX;

		return x;
	}

	private static Dimension getDeviceBounds() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return screenSize;
	}

	public static ReturnStatus centerScreen(JComponent parentComponent, JComponent childComponent) {
		try {
			childComponent.setLocation(parentComponent.getWidth() / 2 - childComponent.getSize().width / 2,
					parentComponent.getHeight() / 2 - childComponent.getSize().height / 2);
			return new ReturnStatus(true, "");
		} catch (ArithmeticException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public static ReturnStatus centerScreen(JDialog jDialog, JComponent childComponent) {
		try {
			int dialogWidth = jDialog.getContentPane().getWidth();
			int dialogHeight = jDialog.getContentPane().getHeight();

			if (dialogWidth == 0 || dialogHeight == 0) {
				return new ReturnStatus(true);
			}

			childComponent.setLocation(dialogWidth / 2 - childComponent.getSize().width / 2,
					dialogHeight / 2 - childComponent.getSize().height / 2);
			return new ReturnStatus(true, "");
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public static ReturnStatus centerScreen(JFrame jFrame, JComponent childComponent) {
		try {
			int dialogWidth = jFrame.getWidth();
			int dialogHeight = jFrame.getHeight();

			if (dialogWidth == 0 || dialogHeight == 0) {
				return new ReturnStatus(true);
			}

			childComponent.setLocation(dialogWidth / 2 - childComponent.getSize().width / 2,
					dialogHeight / 2 - childComponent.getSize().height / 2);
			return new ReturnStatus(true, "");
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public static ReturnStatus centerScreen(JInternalFrame jInternalFrame, JComponent childComponent) {
		try {
			int dialogWidth = jInternalFrame.getWidth();
			int dialogHeight = jInternalFrame.getHeight();

			if (dialogWidth == 0 || dialogHeight == 0) {
				return new ReturnStatus(true);
			}

			childComponent.setLocation(dialogWidth / 2 - childComponent.getSize().width / 2,
					dialogHeight / 2 - childComponent.getSize().height / 2);
			return new ReturnStatus(true, "");
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}
}
