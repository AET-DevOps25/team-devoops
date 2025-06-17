# Meet@Mensa Match Database Service

A mysql database for managing matching information

## Local Deployment with Docker
```
# Build and tag the Docker image
docker build -t matchdb .

# Run the Docker container forwarding port 3306 to 3307 and setting the root password via
docker run --name matchdb-service -e MYSQL_ROOT_PASSWORD=root -p 3306:3307 matchdb .

# List running containers (if needed) 
docker ps

# Stop the container
docker stop matchdb-service

# Remove the container
docker remove matchdb-service
```

The mysql database will be available on `http://127.0.0.1:3307` with user: `root` and password: `<MYSQL_ROOT_PASSWORD>`.