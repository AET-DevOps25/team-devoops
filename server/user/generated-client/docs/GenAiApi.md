# GenAiApi

All URIs are relative to *https://meetatmensa.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getApiV2GenaiConversationStarter**](GenAiApi.md#getApiV2GenaiConversationStarter) | **GET** /api/v2/genai/conversation-starter | Request conversation starter |


<a id="getApiV2GenaiConversationStarter"></a>
# **getApiV2GenaiConversationStarter**
> ConversationStarterCollection getApiV2GenaiConversationStarter(userCollection)

Request conversation starter

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
    defaultClient.setBasePath("https://meetatmensa.com");
    

    GenAiApi apiInstance = new GenAiApi(defaultClient);
    UserCollection userCollection = new UserCollection(); // UserCollection | Request Conversation starter for these users
    try {
      ConversationStarterCollection result = apiInstance.getApiV2GenaiConversationStarter(userCollection);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling GenAiApi#getApiV2GenaiConversationStarter");
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
| **userCollection** | [**UserCollection**](UserCollection.md)| Request Conversation starter for these users | [optional] |

### Return type

[**ConversationStarterCollection**](ConversationStarterCollection.md)

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
| **404** | The requested resource was not found.  |  -  |
| **500** | Internal Server Error |  -  |

