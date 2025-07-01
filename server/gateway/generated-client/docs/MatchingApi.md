# MatchingApi

All URIs are relative to *https://meetatmensa.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteApiV2MatchingRequestRequestId**](MatchingApi.md#deleteApiV2MatchingRequestRequestId) | **DELETE** /api/v2/matching/request/{request-id} | Delete MatchRequest with {request-id} |
| [**getApiV2MatchingMatchesUserID**](MatchingApi.md#getApiV2MatchingMatchesUserID) | **GET** /api/v2/matching/matches/{user-id} | Retrieve all matches for a {user-id} |
| [**getApiV2MatchingRequestsUserID**](MatchingApi.md#getApiV2MatchingRequestsUserID) | **GET** /api/v2/matching/requests/{user-id} | Retrieve all MatchRequests for a {user-id} |
| [**getApiV2MatchingRsvpMatchIdAccept**](MatchingApi.md#getApiV2MatchingRsvpMatchIdAccept) | **GET** /api/v2/matching/rsvp/{match-id}/accept | Accept invitation to a given match |
| [**getApiV2MatchingRsvpMatchIdReject**](MatchingApi.md#getApiV2MatchingRsvpMatchIdReject) | **GET** /api/v2/matching/rsvp/{match-id}/reject | Reject invitation to a given match |
| [**postApiV2MatchingRequestSubmit**](MatchingApi.md#postApiV2MatchingRequestSubmit) | **POST** /api/v2/matching/request/submit | Submit matching Request |
| [**putApiV2MatchingRequestRequestId**](MatchingApi.md#putApiV2MatchingRequestRequestId) | **PUT** /api/v2/matching/request/{request-id} | Update MatchRequest with {request-id} |


<a id="deleteApiV2MatchingRequestRequestId"></a>
# **deleteApiV2MatchingRequestRequestId**
> deleteApiV2MatchingRequestRequestId(requestId)

Delete MatchRequest with {request-id}

Delete MatchRequest with ID {request-id} from the system

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MatchingApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://meetatmensa.com");
    

    MatchingApi apiInstance = new MatchingApi(defaultClient);
    UUID requestId = UUID.randomUUID(); // UUID | UUID associated with a given match request
    try {
      apiInstance.deleteApiV2MatchingRequestRequestId(requestId);
    } catch (ApiException e) {
      System.err.println("Exception when calling MatchingApi#deleteApiV2MatchingRequestRequestId");
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
| **requestId** | **UUID**| UUID associated with a given match request | |

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
| **200** | Request Deleted |  -  |
| **400** | The request was malformed or contained invalid parameters.  |  -  |
| **401** | Authentication failed due to missing or invalid OAuth2 token.  |  -  |
| **404** | The requested resource was not found.  |  -  |
| **500** | Internal Server Error |  -  |

<a id="getApiV2MatchingMatchesUserID"></a>
# **getApiV2MatchingMatchesUserID**
> MatchCollection getApiV2MatchingMatchesUserID(userId)

Retrieve all matches for a {user-id}

Retrieve all matches for a user with {user-id} from the matching-service

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MatchingApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://meetatmensa.com");
    

    MatchingApi apiInstance = new MatchingApi(defaultClient);
    UUID userId = UUID.randomUUID(); // UUID | UUID associated with a given user
    try {
      MatchCollection result = apiInstance.getApiV2MatchingMatchesUserID(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MatchingApi#getApiV2MatchingMatchesUserID");
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

[**MatchCollection**](MatchCollection.md)

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

<a id="getApiV2MatchingRequestsUserID"></a>
# **getApiV2MatchingRequestsUserID**
> MatchRequestCollection getApiV2MatchingRequestsUserID(userId)

Retrieve all MatchRequests for a {user-id}

Retrieve all MatchRequests for a user with {user-id} from the matching-service

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MatchingApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://meetatmensa.com");
    

    MatchingApi apiInstance = new MatchingApi(defaultClient);
    UUID userId = UUID.randomUUID(); // UUID | UUID associated with a given user
    try {
      MatchRequestCollection result = apiInstance.getApiV2MatchingRequestsUserID(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MatchingApi#getApiV2MatchingRequestsUserID");
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

[**MatchRequestCollection**](MatchRequestCollection.md)

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

<a id="getApiV2MatchingRsvpMatchIdAccept"></a>
# **getApiV2MatchingRsvpMatchIdAccept**
> getApiV2MatchingRsvpMatchIdAccept(matchId)

Accept invitation to a given match

Accept invitation to a given match

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MatchingApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://meetatmensa.com");
    

    MatchingApi apiInstance = new MatchingApi(defaultClient);
    UUID matchId = UUID.randomUUID(); // UUID | UUID associated with a given match
    try {
      apiInstance.getApiV2MatchingRsvpMatchIdAccept(matchId);
    } catch (ApiException e) {
      System.err.println("Exception when calling MatchingApi#getApiV2MatchingRsvpMatchIdAccept");
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
| **matchId** | **UUID**| UUID associated with a given match | |

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
| **200** | OK |  -  |
| **401** | Authentication failed due to missing or invalid OAuth2 token.  |  -  |
| **404** | The requested resource was not found.  |  -  |
| **500** | Internal Server Error |  -  |

<a id="getApiV2MatchingRsvpMatchIdReject"></a>
# **getApiV2MatchingRsvpMatchIdReject**
> getApiV2MatchingRsvpMatchIdReject(matchId)

Reject invitation to a given match

Reject invitation to a given match

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MatchingApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://meetatmensa.com");
    

    MatchingApi apiInstance = new MatchingApi(defaultClient);
    UUID matchId = UUID.randomUUID(); // UUID | UUID associated with a given match
    try {
      apiInstance.getApiV2MatchingRsvpMatchIdReject(matchId);
    } catch (ApiException e) {
      System.err.println("Exception when calling MatchingApi#getApiV2MatchingRsvpMatchIdReject");
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
| **matchId** | **UUID**| UUID associated with a given match | |

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
| **200** | OK |  -  |
| **401** | Authentication failed due to missing or invalid OAuth2 token.  |  -  |
| **404** | The requested resource was not found.  |  -  |
| **500** | Internal Server Error |  -  |

<a id="postApiV2MatchingRequestSubmit"></a>
# **postApiV2MatchingRequestSubmit**
> MatchRequest postApiV2MatchingRequestSubmit(matchRequestNew)

Submit matching Request

Submit a new matching request to the Matching-Service

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MatchingApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://meetatmensa.com");
    

    MatchingApi apiInstance = new MatchingApi(defaultClient);
    MatchRequestNew matchRequestNew = new MatchRequestNew(); // MatchRequestNew | 
    try {
      MatchRequest result = apiInstance.postApiV2MatchingRequestSubmit(matchRequestNew);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MatchingApi#postApiV2MatchingRequestSubmit");
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
| **matchRequestNew** | [**MatchRequestNew**](MatchRequestNew.md)|  | [optional] |

### Return type

[**MatchRequest**](MatchRequest.md)

### Authorization

[auth0](../README.md#auth0)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Request submitted sucessfully |  -  |
| **400** | The request was malformed or contained invalid parameters.  |  -  |
| **401** | Authentication failed due to missing or invalid OAuth2 token.  |  -  |
| **409** | Conflict |  -  |
| **500** | Internal Server Error |  -  |

<a id="putApiV2MatchingRequestRequestId"></a>
# **putApiV2MatchingRequestRequestId**
> MatchRequest putApiV2MatchingRequestRequestId(requestId, matchRequestUpdate)

Update MatchRequest with {request-id}

Update all information in the MatchRequest with ID {request-id}

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MatchingApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://meetatmensa.com");
    

    MatchingApi apiInstance = new MatchingApi(defaultClient);
    UUID requestId = UUID.randomUUID(); // UUID | UUID associated with a given match request
    MatchRequestUpdate matchRequestUpdate = new MatchRequestUpdate(); // MatchRequestUpdate | 
    try {
      MatchRequest result = apiInstance.putApiV2MatchingRequestRequestId(requestId, matchRequestUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MatchingApi#putApiV2MatchingRequestRequestId");
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
| **requestId** | **UUID**| UUID associated with a given match request | |
| **matchRequestUpdate** | [**MatchRequestUpdate**](MatchRequestUpdate.md)|  | [optional] |

### Return type

[**MatchRequest**](MatchRequest.md)

### Authorization

[auth0](../README.md#auth0)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ok |  -  |
| **400** | The request was malformed or contained invalid parameters.  |  -  |
| **401** | Authentication failed due to missing or invalid OAuth2 token.  |  -  |
| **404** | The requested resource was not found.  |  -  |
| **406** | MatchRequest cannot be updated since it has already been fulfilled! |  -  |
| **500** | Internal Server Error |  -  |

