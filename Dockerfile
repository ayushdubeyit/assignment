# Use OpenJDK 17 (kyunki tum Java 17 use kar rahe ho)
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy JAR into container
COPY target/assignment-0.0.1-SNAPSHOT.jar app.jar

# Expose app port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]