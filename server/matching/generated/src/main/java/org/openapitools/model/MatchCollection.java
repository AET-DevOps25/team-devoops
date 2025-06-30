package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.model.Match;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Object representing a collection of matches in the Meet@Mensa system.
 */

@Schema(name = "MatchCollection", description = "Object representing a collection of matches in the Meet@Mensa system.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-30T16:41:00.409372153Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class MatchCollection {

  @Valid
  private List<@Valid Match> matches = new ArrayList<>();

  public MatchCollection() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MatchCollection(List<@Valid Match> matches) {
    this.matches = matches;
  }

  public MatchCollection matches(List<@Valid Match> matches) {
    this.matches = matches;
    return this;
  }

  public MatchCollection addMatchesItem(Match matchesItem) {
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
  @NotNull @Valid 
  @Schema(name = "matches", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("matches")
  public List<@Valid Match> getMatches() {
    return matches;
  }

  public void setMatches(List<@Valid Match> matches) {
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
    MatchCollection matchCollection = (MatchCollection) o;
    return Objects.equals(this.matches, matchCollection.matches);
  }

  @Override
  public int hashCode() {
    return Objects.hash(matches);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MatchCollection {\n");
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

