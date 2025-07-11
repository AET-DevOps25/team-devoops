

# Group

Object representing a group that has been matched in the Meet@Mensa system.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**groupID** | **UUID** | The unique ID of a Group in the Meet@Mensa system. |  |
|**date** | **LocalDate** | Date the group is scheduled to meet at |  |
|**time** | **Integer** | What times a user is available to be matched  Value | Start Time | End Time ---------|----------|--------- | 1     | 10:00      | 10:15    | | 2     | 10:15      | 10:30    | | 3     | 10:30      | 10:45    | | 4     | 10:45      | 11:00    | | 5     | 11:00      | 11:15    | | 6     | 11:15      | 11:30    | | 7     | 11:30      | 11:45    | | 8     | 11:45      | 12:00    | | 9     | 12:00      | 12:15    | | 10    | 12:15      | 12:30    | | 11    | 12:30      | 12:45    | | 12    | 12:45      | 13:00    | | 13    | 13:00      | 13:15    | | 14    | 13:15      | 13:30    | | 15    | 13:30      | 13:45    | | 16    | 13:45      | 14:00    | |  |
|**location** | **Location** |  |  |
|**userStatus** | [**List&lt;MatchStatus&gt;**](MatchStatus.md) |  |  |
|**conversationStarters** | [**ConversationStarterCollection**](ConversationStarterCollection.md) |  |  |



