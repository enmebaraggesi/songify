package com.songify.infrastructure.security;

import com.songify.domain.usercrud.UserRepository;
import com.songify.infrastructure.security.jwt.JwtAuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
class SecurityConfig {
    
    @Bean
    public UserDetailsManager userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository, passwordEncoder());
    }
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthTokenFilter jwtAuthTokenFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable); // pozwalamy wysyłać żądania bez csrf
        http.cors(corsConfigurerCustomizer()); // ustawiamy, która domena może się dostać do serwera
        http.formLogin(AbstractHttpConfigurer::disable); // pozwalamy wysyłać żądania bez form login
        http.httpBasic(AbstractHttpConfigurer::disable); // pozwalamy wysyłać żądania bez basic auth
        http.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // wyłączamy sesyjność połączeń
        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/users/register").permitAll() // najpierw: pozwalam robić zapytanie o rejestrację użytkownika bez autoryzacji
                .requestMatchers("/swagger-ui/**").permitAll() // potem: pozwalam wchodzić na api swaggera bez autoryzacji
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/token/**").permitAll() // potem: pozwalam bez uprzedniego bycia zalogowanym uzyskiwać token (podczas logowania, więc użytkownik nie był jeszcze zalogowany)
                .requestMatchers(HttpMethod.GET, "/songs/**").permitAll() // potem: pozwalam wyświetlać dane zapisane w bazie danych
                .requestMatchers(HttpMethod.GET, "/artists/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/albums/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/genres/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/songs/**").hasRole("ADMIN") // potem: pozwalam modyfikować dane tylko adminowi
                .requestMatchers(HttpMethod.PATCH, "/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/artists/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/artists/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/artists/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/artists/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/albums/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/albums/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/albums/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/albums/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/genres/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/genres/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/genres/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/genres/**").hasRole("ADMIN")
                .anyRequest().authenticated()); // finalnie: zapytania można robić już tylko za pomocą uprzedniego logowania
        return http.build();
    }
    
    public Customizer<CorsConfigurer<HttpSecurity>> corsConfigurerCustomizer() {
        return c -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(
                        List.of("http://localhost:3000")); // pozwala frontendowi na porcie 3000 wysyłać żądania
                corsConfiguration.setAllowedMethods(
                        List.of("GET", "POST", "PUT", "DELETE")); // jakie żądania są obsługiwane
                corsConfiguration.setAllowedHeaders(
                        List.of("*")); // dowolne headery są dozwolone
                corsConfiguration.setAllowCredentials(true);
                return corsConfiguration;
            };
            c.configurationSource(source);
        };
    }
}
