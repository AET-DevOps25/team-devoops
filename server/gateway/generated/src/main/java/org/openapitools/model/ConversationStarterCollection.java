package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.model.ConversationStarter;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Object representing a collection of conversation starters in the Meet@Mensa system.
 */

@Schema(name = "ConversationStarterCollection", description = "Object representing a collection of conversation starters in the Meet@Mensa system.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-01T11:47:14.577743198Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class ConversationStarterCollection {

  @Valid
  private List<@Valid ConversationStarter> conversationsStarters = new ArrayList<>();

  public ConversationStarterCollection() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ConversationStarterCollection(List<@Valid ConversationStarter> conversationsStarters) {
    this.conversationsStarters = conversationsStarters;
  }

  public ConversationStarterCollection conversationsStarters(List<@Valid ConversationStarter> conversationsStarters) {
    this.conversationsStarters = conversationsStarters;
    return this;
  }

  public ConversationStarterCollection addConversationsStartersItem(ConversationStarter conversationsStartersItem) {
    if (this.conversationsStarters == null) {
      this.conversationsStarters = new ArrayList<>();
    }
    this.conversationsStarters.add(conversationsStartersItem);
    return this;
  }

  /**
   * Get conversationsStarters
   * @return conversationsStarters
   */
  @NotNull @Valid 
  @Schema(name = "conversationsStarters", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("conversationsStarters")
  public List<@Valid ConversationStarter> getConversationsStarters() {
    return conversationsStarters;
  }

  public void setConversationsStarters(List<@Valid ConversationStarter> conversationsStarters) {
    this.conversationsStarters = conversationsStarters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConversationStarterCollection conversationStarterCollection = (ConversationStarterCollection) o;
    return Objects.equals(this.conversationsStarters, conversationStarterCollection.conversationsStarters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conversationsStarters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConversationStarterCollection {\n");
    sb.append("    conversationsStarters: ").append(toIndentedString(conversationsStarters)).append("\n");
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

