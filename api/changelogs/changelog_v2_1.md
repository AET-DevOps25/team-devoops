## Info
### Version
v2.1
### Date
2025-06-27
### Autor
James Stark
## Changelog:

### Models
- Add new Collection models
    - these are returned by paths instead of {array[Object]}
    - this should not change return types, just make code-generation cleaner

``` diff

@@ Collections: @@
+ add model UserCollection
+ add model MatchCollection
+ add model MatchRequestCollection
+ add model ConversationStarterCollection
# add collection objects {array[Object]} to make return types that contain several objects cleaner
# this should not change the return types themselves, just make codegen cleaner


```

### Paths
- Replace {array[Object]} with ObjectCollections
    - this should not change return types, just make code-generation cleaner

``` diff

@@ /api/v2/matching/matches/{user-id} GET @@
- remove response object {array[Match]}
+ insert response object MatchCollection

@@ /api/v2/matching/requests/{user-id} GET @@
- remove response object {array[MatchRequest]}
+ insert response object MatchRequestCollection

@@ /api/v2/genai/conversation-starter GET @@
- remove request object {array[User]}
+ insert request object UserCollection
- remove response object {array[ConversationStarter]}
+ insert response object ConversationStarterCollection


```
