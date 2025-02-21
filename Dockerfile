FROM openjdk:23-jdk-slim

WORKDIR /app

COPY . .

RUN ./gradlew bootJar

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n", "-jar", "./build/libs/app.jar"]
