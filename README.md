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


# Why Jedis with RedisJSON is far better than Redisson

## Summary
- Standard, open source and supported by Redis
- RedisJSON is default and available with Redis Stack, Redis Enterprise
- Redis Insight helps navigate data
- Simple to code and understand Java Object to JSON
- Using RedisJSON one can use JSONPath to read/write sub element
- Using RedisJSON one can use RediSearch to index & query and use RedisOM or Spring Data Redis with repositories for full object mapping.

See those other projects for example
- https://github.com/alexvasseur/redis-jedis-spring/blob/main/src/main/java/com/example/rsapidemo/controller/EmployeeController.java
- https://redis.io/learn/develop/java/spring/redis-om/redis-om-spring


- Despite all the complexity in the API added by Redisson
    - It still requires serialization
    - There are no simple auto magic default
    - If using Java serialization, it is opaque and cannot be read easily
    - If using Redisson RBucket to store JSON, it stores as a String blob with no direct read/write or merge capability
    - If using Redisson RBucket to store JSON, it has no support for nested arrays in JSON
    - If using Redisson RMap, it requires boilerplate code to serialize from Object to JSON to Map

## Code in action

TODO add code and how data looks like


