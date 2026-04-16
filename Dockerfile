FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY src src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/quizapp-*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
