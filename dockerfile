# Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package

# Create the final image
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/vegetable-shopping-be.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "vegetable-shopping-be.jar"]