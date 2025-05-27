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

```bash
# Build the image
docker build -t client-app .

# Run the container
docker run -p 80:80 client-app
```

The application will be available at `http://localhost:80`
