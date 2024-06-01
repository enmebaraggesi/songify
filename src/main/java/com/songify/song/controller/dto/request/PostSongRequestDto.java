package com.songify.song.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PostSongRequestDto(
        @NotNull(message = "songName must not be null")
        @NotEmpty(message = "songName must not be empty")
        String songName,
        
        @NotNull(message = "artist must not be null")
        @NotEmpty(message = "artist must not be empty")
        String artist
) {

}
