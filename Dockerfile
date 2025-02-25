# Start with Java 11
FROM bellsoft/liberica-openjdk-alpine:11

# Copy built app to root
COPY build/libs/*.jar /app.jar

# Expose port 8000
EXPOSE 8000

# Start app with 256MB max memory (-Xmx1G for 1GB, -Xmx512M for 512MB, etc)
ENTRYPOINT ["java","-jar","/app.jar", "-Xmx256M"]
