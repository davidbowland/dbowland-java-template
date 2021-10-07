# dbowland.com Java Template

Starter template for Java projects for dbowland.com

(Either Java or Groovy can be used)

## Developing Locally

### Setup

This project uses Java 11 with the build tool Gradle. [Install SDKMAN](https://sdkman.io/install) and then use it to install a Java 11 version such as `11.0.11.hs-adpt`:

```bash
sdk install java 11.0.11.hs-adpt
```

Followed by Gradle:

```bash
sdk install gradle
```

### Running Locally

Start the local server with:

```bash
./gradlew bootRun
```

Then interact with the server at: <http://localhost:8080/>

### Testing

Run tests with:

```bash
./gradlew test
```

### Production Build

Perform a production-level build with:

```bash
./gradlew build
```

## Git Hooks

Git hooks ensure code is tested and formatted before it is committed. They are automatically installed when you run the `test` or `build` gradle commands but can be manually installed with:

```bash
./gradlew installGitHooks
```

## Docker Container

This project will be containerized and deployed to [AWS ECS](https://aws.amazon.com/ecs/). To test the container locally, first install [Docker](https://docs.docker.com/get-docker/):

```bash
brew cask install docker
```

Once Docker is installed, build the jar and start a container with:

```bash
./scripts/runDockerfile.sh
```

### Copying to ECR

In emergency situations, containers can be copied directly to [AWS ECR](https://aws.amazon.com/ecr/).

Login to ECR:

```bash
aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.us-east-2.amazonaws.com
```

Build the `Dockerfile` and tag it:

```bash
docker build . -t $AWS_ACCOUNT_ID.dkr.ecr.us-east-2.amazonaws.com/${PWD##*/}:latest --platform linux/amd64
```

Push the image:

```bash
docker push $AWS_ACCOUNT_ID.dkr.ecr.us-east-2.amazonaws.com/${PWD##*/}:latest
```

## Infrastructure / Deploying to AWS

The infrastructure for this project is maintained as code as part of the project in the `infrastructure` folder. New Docker images are copied to [AWS ECS](https://aws.amazon.com/ecs/) by redeploying this infrastructure. [Pulumi CLI](https://www.pulumi.com/docs/reference/cli/) and [Node 16](https://nodejs.org/en/) are required for local deployments, which are discouraged. [Node 16](https://nodejs.org/en/) should be installed with [NVM](https://github.com/nvm-sh/nvm) and [Pulumi CLI](https://www.pulumi.com/docs/reference/cli/) can be installed with:

```bash
brew install pulumi
```

From the `infrastructure` folder, deploy the infrastructure with:

```bash
npm run deploy
```

## Additional Documentation

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.5/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.5/gradle-plugin/reference/html/#build-image)
* [SDKMAN SDK list](https://sdkman.io/sdks)
* [Docker](https://docs.docker.com/get-docker/)
* [Pulumi](https://www.pulumi.com/docs/reference/cli/)
