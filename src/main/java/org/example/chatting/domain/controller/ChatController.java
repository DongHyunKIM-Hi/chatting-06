package org.example.chatting.domain.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.example.chatting.common.entity.ChatMessage;
import org.example.chatting.common.entity.ChatRoom;
import org.example.chatting.common.entity.User;
import org.example.chatting.common.interceptor.AuthenticatedUser;
import org.example.chatting.domain.model.ChatMessageDto;
import org.example.chatting.domain.repository.ChatMessageRepository;
import org.example.chatting.domain.repository.ChatRoomRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;

    @MessageMapping("/chat.send")
    public void send(ChatMessageDto dto, Principal principal) {

        User sender = AuthenticatedUser.fromPrincipal(principal);

        ChatRoom room = chatRoomRepository
            .findById(dto.getRoomId())
            .orElseThrow();

        ChatMessage message = new ChatMessage(sender, room, dto.getContent());
        chatMessageRepository.save(message);

        messagingTemplate.convertAndSend(
            "/sub/chat/" + room.getId(),
            dto
        );
    }
}