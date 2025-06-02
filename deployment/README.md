# Deployment
The `deployment` directory contains necessary files for the deployment of Meet@Mensa application. 

To deploy the system manually on your local machine, follow the guides below. Automated deployment workflows in GitHub Actions can be found under `../.github/workflows`.

## Docker Compose
Use the following commands to start / stop / inspect the project using Docker Compose:
```
cd docker                       # Move to docker directory
docker compose up --build       # Starts project in the foreground (with force re-build)
docker compose up -d            # Starts project in the background
docker compose ps               # Shows all running containers
docker compose down             # Stops the project
docker compose logs             # Shows the logs of all containers
docker compose pull             # Pulls the images of all services
docker images                   # Shows locally chached images
```

## Kubernetes Helm
Prerequisites: 
- you are already authenticated in a running Kubernetes cluster,
- the microservice images are built and pushed to GHCR (see `README.md` in individual microservices). 
```
helm install meetatmensa ./k8s -n devoops   # Installs the Helm chart and deploys it to devoops namespace
kubectl get all -n devoops                  # Shows all Kubernetes resources deployed in devoops namespace
helm uninstall meetatmensa -n devoops       # Deletes all resources deployed with the Helm chart
```