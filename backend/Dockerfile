# stage-1 : complete build environment
FROM maven:3.8.1-openjdk-17-slim AS builder
RUN addgroup demogroup; adduser --ingroup demogroup --disabled-password demo
USER demo
WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean install -Dmaven.test.skip=true


# stage-2 : minimal runtime environment
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# copy jar from the first stage
COPY --from=builder /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
