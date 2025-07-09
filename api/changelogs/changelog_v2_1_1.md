## Info
### Version
v2.1.1
### Date
2025-06-27
### Autor
James Stark
## Changelog:

### Paths
- Add 500 responses to all endpoints
- Change 201 response to 200 for register user

``` diff

@@ All @@
+ insert code 500 response
# Formally handle errors

@@ /api/v2/user/registr POST @@
- remove code 201 response
+ insert code 200 response

```
