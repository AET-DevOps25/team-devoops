/*
 * MeetAtMensa
 * This OpenAPI specification defines the endpoints, schemas, and security mechanisms for the Meet@Mensa User micro-service. 
 *
 * The version of the OpenAPI document: 2.3.2
 * Contact: devoops@tyrean.mozmail.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import com.google.gson.TypeAdapter;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Enumerator representing the status of a MatchRequest | Value | Description | ---------|----------| | PENDING | The system has not attempted to match this request yet | | MATCHED | The system has fulfilled this request | | UNMATCHABLE | The system was unable to fulfill this request | | REMATCH | The sytem should attempt to fulfill this request again (ex: group cancelled for lacking RSVPs) | | EXPIRED | The date for this MatchRequest is in the past | 
 */
@JsonAdapter(RequestStatus.Adapter.class)
public enum RequestStatus {
  
  PENDING("PENDING"),
  
  MATCHED("MATCHED"),
  
  UNMATCHABLE("UNMATCHABLE"),
  
  REMATCH("REMATCH"),
  
  EXPIRED("EXPIRED");

  private String value;

  RequestStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static RequestStatus fromValue(String value) {
    for (RequestStatus b : RequestStatus.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }

  public static class Adapter extends TypeAdapter<RequestStatus> {
    @Override
    public void write(final JsonWriter jsonWriter, final RequestStatus enumeration) throws IOException {
      jsonWriter.value(enumeration.getValue());
    }

    @Override
    public RequestStatus read(final JsonReader jsonReader) throws IOException {
      String value = jsonReader.nextString();
      return RequestStatus.fromValue(value);
    }
  }

  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
    String value = jsonElement.getAsString();
    RequestStatus.fromValue(value);
  }
}

