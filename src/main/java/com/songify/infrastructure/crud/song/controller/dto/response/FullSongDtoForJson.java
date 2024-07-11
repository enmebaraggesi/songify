package com.songify.infrastructure.crud.song.controller.dto.response;

import com.songify.domain.crud.dto.GenreDto;

import java.time.Instant;

public record FullSongDtoForJson(Long id,
                                 String title,
                                 String language,
                                 Instant releaseDate,
                                 Long duration,
                                 GenreDto genre) {
    
}
