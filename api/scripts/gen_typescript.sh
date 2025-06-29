#!/bin/bash

# generate api spec in typescript based on openapi.yaml

# dependencies:
#   - npm:
#       - openapi-typescript
#       - typescript

# lint openapi spec
redocly lint api/openapi.yaml

# echo linting
echo "generating client/src/api.ts..."

# generate using openapi-typescript
openapi-typescript api/openapi.yaml -o client/src/api.ts