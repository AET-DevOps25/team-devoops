package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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

@Schema(name = "post_api_v2_matching_request_request", description = "Object representing a request for matching a given user on a given date in the Meet@Mensa system.")
@JsonTypeName("post_api_v2_matching_request_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-27T14:50:19.777600418+02:00[Europe/Berlin]", comments = "Generator version: 7.14.0")
public class PostApiV2MatchingRequestRequest {

  private UUID userID;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;

  @Valid
  private List<@Min(1) @Max(16)Integer> timeslot = new ArrayList<>();

  /**
   * Enumerator representing a mensa at which a meet can happen  Value | Description | ---------|----------| | GARCHING | The Mensa at the TUM Garching Campus | | ARCISSTR | The Mensa at the TUM Innenstadt Campus (Arcisstr. 21) | 
   */
  public enum LocationEnum {
    GARCHING("GARCHING"),
    
    ARCISSTR("ARCISSTR");

    private final String value;

    LocationEnum(String value) {
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
    public static LocationEnum fromValue(String value) {
      for (LocationEnum b : LocationEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private LocationEnum location;

  private MatchPreferences preferences;

  public PostApiV2MatchingRequestRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PostApiV2MatchingRequestRequest(UUID userID, LocalDate date, List<@Min(1) @Max(16)Integer> timeslot, LocationEnum location, MatchPreferences preferences) {
    this.userID = userID;
    this.date = date;
    this.timeslot = timeslot;
    this.location = location;
    this.preferences = preferences;
  }

  public PostApiV2MatchingRequestRequest userID(UUID userID) {
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

  public PostApiV2MatchingRequestRequest date(LocalDate date) {
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

  public PostApiV2MatchingRequestRequest timeslot(List<@Min(1) @Max(16)Integer> timeslot) {
    this.timeslot = timeslot;
    return this;
  }

  public PostApiV2MatchingRequestRequest addTimeslotItem(Integer timeslotItem) {
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

  public PostApiV2MatchingRequestRequest location(LocationEnum location) {
    this.location = location;
    return this;
  }

  /**
   * Enumerator representing a mensa at which a meet can happen  Value | Description | ---------|----------| | GARCHING | The Mensa at the TUM Garching Campus | | ARCISSTR | The Mensa at the TUM Innenstadt Campus (Arcisstr. 21) | 
   * @return location
   */
  @NotNull 
  @Schema(name = "location", description = "Enumerator representing a mensa at which a meet can happen  Value | Description | ---------|----------| | GARCHING | The Mensa at the TUM Garching Campus | | ARCISSTR | The Mensa at the TUM Innenstadt Campus (Arcisstr. 21) | ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("location")
  public LocationEnum getLocation() {
    return location;
  }

  public void setLocation(LocationEnum location) {
    this.location = location;
  }

  public PostApiV2MatchingRequestRequest preferences(MatchPreferences preferences) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostApiV2MatchingRequestRequest postApiV2MatchingRequestRequest = (PostApiV2MatchingRequestRequest) o;
    return Objects.equals(this.userID, postApiV2MatchingRequestRequest.userID) &&
        Objects.equals(this.date, postApiV2MatchingRequestRequest.date) &&
        Objects.equals(this.timeslot, postApiV2MatchingRequestRequest.timeslot) &&
        Objects.equals(this.location, postApiV2MatchingRequestRequest.location) &&
        Objects.equals(this.preferences, postApiV2MatchingRequestRequest.preferences);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userID, date, timeslot, location, preferences);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostApiV2MatchingRequestRequest {\n");
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

