# GenAiApi

All URIs are relative to *http://api.meetatmensa.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**postApiV2GenaiConversationStarter**](GenAiApi.md#postApiV2GenaiConversationStarter) | **POST** /api/v2/genai/conversation-starter | Request Conversation Starter |


<a id="postApiV2GenaiConversationStarter"></a>
# **postApiV2GenaiConversationStarter**
> ConversationStarterCollection postApiV2GenaiConversationStarter(userCollection)

Request Conversation Starter

Request a series of conversation starter prompts from the GenAI microservice. Provide infomation about users on request.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.GenAiApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://api.meetatmensa.com");
    
    // Configure HTTP bearer authorization: jwt-bearer
    HttpBearerAuth jwt-bearer = (HttpBearerAuth) defaultClient.getAuthentication("jwt-bearer");
    jwt-bearer.setBearerToken("BEARER TOKEN");

    GenAiApi apiInstance = new GenAiApi(defaultClient);
    UserCollection userCollection = new UserCollection(); // UserCollection | 
    try {
      ConversationStarterCollection result = apiInstance.postApiV2GenaiConversationStarter(userCollection);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling GenAiApi#postApiV2GenaiConversationStarter");
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
| **userCollection** | [**UserCollection**](UserCollection.md)|  | [optional] |

### Return type

[**ConversationStarterCollection**](ConversationStarterCollection.md)

### Authorization

[jwt-bearer](../README.md#jwt-bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **400** | Bad Request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not Found |  -  |
| **500** | Internal Server Error |  -  |

