package com.songify.song.service;

import com.songify.song.controller.dto.request.*;
import com.songify.song.controller.dto.response.*;
import com.songify.song.model.Song;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class SongMapper {
    
    public static Song mapPostSongRequestDtoToSong(PostSongRequestDto dto) {
        return new Song(dto.songName(), dto.artist());
    }
    
    public static PostSongResponseDto mapSongToPostSongResponseDto(Song song) {
        return new PostSongResponseDto(song);
    }
    
    public static GetAllSongsResponseDto mapCollectionToGetAllSongsResponseDto(Map<Integer, Song> collection) {
        return new GetAllSongsResponseDto(collection);
    }
    
    public static GetSongResponseDto mapSongToGetSongResponseDto(Song song) {
        return new GetSongResponseDto(song);
    }
    
    public static Song mapUpdateSongRequestDtoToSong(UpdateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artist());
    }
    
    public static UpdateSongResponseDto mapSongToUpdateSongResponseDto(Song song) {
        return new UpdateSongResponseDto(song.name(), song.artist());
    }
    
    public static DeleteSongResponseDto mapSongIdToDeleteSongResponseDto(Integer id) {
        return new DeleteSongResponseDto("you deleted song by id: " + id, HttpStatus.OK);
    }
    
    public static Song mapFromPatchSongRequestDtoToSong(PatchSongRequestDto dto) {
        return new Song(dto.songName(), dto.artist());
    }
    
    public static PatchSongResponseDto mapPatchSongResponseDto() {
        return new PatchSongResponseDto("updated song");
    }
}
