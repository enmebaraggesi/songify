package com.songify.domain.usercrud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;
import java.util.UUID;

@Log4j2
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsManager {
    
    public static final String DEFAULT_USER_ROLE = "ROLE_USER";
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConfirmer userConfirmer;
    
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findFirstByEmail(username)
                             .map(SecurityUser::new)
                             .orElseThrow(() -> new RuntimeException("user not found"));
    }
    
    @Override
    public void createUser(final UserDetails user) {
        if (userExists(user.getUsername())) {
            log.warn("user already exists");
            throw new RuntimeException("user already exists");
        }
        User createdUser = new User(user.getUsername(),
                                    passwordEncoder.encode(user.getPassword()),
                                    UUID.randomUUID().toString(),
                                    List.of(DEFAULT_USER_ROLE));
        User savedUser = userRepository.save(createdUser);
        log.info("user created with id {}", savedUser.getId());
        userConfirmer.sendConfirmationEmail(createdUser);
    }
    
    @Override
    public void updateUser(final UserDetails user) {
    
    }
    
    @Override
    public void deleteUser(final String username) {
    
    }
    
    @Override
    public void changePassword(final String oldPassword, final String newPassword) {
    
    }
    
    @Override
    public boolean userExists(final String username) {
        return userRepository.existsByEmail(username);
    }
}