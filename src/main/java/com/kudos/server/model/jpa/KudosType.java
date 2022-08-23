package com.kudos.server.model.jpa;

import java.util.Arrays;
import java.util.List;

public enum KudosType {
  AWESOME("awesome", "Awesome!"),
  THANK_YOU("thankyou", "Thank you!"),
  APPRECIATION("appreciation", "I appreciate"),
  HAPPY("happy", "I am Happy"),
  JOKER("joker", "Joker");

  private final String folder;
  private String formattedText;

  KudosType(String folder, String formattedText) {
    this.folder = folder;
    this.formattedText = formattedText;
  }

  public static List<KudosType> typeList() {
    return Arrays.asList(values());
  }

  public String getFolder() {
    return folder;
  }

  public String getFormattedText() {
    return formattedText;
  }

  public static KudosType getTypeFrom(String name) {
    String check = name.toLowerCase();
    for (KudosType value : values()) {
      if (value.folder.equals(check)) return value;
    }
    return THANK_YOU;
  }
}
