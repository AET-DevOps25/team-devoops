## Info
### Version
v2.2.0
### Date
2025-07-07
### Autor
James Stark & Enrico Bausenhart
## Changelog:

### Models
- Add authID field to userNew

``` diff

@@ UserNew @@
+ create field authID
# This means that newly created users will be tied to their Auth0 accounds


```

### Paths
- Add new Endpoint GET@user/me to allow a user to retrieve their own ID based on auth0 sub


``` diff

@@ get-api-v2-user-me-auth-id @@
+ create new path /api/v2/user/me/{auth-id}
+ create new endpoint GET@/api/v2/user/me/{auth-id}
+ Add responses 200 (User)
+ Add responses 400, 403, 404

```