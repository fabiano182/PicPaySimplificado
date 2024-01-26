FROM gradle:8.5-jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim

RUN mkdir /teste
RUN mkdir /app

COPY . /teste
COPY --from=build /home/gradle/src/build/libs /app/

EXPOSE 3001

CMD ["java", "-jar", "/app/picPaySimplificado-0.0.1-SNAPSHOT.jar"]