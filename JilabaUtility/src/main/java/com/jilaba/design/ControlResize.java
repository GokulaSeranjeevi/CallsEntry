package com.jilaba.design;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;

import com.jilaba.design.DesignEnum.OperatingSystemType;
import com.jilaba.exception.JilabaException;

import sun.java2d.SunGraphicsEnvironment;

public class ControlResize {
	private double screenWidth = 0;
	private double screenHeight = 0;
	private JFrame jFrame;
	private JInternalFrame jInternalFrame;
	private JDialog jDialog;
	private Rectangle rectangle;
	private double minimumWidth = 0;
	private double minimumHeight = 0;
	private Dimension dimension;

	public ControlResize(JFrame jFrame) {
		this.jFrame = jFrame;

		GraphicsConfiguration oConfig = this.jFrame.getGraphicsConfiguration();
		OperatingSystemType operatingSystemType = SystemProperties.getOsType();
		if (operatingSystemType == OperatingSystemType.LINUX) {
			rectangle = SunGraphicsEnvironment.getUsableBounds(oConfig.getDevice());
		} else {
			rectangle = oConfig.getBounds();
		}
		minimumWidth = this.jFrame.getSize().width;
		minimumHeight = this.jFrame.getSize().height;
		screenWidth = rectangle.width;
		screenHeight = rectangle.height;
	}

	public ControlResize(JInternalFrame jInternalFrame, Dimension dimension) {
		this.jInternalFrame = jInternalFrame;
		this.dimension = dimension;
		GraphicsConfiguration oConfig = this.jInternalFrame.getGraphicsConfiguration();
		OperatingSystemType operatingSystemType = SystemProperties.getOsType();

		if (operatingSystemType == OperatingSystemType.LINUX) {
			rectangle = SunGraphicsEnvironment.getUsableBounds(oConfig.getDevice());
		} else {
			rectangle = oConfig.getBounds();
		}

		minimumWidth = this.jInternalFrame.getSize().width;
		minimumHeight = this.jInternalFrame.getSize().height;
		screenWidth = this.dimension.width;
		screenHeight = this.dimension.height;
	}

	public ControlResize(JDialog jDialog) {
		this.jDialog = jDialog;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		minimumWidth = this.jDialog.getSize().width;
		minimumHeight = this.jDialog.getSize().height;

		screenWidth = (minimumWidth / 1024) * 100; // In Percentage
		screenHeight = (minimumHeight / 768) * 100;

		screenWidth = (screenWidth * screenSize.getWidth()) / 100; // In Value
		screenHeight = (screenHeight * screenSize.getHeight()) / 100;
	}

	public Rectangle getRectangle() {
		return this.rectangle;
	}

	public void reAlignControls() throws JilabaException {
		parentJFrameControl();
	}

	private void parentJFrameControl() throws JilabaException {
		try {
			Component component;
			Container cContainer, jFrameContainer = null;
			if (jFrame == null && jInternalFrame == null && jDialog == null)
				return;
			if (jFrame != null) {
				jFrameContainer = jFrame.getContentPane();
			}
			if (jInternalFrame != null) {
				jFrameContainer = jInternalFrame.getContentPane();
			}
			if (jDialog != null) {
				jFrameContainer = jDialog.getContentPane();
				parentControlAlign(jDialog);
			}
			int compCount = jFrameContainer.getComponentCount();
			for (int i = 0; i < compCount; i++) {
				component = jFrameContainer.getComponent(i);
				if (component instanceof JPanel || component instanceof JTabbedPane) {
					parentControlAlign(component);
					cContainer = (Container) component;
					resizeContainers(cContainer);
				} else {
					parentControlAlign(component);
				}
			}
		} catch (JilabaException e) {
			throw e;
		}
	}

	private void resizeContainers(Container container) throws JilabaException {
		try {
			Component component;
			Container cContainer;
			int compCount = container.getComponentCount();
			for (int i = 0; i < compCount; i++) {
				component = container.getComponent(i);
				if (component instanceof JPanel || component instanceof JTabbedPane
						|| component instanceof JInternalFrame || component instanceof JRootPane
						|| component instanceof JLayeredPane) {
					parentControlAlign(component);
					cContainer = (Container) component;
					resizeContainers(cContainer);
				} else {
					parentControlAlign(component);
				}
			}
		} catch (JilabaException e) {
			throw e;
		}
	}

	private void parentControlAlign(Component jParentComponent) throws JilabaException {
		double controlWidth = 0;
		double controlHeight = 0;
		double controlX = 0;
		double controlY = 0;

		double controlWidthPercentage = 0;
		double controlHeightPercentage = 0;
		double controlXPercentage = 0;
		double controlYPercentage = 0;
		try {
			double X = (int) jParentComponent.getLocation().getX();
			double Y = (int) jParentComponent.getLocation().getY();
			double Width = (int) jParentComponent.getSize().getWidth();
			double Height = (int) jParentComponent.getSize().getHeight();

			controlXPercentage = X * 100 / minimumWidth;
			controlYPercentage = Y * 100 / minimumHeight;
			controlWidthPercentage = Width * 100 / minimumWidth;
			controlHeightPercentage = Height * 100 / minimumHeight;

			controlWidth = screenWidth / 100.0 * controlWidthPercentage;
			controlHeight = screenHeight / 100.0 * controlHeightPercentage;
			controlX = screenWidth / 100.0 * controlXPercentage;
			controlY = screenHeight / 100.0 * controlYPercentage;

			if (jParentComponent instanceof JDialog) {
				((JDialog) jParentComponent).getContentPane()
						.setPreferredSize(new Dimension((int) controlWidth, (int) controlHeight));
				jParentComponent.setLocation((int) controlX, (int) controlY);
				((JDialog) jParentComponent).pack();
			} else
				jParentComponent.setBounds((int) controlX, (int) controlY, (int) controlWidth, (int) controlHeight);

			// jParentComponent.setBounds((int) controlX, (int) controlY, (int)
			// controlWidth, (int) controlHeight);

			jParentComponent.repaint();
		} catch (JilabaException e) {
			throw e;
		}
	}

}
