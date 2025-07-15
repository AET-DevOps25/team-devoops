# Build stage
FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm install

# Copy source code
COPY . .

# Add build arg and env for VITE_API_BASE_URL and VITE_USE_MOCK_DATA
ARG VITE_API_BASE_URL=http://api.meetatmensa.com
ARG VITE_USE_MOCK_DATA=false
ENV VITE_API_BASE_URL=$VITE_API_BASE_URL
ENV VITE_USE_MOCK_DATA=$VITE_USE_MOCK_DATA

RUN npm run build

FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"] 