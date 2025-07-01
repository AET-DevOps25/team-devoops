#!/bin/bash

# generate client code and build files based on openapi spec for springboot backend services

# dependencies:
#   - npm:
#       - @openapitools/openapi-generator-cli
#   - other:
#       - gradle

# fail command if any part fails
set -euo pipefail

for path in matching user gateway; do

    # echo generating code
    echo "Generating client code for $path..."

    # generate client code based on OpenAPI specification
    openapi-generator-cli generate \
        -g java \
        -i api/openapi.yaml \
        -o server/$path/generated-client \
        --additional-properties=groupId=org.openapitools.client,artifactId=openapi-client-$path

    # echo generating gradle
    echo "Generating gradle project files for $path..."

    # generate gradle build files.
    gradle init \
        --type pom \
        --dsl groovy \
        --no-incubating \
        --project-dir server/$path/generated-client

done