package com.songify.domain.usercrud;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
    
    User save(User user);
    
    boolean existsByEmail(String email);
    
    Optional<User> findFirstByEmail(String username);
    
    Optional<User> findByConfirmationToken(String confirmationToken);
}
