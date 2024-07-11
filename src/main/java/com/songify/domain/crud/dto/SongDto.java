package com.songify.domain.crud.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record SongDto(Long id,
                      String name,
                      Instant releaseDate,
                      Long duration,
                      String language,
                      GenreDto genre) {
    
}
