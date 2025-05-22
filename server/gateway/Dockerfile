# ------------ Build Stage ------------
FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app

# Copy only necessary files first (for better caching)
COPY build.gradle settings.gradle ./
COPY src ./src

# Build the application
RUN gradle clean bootJar --no-daemon


# ------------ Runtime Stage ------------
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
