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
- Flattened directory structure
    - this avoids openapi-codegen from generating several different model files when referencing across subfolders

``` diff

@@ Collections: @@
+ add model UserCollection
+ add model MatchCollection
+ add model MatchRequestCollection
+ add model ConversationStarterCollection
# add collection objects {array[Object]} to make return types that contain several objects cleaner
# this should not change the return types themselves, just make codegen cleaner

@@ All @@
- remove model.yaml from subfolders ../models/{collections | values | masks | objects}/model
+ insert model.yaml into to new flattened model folder ../models/model
+ update refernces to point at new flattened model
# this helps prevent openapi-codegen from generating multiple model files for each relative path

```

### Paths
- Replace {array[Object]} with ObjectCollections
    - this should not change return types, just make code-generation cleaner
- updated refs to point to new flatend model directory

``` diff

@@ All @@
- remove model references to subfolders ../models/{collections | values | masks | objects}/model
+ insert model references to new flattened model folder ../models/model
# this helps prevent openapi-codegen from generating multiple model files for each relative path

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
