# === Stage 1: The Builder ===
# Use a full JDK and Maven image to build the .jar file
FROM maven:3.8-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
# This is a good trick to speed up builds
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of your source code
COPY src ./src

# Build the project (and skip tests)
RUN mvn clean package -DskipTests


# === Stage 2: The Runner ===
# Use a lightweight, "slim" image that only has the Java Runtime
FROM openjdk:17-slim

WORKDIR /app

# Copy ONLY the built .jar file from the 'build' stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
