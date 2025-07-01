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
 * Object representing a conversation starter in the meet@mensa system
 */

@Schema(name = "ConversationStarter", description = "Object representing a conversation starter in the meet@mensa system")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-01T11:00:43.322894915Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class ConversationStarter {

  private String prompt;

  public ConversationStarter() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ConversationStarter(String prompt) {
    this.prompt = prompt;
  }

  public ConversationStarter prompt(String prompt) {
    this.prompt = prompt;
    return this;
  }

  /**
   * Get prompt
   * @return prompt
   */
  @NotNull 
  @Schema(name = "prompt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("prompt")
  public String getPrompt() {
    return prompt;
  }

  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConversationStarter conversationStarter = (ConversationStarter) o;
    return Objects.equals(this.prompt, conversationStarter.prompt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(prompt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConversationStarter {\n");
    sb.append("    prompt: ").append(toIndentedString(prompt)).append("\n");
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

