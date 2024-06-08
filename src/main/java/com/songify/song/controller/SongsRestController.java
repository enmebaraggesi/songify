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

import java.util.List;

@Log4j2
@RestController
@RequestMapping("songs")
public class SongsRestController {
    
    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    
    public SongsRestController(SongAdder songAdder, SongRetriever songRetriever, SongDeleter songDeleter) {
        this.songAdder = songAdder;
        this.songRetriever = songRetriever;
        this.songDeleter = songDeleter;
    }
    
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
        Song song = songRetriever.findSongById(id)
                                 .orElseThrow(() -> new SongNotFoundException("cannot find song with id " + id));
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
        songRetriever.existsById(id);
        songDeleter.deleteById(id);
        return ResponseEntity.ok(SongMapper.mapSongIdToDeleteSongResponseDto(id));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<UpdateSongResponseDto> updateSongById(@PathVariable Integer id,
                                                                @RequestBody @Valid UpdateSongRequestDto dto) {
        List<Song> allSongs = songRetriever.findAll();
        if (!allSongs.contains(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        Song newSong = SongMapper.mapUpdateSongRequestDtoToSong(dto);
        Song oldSong = songAdder.addSong(newSong);
        log.info("updating song id: {}, {}, with new: {}", id, oldSong, newSong);
        return ResponseEntity.ok(SongMapper.mapSongToUpdateSongResponseDto(newSong));
    }
    
    @PatchMapping("{id}")
    public ResponseEntity<PatchSongResponseDto> patchSongById(@PathVariable Integer id,
                                                              @RequestBody PatchSongRequestDto dto) {
        List<Song> allSongs = songRetriever.findAll();
        if (!allSongs.contains(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        Song songFromDatabase = allSongs.get(id);
        Song updatedSong = SongMapper.mapFromPatchSongRequestDtoToSong(dto);
        Song.SongBuilder builder = Song.builder();
        if (dto.songName() != null) {
            builder.name(updatedSong.getName());
            log.info("patched song id: {}, with new name: {}", id, updatedSong.getName());
        } else {
            builder.name(songFromDatabase.getName());
        }
        if (dto.artist() != null) {
            builder.artist(updatedSong.getArtist());
            log.info("patched song id: {}, with new artist: {}", id, updatedSong.getArtist());
        } else {
            builder.artist(songFromDatabase.getArtist());
        }
        Song newSong = builder.build();
        songAdder.addSong(newSong);
        return ResponseEntity.ok(SongMapper.mapPatchSongResponseDto());
    }
}
