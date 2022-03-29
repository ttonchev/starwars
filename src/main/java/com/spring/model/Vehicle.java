package com.spring.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "vehicle")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle implements Serializable {
  @Transient
  public static final String SEQUENCE_NAME = "vehicle_sequence";
  @Id
  private Long id;
  private String name;
  private Integer count;

  private Vehicle() {}

  public Vehicle(String name, Integer count) {
    this.name = name;
    this.count = count;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String nome) {
    this.name = nome;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  @Override
  public String toString() {
    return "Vehicle [id=" + id + ", name=" + name + ", count=" + count + "]";
  }

}