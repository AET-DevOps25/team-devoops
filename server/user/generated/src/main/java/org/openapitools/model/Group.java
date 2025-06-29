package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.openapitools.model.ConversationStarterCollection;
import org.openapitools.model.Location;
import org.openapitools.model.MatchStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Object representing a group that has been matched in the Meet@Mensa system.
 */

@Schema(name = "Group", description = "Object representing a group that has been matched in the Meet@Mensa system.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-29T10:44:09.399320448Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class Group {

  private @Nullable UUID groupID;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate date;

  private @Nullable Integer time;

  private @Nullable Location location;

  @Valid
  private List<@Valid MatchStatus> userStatus = new ArrayList<>();

  private @Nullable ConversationStarterCollection conversationStarters;

  public Group groupID(@Nullable UUID groupID) {
    this.groupID = groupID;
    return this;
  }

  /**
   * The unique ID of a Group in the Meet@Mensa system.
   * @return groupID
   */
  @Valid 
  @Schema(name = "groupID", description = "The unique ID of a Group in the Meet@Mensa system.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("groupID")
  public @Nullable UUID getGroupID() {
    return groupID;
  }

  public void setGroupID(@Nullable UUID groupID) {
    this.groupID = groupID;
  }

  public Group date(@Nullable LocalDate date) {
    this.date = date;
    return this;
  }

  /**
   * Date the group is scheduled to meet at
   * @return date
   */
  @Valid 
  @Schema(name = "date", description = "Date the group is scheduled to meet at", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("date")
  public @Nullable LocalDate getDate() {
    return date;
  }

  public void setDate(@Nullable LocalDate date) {
    this.date = date;
  }

  public Group time(@Nullable Integer time) {
    this.time = time;
    return this;
  }

  /**
   * What times a user is available to be matched  Value | Start Time | End Time ---------|----------|--------- | 1     | 10:00      | 10:15    | | 2     | 10:15      | 10:30    | | 3     | 10:30      | 10:45    | | 4     | 10:45      | 11:00    | | 5     | 11:00      | 11:15    | | 6     | 11:15      | 11:30    | | 7     | 11:30      | 11:45    | | 8     | 11:45      | 12:00    | | 9     | 12:00      | 12:15    | | 10    | 12:15      | 12:30    | | 11    | 12:30      | 12:45    | | 12    | 12:45      | 13:00    | | 13    | 13:00      | 13:15    | | 14    | 13:15      | 13:30    | | 15    | 13:30      | 13:45    | | 16    | 13:45      | 14:00    |
   * minimum: 1
   * maximum: 16
   * @return time
   */
  @Min(1) @Max(16) 
  @Schema(name = "time", description = "What times a user is available to be matched  Value | Start Time | End Time ---------|----------|--------- | 1     | 10:00      | 10:15    | | 2     | 10:15      | 10:30    | | 3     | 10:30      | 10:45    | | 4     | 10:45      | 11:00    | | 5     | 11:00      | 11:15    | | 6     | 11:15      | 11:30    | | 7     | 11:30      | 11:45    | | 8     | 11:45      | 12:00    | | 9     | 12:00      | 12:15    | | 10    | 12:15      | 12:30    | | 11    | 12:30      | 12:45    | | 12    | 12:45      | 13:00    | | 13    | 13:00      | 13:15    | | 14    | 13:15      | 13:30    | | 15    | 13:30      | 13:45    | | 16    | 13:45      | 14:00    |", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("time")
  public @Nullable Integer getTime() {
    return time;
  }

  public void setTime(@Nullable Integer time) {
    this.time = time;
  }

  public Group location(@Nullable Location location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   * @return location
   */
  @Valid 
  @Schema(name = "location", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("location")
  public @Nullable Location getLocation() {
    return location;
  }

  public void setLocation(@Nullable Location location) {
    this.location = location;
  }

  public Group userStatus(List<@Valid MatchStatus> userStatus) {
    this.userStatus = userStatus;
    return this;
  }

  public Group addUserStatusItem(MatchStatus userStatusItem) {
    if (this.userStatus == null) {
      this.userStatus = new ArrayList<>();
    }
    this.userStatus.add(userStatusItem);
    return this;
  }

  /**
   * Get userStatus
   * @return userStatus
   */
  @Valid @Size(min = 2) 
  @Schema(name = "userStatus", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("userStatus")
  public List<@Valid MatchStatus> getUserStatus() {
    return userStatus;
  }

  public void setUserStatus(List<@Valid MatchStatus> userStatus) {
    this.userStatus = userStatus;
  }

  public Group conversationStarters(@Nullable ConversationStarterCollection conversationStarters) {
    this.conversationStarters = conversationStarters;
    return this;
  }

  /**
   * Get conversationStarters
   * @return conversationStarters
   */
  @Valid 
  @Schema(name = "conversationStarters", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("conversationStarters")
  public @Nullable ConversationStarterCollection getConversationStarters() {
    return conversationStarters;
  }

  public void setConversationStarters(@Nullable ConversationStarterCollection conversationStarters) {
    this.conversationStarters = conversationStarters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Group group = (Group) o;
    return Objects.equals(this.groupID, group.groupID) &&
        Objects.equals(this.date, group.date) &&
        Objects.equals(this.time, group.time) &&
        Objects.equals(this.location, group.location) &&
        Objects.equals(this.userStatus, group.userStatus) &&
        Objects.equals(this.conversationStarters, group.conversationStarters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupID, date, time, location, userStatus, conversationStarters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Group {\n");
    sb.append("    groupID: ").append(toIndentedString(groupID)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    userStatus: ").append(toIndentedString(userStatus)).append("\n");
    sb.append("    conversationStarters: ").append(toIndentedString(conversationStarters)).append("\n");
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

