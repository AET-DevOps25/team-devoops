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
import org.openapitools.model.Location;
import org.openapitools.model.MatchPreferences;
import org.openapitools.model.RequestStatus;
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
 * Object representing a request for matching a given user on a given date in the Meet@Mensa system.
 */

@Schema(name = "MatchRequest", description = "Object representing a request for matching a given user on a given date in the Meet@Mensa system.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-06T15:25:37.929356633Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class MatchRequest {

  private UUID requestID;

  private UUID userID;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;

  @Valid
  private List<@Min(1) @Max(16)Integer> timeslot = new ArrayList<>();

  private Location location;

  private MatchPreferences preferences;

  private RequestStatus status;

  public MatchRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MatchRequest(UUID requestID, UUID userID, LocalDate date, List<@Min(1) @Max(16)Integer> timeslot, Location location, MatchPreferences preferences, RequestStatus status) {
    this.requestID = requestID;
    this.userID = userID;
    this.date = date;
    this.timeslot = timeslot;
    this.location = location;
    this.preferences = preferences;
    this.status = status;
  }

  public MatchRequest requestID(UUID requestID) {
    this.requestID = requestID;
    return this;
  }

  /**
   * The unique ID of a MatchRequest in the Meet@Mensa system.
   * @return requestID
   */
  @NotNull @Valid 
  @Schema(name = "requestID", description = "The unique ID of a MatchRequest in the Meet@Mensa system.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("requestID")
  public UUID getRequestID() {
    return requestID;
  }

  public void setRequestID(UUID requestID) {
    this.requestID = requestID;
  }

  public MatchRequest userID(UUID userID) {
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

  public MatchRequest date(LocalDate date) {
    this.date = date;
    return this;
  }

  /**
   * The date a user would like meet@mensa to find them a match
   * @return date
   */
  @NotNull @Valid 
  @Schema(name = "date", description = "The date a user would like meet@mensa to find them a match", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("date")
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public MatchRequest timeslot(List<@Min(1) @Max(16)Integer> timeslot) {
    this.timeslot = timeslot;
    return this;
  }

  public MatchRequest addTimeslotItem(Integer timeslotItem) {
    if (this.timeslot == null) {
      this.timeslot = new ArrayList<>();
    }
    this.timeslot.add(timeslotItem);
    return this;
  }

  /**
   * Get timeslot
   * @return timeslot
   */
  @NotNull 
  @Schema(name = "timeslot", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("timeslot")
  public List<@Min(1) @Max(16)Integer> getTimeslot() {
    return timeslot;
  }

  public void setTimeslot(List<@Min(1) @Max(16)Integer> timeslot) {
    this.timeslot = timeslot;
  }

  public MatchRequest location(Location location) {
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

  public MatchRequest preferences(MatchPreferences preferences) {
    this.preferences = preferences;
    return this;
  }

  /**
   * Get preferences
   * @return preferences
   */
  @NotNull @Valid 
  @Schema(name = "preferences", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("preferences")
  public MatchPreferences getPreferences() {
    return preferences;
  }

  public void setPreferences(MatchPreferences preferences) {
    this.preferences = preferences;
  }

  public MatchRequest status(RequestStatus status) {
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
  public RequestStatus getStatus() {
    return status;
  }

  public void setStatus(RequestStatus status) {
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
    MatchRequest matchRequest = (MatchRequest) o;
    return Objects.equals(this.requestID, matchRequest.requestID) &&
        Objects.equals(this.userID, matchRequest.userID) &&
        Objects.equals(this.date, matchRequest.date) &&
        Objects.equals(this.timeslot, matchRequest.timeslot) &&
        Objects.equals(this.location, matchRequest.location) &&
        Objects.equals(this.preferences, matchRequest.preferences) &&
        Objects.equals(this.status, matchRequest.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestID, userID, date, timeslot, location, preferences, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MatchRequest {\n");
    sb.append("    requestID: ").append(toIndentedString(requestID)).append("\n");
    sb.append("    userID: ").append(toIndentedString(userID)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    timeslot: ").append(toIndentedString(timeslot)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    preferences: ").append(toIndentedString(preferences)).append("\n");
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

