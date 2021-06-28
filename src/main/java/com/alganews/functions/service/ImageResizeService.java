package com.alganews.functions.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImageResizeService {
	
	private final StorageService storage = new StorageService();
	
	public void createResizedVersionsOnBucket(BufferedImageInput input, String bucketName) {
		try {
			for (ImageResizerType resizeType : ImageResizerType.values()) {
				String uploadPath = addFolderToPath(input.getFilePath(), resizeType.asFolder());
				byte[] bytes = resizeType.resize(input);
				storage.upload(bucketName, uploadPath, bytes);
			}
		} catch (Exception e) {
			throw new ImageResizeException(e);
		}
	}
	
	public List<String> getResizedVersionsReference(Path fileName) {
		return Arrays
				.stream(ImageResizerType.values())
				.map(type -> addFolderToPath(fileName, type.asFolder()))
				.collect(Collectors.toList());
	}
	
	private String addFolderToPath(Path filePath, String folder) {
		return Paths.get(filePath.getParent().toString(),
				folder,
				filePath.getFileName().toString()
		).toString();
	}
	
}
