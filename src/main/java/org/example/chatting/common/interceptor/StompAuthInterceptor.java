package org.example.chatting.common.interceptor;

import lombok.RequiredArgsConstructor;
import org.example.chatting.common.entity.User;
import org.example.chatting.common.utils.JwtUtil;
import org.example.chatting.domain.repository.UserRepository;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompAuthInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String token = accessor.getFirstNativeHeader("Authorization")
                .replace("Bearer ", "");

            Long userId = jwtUtil.getUserId(token);

            User user = userRepository.findById(userId)
                .orElseThrow();

            accessor.setUser(new AuthenticatedUser(user));
        }

        return message;
    }
}
