package com.songify.domain.crud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

class InMemoryGenreRepository implements GenreRepository {
    
    Map<Long, Genre> genres = new HashMap<>();
    AtomicLong index = new AtomicLong(1);
    
    InMemoryGenreRepository() {
        long index1 = index.getAndIncrement();
        Genre genre = new Genre("default");
        genres.put(index1, genre);
        genre.setId(index1);
    }
    
    @Override
    public Genre save(final Genre genre) {
        genres.put(index.getAndIncrement(), genre);
        return genre;
    }
    
    @Override
    public Optional<Genre> findById(final Long id) {
        Genre genre = genres.get(id);
        return Optional.ofNullable(genre);
    }
    
    @Override
    public List<Genre> findAll() {
        return genres.values()
                     .stream()
                     .toList();
    }
}
