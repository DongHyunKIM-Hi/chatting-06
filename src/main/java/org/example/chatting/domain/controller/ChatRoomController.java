package org.example.chatting.domain.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatting.common.entity.ChatRoom;
import org.example.chatting.domain.repository.ChatRoomRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    @PostMapping
    public ChatRoom create(@RequestParam String name) {
        ChatRoom room = new ChatRoom(name);
        return chatRoomRepository.save(room);
    }
}
