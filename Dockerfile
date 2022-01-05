FROM openjdk:17-jdk-alpine AS gradle-build
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:17-jdk-alpine
RUN apk add --no-cache bash
COPY --from=gradle-build build/libs/*.jar app.jar
ARG profile
RUN echo "$profile"
ENTRYPOINT ["java","-Dspring.profiles.active=$profile","-jar","/app.jar"]
