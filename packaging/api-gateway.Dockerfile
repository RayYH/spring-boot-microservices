FROM amazoncorretto:8 AS builder

WORKDIR /app
COPY . /app
RUN ./gradlew :api-gateway:build -x test

FROM amazoncorretto:8

WORKDIR /app

COPY --from=builder /app/api-gateway/build/libs/*.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]