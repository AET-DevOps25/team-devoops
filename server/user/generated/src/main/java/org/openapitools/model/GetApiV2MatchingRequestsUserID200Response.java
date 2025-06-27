package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.model.MatchRequest;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * GetApiV2MatchingRequestsUserID200Response
 */

@JsonTypeName("get_api_v2_matching_requests_userID_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-27T14:31:35.907606713+02:00[Europe/Berlin]", comments = "Generator version: 7.14.0")
public class GetApiV2MatchingRequestsUserID200Response {

  @Valid
  private List<@Valid MatchRequest> matches = new ArrayList<>();

  public GetApiV2MatchingRequestsUserID200Response matches(List<@Valid MatchRequest> matches) {
    this.matches = matches;
    return this;
  }

  public GetApiV2MatchingRequestsUserID200Response addMatchesItem(MatchRequest matchesItem) {
    if (this.matches == null) {
      this.matches = new ArrayList<>();
    }
    this.matches.add(matchesItem);
    return this;
  }

  /**
   * Get matches
   * @return matches
   */
  @Valid 
  @Schema(name = "matches", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("matches")
  public List<@Valid MatchRequest> getMatches() {
    return matches;
  }

  public void setMatches(List<@Valid MatchRequest> matches) {
    this.matches = matches;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetApiV2MatchingRequestsUserID200Response getApiV2MatchingRequestsUserID200Response = (GetApiV2MatchingRequestsUserID200Response) o;
    return Objects.equals(this.matches, getApiV2MatchingRequestsUserID200Response.matches);
  }

  @Override
  public int hashCode() {
    return Objects.hash(matches);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetApiV2MatchingRequestsUserID200Response {\n");
    sb.append("    matches: ").append(toIndentedString(matches)).append("\n");
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

