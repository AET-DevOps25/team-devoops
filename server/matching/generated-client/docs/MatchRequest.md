

# MatchRequest

Object representing a request for matching a given user on a given date in the Meet@Mensa system.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**requestID** | **UUID** | The unique ID of a MatchRequest in the Meet@Mensa system. |  |
|**userID** | **UUID** | The unique ID of a single student in the Meet@Mensa system. |  |
|**date** | **LocalDate** | The date a user would like meet@mensa to find them a match |  |
|**timeslot** | **List&lt;Integer&gt;** |  |  |
|**location** | **Location** |  |  |
|**preferences** | [**MatchPreferences**](MatchPreferences.md) |  |  |
|**status** | **RequestStatus** |  |  |



