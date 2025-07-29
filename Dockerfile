FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY simulation-algorithm/pom.xml simulation-algorithm/
COPY simulation-core/pom.xml simulation-core/

RUN mvn dependency:go-offline -B

COPY simulation-algorithm/src simulation-algorithm/src
COPY simulation-core/src simulation-core/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=build /app/simulation-core/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-Xmx512m -Xms256m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 