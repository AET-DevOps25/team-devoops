#!/bin/sh
set -e
API_BASE_URL=${API_BASE_URL:-https://api.meetatmensa.com/api/v2}
sed "s|__API_BASE_URL__|$API_BASE_URL|g" /usr/share/nginx/html/config.template.js > /usr/share/nginx/html/config.js
exec "$@" 