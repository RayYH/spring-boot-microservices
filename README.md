# Spring Boot Microservices

+ keycloak: [http://localhost:8181/](http://localhost:8181/), `admin`/`admin`.
+ Eureka: [http://localhost:8761/](http://localhost:8761/), `eureka`/`password`.
+ API Gateway Endpoint: [http://localhost:8080/](http://localhost:8080/)
+ Zipkin: [http://localhost:9411/](http://localhost:9411/)

## Getting Started

```shell
$ cp .env.example .env
$ docker compose up -d --force-recreate
```

```shell
# issue token
$ export ACCESS_TOKEN=$(docker exec -it api-gateway curl -d "client_id=spring-cloud-client" -d "client_secret=SVIESaN4w1iHpvFgzlPsM3nJv3zkkx8Z" -d "grant_type=client_credentials" "http://keycloak:8080/realms/spring-boot-microservices-realm/protocol/openid-connect/token" | jq .access_token | tr -d '"')
$ echo $ACCESS_TOKEN
$ curl --request GET   --url http://localhost:8080/api/order/quantity   --header 'Accept: application/json'   --header "Authorization: Bearer $ACCESS_TOKEN"   --header 'Content-Type: application/json'   --data '{
        "orderLineItemsList": [
                {
                        "skuCode": "iphone-13",
                        "price": 1200,
                        "quantity": 1
                },
                {
                        "skuCode": "iphone-13-pro",
                        "price": 1200,
                        "quantity": 1
                }
        ]
}'
```

## Local Development

```shell
$ cp .env.example .env
# stop the containers and remove the volumes
$ docker compose down -v
$ docker compose -f docker-compose-data.yml up -d --force-recreate
$ ./gradlww bootRun --parallel
```