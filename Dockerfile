#docker build java 21 app use dockerfile
FROM maven:3.6.3-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]