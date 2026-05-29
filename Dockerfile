# -------- Stage 1: Build the application using Java 25 and manual Maven --------
FROM eclipse-temurin:25-jdk AS build
WORKDIR /build

# Install Maven manually inside the Java 25 container
RUN apt-get update && apt-get install -y maven

# Copy and download dependencies
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy src and build the application
COPY src ./src
RUN mvn -B clean package -DskipTests

# -------- Stage 2: Run the application using Java 25 --------
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copy the built jar file from Stage 1
COPY --from=build /build/target/*.jar app.jar

EXPOSE 8989

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]