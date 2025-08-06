# --- Build Stage ---
# Use a base image with Java 17 and Maven to build our application
FROM maven:3.9-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file first to leverage Docker's layer caching
COPY pom.xml .

# Copy the rest of the source code
COPY src ./src

# Run the Maven command to build the project and create the executable .jar file
# -DskipTests will skip running tests during the build for speed
RUN mvn clean package -DskipTests


# --- Run Stage ---
# Use a minimal base image with just the Java 17 Runtime Environment
FROM eclipse-temurin:17-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the executable .jar file that we built in the 'builder' stage
COPY --from=builder /app/target/urlshortener-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 so the container can receive traffic
EXPOSE 8080

# The command to run when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]