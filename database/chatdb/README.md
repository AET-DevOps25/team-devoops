# Meet@Mensa Chat Database Service

A mysql database for managing chat information

## Local Deployment with Docker
```
# Build and tag the Docker image
docker build -t chatdb .

# Run the Docker container forwarding port 3306 to 3306 and setting the root password via
docker run --name chatdb-service -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 chatdb .

# List running containers (if needed) 
docker ps

# Stop the container
docker stop chatdb-service

# Remove the container
docker remove chatdb-service
```

The mysql database will be available on `http://127.0.0.1:3306` with user: `root` and password: `<MYSQL_ROOT_PASSWORD>`.