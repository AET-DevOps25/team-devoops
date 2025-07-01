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
 * Enumerator representing the status of a MatchRequest | Value | Description | ---------|----------| | PENDING | The system has not attempted to match this request yet | | MATCHED | The system has fulfilled this request | | UNMATCHABLE | The system was unable to fulfill this request | | REMATCH | The sytem should attempt to fulfill this request again (ex: group cancelled for lacking RSVPs) | | EXPIRED | The date for this MatchRequest is in the past | 
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-01T08:27:59.749909476Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public enum RequestStatus {
  
  PENDING("PENDING"),
  
  MATCHED("MATCHED"),
  
  UNMATCHABLE("UNMATCHABLE"),
  
  REMATCH("REMATCH"),
  
  EXPIRED("EXPIRED");

  private final String value;

  RequestStatus(String value) {
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
  public static RequestStatus fromValue(String value) {
    for (RequestStatus b : RequestStatus.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

