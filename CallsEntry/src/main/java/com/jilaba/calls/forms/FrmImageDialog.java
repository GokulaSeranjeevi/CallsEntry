package com.jilaba.calls.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.common.ImageResource;
import com.jilaba.calls.logic.LogicCallsEntry;
import com.jilaba.calls.logic.LogicDevCallAssign;
import com.jilaba.calls.model.CallsImages;
import com.jilaba.calls.start.Applicationmain;
import com.jilaba.common.ReturnStatus;
import com.jilaba.control.ImageZoomLabel;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;
import com.jilaba.imagemanager.ImageCompressor;

@Component
@Scope("prototype")
public class FrmImageDialog extends JDialog implements ActionListener, KeyListener, MouseListener {

	public static JLabel lblImage1 = null;
	public static JLabel lblImage2 = null;
	public static JLabel lblImage3 = null;
	public static JLabel lblImage4 = null;

	private boolean blnimage1 = false;
	private boolean blnimage2 = false;
	private boolean blnimage3 = false;
	private boolean blnimage4 = false;
	public static boolean blnImageVerify = false;

	private JPanel panelTitle;
	private JPanel panelCommonDialog;
	private JLabel Check = null;
	private JButton btnSubmit;
	private JButton btnExit;
	private JLabel lblTitle;

	private JFileChooser jFileChooser;
	private File previewFile;
	private byte[] lblImage1Path = null;
	private byte[] lblImage2Path = null;
	private byte[] lblImage3Path = null;

	private byte[] lblImage4Path = null;
	private ReturnStatus returnStatus;

	private boolean blnDevCalls = false;
	private boolean blnCallEdit = false;
	private String callNo;

	private byte[] repImageFile;
	private ImageCompressor imageCompressor;
	private ClassLoader classLoader = FrmCallsEntry.class.getClassLoader();
	private ImageIcon previewIcon;
	@Autowired
	private LogicDevCallAssign logicDevCallAssign;
	@Autowired
	private FrmCallsEntry frmCallsEntry;

	private CallsImages callsImages;
	private byte[] imageData1;
	private byte[] imageData2;
	private byte[] imageData3;
	private byte[] imageData4;

	private Image reImage1;
	private Image reImage2;
	private Image reImage3;
	private Image reImage4;

	private BufferedImage image = null;

	private Color color1 = Color.decode("#4682b4");
	private Color color2 = Color.decode("#FFFFFF");
	private Color color3 = Color.decode("#D3D3D3");
	private Color color4 = Color.decode("#000000");
	private Color color5 = Color.decode("#e4dedf");
	private Color color6 = Color.decode("#C0C0C0");
	private Color color7 = Color.decode("#FADBD8");

	public FrmImageDialog(Container contenPane) throws JilabaException {

		setLayout(null);
		setTitle("Call Assign");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setUndecorated(true);
		setResizable(false);
		setSize(700, 600);
		setDefaultCloseOperation(0);
		// setLocation(280, 150);
		setLocationRelativeTo(contenPane);

		createControl();
		createListeners();

		imageCompressor = new ImageCompressor();
	}

