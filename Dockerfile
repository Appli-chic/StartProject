FROM openjdk:23-jdk-slim

WORKDIR /app

COPY . .

RUN ./gradlew bootJar

ENTRYPOINT ["java", "-jar", "./build/libs/app.jar"]