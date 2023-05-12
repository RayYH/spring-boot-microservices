# Spring Boot Microservices

+ Eureka: [http://localhost:8761/](http://localhost:8761/)
+ API Gateway Endpoint: [http://localhost:8080/](http://localhost:8080/)
+ Zipkin: [http://localhost:9411/](http://localhost:9411/)

## Getting Started

```shell
$ cp .env.example .env
$ docker compose up -d --force-recreate
```

## Local Development

```shell
$ cp .env.example .env
# stop the containers and remove the volumes
$ docker compose down -v
$ docker compose -f docker-compose-data.yml up -d --force-recreate
$ ./gradlww bootRun --parallel
```