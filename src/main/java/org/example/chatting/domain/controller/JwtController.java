package org.example.chatting.domain.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatting.common.utils.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtUtil jwtUtil;

    @GetMapping("/api/jwt/{id}")
    public String generateJwtToken(@PathVariable Long id) {
        return jwtUtil.generateToken(id);
    }
}
