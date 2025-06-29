#!/bin/bash

# fail command if any part fails
set -euo pipefail

# echo linting
echo "Linting api/openapi.yaml..."

# lint openapi spec
redocly lint api/openapi.yaml
