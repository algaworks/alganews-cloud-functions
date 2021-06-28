package com.alganews.functions.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.alganews.functions.util.ContentTypeUtil.getContentTypeBy;

public class StorageService {
	
	private final Logger logger = Logger.getLogger(ImageResizeService.class.getName());
	private final Storage storage = StorageOptions.getDefaultInstance().getService();
	
	public Blob download(String bucketName, Path filePath) throws IOException {
		return storage.get(BlobId.of(bucketName, filePath.toString()));
	}
	
	public void delete(String bucketName, List<String> fileNames) {
		for (String fileName : fileNames) {
			storage.delete(bucketName, fileName);
		}
	}
	
	public void upload(String bucketName, String uploadPath, byte[] bytes) {
		try {
			BlobInfo blobInfo = createBlobInfo(bucketName, uploadPath);
			storage.create(blobInfo, bytes);
		} catch (Exception e) {
			logger.log(Level.SEVERE, String.format("Erro ao realizar o upload da imagem %s", uploadPath),e);
		}
	}
	
	private BlobInfo createBlobInfo(String bucketName, String uploadPath) {
		return BlobInfo.newBuilder(bucketName, uploadPath)
				.setContentType(getContentTypeBy(uploadPath))
				.build();
	}
}
