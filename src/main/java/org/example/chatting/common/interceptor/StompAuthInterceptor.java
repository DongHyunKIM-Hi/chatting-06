package org.example.chatting.common.interceptor;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.chatting.common.entity.User;
import org.example.chatting.common.utils.JwtUtil;
import org.example.chatting.domain.repository.UserRepository;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompAuthInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // 🔥 STOMP CONNECT 시점
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String token = accessor.getFirstNativeHeader("Authorization")
                .replace("Bearer ", "");

            Long userId = jwtUtil.getUserId(token);

            User user = userRepository.findById(userId)
                .orElseThrow();

            StompUserPrincipal principal = new StompUserPrincipal(user, user.getName());

            // 이 WebSocket 연결의 주인 설정
            accessor.setUser(principal);
        }

        return message;
    }
}
