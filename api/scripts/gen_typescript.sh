#!/bin/bash

# generate api spec in typescript based on openapi.yaml

# dependencies:
#   - npm:
#       - openapi-typescript
#       - typescript

# fail command if any part fails
set -euo pipefail

# echo linting
echo "Linting api/openapi.yaml..."

# lint openapi spec
redocly lint api/openapi.yaml

# echo linting
echo "generating client/src/api.ts..."

# generate using openapi-typescript
openapi-typescript api/openapi.yaml -o client/src/api.ts