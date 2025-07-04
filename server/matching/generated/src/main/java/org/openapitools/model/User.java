package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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

@Schema(name = "User", description = "Object representing a student user in the Meet@Mensa system.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-02T14:23:27.228170307Z[Etc/UTC]", comments = "Generator version: 7.14.0")
public class User {

  private UUID userID;

  private String email;

  private String firstname;

  private String lastname;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate birthday;

  private String gender = "other";

  private String degree;

  private Integer degreeStart;

  @Valid
  private List<String> interests = new ArrayList<>();

  private String bio;

  public User() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public User(UUID userID, String email, String firstname, String lastname, LocalDate birthday, String gender, String degree, Integer degreeStart, List<String> interests, String bio) {
    this.userID = userID;
    this.email = email;
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthday = birthday;
    this.gender = gender;
    this.degree = degree;
    this.degreeStart = degreeStart;
    this.interests = interests;
    this.bio = bio;
  }

  public User userID(UUID userID) {
    this.userID = userID;
    return this;
  }

  /**
   * The unique ID of a single student in the Meet@Mensa system.
   * @return userID
   */
  @NotNull @Valid 
  @Schema(name = "userID", description = "The unique ID of a single student in the Meet@Mensa system.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("userID")
  public UUID getUserID() {
    return userID;
  }

  public void setUserID(UUID userID) {
    this.userID = userID;
  }

  public User email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Users's e-mail
   * @return email
   */
  @NotNull @javax.validation.constraints.Email 
  @Schema(name = "email", description = "Users's e-mail", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public User firstname(String firstname) {
    this.firstname = firstname;
    return this;
  }

  /**
   * User's given name
   * @return firstname
   */
  @NotNull 
  @Schema(name = "firstname", example = "Max", description = "User's given name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("firstname")
  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public User lastname(String lastname) {
    this.lastname = lastname;
    return this;
  }

  /**
   * User's surname
   * @return lastname
   */
  @NotNull 
  @Schema(name = "lastname", example = "Mustermann", description = "User's surname", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("lastname")
  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public User birthday(LocalDate birthday) {
    this.birthday = birthday;
    return this;
  }

  /**
   * User's date of birth
   * @return birthday
   */
  @NotNull @Valid 
  @Schema(name = "birthday", description = "User's date of birth", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("birthday")
  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public User gender(String gender) {
    this.gender = gender;
    return this;
  }

  /**
   * User's gender
   * @return gender
   */
  @NotNull 
  @Schema(name = "gender", description = "User's gender", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("gender")
  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public User degree(String degree) {
    this.degree = degree;
    return this;
  }

  /**
   * User's degree program
   * @return degree
   */
  @NotNull 
  @Schema(name = "degree", example = "msc_informatics", description = "User's degree program", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("degree")
  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public User degreeStart(Integer degreeStart) {
    this.degreeStart = degreeStart;
    return this;
  }

  /**
   * What year User started their degree
   * minimum: 2000
   * maximum: 2100
   * @return degreeStart
   */
  @NotNull @Min(2000) @Max(2100) 
  @Schema(name = "degreeStart", example = "2024", description = "What year User started their degree", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("degreeStart")
  public Integer getDegreeStart() {
    return degreeStart;
  }

  public void setDegreeStart(Integer degreeStart) {
    this.degreeStart = degreeStart;
  }

  public User interests(List<String> interests) {
    this.interests = interests;
    return this;
  }

  public User addInterestsItem(String interestsItem) {
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
  @NotNull 
  @Schema(name = "interests", description = "Array of a User's interests", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("interests")
  public List<String> getInterests() {
    return interests;
  }

  public void setInterests(List<String> interests) {
    this.interests = interests;
  }

  public User bio(String bio) {
    this.bio = bio;
    return this;
  }

  /**
   * Short introduction text written by the user
   * @return bio
   */
  @NotNull 
  @Schema(name = "bio", description = "Short introduction text written by the user", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("bio")
  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
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
    User user = (User) o;
    return Objects.equals(this.userID, user.userID) &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.firstname, user.firstname) &&
        Objects.equals(this.lastname, user.lastname) &&
        Objects.equals(this.birthday, user.birthday) &&
        Objects.equals(this.gender, user.gender) &&
        Objects.equals(this.degree, user.degree) &&
        Objects.equals(this.degreeStart, user.degreeStart) &&
        Objects.equals(this.interests, user.interests) &&
        Objects.equals(this.bio, user.bio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userID, email, firstname, lastname, birthday, gender, degree, degreeStart, interests, bio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    sb.append("    userID: ").append(toIndentedString(userID)).append("\n");
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

