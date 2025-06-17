# Meet@Mensa User Database Service

A mysql database for managing user information

## Local Deployment with Docker
```
# Build and tag the Docker image
docker build -t userdb .

# Run the Docker container forwarding port 3306 to 3308 and setting the root password via
docker run --name userdb-service -e MYSQL_ROOT_PASSWORD=root -p 3306:3308 userdb .

# List running containers (if needed) 
docker ps

# Stop the container
docker stop userdb-service

# Remove the container
docker remove userdb-service
```

The mysql database will be available on `http://127.0.0.1:3308` with user: `root` and password: `<MYSQL_ROOT_PASSWORD>`.