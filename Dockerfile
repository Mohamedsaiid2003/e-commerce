# Use an official Eclipse Temurin JDK 21 image as the build image
FROM eclipse-temurin:21-jdk AS build

# Set the working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy source code
COPY src src

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the project and skip tests (optional)
RUN ./mvnw clean package -DskipTests

# --- Stage 2: Create the runtime image ---
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8070

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