	private void createListeners() {
		// 

		lblImage1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_D) {

				}

			}
		});

	}

	@PostConstruct
	private ReturnStatus loadDetails() {

		return new ReturnStatus(true);

	}

	private void createControl() {

		//		JOptionPane jop = new JOptionPane();
		//		JDialog dialog = jop.createDialog("Call Assign");
		//		dialog.setSize(400, 400);

		int lblWidth = 300;
		int lblHeight = 250;

		panelTitle = new JPanel(null);
		panelTitle.setBounds(0, 0, 750, 30);
		panelTitle.setBackground(Color.lightGray);
		// panelTitle.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

		panelTitle.setVisible(true);

		lblTitle = new JLabel("Click To Add Images ");
		lblTitle.setBounds(-60, 5, lblWidth + 30, 20);
		lblTitle.setForeground(Color.RED);
		lblTitle.setFont(CustomFonts.fontCalibriBold);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		//		lblTitle.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		lblTitle.setVisible(true);

		panelCommonDialog = new JPanel(null);
		panelCommonDialog.setBounds(0, 0, 700, 600);
		panelCommonDialog.setBackground(color2);
		panelCommonDialog.setVisible(true);
		panelCommonDialog.addKeyListener(this);

		lblImage1 = new JLabel("Image1");
		lblImage1.setBounds(40, 50, lblWidth, lblHeight);
		lblImage1.setForeground(Color.BLACK);
		lblImage1.setFont(CustomFonts.fontCalibriBold);
		lblImage1.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage1.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		lblImage1.setVisible(true);
		lblImage1.addMouseListener(this);

		lblImage2 = new JLabel("Image 2");
		lblImage2.setBounds(lblImage1.getX() + lblImage1.getWidth() + 30, lblImage1.getY(), lblWidth, lblHeight);
		lblImage2.setForeground(Color.BLACK);
		lblImage2.setFont(CustomFonts.fontCalibriBold);
		lblImage2.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		lblImage2.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage2.setVisible(true);
		lblImage2.addMouseListener(this);

		lblImage3 = new JLabel("Image 3");
		lblImage3.setBounds(lblImage1.getX(), lblImage1.getY() + lblImage1.getHeight() + 15, lblWidth, lblHeight);
		lblImage3.setForeground(Color.BLACK);
		lblImage3.setFont(CustomFonts.fontCalibriBold);
		lblImage3.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		lblImage3.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage3.setVisible(true);
		lblImage3.addMouseListener(this);

		lblImage4 = new JLabel("Image 4");
		lblImage4.setBounds(lblImage2.getX(), lblImage3.getY(), lblWidth, lblHeight);
		lblImage4.setForeground(Color.BLACK);
		lblImage4.setFont(CustomFonts.fontCalibriBold);
		lblImage4.setBorder(BorderFactory.createEtchedBorder(color3, color3));
		lblImage4.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage4.setVisible(true);
		lblImage4.addMouseListener(this);

		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(lblImage3.getX() + lblImage3.getWidth() - 70, lblImage3.getY() + lblImage3.getHeight() + 10,
				80, 20);
		btnSubmit.setBackground(Color.GRAY);
		btnSubmit.setForeground(Color.BLACK);
		btnSubmit.setMnemonic(KeyEvent.VK_S);
		btnSubmit.setFont(CustomFonts.fontCalibriBold);
		btnSubmit.setVisible(true);
		btnSubmit.addKeyListener(this);
		btnSubmit.addActionListener(this);

		btnExit = new JButton("Exit");
		btnExit.setBounds(btnSubmit.getX() + btnSubmit.getWidth() + 50, btnSubmit.getY(), 80, 20);
		btnExit.setBackground(Color.GRAY);
		btnExit.setForeground(Color.BLACK);
		btnExit.setMnemonic(KeyEvent.VK_E);
		btnExit.setFont(CustomFonts.fontCalibriBold);
		btnExit.setVisible(true);
		btnExit.addKeyListener(this);
		btnExit.addActionListener(this);

		getContentPane().add(panelCommonDialog);

		panelCommonDialog.add(panelTitle);

		panelTitle.add(lblTitle);

		panelCommonDialog.add(lblImage1);
		panelCommonDialog.add(lblImage2);
		panelCommonDialog.add(lblImage3);
		panelCommonDialog.add(lblImage4);
		panelCommonDialog.add(btnSubmit);
		panelCommonDialog.add(btnExit);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

			if (e.getSource() == btnSubmit) {

				btnExit.requestFocus();
			}

		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {

			if (e.getSource() == btnExit) {

				btnSubmit.requestFocus();
			}

		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

			setVisible(false);

			//			FrmDevCallAssign frmDevCallAssign = Applicationmain.getAbstractApplicationContext()
			//					.getBean(FrmDevCallAssign.class);
			//			frmDevCallAssign.setVisible(true);

			//			FrmDevCallAssign.tblCalls.requestFocus();

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnSubmit) {

			//frmCallsEntry.btnImageSave(lblImage1Path, lblImage2Path, lblImage3Path, lblImage4Path);

			setVisible(false);

		} else if (e.getSource() == btnExit) {

			setVisible(false);

		}

	}

	public void loadImage() {

		if (blnimage1 == true) {
			Check = lblImage1;
		} else if (blnimage2 == true) {
			Check = lblImage2;
		} else if (blnimage3 == true) {
			Check = lblImage3;
		} else if (blnimage4 == true) {
			Check = lblImage4;
		}

		jFileChooser = new JFileChooser();

		/**JPG, BMP, PCX, GIF, WBMP, PNG, RAW, JPEG, PNM, TIF, TIFF*/
		FileFilter imageFilter = new FileNameExtensionFilter("", ImageIO.getWriterFormatNames());
		jFileChooser.setFileFilter(imageFilter);

		int retVal = jFileChooser.showOpenDialog(getContentPane());
		try {
			if (retVal == JFileChooser.APPROVE_OPTION) {
				previewFile = jFileChooser.getSelectedFile();
				//						lblPath.setText(previewFile.getAbsolutePath());
				returnStatus = changePreview(previewFile);
				if (!returnStatus.isStatus()) {
					throw new JilabaException(returnStatus.getDescription());
				}
			} else {
				returnStatus = loadDefaultPreview(Check);

			}
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(getContentPane(), e2.getMessage(), "", JOptionPane.ERROR_MESSAGE);
			loadDefaultPreview(Check);

		}

	}

	private ReturnStatus loadDefaultPreview(JLabel Check) {

		try {

			if (classLoader.getResource(ImageResource.IMAGEPREVIEW) != null && previewIcon == null) {
				BufferedImage bi = new BufferedImage(Check.getWidth(), Check.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g = bi.createGraphics();
				Image img = ImageIO.read(classLoader.getResource(ImageResource.IMAGEPREVIEW));
				g.drawImage(img, 0, 0, Check.getWidth(), Check.getHeight(), null);
				g.dispose();

				previewIcon = new ImageIcon(
						img.getScaledInstance(Check.getWidth(), Check.getHeight(), BufferedImage.SCALE_SMOOTH));
			}

			Check.setIcon(previewIcon);

			previewFile = null;
			//			lblPath.setText("");

			return new ReturnStatus(true);

		} catch (IOException e) {
			return new ReturnStatus(false, ErrorHandling.handleError(e));
		}
	}

	private ReturnStatus changePreview(File file) {
		try {
			/*BufferedImage bi = new BufferedImage(Check.getWidth(), Check.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bi.createGraphics();
			Image img = ImageIO.read(file);
			g.drawImage(img, 0, 0, Check.getWidth(), Check.getHeight(), null);
			g.dispose();
			Check.setIcon(new ImageIcon(
					img.getScaledInstance(Check.getWidth(), Check.getHeight(), BufferedImage.SCALE_SMOOTH)));
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "png", baos);
			
			repImageFile = baos.toByteArray();*/

			String imagePath = file.getAbsolutePath();

			BufferedImage bimage = ImageIO.read(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bimage, imagePath.substring(imagePath.lastIndexOf(".") + 1), baos);
			repImageFile = baos.toByteArray();
			repImageFile = imageCompressor.compressBytes(repImageFile);

			ImageIcon imageIcon = new ImageIcon(repImageFile);
			Image img = imageIcon.getImage().getScaledInstance(Check.getWidth(), Check.getHeight(), Image.SCALE_SMOOTH);
			Check.setIcon(new ImageIcon(img));

			/*BufferedImage bimage = ImageIO.read(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bimage, file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1), baos);
			repImageFile = baos.toByteArray();
			*/
			blnImageVerify = true;
			// repImageFile = imageCompressor.compressBytes(repImageFile);
			if (blnimage1 == true) {
				reImage1 = img;
				lblImage1Path = repImageFile;
				blnimage1 = false;
			} else if (blnimage2 == true) {
				reImage2 = img;
				lblImage2Path = repImageFile;
				blnimage2 = false;
			} else if (blnimage3 == true) {
				reImage3 = img;
				lblImage3Path = repImageFile;
				blnimage3 = false;
			} else if (blnimage4 == true) {
				reImage4 = img;
				lblImage4Path = repImageFile;
				blnimage4 = false;
			}

			return new ReturnStatus(true);
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage());
		}
	}

	public void reloadImage() {

		if (reImage1 != null)
			lblImage1.setIcon(new ImageIcon(reImage1));
		if (reImage2 != null)
			lblImage2.setIcon(new ImageIcon(reImage2));
		if (reImage3 != null)
			lblImage3.setIcon(new ImageIcon(reImage3));
		if (reImage4 != null)
			lblImage4.setIcon(new ImageIcon(reImage4));

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource() == lblImage1) {
			blnimage1 = true;
			if (blnDevCalls == true) {

				try {
					FrmZoomImages frmZoomImages = Applicationmain.getAbstractApplicationContext()
							.getBean(FrmZoomImages.class, new Object[] { getContentPane() });
					frmZoomImages.setImages(imageData1);
					frmZoomImages.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else if (blnCallEdit == true) {

				if (imageData1 != null) {
					int response = JOptionPane.showConfirmDialog(panelCommonDialog, "Do you want to Remove?", "Confirm",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response == JOptionPane.YES_OPTION) {
						imageData1 = null;
						lblImage1.setIcon(null);
						lblImage1Path = null;
						blnImageVerify = true;
					} else if (response == JOptionPane.NO_OPTION) {
						return;
					}
				} else {
					int responseAdd = JOptionPane.showConfirmDialog(panelCommonDialog, "Do you want Add New Image?",
							"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (responseAdd == JOptionPane.YES_OPTION) {
						ImageCheck();
						loadImage();

					} else {
						return;
					}
				}
			}
			;

		} else if (e.getSource() == lblImage2) {
			blnimage2 = true;
			if (blnDevCalls == true) {
				try {
					FrmZoomImages frmZoomImages = Applicationmain.getAbstractApplicationContext()
							.getBean(FrmZoomImages.class, new Object[] { getContentPane() });
					frmZoomImages.setImages(imageData2);
					frmZoomImages.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else if (blnCallEdit == true) {

				if (imageData2 != null) {
					int response = JOptionPane.showConfirmDialog(panelCommonDialog, "Do you want to Remove?", "Confirm",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response == JOptionPane.YES_OPTION) {
						imageData2 = null;
						lblImage2.setIcon(null);
						lblImage2Path = null;
						blnImageVerify = true;
					} else if (response == JOptionPane.NO_OPTION) {
						return;
					}
				} else {
					int responseAdd = JOptionPane.showConfirmDialog(panelCommonDialog, "Do you want Add New Image?",
							"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (responseAdd == JOptionPane.YES_OPTION) {
						ImageCheck();
						loadImage();
					} else {
						return;
					}
				}
			}
			;

		} else if (e.getSource() == lblImage3)

		{
			blnimage3 = true;
			if (blnDevCalls == true) {
				try {

					FrmZoomImages frmZoomImages = Applicationmain.getAbstractApplicationContext()
							.getBean(FrmZoomImages.class, new Object[] { getContentPane() });
					frmZoomImages.setImages(imageData3);
					frmZoomImages.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else if (blnCallEdit == true) {

				if (imageData3 != null) {
					int response = JOptionPane.showConfirmDialog(panelCommonDialog, "Do you want to Remove?", "Confirm",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response == JOptionPane.YES_OPTION) {
						imageData3 = null;
						lblImage3.setIcon(null);
						lblImage3Path = null;
						blnImageVerify = true;
					} else if (response == JOptionPane.NO_OPTION) {
						return;
					}
				} else {
					int responseAdd = JOptionPane.showConfirmDialog(panelCommonDialog, "Do you want Add New Image?",
							"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (responseAdd == JOptionPane.YES_OPTION) {
						ImageCheck();
						loadImage();
					} else {
						return;
					}
				}
			}
			;

		} else if (e.getSource() == lblImage4) {
			blnimage4 = true;
			if (blnDevCalls == true) {
				try {
					FrmZoomImages frmZoomImages = Applicationmain.getAbstractApplicationContext()
							.getBean(FrmZoomImages.class, new Object[] { getContentPane() });
					frmZoomImages.setImages(imageData4);
					frmZoomImages.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else if (blnCallEdit == true) {

				if (imageData4 != null) {
					int response = JOptionPane.showConfirmDialog(panelCommonDialog, "Do you want to Remove?", "Confirm",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response == JOptionPane.YES_OPTION) {
						imageData4 = null;
						lblImage4.setIcon(null);
						lblImage4Path = null;
						blnImageVerify = true;

					} else if (response == JOptionPane.NO_OPTION) {
						return;
					}
				} else {
					int responseAdd = JOptionPane.showConfirmDialog(panelCommonDialog, "Do you want Add New Image?",
							"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (responseAdd == JOptionPane.YES_OPTION) {
						ImageCheck();
						loadImage();
					} else {
						return;
					}
				}
			}
			;

		}

		if (blnDevCalls == false && blnCallEdit == false) {

			loadImage();
		}
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

	public byte[] getLblImage1Path() {
		return lblImage1Path;
	}

	public byte[] getLblImage2Path() {
		return lblImage2Path;
	}

	public byte[] getLblImage3Path() {
		return lblImage3Path;
	}

	public byte[] getLblImage4Path() {
		return lblImage4Path;
	}

	public void booleanAssign(boolean blnFrmCallEdit, boolean blnFrmDevCall, String strCallNo,
			CallsImages callsImages) {

		this.blnCallEdit = blnFrmCallEdit;
		this.blnDevCalls = blnFrmDevCall;
		this.callNo = strCallNo;
		this.callsImages = callsImages;

		if (blnDevCalls == true) {

			btnSubmit.setVisible(false);
			panelTitle.removeAll();
			panelTitle.setToolTipText("Click to Zoom Images !...");

		}

		ImageCheck();

	}

	private void ImageCheck() {

		//		try {

		imageData1 = callsImages.getImage1();
		imageData2 = callsImages.getImage2();
		imageData3 = callsImages.getImage3();
		imageData4 = callsImages.getImage4();

		if (imageData1 != null) {
			/*ImageIcon imageIcon1 = new ImageIcon(imageData1);
			lblImage1Path = imageData1;
			lblImage1.setIcon(imageIcon1);*/

			ImageIcon imageIcon1 = new ImageIcon(imageData1);
			Image scaledImage = imageIcon1.getImage().getScaledInstance(lblImage1.getWidth(), lblImage1.getHeight(),
					Image.SCALE_SMOOTH);
			lblImage1.setIcon(new ImageIcon(scaledImage));
			lblImage1Path = imageData1;
		}
		if (imageData2 != null) {
			ImageIcon imageIcon2 = new ImageIcon(imageData2);
			lblImage2Path = imageData2;
			lblImage2.setIcon(imageIcon2);
		}
		if (imageData3 != null) {
			ImageIcon imageIcon3 = new ImageIcon(imageData3);
			lblImage3Path = imageData3;
			lblImage3.setIcon(imageIcon3);
		}
		if (imageData4 != null) {
			ImageIcon imageIcon4 = new ImageIcon(imageData4);
			lblImage4Path = imageData4;
			lblImage4.setIcon(imageIcon4);
		}
		//		} catch (Exception e) {
		//
		//			JOptionPane.showMessageDialog(panelCommonDialog, "No Images Found ... !");
		//			return ;
		//		}
	}

	public Image getReImage1() {
		return reImage1;
	}

	public void setReImage1(Image reImage1) {
		this.reImage1 = reImage1;
	}

}
