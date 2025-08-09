FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app
COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY gradle/libs.versions.toml gradle/libs.versions.toml

RUN ./gradlew dependencies --no-daemon

COPY src ./src

RUN ./gradlew clean build -x test --no-daemon

FROM openjdk:21-jdk AS runner

WORKDIR /app

COPY --from=builder /app/build/libs/patient-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4000

ENTRYPOINT ["java", "-jar", "app.jar"]
