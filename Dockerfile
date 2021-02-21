FROM oracle/graalvm-ce:20.1.0-java11 as graalvm
RUN gu install native-image

COPY build/libs/telebot-*-all.jar telebot.jar

RUN native-image --no-server -cp telebot.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm telebot /app/telebot
ENTRYPOINT ["/app/telebot", "-Dmicronaut.environments=development"]