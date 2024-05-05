package com.example.rsapidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class RedissonapidemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedissonapidemoApplication.class, args);
	}

	//@Bean
	//JedisConnectionFactory jedisConnectionFactory() {
	//	return new JedisConnectionFactory();
	//}

	//@Bean
	//public RedisTemplate<String, Object> redisTemplate() {
	//	final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
	//	template.setConnectionFactory(jedisConnectionFactory());
	//	template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
	//	return template;
	//}
}