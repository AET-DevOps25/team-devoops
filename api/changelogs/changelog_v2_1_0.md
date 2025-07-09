## Info
### Version
v2.1.0
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
+ insert entry for each model into openapi.yaml
+ update refernces to point at new flattened model
# this helps prevent openapi-codegen from generating multiple model files for each relative path

@ Update / New @@
- remove all models with template NewObject
- remove all models with template UpdateObject
+ insert models with template ObjectNew
+ insert models with template ObjectUpdate
# this helps keep things together alphabetically

```

### Paths
- Replace {array[Object]} with ObjectCollections
    - this should not change return types, just make code-generation cleaner
- updated refs to point to new flatend models in Openapi.yaml

``` diff

@@ All @@
- remove model references to subfolders ../models/{collections | values | masks | objects}/model
+ insert model references to new flattened structure within openapi.yaml
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
