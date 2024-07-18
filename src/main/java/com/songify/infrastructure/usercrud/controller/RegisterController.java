package com.songify.infrastructure.usercrud.controller;

import com.songify.domain.usercrud.UserConfirmer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("users")
class RegisterController {
    
    private final UserDetailsManager userDetailsManager;
    private final UserConfirmer userConfirmer;
    
    @PostMapping("register")
    public ResponseEntity<RegisterUserResponseDto> register(@RequestBody RegisterUserRequestDto dto) {
        String password = dto.password();
        String username = dto.username();
        UserDetails userDetails = User.builder()
                                      .username(username)
                                      .password(password)
                                      .build();
        userDetailsManager.createUser(userDetails);
        return ResponseEntity.ok(new RegisterUserResponseDto("Registered new user"));
    }
    
    @GetMapping("confirm")
    public String confirm(@RequestParam String token) {
        boolean isConfirmed = userConfirmer.confirmUser(token);
        return isConfirmed ? "Confirmed" : "Unconfirmed";
    }
}
