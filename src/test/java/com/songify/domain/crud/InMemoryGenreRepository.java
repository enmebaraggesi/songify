package com.songify.domain.crud;

import java.util.Optional;

class InMemoryGenreRepository implements GenreRepository {
    
    @Override
    public Genre save(final Genre genre) {
        return null;
    }
    
    @Override
    public Optional<Genre> findById(final Long id) {
        return Optional.empty();
    }
}
