package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PatchSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PostSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PatchSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PostSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SongDtoForJson;
import com.songify.infrastructure.crud.song.controller.dto.response.UpdateSongResponseDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SongMapper {
    
    public static SongDtoForJson mapSongDtoToSongDtoForJson(SongDto songDto) {
        return SongDtoForJson.builder()
                             .id(songDto.id())
                             .name(songDto.name())
                             .build();
    }
    
    static SongDtoForJson mapPostSongRequestDtoToSongDtoForJson(PostSongRequestDto requestDto) {
        return SongDtoForJson.builder()
                             .name(requestDto.songName())
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
    
    public static GetSongResponseDto mapSongDtoForJsonToGetSongResponseDto(SongDtoForJson song) {
        return new GetSongResponseDto(song);
    }
    
    public static SongDtoForJson mapUpdateSongRequestDtoToSong(UpdateSongRequestDto requestDto) {
        return SongDtoForJson.builder()
                             .name(requestDto.songName())
                             .build();
    }
    
    public static UpdateSongResponseDto mapSongDtoForJsonToUpdateSongResponseDto(SongDtoForJson song) {
        return new UpdateSongResponseDto(song.name());
    }
    
    public static DeleteSongResponseDto mapSongIdToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("you deleted song by id: " + id, HttpStatus.OK);
    }
    
    public static SongDtoForJson mapFromPatchSongRequestDtoToSongDtoForJson(PatchSongRequestDto requestDto) {
        return SongDtoForJson.builder()
                             .name(requestDto.songName())
                             .build();
    }
    
    public static PatchSongResponseDto mapPatchSongResponseDto(SongDtoForJson song) {
        return new PatchSongResponseDto("updated song " + song.name());
    }
}
