#!/bin/sh

# Stop if an error occurs
set -e

# Build jar
./gradlew build

# Build Dockerfile and tag it with java-prototype
docker build . -t java-template

# Run image built on port 8000, use -e for environment variables
docker run -dp 8000:8000 java-template
