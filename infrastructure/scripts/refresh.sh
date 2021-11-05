#!/usr/bin/env bash

# Stop immediately on error
set -e

# Log in and set environment variables, if necessary
if [[ -z "$1" ]]; then
  $(./scripts/assumeDeveloperRole.sh)
fi

# Refresh state with infrastructure
pulumi refresh -s dev
