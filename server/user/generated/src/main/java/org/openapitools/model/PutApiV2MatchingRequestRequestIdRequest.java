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
import org.openapitools.model.MatchPreferences2;
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

@Schema(name = "put_api_v2_matching_request_request_id_request", description = "Object representing a request for matching a given user on a given date in the Meet@Mensa system.")
@JsonTypeName("put_api_v2_matching_request_request_id_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-27T14:31:35.907606713+02:00[Europe/Berlin]", comments = "Generator version: 7.14.0")
public class PutApiV2MatchingRequestRequestIdRequest {

  private @Nullable UUID userID;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate date;

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

  private @Nullable LocationEnum location;

  private @Nullable MatchPreferences2 preferences;

  public PutApiV2MatchingRequestRequestIdRequest userID(@Nullable UUID userID) {
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

  public PutApiV2MatchingRequestRequestIdRequest date(@Nullable LocalDate date) {
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

  public PutApiV2MatchingRequestRequestIdRequest timeslot(List<@Min(1) @Max(16)Integer> timeslot) {
    this.timeslot = timeslot;
    return this;
  }

  public PutApiV2MatchingRequestRequestIdRequest addTimeslotItem(Integer timeslotItem) {
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

  public PutApiV2MatchingRequestRequestIdRequest location(@Nullable LocationEnum location) {
    this.location = location;
    return this;
  }

  /**
   * Enumerator representing a mensa at which a meet can happen  Value | Description | ---------|----------| | GARCHING | The Mensa at the TUM Garching Campus | | ARCISSTR | The Mensa at the TUM Innenstadt Campus (Arcisstr. 21) | 
   * @return location
   */
  
  @Schema(name = "location", description = "Enumerator representing a mensa at which a meet can happen  Value | Description | ---------|----------| | GARCHING | The Mensa at the TUM Garching Campus | | ARCISSTR | The Mensa at the TUM Innenstadt Campus (Arcisstr. 21) | ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("location")
  public @Nullable LocationEnum getLocation() {
    return location;
  }

  public void setLocation(@Nullable LocationEnum location) {
    this.location = location;
  }

  public PutApiV2MatchingRequestRequestIdRequest preferences(@Nullable MatchPreferences2 preferences) {
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
  public @Nullable MatchPreferences2 getPreferences() {
    return preferences;
  }

  public void setPreferences(@Nullable MatchPreferences2 preferences) {
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
    PutApiV2MatchingRequestRequestIdRequest putApiV2MatchingRequestRequestIdRequest = (PutApiV2MatchingRequestRequestIdRequest) o;
    return Objects.equals(this.userID, putApiV2MatchingRequestRequestIdRequest.userID) &&
        Objects.equals(this.date, putApiV2MatchingRequestRequestIdRequest.date) &&
        Objects.equals(this.timeslot, putApiV2MatchingRequestRequestIdRequest.timeslot) &&
        Objects.equals(this.location, putApiV2MatchingRequestRequestIdRequest.location) &&
        Objects.equals(this.preferences, putApiV2MatchingRequestRequestIdRequest.preferences);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userID, date, timeslot, location, preferences);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PutApiV2MatchingRequestRequestIdRequest {\n");
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

