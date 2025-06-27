#!/bin/bash


for path in matching user; do

    # echo generating code
    echo "Generating code for $path..."

    # generate code based on OpenAPI specification
    openapi-generator-cli generate \
        -g spring \
        -i api/openapi.yaml \
        -o server/{$path}/generated \
        --additional-properties=useTags=true,interfaceOnly=true

    # echo generating gradle
    echo "Generating gradle project files for $path..."

    # generate gradle build files.
    gradle init \
        --type pom \
        --dsl groovy \
        --no-incubating
        --project-dir server/{$path}/generated

done