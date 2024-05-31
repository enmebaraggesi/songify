package com.songify;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
public class SongsController {
    
    Map<Integer, String> database = new HashMap<>();
    
    public SongsController() {
        database.put(1, "abc");
        database.put(2, "def");
        database.put(3, "ghi");
        database.put(4, "jkl");
    }
    
    @GetMapping("songs")
    public ResponseEntity<SongResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, String> limitedMap = database.entrySet().stream()
                                                      .limit(limit)
                                                      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return ResponseEntity.ok(new SongResponseDto(limitedMap));
        }
        SongResponseDto responseDto = new SongResponseDto(database);
        return ResponseEntity.ok(responseDto);
    }
    
    @GetMapping("songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongByID(@PathVariable Integer id,
                                                             @RequestHeader(required = false) String requestId) {
        log.info("requestId: {}", requestId);
        String song = database.get(id);
        if (song == null) {
            return ResponseEntity.notFound().build();
        }
        SingleSongResponseDto responseDto = new SingleSongResponseDto(song);
        return ResponseEntity.ok(responseDto);
    }
    
    @PostMapping("songs")
    public ResponseEntity<SingleSongResponseDto> postSong(@RequestBody @Valid SongRequestDto songRequest) {
        String songName = songRequest.songName();
        log.info("adding new song: {}", songName);
        database.put(database.size() + 1, songName);
        return ResponseEntity.ok(new SingleSongResponseDto(songName));
    }
}
