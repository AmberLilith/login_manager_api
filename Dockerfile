#FROM alpine:latest
#RUN apk --update add openjdk17
#RUN addgroup -S spring && adduser -S spring -G spring
#FROM adoptopenjdk/openjdk17:alpine-jre
FROM openjdk:17-jdk-alpine
COPY build/libs/*.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]

