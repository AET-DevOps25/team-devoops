# Meet@Mensa Client

A React application built with Vite, featuring ESLint and Prettier for code quality, and Jest with React Testing Library for testing.

## Local Development

1. Install dependencies:

```bash
npm install
```

2. Start the development server:

```bash
npm run dev
```

The application will be available at `http://localhost:5173`

## Docker

Build and run the application using Docker:

```
# Build the image
docker build -t ghcr.io/aet-devops25/team-devoops/client .

# Push the image to GHCR (latest tag by default, access needed)
docker push ghcr.io/aet-devops25/team-devoops/client

# Run the container
docker run -p 80:80 ghcr.io/aet-devops25/team-devoops/client
```

The application will be available at `http://localhost:80`
