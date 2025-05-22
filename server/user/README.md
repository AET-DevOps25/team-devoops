# Meet@Mensa User Microservice

A Spring Boot application built with Gradle supporting the user-related features and database interaction in the Meet@Mensa app.

Implementation details to follow.

## Local Deployment with Docker
```
# Build and tag the Docker image
docker build -t meet_at_mensa/user .   

# Run the Docker container on port 8082
docker run --name user-service -p 8082:80 meet_at_mensa/user   

# List running containers (if needed) 
docker ps                             

# Stop the container     
docker stop user-service
```

The application will be available at `http://localhost:8082`.