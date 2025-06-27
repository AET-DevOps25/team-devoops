package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * MatchStatus
 */

@JsonTypeName("matchStatus")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-27T14:50:25.075958280+02:00[Europe/Berlin]", comments = "Generator version: 7.14.0")
public class MatchStatus {

  private @Nullable UUID userID;

  /**
   * Enumerator representing the status of a Invitation Value | Description | ---------|----------| | UNSENT | The system has not sent out this invitation yet | | SENT | The system has sent out this invitation | | CONFIRMED | The user has confirmed this invitation | | REJECTED | The user has rejected this invitation | | EXPIRED | The date for this invitation is in the past |
   */
  public enum StatusEnum {
    UNSENT("UNSENT"),
    
    SENT("SENT"),
    
    CONFIRMED("CONFIRMED"),
    
    REJECTED("REJECTED"),
    
    EXPIRED("EXPIRED");

    private final String value;

    StatusEnum(String value) {
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
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable StatusEnum status;

  public MatchStatus userID(@Nullable UUID userID) {
    this.userID = userID;
    return this;
  }

  /**
   * The unique ID of a single student in the Meet@Mensa system.
   * @return userID
   */
  @Valid 
  @Schema(name = "userID", description = "The unique ID of a single student in the Meet@Mensa system.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("userID")
  public @Nullable UUID getUserID() {
    return userID;
  }

  public void setUserID(@Nullable UUID userID) {
    this.userID = userID;
  }

  public MatchStatus status(@Nullable StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Enumerator representing the status of a Invitation Value | Description | ---------|----------| | UNSENT | The system has not sent out this invitation yet | | SENT | The system has sent out this invitation | | CONFIRMED | The user has confirmed this invitation | | REJECTED | The user has rejected this invitation | | EXPIRED | The date for this invitation is in the past |
   * @return status
   */
  
  @Schema(name = "status", description = "Enumerator representing the status of a Invitation Value | Description | ---------|----------| | UNSENT | The system has not sent out this invitation yet | | SENT | The system has sent out this invitation | | CONFIRMED | The user has confirmed this invitation | | REJECTED | The user has rejected this invitation | | EXPIRED | The date for this invitation is in the past |", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public @Nullable StatusEnum getStatus() {
    return status;
  }

  public void setStatus(@Nullable StatusEnum status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MatchStatus matchStatus = (MatchStatus) o;
    return Objects.equals(this.userID, matchStatus.userID) &&
        Objects.equals(this.status, matchStatus.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userID, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MatchStatus {\n");
    sb.append("    userID: ").append(toIndentedString(userID)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

