package com.jilaba.calls.forms;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.start.Applicationmain;
import com.jilaba.common.ReturnStatus;
import com.jilaba.control.ImageZoomLabel;
import com.jilaba.imagemanager.ImageCompressor;

@Component
@Scope("prototype")
public class FrmZoomImages extends JDialog implements MouseListener, ActionListener, KeyListener {

	private ImageCompressor imageCompressor;
	private byte[] imageData;
	private JLabel lblZoomImages;
	private JPanel panelImageZoom;
	private JPanel panelMain;

	private ClassLoader classLoader = FrmImageDialog.class.getClassLoader();
	private ImageIcon previewIcon;
	private File previewFile;

	private Color color1 = Color.decode("#ff6666");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#D3D3D3");
	private Color color4 = Color.decode("#000000");
	private Color color5 = Color.decode("#e4dedf");
	private Color color6 = Color.decode("#C0C0C0");
	private Color color7 = Color.decode("#FADBD8");

	public FrmZoomImages(Container contentPane) {

		setLayout(null);
		setTitle("Call Assign");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setUndecorated(true);
		setResizable(false);
		setSize(1500, 800);
		setDefaultCloseOperation(0);
		// setLocation(280, 150);
		setLocationRelativeTo(contentPane);

		createControl();
		setupKeyBindings();

		imageCompressor = new ImageCompressor();
	}

	private void createControl() {

		panelMain = new JPanel(null);
		panelMain.setBounds(0, 0, 1500, 800);
		panelMain.setBackground(color2);
		panelMain.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		panelMain.setVisible(true);
		panelMain.addKeyListener(this);

		panelImageZoom = new JPanel(null);
		panelImageZoom.setBounds(0, 0, 1500, 800);
		panelImageZoom.setBackground(color2);
		panelImageZoom.setVisible(true);
		panelImageZoom.addKeyListener(this);

		lblZoomImages = new ImageZoomLabel();
		lblZoomImages.setBounds(20, 20, 1450, 780);
		lblZoomImages.setForeground(Color.BLACK);
		lblZoomImages.setFont(CustomFonts.fontCalibriPlain15);
		lblZoomImages.setHorizontalAlignment(SwingConstants.CENTER);
		lblZoomImages.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		lblZoomImages.setVisible(false);
		lblZoomImages.addMouseListener(this);
		lblZoomImages.addKeyListener(this);

		getContentPane().add(panelMain);
		panelMain.add(panelImageZoom);
		panelImageZoom.add(lblZoomImages);

		panelImageZoom.setVisible(true);
		lblZoomImages.setVisible(true);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public ReturnStatus setImages(byte[] imageData1) throws IOException {

		// Convert byte array to BufferedImage
		ByteArrayInputStream bais = new ByteArrayInputStream(imageData1);
		BufferedImage originalImage = ImageIO.read(bais);

		if (originalImage == null) {
			// Return a failure status if the image cannot be read
			return new ReturnStatus(false, "Image could not be read");
		}

		// Get dimensions of the label
		int labelWidth = lblZoomImages.getWidth();
		int labelHeight = lblZoomImages.getHeight();

		// Create a new BufferedImage for the scaled image
		BufferedImage scaledImage = new BufferedImage(labelWidth, labelHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = scaledImage.createGraphics();

		// Set rendering hints for better image quality
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the original image scaled to the new image
		g.drawImage(originalImage, 0, 0, labelWidth, labelHeight, null);
		g.dispose();

		// Create an ImageIcon with the scaled image
		previewIcon = new ImageIcon(scaledImage);

		// Set the icon of lblZoomImages to the scaled image
		lblZoomImages.setIcon(previewIcon);

		// Reset previewFile and other elements if needed
		previewFile = null;
		// lblPath.setText("");

		return new ReturnStatus(true);

	}

	private void setupKeyBindings() {
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		getRootPane().registerKeyboardAction(e -> handleEscape(), escapeKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	private void handleEscape() {
		setVisible(false);

		FrmImageDialog frmImageDialog = Applicationmain.getAbstractApplicationContext().getBean(FrmImageDialog.class);
		frmImageDialog.setVisible(true);
	}
}