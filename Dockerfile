FROM registry.rd.ertelecom.ru/devops/docker/alpine-oraclejdk8:latest

COPY build/libs/er-logger-*.jar /app/er-logger.jar

RUN apk add --update --no-cache busybox-extras curl bash

ENV JAVA_OPTS="-XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=2" \
    SPRING_PROFILE="prod" \
    KAFKA_HOST="app-kafka-cp-kafka:9093" \
    KAFKA_TOPIC="device"

RUN ls /app

CMD java \
    ${JAVA_OPTS} \
    -Dspring.profiles.active=${SPRING_PROFILE} \
    -jar /app/er-logger.jar
