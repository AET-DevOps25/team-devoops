## Info
### Version
v2.0
### Date
2025-06-26
### Autor
James Stark
## Changelog:

### Models
- moved models out of openapi.yaml for increased portability
- switched to masks to benefit from dependency
- added models for updating entries
- other tweaks to model fields

``` diff

@@ General: @@
- Remove Models from local Openapi.yaml file
+ add Models to components list
# increase re-usability for models across more than one Openapi.yaml


@@ location: @@
+ capitalize value: GARCHING
+ capitalize value: ARCISSTR
# capitalize Enum values


@@ User: @@
- remove field: name
+ insert field: firstname
+ insert field: lastname
# split first and lastname fields

+ insert field: degreeStart
# this should allow for calculating which semester a user is in

- remove field: blurb
+ insert field: bio
# rename blurb to bio for Anastasiia


@@ NewUser: @@
- remove model: NewUser
+ insert mask: User.NewUser
# convert to mask to benefit from inheritance


@@ MatchRequest: @@
- remove field: Group
# group used to be used as an improvised form of status. This is no longer needed as it's covered by the status field.


@@ NewMatchRequest: @@
- remove model: NewMatchRequest
+ insert mask: MatchRequest.NewMatchRequest
# convert to mask to benefit from inheritance


@@ Match: @@
+ insert field: matchID
# allow editing existing matches


@@ userStatus: @@
- remove model: userStatus
+ insert mask: Match.matchStatus
# convert to mask to benefit from inheritance


@@ ConversationStarter @@
+ insert model: conversationStarter
+ insert field: prompts ([String])
# add an object for GenAI conversation starters to allow for easier changes in the future


@@ Group @@
- remove field: conversationStarter ([String])
+ insert field: conversationStarters ([ConversationStarter])
# convert conversationStarter into an object to allow for easier changes in the future


@@ UpdateMatchRequest: @@
+ insert mask: MatchRequest.UpdateMatchRequest
# Added a mask of MatchRequest with optional fields to allow updating an existing request


@@ UpdateUser: @@
+ insert mask: User.UpdateUser
# Added a mask of User with optional fields to allow updating an existing user
```

### Paths
- update paths to use new models
- add paths to update users and MatchRequests
- add paths for RSVP functionality

``` diff

@@ General: @@
+ update paths to use new models
+ update all paths to v2
- remove all v1 paths


@@ /api/v2/user/{user-id} @@
@@ PUT @@
- remove request object User
+ add request object UpdateUser
# use the new UpdateUser mask


@@ /api/v2/matching/request/{request-id} @@
@@ PUT @@
+ add endpoint PUT
+ add request object UpdateMatchRequest
+ add 200 response with MatchRequest
+ add 400, 401, 404 responses
+ add 406 (Not Acceaptable) response for when request is fulfilled
# use the new UpdateMatchRequest mask, add 406 for when the request has already been fulfilled


@@ /api/v2/matching/rsvp/{match-id}/accept @@
+ add path /api/v2/matching/rsvp/{match-id}/accept
@@ GET @@
+ add endpoint GET
+ add 200 response
+ add 401, 404 responses
# Add path and endpoint for RSVPing functionality (accept)


@@ /api/v2/matching/rsvp/{match-id}/reject @@
+ add path /api/v2/matching/rsvp/{match-id}/reject
@@ GET @@
+ add endpoint GET
+ add 200 response
+ add 401, 404 responses
# Add path and endpoint for RSVPing functionality (reject)

```
