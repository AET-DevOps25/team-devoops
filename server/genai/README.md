# Meet@Mensa GenAI Microservice

A FastAPI-based Python application built using LangChain enabling the Generative AI conversation starter in the Meet@Mensa app.

## Features

* **LangChain integration** – swap cloud or local LLMs via dependency injection.
* **FastAPI** endpoint conforming to the provided OpenAPI spec.
* **Configurable** via environment variables.
* **Test‑driven** – deterministic tests with a stubbed LLM.

## Local Deployment with Docker
```
# Build and tag the Docker image
docker build -t ghcr.io/aet-devops25/team-devoops/genai .   

# Push the image to GHCR (latest tag by default, access needed)
docker push ghcr.io/aet-devops25/team-devoops/genai

# Run the Docker container and map port 80 to the desired port
docker run --name genai-service -e OPENAI_API_KEY=sk-YOURKEY -p <desired_port>:80 ghcr.io/aet-devops25/team-devoops/genai

# List running containers (if needed)
docker ps 

# Stop the container     
docker stop genai-service

# Remove the container
docker remove genai-service
```

The application will be available at `http://localhost:<desired_port>`.