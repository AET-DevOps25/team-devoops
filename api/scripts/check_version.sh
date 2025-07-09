#!/bin/bash

set -euo pipefail

# Get the API version of the current spec:
SPEC_VERSION=$(yq '.info.version' api/openapi.yaml)

# Get the API version of the last generated spec:
LAST_VERSION=$(yq '.info.version' api/flags/flags.yaml)


if [ "$SPEC_VERSION" = "$LAST_VERSION" ]; then

    echo OK

else

    echo UPDATE

fi
