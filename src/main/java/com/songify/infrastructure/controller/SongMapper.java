package com.songify.infrastructure.controller;

import com.songify.domain.crud.song.Song;
import com.songify.infrastructure.controller.dto.request.PatchSongRequestDto;
import com.songify.infrastructure.controller.dto.request.PostSongRequestDto;
import com.songify.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.controller.dto.response.PatchSongResponseDto;
import com.songify.infrastructure.controller.dto.response.PostSongResponseDto;
import com.songify.infrastructure.controller.dto.response.SongDto;
import com.songify.infrastructure.controller.dto.response.UpdateSongResponseDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SongMapper {
    
    public static SongDto mapSongToSongDto(Song song) {
        return new SongDto(song.getId(), song.getName(), song.getArtist());
    }
    
    public static Song mapPostSongRequestDtoToSong(PostSongRequestDto dto) {
        return new Song(dto.songName(), dto.artist());
    }
    
    public static PostSongResponseDto mapSongToPostSongResponseDto(Song song) {
        SongDto songDto = mapSongToSongDto(song);
        return new PostSongResponseDto(songDto);
    }
    
    public static GetAllSongsResponseDto mapCollectionToGetAllSongsResponseDto(List<Song> songs) {
        List<SongDto> songDtos = songs.stream()
                                      .map(SongMapper::mapSongToSongDto)
                                      .toList();
        return new GetAllSongsResponseDto(songDtos);
    }
    
    public static GetSongResponseDto mapSongToGetSongResponseDto(Song song) {
        SongDto songDto = mapSongToSongDto(song);
        return new GetSongResponseDto(songDto);
    }
    
    public static Song mapUpdateSongRequestDtoToSong(UpdateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artist());
    }
    
    public static UpdateSongResponseDto mapSongToUpdateSongResponseDto(Song song) {
        return new UpdateSongResponseDto(song.getName(), song.getArtist());
    }
    
    public static DeleteSongResponseDto mapSongIdToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("you deleted song by id: " + id, HttpStatus.OK);
    }
    
    public static Song mapFromPatchSongRequestDtoToSong(PatchSongRequestDto dto) {
        return new Song(dto.songName(), dto.artist());
    }
    
    public static PatchSongResponseDto mapPatchSongResponseDto(Song song) {
        return new PatchSongResponseDto("updated song " + song.getName() + " by " + song.getArtist());
    }
}
