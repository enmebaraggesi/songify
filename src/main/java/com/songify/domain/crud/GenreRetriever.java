package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import com.songify.infrastructure.crud.genre.error.GenreNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class GenreRetriever {
    
    private final GenreRepository genreRepository;
    
    Genre findById(final Long id) {
        return genreRepository.findById(id)
                              .orElseThrow(GenreNotFoundException::new);
    }
    
    List<GenreDto> findAll() {
        List<Genre> genreList = genreRepository.findAll();
        return genreList.stream()
                        .map(genre -> new GenreDto(genre.getId(), genre.getName()))
                        .toList();
    }
}
