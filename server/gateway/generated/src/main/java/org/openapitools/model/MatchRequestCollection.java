package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * Object representing a collection of match requests in the Meet@Mensa system.
 */

@Schema(name = "MatchRequestCollection", description = "Object representing a collection of match requests in the Meet@Mensa system.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-01T11:33:37.224933887Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class MatchRequestCollection {

  @Valid
  private List<@Valid MatchRequest> requests = new ArrayList<>();

  public MatchRequestCollection() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MatchRequestCollection(List<@Valid MatchRequest> requests) {
    this.requests = requests;
  }

  public MatchRequestCollection requests(List<@Valid MatchRequest> requests) {
    this.requests = requests;
    return this;
  }

  public MatchRequestCollection addRequestsItem(MatchRequest requestsItem) {
    if (this.requests == null) {
      this.requests = new ArrayList<>();
    }
    this.requests.add(requestsItem);
    return this;
  }

  /**
   * Get requests
   * @return requests
   */
  @NotNull @Valid 
  @Schema(name = "requests", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("requests")
  public List<@Valid MatchRequest> getRequests() {
    return requests;
  }

  public void setRequests(List<@Valid MatchRequest> requests) {
    this.requests = requests;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MatchRequestCollection matchRequestCollection = (MatchRequestCollection) o;
    return Objects.equals(this.requests, matchRequestCollection.requests);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requests);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MatchRequestCollection {\n");
    sb.append("    requests: ").append(toIndentedString(requests)).append("\n");
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

