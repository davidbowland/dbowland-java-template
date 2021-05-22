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

### Git Hooks

Git hooks ensure code is tested and formatted before it is committed. They are automatically installed when you run the `test` or `build` gradle commands but can be manually installed with:

```bash
./gradlew installGitHooks
```

## Additional Documentation

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.5/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.5/gradle-plugin/reference/html/#build-image)
* [SDKMAN SDK list](https://sdkman.io/sdks)
