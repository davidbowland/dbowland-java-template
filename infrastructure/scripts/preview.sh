#!/usr/bin/env bash

# Stop immediately on error
set -e

# Log in and set environment variables, if necessary
if [[ -z "$1" ]]; then
  $(./scripts/assumeDeveloperRole.sh)
fi

# Lint to catch syntax issues
npm run lint

# Generate a preview of what will change
pulumi preview -s dev
