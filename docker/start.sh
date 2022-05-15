#!/bin/bash

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

docker-compose -p unwe-rac -f "${DIR}/docker-compose.yml" up -d