package com.alganews.functions.service;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

import static org.apache.commons.io.FilenameUtils.getExtension;

public class BufferedImageInput {
	
	private final BufferedImage image;
	private final Path filePath;
	
	public BufferedImageInput(BufferedImage image, String filePath, Path fileName) {
		this.image = image;
		this.filePath = fileName;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public Path getFilePath() {
		return filePath;
	}
	
	public String getFileName() {
		return filePath.getFileName().toString();
	}
	
	public String getFormat() {
		String extension = getExtension(getFileName());
		extension = extension.toLowerCase();
		
		if ("jpg".equals(extension)) {
			extension = "jpeg";
		}
		
		return extension;
	}
}
