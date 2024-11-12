FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./target/bulletin1.jar app.jar

EXPOSE 8886

ENTRYPOINT ["java", "-jar", "app.jar"]
