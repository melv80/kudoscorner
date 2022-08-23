package com.kudos.server.model.dto.ui;

import com.kudos.server.model.jpa.KudosType;

/**
 * DTO to create a card
 */
public class CreateCard {

  private String message;
  private String writer;

  private KudosType kudostype;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  public KudosType getKudostype() {
    return kudostype;
  }

  public void setKudostype(KudosType kudostype) {
    this.kudostype = kudostype;
  }
}
