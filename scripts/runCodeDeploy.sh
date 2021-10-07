#!/usr/bin/env bash

## Create a CodeDeploy deployment and wait for it to complete

# Stop immediately on error
set -e

if [[ -z "$1" ]]; then
  $(./infrastructure/scripts/assumeDeveloperRole.sh)

  SERVICE_NAME=${PWD##*/}
fi

APPSPEC_S3_LOCATION="{bucket=jokes-ecs-deploy,key="$SERVICE_NAME/AppSpec.json",bundleType=JSON}"

# Create a new deployment and remember the ID
DEPLOYMENT_ID=$(aws deploy create-deployment --application-name "$SERVICE_NAME" \
    --deployment-group-name "$SERVICE_NAME" \
    --revision revisionType=S3,s3Location="$APPSPEC_S3_LOCATION" \
    | jq .deploymentId -r)
echo "[$(date +%s)] Created deployment $DEPLOYMENT_ID"

# Wait for deploy to exit in progress status, then display output

DEPLOYMENT_STATUS="Pending"
# Possible status values: Failed,InProgress,Pending,Ready,Skipped,Succeeded
while [[ "$DEPLOYMENT_STATUS" == "Pending" ]] || [[ "$DEPLOYMENT_STATUS" == "InProgress" ]]; do
  sleep 3
  DEPLOYMENT_STATUS=$(aws deploy get-deployment --deployment-id "$DEPLOYMENT_ID" | jq .deploymentInfo.status -r)
  echo "[$(date +%s)] Deployment status: $DEPLOYMENT_STATUS"
done

if [[ "$DEPLOYMENT_STATUS" == "Failed" ]]; then
  ERROR_DETAILS=$(aws deploy get-deployment --deployment-id "$DEPLOYMENT_ID" | jq .deploymentInfo.errorInformation -r)
  echo "[$(date +%s)] Error information: $ERROR_DETAILS"
  exit 1
fi
