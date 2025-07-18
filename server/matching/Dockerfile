# ------------ Build Stage ------------
FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app

# Copy only necessary files first (for better caching)
COPY build.gradle settings.gradle ./
COPY src ./src

# Copy only necessary files for generated server dependencies
COPY generated/build.gradle generated/settings.gradle ./generated/
COPY generated/src ./generated/src
COPY generated/gradle/libs.versions.toml ./generated/gradle/

# Copy only necessary files for generated client dependencies
COPY generated-client/build.gradle generated-client/settings.gradle ./generated-client/
COPY generated-client/src ./generated-client/src
COPY generated-client/gradle/libs.versions.toml ./generated-client/gradle/

# Build the application
RUN gradle clean bootJar --no-daemon


# ------------ Runtime Stage ------------
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 80
EXPOSE 80

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
