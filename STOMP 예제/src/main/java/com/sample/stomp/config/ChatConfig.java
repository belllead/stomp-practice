package com.sample.stomp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Stomp를 사용하기 위한 어노테이션
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 해당 경로로 SimpleBroker 등록.
        // SimpleBroker는 해당하는 경로를 SUBSCRIBE하는 Client에게
        // 메세지를 전달하는 간단한 작업을 수행
        registry.enableSimpleBroker("/queue", "/topic");
        // Client에서 SEND 요청을 처리
        registry.setApplicationDestinationPrefixes("/app");

        // SimpleBroker의 기능과
        // 외부 Message Broker(RabbitMQ, ActiveMQ 등)에
        // 메세지를 전달하는 기능
        // registry.enableStompBrokerRelay()
    }
}
