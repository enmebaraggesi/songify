package com.songify.song.controller;

import com.songify.song.controller.dto.request.*;
import com.songify.song.controller.dto.response.*;
import com.songify.song.model.Song;
import com.songify.song.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.songify.song.service.SongMapper.*;

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
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault(size = 20) Pageable pageable) {
        List<Song> songList = songRetriever.findAll(pageable);
        return ResponseEntity.ok(mapCollectionToGetAllSongsResponseDto(songList));
    }
    
    @GetMapping("{id}")
    public ResponseEntity<GetSongResponseDto> getSongByID(@PathVariable Long id,
                                                          @RequestHeader(required = false) String requestId) {
        log.info("requestId: {}", requestId);
        Song song = songRetriever.findSongById(id);
        return ResponseEntity.ok(mapSongToGetSongResponseDto(song));
    }
    
    @PostMapping
    public ResponseEntity<PostSongResponseDto> addSong(@RequestBody @Valid PostSongRequestDto dto) {
        Song song = mapPostSongRequestDtoToSong(dto);
        Song addedSong = songAdder.addSong(song);
        return ResponseEntity.ok(mapSongToPostSongResponseDto(addedSong));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongById(@PathVariable Long id) {
        songDeleter.deleteById(id);
        return ResponseEntity.ok(mapSongIdToDeleteSongResponseDto(id));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<UpdateSongResponseDto> updateSongById(@PathVariable Long id,
                                                                @RequestBody @Valid UpdateSongRequestDto dto) {
        Song newSong = mapUpdateSongRequestDtoToSong(dto);
        songUpdater.updateById(id, newSong);
        log.info("updating song id: {}, with: {}", id, newSong);
        return ResponseEntity.ok(mapSongToUpdateSongResponseDto(newSong));
    }
    
    @PatchMapping("{id}")
    public ResponseEntity<PatchSongResponseDto> patchSongById(@PathVariable Long id,
                                                              @RequestBody PatchSongRequestDto dto) {
        Song updatedSong = mapFromPatchSongRequestDtoToSong(dto);
        Song updatePartiallyById = songUpdater.updatePartiallyById(id, updatedSong);
        return ResponseEntity.ok(mapPatchSongResponseDto(updatePartiallyById));
    }
}
