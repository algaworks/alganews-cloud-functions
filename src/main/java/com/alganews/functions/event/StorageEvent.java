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

package com.alganews.functions.event;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StorageEvent {
  
  private String bucket;
  private String name;

  public String getBucket() {
    return bucket;
  }

  public void setBucket(String bucket) {
    this.bucket = bucket;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public Path getFilePath() {
    return Paths.get(name);
  }
}
