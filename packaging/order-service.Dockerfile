FROM amazoncorretto:8 AS builder

WORKDIR /app
COPY . /app
RUN ./gradlew :order-service:build -x test

FROM amazoncorretto:8

WORKDIR /app
COPY --from=builder /app/order-service/build/libs/*.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]