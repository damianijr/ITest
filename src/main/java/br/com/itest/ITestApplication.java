package br.com.itest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by damianijr on 10/25/16.
 */
@SpringBootApplication
public class ITestApplication {

    public static final String REDIS_HOSTNAME = "redis";
    public static final int REDIS_PORT = 6379;

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        // TODO: Externalize it :(
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName(REDIS_HOSTNAME);
        jedisConFactory.setPort(REDIS_PORT);
        return jedisConFactory;
    }

    public static void main(String[] args) {
        SpringApplication.run(ITestApplication.class, args);
    }
}
