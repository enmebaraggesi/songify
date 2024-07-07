package com.songify.domain.crud;

import org.springframework.data.repository.Repository;

interface GenreRepository extends Repository<Genre, Integer> {
    
    Genre save(Genre genre);
}
