package com.songify.song.controller;

import com.songify.song.controller.dto.request.*;
import com.songify.song.controller.dto.response.*;
import com.songify.song.error.SongNotFoundException;
import com.songify.song.model.Song;
import com.songify.song.service.SongMapper;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("songs")
public class SongsRestController {
    
    Map<Integer, Song> database = new HashMap<>();
    
    public SongsRestController() {
        database.put(1, new Song("abc", "cba"));
        database.put(2, new Song("def", "fed"));
        database.put(3, new Song("ghi", "ihg"));
        database.put(4, new Song("jkl", "lkj"));
    }
    
    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, Song> limitedMap = database.entrySet()
                                                    .stream()
                                                    .limit(limit)
                                                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return ResponseEntity.ok(SongMapper.mapCollectionToGetAllSongsResponseDto(limitedMap));
        }
        return ResponseEntity.ok(SongMapper.mapCollectionToGetAllSongsResponseDto(database));
    }
    
    @GetMapping("{id}")
    public ResponseEntity<GetSongResponseDto> getSongByID(@PathVariable Integer id,
                                                          @RequestHeader(required = false) String requestId) {
        log.info("requestId: {}", requestId);
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        Song song = database.get(id);
        return ResponseEntity.ok(SongMapper.mapSongToGetSongResponseDto(song));
    }
    
    @PostMapping
    public ResponseEntity<PostSongResponseDto> postSong(@RequestBody @Valid PostSongRequestDto dto) {
        Song song = SongMapper.mapPostSongRequestDtoToSong(dto);
        log.info("adding new song: {}", song);
        database.put(database.size() + 1, song);
        return ResponseEntity.ok(SongMapper.mapSongToPostSongResponseDto(song));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongById(@PathVariable Integer id) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        database.remove(id);
        return ResponseEntity.ok(SongMapper.mapSongIdToDeleteSongResponseDto(id));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<UpdateSongResponseDto> updateSongById(@PathVariable Integer id,
                                                                @RequestBody @Valid UpdateSongRequestDto dto) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        Song newSong = SongMapper.mapUpdateSongRequestDtoToSong(dto);
        Song oldSong = database.put(id, newSong);
        log.info("updating song id: {}, {}, with new: {}", id, oldSong, newSong);
        return ResponseEntity.ok(SongMapper.mapSongToUpdateSongResponseDto(newSong));
    }
    
    @PatchMapping("{id}")
    public ResponseEntity<PatchSongResponseDto> patchSongById(@PathVariable Integer id,
                                                              @RequestBody PatchSongRequestDto dto) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        Song songFromDatabase = database.get(id);
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
        database.put(id, newSong);
        return ResponseEntity.ok(SongMapper.mapPatchSongResponseDto());
    }
}
