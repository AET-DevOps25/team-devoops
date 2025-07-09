# Meet@Mensa GenAI Microservice

A Python application built using Hatchling enabling the Generative AI conversation starter using in the Meet@Mensa app.

Currently unimplemented, save for a placeholder webserver

## Local Deployment with Docker
```
# Build and tag the Docker image
docker build -t ghcr.io/aet-devops25/team-devoops/genai .   

# Push the image to GHCR (latest tag by default, access needed)
docker push ghcr.io/aet-devops25/team-devoops/genai

# Run the Docker container and map port 80 to the desired port
docker run --name genai-service -p <desired_port>:80 ghcr.io/aet-devops25/team-devoops/genai

# List running containers (if needed)
docker ps 

# Stop the container     
docker stop genai-service

# Remove the container
docker remove genai-service
```

The application will be available at `http://localhost:<desired_port>`, followed by any path.