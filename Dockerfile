FROM maven:3.9.0-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src/ /app/src/

RUN mvn package

FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/target/AmazinBookStore-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
