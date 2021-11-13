#!/usr/bin/env bash

# Stop immediately on error
set -e

# Log in and set environment variables, if necessary
if [[ -z "$1" ]]; then
  $(./scripts/assumeDeveloperRole.sh)
fi

# Clear any pending operations on the stack
pulumi stack -s dev export | jq ".deployment.pending_operations=[]" | pulumi stack -s dev import

# Refresh infrastructure state with no prompt
pulumi refresh -s dev -y
