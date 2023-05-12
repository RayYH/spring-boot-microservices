FROM amazoncorretto:8 AS builder

WORKDIR /app
COPY . /app
RUN ./gradlew :discovery-server:build -x test

FROM amazoncorretto:8

WORKDIR /app

COPY --from=builder /app/discovery-server/build/libs/*.jar /app/app.jar
EXPOSE 8761
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]