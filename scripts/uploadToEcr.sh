#!/usr/bin/env bash

## Build the application, Dockerize it, then upload it to ECR

# Stop immediately on error
set -e

ECR_REGISTRY="$AWS_ACCOUNT_ID.dkr.ecr.us-east-2.amazonaws.com"

# Log in and set environment variables, if necessary
if [[ -z "$1" ]]; then
  $(./scripts/assumeDeveloperRole.sh)

  # Login to ECR
  aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin "$ECR_REGISTRY"

  SERVICE_NAME="${PWD##*/}"
fi

# Generate image tag from git branch and sha1 hash
BRANCH_NAME=$(git rev-parse --abbrev-ref HEAD | grep -o "[^/]*$")
COMMIT_HASH=$(git rev-parse HEAD)
IMAGE_TAG=$BRANCH_NAME-$COMMIT_HASH

# Build the Dockerfile and tag it
docker build . -t "$ECR_REGISTRY/$SERVICE_NAME:$IMAGE_TAG" --platform linux/amd64

# Push the image
docker push "$ECR_REGISTRY/$SERVICE_NAME:$IMAGE_TAG"

# Re-tag the image as "latest" then re-push
docker tag "$ECR_REGISTRY/$SERVICE_NAME:$IMAGE_TAG" "$ECR_REGISTRY/$SERVICE_NAME:latest"
docker push "$ECR_REGISTRY/$SERVICE_NAME:latest"
