package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import org.openapitools.model.InviteStatus;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-07T12:52:57.425330906Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class MatchStatus {

  private UUID userID;

  private InviteStatus status;

  public MatchStatus() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MatchStatus(UUID userID, InviteStatus status) {
    this.userID = userID;
    this.status = status;
  }

  public MatchStatus userID(UUID userID) {
    this.userID = userID;
    return this;
  }

  /**
   * The unique ID of a single student in the Meet@Mensa system.
   * @return userID
   */
  @NotNull @Valid 
  @Schema(name = "userID", description = "The unique ID of a single student in the Meet@Mensa system.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("userID")
  public UUID getUserID() {
    return userID;
  }

  public void setUserID(UUID userID) {
    this.userID = userID;
  }

  public MatchStatus status(InviteStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  @NotNull @Valid 
  @Schema(name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public InviteStatus getStatus() {
    return status;
  }

  public void setStatus(InviteStatus status) {
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

