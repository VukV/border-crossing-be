FROM gradle:7.5.0-jdk17 as build

WORKDIR /home/border-crossing-be

COPY build.gradle settings.gradle /home/border-crossing-be/
COPY src /home/border-crossing-be/src

RUN gradle build --no-daemon


FROM openjdk:17-slim

ARG SERVICE_NAME="border-crossing-be"

WORKDIR /app
COPY --from=build /home/user-service/build/libs/$SERVICE_NAME.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xms256m", "-Xmx3072m", "-jar", "app.jar"]