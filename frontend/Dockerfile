# --- Build Stage ---
# Use a Node.js base image to build the React app
FROM node:18-alpine AS builder

# Set the working directory
WORKDIR /app

# Copy the package.json and yarn.lock files
COPY package.json yarn.lock ./

# Install dependencies using yarn
RUN yarn install

# Copy the rest of the source code
COPY . .

# Run the build script to create the static files
RUN yarn build


# --- Run Stage ---
# Use a lightweight Nginx web server image
FROM nginx:1.25-alpine

# Copy the built static files from the 'builder' stage into the Nginx web root directory
COPY --from=builder /app/build /usr/share/nginx/html

# Copy our custom Nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80 (Nginx's default port)
EXPOSE 80

# The command to start Nginx when the container starts
CMD ["nginx", "-g", "daemon off;"]