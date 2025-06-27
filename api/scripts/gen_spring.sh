#!/bin/bash

# generate code and build files based on openapi spec for springboot backend services

# dependencies:
#   - npm:
#       - @openapitools/openapi-generator-cli
#       - @redocly/cli
#   - other:
#       - gradle

# echo linting
echo "Linting api/openapi.yaml..."

# fail command if any part fails
set -euo pipefail

# lint openapi spec
redocly lint api/openapi.yaml

for path in matching user gateway; do

    # echo generating code
    echo "Generating code for $path..."

    # generate code based on OpenAPI specification
    openapi-generator-cli generate \
        -g spring \
        -i api/openapi.yaml \
        -o server/$path/generated \
        --additional-properties=useTags=true,interfaceOnly=true

    # echo generating gradle
    echo "Generating gradle project files for $path..."

    # generate gradle build files.
    gradle init \
        --type pom \
        --dsl groovy \
        --no-incubating \
        --project-dir server/$path/generated

done