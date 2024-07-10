package com.songify.domain.crud;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface GenreRepository extends Repository<Genre, Integer> {
    
    Genre save(Genre genre);
    
    Optional<Genre> findById(Long id);
}
