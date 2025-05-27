# Meet@Mensa Chat Microservice

A Spring Boot application built with Gradle supporting the chat function in the Meet@Mensa app.

Implementation details to follow.

## Local Deployment with Docker
```
# Build and tag the Docker image
docker build -t meet_at_mensa/chat .

# Run the Docker container on port 8081
docker run --name chat-service -p 8081:80 meet_at_mensa/chat

# List running containers (if needed) 
docker ps

# Stop the container     
docker stop chat-service

# Remove the container
docker remove chat-service
```

The application will be available at `http://localhost:8081` and `http://localhost:8081/chat`.