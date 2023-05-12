FROM amazoncorretto:8 AS builder

WORKDIR /app
COPY . /app
RUN ./gradlew :product-service:build -x test

FROM amazoncorretto:8

WORKDIR /app

COPY --from=builder /app/product-service/build/libs/*.jar /app/app.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]