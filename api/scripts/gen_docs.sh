#!/bin/bash

# install redocly with:
# npm install -g @redocly/cli

# fail if any single command fails
set -euo pipefail

# generate documentation
redocly build-docs api/openapi.yaml -o docs/api.html