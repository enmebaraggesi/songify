package com.songify.song.controller;

import com.songify.song.dto.request.*;
import com.songify.song.dto.response.*;
import com.songify.song.error.SongNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
            return ResponseEntity.ok(new GetAllSongsResponseDto(limitedMap));
        }
        GetAllSongsResponseDto responseDto = new GetAllSongsResponseDto(database);
        return ResponseEntity.ok(responseDto);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<GetSongResponseDto> getSongByID(@PathVariable Integer id,
                                                          @RequestHeader(required = false) String requestId) {
        log.info("requestId: {}", requestId);
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        GetSongResponseDto responseDto = new GetSongResponseDto(database.get(id));
        return ResponseEntity.ok(responseDto);
    }
    
    @PostMapping
    public ResponseEntity<PostSongResponseDto> postSong(@RequestBody @Valid PostSongRequestDto songRequest) {
        Song newSong = new Song(songRequest.songName(), songRequest.artist());
        log.info("adding new song: {}", newSong);
        database.put(database.size() + 1, newSong);
        return ResponseEntity.ok(new PostSongResponseDto(newSong));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongById(@PathVariable Integer id) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        database.remove(id);
        return ResponseEntity.ok(new DeleteSongResponseDto("you deleted song by id: " + id, HttpStatus.OK));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<UpdateSongResponseDto> updateSongById(@PathVariable Integer id,
                                                                @RequestBody @Valid UpdateSongRequestDto songRequest) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        Song newSong = new Song(songRequest.songName(), songRequest.artist());
        Song oldSong = database.put(id, newSong);
        log.info("updating song id: {}, {}, with new: {}", id, oldSong, newSong);
        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.name(), newSong.artist()));
    }
    
    @PatchMapping("{id}")
    public ResponseEntity<PatchSongResponseDto> patchSongById(@PathVariable Integer id,
                                                              @RequestBody PatchSongRequestDto songRequest) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
        Song songFromDatabase = database.get(id);
        Song.SongBuilder builder = Song.builder();
        if (songRequest.songName() != null) {
            builder.name(songRequest.songName());
            log.info("patched song id: {}, with new name: {}", id, songRequest.songName());
        } else {
            builder.name(songFromDatabase.name());
        }
        if (songRequest.artist() != null) {
            builder.artist(songRequest.artist());
            log.info("patched song id: {}, with new artist: {}", id, songRequest.artist());
        } else {
            builder.artist(songFromDatabase.artist());
        }
        Song newSong = builder.build();
        database.put(id, newSong);
        return ResponseEntity.ok(new PatchSongResponseDto("updated song"));
    }
}
