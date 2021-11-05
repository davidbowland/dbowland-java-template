#!/usr/bin/env bash

### Build the application and use it to build and run a new Docker container


# Stop if an error occurs
set -e

# Build jar
./gradlew build

# Determine project name from path
SERVICE_NAME=${PWD##*/}

# Build Dockerfile and tag it with java-prototype
docker build . -t "$SERVICE_NAME"

# Run image built from port 80, mapped to port 80 of the service
# Add -e for environment variables
docker run -dp 80:80 "$SERVICE_NAME"
