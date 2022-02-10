#!/usr/bin/env bash

## Create a CodeDeploy deployment and wait for it to complete

# Stop immediately on error
set -e

# Log in and set environment variables, if necessary
BUCKET=dbowland-ecs
if [[ -z "$1" ]]; then
  $(./scripts/assumeDeveloperRole.sh)

  BUCKET=dbowland-ecs-test
  SERVICE_NAME=${PWD##*/}
fi

APPSPEC_S3_LOCATION="{bucket=$BUCKET,key="$SERVICE_NAME/AppSpec.json",bundleType=JSON}"

# Create a new deployment and remember the ID
DEPLOYMENT_ID=$(aws deploy create-deployment --application-name "$SERVICE_NAME" \
    --deployment-group-name "$SERVICE_NAME" \
    --revision revisionType=S3,s3Location="$APPSPEC_S3_LOCATION" \
    | jq .deploymentId -r)
echo "[$(date -u)] Created deployment $DEPLOYMENT_ID"

# Wait for deploy to exit in progress status, then display output

DEPLOYMENT_STATUS="Pending"
# Possible status values: Failed,InProgress,Pending,Ready,Skipped,Succeeded
while [[ "$DEPLOYMENT_STATUS" == "Pending" ]] || [[ "$DEPLOYMENT_STATUS" == "InProgress" ]]; do
  sleep 3
  DEPLOYMENT_STATUS=$(aws deploy get-deployment --deployment-id "$DEPLOYMENT_ID" | jq .deploymentInfo.status -r)
  echo "[$(date -u)] Deployment status: $DEPLOYMENT_STATUS"
done

if [[ "$DEPLOYMENT_STATUS" == "Failed" ]]; then
  ERROR_DETAILS=$(aws deploy get-deployment --deployment-id "$DEPLOYMENT_ID" | jq .deploymentInfo.errorInformation -r)
  echo "[$(date -u)] Error information: $ERROR_DETAILS"
  exit 1
fi
