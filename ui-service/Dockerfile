# -----------------------------------------------------------------
# STAGE 1: El "Builder" (Basat en Node)
# -----------------------------------------------------------------
FROM node:18-alpine AS builder

WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build

# -----------------------------------------------------------------
# STAGE 2: El "Runner" (Basat en Nginx)
# -----------------------------------------------------------------
FROM nginx:1.25-alpine


COPY --from=builder /app/dist /usr/share/nginx/html

# Copiem la nostra configuració personalitzada de Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80