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
```

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

## Build

```bash
npm run build
```

## Testing

```bash
npm test
npm run test:e2e
```
