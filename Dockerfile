# -------- Stage 1: Build the application --------
FROM maven:3.9.9-eclipse-temurin-25 AS build
WORKDIR /build

# Copy only the pom.xml first to cache dependencies (makes future builds faster)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy the source code and compile the JAR
COPY src ./src
RUN mvn -B clean package -DskipTests

# -------- Stage 2: Run the application --------
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copy the built jar file from Stage 1
COPY --from=build /build/target/*.jar app.jar

# Expose the internal port (Render overrides this with $PORT dynamically)
EXPOSE 8080

# Execute the application
ENTRYPOINT ["java", "-jar", "app.jar"]