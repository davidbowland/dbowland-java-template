#!/usr/bin/env bash

# Stop immediately on error
set -e

# Log in and set environment variables, if necessary
if [[ -z "$1" ]]; then
  $(./scripts/assumeDeveloperRole.sh)
fi

# Lint to catch syntax issues
if [[ "$NODE_ENV" != "production" ]]; then
  npm run lint
fi

## Deploy infrastructure, generate AppSpec.json, and copy AppSpec to S3

INFRASTRUCTURE_DIR=$(echo "$PWD" | grep -o '.*/infrastructure')
PROJECT_NAME=$(echo "$INFRASTRUCTURE_DIR" | rev | cut -d '/' -f 2 | rev)

# Remember existing task ARN
TASK_ARN_BEFORE=$(pulumi stack output -j -s dev | jq .taskDefinitionArn)

# This command generates a preview and gives a prompt before pushing changes
pulumi up -s dev

# If the task ARN changed, copy the change to S3
TASK_ARN_AFTER=$(pulumi stack output -j -s dev | jq .taskDefinitionArn)
if [[ "$TASK_ARN_BEFORE" != "$TASK_ARN_AFTER" ]]; then
  echo "Copying new AppSpec.json to S3"
  aws s3 cp "$INFRASTRUCTURE_DIR/codedeploy/appspec/AppSpec.json" "s3://jokes-ecs-deploy/$PROJECT_NAME/AppSpec.json"
fi
