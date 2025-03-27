package com.jilaba.calls.common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

import javax.swing.JLabel;

public class ImageZoomLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	private double zoom = 1;
	private int zoomPointX;
	private int zoomPointY;

	public ImageZoomLabel() {
		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (getIcon() == null)
					return;

				zoomPointX = e.getX();
				zoomPointY = e.getY();

				if (e.getPreciseWheelRotation() < 0) {
					zoom -= 0.2;
				} else {
					zoom += 0.2;
				}

				if (zoom < 1) {
					zoom = 1;
				}

				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (getIcon() == null)
			return;

		Graphics2D g2D = (Graphics2D) g;

		AffineTransform at = g2D.getTransform();
		at.translate(zoomPointX, zoomPointY);
		at.scale(zoom, zoom);
		at.translate(-zoomPointX, -zoomPointY);
		g2D.setTransform(at);

		// System.out.println("Zoom-X : " + zoomPointX + " | Zoom-Y : " + zoomPointY +
		// "Zoom % : " + zoom);
		// getIcon().paintIcon(this, g2D, 0, 0);
		super.paintComponent(g2D);
	}

	public void resetImage() {
		zoom = 1;
		repaint();
	}

}
