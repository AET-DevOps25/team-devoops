## Info
### Version
v2.1.4
### Date
2025-07-01
### Autor
James Stark
## Changelog:

### Models
- Bug: remove userID value from MatchRequestUpdate

``` diff

@@ MatchRequestUpdate @@
- remove attribute userID: UUID
# User should not be changeable!


```
