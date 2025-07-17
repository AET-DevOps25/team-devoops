# Meet@Mensa Client

This is the React frontend for the Meet@Mensa application.

## Development

```bash
npm install
npm run dev
```

The development server will start on port 3000.

## Environment Configuration

### Local Development

Copy the example environment file and customize it:

```bash
cp env.example .env.local
```

Then edit `.env.local` with your local settings:

```
VITE_API_BASE_URL=http://localhost:8080
VITE_USE_MOCK_DATA=false
```
If mock data usage is set to true, no requests will be sent to the Gateway. 

### Docker Compose Deployment

The API base URL is configured in `deployment/docker/compose.yml`:

```yaml
client-service:
  build:
    context: ../../client
    args:
      VITE_API_BASE_URL: 'http://localhost:8080'
```

### Kubernetes Deployment

Environment variables are configured in `deployment/k8s/charts/client/values.yaml`:

```yaml
env:
  VITE_API_BASE_URL: 'http://gateway-service:80'
```

### AWS Deployment

Environment variables are configured in `deployment/compose.aws.yml`

```yaml
client:
    image: ghcr.io/aet-devops25/team-devoops/client:latest
    environment:
      - API_BASE_URL=https://api.${EC2_PUBLIC_IP}.nip.io
```

---

## Local Deployment with Docker

```
# Build and tag the Docker image
docker build -t ghcr.io/aet-devops25/team-devoops/client .

# Push the image to GHCR (latest tag by default, access needed)
docker push ghcr.io/aet-devops25/team-devoops/client

# Run the Docker container on port 80
docker run --name client-service -p 80:80 ghcr.io/aet-devops25/team-devoops/client

# List running containers (if needed)
docker ps

# Stop the container
docker stop client-service

# Remove the container
docker rm client-service
```

## Build

```bash
npm run build
```

## Testing

```bash
npm test
npm run test:e2e
```
