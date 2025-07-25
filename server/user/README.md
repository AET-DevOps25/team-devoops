# Meet@Mensa User Microservice

A Spring Boot application built with Gradle supporting the user-related features and database interaction in the Meet@Mensa app.

## Class Diagram
Diagram showing the basic class structure for the User Microservice

![Class Diagram](../../resources/diagrams/meetatmensa_uml_class_user.png)

## 

## Local Deployment with Docker
```
# Build and tag the Docker image
docker build -t ghcr.io/aet-devops25/team-devoops/user .

# Push the image to GHCR (latest tag by default, access needed)
docker push ghcr.io/aet-devops25/team-devoops/user

# Run the Docker container on port 8083
docker run --name user-service -p 8083:80 ghcr.io/aet-devops25/team-devoops/user

# List running containers (if needed) 
docker ps

# Stop the container     
docker stop user-service

# Remove the container     
docker remove user-service
```

The application will be available at `http://localhost:8083` and `http://localhost:8083/user`.