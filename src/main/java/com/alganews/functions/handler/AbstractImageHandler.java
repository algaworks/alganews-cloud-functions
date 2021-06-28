package com.alganews.functions.handler;

import com.alganews.functions.event.StorageEvent;
import com.alganews.functions.service.ImageResizerType;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.io.FilenameUtils.getExtension;

public abstract class AbstractImageHandler implements BackgroundFunction<StorageEvent> {
  
  private static final Logger logger = Logger.getLogger(AbstractImageHandler.class.getName());
  private static final List<String> validExtensions = Arrays.asList("jpg", "jpeg", "png");

  @Override
  public void accept(StorageEvent storageEvent, Context context) {
    try {
      if (isNotValid(storageEvent)) return;
      logger.info(String.format("Iniciando processamento da imagem %s", storageEvent.getFilePath()));
      handleEvent(storageEvent);
      logger.info(String.format("Processamento da imagem %s concluido", storageEvent.getFilePath()));
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Ocorreu um erro durante o processamento da imagem: " + e.getMessage(), e);
    }
  }
  
  abstract void handleEvent(StorageEvent storageEvent) throws Exception;
  
  private boolean isNotValid(StorageEvent storageEvent) {
    if (isEventValid(storageEvent)) {
      logger.severe("Erro: Evento malformatado.");
      return true;
    }
    
    if (canIgnore(storageEvent.getFilePath())) {
      logger.info(String.format("Ignorando imagem %s.", storageEvent.getFilePath()));
      return true;
    }
    
    if (!isExtensionSuported(storageEvent.getFilePath())) {
      logger.warning(String.format("Ignorando imagem %s extensão não suportada.", storageEvent.getFilePath()));
      return true;
    }
    
    return false;
  }
  
  private boolean isEventValid(StorageEvent storageEvent) {
    return storageEvent.getBucket() == null || storageEvent.getName() == null;
  }
  
  private boolean canIgnore(Path filePath) {
    List<String> ignorableFolders = ImageResizerType.valuesAsFolder();
    ignorableFolders.add("/post-body");
    return StringUtils.containsAny(filePath.toString(), ignorableFolders.toArray(new String[0]));
  }
  
  private boolean isExtensionSuported(Path fileName) {
    String extension = getExtension(fileName.getFileName().toString());
    return validExtensions.contains(extension.toLowerCase());
  }
  
  private String removeExtension(String fileName) {
    return fileName.substring(0, fileName.lastIndexOf('.'));
  }
  
}

