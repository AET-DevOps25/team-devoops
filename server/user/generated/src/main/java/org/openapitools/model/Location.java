package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumerator representing a mensa at which a meet can happen  | Value | Description | ---------|----------| | GARCHING | The Mensa at the TUM Garching Campus | | ARCISSTR | The Mensa at the TUM Innenstadt Campus (Arcisstr. 21) | 
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-29T10:04:02.126255198Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public enum Location {
  
  GARCHING("GARCHING"),
  
  ARCISSTR("ARCISSTR");

  private final String value;

  Location(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static Location fromValue(String value) {
    for (Location b : Location.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

