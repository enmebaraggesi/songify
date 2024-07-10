package com.songify.domain.crud;

import com.songify.infrastructure.crud.genre.error.GenreNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class GenreRetriever {
    
    private final GenreRepository genreRepository;
    
    
    Genre findById(final Long id) {
        return genreRepository.findById(id)
                              .orElseThrow(GenreNotFoundException::new);
    }
}
