# UserApi

All URIs are relative to *https://meetatmensa.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteApiV2UserUserID**](UserApi.md#deleteApiV2UserUserID) | **DELETE** /api/v2/user/{user-id} | Delete User with {user-id} |
| [**getApiV2UserMeAuthId**](UserApi.md#getApiV2UserMeAuthId) | **GET** /api/v2/user/me/{auth-id} | Retrieve User based on AuthID |
| [**getApiV2UserUserID**](UserApi.md#getApiV2UserUserID) | **GET** /api/v2/user/{user-id} | Retrieve User with {user-id} |
| [**postApiV2UserRegister**](UserApi.md#postApiV2UserRegister) | **POST** /api/v2/user/register | Register new User |
| [**putApiV2UserUserID**](UserApi.md#putApiV2UserUserID) | **PUT** /api/v2/user/{user-id} | Update User with {user-id} |


<a id="deleteApiV2UserUserID"></a>
# **deleteApiV2UserUserID**
> deleteApiV2UserUserID(userId)

Delete User with {user-id}

Remove all information about user with ID {user-id} from user-service

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://meetatmensa.com");
    

    UserApi apiInstance = new UserApi(defaultClient);
    UUID userId = UUID.randomUUID(); // UUID | UUID associated with a given user
    try {
      apiInstance.deleteApiV2UserUserID(userId);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#deleteApiV2UserUserID");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userId** | **UUID**| UUID associated with a given user | |

### Return type

null (empty response body)

### Authorization

[auth0](../README.md#auth0)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | User deleted |  -  |
| **400** | The request was malformed or contained invalid parameters.  |  -  |
| **401** | The request was malformed or contained invalid parameters.  |  -  |
| **404** | The requested resource was not found.  |  -  |
| **500** | Internal Server Error |  -  |

<a id="getApiV2UserMeAuthId"></a>
# **getApiV2UserMeAuthId**
> User getApiV2UserMeAuthId(authId)

Retrieve User based on AuthID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://meetatmensa.com");
    

    UserApi apiInstance = new UserApi(defaultClient);
    String authId = "authId_example"; // String | User's Auth0 sub ID
    try {
      User result = apiInstance.getApiV2UserMeAuthId(authId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#getApiV2UserMeAuthId");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **authId** | **String**| User&#39;s Auth0 sub ID | |

### Return type

[**User**](User.md)

### Authorization

[auth0](../README.md#auth0)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **400** | Bad Request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not Found |  -  |

<a id="getApiV2UserUserID"></a>
# **getApiV2UserUserID**
> User getApiV2UserUserID(userId)

Retrieve User with {user-id}

Fetch all information about user with ID {user-id} from user-service

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://meetatmensa.com");
    

    UserApi apiInstance = new UserApi(defaultClient);
    UUID userId = UUID.randomUUID(); // UUID | UUID associated with a given user
    try {
      User result = apiInstance.getApiV2UserUserID(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#getApiV2UserUserID");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userId** | **UUID**| UUID associated with a given user | |

### Return type

[**User**](User.md)

### Authorization

[auth0](../README.md#auth0)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **400** | The request was malformed or contained invalid parameters.  |  -  |
| **401** | Authentication failed due to missing or invalid OAuth2 token.  |  -  |
| **404** | The requested resource was not found.  |  -  |
| **500** | Internal Server Error |  -  |

<a id="postApiV2UserRegister"></a>
# **postApiV2UserRegister**
> User postApiV2UserRegister(userNew)

Register new User

Register a new user and respond with it&#39;s {user-id}

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://meetatmensa.com");
    

    UserApi apiInstance = new UserApi(defaultClient);
    UserNew userNew = new UserNew(); // UserNew | 
    try {
      User result = apiInstance.postApiV2UserRegister(userNew);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#postApiV2UserRegister");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userNew** | [**UserNew**](UserNew.md)|  | [optional] |

### Return type

[**User**](User.md)

### Authorization

[auth0](../README.md#auth0)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **400** | The request was malformed or contained invalid parameters.  |  -  |
| **401** | Authentication failed due to missing or invalid OAuth2 token.  |  -  |
| **409** | Conflict |  -  |
| **500** | Internal Server Error |  -  |

<a id="putApiV2UserUserID"></a>
# **putApiV2UserUserID**
> User putApiV2UserUserID(userId, userUpdate)

Update User with {user-id}

Update all information about user with ID {user-id} from user-service

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://meetatmensa.com");
    

    UserApi apiInstance = new UserApi(defaultClient);
    UUID userId = UUID.randomUUID(); // UUID | UUID associated with a given user
    UserUpdate userUpdate = new UserUpdate(); // UserUpdate | 
    try {
      User result = apiInstance.putApiV2UserUserID(userId, userUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#putApiV2UserUserID");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userId** | **UUID**| UUID associated with a given user | |
| **userUpdate** | [**UserUpdate**](UserUpdate.md)|  | [optional] |

### Return type

[**User**](User.md)

### Authorization

[auth0](../README.md#auth0)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | User updated successfully |  -  |
| **400** | The request was malformed or contained invalid parameters.  |  -  |
| **401** | Authentication failed due to missing or invalid OAuth2 token.  |  -  |
| **404** | The requested resource was not found.  |  -  |
| **5XX** | Server Error |  -  |

