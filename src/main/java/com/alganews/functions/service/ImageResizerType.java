package com.alganews.functions.service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.alganews.functions.util.SystemEnvUtil.getEnvOrDefault;

public enum ImageResizerType {
	
	SMALL(getEnvOrDefault("SMALL_SIZE", 360)),
	MEDIUM(getEnvOrDefault("MEDIUM_SIZE", 720)),
	LARGE(getEnvOrDefault("LARGER_SIZE", 1080));
	
	private final int resizedHeight;
	
	ImageResizerType(int resizedHeight) {
		this.resizedHeight = resizedHeight;
	}
	
	public String asFolder() {
		return "/" + this.toString().toLowerCase();
	}
	
	public static List<String> valuesAsFolder() {
		return Arrays.stream(ImageResizerType.values())
				.map(ImageResizerType::asFolder)
				.collect(Collectors.toList());
	}
	
	public byte[] resize(BufferedImageInput input) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		BufferedImage originalImage = input.getImage();
		String format = input.getFormat();
		
		BufferedImage resizedImage = resize(originalImage);
		ImageIO.write(resizedImage, format, outputStream);
		
		return outputStream.toByteArray();
	}
	
	public BufferedImage resize(BufferedImage originalImage) {
		int resizedWidth = calculateResizedWidth(originalImage);
		
		Image resizeImage = originalImage.getScaledInstance(resizedWidth, resizedHeight, Image.SCALE_SMOOTH);
		
		BufferedImage bufferedResizedImage = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_INT_RGB);
		bufferedResizedImage.getGraphics().drawImage(resizeImage, 0, 0 , null);
		
		return bufferedResizedImage;
	}
	
	private int calculateResizedWidth(BufferedImage originalImage) {
		float heightResizedPercent = resizedHeight / (float) originalImage.getHeight();
		return (int) (originalImage.getWidth() * heightResizedPercent);
	}
	
}
