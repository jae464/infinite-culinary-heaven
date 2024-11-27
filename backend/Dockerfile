FROM openjdk:17-jdk-alpine

ARG JAR_FILE=backend/build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-Dlogging.level.org.springframework.boot.context.config=TRACE", "-jar", "/app.jar", "--spring.profiles.active=local"]
