package org.aiboot.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * <p>Redis配置</p>
 *
 * @author Hullson
 * @date 2025-07-09
 */
@Configuration
public class RedisConfig {

        @Bean
        @Primary
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(factory);

            // 使用 Jackson2JsonRedisSerializer 序列化 Value
            Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
            serializer.setObjectMapper(objectMapper);

            // 配置序列化
            template.setKeySerializer(RedisSerializer.string());
            template.setValueSerializer(serializer);
            template.setHashKeySerializer(RedisSerializer.string());
            template.setHashValueSerializer(serializer);
            template.afterPropertiesSet();

            return template;
        }
}
