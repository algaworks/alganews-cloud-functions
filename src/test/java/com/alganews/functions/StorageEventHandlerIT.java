/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alganews.functions;

import com.alganews.functions.event.StorageEvent;
import com.alganews.functions.handler.ImageCreatedHandler;
import com.alganews.functions.handler.ImageDeletedHandler;
import com.google.common.testing.TestLogHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class StorageEventHandlerIT {
  private static final Logger logger = Logger.getLogger(ImageCreatedHandler.class.getName());

  private static final TestLogHandler LOG_HANDLER = new TestLogHandler();
  //GOOGLE_APPLICATION_CREDENTIALS=/home/admin/auth/algaworks-gcp.json;BUCKET=alganews;LARGER_SIZE=350;SMALL_SIZE=80;MEDIUM_SIZE=150
  @BeforeClass
  public static void setUp() {
    logger.addHandler(LOG_HANDLER);
  }

  @After
  public void afterTest() {
    LOG_HANDLER.clear();
  }

  @Test
  public void shouldCreateImageVersions() {
    String imageName = "test/small/como-contribuir-para-um-repositorio-no-github.jpg";

    StorageEvent event = new StorageEvent();
    event.setBucket("alganews-files");
    event.setName(imageName);

    new ImageCreatedHandler().accept(event, new MockContext());

    List<LogRecord> logs = LOG_HANDLER.getStoredLogRecords();
    
  }
  
  @Test
  public void shouldDeleteImageVersions() {
    String imageName = "test/especialista-spring-rest.png";
    
    StorageEvent event = new StorageEvent();
    event.setBucket("alganews-files");
    event.setName(imageName);
    
    new ImageDeletedHandler().accept(event, new MockContext());
    
    List<LogRecord> logs = LOG_HANDLER.getStoredLogRecords();
    
  }
}
