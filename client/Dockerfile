# Build stage
FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm install

# Copy source code
COPY . .

# Hardcode API URL for Docker deployment
ENV VITE_API_BASE_URL=http://localhost:8080

RUN npm run build

FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"] 