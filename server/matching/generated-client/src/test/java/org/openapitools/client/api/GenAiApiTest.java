/*
 * MeetAtMensa
 * This OpenAPI specification defines the endpoints, schemas, and security mechanisms for the Meet@Mensa User micro-service. 
 *
 * The version of the OpenAPI document: 2.1.5
 * Contact: devoops@tyrean.mozmail.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.ConversationStarterCollection;
import org.openapitools.client.model.UserCollection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for GenAiApi
 */
@Disabled
public class GenAiApiTest {

    private final GenAiApi api = new GenAiApi();

    /**
     * Request conversation starter
     *
     * Request a series of conversation starter prompts from the GenAI microservice. Provide infomation about users on request.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getApiV2GenaiConversationStarterTest() throws ApiException {
        UserCollection userCollection = null;
        ConversationStarterCollection response = api.postApiV2GenaiConversationStarter(userCollection);
        // TODO: test validations
    }

}
