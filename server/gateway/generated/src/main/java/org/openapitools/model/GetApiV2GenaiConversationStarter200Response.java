package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.model.ConversationStarter1;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * GetApiV2GenaiConversationStarter200Response
 */

@JsonTypeName("get_api_v2_genai_conversation_starter_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-27T14:50:29.454419621+02:00[Europe/Berlin]", comments = "Generator version: 7.14.0")
public class GetApiV2GenaiConversationStarter200Response {

  private @Nullable ConversationStarter1 conversationStarters;

  public GetApiV2GenaiConversationStarter200Response conversationStarters(@Nullable ConversationStarter1 conversationStarters) {
    this.conversationStarters = conversationStarters;
    return this;
  }

  /**
   * Get conversationStarters
   * @return conversationStarters
   */
  @Valid 
  @Schema(name = "conversationStarters", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("conversationStarters")
  public @Nullable ConversationStarter1 getConversationStarters() {
    return conversationStarters;
  }

  public void setConversationStarters(@Nullable ConversationStarter1 conversationStarters) {
    this.conversationStarters = conversationStarters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetApiV2GenaiConversationStarter200Response getApiV2GenaiConversationStarter200Response = (GetApiV2GenaiConversationStarter200Response) o;
    return Objects.equals(this.conversationStarters, getApiV2GenaiConversationStarter200Response.conversationStarters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conversationStarters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetApiV2GenaiConversationStarter200Response {\n");
    sb.append("    conversationStarters: ").append(toIndentedString(conversationStarters)).append("\n");
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

