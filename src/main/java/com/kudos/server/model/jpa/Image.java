package com.kudos.server.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Image extends KudosItem {


  @Column(name = "pathondisk",unique=true)
  public String pathOnDisk;

  public String name;

  public int width;

  public int height;

  public int usedInCards;

  public long size;

  public KudosType type;

  public String getUrl() {
    return "/images/"+getId();
  }

  @Override
  public String toString() {
    return "Image{" +
        "name='" + name + '\'' +
        ", type=" + type +
        '}';
  }
}
