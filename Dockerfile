

# Stage 1: Build the JAR
FROM eclipse-temurin:17-jdk as builder

WORKDIR /app

# Copy project files
COPY . .

# Build the project (skip tests for speed)
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the JAR
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /app/target/assignment-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
