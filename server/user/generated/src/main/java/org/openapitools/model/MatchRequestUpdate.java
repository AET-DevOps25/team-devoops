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

@Schema(name = "MatchRequestUpdate", description = "Object representing a request for matching a given user on a given date in the Meet@Mensa system.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-29T10:44:09.399320448Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class MatchRequestUpdate {

  private @Nullable UUID userID;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate date;

  @Valid
  private List<@Min(1) @Max(16)Integer> timeslot = new ArrayList<>();

  private @Nullable Location location;

  private @Nullable MatchPreferences preferences;

  public MatchRequestUpdate userID(@Nullable UUID userID) {
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

  public MatchRequestUpdate date(@Nullable LocalDate date) {
    this.date = date;
    return this;
  }

  /**
   * The date a user would like meet@mensa to find them a match
   * @return date
   */
  @Valid 
  @Schema(name = "date", description = "The date a user would like meet@mensa to find them a match", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("date")
  public @Nullable LocalDate getDate() {
    return date;
  }

  public void setDate(@Nullable LocalDate date) {
    this.date = date;
  }

  public MatchRequestUpdate timeslot(List<@Min(1) @Max(16)Integer> timeslot) {
    this.timeslot = timeslot;
    return this;
  }

  public MatchRequestUpdate addTimeslotItem(Integer timeslotItem) {
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
  
  @Schema(name = "timeslot", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("timeslot")
  public List<@Min(1) @Max(16)Integer> getTimeslot() {
    return timeslot;
  }

  public void setTimeslot(List<@Min(1) @Max(16)Integer> timeslot) {
    this.timeslot = timeslot;
  }

  public MatchRequestUpdate location(@Nullable Location location) {
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

  public MatchRequestUpdate preferences(@Nullable MatchPreferences preferences) {
    this.preferences = preferences;
    return this;
  }

  /**
   * Get preferences
   * @return preferences
   */
  @Valid 
  @Schema(name = "preferences", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("preferences")
  public @Nullable MatchPreferences getPreferences() {
    return preferences;
  }

  public void setPreferences(@Nullable MatchPreferences preferences) {
    this.preferences = preferences;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MatchRequestUpdate matchRequestUpdate = (MatchRequestUpdate) o;
    return Objects.equals(this.userID, matchRequestUpdate.userID) &&
        Objects.equals(this.date, matchRequestUpdate.date) &&
        Objects.equals(this.timeslot, matchRequestUpdate.timeslot) &&
        Objects.equals(this.location, matchRequestUpdate.location) &&
        Objects.equals(this.preferences, matchRequestUpdate.preferences);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userID, date, timeslot, location, preferences);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MatchRequestUpdate {\n");
    sb.append("    userID: ").append(toIndentedString(userID)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    timeslot: ").append(toIndentedString(timeslot)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    preferences: ").append(toIndentedString(preferences)).append("\n");
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

