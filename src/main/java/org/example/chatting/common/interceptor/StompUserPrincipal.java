package org.example.chatting.common.interceptor;

import java.security.Principal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.chatting.common.entity.User;

@Getter
@AllArgsConstructor
public class StompUserPrincipal implements Principal {

    private final User user;
    private final String username;

    @Override
    public String getName() {
        return username;
    }

}
