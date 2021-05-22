# Start with Java 11, using the slimmest version
FROM openjdk:11-jre-slim

# Copy built app to root
COPY build/libs/*.jar /app.jar

# Start app with 256MB max memory (-Xmx1G for 1GB, -Xmx512M for 512MB, etc)
ENTRYPOINT ["java","-jar","/app.jar", "-Xmx256M"]
