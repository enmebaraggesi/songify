package com.songify.domain.usercrud;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface UserRepository extends Repository<User, Long> {

    Optional<User> findByEmail(String email);
    
    User save(User user);
    
    boolean existsByEmail(String email);
}
