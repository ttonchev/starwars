package com.spring;

import javax.validation.constraints.NotNull;

import org.testcontainers.containers.GenericContainer;

public class MongoDbContainer extends GenericContainer<MongoDbContainer> {

  public static final int MONGODB_PORT = 27017;
  public static final String DEFAULT_IMAGE_AND_TAG = "mongo:latest";
  public MongoDbContainer() {
    this(DEFAULT_IMAGE_AND_TAG);
  }
  public MongoDbContainer(@NotNull String image) {
    super(image);
    addExposedPort(MONGODB_PORT);
  }
  @NotNull
  public Integer getPort() {
    return getMappedPort(MONGODB_PORT);
  }
}
