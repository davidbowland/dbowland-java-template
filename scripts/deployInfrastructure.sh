#!/usr/bin/env bash

# Stop immediately on error
set -e

if [[ -z "$1" ]]; then
  $(./scripts/assumeDeveloperRole.sh)
fi

# Deploy infrastructure

#sam deploy --stack-name java-template-ecr-test \
#  --region us-east-2 \
#  --capabilities CAPABILITY_NAMED_IAM \
#  --template-file template-ecr.yaml \
#  --parameter-overrides AccountId=$AWS_ACCOUNT_ID Environment=test

#./scripts/uploadToEcr.sh

sam deploy --stack-name java-template-test \
  --region us-east-2 \
  --capabilities CAPABILITY_NAMED_IAM \
  --template-file template.yaml \
  --parameter-overrides AccountId=$AWS_ACCOUNT_ID Environment=test
