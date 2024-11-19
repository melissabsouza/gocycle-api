FROM gradle AS builder
WORKDIR /app
COPY . .
RUN gradle clean build

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/build/libs/* .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api-0.0.1-SNAPSHOT.jar"]

