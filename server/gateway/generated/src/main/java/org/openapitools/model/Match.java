package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import org.openapitools.model.Group;
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
 * Object representing a single match for a given user on a given date in the Meet@Mensa system.
 */

@Schema(name = "Match", description = "Object representing a single match for a given user on a given date in the Meet@Mensa system.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-29T16:19:23.653238918Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class Match {

  private UUID matchID;

  private UUID userID;

  private InviteStatus status;

  private Group group;

  public Match() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Match(UUID matchID, UUID userID, InviteStatus status, Group group) {
    this.matchID = matchID;
    this.userID = userID;
    this.status = status;
    this.group = group;
  }

  public Match matchID(UUID matchID) {
    this.matchID = matchID;
    return this;
  }

  /**
   * The unique ID of a Group in the Meet@Mensa system.
   * @return matchID
   */
  @NotNull @Valid 
  @Schema(name = "matchID", description = "The unique ID of a Group in the Meet@Mensa system.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("matchID")
  public UUID getMatchID() {
    return matchID;
  }

  public void setMatchID(UUID matchID) {
    this.matchID = matchID;
  }

  public Match userID(UUID userID) {
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

  public Match status(InviteStatus status) {
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

  public Match group(Group group) {
    this.group = group;
    return this;
  }

  /**
   * Get group
   * @return group
   */
  @NotNull @Valid 
  @Schema(name = "group", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("group")
  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Match match = (Match) o;
    return Objects.equals(this.matchID, match.matchID) &&
        Objects.equals(this.userID, match.userID) &&
        Objects.equals(this.status, match.status) &&
        Objects.equals(this.group, match.group);
  }

  @Override
  public int hashCode() {
    return Objects.hash(matchID, userID, status, group);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Match {\n");
    sb.append("    matchID: ").append(toIndentedString(matchID)).append("\n");
    sb.append("    userID: ").append(toIndentedString(userID)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    group: ").append(toIndentedString(group)).append("\n");
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

