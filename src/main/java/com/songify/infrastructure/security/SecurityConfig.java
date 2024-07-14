package com.songify.infrastructure.security;

import com.songify.domain.usercrud.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class SecurityConfig {
    
    @Bean
    public UserDetailsManager userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository, passwordEncoder());
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable); // pozwalamy wysyłać żądania spoza serwera
        http.formLogin(Customizer.withDefaults()); // nadpisując SecurityFilterChain trzeba na nowo włączyć defaultowe filtry
        http.httpBasic(Customizer.withDefaults());
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated()); // zapytania można robić już tylko za pomocą uprzedniego logowania
        return http.build();
    }
}
