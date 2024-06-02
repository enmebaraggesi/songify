package com.songify.song.controller;

import com.songify.song.controller.dto.request.*;
import com.songify.song.controller.dto.response.*;
import com.songify.song.error.SongNotFoundException;
import com.songify.song.model.Song;
import com.songify.song.service.*;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("songs")
public class SongsRestController {
    
    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    
    public SongsRestController(SongAdder songAdder, SongRetriever songRetriever) {
        this.songAdder = songAdder;
        this.songRetriever = songRetriever;
    }
    
    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, Song> limitedMap = songRetriever.findAllLimited(limit);
            return ResponseEntity.ok(SongMapper.mapCollectionToGetAllSongsResponseDto(limitedMap));
        }
        return ResponseEntity.ok(SongMapper.mapCollectionToGetAllSongsResponseDto(songRetriever.findAll()));
    }
    
    @GetMapping("{id}")
    public ResponseEntity<GetSongResponseDto> getSongByID(@PathVariable Integer id,
                                                          @RequestHeader(required = false) String requestId) {
        log.info("requestId: {}", requestId);
        Map<Integer, Song> allSongs = songRetriever.findAll();
        if (!allSongs.containsKey(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        Song song = allSongs.get(id);
        return ResponseEntity.ok(SongMapper.mapSongToGetSongResponseDto(song));
    }
    
    @PostMapping
    public ResponseEntity<PostSongResponseDto> postSong(@RequestBody @Valid PostSongRequestDto dto) {
        Song song = SongMapper.mapPostSongRequestDtoToSong(dto);
        Song added = songAdder.addSong(song);
        return ResponseEntity.ok(SongMapper.mapSongToPostSongResponseDto(added));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongById(@PathVariable Integer id) {
        Map<Integer, Song> allSongs = songRetriever.findAll();
        if (!allSongs.containsKey(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        allSongs.remove(id);
        return ResponseEntity.ok(SongMapper.mapSongIdToDeleteSongResponseDto(id));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<UpdateSongResponseDto> updateSongById(@PathVariable Integer id,
                                                                @RequestBody @Valid UpdateSongRequestDto dto) {
        Map<Integer, Song> allSongs = songRetriever.findAll();
        if (!allSongs.containsKey(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        Song newSong = SongMapper.mapUpdateSongRequestDtoToSong(dto);
        Song oldSong = allSongs.put(id, newSong);
        log.info("updating song id: {}, {}, with new: {}", id, oldSong, newSong);
        return ResponseEntity.ok(SongMapper.mapSongToUpdateSongResponseDto(newSong));
    }
    
    @PatchMapping("{id}")
    public ResponseEntity<PatchSongResponseDto> patchSongById(@PathVariable Integer id,
                                                              @RequestBody PatchSongRequestDto dto) {
        Map<Integer, Song> allSongs = songRetriever.findAll();
        if (!allSongs.containsKey(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        Song songFromDatabase = allSongs.get(id);
        Song updatedSong = SongMapper.mapFromPatchSongRequestDtoToSong(dto);
        Song.SongBuilder builder = Song.builder();
        if (dto.songName() != null) {
            builder.name(updatedSong.name());
            log.info("patched song id: {}, with new name: {}", id, updatedSong.name());
        } else {
            builder.name(songFromDatabase.name());
        }
        if (dto.artist() != null) {
            builder.artist(updatedSong.artist());
            log.info("patched song id: {}, with new artist: {}", id, updatedSong.artist());
        } else {
            builder.artist(songFromDatabase.artist());
        }
        Song newSong = builder.build();
        allSongs.put(id, newSong);
        return ResponseEntity.ok(SongMapper.mapPatchSongResponseDto());
    }
}
