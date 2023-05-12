FROM amazoncorretto:8 AS builder

WORKDIR /app
COPY . /app
RUN ./gradlew :inventory-service:build -x test

FROM amazoncorretto:8

WORKDIR /app

COPY --from=builder /app/inventory-service/build/libs/*.jar /app/app.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]