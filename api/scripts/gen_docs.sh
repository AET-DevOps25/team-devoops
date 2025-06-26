#!/bin/bash

# install redocly with:
# npm install -g @redocly/cli

# fail if any single command fails
set -euo pipefail

# lint openapi spec
redocly lint api/openapi.yaml

# generate documentation
redocly build-docs api/openapi.yaml -o docs/api.html