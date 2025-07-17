# Meet@Mensa API Gateway

A Spring Cloud Gateway built with Gradle supporting the backend ingress routing and SSO authentication in the Meet@Mensa app.

Implementation details to follow.

## Local Deployment with Docker
```
# Build and tag the Docker image
docker build -t ghcr.io/aet-devops25/team-devoops/gateway .

# Push the image to GHCR (latest tag by default, access needed)
docker push ghcr.io/aet-devops25/team-devoops/gateway

# Run the Docker container on port 8080
docker run --name gateway-service -p 8080:80 ghcr.io/aet-devops25/team-devoops/gateway

# List running containers (if needed) 
docker ps

# Stop the container
docker stop gateway-service

# Remove the container
docker remove gateway-service
```

The application will be available at `http://localhost:80`.