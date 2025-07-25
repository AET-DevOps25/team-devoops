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
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openapitools.client.JSON;

/**
 * Object representing a student user in the Meet@Mensa system.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-07-20T16:12:03.521616557Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class UserUpdate {
  public static final String SERIALIZED_NAME_EMAIL = "email";
  @SerializedName(SERIALIZED_NAME_EMAIL)
  @javax.annotation.Nullable
  private String email;

  public static final String SERIALIZED_NAME_FIRSTNAME = "firstname";
  @SerializedName(SERIALIZED_NAME_FIRSTNAME)
  @javax.annotation.Nullable
  private String firstname;

  public static final String SERIALIZED_NAME_LASTNAME = "lastname";
  @SerializedName(SERIALIZED_NAME_LASTNAME)
  @javax.annotation.Nullable
  private String lastname;

  public static final String SERIALIZED_NAME_BIRTHDAY = "birthday";
  @SerializedName(SERIALIZED_NAME_BIRTHDAY)
  @javax.annotation.Nullable
  private LocalDate birthday;

  public static final String SERIALIZED_NAME_GENDER = "gender";
  @SerializedName(SERIALIZED_NAME_GENDER)
  @javax.annotation.Nullable
  private String gender = "other";

  public static final String SERIALIZED_NAME_DEGREE = "degree";
  @SerializedName(SERIALIZED_NAME_DEGREE)
  @javax.annotation.Nullable
  private String degree;

  public static final String SERIALIZED_NAME_DEGREE_START = "degreeStart";
  @SerializedName(SERIALIZED_NAME_DEGREE_START)
  @javax.annotation.Nullable
  private Integer degreeStart;

  public static final String SERIALIZED_NAME_INTERESTS = "interests";
  @SerializedName(SERIALIZED_NAME_INTERESTS)
  @javax.annotation.Nullable
  private List<String> interests = new ArrayList<>();

  public static final String SERIALIZED_NAME_BIO = "bio";
  @SerializedName(SERIALIZED_NAME_BIO)
  @javax.annotation.Nullable
  private String bio;

  public UserUpdate() {
  }

  public UserUpdate email(@javax.annotation.Nullable String email) {
    this.email = email;
    return this;
  }

  /**
   * Users&#39;s e-mail
   * @return email
   */
  @javax.annotation.Nullable
  public String getEmail() {
    return email;
  }

  public void setEmail(@javax.annotation.Nullable String email) {
    this.email = email;
  }


  public UserUpdate firstname(@javax.annotation.Nullable String firstname) {
    this.firstname = firstname;
    return this;
  }

  /**
   * User&#39;s given name
   * @return firstname
   */
  @javax.annotation.Nullable
  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(@javax.annotation.Nullable String firstname) {
    this.firstname = firstname;
  }


  public UserUpdate lastname(@javax.annotation.Nullable String lastname) {
    this.lastname = lastname;
    return this;
  }

  /**
   * User&#39;s surname
   * @return lastname
   */
  @javax.annotation.Nullable
  public String getLastname() {
    return lastname;
  }

  public void setLastname(@javax.annotation.Nullable String lastname) {
    this.lastname = lastname;
  }


  public UserUpdate birthday(@javax.annotation.Nullable LocalDate birthday) {
    this.birthday = birthday;
    return this;
  }

  /**
   * User&#39;s date of birth
   * @return birthday
   */
  @javax.annotation.Nullable
  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(@javax.annotation.Nullable LocalDate birthday) {
    this.birthday = birthday;
  }


  public UserUpdate gender(@javax.annotation.Nullable String gender) {
    this.gender = gender;
    return this;
  }

  /**
   * User&#39;s gender
   * @return gender
   */
  @javax.annotation.Nullable
  public String getGender() {
    return gender;
  }

  public void setGender(@javax.annotation.Nullable String gender) {
    this.gender = gender;
  }


  public UserUpdate degree(@javax.annotation.Nullable String degree) {
    this.degree = degree;
    return this;
  }

  /**
   * User&#39;s degree program
   * @return degree
   */
  @javax.annotation.Nullable
  public String getDegree() {
    return degree;
  }

  public void setDegree(@javax.annotation.Nullable String degree) {
    this.degree = degree;
  }


  public UserUpdate degreeStart(@javax.annotation.Nullable Integer degreeStart) {
    this.degreeStart = degreeStart;
    return this;
  }

  /**
   * What year User started their degree
   * minimum: 2000
   * maximum: 2100
   * @return degreeStart
   */
  @javax.annotation.Nullable
  public Integer getDegreeStart() {
    return degreeStart;
  }

  public void setDegreeStart(@javax.annotation.Nullable Integer degreeStart) {
    this.degreeStart = degreeStart;
  }


  public UserUpdate interests(@javax.annotation.Nullable List<String> interests) {
    this.interests = interests;
    return this;
  }

  public UserUpdate addInterestsItem(String interestsItem) {
    if (this.interests == null) {
      this.interests = new ArrayList<>();
    }
    this.interests.add(interestsItem);
    return this;
  }

  /**
   * Array of a User&#39;s interests
   * @return interests
   */
  @javax.annotation.Nullable
  public List<String> getInterests() {
    return interests;
  }

  public void setInterests(@javax.annotation.Nullable List<String> interests) {
    this.interests = interests;
  }


  public UserUpdate bio(@javax.annotation.Nullable String bio) {
    this.bio = bio;
    return this;
  }

  /**
   * Short introduction text written by the user
   * @return bio
   */
  @javax.annotation.Nullable
  public String getBio() {
    return bio;
  }

  public void setBio(@javax.annotation.Nullable String bio) {
    this.bio = bio;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserUpdate userUpdate = (UserUpdate) o;
    return Objects.equals(this.email, userUpdate.email) &&
        Objects.equals(this.firstname, userUpdate.firstname) &&
        Objects.equals(this.lastname, userUpdate.lastname) &&
        Objects.equals(this.birthday, userUpdate.birthday) &&
        Objects.equals(this.gender, userUpdate.gender) &&
        Objects.equals(this.degree, userUpdate.degree) &&
        Objects.equals(this.degreeStart, userUpdate.degreeStart) &&
        Objects.equals(this.interests, userUpdate.interests) &&
        Objects.equals(this.bio, userUpdate.bio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, firstname, lastname, birthday, gender, degree, degreeStart, interests, bio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserUpdate {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstname: ").append(toIndentedString(firstname)).append("\n");
    sb.append("    lastname: ").append(toIndentedString(lastname)).append("\n");
    sb.append("    birthday: ").append(toIndentedString(birthday)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    degree: ").append(toIndentedString(degree)).append("\n");
    sb.append("    degreeStart: ").append(toIndentedString(degreeStart)).append("\n");
    sb.append("    interests: ").append(toIndentedString(interests)).append("\n");
    sb.append("    bio: ").append(toIndentedString(bio)).append("\n");
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


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>(Arrays.asList("email", "firstname", "lastname", "birthday", "gender", "degree", "degreeStart", "interests", "bio"));

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>(0);
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to UserUpdate
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!UserUpdate.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in UserUpdate is not found in the empty JSON string", UserUpdate.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!UserUpdate.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `UserUpdate` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("email") != null && !jsonObj.get("email").isJsonNull()) && !jsonObj.get("email").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `email` to be a primitive type in the JSON string but got `%s`", jsonObj.get("email").toString()));
      }
      if ((jsonObj.get("firstname") != null && !jsonObj.get("firstname").isJsonNull()) && !jsonObj.get("firstname").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `firstname` to be a primitive type in the JSON string but got `%s`", jsonObj.get("firstname").toString()));
      }
      if ((jsonObj.get("lastname") != null && !jsonObj.get("lastname").isJsonNull()) && !jsonObj.get("lastname").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `lastname` to be a primitive type in the JSON string but got `%s`", jsonObj.get("lastname").toString()));
      }
      if ((jsonObj.get("gender") != null && !jsonObj.get("gender").isJsonNull()) && !jsonObj.get("gender").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `gender` to be a primitive type in the JSON string but got `%s`", jsonObj.get("gender").toString()));
      }
      if ((jsonObj.get("degree") != null && !jsonObj.get("degree").isJsonNull()) && !jsonObj.get("degree").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `degree` to be a primitive type in the JSON string but got `%s`", jsonObj.get("degree").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("interests") != null && !jsonObj.get("interests").isJsonNull() && !jsonObj.get("interests").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `interests` to be an array in the JSON string but got `%s`", jsonObj.get("interests").toString()));
      }
      if ((jsonObj.get("bio") != null && !jsonObj.get("bio").isJsonNull()) && !jsonObj.get("bio").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `bio` to be a primitive type in the JSON string but got `%s`", jsonObj.get("bio").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!UserUpdate.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'UserUpdate' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<UserUpdate> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(UserUpdate.class));

       return (TypeAdapter<T>) new TypeAdapter<UserUpdate>() {
           @Override
           public void write(JsonWriter out, UserUpdate value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public UserUpdate read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of UserUpdate given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of UserUpdate
   * @throws IOException if the JSON string is invalid with respect to UserUpdate
   */
  public static UserUpdate fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, UserUpdate.class);
  }

  /**
   * Convert an instance of UserUpdate to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

