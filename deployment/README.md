# Deployment
The `deployment` directory contains necessary files for the deployment of Meet@Mensa application. 

To deploy the system manually on your local machine, follow the guides below. Automated deployment workflows in GitHub Actions will follow.

## Docker Compose
Use the following commands to start / stop / inspect the project using Docker Compose:
```
docker compose up --build       # Starts project in the foreground (with force re-build)
docker compose up -d            # Starts project in the background
docker compose ps               # Shows all running containers
docker compose down             # Stops the project
docker compose logs             # Shows the logs of all containers
docker compose pull             # Pulls the images of all services
docker images                   # Shows locally chached images
```
