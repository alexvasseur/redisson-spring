# SpringBoot Redis with Jedis & RedisJSON (native) compared to Redisson

Basic application that has CRUD with Redis and a JSON Restful API.

It shows how native & simple it is to use RedisJSON with Jedis and Gson
compared to Redisson which has no native JSON support, complex codec, and circumvoluted Map with also proprietary clustered Map only available in the PRO edition.


## Run locally


```
./mvnw package spring-boot:run

or 
java -jar target/redissonapidemo-1.0.0.jar
```


## RedisStack in Docker

docker run -it -p 6379:6379 -p 8001:8001 --name redis1 redis/redis-stack:latest

./mvnw package spring-boot:run

Use RedisInsight http://localhost:8001




