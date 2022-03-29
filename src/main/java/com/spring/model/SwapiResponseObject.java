package com.spring.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class SwapiResponseObject<T extends Serializable> {
  private Integer count;
  protected List<T> results;

  public Integer getCount() {
    return count;
  }

  public List<T> getResults() {
    return results != null ? results : Arrays.asList();
  }
}
