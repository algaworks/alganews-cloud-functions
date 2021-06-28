package com.alganews.functions.handler;

import com.alganews.functions.event.StorageEvent;
import com.alganews.functions.service.ImageResizeService;
import com.alganews.functions.service.StorageService;

import java.nio.file.Path;
import java.util.List;

public class ImageDeletedHandler extends AbstractImageHandler {
  
  private static final StorageService storage = new StorageService();
  private static final ImageResizeService imageResizeService = new ImageResizeService();

  @Override
  public void handleEvent(StorageEvent storageEvent) throws Exception {
    String bucketName = storageEvent.getBucket();
    Path fileName = storageEvent.getFilePath();
    
    List<String> fileNames = imageResizeService.getResizedVersionsReference(fileName);
    storage.delete(bucketName, fileNames);
    
  }
}

