package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SongDtoForJson;
import org.springframework.stereotype.Service;

@Service
class SongCrudMapper {
    
    static SongDto mapSongToSongDto(Song song) {
        return SongDto.builder()
                      .id(song.getId())
                      .name(song.getName())
                      .build();
    }
    
    static SongDto mapSongDtoForJsonToSongDto(SongDtoForJson song) {
        return SongDto.builder()
                      .name(song.name())
                      .build();
    }
}
