package org.example.chatting;

import org.example.chatting.common.entity.User;
import org.example.chatting.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class TestUserInitializer {

    public TestUserInitializer(UserRepository userRepository) {
        userRepository.save(new User("Alice"));
        userRepository.save(new User("Bob"));
    }
}
