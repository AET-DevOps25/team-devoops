package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * Object representing a student user in the Meet@Mensa system.
 */

@Schema(name = "UserUpdate", description = "Object representing a student user in the Meet@Mensa system.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-01T11:47:14.577743198Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class UserUpdate {

  private @Nullable String email;

  private @Nullable String firstname;

  private @Nullable String lastname;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate birthday;

  private String gender = "other";

  private @Nullable String degree;

  private @Nullable Integer degreeStart;

  @Valid
  private List<String> interests = new ArrayList<>();

  private @Nullable String bio;

  public UserUpdate email(@Nullable String email) {
    this.email = email;
    return this;
  }

  /**
   * Users's e-mail
   * @return email
   */
  @javax.validation.constraints.Email 
  @Schema(name = "email", description = "Users's e-mail", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public @Nullable String getEmail() {
    return email;
  }

  public void setEmail(@Nullable String email) {
    this.email = email;
  }

  public UserUpdate firstname(@Nullable String firstname) {
    this.firstname = firstname;
    return this;
  }

  /**
   * User's given name
   * @return firstname
   */
  
  @Schema(name = "firstname", example = "Max", description = "User's given name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("firstname")
  public @Nullable String getFirstname() {
    return firstname;
  }

  public void setFirstname(@Nullable String firstname) {
    this.firstname = firstname;
  }

  public UserUpdate lastname(@Nullable String lastname) {
    this.lastname = lastname;
    return this;
  }

  /**
   * User's surname
   * @return lastname
   */
  
  @Schema(name = "lastname", example = "Mustermann", description = "User's surname", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastname")
  public @Nullable String getLastname() {
    return lastname;
  }

  public void setLastname(@Nullable String lastname) {
    this.lastname = lastname;
  }

  public UserUpdate birthday(@Nullable LocalDate birthday) {
    this.birthday = birthday;
    return this;
  }

  /**
   * User's date of birth
   * @return birthday
   */
  @Valid 
  @Schema(name = "birthday", description = "User's date of birth", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("birthday")
  public @Nullable LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(@Nullable LocalDate birthday) {
    this.birthday = birthday;
  }

  public UserUpdate gender(String gender) {
    this.gender = gender;
    return this;
  }

  /**
   * User's gender
   * @return gender
   */
  
  @Schema(name = "gender", description = "User's gender", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("gender")
  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public UserUpdate degree(@Nullable String degree) {
    this.degree = degree;
    return this;
  }

  /**
   * User's degree program
   * @return degree
   */
  
  @Schema(name = "degree", example = "msc_informatics", description = "User's degree program", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("degree")
  public @Nullable String getDegree() {
    return degree;
  }

  public void setDegree(@Nullable String degree) {
    this.degree = degree;
  }

  public UserUpdate degreeStart(@Nullable Integer degreeStart) {
    this.degreeStart = degreeStart;
    return this;
  }

  /**
   * What year User started their degree
   * minimum: 2000
   * maximum: 2100
   * @return degreeStart
   */
  @Min(2000) @Max(2100) 
  @Schema(name = "degreeStart", example = "2024", description = "What year User started their degree", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("degreeStart")
  public @Nullable Integer getDegreeStart() {
    return degreeStart;
  }

  public void setDegreeStart(@Nullable Integer degreeStart) {
    this.degreeStart = degreeStart;
  }

  public UserUpdate interests(List<String> interests) {
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
   * Array of a User's interests
   * @return interests
   */
  
  @Schema(name = "interests", description = "Array of a User's interests", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("interests")
  public List<String> getInterests() {
    return interests;
  }

  public void setInterests(List<String> interests) {
    this.interests = interests;
  }

  public UserUpdate bio(@Nullable String bio) {
    this.bio = bio;
    return this;
  }

  /**
   * Short introduction text written by the user
   * @return bio
   */
  
  @Schema(name = "bio", description = "Short introduction text written by the user", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bio")
  public @Nullable String getBio() {
    return bio;
  }

  public void setBio(@Nullable String bio) {
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
}

