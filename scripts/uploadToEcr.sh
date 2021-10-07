#!/usr/bin/env bash

## Build the application, Dockerize it, then upload it to ECR

# Stop immediately on error
set -e

if [[ -z "$1" ]]; then
  $(./scripts/assumeDeveloperRole.sh)

  # Login to ECR
  aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin "$AWS_ACCOUNT_ID.dkr.ecr.us-east-2.amazonaws.com"

  IMAGE_TAG=latest
  SERVICE_NAME=${PWD##*/}
else
  IMAGE_TAG=$1
fi

# Build the Dockerfile and tag it:
docker build . -t "$AWS_ACCOUNT_ID.dkr.ecr.us-east-2.amazonaws.com/$SERVICE_NAME:$IMAGE_TAG" --platform linux/amd64

# Push the image:
docker push "$AWS_ACCOUNT_ID.dkr.ecr.us-east-2.amazonaws.com/$SERVICE_NAME:$IMAGE_TAG"
