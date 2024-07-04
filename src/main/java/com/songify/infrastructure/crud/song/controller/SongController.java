package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PatchSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PostSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PatchSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PostSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SongDtoForJson;
import com.songify.infrastructure.crud.song.controller.dto.response.UpdateSongResponseDto;
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

import static com.songify.infrastructure.crud.song.controller.SongMapper.mapFromPatchSongRequestDtoToSongDtoForJson;
import static com.songify.infrastructure.crud.song.controller.SongMapper.mapPatchSongResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongMapper.mapPostSongRequestDtoToSongDtoForJson;
import static com.songify.infrastructure.crud.song.controller.SongMapper.mapSongDtoForJsonToGetSongResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongMapper.mapSongDtoForJsonToUpdateSongResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongMapper.mapSongDtoListToGetAllSongsResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongMapper.mapSongDtoToPostSongResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongMapper.mapSongDtoToSongDtoForJson;
import static com.songify.infrastructure.crud.song.controller.SongMapper.mapSongIdToDeleteSongResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongMapper.mapUpdateSongRequestDtoToSong;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("songs")
public class SongController {
    
    private final SongifyCrudFacade songifyCrudFacade;
    
    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault(size = 20, sort = {"id"}) Pageable pageable) {
        List<SongDto> songDtoList = songifyCrudFacade.findAllSongs(pageable);
        return ResponseEntity.ok(mapSongDtoListToGetAllSongsResponseDto(songDtoList));
    }
    
    @GetMapping("{id}")
    public ResponseEntity<GetSongResponseDto> getSongByID(@PathVariable Long id,
                                                          @RequestHeader(required = false) String requestId) {
        log.info("requestId: {}", requestId);
        SongDtoForJson song = mapSongDtoToSongDtoForJson(songifyCrudFacade.findSongById(id));
        return ResponseEntity.ok(mapSongDtoForJsonToGetSongResponseDto(song));
    }
    
    @PostMapping
    public ResponseEntity<PostSongResponseDto> addSongWithArtist(@RequestBody @Valid PostSongRequestDto dto) {
        SongDtoForJson song = mapPostSongRequestDtoToSongDtoForJson(dto);
        SongDto addedSong = songifyCrudFacade.addSong(song);
        return ResponseEntity.ok(mapSongDtoToPostSongResponseDto(addedSong));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongById(@PathVariable Long id) {
        songifyCrudFacade.deleteSongById(id);
        return ResponseEntity.ok(mapSongIdToDeleteSongResponseDto(id));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<UpdateSongResponseDto> updateSongById(@PathVariable Long id,
                                                                @RequestBody @Valid UpdateSongRequestDto requestDto) {
        SongDtoForJson newSong = mapUpdateSongRequestDtoToSong(requestDto);
        songifyCrudFacade.updateSongById(id, newSong);
        log.info("updating song id: {}, with: {}", id, newSong);
        return ResponseEntity.ok(mapSongDtoForJsonToUpdateSongResponseDto(newSong));
    }
    
    @PatchMapping("{id}")
    public ResponseEntity<PatchSongResponseDto> patchSongById(@PathVariable Long id,
                                                              @RequestBody PatchSongRequestDto requestDto) {
        SongDtoForJson updatedSong = mapFromPatchSongRequestDtoToSongDtoForJson(requestDto);
        SongDto updatePartiallyById = songifyCrudFacade.updateSongPartiallyById(id, updatedSong);
        SongDtoForJson songDtoForJson = mapSongDtoToSongDtoForJson(updatePartiallyById);
        return ResponseEntity.ok(mapPatchSongResponseDto(songDtoForJson));
    }
}
