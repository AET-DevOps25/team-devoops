## Info
### Version
v2.3.0
### Date
2025-20-07
### Autor
James Stark, Anastasiia Korzhylova
## Changelog:

### Endpoints
- Replace @GET /api/v2/genai/conversation-starter endpoint with POST


``` diff

@@ /api/v2/genai/conversation-starter @@
- Remove GET endpoint
+ add POST endpoint
# fixes issue where GET request body is empty

```