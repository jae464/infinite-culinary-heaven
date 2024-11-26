FROM openjdk:17-jdk-alpine

ARG JAR_FILE=backend/build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENV AWS_ACCESS_KEY=${AWS_ACCESS_KEY}
ENV AWS_SECRET_KEY=${AWS_SECRET_KEY}
ENV AWS_BUCKET_NAME=${S3_IMAGE_BUCKET}
ENV AWS_REGION=${AWS_REGION}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=local"]
