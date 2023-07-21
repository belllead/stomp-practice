package com.sample.stomp.controller;

import com.sample.stomp.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    // 특정 Broker로 메시지 전달
    private final SimpMessageSendingOperations sendingOperations;

    // Client가 SEND할 수 있는 경로
    // stompConfig에서 설정한 apllicationDestinationPrefixes와
    // @MessageMapping 경로가 병합됨
    @MessageMapping("/chat/enter")
    public void enter(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하였습니다.");
            sendingOperations.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);
        }
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        sendingOperations.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);
    }
}
