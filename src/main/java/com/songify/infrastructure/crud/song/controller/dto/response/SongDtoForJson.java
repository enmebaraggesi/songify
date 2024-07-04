package com.songify.infrastructure.crud.song.controller.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record SongDtoForJson(Long id,
                             String title,
                             String language,
                             Instant releaseDate,
                             Long duration) {
    
}
