## Info
### Version
v2.3.0
### Date
2025-19-07
### Autor
James Stark
## Changelog:

### Endpoints
- Add new POST endpoint to matching to enable demo functionality
- Add new GET endpoint to user to enable fetching pre-made demo users


``` diff

@@ POST @ /api/v2/matching/demo @@
+ Add new POST operation with a MatchRequestNew payload
# Generates a match fitting this MatchRequest with placeholder users

@@ Get @ /api/v2/users/demo @@
+ Add new GET operation with no payload that returns demo users
# Returns 3 pre-made demo-users

```