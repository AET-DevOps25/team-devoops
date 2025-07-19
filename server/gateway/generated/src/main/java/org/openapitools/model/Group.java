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
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-19T16:54:23.471850629Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class Group {

  private UUID groupID;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;

  private Integer time;

  private Location location;

  @Valid
  private List<@Valid MatchStatus> userStatus = new ArrayList<>();

  private ConversationStarterCollection conversationStarters;

  public Group() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Group(UUID groupID, LocalDate date, Integer time, Location location, List<@Valid MatchStatus> userStatus, ConversationStarterCollection conversationStarters) {
    this.groupID = groupID;
    this.date = date;
    this.time = time;
    this.location = location;
    this.userStatus = userStatus;
    this.conversationStarters = conversationStarters;
  }

  public Group groupID(UUID groupID) {
    this.groupID = groupID;
    return this;
  }

  /**
   * The unique ID of a Group in the Meet@Mensa system.
   * @return groupID
   */
  @NotNull @Valid 
  @Schema(name = "groupID", description = "The unique ID of a Group in the Meet@Mensa system.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("groupID")
  public UUID getGroupID() {
    return groupID;
  }

  public void setGroupID(UUID groupID) {
    this.groupID = groupID;
  }

  public Group date(LocalDate date) {
    this.date = date;
    return this;
  }

  /**
   * Date the group is scheduled to meet at
   * @return date
   */
  @NotNull @Valid 
  @Schema(name = "date", description = "Date the group is scheduled to meet at", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("date")
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Group time(Integer time) {
    this.time = time;
    return this;
  }

  /**
   * What times a user is available to be matched  Value | Start Time | End Time ---------|----------|--------- | 1     | 10:00      | 10:15    | | 2     | 10:15      | 10:30    | | 3     | 10:30      | 10:45    | | 4     | 10:45      | 11:00    | | 5     | 11:00      | 11:15    | | 6     | 11:15      | 11:30    | | 7     | 11:30      | 11:45    | | 8     | 11:45      | 12:00    | | 9     | 12:00      | 12:15    | | 10    | 12:15      | 12:30    | | 11    | 12:30      | 12:45    | | 12    | 12:45      | 13:00    | | 13    | 13:00      | 13:15    | | 14    | 13:15      | 13:30    | | 15    | 13:30      | 13:45    | | 16    | 13:45      | 14:00    |
   * minimum: 1
   * maximum: 16
   * @return time
   */
  @NotNull @Min(1) @Max(16) 
  @Schema(name = "time", description = "What times a user is available to be matched  Value | Start Time | End Time ---------|----------|--------- | 1     | 10:00      | 10:15    | | 2     | 10:15      | 10:30    | | 3     | 10:30      | 10:45    | | 4     | 10:45      | 11:00    | | 5     | 11:00      | 11:15    | | 6     | 11:15      | 11:30    | | 7     | 11:30      | 11:45    | | 8     | 11:45      | 12:00    | | 9     | 12:00      | 12:15    | | 10    | 12:15      | 12:30    | | 11    | 12:30      | 12:45    | | 12    | 12:45      | 13:00    | | 13    | 13:00      | 13:15    | | 14    | 13:15      | 13:30    | | 15    | 13:30      | 13:45    | | 16    | 13:45      | 14:00    |", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("time")
  public Integer getTime() {
    return time;
  }

  public void setTime(Integer time) {
    this.time = time;
  }

  public Group location(Location location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   * @return location
   */
  @NotNull @Valid 
  @Schema(name = "location", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("location")
  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
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
  @NotNull @Valid @Size(min = 2) 
  @Schema(name = "userStatus", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("userStatus")
  public List<@Valid MatchStatus> getUserStatus() {
    return userStatus;
  }

  public void setUserStatus(List<@Valid MatchStatus> userStatus) {
    this.userStatus = userStatus;
  }

  public Group conversationStarters(ConversationStarterCollection conversationStarters) {
    this.conversationStarters = conversationStarters;
    return this;
  }

  /**
   * Get conversationStarters
   * @return conversationStarters
   */
  @NotNull @Valid 
  @Schema(name = "conversationStarters", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("conversationStarters")
  public ConversationStarterCollection getConversationStarters() {
    return conversationStarters;
  }

  public void setConversationStarters(ConversationStarterCollection conversationStarters) {
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

