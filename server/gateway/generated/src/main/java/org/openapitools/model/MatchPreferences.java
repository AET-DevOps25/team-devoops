package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Object Representing a set of user preferences
 */

@Schema(name = "MatchPreferences", description = "Object Representing a set of user preferences")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-27T17:20:45.213135324Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class MatchPreferences {

  private @Nullable Boolean degreePref;

  private @Nullable Boolean agePref;

  private @Nullable Boolean genderPref;

  public MatchPreferences degreePref(@Nullable Boolean degreePref) {
    this.degreePref = degreePref;
    return this;
  }

  /**
   * Value | Meaning  ---------|---------  true | degree = same (priority)  false | degree = any (no priority)
   * @return degreePref
   */
  
  @Schema(name = "degreePref", description = "Value | Meaning  ---------|---------  true | degree = same (priority)  false | degree = any (no priority)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("degreePref")
  public @Nullable Boolean getDegreePref() {
    return degreePref;
  }

  public void setDegreePref(@Nullable Boolean degreePref) {
    this.degreePref = degreePref;
  }

  public MatchPreferences agePref(@Nullable Boolean agePref) {
    this.agePref = agePref;
    return this;
  }

  /**
   * Value | Meaning  ---------|---------  true | age = same (priority)  false | age = any (no priority)
   * @return agePref
   */
  
  @Schema(name = "agePref", description = "Value | Meaning  ---------|---------  true | age = same (priority)  false | age = any (no priority)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("agePref")
  public @Nullable Boolean getAgePref() {
    return agePref;
  }

  public void setAgePref(@Nullable Boolean agePref) {
    this.agePref = agePref;
  }

  public MatchPreferences genderPref(@Nullable Boolean genderPref) {
    this.genderPref = genderPref;
    return this;
  }

  /**
   * Value | Meaning  ---------|---------  true | gender = same (priority)  false | gender = any (no priority)
   * @return genderPref
   */
  
  @Schema(name = "genderPref", description = "Value | Meaning  ---------|---------  true | gender = same (priority)  false | gender = any (no priority)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("genderPref")
  public @Nullable Boolean getGenderPref() {
    return genderPref;
  }

  public void setGenderPref(@Nullable Boolean genderPref) {
    this.genderPref = genderPref;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MatchPreferences matchPreferences = (MatchPreferences) o;
    return Objects.equals(this.degreePref, matchPreferences.degreePref) &&
        Objects.equals(this.agePref, matchPreferences.agePref) &&
        Objects.equals(this.genderPref, matchPreferences.genderPref);
  }

  @Override
  public int hashCode() {
    return Objects.hash(degreePref, agePref, genderPref);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MatchPreferences {\n");
    sb.append("    degreePref: ").append(toIndentedString(degreePref)).append("\n");
    sb.append("    agePref: ").append(toIndentedString(agePref)).append("\n");
    sb.append("    genderPref: ").append(toIndentedString(genderPref)).append("\n");
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

