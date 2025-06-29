#!/bin/bash

# fail command if any part fails
set -euo pipefail

# install with npm
npm install \
    @openapitools/openapi-generator-cli \
    @redocly/cli \
    openapi-typescript \
    typescript \
    -g