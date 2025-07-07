# DefaultApi

All URIs are relative to *https://meetatmensa.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getApiV2UserMeAuthId**](DefaultApi.md#getApiV2UserMeAuthId) | **GET** /api/v2/user/me/{auth-id} | Retrieve User based on AuthID |


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
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://meetatmensa.com");
    

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String authId = "authId_example"; // String | User's Auth0 sub ID
    try {
      User result = apiInstance.getApiV2UserMeAuthId(authId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getApiV2UserMeAuthId");
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

