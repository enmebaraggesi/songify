package com.songify.infrastructure.controller;

import com.songify.domain.crud.song.Song;
import com.songify.domain.crud.song.SongCrudFacade;
import com.songify.infrastructure.controller.dto.request.PatchSongRequestDto;
import com.songify.infrastructure.controller.dto.request.PostSongRequestDto;
import com.songify.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.controller.dto.response.PatchSongResponseDto;
import com.songify.infrastructure.controller.dto.response.PostSongResponseDto;
import com.songify.infrastructure.controller.dto.response.UpdateSongResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.songify.infrastructure.controller.SongMapper.mapCollectionToGetAllSongsResponseDto;
import static com.songify.infrastructure.controller.SongMapper.mapFromPatchSongRequestDtoToSong;
import static com.songify.infrastructure.controller.SongMapper.mapPatchSongResponseDto;
import static com.songify.infrastructure.controller.SongMapper.mapPostSongRequestDtoToSong;
import static com.songify.infrastructure.controller.SongMapper.mapSongIdToDeleteSongResponseDto;
import static com.songify.infrastructure.controller.SongMapper.mapSongToGetSongResponseDto;
import static com.songify.infrastructure.controller.SongMapper.mapSongToPostSongResponseDto;
import static com.songify.infrastructure.controller.SongMapper.mapSongToUpdateSongResponseDto;
import static com.songify.infrastructure.controller.SongMapper.mapUpdateSongRequestDtoToSong;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("songs")
public class SongsRestController {
    
    private final SongCrudFacade songCrudFacade;
    
    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault(size = 20) Pageable pageable) {
        List<Song> songList = songCrudFacade.findAll(pageable);
        return ResponseEntity.ok(mapCollectionToGetAllSongsResponseDto(songList));
    }
    
    @GetMapping("{id}")
    public ResponseEntity<GetSongResponseDto> getSongByID(@PathVariable Long id,
                                                          @RequestHeader(required = false) String requestId) {
        log.info("requestId: {}", requestId);
        Song song = songCrudFacade.findSongById(id);
        return ResponseEntity.ok(mapSongToGetSongResponseDto(song));
    }
    
    @PostMapping
    public ResponseEntity<PostSongResponseDto> addSong(@RequestBody @Valid PostSongRequestDto dto) {
        Song song = mapPostSongRequestDtoToSong(dto);
        Song addedSong = songCrudFacade.addSong(song);
        return ResponseEntity.ok(mapSongToPostSongResponseDto(addedSong));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongById(@PathVariable Long id) {
        songCrudFacade.deleteById(id);
        return ResponseEntity.ok(mapSongIdToDeleteSongResponseDto(id));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<UpdateSongResponseDto> updateSongById(@PathVariable Long id,
                                                                @RequestBody @Valid UpdateSongRequestDto dto) {
        Song newSong = mapUpdateSongRequestDtoToSong(dto);
        songCrudFacade.updateById(id, newSong);
        log.info("updating song id: {}, with: {}", id, newSong);
        return ResponseEntity.ok(mapSongToUpdateSongResponseDto(newSong));
    }
    
    @PatchMapping("{id}")
    public ResponseEntity<PatchSongResponseDto> patchSongById(@PathVariable Long id,
                                                              @RequestBody PatchSongRequestDto dto) {
        Song updatedSong = mapFromPatchSongRequestDtoToSong(dto);
        Song updatePartiallyById = songCrudFacade.updatePartiallyById(id, updatedSong);
        return ResponseEntity.ok(mapPatchSongResponseDto(updatePartiallyById));
    }
}
