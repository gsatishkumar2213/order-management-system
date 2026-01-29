FROM maven:3-eclipse-temurin-21-alpine as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package


FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar inventory-service.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar","inventory-service.jar"]
