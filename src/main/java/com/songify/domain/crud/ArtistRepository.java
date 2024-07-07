package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.Set;

interface ArtistRepository extends Repository<Artist, Integer> {
    
    Set<Artist> findAll(final Pageable pageable);
    
    Optional<Artist> findById(Long id);
    
    void deleteById(Long id);
    
    Artist save(Artist artist);
}
