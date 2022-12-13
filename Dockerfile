FROM eclipse-temurin:19-jammy
COPY .git .git
COPY gradle gradle
COPY gradlew gradlew
COPY build.gradle.kts build.gradle.kts
COPY src src
COPY settings.gradle.kts settings.gradle.kts
# RUN apk update
# RUN apk add git
RUN apt update
RUN apt install -y git
RUN ./gradlew compileKotlin
RUN ./gradlew --stop
CMD ./gradlew tasks
