# ---- Build stage ----
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Cache dependencies separately from source so code changes don't bust the layer
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# H2 file-backed local profile; override with -e SPRING_PROFILES_ACTIVE=dev to use Postgres instead
ENV SPRING_PROFILES_ACTIVE=local

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
