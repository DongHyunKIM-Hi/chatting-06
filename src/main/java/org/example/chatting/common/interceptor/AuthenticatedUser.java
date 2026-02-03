package org.example.chatting.common.interceptor;

import java.security.Principal;
import lombok.Getter;
import org.example.chatting.common.entity.User;

@Getter
public class AuthenticatedUser implements Principal {

    private final User user;
    private final String name;

    public AuthenticatedUser(User user) {
        this.user = user;
        this.name = user.getName();
    }

    // Principal에서 User 꺼내기
    public static User fromPrincipal(Principal principal) {
        return ((AuthenticatedUser) principal).getUser();
    }
}