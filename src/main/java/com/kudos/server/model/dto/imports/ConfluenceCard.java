package com.kudos.server.model.dto.imports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * data format of confluence export
 */
// ignore unknown properties, we grab only what we want to
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfluenceCard {

  @JsonProperty("id")
  public long id;

  @JsonProperty("userKey")
  public String userKey;

  @JsonProperty("userName")
  public String userName;

  @JsonProperty("created")
  public String created;

  @JsonProperty("updated")
  public String updated;

  @JsonProperty("fields")
  public List<ConfluenceFormValue> fields;


}
