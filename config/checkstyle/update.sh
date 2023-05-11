#!/usr/bin/env bash

if ! command -v curl &>/dev/null; then
  echo "curl not installed"
  return
fi

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)

curl https://raw.githubusercontent.com/checkstyle/checkstyle/checkstyle-8.45.1/src/main/resources/google_checks.xml --output "$SCRIPT_DIR/checkstyle.xml"
