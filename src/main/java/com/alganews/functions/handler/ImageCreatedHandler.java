package com.alganews.functions.handler;

import com.alganews.functions.event.StorageEvent;
import com.alganews.functions.service.BufferedImageInput;
import com.alganews.functions.service.ImageResizeService;
import com.alganews.functions.service.StorageService;
import com.google.cloud.storage.Blob;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.apache.commons.io.FilenameUtils.getExtension;

public class ImageCreatedHandler extends AbstractImageHandler {
  
  private final StorageService storage = new StorageService();
  private final ImageResizeService imageResizeService = new ImageResizeService();
  
  @Override
  public void handleEvent(StorageEvent storageEvent) throws Exception {
    String bucketName = storageEvent.getBucket();
    Path filePath = storageEvent.getFilePath();
    
    Blob image = storage.download(bucketName, filePath);
    BufferedImageInput imageInput = createInput(filePath, image);
    imageResizeService.createResizedVersionsOnBucket(imageInput, bucketName);
  }
  
  private BufferedImageInput createInput(Path fileName, Blob image) throws IOException {
    String extension = getExtension(fileName.toString());
    BufferedImage bufferedImage = createBuffer(image, extension);
    return new BufferedImageInput(bufferedImage, fileName.toString(), fileName);
  }
  
  private BufferedImage createBuffer(Blob image, String extension) throws IOException {
    return ImageIO.read(new ByteArrayInputStream(image.getContent()));
  }
}

