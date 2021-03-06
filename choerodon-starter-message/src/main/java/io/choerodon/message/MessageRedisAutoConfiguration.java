package io.choerodon.message;

import io.choerodon.message.impl.redis.MessagePublisherImpl;
import io.choerodon.message.impl.redis.QueueListenerContainer;
import io.choerodon.message.impl.redis.TopicListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ConditionalOnProperty(havingValue = "redis", prefix = "message", name = "provider")
public class MessageRedisAutoConfiguration {

    @Autowired
    private JedisConnectionFactory v2redisConnectionFactory;

    @Autowired
    private StringRedisSerializer stringRedisSerializer;

    @Bean
    public TopicListenerContainer container() throws Exception {
        TopicListenerContainer container = new TopicListenerContainer();
        container.setConnectionFactory(v2redisConnectionFactory);
        container.setRecoveryInterval(10000L);
        return container;
    }

    @Bean
    public QueueListenerContainer queueListenerContainer() throws Exception {
        QueueListenerContainer container = new QueueListenerContainer();
        container.setConnectionFactory(v2redisConnectionFactory);
        container.setRecoveryInterval(5000L);
        container.setStringRedisSerializer(stringRedisSerializer);
        return container;
    }

    @Bean
    public MessagePublisherImpl messagePublisher(){
        return new MessagePublisherImpl();
    }

}
