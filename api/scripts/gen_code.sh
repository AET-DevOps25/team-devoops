#!/bin/bash

#
openapi-generator-cli generate -g spring -i api/openapi.yaml --additional-properties=useTags=true,buildTool=gradle,interfaceOnly=true -o server/matching/generated