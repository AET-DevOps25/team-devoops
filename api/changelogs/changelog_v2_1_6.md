## Info
### Version
v2.1.6
### Date
2025-07-01
### Autor
James Stark
## Changelog:

### Paths
- Add 409 response and 200 body to api/v2/matching/request/submit

``` diff

@@ api/v2/matching/request/submit @@
+ add return object to 200: MatchingRequset
+ add 409 CONFLICT response when a user already has a match on a given day


```
