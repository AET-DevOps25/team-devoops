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
 * Enumerator representing the status of a Invitation | Value | Description | ---------|----------| | UNSENT | The system has not sent out this invitation yet | | SENT | The system has sent out this invitation | | CONFIRMED | The user has confirmed this invitation | | REJECTED | The user has rejected this invitation | | EXPIRED | The date for this invitation is in the past |
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-01T11:33:37.224933887Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public enum InviteStatus {
  
  UNSENT("UNSENT"),
  
  SENT("SENT"),
  
  CONFIRMED("CONFIRMED"),
  
  REJECTED("REJECTED"),
  
  EXPIRED("EXPIRED");

  private final String value;

  InviteStatus(String value) {
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
  public static InviteStatus fromValue(String value) {
    for (InviteStatus b : InviteStatus.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

