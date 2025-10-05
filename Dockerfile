# syntax=docker/dockerfile:1

# ---- Build stage ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Leverage layer caching for dependencies
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 \
    mvn -q -e -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -q -e -DskipTests package

# ---- Run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the bootable jar built in the first stage
COPY --from=build /app/target/shoppinglist-0.0.1-SNAPSHOT.jar /app/app.jar

# Default Spring Boot port
EXPOSE 8080

# JVM tuning suitable for containers and fast startup
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]

