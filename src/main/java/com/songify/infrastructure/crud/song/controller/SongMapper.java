package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PatchSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PostSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.FullSongDtoForJson;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PatchSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PostSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SongDtoForJson;
import com.songify.infrastructure.crud.song.controller.dto.response.UpdateSongResponseDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SongMapper {
    
    public static SongDtoForJson mapSongDtoToSongDtoForJson(SongDto dto) {
        return SongDtoForJson.builder()
                             .id(dto.id())
                             .title(dto.name())
                             .language(dto.language())
                             .duration(dto.duration())
                             .releaseDate(dto.releaseDate())
                             .build();
    }
    
    static SongDtoForJson mapPostSongRequestDtoToSongDtoForJson(PostSongRequestDto dto) {
        return SongDtoForJson.builder()
                             .title(dto.title())
                             .language(dto.language())
                             .releaseDate(dto.releaseDate())
                             .duration(dto.duration())
                             .build();
    }
    
    public static PostSongResponseDto mapSongDtoToPostSongResponseDto(SongDto song) {
        SongDtoForJson songDtoForJson = mapSongDtoToSongDtoForJson(song);
        return new PostSongResponseDto(songDtoForJson);
    }
    
    public static GetAllSongsResponseDto mapSongDtoListToGetAllSongsResponseDto(List<SongDto> songs) {
        List<SongDtoForJson> responseDtoList = songs.stream()
                                                    .map(SongMapper::mapSongDtoToSongDtoForJson)
                                                    .toList();
        return new GetAllSongsResponseDto(responseDtoList);
    }
    
    public static GetSongResponseDto mapSongDtoForJsonToGetSongResponseDto(FullSongDtoForJson song) {
        return new GetSongResponseDto(song);
    }
    
    public static SongDtoForJson mapUpdateSongRequestDtoToSong(UpdateSongRequestDto requestDto) {
        return SongDtoForJson.builder()
                             .title(requestDto.songName())
                             .build();
    }
    
    public static UpdateSongResponseDto mapSongDtoForJsonToUpdateSongResponseDto(SongDtoForJson song) {
        return new UpdateSongResponseDto(song.title());
    }
    
    public static DeleteSongResponseDto mapSongIdToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("you deleted song by id: " + id, HttpStatus.OK);
    }
    
    public static SongDtoForJson mapFromPatchSongRequestDtoToSongDtoForJson(PatchSongRequestDto requestDto) {
        return SongDtoForJson.builder()
                             .title(requestDto.songName())
                             .build();
    }
    
    public static PatchSongResponseDto mapPatchSongResponseDto(SongDtoForJson song) {
        return new PatchSongResponseDto("updated song " + song.title());
    }
    
    static FullSongDtoForJson mapSongDtoToFullSongDtoForJson(final SongDto songById) {
        return new FullSongDtoForJson(songById.id(),
                                      songById.name(),
                                      songById.language(),
                                      songById.releaseDate(),
                                      songById.duration(),
                                      new GenreDto(songById.genre().id(),
                                                   songById.genre().name()));
    }
}
