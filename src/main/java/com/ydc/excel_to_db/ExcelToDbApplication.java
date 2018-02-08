package com.ydc.excel_to_db;

import com.ydc.excel_to_db.redis.Receiver;
import com.ydc.excel_to_db.util.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@SpringBootApplication
@ServletComponentScan
public class ExcelToDbApplication {
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapterSingle, MessageListenerAdapter listenerAdapterList) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 注入多个消息监听器(receiveSingle/receiveList)
        container.addMessageListener(listenerAdapterSingle, new PatternTopic(Constant.receiveSingle));
        container.addMessageListener(listenerAdapterList, new PatternTopic(Constant.receiveList));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapterSingle(Receiver receiver) {
        return new MessageListenerAdapter(receiver, Constant.singleMethodName);
    }

    @Bean
    MessageListenerAdapter listenerAdapterList(Receiver receiver) {
        return new MessageListenerAdapter(receiver, Constant.listMethodName);
    }

    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    public static void main(String[] args) {
        SpringApplication.run(ExcelToDbApplication.class, args);
    }

}
