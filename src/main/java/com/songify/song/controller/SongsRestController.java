package com.songify.song.controller;

import com.songify.song.controller.dto.request.*;
import com.songify.song.controller.dto.response.*;
import com.songify.song.model.Song;
import com.songify.song.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("songs")
public class SongsRestController {
    
    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;
    
    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            List<Song> limitedMap = songRetriever.findAllLimited(limit);
            return ResponseEntity.ok(SongMapper.mapCollectionToGetAllSongsResponseDto(limitedMap));
        }
        return ResponseEntity.ok(SongMapper.mapCollectionToGetAllSongsResponseDto(songRetriever.findAll()));
    }
    
    @GetMapping("{id}")
    public ResponseEntity<GetSongResponseDto> getSongByID(@PathVariable Long id,
                                                          @RequestHeader(required = false) String requestId) {
        log.info("requestId: {}", requestId);
        Song song = songRetriever.findSongById(id);
        return ResponseEntity.ok(SongMapper.mapSongToGetSongResponseDto(song));
    }
    
    @PostMapping
    public ResponseEntity<PostSongResponseDto> addSong(@RequestBody @Valid PostSongRequestDto dto) {
        Song song = SongMapper.mapPostSongRequestDtoToSong(dto);
        Song addedSong = songAdder.addSong(song);
        return ResponseEntity.ok(SongMapper.mapSongToPostSongResponseDto(addedSong));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongById(@PathVariable Long id) {
        songDeleter.deleteById(id);
        return ResponseEntity.ok(SongMapper.mapSongIdToDeleteSongResponseDto(id));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<UpdateSongResponseDto> updateSongById(@PathVariable Long id,
                                                                @RequestBody @Valid UpdateSongRequestDto dto) {
        Song newSong = SongMapper.mapUpdateSongRequestDtoToSong(dto);
        songUpdater.updateById(id, newSong);
        log.info("updating song id: {}, with: {}", id, newSong);
        return ResponseEntity.ok(SongMapper.mapSongToUpdateSongResponseDto(newSong));
    }
    
    @PatchMapping("{id}")
    public ResponseEntity<PatchSongResponseDto> patchSongById(@PathVariable Long id,
                                                              @RequestBody PatchSongRequestDto dto) {
        Song updatedSong = SongMapper.mapFromPatchSongRequestDtoToSong(dto);
        Song updatePartiallyById = songUpdater.updatePartiallyById(id, updatedSong);
        return ResponseEntity.ok(SongMapper.mapPatchSongResponseDto(updatePartiallyById));
    }
}
