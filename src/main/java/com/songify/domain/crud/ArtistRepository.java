package com.songify.domain.crud;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

interface ArtistRepository extends CrudRepository<Artist, Integer> {
    
    @Override
    Set<Artist> findAll();
}
