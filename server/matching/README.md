# Meet@Mensa Matching Microservice

A Spring Boot application built with Gradle supporting the matching process in the Meet@Mensa app.

Implementation details to follow.

## Local Deployment with Docker
```
# Build and tag the Docker image
docker build -t meet_at_mensa/matching .   

# Run the Docker container on port 8082
docker run --name matching-service -p 8082:80 meet_at_mensa/matching   

# List running containers (if needed) 
docker ps                             

# Stop the container     
docker stop matching-service

# Remove the container     
docker remove matching-service
```

The application will be available at `http://localhost:8082`.