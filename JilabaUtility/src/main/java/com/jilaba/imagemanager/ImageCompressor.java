package com.jilaba.imagemanager;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLConnection;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;

public class ImageCompressor {
	// private int size = 500;

	private float compressRatio = 0.5f;
	private long maxImgSizeWithoutCompress = 200000;
	private boolean blnRatioSet = false;

	public ImageCompressor() {
		blnRatioSet = false;
	}

	public byte[] compressBytes(byte[] bytes) throws Exception {

		if (checkImageSize(bytes, maxImgSizeWithoutCompress))
			return bytes;
		return getCompressBytes(bytes, compressRatio);
	}

	private byte[] getCompressBytes(byte[] bytes, float ratioOfCompress) throws Exception {
		ByteArrayOutputStream compressed = new ByteArrayOutputStream();
		bytes = convertToJpg(bytes);

		ImageWriter writter = null;
		// declaring i/o objects auto closable.(not applicable for versions below
		// JavaSE-1.7)
		try (ImageOutputStream outputStream = new MemoryCacheImageOutputStream(compressed)) {

			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			BufferedImage image = ImageIO.read(bis);

			writter = ImageIO.getImageWritersByFormatName("JPEG").next();

			ImageWriteParam param = writter.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(ratioOfCompress);// Configure JPEG compression: 50% quality

			writter.setOutput(outputStream);// set your in-memory stream as the output
			writter.write(null, new IIOImage(image, null, null), param);
			writter.dispose(); // disposing writer to free resources

			// get data for further processing...
			byte[] data = compressed.toByteArray();

			return data;
		} finally {
			if (null != writter)
				writter.dispose();
		}
	}

	public byte[] compressBytes(byte[] bytes, long imgCompressBytesSize) throws Exception {

		if (checkImageSize(bytes, imgCompressBytesSize))
			return bytes;
		return getCompressBytes(bytes, compressRatio);
	}

	public byte[] compressBytes(byte[] bytes, long imgCompressBytesSize, float ratio) throws Exception {

		if (bytes.length <= imgCompressBytesSize)
			return bytes;

		return getCompressBytes(bytes, ratio);
	}

	private boolean checkImageSize(byte[] bytes, long maxByteSize) {
		try {

			if (blnRatioSet && bytes.length > maxByteSize)
				return false;

			if (bytes.length <= maxByteSize)
				return true;

			if (bytes.length <= (2 * maxByteSize)) {
				compressRatio = 0.7f;
				return false;
			}

			if (bytes.length <= (4 * maxByteSize)) {
				compressRatio = 0.5f;
				return false;
			}

			compressRatio = 0.25f;
			return false;

		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	private byte[] convertToJpg(byte[] bytes) throws Exception {

		String extension = getFileExtension(bytes);
		if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg"))
			return bytes;

		ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();

		// declaring i/o objects auto closable.
		try (ImageOutputStream outputStream = new MemoryCacheImageOutputStream(byteArrayOutput)) {

			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			BufferedImage image = ImageIO.read(bis);

			BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
			ImageIO.write(result, "jpg", outputStream);

			return byteArrayOutput.toByteArray();
		}
	}

	public String getFileExtension(byte[] bytes) throws IOException {

		String extension = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(bytes));

		String fileExtention = "JPEG";
		if (extension != null)
			fileExtention = extension.substring(extension.lastIndexOf("/") + 1, extension.length());
		return fileExtention;

	}

	public long getMaxImgSizeWithoutCompress() {
		return maxImgSizeWithoutCompress;
	}

	public void setMaxImgSizeWithoutCompress(long maxImgSizeWithoutCompress) {
		this.maxImgSizeWithoutCompress = maxImgSizeWithoutCompress;
	}

	public float getCompressRatio() {
		return compressRatio;
	}

	public void setCompressRatio(float compressRatio) {
		blnRatioSet = true;
		this.compressRatio = compressRatio;
	}

	public void checkImageSize(byte[] data, int maxSize) {
		// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
		double fileSizeInKB = data.length / 1024;

		/*System.out.println("Image Size [bytes: " + data.length + "] - [KB: " + Math.round(fileSizeInKB) + "] - [MB: "
				+ Math.round(fileSizeInKB / 1024) + "]");*/

		if (fileSizeInKB > maxSize)
			throw new JilabaException("Image Size Should Not Be Greater Than " + maxSize + " KB.");
	}

}
