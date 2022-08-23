package com.kudos.server.model.dto.imports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfluenceFormValue {
  @JsonProperty("uid")
  public String uid;

  @JsonProperty("values")
  public String[] values;

}
