## Info
### Version
v2.1.3
### Date
2025-06-30
### Autor
James Stark
## Changelog:

### Models
- Bug: fix incorrect values for collection models

``` diff

@@ UserCollection @@
- remove attribute Users: User
+ insert attribute Users: [User]
# fix incorrect attribute

@@ MatchCollection @@
- remove attribute Matches: Matches
+ insert attribute Matches: [Matches]
# fix incorrect attribute

```
