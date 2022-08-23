package com.kudos.server.model.jpa;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class KudosCard extends KudosItem {


  // imported from external system with ID
  @Basic(optional = true)
  private Long importerID;

  private String writer;

  @Size(max = 512)
  private String message;

  @Enumerated(EnumType.STRING)
  private KudosType type;

  @ManyToOne
  @Nullable
  private Image backgroundImage;

  public Long getImporterID() {
    return importerID;
  }

  public void setImporterID(Long importerID) {
    this.importerID = importerID;
  }

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public KudosType getType() {
    return type;
  }

  public void setType(KudosType type) {
    this.type = type;
  }

  @Nullable
  public Image getBackgroundImage() {
    return backgroundImage;
  }

  public void setBackgroundImage(@NotNull Image backgroundImage) {
    this.backgroundImage = backgroundImage;
  }

}
